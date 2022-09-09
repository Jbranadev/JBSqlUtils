/***
 * Copyright (C) 2022 El proyecto de código abierto JBSqlUtils de José Bran
 *
 * Con licencia de Apache License, Versión 2.0 (la "Licencia");
 * no puede usar este archivo excepto de conformidad con la Licencia.
 * Puede obtener una copia de la Licencia en
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * A menos que lo exija la ley aplicable o se acuerde por escrito, el software
 * distribuido bajo la Licencia se distribuye "TAL CUAL",
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
 * Consulte la Licencia para conocer el idioma específico que rige los permisos y
 * limitaciones bajo la Licencia.
 */
package io.github.josecarlosbran.JBSqlUtils;

import com.josebran.LogsJB.LogsJB;
import io.github.josecarlosbran.JBSqlUtils.DataBase.Where;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Jose Bran
 * Clase que proporciona metodos que permiten al modelo ejecutar sentencias DML, así como crear o eliminar la tabla
 * correspondiente al modelo
 */
class Methods extends Methods_Conexion {

    /**
     * Constructor por default de la clase Methods
     *
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public Methods() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    /**
     * Crea la tabla correspondiente al modelo en BD's si esta no existe.
     *
     * @return True si la tabla correspondiente al modelo en BD's no existe y fue creada exitosamente,
     * False si la tabla correspondiente al modelo ya existe en BD's
     */
    public Boolean crateTable() {
        Boolean result = false;
        try {
            Callable<Boolean> createtabla = () -> {
                try {
                    if (this.tableExist()) {
                        LogsJB.info("La tabla correspondiente al modelo ya existe en la BD's, por lo cual no será creada.");
                        return false;
                    } else {
                        String sql = "CREATE TABLE " + this.getTableName() + "(";
                        List<Method> metodos = new ArrayList<>();
                        metodos = this.getMethodsGetOfModel(this.getMethodsModel());
                        //Aquí vamos a ordenar la lista
                        LogsJB.debug("Comienza a ordenar la lista");
                        metodos.sort((metodo1,metodo2)->{
                            try {

                                Column columna1 = (Column) metodo1.invoke(this, null);
                                Column columna2 = (Column) metodo2.invoke(this, null);
                                LogsJB.trace("Columnas a evaluar: "+metodo1.getName()+"  "+metodo2.getName());
                                if(columna1.getDataTypeSQL().getOrden()>columna2.getDataTypeSQL().getOrden()){
                                    LogsJB.trace("Columna de metodo 1 es mayor");
                                    return 1;
                                }else if(columna2.getDataTypeSQL().getOrden()>columna1.getDataTypeSQL().getOrden()){
                                    LogsJB.trace("Columna de metodo 2 es mayor");
                                    return -1;
                                }else{
                                    LogsJB.trace("Columnas son iguales");
                                    return 0;
                                }
                            } catch (Exception e) {
                                LogsJB.fatal("Excepción disparada al tratar de ordenar los metodos get de la lista: " + e.toString());
                                LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                                LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                                LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                                LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                            }
                            return 0;
                        });


                        LogsJB.info("Termino de ordenar la lista");


                        int datos = 0;
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(this, null);
                            String columnName = metodo.getName();
                            columnName = StringUtils.removeStartIgnoreCase(columnName, "get");
                            DataType columnType = columnsSQL.getDataTypeSQL();
                            //Manejo de tipo de dato TimeStamp en SQLServer
                            if ((columnType == DataType.TIMESTAMP) && (this.getDataBaseType() == DataBase.SQLServer)) {
                                columnType = DataType.DATETIME;
                            }
                            Constraint[] columnRestriccion = columnsSQL.getRestriccion();
                            String restricciones = "";
                            String tipo_de_columna = columnType.toString();
                            if ((((this.getDataBaseType() == DataBase.PostgreSQL)) || ((this.getDataBaseType() == DataBase.MySQL))
                                    || ((this.getDataBaseType() == DataBase.SQLite))) &&
                                    (columnType == DataType.BIT)) {
                                tipo_de_columna = DataType.BOOLEAN.toString();
                            }
                            if (!Objects.isNull(columnRestriccion)) {
                                for (Constraint restriccion : columnRestriccion) {
                                    if ((DataBase.PostgreSQL == this.getDataBaseType()) &&
                                            (restriccion == Constraint.AUTO_INCREMENT)) {
                                        tipo_de_columna = DataType.SERIAL.name();
                                    } else if ((DataBase.SQLServer == this.getDataBaseType()) &&
                                            (restriccion == Constraint.AUTO_INCREMENT)) {
                                        //tipo_de_columna = DataType.IDENTITY.toString();
                                        restricciones = restricciones + DataType.IDENTITY.toString() + " ";
                                    } else if ((DataBase.SQLite == this.getDataBaseType()) &&
                                            (restriccion == Constraint.AUTO_INCREMENT)) {
                                        restricciones = restricciones + "";
                                    } else if (restriccion == Constraint.DEFAULT) {
                                        restricciones = restricciones + restriccion.getRestriccion() + " " + columnsSQL.getDefault_value() + " ";

                                    } else {
                                        restricciones = restricciones + restriccion.getRestriccion() + " ";
                                    }


                                }
                            }

                            //Si el modelo tiene seteado que no se manejaran las timestamps entonces
                            //Ignora el guardar esas columnas
                            if ((!this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, "created_at"))
                                    || (StringUtils.equalsIgnoreCase(columnName, "updated_at")))) {
                                continue;
                            }

                            String columna = columnName + " " + tipo_de_columna + " " + restricciones;

                            datos++;
                            if (datos > 1) {
                                sql = sql + ", ";
                            }

                            sql = sql + columna;
                            /*int temporal = metodos.size() - 1;
                            if (i < temporal) {
                                sql = sql + ", ";
                            } else if (i == temporal) {
                                sql = sql + ");";
                            }*/

                        }
                        sql = sql + ");";
                        Connection connect = this.getConnection();
                        Statement ejecutor = connect.createStatement();
                        LogsJB.info(sql);
                        if (!ejecutor.execute(sql)) {
                            LogsJB.info("Sentencia para crear tabla de la BD's ejecutada exitosamente");
                            LogsJB.info("Tabla " + this.getTableName() + " Creada exitosamente");
                            LogsJB.info(sql);

                            this.closeConnection(connect);
                            this.refresh();
                            return true;
                        }
                        ejecutor.close();
                        this.closeConnection(connect);

                    }
                    return false;
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Crea la tabla correspondiente al modelo: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                }
                return false;
            };

            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            Future<Boolean> future = ejecutor.submit(createtabla);
            while (!future.isDone()) {

            }
            ejecutor.shutdown();
            result = future.get();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Crea la tabla correspondiente al modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }

    /**
     * Elimina la tabla correspondiente al modelo en BD's
     *
     * @return True si la tabla correspondiente al modelo en BD's existe y fue eliminada, de no existir la tabla correspondiente
     * en BD's retorna False.
     */
    public Boolean dropTableIfExist() {
        Boolean result = false;
        try {
            Connection connect = this.getConnection();
            Callable<Boolean> dropTable = () -> {
                try {
                    if (this.tableExist()) {
                        String sql = "";
                        if (this.getDataBaseType() == DataBase.MySQL || this.getDataBaseType() == DataBase.PostgreSQL || this.getDataBaseType() == DataBase.SQLite) {
                            sql = "DROP TABLE IF EXISTS " + this.getTableName();
                            //+ " RESTRICT";
                        } else if (this.getDataBaseType() == DataBase.SQLServer) {
                            sql = "if exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '" +
                                    this.getTableName() +
                                    "' AND TABLE_SCHEMA = 'dbo')\n" +
                                    "    drop table dbo." +
                                    this.getTableName();
                            //+" RESTRICT;";
                        }
                        LogsJB.info(sql);

                        Statement ejecutor = connect.createStatement();

                        if (!ejecutor.execute(sql)) {
                            LogsJB.info("Sentencia para eliminar tabla de la BD's ejecutada exitosamente");
                            LogsJB.info("Tabla " + this.getTableName() + " Eliminada exitosamente");
                            LogsJB.info(sql);
                            //this.setTableExist(false);
                            this.refresh();
                            return true;
                        }
                        ejecutor.close();
                        this.closeConnection(connect);

                    } else {
                        LogsJB.info("Tabla correspondiente al modelo no existe en BD's por eso no pudo ser eliminada");
                        return false;
                    }
                    return false;
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Elimina la tabla correspondiente al modelo: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                }
                return false;
            };

            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            Future<Boolean> future = ejecutor.submit(dropTable);
            while (!future.isDone()) {

            }
            ejecutor.shutdown();
            result = future.get();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Elimina la tabla correspondiente al modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }

    /**
     * Almacena la información del modelo que hace el llamado en BD's.'
     */
    public void save() {
        try {
            saveModel(this);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }


    /**
     * Almacena la información de los modelos proporcionados en BD's
     *
     * @param modelos Lista de modelos que serán Insertados o Actualizados
     * @param <T>     Tipo de parametro que hace que el metodo sea generico para poder ser
     *                llamado por diferentes tipos de objetos, siempre y cuando estos hereden la clase Methods Conexion.
     */
    public <T extends JBSqlUtils> void saveALL(List<T> modelos) {
        try {
            T temp = null;
            for (T modelo : modelos) {
                //Optimización de los tiempos de inserción de cada modelo.
                if (!Objects.isNull(temp)) {
                    modelo.setTabla(temp.getTabla());
                    modelo.setTableExist(temp.getTableExist());
                    modelo.setTableName(temp.getTableName());
                    //Con esto se maneja las tablas que existen en BD's
                    modelo.getTabla().setColumnsExist(temp.getTabla().getColumnsExist());
                    LogsJB.info("Modelo Ya había sido inicializado");
                } else {
                    temp = (T) modelo.getClass().newInstance();
                    LogsJB.warning("Modelo era Null, crea una nueva instancia");
                    temp.refresh();
                }
                if (!modelo.getTableExist()) {
                    LogsJB.info("Obtendra la información de conexión de la BD's");
                    modelo.refresh();
                    while (modelo.getTabla().getColumnas().size() == 0) {

                    }
                    LogsJB.info("Ya obtuvo la información de BD's'");
                    temp.setTabla(modelo.getTabla());
                    temp.setTableExist(modelo.getTableExist());
                    temp.setTableName(modelo.getTableName());
                    //Con esto se maneja las tablas que existen en BD's
                    temp.getTabla().setColumnsExist(modelo.getTabla().getColumnsExist());
                }
                modelo.saveModel(modelo);
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda la lista de modelos en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }


    /**
     * Elimina la información del modelo que hace el llamado en BD´s
     */
    public void delete() {
        try {
            deleteModel(this);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Elimina la información de los modelos proporcionados en BD's
     *
     * @param modelos Lista de modelos que serán Eliminados
     * @param <T>     Tipo de parametro que hace que el metodo sea generico para poder ser
     *                llamado por diferentes tipos de objetos, siempre y cuando estos hereden la clase Methods Conexion.
     */
    public <T extends JBSqlUtils> void deleteALL(List<T> modelos) {
        try {
            T temp = null;
            for (T modelo : modelos) {
                //Optimización de los tiempos de inserción de cada modelo.
                if (!Objects.isNull(temp)) {
                    modelo.setTabla(temp.getTabla());
                    modelo.setTableExist(temp.getTableExist());
                    modelo.setTableName(temp.getTableName());
                    LogsJB.info("Modelo Ya había sido inicializado");
                } else {
                    temp = (T) modelo.getClass().newInstance();
                    LogsJB.warning("Modelo era Null, crea una nueva instancia");
                }
                if (!modelo.getTableExist()) {
                    LogsJB.info("Obtendra la información de conexión de la BD's");
                    modelo.refresh();
                    while (modelo.getTabla().getColumnas().size() == 0) {

                    }
                    LogsJB.info("Ya obtuvo la información de BD's'");
                    temp.setTableExist(modelo.getTableExist());
                    temp.setTableName(modelo.getTableName());
                    temp.setTabla(modelo.getTabla());
                }
                modelo.deleteModel(modelo);
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda la lista de modelos en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }


    /**
     * Almacena la información del modelo que hace el llamado, esperando a que la operación termine de ser realizada
     *
     * @return Retorna True cuando se a terminado de insertar o actualizar la información del modelo en BD's
     */
    public Boolean saveBoolean() {
        Boolean result = false;
        try {
            saveModel(this);
            while (!this.getTaskIsReady()) {
            }
            result = this.getTaskIsReady();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }


    /**
     * Proporciona un punto de entrada para obtener uno o mas modelos del tipo de modelo que invoca este procedimiento
     *
     * @param columna  Columna que sera evaluada
     * @param operador Operador por medio del cual se evaluara la columna
     * @param valor    Valor contra el cual se evaluara la columna
     * @return Punto de entrada a metodos que permiten seguir modificando la expresión de filtro u obtener el o los
     * modelos que hacen match con la consulta generada
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     * @throws ValorUndefined        Lanza esta excepción si alguno de los parametros proporcionados esta
     *                               Vacío o es Null
     */
    public Where where(String columna, Operator operador, Object valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Where(columna, operador, valor, this);
    }


    /**
     * Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @throws InstantiationException Lanza esta excepción si ocurre un error al crear una nueva instancia
     *                                del tipo de modelo proporcionado
     * @throws IllegalAccessException Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     */
    public <T extends JBSqlUtils> List<T> getAll() throws InstantiationException, IllegalAccessException {
        this.setTaskIsReady(false);
        List<T> lista = new ArrayList<>();
        try {
            if (!this.getTableExist()) {
                this.refresh();
            }
            Connection connect = this.getConnection();
            //T finalTemp = temp;
            Runnable get = () -> {
                try {
                    if (this.getTableExist()) {
                        String sql = "SELECT * FROM " + this.getTableName();
                        sql = sql + ";";
                        LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        ResultSet registros = ejecutor.executeQuery();
                        while (registros.next()) {
                            lista.add(procesarResultSet((T) this, registros));
                            //procesarResultSet(modelo, registros);
                        }
                        this.closeConnection(connect);
                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "recuperar el Registro");
                    }
                    this.setTaskIsReady(true);
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Recupera la lista de registros que cumplen con la sentencia" +
                            "SQL de la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    this.setTaskIsReady(true);
                }
            };
            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            ejecutor.submit(get);
            ejecutor.shutdown();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que recupera los modelos de la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());

        }
        return lista;
    }


    /**
     * Llena el modelo con la información del controlador
     * @param controlador Controlador que debe poseer los atributos en java, que corresponden al modelo, con
     *                    sus respectivos metodos setter y getter
     * @param modelo Modelo que será llenado con la información del controlador
     * @param <T> Tipo de dato del controlador, acepta cualquier Object
     * @param <G> Tipo de dato del modelo, acepta unicamente aquellos que heredan de la clase JBSqlUtils
     */
    public <T, G extends JBSqlUtils> void llenarModelo(T controlador, G modelo){
        try{

            List<Method> controladorMethods = new ArrayList<>(Arrays.asList(controlador.getClass().getMethods()));
            for(Method controladorMethod : controladorMethods){
                String controllerName=controladorMethod.getName();
                String claseMethod=controladorMethod.getDeclaringClass().getSimpleName();
                LogsJB.debug("Nombre del metodo del controlador: "+controllerName+" Clase a la que pertenece: "+claseMethod);
                //Si la clase donde se declaro el metodo pertenece a la clase Object
                if(claseMethod.equalsIgnoreCase("Object")){
                    continue;
                }

                //Si el metodo no es un metodo get o set salta a la siguiente iteración
                if(!(StringUtils.startsWithIgnoreCase(controllerName, "get"))
                        && !(StringUtils.startsWithIgnoreCase(controllerName, "set"))){
                    continue;
                }

                //Si el metodo es un set, que continue, no tiene caso hacer lo siguiente
                if(StringUtils.startsWithIgnoreCase(controllerName, "set")){
                    continue;
                }

                int parametros=controladorMethod.getParameterCount();
                LogsJB.trace("Cantidad de parametros: "+parametros);
                if(parametros!=0){
                    continue;
                }

                //Validara si es un void
                if (controladorMethod.getReturnType().equals(Void.TYPE)){
                    LogsJB.debug("El metodo "+controladorMethod.getName()+" No retorna ningun tipo" +
                            "de dato por lo que no tiene caso continuar con la Iteración");
                    continue;
                }


                LogsJB.trace("Validara si el contenido es Null: "+controllerName);
                //Si el contenido es null, continua, no tiene caso hacer el resto
                Object contenido=(Object) controladorMethod.invoke(controlador, null);
                if(Objects.isNull(contenido)){
                    continue;
                }


                //Obtiene los metodos get del modelo
                List<Method> modelGetMethods=modelo.getMethodsGetOfModel(modelo.getMethodsModel());
                LogsJB.debug("Obtuvo los metodos Get del modelo: "+controllerName);
                for (Method modelGetMethod: modelGetMethods){
                    String modelGetName=modelGetMethod.getName();
                    /*Si el nombre del metodo get, no coincide con el nombre del metodo del
                    controlador, continua
                    * */
                    LogsJB.debug("Nombre del metodo Get del modelo: "+modelGetName);
                    if(!StringUtils.equalsIgnoreCase(controllerName, modelGetName)){
                        continue;
                    }
                    LogsJB.debug("Obtiene la columna: "+modelGetName);
                    //Obtengo la información de la columna
                    Column columnsSQL = (Column) modelGetMethod.invoke(modelo, null);
                    //Le meto la información a la columa
                    LogsJB.debug("Setea el contenido a la columna: "+modelGetName);
                    columnsSQL.setValor(contenido);
                    String columnName = modelGetMethod.getName();
                    columnName = StringUtils.removeStartIgnoreCase(columnName, "get");
                    LogsJB.debug("Nombre de la columna a validar: "+columnName);

                    //Obtiene los metodos set del modelo
                    List<Method> modelSetMethods=modelo.getMethodsSetOfModel(modelo.getMethodsModel());
                    LogsJB.debug("Obtuvo los metodos Set del modelo: "+modelGetName);

                    Boolean isready = false;

                    for (Method modelSetMethod: modelSetMethods){
                        String modelSetName=modelSetMethod.getName();
                        LogsJB.trace("Nombre del metodo: "+modelSetName);
                        //Si el metodo es un get, que continue, no tiene caso hacer lo siguiente
                        if(StringUtils.startsWithIgnoreCase(modelSetName, "get")){
                            continue;
                        }
                        LogsJB.trace("Si es un metodo Set: "+modelSetName);

                        modelSetName = StringUtils.removeStartIgnoreCase(modelSetName, "set");
                        LogsJB.trace("Nombre del metodo set a validar: "+modelSetName);
                        if(StringUtils.equalsIgnoreCase(modelSetName, columnName)){
                            //Setea el valor del metodo
                            modelSetMethod.invoke(modelo, columnsSQL);
                            LogsJB.info("Ingreso la columna en el metodo set: "+modelSetName);
                            isready=true;
                            break;
                        }
                    }
                    if(isready){
                        break;
                    }

                }
            }

        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada al llenar el modelo, con la info del controlador: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }

    /**
     *
     * @param controlador Controlador que debe poseer los atributos en java, que corresponden al modelo, con
     *                    sus respectivos metodos setter y getter
     * @param modelo Modelo del cual se extraera la información para llenar el controlador
     * @param <T> Tipo de dato del controlador, acepta cualquier Object
     * @param <G> Tipo de dato del modelo, acepta unicamente aquellos que heredan de la clase JBSqlUtils
     */
    public <T, G extends JBSqlUtils> void llenarControlador(T controlador, G modelo){
        try{
            //Obtiene los metodos get del modelo
            List<Method> modelGetMethods=modelo.getMethodsGetOfModel(modelo.getMethodsModel());
            LogsJB.debug("Obtuvo los metodos Get del modelo: ");
            for (Method modelGetMethod: modelGetMethods){
                String modelGetName=modelGetMethod.getName();
                LogsJB.debug("Nombre del metodo Get del modelo: "+modelGetName);
                //Obtengo la información de la columna
                Column columnsSQL = (Column) modelGetMethod.invoke(modelo, null);
                Object dato=columnsSQL.getValor();
                //Si el valor de la columna esta Null, no tiene caso seguir, salta a la siguiente iteración
                if(Objects.isNull(dato)){
                    continue;
                }
                LogsJB.debug("Dato que se ingresara al controlador: "+dato);

                List<Method> controladorMethods = new ArrayList<>(Arrays.asList(controlador.getClass().getMethods()));
                for(Method controladorMethod : controladorMethods){
                    String controllerName=controladorMethod.getName();
                    String claseMethod=controladorMethod.getDeclaringClass().getSimpleName();
                    LogsJB.debug("Nombre del metodo del controlador: "+controllerName+" Clase a la que pertenece: "+claseMethod);
                    //Si la clase donde se declaro el metodo pertenece a la clase Object
                    if(claseMethod.equalsIgnoreCase("Object")){
                        continue;
                    }

                    //Si el metodo no es un metodo get o set salta a la siguiente iteración
                    if(!(StringUtils.startsWithIgnoreCase(controllerName, "get"))
                            && !(StringUtils.startsWithIgnoreCase(controllerName, "set"))){
                        continue;
                    }

                    //Si el metodo es un get, que continue, no tiene caso hacer lo siguiente
                    if(StringUtils.startsWithIgnoreCase(controllerName, "get")){
                        continue;
                    }

                    //Valida que el metodo Set, si o sí, reciba un unico parametro
                    int parametros=controladorMethod.getParameterCount();
                    LogsJB.trace("Cantidad de parametros: "+parametros);
                    if((parametros<1)||(parametros>1)){
                        continue;
                    }

                    /*Si el nombre del metodo Get del modelo coincide con el nombre del metodo Set
                    Guardara la información en el controlador*/
                    modelGetName = StringUtils.removeStartIgnoreCase(modelGetName, "get");
                    controllerName = StringUtils.removeStartIgnoreCase(controllerName, "set");
                    LogsJB.debug("Nombre de la columna en el modelo: "+modelGetName+", controlador: "+controllerName);
                    if(StringUtils.equalsIgnoreCase(modelGetName, controllerName)){
                        controladorMethod.invoke(controlador, dato);
                        LogsJB.info("Lleno la columna "+controllerName+" Con la información del modelo: "+dato);
                        break;
                    }

                }

            }
        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada al llenar el modelo, con la info del controlador: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }

}

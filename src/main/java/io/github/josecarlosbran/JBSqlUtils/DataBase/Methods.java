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
package io.github.josecarlosbran.JBSqlUtils.DataBase;

import com.josebran.LogsJB.LogsJB;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author Jose Bran
 * Clase que proporciona metodos que permiten al modelo ejecutar sentencias DML, así como crear o eliminar la tabla
 * correspondiente al modelo
 */
class Methods extends Methods_Conexion {

    /**
     * Constructor por default de la clase Methods
     */
    protected Methods() {
        super();
    }

    /**
     * Constructor por default de la clase Methods
     *
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    protected Methods(Boolean getPropertySystem) {
        super(getPropertySystem);
    }

    /**
     * Almacena la información del modelo que hace el llamado en BD's.'
     *
     * @return La cantidad de filas insertadas o actualizadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public Integer save() throws Exception {
        return saveModel(this);
    }

    /**
     * Almacena la información de los modelos proporcionados en BD's
     *
     * @param modelos Lista de modelos que serán Insertados o Actualizados
     * @param <T>     Tipo de parámetro que hace que el método sea generico para poder ser
     *                llamado por diferentes tipos de objetos, siempre y cuando estos hereden la clase Methods Conexion.
     * @return La cantidad de filas insertadas o actualizadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> Integer saveALL(List<T> modelos) throws Exception {
        Integer result = 0;
        T temp = null;
        for (T modelo : modelos) {
            //Optimización de los tiempos de inserción de cada modelo.
            if (!Objects.isNull(temp)) {
                modelo.setTabla(temp.getTabla());
                modelo.setTableExist(temp.getTableExist());
                modelo.setTableName(temp.getTableName());
                //Con esto se maneja las tablas que existen en BD's
                modelo.getTabla().setColumnsExist(temp.getTabla().getColumnsExist());
                modelo.llenarPropertiesFromModel(temp);
                LogsJB.debug("Modelo Ya había sido inicializado: " + temp.getClass().getSimpleName());
            } else {
                modelo.llenarPropertiesFromModel(this);
                temp = this.obtenerInstanciaOfModel(modelo);
                LogsJB.warning("Modelo era Null, crea una nueva instancia: " + temp.getClass().getSimpleName());
                temp.refresh();
            }
            if (!modelo.getTableExist()) {
                LogsJB.debug("Obtendra la información de conexión de la BD's: " + modelo.getClass().getSimpleName());
                modelo.refresh();
                modelo.waitOperationComplete();
                LogsJB.debug("Ya obtuvo la información de BD's");
                temp.setTabla(modelo.getTabla());
                temp.setTableExist(modelo.getTableExist());
                temp.setTableName(modelo.getTableName());
                //Con esto se maneja las tablas que existen en BD's
                temp.getTabla().setColumnsExist(modelo.getTabla().getColumnsExist());
            }
            result = result + modelo.saveModel(modelo);
        }
        return result;
    }

    /**
     * Elimina la información del modelo que hace el llamado en BD´s
     *
     * @return La cantidad de filas eliminadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public Integer delete() throws Exception {
        return deleteModel(this);
    }

    /**
     * Elimina la información de los modelos proporcionados en BD's
     *
     * @param modelos Lista de modelos que serán Eliminados
     * @param <T>     Tipo de parámetro que hace que el método sea generico para poder ser
     *                llamado por diferentes tipos de objetos, siempre y cuando estos hereden la clase Methods Conexion.
     * @return La cantidad de filas eliminadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> Integer deleteALL(List<T> modelos) throws Exception {
        Integer result = 0;
        T temp = null;
        for (T modelo : modelos) {
            //Optimización de los tiempos de inserción de cada modelo.
            if (!Objects.isNull(temp)) {
                modelo.setTabla(temp.getTabla());
                modelo.setTableExist(temp.getTableExist());
                modelo.setTableName(temp.getTableName());
                LogsJB.debug("Modelo Ya había sido inicializado: " + temp.getClass().getSimpleName());
                modelo.llenarPropertiesFromModel(temp);
            } else {
                modelo.llenarPropertiesFromModel(this);
                temp = modelo.obtenerInstanciaOfModel(modelo);
                LogsJB.warning("Modelo era Null, crea una nueva instancia: " + temp.getClass().getSimpleName());
            }
            if (!modelo.getTableExist()) {
                LogsJB.debug("Obtendra la información de conexión de la BD's: " + modelo.getClass().getSimpleName());
                modelo.refresh();
                modelo.waitOperationComplete();
                LogsJB.debug("Ya obtuvo la información de BD's");
                temp.setTableExist(modelo.getTableExist());
                temp.setTableName(modelo.getTableName());
                temp.setTabla(modelo.getTabla());
            }
            result = result + modelo.deleteModel(modelo);
        }
        return result;
    }

    /**
     * Almacena la información del modelo que hace el llamado, esperando a que la operación termine de ser realizada
     *
     * @return Retorna True cuando se a terminado de insertar o actualizar la información del modelo en BD's
     */
    public Boolean saveBoolean() throws Exception {
        Integer resultado = saveModel(this);
        this.waitOperationComplete();
        return resultado >= 1;
    }

    /**
     * Proporciona un punto de entrada para obtener uno o mas modelos del tipo de modelo que invoca este procedimiento
     *
     * @param columna  Columna que sera evaluada
     * @param operador Operador por medio del cual se evaluara la columna
     * @param valor    Valor contra el cual se evaluara la columna
     * @return Punto de entrada a metodos que permiten seguir modificando la expresión de filtro u obtener el o los
     * modelos que hacen match con la consulta generada
     * @throws ValorUndefined Lanza esta excepción si alguno de los parametros proporcionados esta
     *                        Vacío o es Null
     */
    public Where where(String columna, Operator operador, Object valor) throws ValorUndefined {
        if (!this.getGetPropertySystem()) {
            Where where = new Where(columna, operador, valor, this, false);
            return where;
        }
        return new Where(columna, operador, valor, this);
    }

    /**
     * Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return Retorna una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> List<T> getAll() throws Exception {
        this.setTaskIsReady(false);
        List<T> lista = new ArrayList<T>();
        this.validarTableExist(this);
        Callable<ResultAsync<List<T>>> get = () -> {
            List<T> listatemp = new ArrayList<T>();
            try {
                if (this.getTableExist()) {
                    String sql = "SELECT * FROM " + this.getTableName();
                    sql = sql + ";";
                    LogsJB.info(sql);
                    Connection connect = this.getConnection();
                    PreparedStatement ejecutor = connect.prepareStatement(sql);
                    ResultSet registros = ejecutor.executeQuery();
                    while (registros.next()) {
                        listatemp.add(procesarResultSet((T) this, registros));
                    }
                    this.closeConnection(connect);
                    return new ResultAsync<>(listatemp, null);
                } else {
                    LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                            "recuperar el Registro: " + this.getTableName());
                    return new ResultAsync<>(listatemp, null);
                }
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que Recupera la lista de registros que cumplen con la sentencia" +
                        "SQL de la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                return new ResultAsync<>(listatemp, e);
            }
        };
        Future<ResultAsync<List<T>>> future = this.ejecutor.submit(get);
        while (!future.isDone()) {
        }
        ResultAsync<List<T>> resultado = future.get();
        this.setTaskIsReady(true);
        if (!Objects.isNull(resultado.getException())) {
            throw resultado.getException();
        }
        lista = resultado.getResult();
        return lista;
    }

    /**
     * Llena el modelo con la información del controlador
     *
     * @param controlador Controlador que debe poseer los atributos en java, que corresponden al modelo, con
     *                    sus respectivos metodos setter y getter
     * @param modelo      Modelo que será llenado con la información del controlador
     * @param <T>         Tipo de dato del controlador, acepta cualquier Object
     * @param <G>         Tipo de dato del modelo, acepta unicamente aquellos que heredan de la clase JBSqlUtils
     */
    public <T, G extends JBSqlUtils> void llenarModelo(T controlador, G modelo) {
        try {
            List<Method> controladorMethods = new ArrayList<>(Arrays.asList(controlador.getClass().getDeclaredMethods()));
            controladorMethods = controladorMethods.stream().filter(metodo -> {
                return metodo.getDeclaringClass().getPackage().hashCode() == controlador.getClass().getPackage().hashCode();
            }).collect(Collectors.toList());
            for (Method controladorMethod : controladorMethods) {
                String controllerName = controladorMethod.getName();
                String claseMethod = controladorMethod.getDeclaringClass().getSimpleName();
                LogsJB.debug("Nombre del metodo del controlador: " + controllerName + " Clase a la que pertenece: " + claseMethod);
                //Si la clase donde se declaro el metodo pertenece a la clase Object
                //Si el metodo No es un get, que continue, no tiene caso hacer lo siguiente
                int parametros = controladorMethod.getParameterCount();
                if ((parametros > 0) || (!StringUtils.startsWithIgnoreCase(controllerName, "get") || claseMethod.equalsIgnoreCase("Object"))) {
                    continue;
                }
                LogsJB.trace("Cantidad de parametros: " + parametros);
                LogsJB.trace("Validara si el contenido es Null: " + controllerName);
                Object contenido = (Object) controladorMethod.invoke(controlador, null);
                LogsJB.debug("Dato que se ingresara al modelo: " + contenido);
                //Obtiene los metodos get del modelo
                List<Method> modelGetMethods = new ArrayList<>(modelo.getMethodsGetOfModel());
                Iterator<Method> iteradorModelGetMethods = modelGetMethods.iterator();
                while (iteradorModelGetMethods.hasNext()) {
                    try {
                        Method modelGetMethod = iteradorModelGetMethods.next();
                        String modelGetName = modelGetMethod.getName();
                    /*Si el nombre del metodo get, no coincide con el nombre del metodo del
                    controlador, continua
                    */
                        LogsJB.debug("Nombre del metodo Get del modelo: " + modelGetName);
                        if (!StringUtils.equalsIgnoreCase(controllerName, modelGetName)) {
                            iteradorModelGetMethods.remove();
                            continue;
                        }
                        LogsJB.debug("Obtiene la columna: " + modelGetName);
                        //Obtengo la información de la columna
                        Column columnsSQL = (Column) modelGetMethod.invoke(modelo, null);
                        //Le meto la información a la columa
                        LogsJB.debug("Setea el contenido a la columna: " + modelGetName);
                        columnsSQL.setValor(contenido);
                        String columnName = modelGetMethod.getName();
                        columnName = StringUtils.removeStartIgnoreCase(columnName, "get");
                        LogsJB.debug("Nombre de la columna a validar: " + columnName);
                        Boolean isready = false;
                        //Obtiene los metodos set del modelo
                        List<Method> modelSetMethods = new ArrayList<>(modelo.getMethodsSetOfModel());
                        Iterator<Method> iteradorModelSetMethods = modelSetMethods.iterator();
                        while (iteradorModelSetMethods.hasNext()) {
                            try {
                                Method modelSetMethod = iteradorModelSetMethods.next();
                                String modelSetName = modelSetMethod.getName();
                                LogsJB.trace("Nombre del metodo: " + modelSetName);
                                //Si el metodo es un get, que continue, no tiene caso hacer lo siguiente
                                LogsJB.trace("Si es un metodo Set: " + modelSetName);
                                modelSetName = StringUtils.removeStartIgnoreCase(modelSetName, "set");
                                LogsJB.trace("Nombre del metodo set a validar: " + modelSetName);
                                if (StringUtils.equalsIgnoreCase(modelSetName, columnName)) {
                                    //Setea el valor del metodo
                                    modelSetMethod.invoke(modelo, columnsSQL);
                                    LogsJB.debug("Ingreso la columna en el metodo set: " + modelSetName);
                                    isready = true;
                                    iteradorModelSetMethods.remove();
                                    break;
                                }
                            } catch (Exception e) {
                                LogsJB.fatal("Excepción disparada al llenar el modelo, con la info del controlador, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                            }
                        }
                        if (isready) {
                            iteradorModelGetMethods.remove();
                            break;
                        }
                    } catch (Exception e) {
                        LogsJB.fatal("Excepción disparada al llenar el modelo, con la info del controlador, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                    }
                }
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al llenar el modelo, con la info del controlador, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Llena el controlador proporcionado con la información del modelo
     *
     * @param controlador Controlador que debe poseer los atributos en java, que corresponden al modelo, con
     *                    sus respectivos metodos setter y getter
     * @param modelo      Modelo del cual se extraera la información para llenar el controlador
     * @param <T>         Tipo de dato del controlador, acepta cualquier Object
     * @param <G>         Tipo de dato del modelo, acepta unicamente aquellos que heredan de la clase JBSqlUtils
     */
    public <T, G extends JBSqlUtils> void llenarControlador(T controlador, G modelo) {
        try {
            //Obtiene los metodos get del modelo
            List<Method> modelGetMethods = modelo.getMethodsGetOfModel();
            LogsJB.debug("Obtuvo los metodos Get del modelo: ");
            List<Method> controladorMethods = new ArrayList<>(Arrays.asList(controlador.getClass().getDeclaredMethods()));
            controladorMethods = controladorMethods.stream().filter(metodo -> {
                return metodo.getDeclaringClass().getPackage().hashCode() == controlador.getClass().getPackage().hashCode();
            }).collect(Collectors.toList());
            for (Method modelGetMethod : modelGetMethods) {
                String modelGetName = modelGetMethod.getName();
                LogsJB.debug("Nombre del metodo Get del modelo: " + modelGetName);
                //Obtengo la información de la columna
                Column columnsSQL = (Column) modelGetMethod.invoke(modelo, null);
                Object dato = columnsSQL.getValor();
                LogsJB.debug("Dato que se ingresara al controlador: " + dato);
                Iterator<Method> iteradorController = controladorMethods.iterator();
                while (iteradorController.hasNext()) {
                    try {
                        Method controladorMethod = iteradorController.next();
                        String controllerName = controladorMethod.getName();
                        String claseMethod = controladorMethod.getDeclaringClass().getSimpleName();
                        LogsJB.debug("Nombre del metodo del controlador: " + controllerName + " Clase a la que pertenece: " + claseMethod);
                        //Si la clase donde se declaro el metodo pertenece a la clase Object
                        //Si el metodo No es un set, que continue, no tiene caso hacer lo siguiente
                        //Valida que el metodo Set, si o sí, reciba un unico parametro
                        int parametros = controladorMethod.getParameterCount();
                        if ((parametros > 1 || parametros < 1) || (!StringUtils.startsWithIgnoreCase(controllerName, "set")) || (claseMethod.equalsIgnoreCase("Object"))) {
                            continue;
                        }
                        LogsJB.trace("Cantidad de parametros: " + parametros);
                    /*Si el nombre del metodo Get del modelo coincide con el nombre del metodo Set
                    Guardara la información en el controlador*/
                        modelGetName = StringUtils.removeStartIgnoreCase(modelGetName, "get");
                        controllerName = StringUtils.removeStartIgnoreCase(controllerName, "set");
                        LogsJB.debug("Nombre de la columna en el modelo: " + modelGetName + ", controlador: " + controllerName);
                        if (StringUtils.equalsIgnoreCase(modelGetName, controllerName)) {
                            controladorMethod.invoke(controlador, dato);
                            LogsJB.debug("Lleno la columna " + controllerName + " Con la información del modelo: " + dato);
                            iteradorController.remove();
                            break;
                        }
                    } catch (Exception e) {
                        LogsJB.fatal("Excepción disparada al llenar el controlador, con la info del modelo, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                    }
                }
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al llenar el controlador, con la info del modelo, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Setea null en el campo valor de cada columna que posee el modelo.
     *
     * @param <T> Tipo de dato del modelo, acepta unicamente aquellos que heredan de la clase JBSqlUtils
     */
    public <T extends JBSqlUtils> void cleanModel() {
        try {
            this.setModelExist(false);
            //Obtiene los metodos get del modelo
            List<Field> campos=this.getFieldsOfModel();
            for(Field campo: campos){
                if (campo.getType().isAssignableFrom(String.class)) {
                    //Caracteres y cadenas de Texto
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Double.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Integer.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Float.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Boolean.class)) {
                    FieldUtils.writeField(this, campo.getName(), false, true);
                } else if (campo.getType().isAssignableFrom(byte[].class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Date.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Time.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Timestamp.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else {
                    LogsJB.warning("No se pudo setear el valor de la columna: " + campo.getName() + " " + this.getTableName());
                    LogsJB.warning("Debido a que ninguno de los métodos corresponde al tipo de dato SQL: " + campo.getType());
                    FieldUtils.writeField(this, campo.getName(), null, true);
                }
            }

        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al limpiar el modelo, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }
}

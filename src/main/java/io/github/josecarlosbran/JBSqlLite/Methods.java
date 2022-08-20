package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Search.Where;
import io.github.josecarlosbran.LogsJB.LogsJB;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Methods extends Methods_Conexion {
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
                        String sql = "CREATE TABLE " + this.getClass().getSimpleName() + "(";
                        List<Method> metodos = new ArrayList<>();
                        metodos = this.getMethodsGetOfModel(this.getMethodsModel());
                        int datos = 0;
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(this, null);
                            String columnName = metodo.getName();
                            columnName = StringUtils.remove(columnName, "get");
                            DataType columnType = columnsSQL.getDataTypeSQL();
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
                                    } else {
                                        restricciones = restricciones + restriccion.getRestriccion() + " ";
                                    }
                                }
                            }

                            //Si el modelo tiene seteado que no se manejaran las timestamps entonces
                            //Ignora el guardar esas columnas
                            if((!this.getTimestamps())&&((StringUtils.equalsIgnoreCase(columnName, "created_at"))
                                    ||(StringUtils.equalsIgnoreCase(columnName, "updated_at")))){
                                continue;
                            }

                            String columna = columnName + " " + tipo_de_columna + " " + restricciones;

                            datos++;
                            if(datos>1){
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
                            LogsJB.info("Tabla " + this.getClass().getSimpleName() + " Creada exitosamente");
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
                            sql = "DROP TABLE IF EXISTS " + this.getClass().getSimpleName();
                            //+ " RESTRICT";
                        } else if (this.getDataBaseType() == DataBase.SQLServer) {
                            sql = "if exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '" +
                                    this.getClass().getSimpleName() +
                                    "' AND TABLE_SCHEMA = 'dbo')\n" +
                                    "    drop table dbo." +
                                    this.getClass().getSimpleName();
                            //+" RESTRICT;";
                        }
                        LogsJB.info(sql);

                        Statement ejecutor = connect.createStatement();

                        if (!ejecutor.execute(sql)) {
                            LogsJB.info("Sentencia para eliminar tabla de la BD's ejecutada exitosamente");
                            LogsJB.info("Tabla " + this.getClass().getSimpleName() + " Eliminada exitosamente");
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


    public <T extends Methods_Conexion> void saveALL(List<T> modelos){
        try{
            T temp=null;
            for(T modelo: modelos){
                //Optimización de los tiempos de inserción de cada modelo.
                if(!Objects.isNull(temp)){
                    modelo.setTabla(temp.getTabla());
                    modelo.setTableExist(temp.getTableExist());
                    modelo.setTableName(temp.getTableName());
                    LogsJB.info("Modelo Ya había sido inicializado");
                }else{
                    temp=(T) modelo.getClass().newInstance();
                    LogsJB.warning("Modelo era Null, crea una nueva instancia");
                }
                if (!modelo.getTableExist()) {
                    LogsJB.info("Obtendra la información de conexión de la BD's");
                    modelo.refresh();
                    while(modelo.getTabla().getColumnas().size()==0){

                    }
                    LogsJB.info("Ya obtuvo la información de BD's'");
                    temp.setTableExist(modelo.getTableExist());
                    temp.setTableName(modelo.getTableName());
                    temp.setTabla(modelo.getTabla());
                }
                modelo.saveModel(modelo);
            }
        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda la lista de modelos en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }


    public Boolean saveBoolean(){
        Boolean result = false;
        try{
            saveModel(this);
            while(!this.getTaskIsReady()){
            }
            result = this.getTaskIsReady();
        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }



    public Where where(String columna, Operator operador, String valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Where(columna, operador, valor, this);
    }



}

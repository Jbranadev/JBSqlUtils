package io.github.josecarlosbran.JBSqlLite.DataBase;


import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;
import io.github.josecarlosbran.LogsJB.LogsJB;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Get extends Methods_Conexion {


    protected Get() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }


    protected  <T extends Methods_Conexion> void get(T modelo, String Sql){
        try {
            modelo.setTaskIsReady(false);
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }
            Connection connect = modelo.getConnection();
            Runnable get = () -> {
                try {
                    if (modelo.getTableExist()) {
                        String sql="SELECT * FROM " + modelo.getTableName();
                        sql = sql+Sql + ";";
                        LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        ResultSet registros = ejecutor.executeQuery();
                        if (registros.next()) {
                            procesarResultSetOneResult(modelo, registros);

                        }
                        modelo.closeConnection(connect);
                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "recuperar el Registro");
                    }
                    modelo.setTaskIsReady(true);
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    modelo.setTaskIsReady(true);
                }
            };
            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            ejecutor.submit(get);
            ejecutor.shutdown();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que obtiene el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    protected <T extends Methods_Conexion> T first(T modelo, String Sql){
        try {
            modelo.setTaskIsReady(false);
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }
            Connection connect = modelo.getConnection();
            Runnable get = () -> {
                try {
                    if (modelo.getTableExist()) {
                        String sql="SELECT * FROM " + modelo.getTableName();
                        sql = sql+Sql + ";";
                        LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        ResultSet registros = ejecutor.executeQuery();
                        if (registros.next()) {
                            procesarResultSetOneResult(modelo, registros);

                        }
                        modelo.closeConnection(connect);
                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "recuperar el Registro");
                    }
                    modelo.setTaskIsReady(true);
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    modelo.setTaskIsReady(true);
                }
            };
            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            ejecutor.submit(get);
            ejecutor.shutdown();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que obtiene el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return modelo;
    }

    protected <T extends Methods_Conexion> T firstOrFail(T modelo, String Sql) throws ModelNotFound {
            modelo.setTaskIsReady(false);
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }

            Connection connect = modelo.getConnection();
            Callable<Boolean> get = () -> {
                Boolean respuesta=false;
                try {
                    if (modelo.getTableExist()) {
                        String sql="SELECT * FROM " + modelo.getTableName();
                        sql = sql+Sql + ";";
                        LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        ResultSet registros = ejecutor.executeQuery();
                        if (registros.next()) {
                            procesarResultSetOneResult(modelo, registros);
                            respuesta=true;
                        }
                        modelo.closeConnection(connect);
                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "recuperar el Registro");
                    }
                    modelo.setTaskIsReady(true);
                } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    modelo.setTaskIsReady(true);
                }
                return respuesta;
            };

            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            Future<Boolean> future = ejecutor.submit(get);
            while (!future.isDone()) {

            }
            ejecutor.shutdown();
            Boolean result=false;
            try{
                result = future.get();
            }catch (Exception e){
                LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's: " + e.toString());
                LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            }
            if(!result){
                String sql="SELECT * FROM " + modelo.getTableName();
                throw new ModelNotFound("No existe un modelo en BD's que corresponda a los criterios de la consulta sql: "+sql+Sql);
            }
        return modelo;
    }

    protected <T extends Methods_Conexion> List<T> getAll(T modelo, String Sql) throws InstantiationException, IllegalAccessException {
        modelo.setTaskIsReady(false);
        List<T> lista = new ArrayList<>();
        try {
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }
            Connection connect = modelo.getConnection();
            //T finalTemp = temp;
            Runnable get = () -> {
                try {
                    if (modelo.getTableExist()) {
                        String sql="SELECT * FROM " + modelo.getTableName();
                        sql = sql+Sql + ";";

                        //Si es sql server y trae la palabra limit verificara y modificara la sentencia
                        if(modelo.getDataBaseType()== DataBase.SQLServer){
                            if(StringUtils.containsIgnoreCase(sql, "LIMIT")){
                                String temporal;
                                LogsJB.info("Sentencia SQL a modificar: "+sql);
                                int indice_limite= StringUtils.lastIndexOfIgnoreCase(sql, "LIMIT");
                                LogsJB.debug("Indice limite: "+indice_limite);
                                LogsJB.debug("Longitud de la sentencia: "+sql.length());
                                String temporal_limite=StringUtils.substring(sql, indice_limite);
                                sql=sql.replace(temporal_limite, ";");
                                LogsJB.debug("Sentencia SQL despues de eliminar el limite: "+sql);
                                LogsJB.trace("Temporal Limite: "+temporal_limite);
                                temporal_limite=StringUtils.remove(temporal_limite, "LIMIT");
                                LogsJB.trace("Temporal Limite: "+temporal_limite);
                                temporal_limite=StringUtils.remove(temporal_limite, ";");
                                LogsJB.trace("Temporal Limite: "+temporal_limite);
                                temporal=sql;
                                LogsJB.trace("Temporal SQL: "+temporal);
                                String select="SELECT TOP "+temporal_limite+" * FROM ";
                                sql=temporal.replace("SELECT * FROM ", select);
                                LogsJB.info("Se modifico la sentencia SQL para que unicamente obtenga la cantidad de " +
                                        "registros especificados por el usuario: "+sql);
                            }
                        }
                        LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        ResultSet registros = ejecutor.executeQuery();

                        while(registros.next()) {
                            lista.add(procesarResultSet(modelo, registros));
                            //procesarResultSet(modelo, registros);
                        }
                        modelo.closeConnection(connect);
                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "recuperar el Registro");
                    }
                    modelo.setTaskIsReady(true);
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Recupera la lista de registros que cumplen con la sentencia" +
                            "SQL de la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    modelo.setTaskIsReady(true);
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



}

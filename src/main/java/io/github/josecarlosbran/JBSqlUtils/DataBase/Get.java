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
import io.github.josecarlosbran.JBSqlUtils.Column;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Methods_Conexion;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

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

/**
 * @author Jose Bran
 * Clase que proporciona los metodos que obtienen los registros de BD's
 */
public class Get extends Methods_Conexion {

    /**
     * Constructor por default de la clase Get, que inicializa la clase
     *
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected Get() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }


    /**
     * Llena el modelo que invoca este metodo con la información que obtiene de BD's
     *
     * @param modelo     Modelo que será llenado
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     */
    protected <T extends JBSqlUtils> void get(T modelo, String Sql, List<Column> parametros) {
        try {
            modelo.setTaskIsReady(false);
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }
            Connection connect = modelo.getConnection();
            Runnable get = () -> {
                try {
                    if (modelo.getTableExist()) {
                        String sql = "SELECT * FROM " + modelo.getTableName();
                        sql = sql + Sql + ";";
                        //LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        for (int i = 0; i < parametros.size(); i++) {
                            //Obtengo la información de la columna
                            Column columnsSQL = parametros.get(i);
                            convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                        }

                        LogsJB.info(ejecutor.toString());

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

    /**
     * Obtiene un modelo del tipo que invoca este metodo con la información que obtiene de BD's
     *
     * @param modelo     Modelo que esta invocando el metodo
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna un un modelo del tipo que invoca este metodo con la información que obtiene de BD's.
     */
    protected <T extends JBSqlUtils> T first(T modelo, String Sql, List<Column> parametros) {
        try {
            modelo.setTaskIsReady(false);
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }
            Connection connect = modelo.getConnection();
            Runnable get = () -> {
                try {
                    if (modelo.getTableExist()) {
                        String sql = "SELECT * FROM " + modelo.getTableName();
                        sql = sql + Sql + ";";
                        //LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        for (int i = 0; i < parametros.size(); i++) {
                            //Obtengo la información de la columna
                            Column columnsSQL = parametros.get(i);
                            convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                        }
                        LogsJB.info(ejecutor.toString());
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

    /**
     * Obtiene un modelo del tipo que invoca este metodo con la información que obtiene de BD's
     *
     * @param modelo     Modelo que esta invocando el metodo
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna un un modelo del tipo que invoca este metodo con la información que obtiene de BD's.
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    protected <T extends JBSqlUtils> T firstOrFail(T modelo, String Sql, List<Column> parametros) throws ModelNotFound {
        modelo.setTaskIsReady(false);
        if (!modelo.getTableExist()) {
            modelo.refresh();
        }

        Connection connect = modelo.getConnection();
        Callable<Boolean> get = () -> {
            Boolean respuesta = false;
            try {
                if (modelo.getTableExist()) {
                    String sql = "SELECT * FROM " + modelo.getTableName();
                    sql = sql + Sql + ";";
                    //LogsJB.info(sql);
                    PreparedStatement ejecutor = connect.prepareStatement(sql);

                    for (int i = 0; i < parametros.size(); i++) {
                        //Obtengo la información de la columna
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    ResultSet registros = ejecutor.executeQuery();
                    if (registros.next()) {
                        procesarResultSetOneResult(modelo, registros);
                        respuesta = true;
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
        Boolean result = false;
        try {
            result = future.get();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        if (!result) {
            String sql = "SELECT * FROM " + modelo.getTableName();
            throw new ModelNotFound("No existe un modelo en BD's que corresponda a los criterios de la consulta sql: " + sql + Sql);
        }
        return modelo;
    }

    /**
     * Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     *
     * @param modelo     Modelo que esta invocando el metodo
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @throws InstantiationException Lanza esta excepción si ocurre un error al crear una nueva instancia
     *                                del tipo de modelo proporcionado
     * @throws IllegalAccessException Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     */
    protected <T extends JBSqlUtils> List<T> getAll(T modelo, String Sql, List<Column> parametros) throws InstantiationException, IllegalAccessException {
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
                        String sql = "SELECT * FROM " + modelo.getTableName();
                        sql = sql + Sql + ";";

                        //Si es sql server y trae la palabra limit verificara y modificara la sentencia
                        if (modelo.getDataBaseType() == DataBase.SQLServer) {
                            if (StringUtils.containsIgnoreCase(sql, "LIMIT")) {
                                String temporal;
                                LogsJB.info("Sentencia SQL a modificar: " + sql);
                                int indice_limite = StringUtils.lastIndexOfIgnoreCase(sql, "LIMIT");
                                LogsJB.debug("Indice limite: " + indice_limite);
                                LogsJB.debug("Longitud de la sentencia: " + sql.length());
                                String temporal_limite = StringUtils.substring(sql, indice_limite);
                                sql = sql.replace(temporal_limite, ";");
                                LogsJB.debug("Sentencia SQL despues de eliminar el limite: " + sql);
                                LogsJB.trace("Temporal Limite: " + temporal_limite);
                                temporal_limite = StringUtils.remove(temporal_limite, "LIMIT");
                                LogsJB.trace("Temporal Limite: " + temporal_limite);
                                temporal_limite = StringUtils.remove(temporal_limite, ";");
                                LogsJB.trace("Temporal Limite: " + temporal_limite);
                                temporal = sql;
                                LogsJB.trace("Temporal SQL: " + temporal);
                                String select = "SELECT TOP " + temporal_limite + " * FROM ";
                                sql = temporal.replace("SELECT * FROM ", select);
                                LogsJB.info("Se modifico la sentencia SQL para que unicamente obtenga la cantidad de " +
                                        "registros especificados por el usuario: " + sql);
                            }
                        }
                        //LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);

                        for (int i = 0; i < parametros.size(); i++) {
                            //Obtengo la información de la columna
                            Column columnsSQL = parametros.get(i);
                            convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                        }
                        LogsJB.info(ejecutor.toString());

                        ResultSet registros = ejecutor.executeQuery();

                        while (registros.next()) {
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


    /**
     * Obtiene una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     * @param Sql Sentencia SQL
     * @param parametros Lista de parametros de la sentencia SQL
     * @param columnas Lista con los nombres de las columnas que se desea recuperar, si se desea obtener
     *                 todas las columnas de la tabla especificada envíar NULL como parametro
     * @return Retorna una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     *      Envíada como parametro
     */
    protected List<JSONObject> get(String Sql, List<Column> parametros, List<String> columnas) {
        List<JSONObject> lista = new ArrayList<>();
        String tableName=Sql.replace("SELECT * FROM ", "").split(" ")[0];
        this.setTaskIsReady(false);
        this.setTableName(tableName);
        try {
            if (!this.getTableExist()) {
                this.refresh();
            }
            Connection connect = this.getConnection();
            //T finalTemp = temp;
            Runnable get = () -> {
                try {
                    if (this.getTableExist()) {
                        String sql =Sql+ ";";
                        //Si es sql server y trae la palabra limit verificara y modificara la sentencia
                        if (this.getDataBaseType() == DataBase.SQLServer) {
                            if (StringUtils.containsIgnoreCase(sql, "LIMIT")) {
                                String temporal;
                                LogsJB.info("Sentencia SQL a modificar: " + sql);
                                int indice_limite = StringUtils.lastIndexOfIgnoreCase(sql, "LIMIT");
                                LogsJB.debug("Indice limite: " + indice_limite);
                                LogsJB.debug("Longitud de la sentencia: " + sql.length());
                                String temporal_limite = StringUtils.substring(sql, indice_limite);
                                sql = sql.replace(temporal_limite, ";");
                                LogsJB.debug("Sentencia SQL despues de eliminar el limite: " + sql);
                                LogsJB.trace("Temporal Limite: " + temporal_limite);
                                temporal_limite = StringUtils.remove(temporal_limite, "LIMIT");
                                LogsJB.trace("Temporal Limite: " + temporal_limite);
                                temporal_limite = StringUtils.remove(temporal_limite, ";");
                                LogsJB.trace("Temporal Limite: " + temporal_limite);
                                temporal = sql;
                                LogsJB.trace("Temporal SQL: " + temporal);
                                String select = "SELECT TOP " + temporal_limite + " * FROM ";
                                sql = temporal.replace("SELECT * FROM ", select);
                                LogsJB.info("Se modifico la sentencia SQL para que unicamente obtenga la cantidad de " +
                                        "registros especificados por el usuario: " + sql);
                            }
                        }
                        //LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);

                        for (int i = 0; i < parametros.size(); i++) {
                            //Obtengo la información de la columna
                            Column columnsSQL = parametros.get(i);
                            convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                        }
                        LogsJB.info(ejecutor.toString());
                        ResultSet registros = ejecutor.executeQuery();
                        while (registros.next()) {
                            lista.add(this.procesarResultSetJSON(columnas, registros));
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


}

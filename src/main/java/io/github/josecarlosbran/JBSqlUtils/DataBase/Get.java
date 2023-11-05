/**
 * Copyright (C) 2022 El proyecto de código abierto JBSqlUtils de José Bran
 * <p>
 * Con licencia de Apache License, Versión 2.0 (la "Licencia");
 * no puede usar este archivo excepto de conformidad con la Licencia.
 * Puede obtener una copia de la Licencia en
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * A menos que lo exija la ley aplicable o se acuerde por escrito, el software
 * distribuido bajo la Licencia se distribuye "TAL CUAL",
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
 * Consulte la Licencia para conocer el idioma específico que rige los permisos y
 * limitaciones bajo la Licencia.
 */
package io.github.josecarlosbran.JBSqlUtils.DataBase;

import com.josebran.LogsJB.LogsJB;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Jose Bran
 * Clase que proporciona los metodos que obtienen los registros de BD's
 */
class Get extends Methods_Conexion {

    /**
     * Constructor por default de la clase Get, que inicializa la clase
     */
    protected Get() {
        super();
    }

    /**
     * Constructor por default de la clase Get, que inicializa la clase
     *
     * @param getGetPropertiesSystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    protected Get(Boolean getGetPropertiesSystem) {
        super(getGetPropertiesSystem);
    }

    /**
     * Llena el modelo que invoca este método con la información que obtiene de BD's
     *
     * @param modelo     Modelo que será llenado
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    protected <T extends JBSqlUtils> void get(T modelo, String Sql, List<Column> parametros) throws Exception {
        if (!this.getGetPropertySystem()) {
            modelo.setGetPropertySystem(false);
            modelo.llenarPropertiesFromModel(modelo);
        }
        modelo.setTaskIsReady(false);
        modelo.validarTableExist(modelo);
        Callable<ResultAsync<Boolean>> get = () -> {
            try {
                if (modelo.getTableExist()) {
                    String sql = "SELECT * FROM " + modelo.getTableName();
                    sql = sql + Sql + ";";
                    LogsJB.info(sql);
                    Connection connect = modelo.getConnection();
                    PreparedStatement ejecutor = connect.prepareStatement(sql);
                    for (int i = 0; i < parametros.size(); i++) {
                        //Obtengo la información de la columna
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    ResultSet registros = ejecutor.executeQuery();
                    while (registros.next()) {
                        procesarResultSetOneResult(modelo, registros);
                    }
                    modelo.closeConnection(connect);
                } else {
                    LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                            "recuperar el Registro");
                }
                modelo.setTaskIsReady(true);
                return new ResultAsync<>(true, null);
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                modelo.setTaskIsReady(true);
                return new ResultAsync<>(true, e);
            }
        };
        Future<ResultAsync<Boolean>> future = ejecutor.submit(get);
        while (!future.isDone()) {
        }
        ResultAsync<Boolean> resultado = future.get();
        if (!Objects.isNull(resultado.getException())) {
            throw resultado.getException();
        }
    }

    /**
     * Obtiene un modelo del tipo que invoca este método con la información que obtiene de BD's
     *
     * @param modelo     Modelo que está invocando el método
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return Retorna un un modelo del tipo que invoca este método con la información que obtiene de BD's.
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    protected <T extends JBSqlUtils> T first(T modelo, String Sql, List<Column> parametros) throws Exception {
        T modeloResult;
        if (!this.getGetPropertySystem()) {
            modelo.setGetPropertySystem(false);
            modelo.llenarPropertiesFromModel(modelo);
        }
        modeloResult = modelo.obtenerInstanciaOfModel(modelo);
        modelo.setTaskIsReady(false);
        modelo.validarTableExist(modelo);
        Callable<ResultAsync<T>> get = () -> {
            T modeloTemp = modelo.obtenerInstanciaOfModel(modelo);
            try {
                if (modelo.getTableExist()) {
                    String sql = "SELECT * FROM " + modelo.getTableName();
                    sql = sql + Sql + ";";
                    //LogsJB.info(sql);
                    Connection connect = modelo.getConnection();
                    PreparedStatement ejecutor = connect.prepareStatement(sql);
                    for (int i = 0; i < parametros.size(); i++) {
                        //Obtengo la información de la columna
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    ResultSet registros = ejecutor.executeQuery();
                    if (registros.next()) {
                        modeloTemp = procesarResultSet(modelo, registros);
                    }
                    modelo.closeConnection(connect);
                } else {
                    LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                            "recuperar el Registro");
                }
                modelo.setTaskIsReady(true);
                return new ResultAsync<>(modeloTemp, null);
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                modelo.setTaskIsReady(true);
                return new ResultAsync<>(modeloTemp, e);
            }
        };
        Future<ResultAsync<T>> future = ejecutor.submit(get);
        while (!future.isDone()) {
        }
        ResultAsync<T> resultado = future.get();
        if (!Objects.isNull(resultado.getException())) {
            throw resultado.getException();
        }
        modeloResult = resultado.getResult();
        return modeloResult;
    }

    /**
     * Llena el modelo que invoca este método con la información que obtiene de BD's
     *
     * @param modelo     Modelo que está invocando el metodo
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    protected <T extends JBSqlUtils> void firstOrFailGet(T modelo, String Sql, List<Column> parametros) throws Exception {
        if (!this.getGetPropertySystem()) {
            modelo.setGetPropertySystem(false);
            modelo.llenarPropertiesFromModel(modelo);
        }
        modelo.setTaskIsReady(false);
        modelo.validarTableExist(modelo);
        Callable<ResultAsync<Boolean>> get = () -> {
            try {
                Boolean result=false;
                if (modelo.getTableExist()) {
                    String sql = "SELECT * FROM " + modelo.getTableName();
                    sql = sql + Sql + ";";
                    //LogsJB.info(sql);
                    Connection connect = modelo.getConnection();
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
                        result=true;
                    }
                    modelo.closeConnection(connect);
                    modelo.setTaskIsReady(true);
                    return new ResultAsync<>(result, null);
                } else {
                    LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                            "recuperar el Registro");
                    modelo.setTaskIsReady(true);
                    return new ResultAsync<>(false, null);
                }
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                modelo.setTaskIsReady(true);
                return new ResultAsync<>(false, e);
            }
        };
        Future<ResultAsync<Boolean>> future = ejecutor.submit(get);
        while (!future.isDone()) {
        }
        ResultAsync<Boolean> resultado = future.get();
        if (!Objects.isNull(resultado.getException())) {
            throw resultado.getException();
        }
        Boolean result = resultado.getResult();
        if (!result) {
            String sql = "SELECT * FROM " + modelo.getTableName();
            throw new ModelNotFound("No existe un modelo en BD's que corresponda a los criterios de la consulta sql: " + sql + Sql);
        }
    }


    /**
     * Obtiene un modelo del tipo que invoca este metodo con la información que obtiene de BD's
     *
     * @param modelo     Modelo que está invocando el metodo
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna un un modelo del tipo que invoca este metodo con la información que obtiene de BD's.
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    protected <T extends JBSqlUtils> T firstOrFail(T modelo, String Sql, List<Column> parametros) throws Exception {
        T modeloResult;
        if (!this.getGetPropertySystem()) {
            modelo.setGetPropertySystem(false);
            modelo.llenarPropertiesFromModel(modelo);
        }
        modelo.setTaskIsReady(false);
        modelo.validarTableExist(modelo);
        modeloResult = modelo.obtenerInstanciaOfModel(modelo);
        Callable<ResultAsync<T>> get = () -> {
            T modeloTemp = modelo.obtenerInstanciaOfModel(modelo);
            try {
                if (modelo.getTableExist()) {
                    String sql = "SELECT * FROM " + modelo.getTableName();
                    sql = sql + Sql + ";";
                    //LogsJB.info(sql);
                    Connection connect = modelo.getConnection();
                    PreparedStatement ejecutor = connect.prepareStatement(sql);
                    for (int i = 0; i < parametros.size(); i++) {
                        //Obtengo la información de la columna
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    ResultSet registros = ejecutor.executeQuery();
                    if (registros.next()) {
                        modeloTemp = procesarResultSet(modelo, registros);
                    }
                    modelo.closeConnection(connect);
                    modelo.setTaskIsReady(true);
                    return new ResultAsync<>(modeloTemp, null);
                } else {
                    LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                            "recuperar el Registro");
                    modelo.setTaskIsReady(true);
                    return new ResultAsync<>(modeloTemp, null);
                }
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                modelo.setTaskIsReady(true);
                return new ResultAsync<>(modeloTemp, e);
            }
        };
        Future<ResultAsync<T>> future = ejecutor.submit(get);
        while (!future.isDone()) {
        }
        ResultAsync<T> resultado = future.get();
        if (!Objects.isNull(resultado.getException())) {
            throw resultado.getException();
        }
        modeloResult = resultado.getResult();
        Boolean result;
        result = modeloResult.getModelExist();
        if (!result) {
            String sql = "SELECT * FROM " + modelo.getTableName();
            throw new ModelNotFound("No existe un modelo en BD's que corresponda a los criterios de la consulta sql: " + sql + Sql);
        }
        return modeloResult;
    }

    /**
     * Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     *
     * @param modelo     Modelo que está invocando el metodo
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    protected <T extends JBSqlUtils> List<T> getAll(T modelo, String Sql, List<Column> parametros) throws Exception {
        if (!this.getGetPropertySystem()) {
            modelo.setGetPropertySystem(false);
            modelo.llenarPropertiesFromModel(modelo);
        }
        modelo.setTaskIsReady(false);
        List<T> lista;
        modelo.validarTableExist(modelo);
        Callable<ResultAsync<List<T>>> get = () -> {
            List<T> listaTemp = new ArrayList<>();
            try {
                if (modelo.getTableExist()) {
                    String sql = "SELECT * FROM " + modelo.getTableName();
                    sql = sql + Sql + ";";
                    //Si es sql server y trae la palabra limit verificara y modificara la sentencia
                    if (modelo.getDataBaseType() == DataBase.SQLServer) {
                        if (StringUtils.containsIgnoreCase(sql, "LIMIT")) {
                            String temporal;
                            LogsJB.debug("Sentencia SQL a modificar: " + sql);
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
                            LogsJB.debug("Se modifico la sentencia SQL para que unicamente obtenga la cantidad de " +
                                    "registros especificados por el usuario: " + sql);
                        }
                    }
                    //LogsJB.info(sql);
                    Connection connect = modelo.getConnection();
                    PreparedStatement ejecutor = connect.prepareStatement(sql);
                    for (int i = 0; i < parametros.size(); i++) {
                        //Obtengo la información de la columna
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    ResultSet registros = ejecutor.executeQuery();
                    while (registros.next()) {
                        listaTemp.add(procesarResultSet(modelo, registros));
                    }
                    modelo.closeConnection(connect);
                } else {
                    LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                            "recuperar el Registro");
                }
                modelo.setTaskIsReady(true);
                return new ResultAsync(listaTemp, null);
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que Recupera la lista de registros que cumplen con la sentencia" +
                        "SQL de la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                modelo.setTaskIsReady(true);
                return new ResultAsync(listaTemp, e);
            }
        };
        Future<ResultAsync<List<T>>> future = ejecutor.submit(get);
        while (!future.isDone()) {
        }
        ResultAsync<List<T>> resultado = future.get();
        if (!Objects.isNull(resultado.getException())) {
            throw resultado.getException();
        }
        lista = resultado.getResult();
        return lista;
    }

    /**
     * Obtiene una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     *
     * @param Sql        Sentencia SQL
     * @param parametros Lista de parametros de la sentencia SQL
     * @param columnas   Lista con los nombres de las columnas que se desea recuperar, si se desea obtener
     *                   todas las columnas de la tabla especificada envíar NULL como parametro
     * @return Retorna una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    protected List<JSONObject> get(String Sql, List<Column> parametros, List<String> columnas) throws Exception {
        List<JSONObject> lista;
        String tableName = Sql.replace("SELECT * FROM ", "").split(" ")[0];
        this.setTaskIsReady(false);
        this.setTableName(tableName);
        this.validarTableExist(this);
        Callable<ResultAsync<List<JSONObject>>> get = () -> {
            List<JSONObject> temp = new ArrayList<>();
            try {
                if (this.getTableExist()) {
                    String sql = Sql + ";";
                    //Si es sql server y trae la palabra limit verificara y modificara la sentencia
                    if (this.getDataBaseType() == DataBase.SQLServer) {
                        if (StringUtils.containsIgnoreCase(sql, "LIMIT")) {
                            String temporal;
                            LogsJB.debug("Sentencia SQL a modificar: " + sql);
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
                            LogsJB.debug("Se modifico la sentencia SQL para que unicamente obtenga la cantidad de " +
                                    "registros especificados por el usuario: " + sql);
                        }
                    }
                    //LogsJB.info(sql);
                    Connection connect = this.getConnection();
                    PreparedStatement ejecutor = connect.prepareStatement(sql);
                    for (int i = 0; i < parametros.size(); i++) {
                        //Obtengo la información de la columna
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    ResultSet registros = ejecutor.executeQuery();
                    while (registros.next()) {
                        temp.add(this.procesarResultSetJSON(columnas, registros));
                    }
                    this.closeConnection(connect);
                    this.setTaskIsReady(true);
                    return new ResultAsync<>(temp, null);
                } else {
                    LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                            "recuperar los Registros");
                    this.setTaskIsReady(true);
                    return new ResultAsync<>(temp, null);
                }
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que Recupera la lista de registros que cumplen con la sentencia" +
                        "SQL de la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                this.setTaskIsReady(true);
                return new ResultAsync<>(temp, e);
            }
        };
        Future<ResultAsync<List<JSONObject>>> future = ejecutor.submit(get);
        while (!future.isDone()) {
        }
        ResultAsync<List<JSONObject>> resultado = future.get();
        if (!Objects.isNull(resultado.getException())) {
            throw resultado.getException();
        }
        lista = resultado.getResult();
        return lista;
    }
}

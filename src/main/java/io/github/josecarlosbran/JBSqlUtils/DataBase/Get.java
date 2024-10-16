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
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import io.github.josecarlosbran.JBSqlUtils.Utilities.ColumnsSQL;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

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
     * Carla: VERSIÓN ORIGINAL HACIENDO cambiado al uso de USO DEL CompletableFuture
     * Llena el modelo que invoca este método con la información que obtiene de BD's
     *
     * @param modelo     Modelo que será llenado
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    protected <T extends JBSqlUtils> T get(T modelo, String Sql, List<Column> parametros) throws Exception {
        // Ejecuta la tarea asíncrona y espera a su resultado
        return this.getCompletableFuture(modelo, Sql, parametros).get();
    }

    /**
     * Carla: VERSIÓN CAMBIO APLICANDO EL  CompletableFuture
     * Llena el modelo ompletableFuture que invoca este método con la información que obtiene de BD's
     *
     * @param modelo     Modelo ompletableFuture que será llenado
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    protected <T extends JBSqlUtils> CompletableFuture<T> getCompletableFuture(T modelo, String Sql, List<Column> parametros) throws Exception {
        if (!this.getGetPropertySystem()) {
            modelo.setGetPropertySystem(false);
            modelo.llenarPropertiesFromModel(modelo);
        }
        modelo.setTaskIsReady(false);
        modelo.validarTableExist(modelo).join();
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connect = modelo.getConnection()) {
                String query = "SELECT * FROM " + modelo.getTableName() + Sql + ";";
                query = modelo.generateOrderSQL(query, modelo);
                try (PreparedStatement ejecutor = connect.prepareStatement(query)) {
                    for (int i = 0; i < parametros.size(); i++) {
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    try (ResultSet registros = ejecutor.executeQuery()) {
                        while (registros.next()) {
                            procesarResultSetOneResult(modelo, registros);
                        }
                    }
                    modelo.setTaskIsReady(true);
                    return modelo; // Devuelve el modelo poblado
                }
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que obtiene la información del modelo de la BD's, Trace de la Excepción: " + ExceptionUtils.getStackTrace(e));
                modelo.setTaskIsReady(true);
                throw new CompletionException(e); // Envuelve en CompletionException
            }
        });
    }

    /*** CARLA: YA ESTA APLICADO EL CAMBIO
     * Ya esta modificado al uso de  CompletableFuture
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
        return this.firstCompleteableFeature(modelo, Sql, parametros).get();
    }

    /*** CARLA: YA ESTA APLICADO EL CAMBIO
     * Obtiene un CompleteableFeature que representa el modelo del tipo que invoca este método con la información que obtiene de BD's
     *
     * @param modelo     Modelo que está invocando el método
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return Retorna un CompleteableFeature que representa el modelo del tipo que invoca este método con la información que obtiene de BD's.
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    protected <T extends JBSqlUtils> CompletableFuture<T> firstCompleteableFeature(T modelo, String Sql, List<Column> parametros) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Verifica el estado del sistema de propiedades
                if (!this.getGetPropertySystem()) {
                    modelo.setGetPropertySystem(false);
                    modelo.llenarPropertiesFromModel(modelo);
                }
                // Asegurarse de que la tabla existe antes de proceder
                modelo.setTaskIsReady(false);
                modelo.validarTableExist(modelo).join();
                T modeloTemp = modelo.obtenerInstanciaOfModel(modelo);
                // Ejecutar la consulta SQL
                try (Connection connect = modelo.getConnection()) {
                    String query = "SELECT * FROM " + modelo.getTableName() + Sql + ";";
                    query = modelo.generateOrderSQL(query, modelo);
                    PreparedStatement ejecutor = connect.prepareStatement(query);
                    for (int i = 0; i < parametros.size(); i++) {
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    try (ResultSet registros = ejecutor.executeQuery()) {
                        if (registros.next()) {
                            modeloTemp = procesarResultSet(modelo, registros);
                        }
                    }
                    // Marca la tarea como completa
                    modelo.setTaskIsReady(true);
                    return modeloTemp;
                } catch (Exception e) {
                    LogsJB.fatal("Excepción en el método que obtiene el modelo de la BD: " + ExceptionUtils.getStackTrace(e));
                    modelo.setTaskIsReady(true);
                    throw new CompletionException(e); // Asegurarse de propagar la excepción en el flujo de CompletableFuture
                }
            } catch (Exception e) {
                throw new CompletionException(e); // Envolver cualquier excepción en un CompletionException
            }
        });
    }

    /**
     * Carla: VERSION ORIGINAL DEL firstOrFailGet
     * Llena el modelo que invoca este método con la información que obtiene de BD's
     *
     * @param modelo     Modelo que está invocando el metodo
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    protected <T extends JBSqlUtils> T firstOrFailGet(T modelo, String Sql, List<Column> parametros) throws Exception {
        try {
            // Ejecuta la tarea asíncrona y espera a su resultado
            return this.firstOrFailGetCompleteableFeature(modelo, Sql, parametros).get();
        } catch (ExecutionException e) {
            // Si es una CompletionException, revisa si la causa es ModelNotFound y la vuelve a lanzar
            if (e.getCause() instanceof ModelNotFound) {
                throw (ModelNotFound) e.getCause();
            }
            // Si no es una ModelNotFound, vuelve a lanzar la excepción como es
            throw e;
        }
    }

    /**
     * Carla : firstOrFailGet- ADICION DEL CompleteableFeature,
     * Llena el modelo CompletableFuture que invoca este método con la información que obtiene de BD's
     *
     * @param modelo     Modelo que está invocando el metodo
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    protected <T extends JBSqlUtils> CompletableFuture<T> firstOrFailGetCompleteableFeature(T modelo, String Sql, List<Column> parametros) throws Exception {
        if (!this.getGetPropertySystem()) {
            modelo.setGetPropertySystem(false);
            modelo.llenarPropertiesFromModel(modelo);
        }
        modelo.setTaskIsReady(false);
        modelo.validarTableExist(modelo).join();
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connect = modelo.getConnection()) {
                String query = "SELECT * FROM " + modelo.getTableName() + Sql + ";";
                query = modelo.generateOrderSQL(query, modelo);
                try (PreparedStatement ejecutor = connect.prepareStatement(query)) {
                    for (int i = 0; i < parametros.size(); i++) {
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    boolean result = false;
                    try (ResultSet registros = ejecutor.executeQuery()) {
                        if (registros.next()) {
                            procesarResultSetOneResult(modelo, registros);
                            result = true;
                        }
                    }
                    modelo.setTaskIsReady(true);
                    if (!result) {
                        String sql = "SELECT * FROM " + modelo.getTableName();
                        throw new ModelNotFound("No existe un modelo en BD's que corresponda a los criterios de la consulta sql: " + sql + Sql);
                    }
                    return modelo; // Devuelve el modelo poblado
                }
            } catch (ModelNotFound e) {
                // Lanza la excepción sin envolverla
                throw new CompletionException(e);  // Usa CompletionException en lugar de CompletionException
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                LogsJB.fatal("Excepción disparada en el método que obtiene la información del modelo de la BD's, Trace de la Excepción: " + ExceptionUtils.getStackTrace(e));
                modelo.setTaskIsReady(true);
                throw new CompletionException(e);  // Lanza CompletionException para otras excepciones
            } catch (DataBaseUndefind e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * CARLA: YA ESTA APLICADO EL CAMBIO
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
        try {
            // Ejecuta la tarea asíncrona y espera a su resultado
            return this.firstOrFailCompleteableFeature(modelo, Sql, parametros).get();
        } catch (ExecutionException e) {
            // Si es una CompletionException, revisa si la causa es ModelNotFound y la vuelve a lanzar
            if (e.getCause() instanceof ModelNotFound) {
                throw (ModelNotFound) e.getCause();
            }
            // Si no es una ModelNotFound, vuelve a lanzar la excepción como es
            throw e;
        }
    }

    /*** CARLA: YA ESTA APLICADO EL CAMBIO
     * Obtiene un CompleteableFeature con el modelo del tipo que invoca este metodo con la información que obtiene de BD's
     *
     * @param modelo     Modelo que está invocando el metodo
     * @param Sql        Sentencia SQL para obtener el modelo
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param <T>        Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna un CompleteableFeature que representa el modelo del tipo que invoca este metodo con la información que obtiene de BD's.
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    protected <T extends JBSqlUtils> CompletableFuture<T> firstOrFailCompleteableFeature(T modelo, String Sql, List<Column> parametros) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (!this.getGetPropertySystem()) {
                    modelo.setGetPropertySystem(false);
                    modelo.llenarPropertiesFromModel(modelo);
                }
                modelo.setTaskIsReady(false);
                modelo.validarTableExist(modelo).join();  // Asegurarse de que la tabla existe antes de proceder
                T modeloTemp = modelo.obtenerInstanciaOfModel(modelo);
                try (Connection connect = modelo.getConnection()) {
                    String query = "SELECT * FROM " + modelo.getTableName() + Sql + ";";
                    query = modelo.generateOrderSQL(query, modelo);
                    PreparedStatement ejecutor = connect.prepareStatement(query);
                    for (int i = 0; i < parametros.size(); i++) {
                        Column columnsSQL = parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                    }
                    LogsJB.info(ejecutor.toString());
                    try (ResultSet registros = ejecutor.executeQuery()) {
                        if (registros.next()) {
                            modeloTemp = procesarResultSet(modelo, registros);
                        }
                    }
                    modelo.setTaskIsReady(true);
                    return modeloTemp;
                } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's, Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                    modelo.setTaskIsReady(true);
                    throw new CompletionException(e);
                }
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenApply(modeloResult -> {
            if (!modeloResult.getModelExist()) {
                String sql = "SELECT * FROM " + modelo.getTableName();
                throw new CompletionException(new ModelNotFound("No existe un modelo en BD's que corresponda a los criterios de la consulta sql: " + sql + Sql));
            }
            return modeloResult;
        });
    }

    /**
     * Carla: versión modificada del metodo getAll con el uso de  CompletableFuture
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
        // Ejecuta la tarea asíncrona y espera a su resultado
        return this.getAllCompletableFuture(modelo, Sql, parametros).get();
    }

    /**
     * Carla: versión con cambios del metodo getAll  con el uso de  CompletableFuture
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
    protected <T extends JBSqlUtils> CompletableFuture<List<T>> getAllCompletableFuture(T modelo, String Sql, List<Column> parametros) throws Exception {
        if (!this.getGetPropertySystem()) {
            modelo.setGetPropertySystem(false);
            modelo.llenarPropertiesFromModel(modelo);
        }
        modelo.setTaskIsReady(false);
        modelo.validarTableExist(modelo).join();
        final String finalSql = Sql; // Hacer Sql final
        return CompletableFuture.supplyAsync(() -> {
            List<T> listaTemp = new ArrayList<>();
            try (Connection connect = modelo.getConnection()) {
                if (modelo.getTableExist()) {
                    String query = "SELECT * FROM " + modelo.getTableName() + finalSql + ";";
                    query = modelo.generateOrderSQL(query, modelo);
                    try (PreparedStatement ejecutor = connect.prepareStatement(query)) {
                        for (int i = 0; i < parametros.size(); i++) {
                            // Obtengo la información de la columna
                            Column columnsSQL = parametros.get(i);
                            convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                        }
                        LogsJB.info(ejecutor.toString());
                        try (ResultSet registros = ejecutor.executeQuery()) {
                            while (registros.next()) {
                                listaTemp.add(procesarResultSet(modelo, registros));
                            }
                        }
                    }
                } else {
                    LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo recuperar el Registro");
                }
                modelo.setTaskIsReady(true);
                return listaTemp; // Devuelve la lista de resultados
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que Recupera la lista de registros que cumplen con la sentencia SQL de la BD's, " +
                        "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                modelo.setTaskIsReady(true);
                throw new CompletionException(e); // Envuelve en CompletionException
            }
        });
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
    protected <T extends Methods_Conexion> List<JSONObject> get(String Sql, List<Column> parametros, String... columnas) throws Exception {
        return this.getCompletableFuture(Sql, parametros, columnas).get();
    }

    /**
     * Obtiene un Completeable Feature que representa la lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
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
    protected <T extends Methods_Conexion> CompletableFuture<List<JSONObject>> getCompletableFuture(String Sql, List<Column> parametros, String... columnas) throws Exception {
        String tableName = Sql.replace("SELECT * FROM ", "").split(" ")[0];
        this.setTaskIsReady(false);
        this.setTableName(tableName);
        return this.validarTableExist(this).thenCompose(v -> {
            if (this.getTableExist()) {
                return CompletableFuture.supplyAsync(() -> {
                    List<JSONObject> temp = new ArrayList<>();
                    String query = Sql + ";";
                    try (Connection connect = this.getConnection()) {
                        query = this.generateOrderSQL(query, this);
                        if (columnas != null && columnas.length > 0) {
                            StringBuilder columnasStr = new StringBuilder();
                            for (String columna : columnas) {
                                if (columnasStr.length() > 0) {
                                    columnasStr.append(", ");
                                }
                                columnasStr.append(columna);
                            }
                            query = query.replace("SELECT *", "SELECT " + columnasStr);
                        }
                        PreparedStatement ejecutor = connect.prepareStatement(query);
                        for (int i = 0; i < parametros.size(); i++) {
                            Column columnsSQL = parametros.get(i);
                            convertJavaToSQL(columnsSQL, ejecutor, i + 1);
                        }
                        LogsJB.info(ejecutor.toString());
                        ResultSet registros = ejecutor.executeQuery();
                        ResultSetMetaData metaData = registros.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        List<ColumnsSQL> columnMetadata = new ArrayList<>(columnCount);
                        for (int i = 1; i <= columnCount; i++) {
                            ColumnsSQL columna = new ColumnsSQL();
                            columna.setCOLUMN_NAME(metaData.getColumnName(i));
                            columna.setTYPE_NAME(metaData.getColumnTypeName(i));
                            columnMetadata.add(columna);
                        }
                        while (registros.next()) {
                            temp.add(this.procesarResultSetJSON(registros, columnMetadata));
                        }
                        this.closeConnection(connect);
                        this.setTaskIsReady(true);
                        return temp;
                    } catch (Exception e) {
                        LogsJB.fatal("Excepción disparada en el método que Recupera la lista de registros que cumplen con la sentencia" +
                                "SQL de la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                        this.setTaskIsReady(true);
                        throw new CompletionException(e);
                    }
                });
            } else {
                LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                        "recuperar los Registros");
                this.setTaskIsReady(true);
                return CompletableFuture.completedFuture(new ArrayList<>());
            }
        });
    }
}

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
import io.github.josecarlosbran.JBSqlUtils.Anotations.Actions;
import io.github.josecarlosbran.JBSqlUtils.Anotations.ColumnDefined;
import io.github.josecarlosbran.JBSqlUtils.Anotations.ForeignKey;
import io.github.josecarlosbran.JBSqlUtils.Anotations.Index;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ConexionUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.*;
import java.sql.Date;
import java.sql.*;
import java.util.Set;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.*;

/**
 * @author Jose Bran
 * Clase que proporciona los métodos de conexión necesarios para que el modelo pueda realizar
 * la conversion de datos entre java y sql, así mismo proporciona métodos de logica del manejor del resultado
 * como de envíar valores a BD's
 */
class Methods_Conexion extends Conexion {
    /**
     * Constructor de la clase Conexión que se encarga de inicializar las propiedades de conexión del modelo,
     * las cuales las obtiene de las propiedades del sistema Java.
     */
    protected Methods_Conexion() {
        super();
        this.getMethodsModel();
        this.getFieldsModel();
    }

    /**
     * Constructor de la clase Conexión que se encarga de inicializar las propiedades de conexión del modelo,
     * las cuales las obtiene de las propiedades del sistema Java.
     *
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    protected Methods_Conexion(Boolean getPropertySystem) {
        super(getPropertySystem);
        this.getMethodsModel();
        this.getFieldsModel();
    }

    /**
     * Obtiene la lista de métodos pertenecientes al modelo que lo invoca.
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     */
    protected synchronized <T> void getMethodsModel() {
        Method[] metodos = this.getClass().getMethods();
        String classColumn = Column.class.getSimpleName();
        List<Method> getMethods = this.getMethodsGetOfModel();
        List<Method> setMethods = this.getMethodsSetOfModel();
        for (Method metodo : metodos) {
            String returnType = metodo.getReturnType().getSimpleName();
            String nameMetodo = metodo.getName();
            Parameter[] parametros = metodo.getParameters();
            if (metodo.getDeclaringClass().getPackage().hashCode() != JBSqlUtils.class.getPackage().hashCode() && StringUtils.startsWithIgnoreCase(nameMetodo, "get")) {
                getMethods.add(metodo);
            } else if (parametros.length == 1 && metodo.getDeclaringClass().getPackage().hashCode() != JBSqlUtils.class.getPackage().hashCode()
                    && StringUtils.startsWithIgnoreCase(nameMetodo, "set")) {
                setMethods.add(metodo);
            }
        }
    }

    /**
     * este metodo esta creado para ver si la clase actual es una subclase de JBSqlUtils,
     * mediante los get se obtienen todos los campos de la clase actual y algunos que tienen datos especificos,
     * los añade a una coleccion existente, pero los filtra primero.
     *
     * @param <T> es un metodo generico, se puede trabajar con diferentes tipos especificados en la instancia.
     */
    protected synchronized <T> void getFieldsModel() {
        //se esta obteniendo el nombre simple de la clase JBSqlUtils y se esta almacenando en la variable declarada
        String JBSQLUTILSNAME = JBSqlUtils.class.getSimpleName();
        //se obtiene el nombre de la superclase y se almacena en superclasemodelo
        String SuperClaseModelo = this.getClass().getSuperclass().getSimpleName();
        if (StringUtils.equalsIgnoreCase(JBSQLUTILSNAME, SuperClaseModelo)) {
            //Obtiene los Fields del modelo
            List<Field> modelFields = Arrays.asList(this.getClass().getDeclaredFields());
            List<Field> modelFieldsWithAnotations =
                    Arrays.asList(FieldUtils.getFieldsWithAnnotation(this.getClass(),
                            ColumnDefined.class));
            // Combina y filtra los campos
            List<Field> combinedFields = Stream.concat(
                    modelFields.stream().filter(field -> !modelFieldsWithAnotations.contains(field)),
                    modelFieldsWithAnotations.stream()
            ).collect(Collectors.toList());
            this.getFieldsOfModel().addAll(modelFields);
        }
    }

    /**
     * Obtiene la conexión del modelo a la BD's con las propiedades definidas.
     *
     * @return Retorna la conexión del modelo a la BD's con las propiedades definidas.
     */
    public synchronized Connection getConnection() {
        Connection connect = null;
        try {
            if (this.getContadorConexiones() == 0) {
            }
            String url;
            String usuario = this.getUser();
            String password = this.getPassword();
            if (this.getDataBaseType() == DataBase.PostgreSQL) {
                String host = this.getHost();
                Class.forName("org.postgresql.Driver");
                DriverManager.registerDriver(new org.postgresql.Driver());
                if (!stringIsNullOrEmpty(this.getPort())) {
                    host = host + ":" + this.getPort();
                }
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        host;
                if (!stringIsNullOrEmpty(this.getBD())) {
                    url = url + "/" + this.getBD();
                }
                if (!stringIsNullOrEmpty(this.getPropertisURL())) {
                    url = url + this.getPropertisURL();
                }
                LogsJB.debug("Url de conexion a DB: " + url);
                connect = DriverManager.getConnection(url, usuario, password);
            }
            if (this.getDataBaseType() == DataBase.MySQL) {
                connect = null;
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + "/" + this.getBD();
                if (!stringIsNullOrEmpty(this.getPropertisURL())) {
                    url = url + this.getPropertisURL();
                }
                LogsJB.debug("Url de conexion a DB: " + url);
                connect = DriverManager.getConnection(url, usuario, password);
            }
            if (this.getDataBaseType() == DataBase.MariaDB) {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                connect = null;
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + "/" + this.getBD();
                if (!stringIsNullOrEmpty(this.getPropertisURL())) {
                    url = url + this.getPropertisURL();
                }
                LogsJB.debug("Url de conexion a DB: " + url);
                connect = DriverManager.getConnection(url, usuario, password);
            }
            if (this.getDataBaseType() == DataBase.SQLServer) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                connect = null;
                String host = this.getHost();
                if (!stringIsNullOrEmpty(this.getPort())) {
                    host = host + ":" + this.getPort();
                }
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        host;
                if (!stringIsNullOrEmpty(this.getBD())) {
                    url = url + ";databaseName=" + this.getBD();
                }
                if (!stringIsNullOrEmpty(this.getPropertisURL())) {
                    url = url + this.getPropertisURL();
                }
                LogsJB.debug("Url de conexion a DB: " + url);
                connect = DriverManager.getConnection(url, usuario, password);
            }
            if (this.getDataBaseType() == DataBase.SQLite) {
                Class.forName("org.sqlite.JDBC").newInstance();
                DriverManager.registerDriver(new org.sqlite.JDBC());
                //Rutas de archivos
                File fichero = new File(this.getBD());
                //Verifica si existe la carpeta Logs, si no existe, la Crea
                String carpeta = fichero.getParent();
                if (!Objects.isNull(carpeta) && !(new File(carpeta).exists())) {
                    File directorio = new File(carpeta);
                    directorio.mkdirs();
                    LogsJB.debug("Crea el directorio donde estara la BD's SQLite: " + fichero.getParent());
                }
                url = "jdbc:" + this.getDataBaseType().getDBType() + ":" + this.getBD();
                LogsJB.debug("Url de conexion a DB: " + url);
                connect = DriverManager.getConnection(url);
            }
            LogsJB.info("Conexión a BD's " + this.getBD() + " Realizada exitosamente " + this.getClass().getSimpleName());
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
        this.setContadorConexiones(this.getContadorConexiones() + 1);
        return connect;
    }

    /**
     * Cierra la conexión a BD's
     *
     * @param connect Conexión que se desea cerrar
     */
    public void closeConnection(Connection connect) {
        try {
            if (Objects.isNull(connect)) {
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new ConexionUndefind("No se a conectado el modelo a la BD's");
            }
            if (!connect.isClosed()) {
                connect.close();
                LogsJB.info("Conexión a BD's cerrada " + this.getClass().getSimpleName());
            }
        } catch (ConexionUndefind | SQLException e) {
            LogsJB.warning("El modelo no estaba conectado a la BD's por lo cual no se cerrara la conexión: " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Verifica la existencia de la tabla correspondiente al modelo en BD's
     *
     * @return True si la tabla correspondiente al modelo existe en BD's, de lo contrario False.
     */
    public CompletableFuture<Boolean> tableExist() {
        return CompletableFuture.supplyAsync(() -> {
            Connection connect = null;
            ResultSet tables = null;
            try {
                LogsJB.info("Comienza a verificar la existencia de la tabla");
                connect = this.getConnection();
                DatabaseMetaData metaData = connect.getMetaData();
                String databaseName = this.getBD();
                tables = metaData.getTables(null, null, "%", null);
                LogsJB.trace("Revisara el resultSet");
                while (tables.next()) {
                    TablesSQL temp = new TablesSQL();
                    temp.setTABLE_CAT(tables.getString(1));
                    temp.setTABLE_SCHEM(tables.getString(2));
                    temp.setTABLE_NAME(tables.getString(3));
                    temp.setTABLE_TYPE(tables.getString(4));
                    temp.setREMARKS(tables.getString(5));
                    if (this.getDataBaseType() != DataBase.SQLServer) {
                        temp.setTYPE_CAT(tables.getString(6));
                        temp.setTYPE_SCHEM(tables.getString(7));
                        temp.setTYPE_NAME(tables.getString(8));
                        temp.setSELF_REFERENCING_COL_NAME(tables.getString(9));
                        temp.setREF_GENERATION(tables.getString(10));
                    }
                    String nameModel = this.getTableName();
                    String nameTable = temp.getTABLE_NAME();
                    String databaseTemp = tables.getString(1);
                    String databaseTemp2 = tables.getString(2);
                    boolean tablaisofDB = true;
                    if (nameModel.equalsIgnoreCase(nameTable)) {
                        LogsJB.debug("Base de datos del modelo: " + databaseName + " Base de datos del servidor: " + databaseTemp);
                    }
                    if (!stringIsNullOrEmpty(databaseTemp) && !databaseName.equalsIgnoreCase(databaseTemp)) {
                        tablaisofDB = false;
                    }
                    if (!tablaisofDB && !stringIsNullOrEmpty(databaseTemp2)) {
                        tablaisofDB = databaseName.equalsIgnoreCase(databaseTemp2);
                    }
                    if (this.getDataBaseType() == DataBase.PostgreSQL && nameModel.equalsIgnoreCase(nameTable)) {
                        tablaisofDB = true;
                    }
                    if (nameModel.equalsIgnoreCase(nameTable) && tablaisofDB) {
                        LogsJB.debug("Base de datos del modelo: " + databaseName + " Base de datos del servidor3: " + databaseTemp);
                        this.setTableExist(Boolean.TRUE);
                        this.setTableName(nameTable);
                        this.setTabla(temp);
                        ResultSet clavePrimaria = metaData.getPrimaryKeys(temp.getTABLE_CAT(), temp.getTABLE_SCHEM(), nameTable);
                        if (clavePrimaria.next()) {
                            PrimaryKey clave = new PrimaryKey();
                            clave.setTABLE_CAT(clavePrimaria.getString(1));
                            clave.setTABLE_SCHEM(clavePrimaria.getString(2));
                            clave.setTABLE_NAME(clavePrimaria.getString(3));
                            clave.setCOLUMN_NAME(clavePrimaria.getString(4));
                            clave.setKEY_SEQ(clavePrimaria.getShort(5));
                            clave.setPK_NAME(clavePrimaria.getString(6));
                            this.getTabla().setClaveprimaria(clave);
                        }
                        LogsJB.info("La tabla correspondiente a este modelo, existe en BD's " + this.getClass().getSimpleName());
                        getColumnsTable(connect);
                        return true;
                    }
                }
                LogsJB.trace("Termino de Revisarar el resultSet");
                if (!this.getTableExist()) {
                    LogsJB.info("La tabla correspondiente a este modelo, No existe en BD's " + this.getClass().getSimpleName());
                    return false;
                }
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que verifica si existe la tabla correspondiente al modelo, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                return false;
            } finally {
                this.closeConnection(connect);
            }
            return false;
        });
    }

    /**
     * Obtiene las columnas que tiene la tabla correspondiente al modelo en BD's.
     *
     * @param connect a BD's para obtener la metadata
     * @throws SQLException Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    protected void getColumnsTable(Connection connect) throws SQLException {
        LogsJB.debug("Comienza a obtener las columnas que le pertenecen a la tabla " + this.getTableName());
        DatabaseMetaData metaData = null;
        ResultSet columnas = null;
        try {
            LogsJB.trace("Obtuvo el objeto conexión");
            metaData = connect.getMetaData();
            LogsJB.trace("Ya tiene el MetaData de la BD's");
            columnas = metaData.getColumns(this.getTabla().getTABLE_CAT(),
                    this.getTabla().getTABLE_SCHEM(), this.getTableName(), null);
            LogsJB.debug("Ya tiene el resultset con las columnas de la tabla");
            // Limpiar las listas antes de llenarlas
            this.getTabla().getColumnas().clear();
            this.getTabla().getColumnsExist().clear();
            while (columnas.next()) {
                ColumnsSQL temp = new ColumnsSQL();
                temp.setTABLE_CAT(columnas.getString(1));
                temp.setTABLE_SCHEM(columnas.getString(2));
                temp.setTABLE_NAME(columnas.getString(3));
                temp.setCOLUMN_NAME(columnas.getString(4));
                this.getTabla().getColumnsExist().add(columnas.getString(4).toUpperCase());
                temp.setDATA_TYPE(columnas.getInt(5));
                temp.setTYPE_NAME(columnas.getString(6));
                temp.setCOLUMN_SIZE(columnas.getInt(7));
                temp.setDECIMAL_DIGITS(columnas.getInt(9));
                temp.setNUM_PREC_RADIX(columnas.getInt(10));
                temp.setNULLABLE(columnas.getInt(11));
                temp.setREMARKS(columnas.getString(12));
                temp.setCOLUMN_DEF(columnas.getString(13));
                temp.setCHAR_OCTET_LENGTH(columnas.getInt(16));
                temp.setORDINAL_POSITION(columnas.getInt(17));
                temp.setIS_NULLABLE(columnas.getString(18));
                temp.setSCOPE_CATALOG(columnas.getString(19));
                temp.setSCOPE_SCHEMA(columnas.getString(20));
                temp.setSCOPE_TABLE(columnas.getString(21));
                temp.setSOURCE_DATA_TYPE(columnas.getShort(22));
                temp.setIS_AUTOINCREMENT(columnas.getString(23));
                temp.setIS_GENERATEDCOLUMN(columnas.getString(24));
                this.getTabla().getColumnas().add(temp);
            }
            LogsJB.debug("Información de las columnas de la tabla correspondiente al modelo obtenida " + this.getClass().getSimpleName());
        } catch (SQLException e) {
            LogsJB.fatal("Excepción al obtener las columnas de la tabla: " + ExceptionUtils.getStackTrace(e));
            throw e;
        } finally {
            this.closeConnection(connect);
        }
        this.getTabla().getColumnas().sort(Comparator.comparing(ColumnsSQL::getORDINAL_POSITION));
    }

    /**
     * Metodo que actualiza la información que el modelo tiene sobre lo que existe en BD's'
     * y Recarga el modelo si este existía previamente en BD's
     *
     * @throws Exception Lanza una Excepción si ocurre algun error al ejecutar el metodo refresh
     */
    public void refresh() throws Exception {
        this.tableExist().join();
        this.reloadModel();
    }

    /**
     * Refresca el modelo con la información de BD's, se perderan las modificaciones que se hayan realizadas sobre el modelo,
     * si estas no han sido plasmadas en BD's.
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return True si el modelo fue recargado desde BD's, False caso contrario.
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> Boolean reloadModel() throws Exception {
        return this.reloadModelCompletableFuture().get();
    }

    /**
     * Nuevo metodo aplicando el CompletableFuture
     * Refresca el modelo con la información de BD's, se perderan las modificaciones que se hayan realizadas sobre el modelo,
     * si estas no han sido plasmadas en BD's.
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return True si el modelo fue recargado desde BD's, False caso contrario.
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> CompletableFuture<Boolean> reloadModelCompletableFuture() throws Exception {
        this.setTaskIsReady(false);
        // Validar la existencia de la tabla de forma síncrona
        this.validarTableExist(this).join();
        return CompletableFuture.supplyAsync(() -> {
            Boolean result = false;
            Connection connect = null;
            ResultSet registros = null;
            try {
                if (this.getTableExist()) {
                    StringBuilder sql = new StringBuilder("SELECT * FROM ").append(this.getTableName());
                    String namePrimaryKey = this.getTabla().getClaveprimaria().getCOLUMN_NAME();
                    List<Field> columnas = this.getFieldsOfModel();
                    List<Field> values = new ArrayList<>();
                    for (Field columna : columnas) {
                        if ((StringUtils.equalsIgnoreCase(namePrimaryKey, this.getColumnName(columna)) && !this.getValueColumnIsNull(this, columna))
                                || (this.getColumnIsIndexValidValue(this, columna))) {
                            values.add(columna);
                            if (values.size() > 1) {
                                sql.append(" AND ");
                            } else {
                                sql.append(" WHERE ");
                            }
                            sql.append(this.getColumnName(columna)).append(" = ?");
                        }
                    }
                    sql.append(";");
                    LogsJB.info(sql.toString());
                    connect = this.getConnection();
                    //if sql contiene WHERE HACE EL RESTO DE LOGICA CASO CONTRARIO RETORNA FALSE
                    if (StringUtils.containsIgnoreCase(sql.toString(), "WHERE")) {
                        PreparedStatement ejecutor = connect.prepareStatement(sql.toString());
                        int auxiliar = 0;
                        for (Field value : values) {
                            auxiliar++;
                            convertJavaToSQL(this, value, ejecutor, auxiliar);
                        }
                        registros = ejecutor.executeQuery();
                        if (registros.next()) {
                            procesarResultSetOneResult((T) this, registros);
                            result = true;
                        }
                    } else {
                        result = false;
                    }
                    return result; // Retornar el resultado como Boolean
                } else {
                    LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo recuperar el Registro: " + this.getTableName());
                    return result; // Retornar false si la tabla no existe
                }
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que Recupera la lista de registros que cumplen con la sentencia SQL de la BD's, Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                throw new CompletionException(e); // Lanzar una RuntimeException para manejar en el futuro
            } finally {
                this.closeConnection(connect);
            }
        }).thenApply(reloadModel -> {
            this.setTaskIsReady(true);
            return reloadModel; // Retornar el resultado booleano
        });
    }

    /**
     * Metodo que setea la información de la columna Java en el respectivo tipo de Dato SQL
     *
     * @param columnsSQL Columna java que será analizada
     * @param ejecutor   PreparedStatement sobre el cual se estara envíando la información de la columna
     * @param auxiliar   Indice que indica la posición del parametro en el ejecutor.
     * @throws SQLException Lanza esta excepción si sucede algún problema al setear el valor Java en el ejecutor.
     */
    protected void convertJavaToSQL(Column columnsSQL, PreparedStatement ejecutor, int auxiliar) throws SQLException {
        LogsJB.debug("DataType de la columna: " + columnsSQL.getDataTypeSQL());
        LogsJB.debug("Indice donde insertara la columna: " + auxiliar);
        LogsJB.debug("Valor de la columna: " + columnsSQL.getValor());
        if ((columnsSQL.getDataTypeSQL() == DataType.NCHAR) || (columnsSQL.getDataTypeSQL() == DataType.NVARCHAR)) {
            //Caracteres y cadenas de Texto
            ejecutor.setNString(auxiliar, (String) columnsSQL.getValor());
        } else if ((columnsSQL.getDataTypeSQL() == DataType.CHAR) || (columnsSQL.getDataTypeSQL() == DataType.VARCHAR)
                || (columnsSQL.getDataTypeSQL() == DataType.LONGVARCHAR)
                || (columnsSQL.getDataTypeSQL() == DataType.TEXT)
        ) {
            //Caracteres y cadenas de Texto
            ejecutor.setString(auxiliar, (String) columnsSQL.getValor());
        } else if ((columnsSQL.getDataTypeSQL() == DataType.SMALLINT) || (columnsSQL.getDataTypeSQL() == DataType.TINYINT)
                || (columnsSQL.getDataTypeSQL() == DataType.INTEGER) || (columnsSQL.getDataTypeSQL() == DataType.IDENTITY)
                || (columnsSQL.getDataTypeSQL() == DataType.SERIAL)) {
            //Valores Enteros
            Number valor = (Number) columnsSQL.getValor();
            ejecutor.setInt(auxiliar, valor.intValue());
        } else if ((columnsSQL.getDataTypeSQL() == DataType.NUMERIC) || (columnsSQL.getDataTypeSQL() == DataType.DECIMAL)
                || (columnsSQL.getDataTypeSQL() == DataType.MONEY) || (columnsSQL.getDataTypeSQL() == DataType.SMALLMONEY)
                || (columnsSQL.getDataTypeSQL() == DataType.DOUBLE)) {
            //Dinero y numericos que tienen decimales
            Number valor = (Number) columnsSQL.getValor();
            ejecutor.setDouble(auxiliar, valor.doubleValue());
        } else if ((columnsSQL.getDataTypeSQL() == DataType.BIT)
                || (columnsSQL.getDataTypeSQL() == DataType.BOOLEAN)
                || (columnsSQL.getDataTypeSQL() == DataType.BOOL)) {
            //Valores Booleanos
            ejecutor.setBoolean(auxiliar, (Boolean) columnsSQL.getValor());
        } else if ((columnsSQL.getDataTypeSQL() == DataType.REAL) || (columnsSQL.getDataTypeSQL() == DataType.FLOAT)) {
            //Valores Flotantes
            Number valor = (Number) columnsSQL.getValor();
            ejecutor.setFloat(auxiliar, valor.floatValue());
        } else if ((columnsSQL.getDataTypeSQL
                () == DataType.BINARY) || (columnsSQL.getDataTypeSQL() == DataType.VARBINARY)
                || (columnsSQL.getDataTypeSQL() == DataType.LONGVARBINARY)) {
            //Valores binarios
            ejecutor.setBytes(auxiliar, (byte[]) columnsSQL.getValor());
        } else if ((columnsSQL.getDataTypeSQL() == DataType.DATE)) {
            //DATE
            ejecutor.setDate(auxiliar, (Date) columnsSQL.getValor());
        } else if ((columnsSQL.getDataTypeSQL() == DataType.TIME)) {
            //Time
            ejecutor.setTime(auxiliar, (Time) columnsSQL.getValor());
        } else if ((columnsSQL.getDataTypeSQL() == DataType.TIMESTAMP) || (columnsSQL.getDataTypeSQL() == DataType.DATETIME)
                || (columnsSQL.getDataTypeSQL() == DataType.SMALLDATETIME)
                || (columnsSQL.getDataTypeSQL() == DataType.DATETIME2)) {
            //TimeStamp
            ejecutor.setTimestamp(auxiliar, (Timestamp) columnsSQL.getValor());
        } else {
            LogsJB.debug("No logro setear el tipo de dato");
            ejecutor.setObject(auxiliar, columnsSQL.getValor());
        }
    }

    /**
     * Metodo que setea la información de la columna Java en el respectivo tipo de Dato SQL
     *
     * @param columnsSQL Columna java que será analizada
     * @param ejecutor   PreparedStatement sobre el cual se estara envíando la información de la columna
     * @param auxiliar   Indice que indica la posición del parametro en el ejecutor.
     * @throws SQLException           Lanza esta excepción si sucede algún problema al setear el valor Java en el ejecutor.
     * @throws IllegalAccessException Lanza esta excepción si sucede algún problema al setear el valor Java en el ejecutor.
     */
    protected void convertJavaToSQL(Object modelo, Field columnsSQL, PreparedStatement ejecutor, int auxiliar) throws SQLException, IllegalAccessException {
        LogsJB.debug("DataType de la columna: " + columnsSQL.getType());
        LogsJB.debug("Indice donde insertara la columna: " + auxiliar);
        Object valor = getValueColumn(modelo, columnsSQL);
        LogsJB.debug("Valor de la columna: " + valor);
        Class<?> columnType = columnsSQL.getType();
        if (columnType.isAssignableFrom(String.class)) {
            //Caracteres y cadenas de Texto
            ejecutor.setString(auxiliar, (String) valor);
        } else if (columnType.isAssignableFrom(Double.class)) {
            Number value = (Number) valor;
            ejecutor.setDouble(auxiliar, value.doubleValue());
        } else if (columnType.isAssignableFrom(Integer.class)) {
            Number value = (Number) valor;
            ejecutor.setInt(auxiliar, value.intValue());
        } else if (columnType.isAssignableFrom(Float.class)) {
            Number value = (Number) valor;
            ejecutor.setFloat(auxiliar, value.floatValue());
        } else if (columnType.isAssignableFrom(Boolean.class)) {
            Boolean value = (Boolean) valor;
            ejecutor.setBoolean(auxiliar, value.booleanValue());
        } else if (columnType.isAssignableFrom(byte[].class)) {
            //Valores binarios
            ejecutor.setBytes(auxiliar, (byte[]) valor);
        } else if (columnType.isAssignableFrom(Date.class)) {
            //DATE
            ejecutor.setDate(auxiliar, (Date) valor);
        } else if (columnType.isAssignableFrom(Time.class)) {
            //Time
            ejecutor.setTime(auxiliar, (Time) valor);
        } else if (columnType.isAssignableFrom(Timestamp.class)) {
            //Timestamp
            ejecutor.setTimestamp(auxiliar, (Timestamp) valor);
        } else {
            ejecutor.setObject(auxiliar, valor);
        }
    }

    /**
     * Metodo que convierte la información obtenida de BD's a Java
     *
     * @param columna   Columna del modelo
     * @param resultado ResulSet que está siendo evaludo
     * @param field     Columna SQL que corresponde a la columna del modelo
     * @param invocador Invocador del metodo
     * @throws SQLException              Lanza esta excepción de suceder algún problema con el ResultSet
     * @throws InvocationTargetException Lanza esta excepción si hubiera algún problema al invocar el metodo Set
     * @throws IllegalAccessException    Lanza esta excepción si hubiera algún problema al invocar el metodo Set
     */
    protected void convertSQLtoJava(ColumnsSQL columna, ResultSet resultado, Field field, Object invocador) throws SQLException, InvocationTargetException, IllegalAccessException {
        Class<?> fieldType = field.getType();
        String columnName = columna.getCOLUMN_NAME();
        if (fieldType.isAssignableFrom(String.class)) {
            FieldUtils.writeField(invocador, field.getName(), resultado.getString(columnName), true);
        } else if (fieldType.isAssignableFrom(Double.class)) {
            FieldUtils.writeField(invocador, field.getName(), resultado.getDouble(columnName), true);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            FieldUtils.writeField(invocador, field.getName(), resultado.getInt(columnName), true);
        } else if (fieldType.isAssignableFrom(Float.class)) {
            FieldUtils.writeField(invocador, field.getName(), resultado.getFloat(columnName), true);
        } else if (fieldType.isAssignableFrom(Boolean.class)) {
            FieldUtils.writeField(invocador, field.getName(), resultado.getBoolean(columnName), true);
        } else if (fieldType.isAssignableFrom(byte[].class)) {
            FieldUtils.writeField(invocador, field.getName(), resultado.getBytes(columnName), true);
        } else if (fieldType.isAssignableFrom(Date.class)) {
            FieldUtils.writeField(invocador, field.getName(), resultado.getDate(columnName), true);
        } else if (fieldType.isAssignableFrom(Time.class)) {
            FieldUtils.writeField(invocador, field.getName(), resultado.getTime(columnName), true);
        } else if (fieldType.isAssignableFrom(Timestamp.class)) {
            FieldUtils.writeField(invocador, field.getName(), resultado.getTimestamp(columnName), true);
        } else {
            FieldUtils.writeField(invocador, field.getName(), resultado.getObject(columnName), true);
        }
    }

    /**
     * Agrega la columna como una propiedad del Json Object envíado como parametro
     *
     * @param columna   Columna que se obtendra.
     * @param resultado ResultSet del cual se obtendra el valor para la columna.
     * @param temp      Json Object al cual se agregara el valor de la columna como una propiedad del JSON.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected void convertSQLtoJson(ColumnsSQL columna, ResultSet resultado, JSONObject temp) throws SQLException {
        String columnName = columna.getCOLUMN_NAME();
        String columnType = columna.getTYPE_NAME();
        LogsJB.trace("DataType de la columna: " + columna.getTYPE_NAME());
        LogsJB.trace("Valor de la columna: " + resultado.getObject(columnName));
        if ((StringUtils.containsIgnoreCase(columnType, DataType.NCHAR.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.NVARCHAR.name()))
        ) {
            //Caracteres y cadenas de Texto
            temp.put(columnName.toUpperCase(), resultado.getNString(columnName));
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.CHAR.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.LONGVARCHAR.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.VARCHAR.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.TEXT.name()))
        ) {
            //Caracteres y cadenas de Texto
            temp.put(columnName.toUpperCase(), resultado.getString(columnName));
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.NUMERIC.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DECIMAL.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.MONEY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.SMALLMONEY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DOUBLE.name()))) {
            //Dinero y numericos que tienen decimales
            temp.put(columnName.toUpperCase(), resultado.getDouble(columnName));
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.BIT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.BOOLEAN.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.BOOL.name()))) {
            //Valores Booleanos
            Object valor = resultado.getObject(columnName);
            LogsJB.trace("Tipo de dato del valor obtenido: " + valor.getClass());
            LogsJB.trace("valor obtenido: " + valor);
            if ((valor instanceof String)) {
                temp.put(columnName.toUpperCase(), Boolean.valueOf((String) valor).booleanValue());
            } else if (valor instanceof Integer) {
                temp.put(columnName.toUpperCase(), getBooleanfromInt((int) valor));
            } else {
                temp.put(columnName.toUpperCase(), resultado.getBoolean(columnName));
            }
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.SMALLINT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.TINYINT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.INTEGER.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.IDENTITY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.INT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.SERIAL.name()))) {
            //Valores Enteros
            temp.put(columnName.toUpperCase(), resultado.getInt(columnName));
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.REAL.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.FLOAT.name()))) {
            //Valores Flotantes
            temp.put(columnName.toUpperCase(), resultado.getFloat(columnName));
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.BINARY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.VARBINARY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.LONGVARBINARY.name()))) {
            //Valores binarios
            temp.put(columnName.toUpperCase(), resultado.getBytes(columnName));
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.DATE.name()))) {
            //DATE
            temp.put(columnName.toUpperCase(), resultado.getDate(columnName));
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.TIME.name()))) {
            //Time
            temp.put(columnName.toUpperCase(), resultado.getTime(columnName));
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.TIMESTAMP.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DATETIME.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.SMALLDATETIME.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DATETIME2.name()))) {
            //TimeStamp
            temp.put(columnName.toUpperCase(), resultado.getTimestamp(columnName));
        } else {
            temp.put(columnName.toUpperCase(), resultado.getObject(columnName));
        }
    }

    /**
     * Almacena el modelo proporcionado en BD's.
     *
     * @param modelo Modelo que será insertado o actualizado en BD's
     * @param <T>    Expresión que hace que el metodo sea generico y pueda ser utilizado por cualquier objeto que herede la Clase JBSqlUtils
     * @return La cantidad de filas almacenadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    protected <T extends Methods_Conexion> CompletableFuture<Integer> saveModel(T modelo) throws Exception {
        modelo.setTaskIsReady(false);
        return modelo.validarTableExist(modelo).thenCompose(v -> {
            if (modelo.getTableExist()) {
                return CompletableFuture.supplyAsync(() -> {
                    try (Connection connect = modelo.getConnection()) {
                        StringBuilder sql = new StringBuilder();
                        StringBuilder sql2 = new StringBuilder();
                        List<Field> campos = modelo.getFieldsOfModel();
                        List<Field> values = new ArrayList<>();
                        List<Field> values2 = new ArrayList<>();
                        int datos = 0;
                        List<Integer> indicemetodos = new ArrayList<>();
                        if (modelo.getModelExist()) {
                            // Lógica de actualización
                            String namePrimaryKey = modelo.getTabla().getClaveprimaria().getCOLUMN_NAME();
                            sql.append("UPDATE ").append(modelo.getTableName()).append(" SET");
                            for (int i = 0; i < campos.size(); i++) {
                                Field campo = campos.get(i);
                                String columnName = getColumnName(campo);
                                if (((this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, this.getCreatedAt()))))
                                        || (!UtilitiesJB.stringIsNullOrEmpty(namePrimaryKey) && StringUtils.equalsIgnoreCase(namePrimaryKey, columnName))
                                        || (getValueColumnIsNull(modelo, campo))
                                        || (!this.getTabla().getColumnsExist().contains(columnName.toUpperCase()))
                                        || ((!this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, modelo.getCreatedAt()))
                                        || (StringUtils.equalsIgnoreCase(columnName, modelo.getUpdateAT()))))
                                ) {
                                    continue;
                                }
                                if (StringUtils.containsIgnoreCase(sql, "?")) {
                                    sql.append(", ").append(columnName).append("=?");
                                } else {
                                    sql.append(" ").append(columnName).append("=?");
                                }
                                datos++;
                                indicemetodos.add(i);
                                values.add(campo);
                            }
                            sql.append(" WHERE ");
                            int contador = 0;
                            for (Field columna : campos) {
                                if ((StringUtils.equalsIgnoreCase(namePrimaryKey, this.getColumnName(columna)) && !this.getValueColumnIsNull(this, columna))
                                        || (this.getColumnIsIndexValidValue(this, columna))) {
                                    values.add(columna);
                                    contador++;
                                    if (contador > 1) {
                                        sql.append(" AND ");
                                    }
                                    sql.append(this.getColumnName(columna)).append(" = ?");
                                }
                            }
                        } else {
                            // Lógica de inserción
                            sql.append("INSERT INTO ").append(modelo.getTableName()).append("(");
                            for (int i = 0; i < campos.size(); i++) {
                                Field campo = campos.get(i);
                                String columnName = getColumnName(campo);
                                if (((!this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, modelo.getCreatedAt()))
                                        || (StringUtils.equalsIgnoreCase(columnName, modelo.getUpdateAT()))))
                                        || (!this.getTabla().getColumnsExist().contains(columnName.toUpperCase()))
                                        || (Objects.isNull(FieldUtils.readDeclaredField(modelo, campo.getName(), true)))
                                ) {
                                    continue;
                                }
                                datos++;
                                if (datos > 1) {
                                    sql.append(", ");
                                }
                                sql.append(columnName);
                                values.add(campo);
                                indicemetodos.add(i);
                            }
                            sql.append(") VALUES (");
                            for (int i = 0; i < values.size(); i++) {
                                sql.append("?");
                                int temporal = values.size() - 1;
                                if (i < temporal) {
                                    sql.append(", ");
                                } else if (i == temporal) {
                                    sql.append(");");
                                }
                            }
                            if (modelo.getDataBaseType() == DataBase.SQLServer) {
                                // Obtener cual es la clave primaria de la tabla
                                int index = sql.indexOf(";");
                                String namePrimaryKey = modelo.getTabla().getClaveprimaria().getCOLUMN_NAME();
                                if (index != -1) {
                                    sql = sql.replace(index, index + 1, " SELECT * FROM " + modelo.getTableName() + " WHERE ");
                                }
                                int contador = 0;
                                for (Field columna : campos) {
                                    if (StringUtils.equalsIgnoreCase(namePrimaryKey, this.getColumnName(columna))) {
                                        if (contador > 0) {
                                            sql.append(" AND ");
                                        }
                                        sql.append(namePrimaryKey).append(" = SCOPE_IDENTITY()");
                                        contador++;
                                    }
                                    if ((this.getColumnIsIndexValidValue(this, columna))) {
                                        if (contador > 0) {
                                            sql.append(" AND ");
                                        }
                                        values.add(columna);
                                        sql.append(this.getColumnName(columna)).append(" = ?");
                                        contador++;
                                    }
                                }
                            } else if (modelo.getDataBaseType() == DataBase.MySQL) {
                                // Obtener cual es la clave primaria de la tabla
                                String namePrimaryKey = modelo.getTabla().getClaveprimaria().getCOLUMN_NAME();
                                sql2.append("SELECT * FROM ").append(modelo.getTableName()).append(" WHERE ");
                                int contador = 0;
                                for (Field columna : campos) {
                                    if (StringUtils.equalsIgnoreCase(namePrimaryKey, this.getColumnName(columna))) {
                                        if (contador > 0) {
                                            sql2.append(" AND ");
                                        }
                                        sql2.append(namePrimaryKey).append(" = LAST_INSERT_ID()");
                                        contador++;
                                    }
                                    if ((this.getColumnIsIndexValidValue(this, columna))) {
                                        if (contador > 0) {
                                            sql2.append(" AND ");
                                        }
                                        values2.add(columna);
                                        sql2.append(this.getColumnName(columna)).append(" = ?");
                                        contador++;
                                    }
                                }
                            } else {
                                // Reemplazar el punto y coma con la nueva cadena
                                int index = sql.indexOf(";");
                                if (index != -1) {
                                    sql.replace(index, index + 1, " RETURNING * ;");
                                }
                            }
                        }
                        PreparedStatement ejecutor = connect.prepareStatement(sql.toString());
                        //Llena el prepareStatement
                        LogsJB.debug("Llenara la información de las columnas: " + indicemetodos.size());
                        int auxiliar = 0;
                        Integer filas = 0;
                        if (values.size() > 0) {
                            for (Field value : values) {
                                auxiliar++;
                                String columnName = getColumnName(value);
                                if ((StringUtils.equalsIgnoreCase(columnName, this.getUpdateAT()))
                                        || (StringUtils.equalsIgnoreCase(columnName, this.getCreatedAt()))) {
                                    Long datetime = System.currentTimeMillis();
                                    FieldUtils.writeField(modelo, value.getName(), new Timestamp(datetime), true);
                                }
                                convertJavaToSQL(modelo, value, ejecutor, auxiliar);
                            }
                            LogsJB.info(ejecutor.toString());
                            if (modelo.getDataBaseType() == DataBase.MySQL && !modelo.getModelExist()) {
                                ejecutor.executeUpdate();
                                ejecutor = connect.prepareStatement(sql2.toString());
                                auxiliar = 0;
                                for (Field value : values2) {
                                    auxiliar++;
                                    convertJavaToSQL(modelo, value, ejecutor, auxiliar);
                                }
                                LogsJB.info(ejecutor.toString());
                                ResultSet registros = ejecutor.executeQuery();
                                if (registros.next()) {
                                    procesarResultSetOneResult(modelo, registros);
                                    filas++;
                                }
                            } else if (modelo.getModelExist()) {
                                filas = ejecutor.executeUpdate();
                            } else {
                                ResultSet registros = ejecutor.executeQuery();
                                if (registros.next()) {
                                    procesarResultSetOneResult(modelo, registros);
                                    filas++;
                                }
                            }
                        }
                        LogsJB.info("Filas afectadas en BD's': " + filas + " " + this.getTableName());
                        modelo.closeConnection(connect);
                        modelo.setTaskIsReady(true);
                        modelo.setModelExist(true);
                        return filas;
                    } catch (Exception e) {
                        LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                        modelo.setTaskIsReady(true);
                        return 0;
                    }
                });
            } else {
                LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo almacenar el Registro");
                modelo.setTaskIsReady(true);
                return CompletableFuture.completedFuture(0);
            }
        });
    }

    /**
     * Elimina la información del modelo proporcionado en BD's
     *
     * @param modelo Modelo del cual se desea eliminar la información en BD's
     * @param <T>    Expresión que hace que el metodo sea generico y pueda ser utilizado por cualquier
     *               objeto que herede la Clase JBSqlUtils
     * @return La cantidad de filas eliminadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    protected <T extends Methods_Conexion> CompletableFuture<Integer> deleteModel(T modelo) throws Exception {
        modelo.setTaskIsReady(false);
        return modelo.validarTableExist(modelo).thenCompose(v -> {
            if (modelo.getTableExist()) {
                return CompletableFuture.supplyAsync(() -> {
                    try (Connection connect = modelo.getConnection()) {
                        String namePrimaryKey = modelo.getTabla().getClaveprimaria().getCOLUMN_NAME();
                        StringBuilder sql = new StringBuilder("DELETE FROM ").append(modelo.getTableName());
                        List<Field> columnas = this.getFieldsOfModel();
                        List<Field> values = new ArrayList<>();
                        boolean whereAdded = false;
                        for (Field columna : columnas) {
                            if ((StringUtils.equalsIgnoreCase(namePrimaryKey, this.getColumnName(columna)) && !this.getValueColumnIsNull(this, columna))
                                    || (this.getColumnIsIndexValidValue(this, columna))) {
                                values.add(columna);
                                if (whereAdded) {
                                    sql.append(" AND ");
                                } else {
                                    sql.append(" WHERE ");
                                    whereAdded = true;
                                }
                                sql.append(this.getColumnName(columna)).append(" = ?");
                            }
                        }
                        sql.append(";");
                        PreparedStatement ejecutor = connect.prepareStatement(sql.toString());
                        int auxiliar = 0;
                        Integer filas = 0;
                        LogsJB.debug("Colocara la información del where: " + auxiliar);
                        if (!values.isEmpty()) {
                            for (Field value : values) {
                                auxiliar++;
                                convertJavaToSQL(this, value, ejecutor, auxiliar);
                            }
                            LogsJB.info(ejecutor.toString());
                            filas = ejecutor.executeUpdate();
                        }
                        LogsJB.info("Filas eliminadas: " + filas);
                        modelo.closeConnection(connect);
                        modelo.setTaskIsReady(true);
                        return filas;
                    } catch (Exception e) {
                        LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                        modelo.setTaskIsReady(true);
                        return 0;
                    }
                });
            } else {
                LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo eliminar el Registro " + this.getClass().getSimpleName());
                modelo.setTaskIsReady(true);
                return CompletableFuture.completedFuture(0);
            }
        });
    }

    /**
     * Obtiene una instancia nueva del tipo de modelo que se envía como parametro
     *
     * @param modelo Tipo de objeto que se desea instanciar
     * @param <T>    Expresión que hace que el metodo sea generico y pueda ser utilizado por cualquier
     *               objeto que herede la Clase JBSqlUtils
     * @return Retorna la nueva instancia del modelo creada
     */
    public <T extends Methods_Conexion> T obtenerInstanciaOfModel(T modelo) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        T temp;
        if (modelo.getGetPropertySystem()) {
            temp = (T) modelo.getClass().newInstance();
            temp.llenarPropertiesFromModel(temp);
        } else {
            Constructor constructor = modelo.getClass().getConstructor(Boolean.class);
            temp = (T) constructor.newInstance(false);
            temp.llenarPropertiesFromModel(modelo);
        }
        return temp;
    }

    /**
     * Valida si el modelo ya conoce si su tabla existe en BD's de lo contrario ejecuta este metodo para asegurarse que
     * obtenga la información de BD's
     *
     * @param modelo Modelo que se desea validar si su tabla existe en BD's
     * @param <T>
     * @throws Exception Si sucede una excepción en la ejecución de esta tarea la lanza al metodo que la invoco
     */
    protected <T extends Methods_Conexion> CompletableFuture<Void> validarTableExist(T modelo) throws Exception {
        if (!modelo.getTableExist()) {
            return modelo.tableExist().thenAccept(
                    exist -> modelo.setTableExist(exist)
            );
        }
        return CompletableFuture.completedFuture(null);
    }

    /**
     * Obtiene un nuevo modelo del tipo de modelo proporcionado para procesar el ResultSet
     * con la información de BD's
     *
     * @param modelo    Modelo que invoca el metodo.
     * @param registros Resulset que contiene la información obtenida de BD's
     * @param <T>       Expresión que hace que el metodo sea generico y pueda ser utilizado por cualquier
     *                  objeto que herede la Clase JBSqlUtils
     * @return Retorna un nuevo modelo del tipo de modelo proporcionado para procesar el ResultSet
     * con la información de BD's
     * @throws InstantiationException    Lanza esta excepción si ocurre un error al crear una nueva instancia
     *                                   del tipo de modelo proporcionado
     * @throws SQLException              Lanza esta excepción de suceder algún problema con el ResultSet
     * @throws InvocationTargetException Lanza esta excepción si hubiera algún problema al invocar el metodo Set
     * @throws IllegalAccessException    Lanza esta excepción si hubiera algún problema al invocar el metodo Set
     * @throws DataBaseUndefind          Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                                   BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined     Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                                   propiedades de conexión necesarias para conectarse a la BD's especificada.
     * @throws NoSuchMethodException     Lanza esta excepción si el modelo no posee el metodo que se invocara
     */
    protected <T extends Methods_Conexion> T procesarResultSet(T modelo, ResultSet registros) throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException, DataBaseUndefind, PropertiesDBUndefined, NoSuchMethodException {
        T temp = modelo.obtenerInstanciaOfModel(modelo);
        temp.setTabla(modelo.getTabla());
        temp.setTableExist(modelo.getTableExist());
        temp.setTableName(modelo.getTableName());
        temp.setModelExist(true);
        LogsJB.debug("Obtuvo un resultado de BD's, procedera a llenar el modelo " + temp.getClass().getSimpleName());
        LogsJB.trace("obtuvo los métodos set");
        LogsJB.debug("Cantidad de columnas : " + temp.getTabla().getColumnas().size());
        List<Field> fields = new ArrayList<>(temp.getFieldsOfModel());
        LogsJB.trace("Inicializa el array list de los campos del modelo");
        LogsJB.debug("Cantidad de campos set: " + fields.size());
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : fields) {
            String fieldName = getColumnName(field);
            fieldMap.put(fieldName.toLowerCase(), field);
        }
        // Llena la información del modelo
        for (ColumnsSQL columna : temp.getTabla().getColumnas()) {
            String columnName = columna.getCOLUMN_NAME();
            LogsJB.trace("Columna : " + columnName);
            // Buscar el campo en el mapa
            Field field = fieldMap.get(columnName.toLowerCase());
            if (field != null) {
                LogsJB.trace("Nombre de la columna, nombre del campo a setear el valor: " + columnName + "   " + field.getName());
                convertSQLtoJava(columna, registros, field, temp);
            }
        }
        return temp;
    }

    /**
     * Llena el modelo proporcionado con la Información Obtenida de BD's
     *
     * @param modelo    Modelo que invoca el metodo.
     * @param registros Resulset que contiene la información obtenida de BD's
     * @throws InstantiationException    Lanza esta excepción si ocurre un error al crear una nueva instancia
     *                                   del tipo de modelo proporcionado
     * @throws SQLException              Lanza esta excepción de suceder algún problema con el ResultSet
     * @throws InvocationTargetException Lanza esta excepción si hubiera algún problema al invocar el metodo Set
     * @throws IllegalAccessException    Lanza esta excepción si hubiera algún problema al invocar el metodo Set
     */
    protected <T extends Methods_Conexion> void procesarResultSetOneResult(T modelo, ResultSet registros) throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
        modelo.setModelExist(true);
        LogsJB.debug("Obtuvo un resultado de BD's, procedera a llenar el modelo " + modelo.getTableName());
        LogsJB.debug("Cantidad de columnas : " + modelo.getTabla().getColumnas().size());
        // Cachear los campos y crear un mapa para búsqueda rápida
        List<Field> fields = new ArrayList<>(modelo.getFieldsOfModel());
        LogsJB.trace("Inicializa el array list de los campos del modelo");
        LogsJB.debug("Cantidad de campos set: " + fields.size());
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : fields) {
            String fieldName = getColumnName(field);
            fieldMap.put(fieldName.toLowerCase(), field);
        }
        //Llena la información del modelo
        for (ColumnsSQL columna : modelo.getTabla().getColumnas()) {
            String columnName = columna.getCOLUMN_NAME();
            LogsJB.trace("Columna : " + columnName);
            // Buscar el campo en el mapa
            Field field = fieldMap.get(columnName.toLowerCase());
            if (field != null) {
                LogsJB.trace("Nombre de la columna, nombre del campo a setear el valor: " + columnName + "   " + field.getName());
                convertSQLtoJava(columna, registros, field, modelo);
            }
        }
    }

    /**
     * Obtiene un Json Object con las columnas solicitadas como propiedades del json con sus respectivos valores
     *
     * @param registros      ResultSet del cual se obtendran los valores de las columnas
     * @param columnMetadata List of ColumnsSQL containing metadata of the columns
     * @return Retorna un Json Object con las columnas solicitadas como propiedades del json con sus respectivos valores
     * @throws SQLException Lanza esta excepción si sucede algún error al obtener el valor de cada una de las columnas solicitadas
     */
    protected JSONObject procesarResultSetJSON(ResultSet registros, List<ColumnsSQL> columnMetadata) throws SQLException {
        JSONObject temp = new JSONObject();
        LogsJB.debug("Obtuvo un resultado de BD's, procedera a llenar el JSON");
        for (ColumnsSQL columna : columnMetadata) {
            String columnName = columna.getCOLUMN_NAME();
            String columnType = columna.getTYPE_NAME();
            LogsJB.trace("Columna : " + columnName);
            LogsJB.trace("Tipo de dato : " + columnType);
            this.convertSQLtoJson(columna, registros, temp);
        }
        return temp;
    }

    /**
     * Crea la tabla correspondiente al modelo en BD's si esta no existe.
     *
     * @return True si la tabla correspondiente al modelo en BD's no existe y fue creada exitosamente,
     * False si la tabla correspondiente al modelo ya existe en BD's
     * @throws Exception Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    public Boolean createTable() throws Exception {
        return this.createTableCompletableFuture().get();
    }

    /**
     * Carla: Metodo que devuelve un completable Booleano
     * Crea la tabla correspondiente al modelo en BD's si esta no existe.
     *
     * @return True si la tabla correspondiente al modelo en BD's no existe y fue creada exitosamente,
     * False si la tabla correspondiente al modelo ya existe en BD's
     * @throws Exception Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    public CompletableFuture<Boolean> createTableCompletableFuture() throws Exception {
        return tableExist().thenCompose(exists -> {
            if (exists) {
                LogsJB.info("La tabla correspondiente al modelo ya existe en la BD's, por lo cual no será creada.");
                return CompletableFuture.completedFuture(false);
            } else {
                return CompletableFuture.supplyAsync(() -> {
                    StringBuilder sql = new StringBuilder("CREATE TABLE ").append(this.getTableName()).append("(");
                    List<Field> fields;
                    List<ForeignKey> foreignKeys = new ArrayList<>();
                    Connection connect = null;
                    Statement ejecutor = null;
                    try {
                        fields = this.getFieldsOfModel().stream()
                                .filter(field -> !Objects.isNull(getDataTypeSQL(field)))
                                .sorted(Comparator.comparingInt(field -> getDataTypeSQL(field).getOrden()))
                                .collect(Collectors.toList());
                        int datos = 0;
                        for (Field campo : fields) {
                            String columnName = getColumnName(campo);
                            DataType columnType = getDataTypeSQL(campo);
                            ForeignKey temp = getForeignKey(campo);
                            if (!Objects.isNull(temp)) {
                                foreignKeys.add(temp);
                            }
                            if (columnType == DataType.TIMESTAMP && this.getDataBaseType() == DataBase.SQLServer) {
                                columnType = DataType.DATETIME;
                            }
                            Constraint[] columnRestriccion = getConstraints(campo);
                            String restricciones = "";
                            String defaultValue = getColumnDefaultValue(campo);
                            String size = getSize(campo);
                            if (Arrays.asList(DataBase.PostgreSQL, DataBase.MySQL, DataBase.SQLite, DataBase.MariaDB).contains(this.getDataBaseType())
                                    && columnType == DataType.BIT) {
                                columnType = DataType.BOOLEAN;
                            }
                            if (this.getDataBaseType() == DataBase.SQLServer && columnType == DataType.BOOLEAN) {
                                columnType = DataType.BIT;
                            }
                            if (this.getDataBaseType() == DataBase.PostgreSQL && columnType == DataType.DOUBLE) {
                                size = "";
                            }
                            if (this.getDataBaseType() == DataBase.SQLServer && columnType == DataType.DOUBLE) {
                                columnType = DataType.REAL;
                            }
                            if (this.getDataBaseType() == DataBase.MySQL && (columnType == DataType.TEXT || columnType == DataType.JSON)) {
                                defaultValue = null;
                            }
                            if (this.getDataBaseType() == DataBase.SQLServer && columnType == DataType.BIT && !stringIsNullOrEmpty(defaultValue)) {
                                defaultValue = "" + getIntFromBoolean(Boolean.valueOf(defaultValue));
                            }
                            String tipo_de_columna = stringIsNullOrEmpty(size) ? columnType.name() : columnType.name() + "(" + size + ")";
                            if (this.getDataBaseType() == DataBase.PostgreSQL && columnType == DataType.DOUBLE) {
                                tipo_de_columna = tipo_de_columna.replace("DOUBLE", "DOUBLE PRECISION");
                            }
                            if (!Objects.isNull(columnRestriccion)) {
                                for (Constraint restriccion : columnRestriccion) {
                                    if (this.getDataBaseType() == DataBase.PostgreSQL && restriccion == Constraint.AUTO_INCREMENT) {
                                        tipo_de_columna = DataType.SERIAL.name();
                                    } else if (this.getDataBaseType() == DataBase.SQLServer && restriccion == Constraint.AUTO_INCREMENT) {
                                        restricciones += DataType.IDENTITY + " ";
                                    } else if (this.getDataBaseType() == DataBase.SQLite && restriccion == Constraint.AUTO_INCREMENT) {
                                        restricciones = restricciones;
                                    } else if (restriccion == Constraint.DEFAULT && stringIsNullOrEmpty(defaultValue)) {
                                        continue;
                                    } else if (restriccion == Constraint.DEFAULT && !stringIsNullOrEmpty(defaultValue)) {
                                        restricciones += restriccion.getRestriccion() + " " + defaultValue + " ";
                                    } else {
                                        restricciones += restriccion.getRestriccion() + " ";
                                    }
                                }
                            }
                            if (!this.getTimestamps() && (StringUtils.equalsIgnoreCase(columnName, this.getCreatedAt())
                                    || StringUtils.equalsIgnoreCase(columnName, this.getUpdateAT()))) {
                                continue;
                            }
                            if (datos++ > 0) {
                                sql.append(", ");
                            }
                            sql.append(columnName).append(" ").append(tipo_de_columna).append(" ").append(restricciones);
                        }
                        for (ForeignKey foreignKey : foreignKeys) {
                            sql.append(", FOREIGN KEY (").append(foreignKey.columName()).append(") REFERENCES ")
                                    .append(foreignKey.tableReference()).append("(").append(foreignKey.columnReference()).append(")");
                            for (Actions accion : foreignKey.actions()) {
                                sql.append(accion.operacion().getOperador()).append(accion.action().getOperacion());
                            }
                        }
                        sql.append(");");
                        connect = this.getConnection();
                        ejecutor = connect.createStatement();
                        LogsJB.info(sql.toString());
                        if (!ejecutor.execute(sql.toString())) {
                            LogsJB.info("Sentencia para crear tabla de la BD's ejecutada exitosamente");
                            LogsJB.info("Tabla " + this.getTableName() + " Creada exitosamente");
                            this.setTableExist(true);
                            this.refresh();
                            return true;
                        }
                    } catch (Exception e) {
                        LogsJB.fatal("Excepción disparada en el método que Crea la tabla correspondiente al modelo, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                        return false;
                    } finally {
                        this.closeConnection(connect);
                    }
                    return false;
                });
            }
        });
    }

    /**
     * Metodo Original
     * Elimina la tabla correspondiente al modelo en BD's
     *
     * @return True si la tabla correspondiente al modelo en BD's existe y fue eliminada, de no existir la tabla correspondiente
     * en BD's retorna False.
     * @throws Exception Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    public Boolean dropTableIfExist() throws Exception {
        return this.dropTableIfExistCompletableFuture().get();
    }

    /**
     * Carla: Metodo que devuelve un completable Booleano
     * Elimina la tabla correspondiente al modelo en BD's
     *
     * @return True si la tabla correspondiente al modelo en BD's existe y fue eliminada, de no existir la tabla correspondiente
     * en BD's retorna False.
     * @throws Exception Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    public CompletableFuture<Boolean> dropTableIfExistCompletableFuture() throws Exception {
        return tableExist().thenCompose(exists -> {
            if (exists) {
                return CompletableFuture.supplyAsync(() -> {
                    StringBuilder sql = new StringBuilder();
                    Connection connect = null;
                    Statement ejecutor = null;
                    try {
                        if (this.getDataBaseType() == DataBase.SQLServer) {
                            sql.append("if exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '")
                                    .append(this.getTableName())
                                    .append("' AND TABLE_SCHEMA = 'dbo')\n")
                                    .append("    drop table dbo.")
                                    .append(this.getTableName());
                        } else {
                            sql.append("DROP TABLE IF EXISTS ").append(this.getTableName());
                        }
                        LogsJB.info(sql.toString());
                        connect = this.getConnection();
                        ejecutor = connect.createStatement();
                        if (!ejecutor.execute(sql.toString())) {
                            LogsJB.info("Sentencia para eliminar tabla de la BD's ejecutada exitosamente");
                            LogsJB.info("Tabla " + this.getTableName() + " Eliminada exitosamente");
                            this.setTableExist(false);
                            this.refresh();
                            return true;
                        }
                    } catch (Exception e) {
                        LogsJB.fatal("Excepción disparada en el método que Elimina la tabla correspondiente al modelo, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                        return false;
                    } finally {
                        this.closeConnection(connect);
                    }
                    return false;
                });
            } else {
                LogsJB.info("Tabla correspondiente al modelo no existe en BD's por eso no pudo ser eliminada");
                return CompletableFuture.completedFuture(false);
            }
        });
    }

    /**
     * Crea la tabla solicitada correspondiente al modelo con las columnas especificadas como parametro
     *
     * @param columnas Lista de columnas que se desea sean creadas por JBSqlUtils
     * @return Retorna True si logra crear la tabla, False en caso que la tabla ya exista en BD's o que
     * haya sucedido un error al momento de ejecutar la sentencia SQL
     * @throws Exception Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    protected Boolean crateTableJSON(List<Column> columnas) throws Exception {
        CompletableFuture<ResultAsync<Boolean>> future = tableExist().thenCompose(exists -> {
            if (exists) {
                LogsJB.info("La tabla correspondiente al modelo ya existe en la BD's, por lo cual no será creada.");
                return CompletableFuture.completedFuture(new ResultAsync<>(false, null));
            } else {
                return CompletableFuture.supplyAsync(() -> {
                    StringBuilder sql = new StringBuilder("CREATE TABLE ").append(this.getTableName()).append("(");
                    Connection connect = null;
                    Statement ejecutor = null;
                    try {
                        LogsJB.debug("Comienza a ordenar la lista");
                        columnas.sort((columna1, columna2) -> {
                            try {
                                LogsJB.trace("Columnas a evaluar: " + columna1.getName() + "  " + columna2.getName());
                                if (columna1.getDataTypeSQL().getOrden() > columna2.getDataTypeSQL().getOrden()) {
                                    LogsJB.trace("Columna de metodo 1 es mayor");
                                    return 1;
                                } else if (columna2.getDataTypeSQL().getOrden() > columna1.getDataTypeSQL().getOrden()) {
                                    LogsJB.trace("Columna de metodo 2 es mayor");
                                    return -1;
                                } else {
                                    LogsJB.trace("Columnas son iguales");
                                    return 0;
                                }
                            } catch (Exception e) {
                                LogsJB.fatal("Excepción disparada al tratar de ordenar los metodos get de la lista, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                            }
                            return 0;
                        });
                        LogsJB.debug("Termino de ordenar la lista");
                        int datos = 0;
                        for (Column columnsSQL : columnas) {
                            String columnName = columnsSQL.getName();
                            DataType columnType = columnsSQL.getDataTypeSQL();
                            if ((columnType == DataType.TIMESTAMP) && (this.getDataBaseType() == DataBase.SQLServer)) {
                                columnType = DataType.DATETIME;
                                columnsSQL.setDataTypeSQL(DataType.DATETIME);
                            }
                            Constraint[] columnRestriccion = columnsSQL.getRestriccion();
                            String restricciones = "";
                            if (((this.getDataBaseType() == DataBase.PostgreSQL) || (this.getDataBaseType() == DataBase.MySQL) || (this.getDataBaseType() == DataBase.SQLite) || (this.getDataBaseType() == DataBase.MariaDB)) && (columnType == DataType.BIT)) {
                                columnsSQL.setDataTypeSQL(DataType.BOOLEAN);
                            }
                            if ((this.getDataBaseType() == DataBase.SQLServer) && columnType == DataType.BOOLEAN) {
                                columnsSQL.setDataTypeSQL(DataType.BIT);
                            }
                            String tipo_de_columna = columnsSQL.columnToString();
                            if (!Objects.isNull(columnRestriccion)) {
                                for (Constraint restriccion : columnRestriccion) {
                                    if ((DataBase.PostgreSQL == this.getDataBaseType()) && (restriccion == Constraint.AUTO_INCREMENT)) {
                                        tipo_de_columna = DataType.SERIAL.name();
                                    } else if ((DataBase.SQLServer == this.getDataBaseType()) && (restriccion == Constraint.AUTO_INCREMENT)) {
                                        restricciones = restricciones + DataType.IDENTITY + " ";
                                    } else if ((DataBase.SQLite == this.getDataBaseType()) && (restriccion == Constraint.AUTO_INCREMENT)) {
                                        restricciones = restricciones;
                                    } else if (restriccion == Constraint.DEFAULT) {
                                        restricciones = restricciones + restriccion.getRestriccion() + " " + columnsSQL.getDefault_value() + " ";
                                    } else {
                                        restricciones = restricciones + restriccion.getRestriccion() + " ";
                                    }
                                }
                            }
                            if ((!this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, "created_at")) || (StringUtils.equalsIgnoreCase(columnName, "updated_at")))) {
                                continue;
                            }
                            String columna = columnName + " " + tipo_de_columna + " " + restricciones;
                            datos++;
                            if (datos > 1) {
                                sql.append(", ");
                            }
                            sql.append(columna);
                        }
                        sql.append(");");
                        connect = this.getConnection();
                        ejecutor = connect.createStatement();
                        LogsJB.info(sql.toString());
                        if (!ejecutor.execute(sql.toString())) {
                            LogsJB.info("Sentencia para crear tabla de la BD's ejecutada exitosamente");
                            LogsJB.info("Tabla " + this.getTableName() + " Creada exitosamente");
                            this.refresh();
                            return new ResultAsync<>(true, null);
                        }
                    } catch (Exception e) {
                        LogsJB.fatal("Excepción disparada en el método que Crea la tabla solicitada, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                        return new ResultAsync<>(false, e);
                    } finally {
                        this.closeConnection(connect);
                    }
                    return new ResultAsync<>(false, null);
                });
            }
        });
        ResultAsync<Boolean> resultado = future.join();
        if (!Objects.isNull(resultado.getException())) {
            throw resultado.getException();
        }
        return resultado.getResult();
    }

    /**
     * Llena las propiedades de conexión de un modelo desde otro
     *
     * @param proveedor Modelo desde el que se obtendran las propiedades de conexión
     * @param <T>       Modelo a llenar
     * @param <G>       Tipo de dato del invocador
     */
    public <T extends Methods_Conexion, G extends Methods_Conexion> void llenarPropertiesFromModel(G proveedor) {
        try {
            this.setGetPropertySystem(proveedor.getGetPropertySystem());
            // Crear un set con los nombres de los métodos que queremos filtrar
            Set<String> metodosProveedorNombres = Set.of("getDataBaseType", "getHost", "getPort", "getUser", "getPassword", "getBD", "getPropertisURL");
            Set<String> metodosReciberNombres = Set.of("setDataBaseType", "setHost", "setPort", "setUser", "setPassword", "setBD", "setPropertisURL");
            // Obtener los métodos de la clase actual y del proveedor
            Method[] metodosClaseActual = this.getClass().getMethods();
            Method[] metodosProveedor = proveedor.getClass().getMethods();
            // Crear un mapa para emparejar los métodos
            // Crear un mapa para emparejar los métodos
            Map<String, Method> metodosReciberMap = Arrays.stream(metodosClaseActual)
                    .filter(metodo -> metodosReciberNombres.contains(metodo.getName()) && metodo.getParameterCount() == 1)
                    .collect(Collectors.toMap(
                            metodo -> StringUtils.removeStartIgnoreCase(metodo.getName(), "set"),
                            metodo -> metodo
                    ));
            // Iterar sobre los métodos del proveedor y emparejar con los métodos del recibidor
            for (Method metodoProveedor : metodosProveedor) {
                String nombreMetodo = metodoProveedor.getName();
                if (metodosProveedorNombres.contains(nombreMetodo)) {
                    String nombreMetodoProveedor = StringUtils.removeStartIgnoreCase(nombreMetodo, "get");
                    Method metodoReciber = metodosReciberMap.get(nombreMetodoProveedor);
                    if (metodoReciber != null) {
                        // Llena el recibidor con la información de las propiedades de conexión
                        metodoReciber.invoke(this, metodoProveedor.invoke(proveedor));
                    }
                }
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al llenar el modelo, con la info del modelo proporcionado, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * en esta funcion se obtiene el nombre de la columna de un campo,
     * si tiene anotacion el campo, va devolver el name
     * si la anotacion es null o vacio, devolvera el nombre del campo
     *
     * @param field maneja un parametro de tipo objeto field
     * @return retorna el nombre de la columna estipulado en el if
     */
    private String getColumnName(Field field) {
        //Obtengo la información de la columna
        ColumnDefined columnDefined = field.getAnnotation(ColumnDefined.class);
        if ((Objects.isNull(columnDefined)) || stringIsNullOrEmpty(columnDefined.name())) {
            //No tiene anotación o el valor seteado
            return field.getName();
        } else {
            //Tiene anotación
            return columnDefined.name();
        }
    }

    /**
     * este metodo verifica si tiene anotacion index es true y si es null es false.
     *
     * @param field parametro tipo objeto field
     * @return retorna una columna
     */
    private Boolean getColumnIsIndex(Field field) {
        //Obtiene si la columna es un Index
        return !Objects.isNull(field.getAnnotation(Index.class));
    }

    /**
     * este metodo comprueba que los campos que vienen tengan indices de valor adecuado.
     *
     * @param model parametro de la entidad que contiene los datos model
     * @param field parametro field que es de tipo objeto
     * @return retorna la columana q es tipo inde y si es nula
     * @throws IllegalAccessException maneja la ecepcion cuando intentan acceder a un campo privado
     */
    private Boolean getColumnIsIndexValidValue(Object model, Field field) throws IllegalAccessException {
        //Obtiene si la columna es un Index
        return this.getColumnIsIndex(field) && !this.getValueColumnIsNull(model, field);
    }

    /**
     * este metodo se encarga de verificar si el valor de un campo especifico de un model es null
     *
     * @param model parametor del objeto donde se quiere leer el campo
     * @param field parametro del nombre del campo que se desea leer
     * @return retorna un valor, donde verifica si el valor es null
     * @throws IllegalAccessException
     */
    private Boolean getValueColumnIsNull(Object model, Field field) throws IllegalAccessException {
        //Obtiene si la columna es un Index
        return Objects.isNull(FieldUtils.readDeclaredField(model, field.getName(), true));
    }

    /**
     * este metodo se utiliza para obtener el valor de un campo especifico de un objeto,
     * donde la diferencia es que este campo puede ser privado.
     *
     * @param model es el objeto desde el cual se leera el valor.
     * @param field es el parametro atributo del cual se recupera el valor.
     * @return retorna el nombre obtenido
     * @throws IllegalAccessException maneja la ecepcion
     */
    private Object getValueColumn(Object model, Field field) throws IllegalAccessException {
        //Obtiene si la columna es un Index
        return FieldUtils.readDeclaredField(model, field.getName(), true);
    }

    /**
     * Este metodo permite determinar el valor inicial o por defecto de un campo que esta
     * siendo mapeado a una BD.
     *
     * @param field es el campo del cual se recupera el valor.
     * @return retorna la columana definida con una cadena string
     */
    private String getColumnDefaultValue(Field field) {
        //Obtengo la información de la columna
        ColumnDefined columnDefined = field.getAnnotation(ColumnDefined.class);
        //Tiene anotación
        if (Objects.isNull(columnDefined)) {
            return null;
        }
        return columnDefined.default_value();
    }

    /**
     * con este metodo se logra conocer las restricciones de tamaño de un campo que esta siendo mapeado.
     *
     * @param field campo tipo field del cual se desea obtener el tamaño
     * @return retorna una cadena string
     */
    private String getSize(Field field) {
        //Obtengo la información de la columna
        ColumnDefined columnDefined = field.getAnnotation(ColumnDefined.class);
        //Tiene anotación
        if (Objects.isNull(columnDefined)) {
            return null;
        }
        return columnDefined.size();
    }

    /**
     * este metodo es utilizado para obtener las restricciones definidas para determinada columna
     * donde denota la anotacion ColumnDefined
     *
     * @param field campo del cual se desea obtener las restricciones
     * @return si el campo no tiene anotacion, retorna null
     */
    private Constraint[] getConstraints(Field field) {
        //Obtengo la información de la columna
        ColumnDefined columnDefined = field.getAnnotation(ColumnDefined.class);
        //Tiene anotación
        if (Objects.isNull(columnDefined)) {
            return null;
        }
        return columnDefined.constraints();
    }

    /**
     * este metodo se utiliza para generar una cadena que representa el tipo de dato SQL,
     *
     * @param field es el campo del cual se desea obtener el dato SQL como cadena
     * @return retorna el tipo de dato, en uno sin importar el tamaño y el otro return si retorna el tamaño de la cadena.
     */
    private String getDataTypeSQLToString(Field field) {
        //Obtengo la información de la columna
        ColumnDefined columnDefined = field.getAnnotation(ColumnDefined.class);
        //Tiene anotación
        if (Objects.isNull(columnDefined) || Objects.isNull(columnDefined.dataTypeSQL())) {
            return null;
        }
        DataType dataType = columnDefined.dataTypeSQL();
        if (stringIsNullOrEmpty(this.getSize(field))) {
            return dataType.name();
        } else {
            return dataType.name() + "(" + this.getSize(field) + ")";
        }
    }

    /**
     * este metodo obtiene el tipo de dato SQL de un campo,
     *
     * @param field es el campo del cual se desea obtener el tipo de dato
     * @return retorna un objeto tipo datatype
     */
    private DataType getDataTypeSQL(Field field) {
        //Obtengo la información de la columna
        ColumnDefined columnDefined = field.getAnnotation(ColumnDefined.class);
        //Tiene anotación
        if (Objects.isNull(columnDefined) || Objects.isNull(columnDefined.dataTypeSQL())) {
            return null;
        }
        return columnDefined.dataTypeSQL();
    }

    /**
     * este metodo devuelve la informacion sobre la clave foranea que se encuentra definida
     * en un campo especifico.
     *
     * @param field campo del cual se desean obtener los datos
     * @return rertorna null si no encuentra anotacion sobre la llave foranea
     */
    private ForeignKey getForeignKey(Field field) {
        //Obtengo la información de la columna
        ColumnDefined columnDefined = field.getAnnotation(ColumnDefined.class);
        //Tiene anotación
        if (Objects.isNull(columnDefined) || stringIsNullOrEmpty(columnDefined.foreignkey().columName())
                || stringIsNullOrEmpty(columnDefined.foreignkey().tableReference())
                || stringIsNullOrEmpty(columnDefined.foreignkey().columnReference())) {
            return null;
        }
        return columnDefined.foreignkey();
    }

    /**
     * Ordena la consula sql de acuerdo al estandar de consulta SQL
     *
     * @param query  Consulta SQL que se desea ordenar
     * @param modelo Es el invocador de los metodos que se estan utilizando
     * @param <T>
     * @return Retorna un string que representa la consulta SQL ordenada
     * @throws DataBaseUndefind Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                          el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                          esta excepción, poder manejarla.
     */
    protected <T extends Methods_Conexion> String generateOrderSQL(String query, T modelo) throws DataBaseUndefind {
        //Si es sql server y trae la palabra limit verificara y modificara la sentencia
        if (modelo.getDataBaseType() == DataBase.SQLServer) {
            if (StringUtils.containsIgnoreCase(query, "LIMIT")) {
                String temporal_limite = StringUtils.substringAfterLast(query, "LIMIT").replace(";", "").trim();
                String select = "SELECT TOP " + temporal_limite + " * FROM ";
                query = query.replace("SELECT * FROM ", select).replace("LIMIT " + temporal_limite, "");
                LogsJB.debug("Se modifico la sentencia SQL para que unicamente obtenga la cantidad de " +
                        "registros especificados por el usuario: " + query);
            }
        }
        // Insertar la cláusula GROUP BY en la posición correcta
        int whereIndex = query.indexOf(" WHERE ");
        int havingIndex = query.indexOf(" HAVING ");
        int orderByIndex = query.indexOf(" ORDER BY ");
        int limitIndex = query.indexOf(" LIMIT ");
        int groupByIndex = query.indexOf(" GROUP BY ");
        int groupByEndIndex = query.indexOf(";", groupByIndex);
        if (groupByIndex != -1 && groupByEndIndex != -1) {
            String groupByClause = query.substring(groupByIndex, groupByEndIndex + 1);
            query = query.substring(0, groupByIndex) + query.substring(groupByEndIndex + 1);
            if (whereIndex != -1) {
                query = query.substring(0, whereIndex + 7) + groupByClause + query.substring(whereIndex + 7);
            } else if (havingIndex != -1) {
                query = query.substring(0, havingIndex) + groupByClause + query.substring(havingIndex);
            } else if (orderByIndex != -1) {
                query = query.substring(0, orderByIndex) + groupByClause + query.substring(orderByIndex);
            } else if (limitIndex != -1) {
                query = query.substring(0, limitIndex) + groupByClause + query.substring(limitIndex);
            } else {
                query = query + groupByClause;
            }
        }
        return query;
    }
}

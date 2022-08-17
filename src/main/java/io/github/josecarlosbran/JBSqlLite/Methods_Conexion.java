package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ConexionUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Utilities.ColumnsSQL;
import io.github.josecarlosbran.JBSqlLite.Utilities.PrimaryKey;
import io.github.josecarlosbran.JBSqlLite.Utilities.TablesSQL;
import io.github.josecarlosbran.LogsJB.LogsJB;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Methods_Conexion extends Conexion {


    /**
     * Constructor de la clase Conexión que se encarga de inicializar las propiedades de conexión del modelo,
     * las cuales las obtiene de las propiedades del sistema Java.
     *
     * @throws DataBaseUndefind      Lanza esta excepción si el tipo de BD's a la cual se conectara el modelo no ha sido definida entre
     *                               las propiedades del sistema Java.
     * @throws PropertiesDBUndefined Lanza esta excepción si las propiedades de conexión no han sido definidas.
     */
    public Methods_Conexion() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }


    /**
     * Obtiene la lista de metodos pertenecientes al modelo que lo invoca.
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna una lista de los metodos pertenecientes al modelo.
     */
    public <T> List<Method> getMethodsModel() {
        Method[] metodos = this.getClass().getMethods();
        List<Method> result = new ArrayList<>();
        // Los muestro en consola
        for (Method metodo : metodos) {
            String clase = metodo.getDeclaringClass().getSimpleName();
            String returntype = metodo.getReturnType().getSimpleName();

            if ((clase.equals("Object") || clase.equals("Conexion") || clase.equals("Methods") || clase.equals("JBSqlUtils")) && !returntype.equals("Column")) {

            } else {
                //System.out.println(metodo.getName() + "   " + metodo.getDeclaringClass() + "  " + returntype);
                result.add(metodo);
            }
            //System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+returntype);
        }
        return result;
    }

    public <T> Method getMethodModel(String nombre) throws NoSuchMethodException {
        Method metodo = this.getClass().getMethod(nombre);
        // Los muestro en consola
        //System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+returntype);

        return metodo;
    }

    //Obtener unicamente los metodos get del modelo

    /**
     * Obtiene la lista de los metodos get del modelo que lo invoca.
     *
     * @param metodos Lista de metodos que tiene el modelo, sobre los cuales se filtraran los metodos get.
     * @param <T>     Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Lista de los metodos get del modelo que lo invoca.
     */
    public <T> List<Method> getMethodsGetOfModel(List<Method> metodos) {
        // Los muestro en consola
        int i = 0;
        List<Method> result = metodos;
        while (i < result.size()) {
            Method metodo = result.get(i);
            String returntype = metodo.getReturnType().getSimpleName();
            String nombre = metodo.getName();
            if (returntype.equals("Column") && StringUtils.containsIgnoreCase(nombre, "Get")) {
                i++;
            } else {
                //System.out.println(metodo.getName() + "   " + metodo.getDeclaringClass() + "  " + returntype);
                result.remove(i);
            }
        }
        return result;
    }

    /**
     * Obtiene la lista de los metodos set del modelo que lo invoca.
     *
     * @param metodos Lista de metodos que tiene el modelo, sobre los cuales se filtraran los metodos set.
     * @param <T>     Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Lista de los metodos set del modelo que lo invoca.
     */
    public <T> List<Method> getMethodsSetOfModel(List<Method> metodos) {
        List<Method> result = metodos;
        int i = 0;
        while (i < result.size()) {
            Method metodo = result.get(i);
            Parameter[] parametros = metodo.getParameters();
            String ParametroType = "";
            String nombre = metodo.getName();
            //System.out.println(metodo.getName() + "   " + metodo.getDeclaringClass() + "  " + ParametroType);
            if (StringUtils.containsIgnoreCase(nombre, "Set")) {
                ParametroType = parametros[0].getType().getSimpleName();
                //System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+ParametroType);
                if (ParametroType.equals("Column")) {
                    if (parametros.length >= 1) {
                        //System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+ParametroType);
                        i++;
                    }else {
                        result.remove(i);
                    }
                }else {
                    result.remove(i);
                }
            } else {
                result.remove(i);
            }
        }
        return result;
    }

    /**
     * Obtiene la conexión del modelo a la BD's con las propiedades definidas.
     *
     * @return Retorna la conexión del modelo a la BD's con las propiedades definidas.
     */
    public Connection getConnection() {
        Connection connect = null;
        try {
            String url = null;
            if (this.getDataBaseType() == DataBase.PostgreSQL) {
                //Carga el controlador de PostgreSQL
                url = null;
                connect = null;
                Class.forName("org.postgresql.Driver");
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + "/" + this.getBD();
                String usuario = this.getUser();
                String password = this.getPassword();
                connect = DriverManager.getConnection(url, usuario, password);
            } else if (this.getDataBaseType() == DataBase.MySQL) {
                url = null;
                connect = null;
                //Carga el controlador de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + "/" + this.getBD();
                String usuario = this.getUser();
                String password = this.getPassword();
                connect = DriverManager.getConnection(url, usuario, password);
            } else if (this.getDataBaseType() == DataBase.SQLServer) {
                url = null;
                connect = null;
                //Carga el controlador de SQLServer
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + ";databaseName=" + this.getBD() + ";TrustServerCertificate=True";
                String usuario = this.getUser();
                String password = this.getPassword();
                connect = DriverManager.getConnection(url, usuario, password);
            } else if (this.getDataBaseType() == DataBase.SQLite) {
                url = null;
                connect = null;
                url = "jdbc:" + this.getDataBaseType().getDBType() + ":" + this.getBD();
                connect = DriverManager.getConnection(url);
            }

            if (!Objects.isNull(connect)) {
                LogsJB.info("Conexión a BD's " + this.getBD() + " Realizada exitosamente");
                //this.setConnect(connect);
                //tableExist(connect);
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
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
                LogsJB.info("Conexión a BD's cerrada");
            } else {
                LogsJB.info("Conexión a BD's ya estaba cerrada");
            }
        } catch (ConexionUndefind e) {
            LogsJB.warning("El modelo no estaba conectado a la BD's por lo cual no se cerrara la conexión");
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada cerrar la conexión a la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Cierra la conexión a BD's del modelo.
     */
    public synchronized void closeConnection() {
        try {
            if (!this.getConnect().isClosed()) {
                this.getConnect().close();
                LogsJB.info("Conexión a BD's cerrada");
            } else {
                LogsJB.info("Conexión a BD's ya estaba cerrada");
            }
        } catch (ConexionUndefind e) {
            LogsJB.warning("El modelo no estaba conectado a la BD's por lo cual no se cerrara la conexión");
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada cerrar la conexión a la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Verifica la existencia de la tabla correspondiente al modelo en BD's
     *
     * @return True si la tabla correspondiente al modelo existe en BD's, de lo contrario False.
     */
    protected Boolean tableExist() {
        Boolean result = false;
        try {
            Callable<Boolean> VerificarExistencia = () -> {
                try {
                    LogsJB.trace("Comienza a verificar la existencia de la tabla");
                    Connection connect = this.getConnection();
                    DatabaseMetaData metaData = connect.getMetaData();
                    ResultSet tables = metaData.getTables(null, null, "%", null);
                    //Obtener las tablas disponibles
                    TablesSQL.getTablas().clear();
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


                        String NameModel = this.getClass().getSimpleName();
                        String NameTable = temp.getTABLE_NAME();
                        if (NameModel.equalsIgnoreCase(NameTable)) {
                            this.setTableExist(Boolean.TRUE);
                            this.setTableName(NameTable);
                            this.setTabla(temp);
                            ResultSet clavePrimaria = metaData.getPrimaryKeys(null, null, NameTable);
                            if (clavePrimaria.next()) {
                                PrimaryKey clave=new PrimaryKey();
                                clave.setTABLE_CAT(clavePrimaria.getString(1));
                                clave.setTABLE_SCHEM(clavePrimaria.getString(2));
                                clave.setTABLE_NAME(clavePrimaria.getString(3));
                                clave.setCOLUMN_NAME(clavePrimaria.getString(4));
                                clave.setKEY_SEQ(clavePrimaria.getShort(5));
                                clave.setPK_NAME(clavePrimaria.getString(6));
                                this.getTabla().setClaveprimaria(clave);
                            }

                            LogsJB.info("La tabla correspondiente a este modelo, existe en BD's");
                            tables.close();
                            this.closeConnection(connect);
                            getColumnsTable();
                            return true;
                        }
                    }
                    LogsJB.trace("Termino de Revisarar el resultSet");
                    tables.close();
                    if (!this.getTableExist()) {
                        LogsJB.info("La tabla correspondiente a este modelo, No existe en BD's");

                        this.closeConnection(connect);

                        return false;
                    }


                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que verifica si existe la tabla correspondiente al modelo: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                }
                return false;
            };
            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<Boolean> future = executor.submit(VerificarExistencia);
            while (!future.isDone()) {

            }
            executor.shutdown();
            result = future.get();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que verifica si existe la tabla correspondiente al modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }

    /**
     * Obtiene las columnas que tiene la tabla correspondiente al modelo en BD's.
     */
    protected void getColumnsTable() {
        Runnable ObtenerColumnas = () -> {
            try {
                Connection connect = this.getConnection();
                DatabaseMetaData metaData = connect.getMetaData();
                ResultSet columnas = metaData.getColumns(null, null, this.getTableName(), null);
                //Obtener las tablas disponibles
                this.getTabla().getColumnas().clear();
                while (columnas.next()) {
                    ColumnsSQL temp = new ColumnsSQL();
                    temp.setTABLE_CAT(columnas.getString(1));
                    temp.setTABLE_SCHEM(columnas.getString(2));
                    temp.setTABLE_NAME(columnas.getString(3));
                    temp.setCOLUMN_NAME(columnas.getString(4));
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
                    //this.getColumnas().add(temp);
                    //Types.ARRAY

                }
                LogsJB.info("Información de las columnas de la tabla correspondiente al modelo obtenida");
                columnas.close();
                this.closeConnection(connect);
                this.getTabla().getColumnas().stream().sorted(Comparator.comparing(ColumnsSQL::getORDINAL_POSITION));
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que obtiene las columnas de la tabla que corresponde al modelo: " + e.toString());
                LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(ObtenerColumnas);
        executor.shutdown();


    }

    /**
     * Metodo que actualiza la información que el modelo tiene sobre lo que existe en BD's'
     */
    public void refresh() {
        try {
            this.setTableExist(this.tableExist());
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el metodo que actualiza la información de conexión del modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }


    /**
     * Metodo que setea la información de la columna Java en el respectivo tipo de Dato SQL
     *
     * @param columnsSQL Columna java que será analizada
     * @param ejecutor   PreparedStatement sobre el cual se estara envíando la información de la columna
     * @param auxiliar   Indice que indica la posición del parametro en el ejecutor.
     * @throws SQLException Lanza esta excepción si sucede algun problema al setear el valor Java en el ejecutor.
     */
    protected void convertJavaToSQL(Column columnsSQL, PreparedStatement ejecutor, int auxiliar) throws SQLException {

        if ((columnsSQL.getDataTypeSQL() == DataType.CHAR) || (columnsSQL.getDataTypeSQL() == DataType.VARCHAR)
                || (columnsSQL.getDataTypeSQL() == DataType.LONGVARCHAR)) {
            //Caracteres y cadenas de Texto
            ejecutor.setString(auxiliar, (String) columnsSQL.getValor());

        } else if ((columnsSQL.getDataTypeSQL() == DataType.NUMERIC) || (columnsSQL.getDataTypeSQL() == DataType.DECIMAL)
                || (columnsSQL.getDataTypeSQL() == DataType.MONEY) || (columnsSQL.getDataTypeSQL() == DataType.SMALLMONEY)
                || (columnsSQL.getDataTypeSQL() == DataType.DOUBLE)) {
            //Dinero y numericos que tienen decimales
            ejecutor.setDouble(auxiliar, (Double) columnsSQL.getValor());

        } else if ((columnsSQL.getDataTypeSQL() == DataType.BIT)) {
            //Valores Booleanos
            ejecutor.setBoolean(auxiliar, (Boolean) columnsSQL.getValor());
            //ejecutor.setObject(auxiliar, columnsSQL.getValor(), Types.BOOLEAN);
        } else if ((columnsSQL.getDataTypeSQL() == DataType.SMALLINT) || (columnsSQL.getDataTypeSQL() == DataType.TINYINT)
                || (columnsSQL.getDataTypeSQL() == DataType.INTEGER) || (columnsSQL.getDataTypeSQL() == DataType.IDENTITY)
                || (columnsSQL.getDataTypeSQL() == DataType.SERIAL)) {
            //Valores Enteros
            ejecutor.setInt(auxiliar, (Integer) columnsSQL.getValor());

        } else if ((columnsSQL.getDataTypeSQL() == DataType.REAL) || (columnsSQL.getDataTypeSQL() == DataType.FLOAT)) {
            //Valores Flotantes
            ejecutor.setFloat(auxiliar, (Float) columnsSQL.getValor());

        } else if ((columnsSQL.getDataTypeSQL() == DataType.BINARY) || (columnsSQL.getDataTypeSQL() == DataType.VARBINARY)
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
            ejecutor.setObject(auxiliar, columnsSQL.getValor());
        }
    }



    protected void convertSQLtoJava(ColumnsSQL columna, ResultSet resultado, Method metodo, Column columnaSql) throws SQLException, InvocationTargetException, IllegalAccessException {
        String columnName = columna.getCOLUMN_NAME();
        String columnType = columna.getTYPE_NAME();
        if ((StringUtils.equalsIgnoreCase(columnType, DataType.CHAR.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.VARCHAR.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.LONGVARCHAR.name()))) {
            //Caracteres y cadenas de Texto
            columnaSql.setValor(resultado.getString(columnName));
            metodo.invoke(this, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.NUMERIC.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.DECIMAL.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.MONEY.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.SMALLMONEY.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.DOUBLE.name()))) {
            //Dinero y numericos que tienen decimales
            columnaSql.setValor(resultado.getDouble(columnName));
            metodo.invoke(this, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.BIT.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.BOOLEAN.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.BOOL.name()))) {
            //Valores Booleanos
            columnaSql.setValor(resultado.getBoolean(columnName));
            metodo.invoke(this, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.SMALLINT.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.TINYINT.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.INTEGER.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.IDENTITY.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.SERIAL.name()))) {
            //Valores Enteros
            columnaSql.setValor(resultado.getInt(columnName));
            metodo.invoke(this, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.REAL.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.FLOAT.name()))) {
            //Valores Flotantes
            columnaSql.setValor(resultado.getFloat(columnName));
            metodo.invoke(this, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.BINARY.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.VARBINARY.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.LONGVARBINARY.name()))) {
            //Valores binarios
            columnaSql.setValor(resultado.getBytes(columnName));
            metodo.invoke(this, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.DATE.name()))) {
            //DATE
            columnaSql.setValor(resultado.getDate(columnName));
            metodo.invoke(this, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.TIME.name()))) {
            //Time
            columnaSql.setValor(resultado.getTime(columnName));
            metodo.invoke(this, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.TIMESTAMP.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.DATETIME.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.SMALLDATETIME.name()))
                || (StringUtils.equalsIgnoreCase(columnType, DataType.DATETIME2.name()))) {
            //TimeStamp
            columnaSql.setValor(resultado.getTimestamp(columnName));
            metodo.invoke(this, columnaSql);
        } else {
            LogsJB.warning("No se pudo setear el valor de la columna: "+columnName);
            LogsJB.warning("Debido a que ninguno de los metodos corresponde al tipo de dato SQL: "+columnType);
        }

    }

    /**
     * Almacena el modelo proporcionado.
     *
     * @param modelo Modelo que será almacenado en BD's
     * @param <T>    Expresión que hace que el metodo sea generico y pueda ser utilizado por cualquier objeto que herede la Clase JBSqlUtils
     */
    public <T extends Methods_Conexion> void saveModel(T modelo) {
        try {
            modelo.setTaskIsReady(false);
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }
            Connection connect = modelo.getConnection();
            Runnable Save = () -> {
                try {
                    if (modelo.getTableExist()) {

                        String sql = "INSERT INTO " + modelo.getClass().getSimpleName() + "(";
                        List<Method> metodos = new ArrayList<>();
                        metodos = modelo.getMethodsGetOfModel(modelo.getMethodsModel());
                        int datos = 0;
                        //Llena la información de las columnas que se insertaran
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            String columnName = metodo.getName();
                            columnName = StringUtils.remove(columnName, "get");
                            if (Objects.isNull(columnsSQL.getValor())) {
                                continue;
                            }
                            datos++;
                            sql = sql + columnName;
                            int temporal = metodos.size() - 1;
                            if (i < temporal) {
                                sql = sql + ", ";
                            } else if (i == temporal) {
                                sql = sql + ") VALUES (";
                            }
                        }

                        //Llena los espacios con la información de los datos que serán agregados
                        for (int i = 0; i < datos; i++) {
                            sql = sql + "?";
                            int temporal = datos - 1;
                            if (i < temporal) {
                                sql = sql + ", ";
                            } else if (i == temporal) {
                                sql = sql + ");";
                            }
                        }
                        LogsJB.info(sql);

                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        //LogsJB.info("Creo la instancia del PreparedStatement");
                        //Llena el prepareStatement
                        int auxiliar = 1;
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            if (Objects.isNull(columnsSQL.getValor())) {
                                continue;
                            }
                            convertJavaToSQL(columnsSQL, ejecutor, auxiliar);
                            auxiliar++;
                        }

                        if (ejecutor.executeUpdate() == 1) {
                            int filas = ejecutor.getUpdateCount();
                            LogsJB.info("Filas actualizadas: " + filas);
                        }

                        modelo.closeConnection(connect);

                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "almacenar el Registro");
                    }
                    modelo.setTaskIsReady(true);
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    modelo.setTaskIsReady(true);
                }
            };

            Runnable Update = () -> {
                try {
                    if (modelo.getTableExist()) {

                        //Obtener cual es la clave primaria de la tabla

                        for(int i=0; i < modelo.getTabla().getColumnas().size(); i++){
                            ColumnsSQL columna=modelo.getTabla().getColumnas().get(i);
                            String columnName=columna.getCOLUMN_NAME();
                            String columnType=columna.getTYPE_NAME();
                            LogsJB.trace("Columna : "+columnName);
                        }


                        String sql = "UPDATE " + modelo.getClass().getSimpleName() + "(";
                        List<Method> metodos = new ArrayList<>();
                        metodos = modelo.getMethodsGetOfModel(modelo.getMethodsModel());
                        int datos = 0;
                        //Llena la información de las columnas que se insertaran
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            String columnName = metodo.getName();
                            columnName = StringUtils.remove(columnName, "get");
                            if (Objects.isNull(columnsSQL.getValor())) {
                                continue;
                            }
                            datos++;
                            sql = sql + columnName;
                            int temporal = metodos.size() - 1;
                            if (i < temporal) {
                                sql = sql + ", ";
                            } else if (i == temporal) {
                                sql = sql + ") VALUES (";
                            }
                        }

                        //Llena los espacios con la información de los datos que serán agregados
                        for (int i = 0; i < datos; i++) {
                            sql = sql + "?";
                            int temporal = datos - 1;
                            if (i < temporal) {
                                sql = sql + ", ";
                            } else if (i == temporal) {
                                sql = sql + ");";
                            }
                        }
                        LogsJB.info(sql);

                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        //LogsJB.info("Creo la instancia del PreparedStatement");
                        //Llena el prepareStatement
                        int auxiliar = 1;
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            if (Objects.isNull(columnsSQL.getValor())) {
                                continue;
                            }
                            convertJavaToSQL(columnsSQL, ejecutor, auxiliar);
                            auxiliar++;
                        }

                        if (ejecutor.executeUpdate() == 1) {
                            int filas = ejecutor.getUpdateCount();
                            LogsJB.info("Filas actualizadas: " + filas);
                        }

                        modelo.closeConnection(connect);

                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "almacenar el Registro");
                    }
                    modelo.setTaskIsReady(true);
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    modelo.setTaskIsReady(true);
                }
            };
            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            ejecutor.submit(Save);
            ejecutor.shutdown();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }


    public <T extends Methods_Conexion> T procesarResultSet(T modelo, ResultSet registros) throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
        T temp= (T) modelo.getClass().newInstance();
        temp=modelo;
        temp.setModelExist(true);
        temp.getTabla().setColumnas(modelo.getTabla().getColumnas());
        LogsJB.info("Obtuvo un resultado de BD's, procedera a llenar el modelo");
        List<Method> metodosSet = new ArrayList<>();
        LogsJB.trace("Inicializa el array list de los metodos set");
        metodosSet = temp.getMethodsSetOfModel(temp.getMethodsModel());
        LogsJB.trace("obtuvo los metodos set");
        LogsJB.debug("Cantidad de columnas : "+temp.getTabla().getColumnas().size());

        //Llena la información del modelo
        for(int i=0; i < temp.getTabla().getColumnas().size(); i++){
            ColumnsSQL columna=temp.getTabla().getColumnas().get(i);
            String columnName=columna.getCOLUMN_NAME();
            LogsJB.trace("Columna : "+columnName);
            LogsJB.debug("Cantidad de metodos set: "+metodosSet.size());
            //Recorrera los metodos set del modelo para ver cual es el que corresponde a la columna
            for (int j = 0; j < metodosSet.size(); j++) {
                Method metodo= metodosSet.get(j);
                String metodoName = metodo.getName();
                metodoName = StringUtils.remove(metodoName, "set");
                if(StringUtils.equalsIgnoreCase(metodoName, columnName)){
                    LogsJB.trace("Nombre de la columna, nombre del metodo set: "+columnName+"   "+metodoName);
                    List<Method> metodosget = new ArrayList<>();
                    metodosget=temp.getMethodsGetOfModel(temp.getMethodsModel());
                    LogsJB.trace("Cantidad de metodos get: "+metodosget.size());
                    //Llena la información de las columnas que se insertaran
                    for (int a = 0; a < metodosget.size(); a++) {
                        //Obtengo el metodo
                        Method metodoget = metodosget.get(a);
                        //Obtengo la información de la columna
                        Column columnsSQL = (Column) metodoget.invoke(temp, null);
                        String NameMetodoGet = metodoget.getName();
                        NameMetodoGet = StringUtils.remove(NameMetodoGet, "get");
                        if(StringUtils.equalsIgnoreCase(NameMetodoGet, columnName)){
                            LogsJB.trace("Nombre de la columna, nombre del metodo get: "+columnName+"   "+NameMetodoGet);
                            LogsJB.debug("Coincide el nombre de los metodos con la columna: "+columnName);
                            convertSQLtoJava(columna, registros, metodo, columnsSQL);
                        }
                    }
                }
            }
        }
        return temp;
    }

}

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
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ConexionUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.ColumnsSQL;
import io.github.josecarlosbran.JBSqlUtils.Utilities.PrimaryKey;
import io.github.josecarlosbran.JBSqlUtils.Utilities.TablesSQL;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.File;
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

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.getBooleanfromInt;
import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;


/**
 * @author Jose Bran
 * Clase que proporciona los metodos de conexión necesarios para que el modelo pueda realizar
 * la conversion de datos entre java y sql, así mismo proporciona metodos de logica del manejor del resultado
 * como de envíar valores a BD's
 */
public class Methods_Conexion extends Conexion {


    /**
     * Constructor de la clase Conexión que se encarga de inicializar las propiedades de conexión del modelo,
     * las cuales las obtiene de las propiedades del sistema Java.
     *
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
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
    public synchronized <T> List<Method> getMethodsModel() {
        Method[] metodos = this.getClass().getMethods();
        List<Method> result = new ArrayList<>();
        // Los muestro en consola
        for (Method metodo : metodos) {
            String clase = metodo.getDeclaringClass().getSimpleName();
            String returntype = metodo.getReturnType().getSimpleName();
            Parameter[] parametros = metodo.getParameters();
            String ParametroType = "";
            if (parametros.length >= 1) {
                ParametroType = parametros[0].getType().getSimpleName();
            }

            if ((clase.equalsIgnoreCase("Object")
                    || clase.equalsIgnoreCase("And")
                    || clase.equalsIgnoreCase("GET")
                    || clase.equalsIgnoreCase("Or")
                    || clase.equalsIgnoreCase("OrderBy")
                    || clase.equalsIgnoreCase("Where")
                    || clase.equalsIgnoreCase("Conexion")
                    || clase.equalsIgnoreCase("Take")
                    || clase.equalsIgnoreCase("Methods")
                    || clase.equalsIgnoreCase("Methods_Conexion"))
                //&& !(returntype.equalsIgnoreCase("Column") ||ParametroType.equalsIgnoreCase("Column"))
            ) {

            } else if ((returntype.equalsIgnoreCase("Column") || ParametroType.equalsIgnoreCase("Column"))) {
                //System.out.println(metodo.getName() + "   " + metodo.getDeclaringClass() + "  " + returntype+"  " + ParametroType);
                result.add(metodo);
            }
            //System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+returntype);
        }
        return result;
    }


    /**
     * Obtiene la lista de los metodos get del modelo que lo invoca.
     *
     * @param metodos Lista de metodos que tiene el modelo, sobre los cuales se filtraran los metodos get.
     * @param <T>     Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Lista de los metodos get del modelo que lo invoca.
     */
    public synchronized <T> List<Method> getMethodsGetOfModel(List<Method> metodos) {
        // Los muestro en consola
        int i = 0;
        //System.out.println("Inicia a obtener los metodos get");
        List<Method> result = new ArrayList<>();
        //System.out.println("asigno los metodos get");
        for (Method metodo : metodos) {
            //Method metodo = result.get(i);
            String returntype = metodo.getReturnType().getSimpleName();
            String nombre = metodo.getName();
            if (returntype.equalsIgnoreCase("Column") && StringUtils.startsWithIgnoreCase(nombre, "Get")) {
                //System.out.println(metodo.getName() + "   " + metodo.getDeclaringClass() + "  " + returntype);
                result.add(metodo);
            } else {
                //System.out.println(metodo.getName() + "   " + metodo.getDeclaringClass() + "  " + returntype);
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
    public synchronized <T> List<Method> getMethodsSetOfModel(List<Method> metodos) {
        List<Method> result = new ArrayList<>();
        for (Method metodo : metodos) {
            Parameter[] parametros = metodo.getParameters();
            String ParametroType = "";
            String nombre = metodo.getName();
            if (parametros.length >= 1) {
                ParametroType = parametros[0].getType().getSimpleName();
            }
            //System.out.println(nombre + "   " + metodo.getDeclaringClass() + "  " + ParametroType);
            if ((StringUtils.startsWithIgnoreCase(nombre, "Set")) && (ParametroType.equalsIgnoreCase("Column"))) {
                result.add(metodo);
                //System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+ParametroType);
            } else {
                //result.remove(metodo);
            }
        }
        return result;
    }

    /**
     * Obtiene la conexión del modelo a la BD's con las propiedades definidas.
     *
     * @return Retorna la conexión del modelo a la BD's con las propiedades definidas.
     */
    public synchronized Connection getConnection() {
        Connection connect = null;
        try {
            String url = null;
            if (this.getDataBaseType() == DataBase.PostgreSQL) {
                //Carga el controlador de PostgreSQL
                url = null;
                connect = null;
                Class.forName("org.postgresql.Driver");
                DriverManager.registerDriver(new org.postgresql.Driver());
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + "/" + this.getBD();
                String usuario = this.getUser();
                String password = this.getPassword();
                if(!stringIsNullOrEmpty(this.getPropertisURL())){
                    url=url+this.getPropertisURL();
                }
                LogsJB.debug("Url de conexion a DB: "+url);
                connect = DriverManager.getConnection(url, usuario, password);
            } else if (this.getDataBaseType() == DataBase.MySQL) {
                url = null;
                connect = null;
                //Carga el controlador de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + "/" + this.getBD();
                String usuario = this.getUser();
                String password = this.getPassword();
                if(!stringIsNullOrEmpty(this.getPropertisURL())){
                    url=url+this.getPropertisURL();
                }
                LogsJB.debug("Url de conexion a DB: "+url);
                connect = DriverManager.getConnection(url, usuario, password);
            } else if (this.getDataBaseType() == DataBase.SQLServer) {
                url = null;
                connect = null;
                //Carga el controlador de SQLServer
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + ";databaseName=" + this.getBD() + ";TrustServerCertificate=True";
                String usuario = this.getUser();
                String password = this.getPassword();
                if(!stringIsNullOrEmpty(this.getPropertisURL())){
                    url=url+this.getPropertisURL();
                }
                LogsJB.debug("Url de conexion a DB: "+url);
                connect = DriverManager.getConnection(url, usuario, password);
            } else if (this.getDataBaseType() == DataBase.SQLite) {
                Class.forName("org.sqlite.JDBC").newInstance();
                DriverManager.registerDriver(new org.sqlite.JDBC());
                url = null;
                connect = null;
                try{
                    //Rutas de archivos
                    File fichero = new File(this.getBD());
                    //Verifica si existe la carpeta Logs, si no existe, la Crea
                    String carpeta=fichero.getParent();
                    if(!Objects.isNull(carpeta)){
                        File directorio = new File(carpeta);
                        if (!directorio.exists()) {
                            if (directorio.mkdirs()) {
                                LogsJB.debug("Crea el directorio donde estara la BD's SQLite: "+fichero.getParent());
                            }
                        }
                    }

                }catch (Exception e){
                    LogsJB.fatal("Excepción disparada al intentar crear el directorio donde estará la BD's SQLite: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                }

                url = "jdbc:" + this.getDataBaseType().getDBType() + ":" + this.getBD();
                LogsJB.debug("Url de conexion a DB: "+url);
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
                LogsJB.warning("Conexión a BD's ya estaba cerrada");
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
                LogsJB.warning("Conexión a BD's ya estaba cerrada");
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
                    LogsJB.info("Comienza a verificar la existencia de la tabla");
                    Connection connect = this.getConnection();
                    DatabaseMetaData metaData = connect.getMetaData();
                    ResultSet tables = metaData.getTables(null, null, "%", null);

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


                        String NameModel = this.getTableName();
                        String NameTable = temp.getTABLE_NAME();
                        //Valida que la tabla pertenezca a la BD's que pertenece el modelo
                        String DatabaseName=this.getBD();
                        String DatabaseTemp=temp.getTABLE_SCHEM();
                        Boolean tablaisofDB=true;
                        if(!stringIsNullOrEmpty(DatabaseTemp)){
                            if(!DatabaseName.equalsIgnoreCase(DatabaseTemp)){
                                tablaisofDB=false;
                            }
                        }

                        if (NameModel.equalsIgnoreCase(NameTable)&&tablaisofDB) {
                            this.setTableExist(Boolean.TRUE);
                            this.setTableName(NameTable);
                            this.setTabla(temp);
                            ResultSet clavePrimaria = metaData.getPrimaryKeys(null, null, NameTable);
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
        //Runnable ObtenerColumnas = () -> {
        try {
            LogsJB.debug("Comienza a obtener las columnas que le pertenecen a la tabla");
            Connection connect = this.getConnection();
            LogsJB.trace("Obtuvo el objeto conexión");
            DatabaseMetaData metaData = connect.getMetaData();
            LogsJB.trace("Ya tiene el MetaData de la BD's");
            ResultSet columnas = metaData.getColumns(null, null, this.getTableName(), null);
            LogsJB.debug("Ya tiene el resultset con las columnas de la tabla");
            //Obtener las tablas disponibles
            this.getTabla().getColumnas().clear();
            this.getTabla().getColumnsExist().clear();
            while (columnas.next()) {
                ColumnsSQL temp = new ColumnsSQL();
                temp.setTABLE_CAT(columnas.getString(1));
                temp.setTABLE_SCHEM(columnas.getString(2));
                temp.setTABLE_NAME(columnas.getString(3));
                temp.setCOLUMN_NAME(columnas.getString(4));
                //Seteara que la columna si existe en BD's
                //columnaExist(columnas.getString(4));
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
        /*};
        ExecutorService executorColumnas = Executors.newFixedThreadPool(1);
        executorColumnas.submit(ObtenerColumnas);
        executorColumnas.shutdown();*/


    }


    /**
     * Si el modelo tiene una columna correspondiente al nombre de la columna especificada en el
     * parametro, setea entre los valores de la columna que esta si existe en BD's.
     *
     * @param nameColumn Nombre de la columna en BD's
     */
    protected void columnaExist(String nameColumn) {
        try {
            Runnable columnexist = () -> {
                try {
                    LogsJB.debug("Buscara si la columna tiene un atributo en el modelo actual: " + nameColumn);
                    List<Method> metodosSet = new ArrayList<>();
                    LogsJB.trace("Inicializa el array list de los metodos set");
                    metodosSet = this.getMethodsSetOfModel(this.getMethodsModel());
                    LogsJB.trace("obtuvo los metodos set");
                    //Llena la información del modelo
                    LogsJB.trace("Columna : " + nameColumn);
                    LogsJB.debug("Cantidad de metodos set: " + metodosSet.size());
                    //Recorrera los metodos set del modelo para ver cual es el que corresponde a la columna
                    for (int j = 0; j < metodosSet.size(); j++) {
                        Method metodo = metodosSet.get(j);
                        String metodoName = metodo.getName();
                        metodoName = StringUtils.removeStartIgnoreCase(metodoName, "set");
                        //Si el nombre del metodo coincide con el nombre de la columna
                        if (StringUtils.equalsIgnoreCase(metodoName, nameColumn)) {
                            LogsJB.trace("Nombre de la columna, nombre del metodo set: " + nameColumn + "   " + metodoName);
                            List<Method> metodosget = new ArrayList<>();
                            metodosget = this.getMethodsGetOfModel(this.getMethodsModel());
                            LogsJB.trace("Cantidad de metodos get: " + metodosget.size());
                            //Llena la información de las columnas que se insertaran
                            for (int a = 0; a < metodosget.size(); a++) {
                                //Obtengo el metodo
                                Method metodoget = metodosget.get(a);
                                //Obtengo la información de la columna
                                Column columnsSQL = (Column) metodoget.invoke(this, null);
                                String NameMetodoGet = metodoget.getName();
                                NameMetodoGet = StringUtils.removeStartIgnoreCase(NameMetodoGet, "get");
                                if (StringUtils.equalsIgnoreCase(NameMetodoGet, nameColumn)) {
                                    columnsSQL.setColumnExist(true);
                                    LogsJB.debug("Seteara que la columna existe en BD's: " + nameColumn + "   " + NameMetodoGet);
                                    //LogsJB.trace("Coincide el nombre de los metodos con la columna: "+columnName);
                                    metodo.invoke(this, columnsSQL);
                                } else if (StringUtils.equalsIgnoreCase(NameMetodoGet, "created_at")) {
                                    if (StringUtils.equalsIgnoreCase(nameColumn, this.getCreatedAt())) {
                                        columnsSQL.setColumnExist(true);
                                        LogsJB.debug("Seteara que la columna existe en BD's: " + nameColumn + "   " + this.getCreatedAt());
                                        //LogsJB.trace("Coincide el nombre de los metodos con la columna: "+columnName);
                                        metodo.invoke(this, columnsSQL);
                                    }
                                } else if (StringUtils.equalsIgnoreCase(NameMetodoGet, "updated_at")) {
                                    if (StringUtils.equalsIgnoreCase(nameColumn, this.getUpdateAT())) {
                                        columnsSQL.setColumnExist(true);
                                        LogsJB.debug("Seteara que la columna existe en BD's: " + nameColumn + "   " + this.getUpdateAT());
                                        //LogsJB.trace("Coincide el nombre de los metodos con la columna: "+columnName);
                                        metodo.invoke(this, columnsSQL);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que setea si la columna existe en BD's'" +
                            "SQL de la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());

                }
            };
            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            ejecutor.submit(columnexist);
            ejecutor.shutdown();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que setea si la columna existe en BD's'" +
                    "SQL de la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());

        }


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
        LogsJB.debug("DataType de la columna: " + columnsSQL.getDataTypeSQL());
        LogsJB.debug("Indice donde insertara la columna: " + auxiliar);
        LogsJB.debug("Valor de la columna: " + columnsSQL.getValor());

        if ((columnsSQL.getDataTypeSQL() == DataType.CHAR) || (columnsSQL.getDataTypeSQL() == DataType.VARCHAR)
                || (columnsSQL.getDataTypeSQL() == DataType.LONGVARCHAR)) {
            //Caracteres y cadenas de Texto
            ejecutor.setString(auxiliar, (String) columnsSQL.getValor());

        } else if ((columnsSQL.getDataTypeSQL() == DataType.NUMERIC) || (columnsSQL.getDataTypeSQL() == DataType.DECIMAL)
                || (columnsSQL.getDataTypeSQL() == DataType.MONEY) || (columnsSQL.getDataTypeSQL() == DataType.SMALLMONEY)
                || (columnsSQL.getDataTypeSQL() == DataType.DOUBLE)) {
            //Dinero y numericos que tienen decimales
            ejecutor.setDouble(auxiliar, (Double) columnsSQL.getValor());

        } else if ((columnsSQL.getDataTypeSQL() == DataType.BIT)
                || (columnsSQL.getDataTypeSQL() == DataType.BOOLEAN)
                || (columnsSQL.getDataTypeSQL() == DataType.BOOL)) {
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
     * Metodo que convierte la información obtenida de BD's a Java
     *
     * @param columna    Columna del modelo
     * @param resultado  ResulSet que esta siendo evaludo
     * @param metodo     Metodo Set en el que se seteara la columna del modelo
     * @param columnaSql Columna SQL que corresponde a la columna del modelo
     * @param invocador  Invocador del metodo
     * @throws SQLException              Lanza esta excepción de suceder algun problema con el ResultSet
     * @throws InvocationTargetException Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     * @throws IllegalAccessException    Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     */
    protected void convertSQLtoJava(ColumnsSQL columna, ResultSet resultado, Method metodo, Column columnaSql, Object invocador) throws SQLException, InvocationTargetException, IllegalAccessException {
        String columnName = columna.getCOLUMN_NAME();
        String columnType = columna.getTYPE_NAME();
        LogsJB.trace("DataType de la columna: " + columna.getTYPE_NAME());
        LogsJB.trace("Valor de la columna: " + resultado.getObject(columnName));
        if ((StringUtils.containsIgnoreCase(columnType, DataType.CHAR.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.LONGVARCHAR.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.VARCHAR.name()))
        ) {
            //Caracteres y cadenas de Texto
            columnaSql.setValor(resultado.getString(columnName));
            metodo.invoke(invocador, columnaSql);
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.NUMERIC.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DECIMAL.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.MONEY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.SMALLMONEY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DOUBLE.name()))) {
            //Dinero y numericos que tienen decimales
            columnaSql.setValor(resultado.getDouble(columnName));
            metodo.invoke(invocador, columnaSql);
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.BIT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.BOOLEAN.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.BOOL.name()))) {
            //Valores Booleanos
            Object valor = resultado.getObject(columnName);
            LogsJB.trace("Tipo de dato del valor obtenido: " + valor.getClass());
            LogsJB.trace("valor obtenido: " + valor);
            if ((valor instanceof String)) {
                columnaSql.setValor(Boolean.valueOf((String) valor));
            } else if (valor instanceof Integer) {
                columnaSql.setValor(getBooleanfromInt((int) valor));
            } else {
                columnaSql.setValor(resultado.getBoolean(columnName));
            }
            metodo.invoke(invocador, columnaSql);
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.SMALLINT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.TINYINT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.INTEGER.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.IDENTITY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.INT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.SERIAL.name()))) {
            //Valores Enteros
            columnaSql.setValor(resultado.getInt(columnName));
            metodo.invoke(invocador, columnaSql);
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.REAL.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.FLOAT.name()))) {
            //Valores Flotantes
            columnaSql.setValor(resultado.getFloat(columnName));
            metodo.invoke(invocador, columnaSql);
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.BINARY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.VARBINARY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.LONGVARBINARY.name()))) {
            //Valores binarios
            columnaSql.setValor(resultado.getBytes(columnName));
            metodo.invoke(invocador, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.DATE.name()))) {
            //DATE
            columnaSql.setValor(resultado.getDate(columnName));
            metodo.invoke(invocador, columnaSql);
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.TIME.name()))) {
            //Time
            columnaSql.setValor(resultado.getTime(columnName));
            metodo.invoke(invocador, columnaSql);
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.TIMESTAMP.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DATETIME.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.SMALLDATETIME.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DATETIME2.name()))) {
            //TimeStamp
            columnaSql.setValor(resultado.getTimestamp(columnName));
            metodo.invoke(invocador, columnaSql);
        } else {
            columnaSql.setValor(resultado.getObject(columnName));
            metodo.invoke(invocador, columnaSql);
            LogsJB.warning("No se pudo setear el valor de la columna: " + columnName);
            LogsJB.warning("Debido a que ninguno de los metodos corresponde al tipo de dato SQL: " + columnType);
        }

    }


    /**
     * Almacena el modelo proporcionado en BD's.
     *
     * @param modelo Modelo que será insertado o actualizado en BD's
     * @param <T>    Expresión que hace que el metodo sea generico y pueda ser utilizado por cualquier objeto que herede la Clase JBSqlUtils
     */
    protected <T extends Methods_Conexion> void saveModel(T modelo) {
        try {
            modelo.setTaskIsReady(false);
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }
            Connection connect = modelo.getConnection();
            Runnable Save = () -> {
                try {
                    if (modelo.getTableExist()) {

                        String sql = "INSERT INTO " + modelo.getTableName() + "(";
                        List<Method> metodos = new ArrayList<>();
                        metodos = modelo.getMethodsGetOfModel(modelo.getMethodsModel());
                        int datos = 0;
                        List<Integer> indicemetodos = new ArrayList<>();
                        //Llena la información de las columnas que se insertaran
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            String columnName = metodo.getName();
                            columnName = StringUtils.removeStartIgnoreCase(columnName, "get");
                            if (Objects.isNull(columnsSQL.getValor())) {

                                continue;
                            }

                            //Si la columna no tiene seteado que existe en BD's no la va a envíar
                            /*if(!columnsSQL.getColumnExist()){
                                continue;
                            }*/
                            if (!this.getTabla().getColumnsExist().contains(columnName.toUpperCase())) {
                                continue;
                            }


                            //Si el modelo tiene seteado que no se manejaran las timestamps entonces
                            //Ignora el guardar esas columnas
                            if ((!this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, "created_at"))
                                    || (StringUtils.equalsIgnoreCase(columnName, "updated_at")))) {
                                continue;
                            }


                            //Setea el nombre de la columna Created_at
                            if ((this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, "created_at"))
                            )) {
                                columnName = this.getCreatedAt();
                            }

                            //Setea el nombre de la columna Update_at
                            if ((this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, "updated_at"))
                            )) {
                                columnName = this.getUpdateAT();
                            }

                            datos++;
                            if (datos > 1) {
                                sql = sql + ", ";
                            }
                            sql = sql + columnName;
                            indicemetodos.add(i);
                        }

                        sql = sql + ") VALUES (";
                        //Llena los espacios con la información de los datos que serán agregados
                        for (int i = 0; i < indicemetodos.size(); i++) {
                            sql = sql + "?";
                            int temporal = indicemetodos.size() - 1;
                            if (i < temporal) {
                                sql = sql + ", ";
                            } else if (i == temporal) {
                                sql = sql + ");";
                            }
                        }

                        //LogsJB.info(sql);

                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        //LogsJB.info("Creo la instancia del PreparedStatement");
                        //Llena el prepareStatement
                        LogsJB.debug("Llenara la información de las columnas: " + indicemetodos.size());
                        int auxiliar = 1;
                        for (int i = 0; i < indicemetodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(indicemetodos.get(i));
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            if (Objects.isNull(columnsSQL.getValor())) {
                                continue;
                            }


                            convertJavaToSQL(columnsSQL, ejecutor, auxiliar);
                            auxiliar++;
                        }

                        LogsJB.info(ejecutor.toString());
                        int filas=ejecutor.executeUpdate();
                        LogsJB.info("Filas Insertadas en BD's': " + filas);
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
                        String namePrimaryKey = modelo.getTabla().getClaveprimaria().getCOLUMN_NAME();

                        String sql = "UPDATE " + modelo.getTableName() + " SET";
                        List<Method> metodos = new ArrayList<>();
                        metodos = modelo.getMethodsGetOfModel(modelo.getMethodsModel());
                        int datos = 0;
                        List<Integer> indicemetodos = new ArrayList<>();
                        int indicePrimarykey = 0;
                        //Llena la información de las columnas que se insertaran
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            String columnName = metodo.getName();
                            columnName = StringUtils.removeStartIgnoreCase(columnName, "get");
                            if (StringUtils.equalsIgnoreCase(namePrimaryKey, columnName)) {
                                indicePrimarykey = i;
                                continue;
                            }
                            if (Objects.isNull(columnsSQL.getValor())) {
                                continue;
                            }

                            //Si la columna no tiene seteado que existe en BD's no la va a envíar
                            /*if(!columnsSQL.getColumnExist()){
                                continue;
                            }*/
                            if (!this.getTabla().getColumnsExist().contains(columnName.toUpperCase())) {
                                continue;
                            }

                            //Si el modelo tiene seteado que no se manejaran las timestamps entonces
                            //Ignora el guardar esas columnas
                            if ((!this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, "created_at"))
                                    || (StringUtils.equalsIgnoreCase(columnName, "updated_at")))) {
                                continue;
                            }

                            //Si si se manejaran las timestamps, entonces obvia la timestamps de created_at
                            if ((this.getTimestamps()) &&
                                    ((StringUtils.equalsIgnoreCase(columnName, "created_at")))
                            ) {
                                continue;
                            }

                            //Setea el nombre de la columna Update_at
                            if ((this.getTimestamps()) && ((StringUtils.equalsIgnoreCase(columnName, "updated_at"))
                            )) {
                                columnName = this.getUpdateAT();
                            }


                            if (StringUtils.containsIgnoreCase(sql, "?")) {
                                sql = sql + ", " + columnName + "=?";
                            } else {
                                sql = sql + " " + columnName + "=?";
                            }

                            datos++;
                            indicemetodos.add(i);
                        }

                        //Colocamos el where
                        sql = sql + " WHERE " + namePrimaryKey + "=?;";


                        //LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        //LogsJB.info("Creo la instancia del PreparedStatement");
                        //Llena el prepareStatement
                        LogsJB.debug("Llenara la información de las columnas: " + indicemetodos.size());
                        int auxiliar = 1;
                        for (int i = 0; i < indicemetodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(indicemetodos.get(i));
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            String columnName = metodo.getName();
                            columnName = StringUtils.removeStartIgnoreCase(columnName, "get");
                            if ((StringUtils.equalsIgnoreCase(columnName, "updated_at"))) {
                                Long datetime = System.currentTimeMillis();
                                columnsSQL.setValor(new Timestamp(datetime));
                            }
                            if (Objects.isNull(columnsSQL.getValor())) {
                                continue;
                            }

                            convertJavaToSQL(columnsSQL, ejecutor, auxiliar);
                            auxiliar++;
                        }

                        LogsJB.debug("Termino de llenar la información de las columnas: " + auxiliar);

                        //Colocamos la información del where
                        //Obtengo el metodo
                        Method metodo = metodos.get(indicePrimarykey);
                        //Obtengo la información de la columna
                        Column columnsSQL = (Column) metodo.invoke(modelo, null);
                        if (Objects.isNull(columnsSQL.getValor())) {
                            LogsJB.warning("El modelo proporcionado no tiene definido el valor de la clave " + namePrimaryKey
                                    + " Por lo cual no se puede actualizar el modelo");
                            modelo.setTaskIsReady(true);
                            return;
                        } else {
                            convertJavaToSQL(columnsSQL, ejecutor, auxiliar);
                        }

                        LogsJB.info(ejecutor.toString());
                        int filas=ejecutor.executeUpdate();
                        LogsJB.info("Filas actualizadas: " + filas);
                        modelo.closeConnection(connect);

                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "actualizar el Registro");
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
            LogsJB.debug("El modelo existe: " + modelo.getModelExist());
            if (modelo.getModelExist()) {
                ejecutor.submit(Update);
            }
            if (!modelo.getModelExist()) {
                ejecutor.submit(Save);
            }
            ejecutor.shutdown();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Elimina la información del modelo proporcionado en BD's
     *
     * @param modelo Modelo del cual se desea eliminar la información en BD's
     * @param <T>    Expresión que hace que el metodo sea generico y pueda ser utilizado por cualquier
     *               objeto que herede la Clase JBSqlUtils
     */
    protected <T extends Methods_Conexion> void deleteModel(T modelo) {
        try {
            modelo.setTaskIsReady(false);
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }
            Connection connect = modelo.getConnection();

            Runnable Delete = () -> {
                try {
                    if (modelo.getTableExist()) {

                        //Obtener cual es la clave primaria de la tabla
                        String namePrimaryKey = modelo.getTabla().getClaveprimaria().getCOLUMN_NAME();

                        String sql = "DELETE FROM " + modelo.getTableName();
                        List<Method> metodos = new ArrayList<>();
                        metodos = modelo.getMethodsGetOfModel(modelo.getMethodsModel());
                        int datos = 0;
                        List<Integer> indicemetodos = new ArrayList<>();
                        int indicePrimarykey = -1;
                        //Llena la información de las columnas que se insertaran
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            String columnName = metodo.getName();
                            columnName = StringUtils.removeStartIgnoreCase(columnName, "get");
                            if (StringUtils.equalsIgnoreCase(namePrimaryKey, columnName)) {
                                indicePrimarykey = i;
                                break;
                            }
                        }

                        //Colocamos el where
                        sql = sql + " WHERE " + namePrimaryKey + "=?;";


                        //LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        //LogsJB.info("Creo la instancia del PreparedStatement");
                        //Llena el prepareStatement
                        int auxiliar = 1;

                        LogsJB.debug("Colocara la información del where: " + auxiliar);
                        LogsJB.debug("Indice del metodo donde esta la información del where: " + indicePrimarykey);

                        //Colocamos la información del where si el indice es un indice valido
                        if (indicePrimarykey >= 0) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(indicePrimarykey);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(modelo, null);
                            if (Objects.isNull(columnsSQL.getValor())) {
                                LogsJB.warning("El modelo proporcionado no tiene definido el valor de la clave " + namePrimaryKey
                                        + " Por lo cual no se puede eliminar el modelo");
                                modelo.setTaskIsReady(true);
                                return;
                            } else {
                                convertJavaToSQL(columnsSQL, ejecutor, auxiliar);
                            }
                        }
                        LogsJB.info(ejecutor.toString());
                        int filas=ejecutor.executeUpdate();
                        LogsJB.info("Filas actualizadas: " + filas);
                        modelo.closeConnection(connect);

                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "Eliminar el Registro");
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

            ejecutor.submit(Delete);


            ejecutor.shutdown();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
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
     * @throws SQLException              Lanza esta excepción de suceder algun problema con el ResultSet
     * @throws InvocationTargetException Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     * @throws IllegalAccessException    Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     * @throws DataBaseUndefind          Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                                   BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined     Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                                   propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected <T extends Methods_Conexion> T procesarResultSet(T modelo, ResultSet registros) throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException, DataBaseUndefind, PropertiesDBUndefined {
        T temp = (T) modelo.getClass().newInstance();
        temp.setTabla(modelo.getTabla());
        temp.setTableExist(modelo.getTableExist());
        temp.setTableName(modelo.getTableName());
        temp.setModelExist(true);

        //Seteamos las propiedades del Modelo que obtuvo la conexión
        temp.setDataBaseType(modelo.getDataBaseType());
        temp.setBD(modelo.getBD());
        temp.setUser(modelo.getUser());
        temp.setPassword(modelo.getPassword());
        temp.setPort(modelo.getPort());
        temp.setHost(modelo.getHost());

        LogsJB.info("Obtuvo un resultado de BD's, procedera a llenar el modelo");
        List<Method> metodosSet = new ArrayList<>();
        LogsJB.trace("Inicializa el array list de los metodos set");
        metodosSet = temp.getMethodsSetOfModel(temp.getMethodsModel());
        LogsJB.trace("obtuvo los metodos set");
        LogsJB.debug("Cantidad de columnas : " + temp.getTabla().getColumnas().size());

        //Llena la información del modelo

        for (int i = 0; i < temp.getTabla().getColumnas().size(); i++) {
            ColumnsSQL columna = temp.getTabla().getColumnas().get(i);
            String columnName = columna.getCOLUMN_NAME();
            LogsJB.trace("Columna : " + columnName);
            LogsJB.debug("Cantidad de metodos set: " + metodosSet.size());
            //Recorrera los metodos set del modelo para ver cual es el que corresponde a la columna
            for (int j = 0; j < metodosSet.size(); j++) {
                Method metodo = metodosSet.get(j);
                String metodoName = metodo.getName();
                metodoName = StringUtils.removeStartIgnoreCase(metodoName, "set");

                if (StringUtils.equalsIgnoreCase(metodoName, columnName)) {

                    LogsJB.trace("Nombre de la columna, nombre del metodo set: " + columnName + "   " + metodoName);
                    List<Method> metodosget = new ArrayList<>();
                    metodosget = temp.getMethodsGetOfModel(temp.getMethodsModel());
                    LogsJB.trace("Cantidad de metodos get: " + metodosget.size());
                    //Llena la información de las columnas que se insertaran

                    for (int a = 0; a < metodosget.size(); a++) {
                        //Obtengo el metodo
                        Method metodoget = metodosget.get(a);
                        //Obtengo la información de la columna
                        Column columnsSQL = (Column) metodoget.invoke(temp, null);
                        String NameMetodoGet = metodoget.getName();
                        NameMetodoGet = StringUtils.removeStartIgnoreCase(NameMetodoGet, "get");
                        if (StringUtils.equalsIgnoreCase(NameMetodoGet, columnName)) {
                            LogsJB.trace("Nombre de la columna, nombre del metodo get: " + columnName + "   " + NameMetodoGet);
                            //LogsJB.trace("Coincide el nombre de los metodos con la columna: "+columnName);
                            columnsSQL.setColumnExist(true);
                            convertSQLtoJava(columna, registros, metodo, columnsSQL, temp);
                            break;
                        }
                    }
                }
            }
        }
        return temp;
    }

    /**
     * Llena el modelo proporcionado con la Información Obtenida de BD's
     *
     * @param modelo    Modelo que invoca el metodo.
     * @param registros Resulset que contiene la información obtenida de BD's
     * @param <T>       * @param <T>    Expresión que hace que el metodo sea generico y pueda ser utilizado por cualquier
     *                  objeto que herede la Clase JBSqlUtils
     * @throws InstantiationException    Lanza esta excepción si ocurre un error al crear una nueva instancia
     *                                   del tipo de modelo proporcionado
     * @throws SQLException              Lanza esta excepción de suceder algun problema con el ResultSet
     * @throws InvocationTargetException Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     * @throws IllegalAccessException    Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     */
    protected <T extends Methods_Conexion> void procesarResultSetOneResult(T modelo, ResultSet registros) throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
        modelo.setModelExist(true);
        LogsJB.info("Obtuvo un resultado de BD's, procedera a llenar el modelo");
        List<Method> metodosSet = new ArrayList<>();
        LogsJB.trace("Inicializa el array list de los metodos set");
        metodosSet = modelo.getMethodsSetOfModel(modelo.getMethodsModel());
        LogsJB.trace("obtuvo los metodos set");
        LogsJB.debug("Cantidad de columnas : " + modelo.getTabla().getColumnas().size());

        //Llena la información del modelo

        for (int i = 0; i < modelo.getTabla().getColumnas().size(); i++) {
            ColumnsSQL columna = modelo.getTabla().getColumnas().get(i);
            String columnName = columna.getCOLUMN_NAME();
            LogsJB.trace("Columna : " + columnName);
            LogsJB.debug("Cantidad de metodos set: " + metodosSet.size());
            //Recorrera los metodos set del modelo para ver cual es el que corresponde a la columna
            for (int j = 0; j < metodosSet.size(); j++) {
                Method metodo = metodosSet.get(j);
                String metodoName = metodo.getName();
                metodoName = StringUtils.removeStartIgnoreCase(metodoName, "set");
                if (StringUtils.equalsIgnoreCase(metodoName, columnName)) {
                    LogsJB.trace("Nombre de la columna, nombre del metodo set: " + columnName + "   " + metodoName);
                    List<Method> metodosget = new ArrayList<>();
                    metodosget = modelo.getMethodsGetOfModel(modelo.getMethodsModel());
                    LogsJB.trace("Cantidad de metodos get: " + metodosget.size());
                    //Llena la información de las columnas que se insertaran
                    for (int a = 0; a < metodosget.size(); a++) {
                        //Obtengo el metodo
                        Method metodoget = metodosget.get(a);
                        //Obtengo la información de la columna
                        Column columnsSQL = (Column) metodoget.invoke(modelo, null);
                        String NameMetodoGet = metodoget.getName();
                        NameMetodoGet = StringUtils.removeStartIgnoreCase(NameMetodoGet, "get");
                        if (StringUtils.equalsIgnoreCase(NameMetodoGet, columnName)) {
                            LogsJB.trace("Nombre de la columna, nombre del metodo get: " + columnName + "   " + NameMetodoGet);
                            //LogsJB.trace("Coincide el nombre de los metodos con la columna: "+columnName);
                            columnsSQL.setColumnExist(true);
                            convertSQLtoJava(columna, registros, metodo, columnsSQL, modelo);
                            break;
                        }
                    }
                }
            }
        }

    }


    /**
     * Obtiene un Json Object con las columnas solicitadas como propiedades del json con sus respectivos valores
     * @param columnas Lista de los nombres de las columnas que se desea recuperar, si se desea recuperar todas las columnas envíar NULL
     * @param registros ResultSet del cual se obtendran los valores de las columnas
     * @return Retorna un Json Object con las columnas solicitadas como propiedades del json con sus respectivos valores
     * @throws SQLException Lanza esta excepción si sucede algun error al obtener el valor de cada una de las columnas solicitadas
     */
    protected JSONObject procesarResultSetJSON(List<String> columnas, ResultSet registros) throws SQLException{
        JSONObject temp= new JSONObject();
        LogsJB.info("Obtuvo un resultado de BD's, procedera a llenar el JSON");
        LogsJB.debug("Cantidad de columnas : " + this.getTabla().getColumnas().size());
        //Llena la información del modelo
        for (int i = 0; i < this.getTabla().getColumnas().size(); i++) {
            ColumnsSQL columna = this.getTabla().getColumnas().get(i);
            String columnName = columna.getCOLUMN_NAME();
            LogsJB.trace("Columna : " + columnName);
            //Si no se especifica las columnas a obtener retorna todas las columnas
            if(Objects.isNull(columnas)){
                this.convertSQLtoJson(columna, registros, temp);
            }else{
                //Si se especificaron las columnas a obtener llena unicamente esas columnas
                for(int j=0;j<columnas.size();j++) {
                    if(columnName.equalsIgnoreCase(columnas.get(j))){
                        //columna.setCOLUMN_NAME(columnas.get(j));
                        this.convertSQLtoJson(columna, registros, temp);
                    }
                }

            }

        }
        return temp;
    }

    /**
     * Agrega la columna como una propiedad del Json Object envíado como parametro
     * @param columna Columna que se obtendra.
     * @param resultado ResultSet del cual se obtendra el valor para la columna.
     * @param temp Json Object al cual se agregara el valor de la columna como una propiedad del JSON.
     * @throws SQLException
     */
    protected void convertSQLtoJson(ColumnsSQL columna, ResultSet resultado, JSONObject temp) throws SQLException {
        String columnName = columna.getCOLUMN_NAME();
        String columnType = columna.getTYPE_NAME();
        LogsJB.trace("DataType de la columna: " + columna.getTYPE_NAME());
        LogsJB.trace("Valor de la columna: " + resultado.getObject(columnName));
        if ((StringUtils.containsIgnoreCase(columnType, DataType.CHAR.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.LONGVARCHAR.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.VARCHAR.name()))
        ) {
            //Caracteres y cadenas de Texto
            temp.put(columnName, resultado.getString(columnName));
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.NUMERIC.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DECIMAL.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.MONEY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.SMALLMONEY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DOUBLE.name()))) {
            //Dinero y numericos que tienen decimales
            temp.put(columnName,resultado.getDouble(columnName));
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.BIT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.BOOLEAN.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.BOOL.name()))) {
            //Valores Booleanos
            Object valor = resultado.getObject(columnName);
            LogsJB.trace("Tipo de dato del valor obtenido: " + valor.getClass());
            LogsJB.trace("valor obtenido: " + valor);
            if ((valor instanceof String)) {
                temp.put(columnName,Boolean.valueOf((String) valor).booleanValue());
            } else if (valor instanceof Integer) {
                temp.put(columnName, getBooleanfromInt((int) valor));
            } else {
                temp.put(columnName, resultado.getBoolean(columnName));
            }

        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.SMALLINT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.TINYINT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.INTEGER.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.IDENTITY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.INT.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.SERIAL.name()))) {
            //Valores Enteros
            temp.put(columnName, resultado.getInt(columnName));

        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.REAL.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.FLOAT.name()))) {
            //Valores Flotantes
            temp.put(columnName, resultado.getFloat(columnName));
        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.BINARY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.VARBINARY.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.LONGVARBINARY.name()))) {
            //Valores binarios
            temp.put(columnName, resultado.getBytes(columnName));
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.DATE.name()))) {
            //DATE
            temp.put(columnName, resultado.getDate(columnName));
        } else if ((StringUtils.equalsIgnoreCase(columnType, DataType.TIME.name()))) {
            //Time
            temp.put(columnName, resultado.getTime(columnName));

        } else if ((StringUtils.containsIgnoreCase(columnType, DataType.TIMESTAMP.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DATETIME.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.SMALLDATETIME.name()))
                || (StringUtils.containsIgnoreCase(columnType, DataType.DATETIME2.name()))) {
            //TimeStamp
            temp.put(columnName, resultado.getTimestamp(columnName));
        } else {
            temp.put(columnName, resultado.getObject(columnName));
            LogsJB.warning("No se pudo setear el valor de la columna: " + columnName);
            LogsJB.warning("Debido a que ninguno de los metodos corresponde al tipo de dato SQL: " + columnType);
        }
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
     * Crea la tabla solicitada correspondiente al modelo con las columnas especificadas como parametro
     * @param columnas Lista de columnas que se desea sean creadas por JBSqlUtils
     * @return Retorna True si logra crear la tabla, False en caso que la tabla ya exista en BD's o que
     * haya sucedido un error al momento de ejecutar la sentencia SQL
     */
    protected Boolean crateTableJSON(List<Column> columnas) {
        Boolean result = false;
        try {
            Callable<Boolean> createtabla = () -> {
                try {
                    if (this.tableExist()) {
                        LogsJB.info("La tabla correspondiente al modelo ya existe en la BD's, por lo cual no será creada.");
                        return false;
                    } else {
                        String sql = "CREATE TABLE " + this.getTableName() + "(";
                        //Aquí vamos a ordenar la lista
                        LogsJB.debug("Comienza a ordenar la lista");
                        columnas.sort((columna1,columna2)->{
                            try {
                                LogsJB.trace("Columnas a evaluar: "+columna1.getName()+"  "+columna2.getName());
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
                        for (int i = 0; i < columnas.size(); i++) {
                            //Obtengo el metodo
                            Column columnsSQL = columnas.get(i);
                            //Obtengo la información de la columna
                            String columnName = columnsSQL.getName();
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
                    LogsJB.fatal("Excepción disparada en el método que Crea la tabla solicitada: " + e.toString());
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
            LogsJB.fatal("Excepción disparada en el método que Crea la tabla solicitada: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }



}

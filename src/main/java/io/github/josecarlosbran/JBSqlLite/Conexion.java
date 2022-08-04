package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ConexionUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Utilities.ColumnsSQL;
import io.github.josecarlosbran.JBSqlLite.Utilities.TablesSQL;
import io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB;
import io.github.josecarlosbran.LogsJB.LogsJB;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class Conexion {


    /**
     * Tipo de BD's a la cual se conectara.
     */
    private DataBase dataBaseType = null;

    /**
     * Host en el cual se encuentra la BD's a la cual se conectara.
     */
    private String host = null;

    /**
     * Puerto en el cual esta escuchando la BD's a la cual nos vamos a conectar.
     */
    private String port = null;

    /**
     * Nombre de la BD's a la cual nos conectaremos.
     */
    private String BD = null;

    /**
     * Usuario de la BD's
     */
    private String user = null;

    /**
     * Contraseña del Usuario de la BD's
     */
    private String password = null;

    /**
     * Indica si para este modelo se utilizará la BD's establecida en las propiedades del sistema
     * al estar en TRUE, cuando esta en FALSE, eso indica que para este modelo se tendra
     * una configuración personalizada.
     */
    private Boolean getPropertySystem = true;

    private Connection connect = null;

    private Boolean tableExist=null;

    private String tableName=null;

    protected List<ColumnsSQL> Columnas=new LinkedList<>();


    public Conexion() throws DataBaseUndefind, PropertiesDBUndefined {
        this.setGetPropertySystem(true);
        this.setDataBaseType(setearDBType());
        this.setBD(setearBD());
        this.setHost(setearHost());
        this.setPort(setearPort());
        this.setUser(setearUser());
        this.setPassword(setearPassword());
    }

    public List<ColumnsSQL> getColumnas() {
        return this.Columnas;
    }

    public void setColumnas(List<ColumnsSQL> columnas) {
        this.Columnas = columnas;
    }


    /**
     * Setea el tipo de BD's al cual se estara conectando este modelo.
     *
     * @return Retorna el tipo de BD's al cual se estara conectando la BD's si esta definida
     * de lo contrario retorna NULL.
     * @throws DataBaseUndefind Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                          el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                          esta excepción, poder manejarla.
     */
    private DataBase setearDBType() throws DataBaseUndefind {
        if (this.getGetPropertySystem()) {
            String dataBase = System.getProperty("DataBase");
            if (stringIsNullOrEmpty(dataBase)) {
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new DataBaseUndefind("No se a seteado la DataBase que índica a que BD's deseamos se pegue JBSqlUtils");
            } else {
                if (dataBase.equals(DataBase.MySQL.name())) {
                    setDataBaseType(DataBase.MySQL);
                    return DataBase.MySQL;
                }
                if (dataBase.equals(DataBase.SQLite.name())) {
                    setDataBaseType(DataBase.SQLite);
                    return DataBase.SQLite;
                }
                if (dataBase.equals(DataBase.SQLServer.name())) {
                    setDataBaseType(DataBase.SQLServer);
                    return DataBase.SQLServer;
                }
                if (dataBase.equals(DataBase.PostgreSQL.name())) {
                    setDataBaseType(DataBase.PostgreSQL);
                    return DataBase.PostgreSQL;
                }
            }
        }
        return null;
    }


    /**
     * Setea el Host en el cual se encuentra la BD's a la cual se conectara.
     *
     * @return Retorna el Host en el cual se encuentra la BD's, de no estar definido, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a definido el Host en el cual se encuentra la BD's, si el tipo
     *                               de BD's al cual se desea conectar es diferente a una BD's SQLite
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     */
    private String setearHost() throws PropertiesDBUndefined, DataBaseUndefind {
        if (this.getGetPropertySystem()) {
            String host = System.getProperty("DataBaseHost");
            if (this.getDataBaseType() != DataBase.SQLite) {
                if (stringIsNullOrEmpty(host)) {
                    //Si la propiedad del sistema no esta definida, Lanza una Exepción
                    throw new PropertiesDBUndefined("No se a seteado el host en el que se encuentra la BD's a la cual deseamos se pegue JBSqlUtils");
                }
            }
            return host;

        }
        return null;
    }


    /**
     * Setea el Puerto en el cual esta escuchando la BD's a la cual nos vamos a conectar.
     *
     * @return Retorna el Puerto en el cual se encuentra la BD's, de no estar definido, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a seteado el Puerto en el cual
     *                               se encuentra escuchando la BD's, si el tipo de BD's al cual se desea conectar es diferente a una BD's SQLite
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     */
    private String setearPort() throws PropertiesDBUndefined, DataBaseUndefind {
        if (this.getGetPropertySystem()) {
            String port = System.getProperty("DataBasePort");
            if (this.getDataBaseType() != DataBase.SQLite) {
                if (stringIsNullOrEmpty(port)) {
                    //Si la propiedad del sistema no esta definida, Lanza una Exepción
                    throw new PropertiesDBUndefined("No se a seteado el puerto en el que se encuentra escuchando la BD's a la cual deseamos se pegue JBSqlUtils");
                }
            }
            return port;

        }
        return null;
    }


    /**
     * Setea el Usuario de la BD's a la cual nos conectaremos
     *
     * @return Retorna el Usuario con el cual se conectara la BD's, de no estar definido, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a seteado el Usuario con el cual
     *                               se conectara a la BD's
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     */
    private String setearUser() throws PropertiesDBUndefined, DataBaseUndefind {
        if (this.getGetPropertySystem()) {
            String user = System.getProperty("DataBaseUser");
            if (this.getDataBaseType() != DataBase.SQLite) {
                if (stringIsNullOrEmpty(user)) {
                    //Si la propiedad del sistema no esta definida, Lanza una Exepción
                    throw new PropertiesDBUndefined("No se a seteado el usuario de la BD's a la cual deseamos se pegue JBSqlUtils");
                }
            }
            return user;
        }
        return null;
    }


    /**
     * Setea el Nombre de la BD's a la cual nos conectaremos.
     *
     * @return Retorna el nombre de la BD's a la cual nos conectaremos, de no estar definido, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a seteado el Nombre de la BD's a la cual nos conectaremos.
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     */
    private String setearBD() throws PropertiesDBUndefined {
        if (this.getGetPropertySystem()) {
            String DB = System.getProperty("DataBaseBD");
            System.out.println("BD seteada en system property: " + DB);
            if (stringIsNullOrEmpty(DB)) {
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new PropertiesDBUndefined("No se a seteado la BD's a la cual deseamos se pegue JBSqlUtils");
            }
            return DB;

        }
        return null;
    }

    /**
     * Setea la contraseña del usuario de la BD's a la cual nos conectaremos.
     *
     * @return Retorna la contraseña del usuario con el cual se conectara la BD's, de no estar definida, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a seteado la contraseña del usuario con el cual
     *                               se conectara a la BD's
     */
    private String setearPassword() throws PropertiesDBUndefined, DataBaseUndefind {
        if (this.getGetPropertySystem()) {
            String password = System.getProperty("DataBasePassword");
            if (this.getDataBaseType() != DataBase.SQLite) {
                if (stringIsNullOrEmpty(password)) {
                    //Si la propiedad del sistema no esta definida, Lanza una Exepción
                    throw new PropertiesDBUndefined("No se a seteado la contraseña del usuario de la BD's a la cual deseamos se pegue JBSqlUtils");

                }
            }
            return password;
        }
        return null;
    }


    /**
     * Obtiene el tipo de base de datos al cual se conectara el modelo
     *
     * @return Retorna el Tipo de Base de Datos a la cual se conectara el modelo, de no estar definida, lanzara una excepción
     * @throws DataBaseUndefind Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                          el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                          esta excepción, poder manejarla.
     */
    public DataBase getDataBaseType() throws DataBaseUndefind {
        if (Objects.isNull(this.dataBaseType)) {
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new DataBaseUndefind("No se a seteado la DataBase que índica a que BD's deseamos se pegue JBSqlUtils");
        }
        return this.dataBaseType;
    }

    /**
     * Setea el tipo de BD's a la cual se estara conectando el Modelo
     *
     * @param dataBase Tipo de BD's a la cual se estara conectando el Modelo, los tipos disponibles son
     *                 MySQL,
     *                 SQLServer,
     *                 PostgreSQL,
     *                 SQLite.
     */
    public void setDataBaseType(DataBase dataBase) {
        try {
            this.dataBaseType = dataBase;
            if (this.getGetPropertySystem()) {
                System.setProperty("DataBase", dataBase.name());
                System.out.println("SystemProperty Seteada: " + System.getProperty("DataBase"));
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al setear el tipo de BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }


    /**
     * Obtiene el host en el cual se encuentra la BD's a la cual se desea conectar el modelo.
     *
     * @return Retorna el host en el cual se encuentra la BD's.
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a definido el Host en el cual se encuentra la BD's, si el tipo
     *                               de BD's al cual se desea conectar es diferente a una BD's SQLite
     */
    public String getHost() throws DataBaseUndefind, PropertiesDBUndefined {
        if ((this.getDataBaseType() != DataBase.SQLite) && stringIsNullOrEmpty(this.host)) {
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new PropertiesDBUndefined("No se a seteado el host en el que se encuentra la BD's a la cual deseamos se pegue JBSqlUtils");
        }
        return this.host;
    }

    /**
     * Setea el host en el cual se encuentra la BD's a la cual se conectara el modelo.
     *
     * @param host Host en el cual se encuentra la BD's a la que nos queremos conectar.
     */
    public void setHost(String host) {
        try {
            this.host = host;
            if (this.getGetPropertySystem() && !stringIsNullOrEmpty(host)) {
                System.setProperty("DataBaseHost", host);
                System.out.println("SystemProperty Seteada: " + System.getProperty("DataBaseHost"));
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al setear el host en el que se encuentra la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }


    /**
     * Obtiene el puerto en el cual se encuentra escuchando la BD's a la cual se pega el modelo.
     *
     * @return Retorna el puerto en el cual se encuentra escuchando la BD's a la cual se pega el modelo.
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a definido el Puerto en el cual se encuentra escuchando la BD's, si el tipo
     *                               de BD's al cual se desea conectar es diferente a una BD's SQLite
     */
    public String getPort() throws DataBaseUndefind, PropertiesDBUndefined {
        if (stringIsNullOrEmpty(this.port) && (this.getDataBaseType() != DataBase.SQLite)) {
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new PropertiesDBUndefined("No se a seteado el puerto en el que se encuentra escuchando la BD's a la cual deseamos se pegue JBSqlUtils");
        }
        return this.port;
    }

    /**
     * Setea el puerto en el cual se encuentra escuchando la BD's a la cual se pegara el modelo.
     *
     * @param port Puerto en el cual se encuentra escuchando la BD's a la cual se pegara el modelo.
     */
    public void setPort(String port) {
        try {
            this.port = port;
            if (this.getGetPropertySystem() && !stringIsNullOrEmpty(port)) {
                System.setProperty("DataBasePort", port);
                System.out.println("SystemProperty Seteada: " + System.getProperty("DataBasePort"));
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al setear el puerto en el cual se encuentra escuchando la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }


    /**
     * Obtiene el usuario con el cual el modelo se conectara a la BD's.
     *
     * @return Retorna el usuario con el cual el modelo se conectara a la BD's.
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a definido el usuario con el cual se conectara a la BD's, si el tipo
     *                               de BD's al cual se desea conectar es diferente a una BD's SQLite
     */
    public String getUser() throws DataBaseUndefind, PropertiesDBUndefined {
        if (stringIsNullOrEmpty(this.user) && (this.getDataBaseType() != DataBase.SQLite)) {
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new PropertiesDBUndefined("No se a seteado el usuario de la BD's a la cual deseamos se pegue JBSqlUtils");
        }
        return this.user;
    }

    /**
     * Setea el Usuario con el cual el modelo se conectara a la BD's.
     *
     * @param user Usuario con el cual el modelo se conectara a la BD's.
     */
    public void setUser(String user) {
        try {
            this.user = user;
            if (this.getGetPropertySystem() && !stringIsNullOrEmpty(user)) {
                System.setProperty("DataBaseUser", user);
                System.out.println("SystemProperty Seteada: " + System.getProperty("DataBaseUser"));
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al setear el usuario con el cual el modelo se conectara a la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Obtiene la contraseña del usuario con el cual el modelo se conectara a la BD's.
     *
     * @return Retorna la contraseña del usuario con el cual el modelo se conectara a la BD's.
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a definido la contraseña del usuario con el cual se conectara a la BD's, si el tipo
     *                               de BD's al cual se desea conectar es diferente a una BD's SQLite
     */
    public String getPassword() throws DataBaseUndefind, PropertiesDBUndefined {
        if (stringIsNullOrEmpty(this.password) && (this.getDataBaseType() != DataBase.SQLite)) {
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new PropertiesDBUndefined("No se a seteado la contraseña del usuario de la BD's a la cual deseamos se pegue JBSqlUtils");
        }
        return this.password;
    }

    /**
     * Setea la contraseña del usuario con el cual el modelo se conectara a la BD's.
     *
     * @param password Contraseña del usuario con el cual el modelo se conectara a la BD's.
     */
    public void setPassword(String password) {
        try {
            this.password = password;
            if (this.getGetPropertySystem() && !stringIsNullOrEmpty(password)) {
                System.setProperty("DataBasePassword", password);
                System.out.println("SystemProperty Seteada: " + System.getProperty("DataBasePassword"));
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al setear la contraseña del usuario con el cual el modelo se conectara a la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }

    /**
     * Obtiene el nombre de la Base de Datos a la que se conectara el modelo.
     *
     * @return Retorna el nombre de la Base de Datos a la que se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a configurado la Base de Datos a la que se conectara el modelo.
     */
    public String getBD() throws PropertiesDBUndefined {
        if (stringIsNullOrEmpty(this.BD)) {
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new PropertiesDBUndefined("No se a seteado la BD's a la cual deseamos se pegue JBSqlUtils");
        }
        return this.BD;
    }

    /**
     * Setea el nombre de la Base de Datos a la que se conectara el modelo.
     *
     * @param BD Nombre de la Base de Datos a la que se conectara el modelo.
     */
    public void setBD(String BD) {
        try {
            this.BD = BD;
            if (this.getGetPropertySystem() && !stringIsNullOrEmpty(BD)) {
                System.out.println("Base de datos a setear: " + BD);
                System.setProperty("DataBaseBD", BD);
                System.out.println("SystemProperty Seteada: " + System.getProperty("DataBaseBD"));
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el nombre de la Base de Datos a la que se conectara el modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Obtiene la bandera que indica si el modelo utilizara la configuración general o una configuración personalidada, sobre la
     * conexión a la BD's del Modelo.
     *
     * @return Retorna TRUE si el modelo obtendra la configuración general del sistema, retorna FALSE si el modelo tendra una configuración
     * personalizada, el valor por default es TRUE.
     */
    public Boolean getGetPropertySystem() {
        return this.getPropertySystem;
    }

    /**
     * Setea la bandera que indica si el modelo utilizara la configuración general o una configuración personalidada, sobre la
     * conexión a la BD's del Modelo.
     *
     * @param getPropertySystem TRUE si el modelo obtendra la configuración general del sistema, retorna FALSE si el modelo tendra una configuración
     *                          personalizada, el valor por default es TRUE.
     */
    public void setGetPropertySystem(Boolean getPropertySystem) {
        try {
            this.getPropertySystem = getPropertySystem;
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al setear la bandera getPropertySystem: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }

    /**
     * Obtiene la conexión del Modelo a la Base de Datos.
     *
     * @return Retorna la conexión del Modelo a la Base de Datos.
     */
    public Connection getConnect() throws ConexionUndefind {
        if (Objects.isNull(this.connect)) {
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new ConexionUndefind("No se a conectado el modelo a la BD's");
        }
        return this.connect;
    }

    /***
     * Setea la conexión del Modelo a la Base de Datos.
     * @param connect Conexión del Modelo a la Base de Datos.
     */
    public void setConnect(Connection connect) {
        try {
            this.connect = connect;
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al setear la Conexión del modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    public Boolean getTableExist() {
        return tableExist;
    }

    public void setTableExist(Boolean tableExist) {
        this.tableExist = tableExist;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

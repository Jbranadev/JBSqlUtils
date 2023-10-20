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

import io.github.josecarlosbran.JBSqlUtils.Enumerations.ConeccionProperties;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.TablesSQL;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * @author Jose Bran
 * Clase que tiene todas las variables y procedimientos necesarios para que un modelo se conecte a la BD's
 * especificada.
 */
class Conexion {

    /**
     * Ejecutor de tareas asincronas
     */
    protected static ExecutorService ejecutor = Executors.newCachedThreadPool();
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

    /**
     * Bandera que sirve para identificar si la tabla correspondiente al modelo Existe
     */
    private Boolean tableExist = false;
    /**
     * Nombre de la tabla correspondiente al modelo.
     */
    private String tableName = null;
    /**
     * Bandera que sirve para identificar si la tarea que estaba realizando el modelo a sido terminada
     */
    private Boolean taskIsReady = true;
    /**
     * Define cual será la clave primaria del modelo
     */
    private String primaryKey = null;
    /**
     * Define si la clave primaria es autoincrementable, el valor por default es true
     */
    private Boolean primaryKeyIsIncremental = true;
    /**
     * Define si el modelo desea que JBSqlUtils maneje las timestamps Created_at, Update_at.
     */
    private Boolean timestamps = true;

    /**
     * Define el nombre de la columna correspondiente a la TimeStamp CreateAT
     */
    private String createdAt = "created_at";
    /**
     * Define el nombre de la columna correspondiente a la TimeStamp UpdateAT
     */
    private String updateAT = "updated_at";
    /**
     * Bandera que sirve para identificar si el modelo existe en BD's, de existir cuando se llame al método save se procedera a actualizar el modelo
     */
    private Boolean modelExist = false;
    /**
     * Representa la metadata de la tabla correspondiente al modelo en BD's
     */
    private TablesSQL tabla = new TablesSQL();
    /**
     * Propiedades extra para la url de conexión a BD's por ejemplo
     * ?autoReconnect=true&useSSL=false
     */
    private String propertisURL = null;
    /**
     * Lista de metodos get que posee el modelo
     */
    private List<Method> MethodsGetOfModel = null;
    /**
     * Lista de metodos set que posee el modelo
     */
    private List<Method> MethodsSetOfModel = null;

    private List<Field> fieldsOfModel = null;

    /**
     * Cantidad de conexiones que ha realizado el modelo a BD's
     */
    private Integer contadorConexiones = 0;

    /**
     * Constructor de la clase Conexión que se encarga de inicializar las propiedades de conexión del modelo,
     * las cuales las obtiene de las propiedades del sistema Java.
     *
     * @throws DataBaseUndefind      Lanza esta excepción si el tipo de BD's a la cual se conectara el modelo no ha sido definida entre
     *                               las propiedades del sistema Java.
     * @throws PropertiesDBUndefined Lanza esta excepción si las propiedades de conexión no han sido definidas.
     */
    protected Conexion() {
        this.setTableName();
        this.inicializador(true);
    }

    /**
     * Constructor de la clase Conexión que se encarga de inicializar las propiedades de conexión del modelo,
     * las cuales las obtiene de las propiedades del sistema Java.
     *
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    protected Conexion(Boolean getPropertySystem) {
        this.setTableName();
        this.inicializador(getPropertySystem);
    }

    /**
     * Inicializa los atributos de la clase Conexion
     *
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    protected void inicializador(Boolean getPropertySystem) {
        if (getPropertySystem) {
            this.getSystemProperties();
        }
        this.setGetPropertySystem(getPropertySystem);
    }

    /**
     * Si el nombre de la tabla no esta definido, setea el nombre del modelo, como nombre de la tabla
     */
    private void setTableName() {
        if (stringIsNullOrEmpty(this.getTableName())) {
            this.setTableName(this.getClass().getSimpleName());
        }
    }

    /**
     * método que se encarga de inicializar las propiedades de conexión del modelo,
     * las cuales las obtiene de las propiedades del sistema Java.
     */
    public void getSystemProperties() {
        this.setGetPropertySystem(true);
        this.setDataBaseType(setearDBType());
        this.setBD(setearBD());
        this.setHost(setearHost());
        this.setPort(setearPort());
        this.setUser(setearUser());
        this.setPassword(setearPassword());
        this.setPropertisURL(setearPropertisUrl());
    }

    /**
     * Setea el tipo de BD's al cual se estara conectando este modelo.
     *
     * @return Retorna el tipo de BD's al cual se estara conectando la BD's si esta definida
     * de lo contrario retorna NULL.
     */
    private DataBase setearDBType() {
        String dataBase = System.getProperty(ConeccionProperties.DBTYPE.getPropiertie());
        if (dataBase.equals(DataBase.MySQL.name())) {
            setDataBaseType(DataBase.MySQL);
            return DataBase.MySQL;
        }
        if (dataBase.equals(DataBase.MariaDB.name())) {
            setDataBaseType(DataBase.MariaDB);
            return DataBase.MariaDB;
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
        return null;
    }

    /**
     * Setea el Host en el cual se encuentra la BD's a la cual se conectara.
     *
     * @return Retorna el Host en el cual se encuentra la BD's, de no estar definido, retorna NULL
     */
    private String setearHost() {
        String host;
        host = System.getProperty(ConeccionProperties.DBHOST.getPropiertie());
        return host;
    }

    /**
     * Setea el Puerto en el cual esta escuchando la BD's a la cual nos vamos a conectar.
     *
     * @return Retorna el Puerto en el cual se encuentra la BD's, de no estar definido, retorna NULL
     */
    private String setearPort() {
        String port;
        port = System.getProperty(ConeccionProperties.DBPORT.getPropiertie());
        return port;
    }

    /**
     * Setea el Usuario de la BD's a la cual nos conectaremos
     *
     * @return Retorna el Usuario con el cual se conectara la BD's, de no estar definido, retorna NULL
     */
    private String setearUser() {
        String user;
        user = System.getProperty(ConeccionProperties.DBUSER.getPropiertie());
        return user;
    }

    /**
     * Setea el Nombre de la BD's a la cual nos conectaremos.
     *
     * @return Retorna el nombre de la BD's a la cual nos conectaremos, de no estar definido, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a seteado el Nombre de la BD's a la cual nos conectaremos.
     */
    private String setearBD() {
        String DB;
        DB = System.getProperty(ConeccionProperties.DBNAME.getPropiertie());
        return DB;
    }

    /**
     * Setea la contraseña del usuario de la BD's a la cual nos conectaremos.
     *
     * @return Retorna la contraseña del usuario con el cual se conectara la BD's, de no estar definida, retorna NULL
     */
    private String setearPassword() {
        String password;
        password = System.getProperty(ConeccionProperties.DBPASSWORD.getPropiertie());
        return password;
    }

    /**
     * Obtiene las propiedades de la url de conexión a la BD's
     *
     * @return Las propiedades de la url para la conexión a la BD's obtenida de las variables del sistema
     */
    private String setearPropertisUrl() {
        String property;
        property = System.getProperty(ConeccionProperties.DBPROPERTIESURL.getPropiertie());
        return property;
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
        try {
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase;
            int posicion = 0;
            for (int i = 3; i <= 7; i += 2) {
                clase = elements[i].getClassName();
                Class<?> tempClass = Class.forName(clase);
                if (tempClass.getPackage().hashCode() == Conexion.class.getPackage().hashCode()) {
                    posicion = i;
                    break;
                }
            }
            clase = elements[posicion].getClassName();
            Class<?> tempClass = Class.forName(clase);
            if (tempClass.getPackage().hashCode() != Conexion.class.getPackage().hashCode()) {
                return null;
            }
        } catch (ClassNotFoundException e) {
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
        if (!Objects.isNull(dataBase))
            this.dataBaseType = dataBase;
    }

    /**
     * Obtiene la propiedades extra de la url de conexión a la BD's
     *
     * @return Propiedades extra de la url de conexión a la BD's
     */
    public String getPropertisURL() {
        try {
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase;
            int posicion = 0;
            for (int i = 3; i <= 7; i += 2) {
                clase = elements[i].getClassName();
                Class<?> tempClass = Class.forName(clase);
                if (tempClass.getPackage().hashCode() == Conexion.class.getPackage().hashCode()) {
                    posicion = i;
                    break;
                }
            }
            clase = elements[posicion].getClassName();
            Class<?> tempClass = Class.forName(clase);
            if (tempClass.getPackage().hashCode() != Conexion.class.getPackage().hashCode()) {
                return null;
            }
        } catch (ClassNotFoundException e) {
        }
        return this.propertisURL;
    }

    /**
     * Setea las propiedades extra para la url de conexión a BD's
     *
     * @param propertisURL Propiedades extra para la url de conexión a BD's por ejemplo
     *                     {@literal ?autoReconnect=true&useSSL=false}
     */
    public void setPropertisURL(String propertisURL) {
        if (!stringIsNullOrEmpty(propertisURL))
            this.propertisURL = propertisURL;
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
        try {
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase;
            int posicion = 0;
            for (int i = 3; i <= 7; i += 2) {
                clase = elements[i].getClassName();
                Class<?> tempClass = Class.forName(clase);
                if (tempClass.getPackage().hashCode() == Conexion.class.getPackage().hashCode()) {
                    posicion = i;
                    break;
                }
            }
            clase = elements[posicion].getClassName();
            Class<?> tempClass = Class.forName(clase);
            if (tempClass.getPackage().hashCode() != Conexion.class.getPackage().hashCode()) {
                return null;
            }
        } catch (ClassNotFoundException e) {
        }
        return this.host;
    }

    /**
     * Setea el host en el cual se encuentra la BD's a la cual se conectara el modelo.
     *
     * @param host Host en el cual se encuentra la BD's a la que nos queremos conectar.
     */
    public void setHost(String host) {
        if (!stringIsNullOrEmpty(host))
            this.host = host;
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
        try {
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase;
            int posicion = 0;
            for (int i = 3; i <= 7; i += 2) {
                clase = elements[i].getClassName();
                Class<?> tempClass = Class.forName(clase);
                if (tempClass.getPackage().hashCode() == Conexion.class.getPackage().hashCode()) {
                    posicion = i;
                    break;
                }
            }
            clase = elements[posicion].getClassName();
            Class<?> tempClass = Class.forName(clase);
            if (tempClass.getPackage().hashCode() != Conexion.class.getPackage().hashCode()) {
                return null;
            }
        } catch (ClassNotFoundException e) {
        }
        return this.port;
    }

    /**
     * Setea el puerto en el cual se encuentra escuchando la BD's a la cual se pegara el modelo.
     *
     * @param port Puerto en el cual se encuentra escuchando la BD's a la cual se pegara el modelo.
     */
    public void setPort(String port) {
        if (!stringIsNullOrEmpty(port))
            this.port = port;
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
        try {
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase;
            int posicion = 0;
            for (int i = 3; i <= 7; i += 2) {
                clase = elements[i].getClassName();
                Class<?> tempClass = Class.forName(clase);
                if (tempClass.getPackage().hashCode() == Conexion.class.getPackage().hashCode()) {
                    posicion = i;
                    break;
                }
            }
            clase = elements[posicion].getClassName();
            Class<?> tempClass = Class.forName(clase);
            if (tempClass.getPackage().hashCode() != Conexion.class.getPackage().hashCode()) {
                return null;
            }
        } catch (ClassNotFoundException e) {
        }
        return this.user;
    }

    /**
     * Setea el Usuario con el cual el modelo se conectara a la BD's.
     *
     * @param user Usuario con el cual el modelo se conectara a la BD's.
     */
    public void setUser(String user) {
        if (!stringIsNullOrEmpty(user))
            this.user = user;
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
        try {
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase;
            int posicion = 0;
            for (int i = 3; i <= 7; i += 2) {
                clase = elements[i].getClassName();
                Class<?> tempClass = Class.forName(clase);
                if (tempClass.getPackage().hashCode() == Conexion.class.getPackage().hashCode()) {
                    posicion = i;
                    break;
                }
            }
            clase = elements[posicion].getClassName();
            Class<?> tempClass = Class.forName(clase);
            if (tempClass.getPackage().hashCode() != Conexion.class.getPackage().hashCode()) {
                return null;
            }
        } catch (ClassNotFoundException e) {
        }
        return this.password;
    }

    /**
     * Setea la contraseña del usuario con el cual el modelo se conectara a la BD's.
     *
     * @param password Contraseña del usuario con el cual el modelo se conectara a la BD's.
     */
    public void setPassword(String password) {
        if (!stringIsNullOrEmpty(password))
            this.password = password;
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
        try {
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase;
            int posicion = 0;
            for (int i = 3; i <= 7; i += 2) {
                clase = elements[i].getClassName();
                Class<?> tempClass = Class.forName(clase);
                if (tempClass.getPackage().hashCode() == Conexion.class.getPackage().hashCode()) {
                    posicion = i;
                    break;
                }
            }
            clase = elements[posicion].getClassName();
            Class<?> tempClass = Class.forName(clase);
            if (tempClass.getPackage().hashCode() != Conexion.class.getPackage().hashCode()) {
                return null;
            }
        } catch (ClassNotFoundException e) {
        }
        return this.BD;
    }

    /**
     * Setea el nombre de la Base de Datos a la que se conectara el modelo.
     *
     * @param BD Nombre de la Base de Datos a la que se conectara el modelo.
     */
    public void setBD(String BD) {
        if (!stringIsNullOrEmpty(BD))
            this.BD = BD;
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
        this.getPropertySystem = getPropertySystem;
    }

    /**
     * Obtiene la bandera que indica si la tabla correspondiente al modelo en BD's
     *
     * @return True si la tabla correspondiente al modelo existe en BD's, de lo contrario retorna
     * False.
     */
    public Boolean getTableExist() {
        return this.tableExist;
    }

    /**
     * Setea la bandera que indica si la tabla correspondiente al modelo existe en BD's
     *
     * @param tableExist True si la tabla correspondiente al modelo existe en BD's, de lo contrario False.
     */
    protected synchronized void setTableExist(Boolean tableExist) {
        this.tableExist = tableExist;
    }

    /**
     * Obtiene el nombre de la tabla en BD's correspondiente al modelo.
     *
     * @return Retorna el nombre de la tabla en BD's correspondiente al modelo.
     */
    public synchronized String getTableName() {
        return tableName;
    }

    /**
     * Setea el nombre de la tabla en BD's correspondiente al modelo.
     *
     * @param tableName Nombre de la tabla en BD's correspondiente al modelo.
     */
    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Obtiene la bandera que indica si la tarea que estaba realizando el modelo ha sido terminada
     *
     * @return True si el modelo actualmente no esta realizando una tarea. False si el modelo esta realizando una tarea
     * actualmente.
     */
    public synchronized Boolean getTaskIsReady() {
        return taskIsReady;
    }

    /**
     * Setea el valor de la bandera que indica si el modelo actual esta realizando una tarea
     *
     * @param taskIsReady True si el modelo actualmente no esta realizando una tarea. False si el modelo esta realizando una tarea
     *                    actualmente.
     */
    protected synchronized void setTaskIsReady(Boolean taskIsReady) {
        this.taskIsReady = taskIsReady;
    }

    /**
     * Si queremos utilizar el mismo modelo para realizar otra operación en BD's
     * es necesario que esperemos a que el modelo no este realizando ninguna tarea, relacionada con lectura o
     * escritura.
     * <p>
     * Debido a que estas tareas JBSqlUtils las realiza en segundo plano, para no interrumpir
     * el hilo de ejecución principal y entregar un mejor rendimiento, por si necesitamos realizar alguna otra
     * instrucción mientras el modelo esta trabajando en segundo plano. para poder saber si el modelo actualmente esta
     * ocupado, podemos hacerlo a través del método getTaskIsReady(), el cual obtiene la bandera que indica si
     * la tarea que estaba realizando el modelo ha sido terminada
     * De utilizar otro modelo, no es necesario esperar a que el primer modelo este libre.
     */
    public void waitOperationComplete() {
        /**
         * Si queremos utilizar el mismo modelo para insertar otro registro con valores diferentes,
         * es necesario que esperemos a que el modelo no este realizando ninguna tarea, relacionada con lectura o
         * escritura en la BD's, debido a que estas tareas JBSqlUtils las realiza en segundo plano, para no interrumpir
         * el hilo de ejecución principal y entregar un mejor rendimiento, por si necesitamos realizar alguna otra
         * instrucción mientras el modelo esta trabajando en segundo plano. para poder saber si el modelo actualmente esta
         * ocupado, podemos hacerlo a través del método getTaskIsReady(), el cual obtiene la bandera que indica si
         * la tarea que estaba realizando el modelo ha sido terminada
         */
        while (!this.getTaskIsReady()) {
        }
    }

    /**
     * Obtiene la clave primaria del modelo.
     *
     * @return Nombre de la clave primaria del modelo
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Setea la clave primaria del modelo
     *
     * @param primaryKey Nombre de la columna que sirve como clave primaria del modelo
     */
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Obtiene la bandera que indica si la clave primaria del modelo es autoincrementable.
     *
     * @return Retorna True si la clave primaria es autoincrementable.
     */
    public Boolean getPrimaryKeyIsIncremental() {
        return primaryKeyIsIncremental;
    }

    /**
     * Setea la información sobre si la clave primaria es autoincrementable.
     *
     * @param primaryKeyIsIncremental True si la clave primaria es autoincrementable, False si no lo es.
     */
    public void setPrimaryKeyIsIncremental(Boolean primaryKeyIsIncremental) {
        this.primaryKeyIsIncremental = primaryKeyIsIncremental;
    }

    /**
     * Obtiene la bandera que define si el modelo desea que JBSqlUtils maneje las timestamps Created_at, Update_at.
     *
     * @return True si JBSqlUtils manejara de forma predeterminada las timestamps del modelo, False si no se
     * desea que JBSqlUtils registre esta información
     */
    protected Boolean getTimestamps() {
        return timestamps;
    }

    /**
     * Setea la bandera que define si el modelo desea que JBSqlUtils maneje las timestamps Created_at, Update_at.
     *
     * @param timestamps True si las timestamps serán manejadas por JBSqlUtils, False, si el modelo no tiene estas
     *                   columnas.
     */
    public void setTimestamps(Boolean timestamps) {
        this.timestamps = timestamps;
    }

    /**
     * Obtiene el nombre de la columna correspondiente a la TimeStamp CreateAT
     *
     * @return Nombre de la columna correspondiente a la TimeStamp CreateAT
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setea el nombre de la columna correspondiente a la TimeStamp CreateAT
     *
     * @param createdAt Nombre de la columna correspondiente a la TimeStamp CreateAT
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Obtiene el nombre de la columna correspondiente a la TimeStamp UpdateAT
     *
     * @return Nombre de la columna correspondiente a la TimeStamp UpdateAT
     */
    public String getUpdateAT() {
        return updateAT;
    }

    /**
     * Setea el nombre de la columna correspondiente a la TimeStamp UpdateAT
     *
     * @param updateAT Nombre de la columna correspondiente a la TimeStamp UpdateAT
     */
    public void setUpdateAT(String updateAT) {
        this.updateAT = updateAT;
    }

    /**
     * Obtiene la Bandera que sirve para identificar si el modelo existe en BD's, de existir cuando se
     * llame al metodo save se procedera a actualizar el modelo
     *
     * @return TRUE indica que el modelo fue obtenido de BD's,
     * False indica que el modelo no existe en BD's
     */
    public Boolean getModelExist() {
        return modelExist;
    }

    /**
     * Setea la Bandera que sirve para identificar si el modelo existe en BD's, de existir cuando se llame
     * al metodo save se procedera a actualizar el modelo
     *
     * @param modelExist Bandera que sirve para identificar si el modelo existe en BD's, TRUE indica que el modelo fue obtenido de BD's
     *                   False indica que el modelo no existe en BD's
     */
    public void setModelExist(Boolean modelExist) {
        this.modelExist = modelExist;
    }

    /**
     * Representa la metadata de la tabla correspondiente al modelo en BD's
     * Retorna la TablaSQL correspondiente al modelo
     */
    protected synchronized TablesSQL getTabla() {
        return tabla;
    }

    /**
     * Setea la tabla que representa al modelo en BD's
     *
     * @param tabla Objeto TableSQL que contiene parte de la meta data de la tabla correspondiente al modelo
     */
    protected void setTabla(TablesSQL tabla) {
        this.tabla = tabla;
    }

    /**
     * Obtiene la lista de los métodos get del modelo que lo invoca.
     *
     * @return Lista de los métodos get del modelo que lo invoca.
     */
    protected synchronized List<Method> getMethodsGetOfModel() {
        if (Objects.isNull(MethodsGetOfModel)) {
            MethodsGetOfModel = new ArrayList<Method>();
        }
        return MethodsGetOfModel;
    }

    /**
     * Obtiene la lista de los métodos set del modelo que lo invoca.
     *
     * @return Lista de los métodos set del modelo que lo invoca.
     */
    protected synchronized List<Method> getMethodsSetOfModel() {
        if (Objects.isNull(MethodsSetOfModel)) {
            MethodsSetOfModel = new ArrayList<Method>();
        }
        return MethodsSetOfModel;
    }

    /**
     * Cantidad de conexiones que ha realizado el modelo a BD's
     *
     * @return Cantidad de conexiones que ha realizado el modelo a BD's
     */
    public synchronized Integer getContadorConexiones() {
        return contadorConexiones;
    }

    /**
     * Setea la cantidad de conexiones que a realizado el modelo
     *
     * @param contadorConexiones Cantidad de conexiones que a realizado el modelo
     */
    protected synchronized void setContadorConexiones(Integer contadorConexiones) {
        this.contadorConexiones = contadorConexiones;
    }

    /**
     * Lista de Field's que posee el modelo mapeados con la tabla correspondiente en BD's
     *
     * @return
     */
    public List<Field> getFieldsOfModel() {
        if (Objects.isNull(fieldsOfModel)) {
            fieldsOfModel = new ArrayList<Field>();
        }
        return fieldsOfModel;
    }
}

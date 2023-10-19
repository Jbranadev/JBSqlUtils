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
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Clase a ser heredada por los modelos, la cual brinda acceso a los métodos necesarios para que el modelo
 * se pegue a la BD's especifícada y pueda realizar las operaciónes necesarias sobre la misma.
 *
 * @author Jose Bran
 */
public class JBSqlUtils extends Methods {

    private Column<Timestamp> created_at1 = new Column<>(DataType.TIMESTAMP);

    private Column<Timestamp> updated_at1 = new Column<>(DataType.TIMESTAMP);

    private Timestamp created_at;

    private Timestamp updated_at;

    /**
     * Constructor por defecto de la Clase JBSqlUtils
     */
    public JBSqlUtils() {
        super();
    }

    /**
     * Constructor por defecto de la Clase JBSqlUtils
     *
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    public JBSqlUtils(Boolean getPropertySystem) {
        super(getPropertySystem);
    }

    /**
     * Setea el nombre de la Base de Datos global a la que se conectaran los modelos que no tengan una configuración
     * personalizada.
     *
     * @param BD Nombre de la Base de Datos.
     */
    public static void setDataBaseGlobal(String BD) {
        if (!UtilitiesJB.stringIsNullOrEmpty(BD))
            System.setProperty(ConeccionProperties.DBNAME.getPropiertie(), BD);
    }

    /**
     * Setea la Contraseña del usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param password Contraseña del usuario con el cual se conectara a la BD's.
     */
    public static void setPasswordGlobal(String password) {
        if (!UtilitiesJB.stringIsNullOrEmpty(password))
            System.setProperty(ConeccionProperties.DBPASSWORD.getPropiertie(), password);
    }

    /**
     * Setea el Usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param user Usuario con el cual se conectara a la BD's.
     */
    public static void setUserGlobal(String user) {
        if (!UtilitiesJB.stringIsNullOrEmpty(user))
            System.setProperty(ConeccionProperties.DBUSER.getPropiertie(), user);
    }

    /**
     * Setea el puerto global con el que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param port Puerto en el cual se encuentra escuchando la BD's a la cual se pegaran los modelos.
     */
    public static void setPortGlobal(String port) {
        if (!UtilitiesJB.stringIsNullOrEmpty(port))
            System.setProperty(ConeccionProperties.DBPORT.getPropiertie(), port);
    }

    /**
     * Setea el host en el cual se encuentra la BD's global a la cual se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param host Host en el cual se encuentra la BD's a la que nos queremos conectar.
     */
    public static void setHostGlobal(String host) {
        if (!UtilitiesJB.stringIsNullOrEmpty(host))
            System.setProperty(ConeccionProperties.DBHOST.getPropiertie(), host);
    }

    /**
     * Setea el tipo de BD's global a la cual se estaran conectando los modelos que no tengan una configuración personalizada.
     *
     * @param dataBase Tipo de BD's a la cual se estaran los modelos que no tengan una configuración personalizada, los tipos disponibles son
     *                 MySQL,
     *                 SQLServer,
     *                 PostgreSQL,
     *                 SQLite.
     */
    public static void setDataBaseTypeGlobal(DataBase dataBase) {
        if (!Objects.isNull(dataBase))
            System.setProperty(ConeccionProperties.DBTYPE.getPropiertie(), dataBase.name());
    }

    /**
     * Setea las propiedades extra de conexión url DB que pueden utilizar los modelos para conectarse a BD's
     *
     * @param propertisUrl Propiedades extra para la url de conexión a BD's por ejemplo
     *                     {@literal ?autoReconnect=true&useSSL=false}
     */
    public static void setPropertisUrlConexionGlobal(String propertisUrl) {
        if (!UtilitiesJB.stringIsNullOrEmpty(propertisUrl))
            System.setProperty(ConeccionProperties.DBPROPERTIESURL.getPropiertie(), propertisUrl);
    }

    /**
     * Proporciona un método de entrada para crear una tabla en BD's a través del generador SQL de JBSqlUtils
     *
     * @param tableName Nombre de la Tabla que deseamos crear
     * @return Objeto que brinda acceso a los métodos con la logica para el execute
     * @throws ValorUndefined Si no se define tableName lanza esta excepción
     */
    public static CreateTable createTable(String tableName) throws ValorUndefined {
        return new CreateTable(tableName);
    }

    /**
     * Proporciona un método de entrada para eliminar una tabla en BD's a través del generador SQL de JBSqlUtils
     *
     * @param tableName Nombre de la Tabla que deseamos eliminar
     * @return Objeto que brinda acceso a los métodos con la logica para el execute
     * @throws ValorUndefined Si no se define tableName lanza esta excepción
     */
    public static DropTableIfExist dropTableIfExist(String tableName) throws ValorUndefined {
        return new DropTableIfExist(tableName);
    }

    /**
     * Proporciona un método de entrada por medio del cual podemos insertar registros a una tabla en BD's a través del
     * generador SQL de JBSqlUtils
     *
     * @param tableName Nombre de la tabla a la que deseamos insertar registros
     * @return Objeto que brinda acceso a los métodos con la logica para el execute
     * @throws ValorUndefined Si no se define tableName lanza esta excepción
     */
    public static InsertInto insertInto(String tableName) throws ValorUndefined {
        return new InsertInto(tableName);
    }

    /**
     * Proporciona un método de entrada para realizar una consulta a una tabla en BD's a través del generador SQL de JBSqlUtils
     *
     * @param tableName Nombre de la tabla que deseamos consultar
     * @return Objeto que brinda acceso a los metodos con la logica para el execute
     * @throws ValorUndefined Si no se define tableName lanza esta excepción
     */
    public static Select select(String tableName) throws ValorUndefined {
        return new Select(tableName);
    }

    /**
     * Actualiza las filas de la tabla proporcionada, de acuerdo a la logica de la consulta generada.
     *
     * @param tableName Nombre de la tabla que deseamos actualizar
     * @return Retorna un objeto de la clase Update que proporciona los metodos y lógica necesaria para realizar la
     * actualización de registros en BD's sin haberlos recuperados.
     * @throws ValorUndefined Lanza esta excepción si el parámetro proporcionado esta Vacío o es NULL.
     */
    public static Update update(String tableName) throws ValorUndefined {
        return new Update(tableName);
    }

    /**
     * Elimina las filas de la tabla proporcionada, de acuerdo a la consulta generada.
     *
     * @param tableName Nombre de la tabla de la cual queremos eliminar los registros que posee
     * @return Retorna un objeto de la clase Delete que proporciona los metodos y lógica necesaria para eliminar los
     * registros en BD's sin haberlos recuperados.
     * @throws ValorUndefined Lanza esta excepción si el parámetro proporcionado esta Vacío o es NULL.
     */
    public static Delete delete(String tableName) throws ValorUndefined {
        return new Delete(tableName);
    }

    /**
     * Obtiene la fecha de creación del modelo
     *
     * @return TimeStamp correspondiente a la fecha de creación del registro en BD's
     */
    public Column<Timestamp> getCreated_at1() {
        if (Objects.isNull(created_at1.getValor())) {
            Long datetime = System.currentTimeMillis();
            created_at1.setValor(new Timestamp(datetime));
            return created_at1;
        }
        return created_at1;
    }

    /**
     * Setea la TimeStamp correspondiente a la fecha de creación del registro en BD's
     *
     * @param created_at TimeStamp correspondiente a la fecha de creación del registro en BD's
     */
    public void setCreated_at1(Column<Timestamp> created_at) {
        this.created_at1 = created_at;
    }

    /**
     * Obtiene la fecha de actualización del modelo
     *
     * @return TimeStamp correspondiente a la fecha de actualización del registro en BD's
     */
    public Column<Timestamp> getUpdated_at1() {
        if (Objects.isNull(updated_at1.getValor())) {
            Long datetime = System.currentTimeMillis();
            updated_at1.setValor(new Timestamp(datetime));
            return updated_at1;
        }
        return updated_at1;
    }

    /**
     * Setea la TimeStamp correspondiente a la fecha de actualización del registro en BD's
     *
     * @param updated_at TimeStamp correspondiente a la fecha de actualización del registro en BD's
     */
    public void setUpdated_at1(Column<Timestamp> updated_at) {
        this.updated_at1 = updated_at;
    }

    /**
     * Obtiene la fecha de creación del modelo
     *
     * @return TimeStamp correspondiente a la fecha de creación del registro en BD's
     */
    public Timestamp getCreated_at() {
        if (Objects.isNull(created_at)) {
            Long datetime = System.currentTimeMillis();
            created_at = new Timestamp(datetime);
            return created_at;
        }
        return created_at;
    }

    /**
     * Setea la TimeStamp correspondiente a la fecha de creación del registro en BD's
     *
     * @param created_at TimeStamp correspondiente a la fecha de creación del registro en BD's
     */
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    /**
     * Obtiene la fecha de actualización del modelo
     *
     * @return TimeStamp correspondiente a la fecha de actualización del registro en BD's
     */
    public Timestamp getUpdated_at() {
        if (Objects.isNull(updated_at)) {
            Long datetime = System.currentTimeMillis();
            updated_at = new Timestamp(datetime);
            return updated_at;
        }
        return updated_at;
    }

    /**
     * Setea la TimeStamp correspondiente a la fecha de actualización del registro en BD's
     *
     * @param updated_at TimeStamp correspondiente a la fecha de actualización del registro en BD's
     */
    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}

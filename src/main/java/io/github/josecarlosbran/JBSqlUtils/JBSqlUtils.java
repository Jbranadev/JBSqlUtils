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
import io.github.josecarlosbran.JBSqlUtils.DataBase.Delete;
import io.github.josecarlosbran.JBSqlUtils.DataBase.Update;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Jose Bran
 * Clase a ser heredada por los modelos, la cual brinda acceso a los metodos necesarios para que el modelo
 * se pegue a la BD's especifícada y pueda realizar las operaciónes necesarias sobre la misma.
 */
public class JBSqlUtils extends Methods {

    private Column<Timestamp> created_at = new Column<>(DataType.TIMESTAMP);

    private Column<Timestamp> updated_at = new Column<>(DataType.TIMESTAMP);


    /**
     * Constructor por defecto de la Clase JBSqlUtils
     *
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public JBSqlUtils() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    /**
     * Setea el nombre de la Base de Datos global a la que se conectaran los modelos que no tengan una configuración
     * personalizada.
     *
     * @param BD Nombre de la Base de Datos.
     */
    public static void setDataBaseGlobal(String BD) {
        try {
            System.setProperty("DataBaseBD", BD);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBaseBD"));
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el nombre de la Base de Datos global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Setea la Contraseña del usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param password Contraseña del usuario con el cual se conectara a la BD's.
     */
    public static void setPasswordGlobal(String password) {
        try {
            System.setProperty("DataBasePassword", password);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBasePassword"));
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea la contraseña del usuario de BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Setea el Usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param user Usuario con el cual se conectara a la BD's.
     */
    public static void setUserGlobal(String user) {
        try {
            System.setProperty("DataBaseUser", user);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBaseUser"));

        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el usuario de BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Setea el puerto global con el que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param port Puerto en el cual se encuentra escuchando la BD's a la cual se pegaran los modelos.
     */
    public static void setPortGlobal(String port) {
        try {
            System.setProperty("DataBasePort", port);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBasePort"));

        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el Puerto de BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Setea el host en el cual se encuentra la BD's global a la cual se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param host Host en el cual se encuentra la BD's a la que nos queremos conectar.
     */
    public static void setHostGlobal(String host) {
        try {
            System.setProperty("DataBaseHost", host);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBaseHost"));
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el Host de la BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

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
        try {
            System.setProperty("DataBase", dataBase.name());
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBase"));
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el Tipo de BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }


    /**
     * @return TimeStamp correspondiente a la fecha de creación del registro en BD's
     */
    public Column<Timestamp> getCreated_at() {
        if (Objects.isNull(created_at.getValor())) {
            Long datetime = System.currentTimeMillis();
            created_at.setValor(new Timestamp(datetime));
            return created_at;
        }
        return created_at;
    }

    /**
     * Setea la TimeStamp correspondiente a la fecha de creación del registro en BD's
     *
     * @param created_at TimeStamp correspondiente a la fecha de creación del registro en BD's
     */
    public void setCreated_at(Column<Timestamp> created_at) {
        this.created_at = created_at;
    }

    /**
     * @return TimeStamp correspondiente a la fecha de actualización del registro en BD's
     */
    public Column<Timestamp> getUpdated_at() {
        if (Objects.isNull(updated_at.getValor())) {
            Long datetime = System.currentTimeMillis();
            updated_at.setValor(new Timestamp(datetime));
            return updated_at;
        }
        return updated_at;
    }

    /**
     * Setea la TimeStamp correspondiente a la fecha de actualización del registro en BD's
     *
     * @param updated_at TimeStamp correspondiente a la fecha de actualización del registro en BD's
     */
    public void setUpdated_at(Column<Timestamp> updated_at) {
        this.updated_at = updated_at;
    }


    /**
     * Actualiza las filas de la tabla proporcionada, de acuerdo a la logica de la consulta generada.
     *
     * @param tableName Nombre de la tabla que deseamos actualizar
     * @return Retorna un objeto de la clase Update que proporciona los metodos y lógica necesaria para realizar la
     * actualización de registros en BD's sin haberlos recuperados.
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado esta Vacío o es NULL.
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
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado esta Vacío o es NULL.
     */
    public static Delete delete(String tableName) throws ValorUndefined {
        return new Delete(tableName);
    }


}

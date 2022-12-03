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
package io.github.josecarlosbran.JBSqlUtils.Enumerations;

/**
 * @author Jose Bran
 * Enumeración que permite indicar a que Base de Datos se estara conectando el modelo.
 */
public enum DataBase {

    /**
     * SQLite
     */
    SQLite("sqlite"),

    /**
     * MySQL
     */
    MySQL("mysql"),

    /**
     * SQL Server
     */
    SQLServer("sqlserver"),

    /**
     * PostgreSQL
     */
    PostgreSQL("postgresql");

    private String DBType;

    private DataBase(String s) {
        this.setDBType(s);
    }

    /**
     * Retorna el tipo de BD's a la que se estara conectando JBSqlUtils
     *
     * @return Retorna el tipo de BD's a la que se estara conectando JBSqlUtils en un String
     */
    public String getDBType() {
        return DBType;
    }

    private void setDBType(String DBType) {
        this.DBType = DBType;
    }
}
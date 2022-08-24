package io.github.josecarlosbran.JBSqlLite.Enumerations;

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
     * @return Retorna el tipo de BD's a la que se estara conectando JBSqlUtils en un String
     */
    public String getDBType() {
        return DBType;
    }

    private void setDBType(String DBType) {
        this.DBType = DBType;
    }
}

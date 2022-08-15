package io.github.josecarlosbran.JBSqlLite.Enumerations;

/**
 * @author Jose Bran
 * Enumeración que permite indicar a que Base de Datos se estara conectando el modelo.
 */
public enum DataBase {

    SQLite("sqlite"),
    MySQL("mysql"),
    SQLServer("sqlserver"),
    PostgreSQL("postgresql");

    private String DBType;

    private DataBase(String s) {
        this.setDBType(s);
    }

    public String getDBType() {
        return DBType;
    }

    private void setDBType(String DBType) {
        this.DBType = DBType;
    }
}

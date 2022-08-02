package io.github.josecarlosbran.JBSqlLite.Enumerations;

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

    public void setDBType(String DBType) {
        this.DBType = DBType;
    }
}

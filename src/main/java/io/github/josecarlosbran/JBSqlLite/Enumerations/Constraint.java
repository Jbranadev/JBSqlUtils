package io.github.josecarlosbran.JBSqlLite.Enumerations;


public enum Constraint {

    NOT_NULL("NOT NULL"),

    UNIQUE("UNIQUE"),

    CHECK("CHECK"),

    PRIMARY_KEY("PRIMARY KEY"),

    FOREIGN_KEY("FOREIGN KEY"),

    CURRENT_TIMESTAMP("CURRENT_TIMESTAMP");

    private String restriccion;

    private Constraint(String Restriccion){
        this.restriccion=Restriccion;
    }



}

package io.github.josecarlosbran.JBSqlLite.Enumerations;

public enum Operator {
    MAYOR_IGUAL_QUE(" >= "),

    MAYOR_QUE(" > "),

    IGUAL_QUE(" = "),

    DISTINTO(" <> "),

    MENOR_QUE(" < "),

    MENOR_IGUAL_QUE(" <= "),


    LIKE(" LIKE "),

    OPEN_PARENTESIS(" ("),

    CLOSE_PARENTESIS(" )"),

    ORDERBY(" ORDER BY "),


    AND(" AND "),

    OR(" OR "),

    NOT(" NOT ");





    private String operador;
    private Operator(String s){
        this.operador=s;
    }

    public String getOperador() {
        return operador;
    }



}

package io.github.josecarlosbran.JBSqlLite.Enumerations;

/**
 * @author Jose Bran
 * Enumeración que proporciona acceso a los diferentes tipos de operadores disponibles para realizar
 * consultas SQL Personalizadas.
 */
public enum Operator {

    /**
     * Operador >=
     */
    MAYOR_IGUAL_QUE(" >= "),
    /**
     * Operador >
     */
    MAYOR_QUE(" > "),
    /**
     * Operador =
     */
    IGUAL_QUE(" = "),
    /**
     * Operador <>
     */
    DISTINTO(" <> "),
    /**
     * Operador <
     */
    MENOR_QUE(" < "),
    /**
     * Operador <=
     */
    MENOR_IGUAL_QUE(" <= "),

    /**
     * Operador LIKE
     */
    LIKE(" LIKE "),
    /**
     * Operador (
     */
    OPEN_PARENTESIS(" ("),
    /**
     * Operador )
     */
    CLOSE_PARENTESIS(" )"),
    /**
     * Operador ORDER BY
     */
    ORDERBY(" ORDER BY "),

    /**
     * Operador AND
     */
    AND(" AND "),

    /**
     * Operador OR
     */
    OR(" OR "),

    /**
     * Operador NOT
     */
    NOT(" NOT ");





    private String operador;
    private Operator(String s){
        this.operador=s;
    }

    public String getOperador() {
        return operador;
    }



}

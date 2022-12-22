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

import java.util.Arrays;
import java.util.List;

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

    private Operator(String s) {
        this.operador=s;
    }

    public String getOperador() {
        return operador;
    }

    /**
     * Retorna la numeración correspondiente al nombre proporcionado
     * @param name Nombre de la Numeración que se desea obtener
     * @return Numeración correspondiente al nombre proporcionado
     */
    public Operator getNumeracionforName(String name) {
        Operator respuesta=Operator.OR;
        Class<Operator> esta=Operator.class;
        Operator[] temp= esta.getEnumConstants();
        List<Operator> numeraciones= Arrays.asList(temp);
        for(Operator numeracion:numeraciones){
            if(numeracion.name().equalsIgnoreCase(name)){
                /*LogsJB.info("Nombre: "+numeracion.name()+" Posicion Ordinal: "+numeracion.ordinal()
                        +" operador: "+numeracion.getOperador());*/
                respuesta=numeracion;
            }
        }
        return respuesta;
    }
}

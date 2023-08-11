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

import java.util.Arrays;
import java.util.List;

/**
 * @author Jose Bran
 * Enumeración que permite realizar la converción de datos de SQL a Java
 */
enum SQLtoJava {
    /**
     * Cadena de caracteres de longitud fija
     */
    CHAR("java.lang.String"),

    /**
     * Cadena de caracteres de longitud variable
     */
    VARCHAR("java.lang.String"),

    /**
     * Cadenas de cualquier longitud (varios megabytes)
     */
    LONGVARCHAR("java.lang.String"),

    /**
     * Valores decimales de precisión absoluta
     */
    NUMERIC("java.lang.Bignum"),

    /**
     * Valor decimal de precisión absoluta
     */
    DECIMAL("java.lang.Bignum"),

    /**
     * Valor decimal de precisión absoluta
     */
    MONEY("java.lang.BigDecimal"),

    /**
     * Valor decimal de precisión absoluta
     */
    SMALLMONEY("java.lang.BigDecimal"),

    /**
     * Bit único/valor binario (activado o desactivado)
     */
    BIT("java.lang.Boolean"),

    /**
     * entero de 16 bits
     */
    SMALLINT("short"),

    /**
     * entero de 16 bits
     */
    TINYINT("short"),

    //java.lang.Integer

    /**
     * Entero de 32 bits con signo
     */
    INTEGER("int"),

    //java.lang.Integer

    /**
     * Valor de coma flotante
     */
    REAL("java.lang.Float"),

    /**
     * Valor de coma flotante
     */
    FLOAT("java.lang.Float"),

    //double

    //java.lang.Double

    /**
     * Gran valor de punto flotante
     */
    DOUBLE("java.lang.Double"),

    /**
     * Matriz de valores binarios
     */
    BINARY("byte[]"),

    /**
     * Matriz de longitud variable de valores binarios
     */
    VARBINARY("byte[]"),

    /**
     * Matriz de valores binarios de cualquier longitud (varios megabytes)
     */
    LONGVARBINARY("byte[]"),


    /**
     * Valor de fecha
     */
    DATE("java.sql.Date"),

    /**
     * Valor del tiempo
     */
    TIME("java.sql.Time"),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    TIMESTAMP("java.sql.Timestamp"),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    SMALLDATETIME("java.sql.Timestamp"),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    DATETIME("java.sql.Timestamp"),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    DATETIME2("java.sql.Timestamp");
    private String typeJava;

    private SQLtoJava(String tipo) {
        this.typeJava = tipo;
    }

    /***
     * Obtiene el nombre de la clase del tipo equivalente en java al tipo SQL.
     * @return Retorna el nombre de la clase del tipo equivalente en java al tipo SQL.
     */
    public String getTypeJava() {
        return typeJava;
    }


    /**
     * Retorna la numeración correspondiente al nombre proporcionado
     *
     * @param name Nombre de la Numeración que se desea obtener
     * @return Numeración correspondiente al nombre proporcionado
     */
    public SQLtoJava getNumeracionforName(String name) {
        Class<SQLtoJava> esta = SQLtoJava.class;
        SQLtoJava[] temp = esta.getEnumConstants();
        List<SQLtoJava> numeraciones = Arrays.asList(temp);
        for (SQLtoJava numeracion : numeraciones) {
            if (numeracion.name().equalsIgnoreCase(name)) {
                /*LogsJB.info("Nombre: "+numeracion.name()+" Posicion Ordinal: "+numeracion.ordinal()
                        +" operador: "+numeracion.getOperador());*/
                return numeracion;
            }
        }
        return null;
    }


}

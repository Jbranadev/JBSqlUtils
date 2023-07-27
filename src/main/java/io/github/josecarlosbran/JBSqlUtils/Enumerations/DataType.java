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

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * @author Jose Bran
 * Enumeración que sirve para identificar el tipo de dato SQL que tiene la columna en el servidor.
 */
public enum DataType {

    //java.lang.Integer

    /**
     * Entero de 32 bits con signo
     */
    INTEGER("", 1),

    /**
     * Entero de 32 bits con signo
     */
    INT("", 1),

    /**
     * Tipo de dato auto incrementable en SQL Server
     */
    IDENTITY("1,1", 1),

    /**
     * Tipo de dato auto incrementable en PostgreSQL
     */
    SERIAL("SERIAL", 1),
    //java.lang.Integer


    /**
     * Cadena de caracteres de longitud fija
     */
    CHAR("1", 2),

    /**
     * Cadena de caracteres de longitud variable
     */
    VARCHAR("21844", 2),

    /**
     * Cadenas de cualquier longitud (varios megabytes), debe definir el tamaño que desea tenga la columna
     * por medio del metodo setSize, para esta numeración en especifico.
     */
    LONGVARCHAR("", 2),

    /**
     * Enum se tienen que definir las opciones disponibles a través del metodo setSize, envíando como parametro
     * para esta numeración, las opciones que deseamos tenga disponible
     */
    ENUM("", 2),


    /**
     * entero de 16 bits
     */
    SMALLINT("", 3),

    /**
     * entero de 16 bits
     */
    TINYINT("", 3),


    /**
     * Valor de coma flotante
     */
    REAL("", 4),

    /**
     * Valor de coma flotante
     */
    FLOAT("", 4),

    //double

    //java.lang.Double

    /**
     * Gran valor de punto flotante
     */
    DOUBLE("38,3", 4),


    /**
     * Valores decimales de precisión absoluta
     */
    NUMERIC("38,2", 4),

    /**
     * Valor decimal de precisión absoluta
     */
    DECIMAL("38,3", 4),

    /**
     * Valor decimal de precisión absoluta
     */
    MONEY("38,2", 4),

    /**
     * Valor decimal de precisión absoluta
     */
    SMALLMONEY("18,2", 4),


    /**
     * Bit único/valor binario (activado o desactivado)
     */
    BIT("", 5),

    /**
     * Valor Booleano
     */
    BOOLEAN("", 5),

    /**
     * Valor Booleano
     */
    BOOL("", 5),


    /**
     * Matriz de valores binarios
     */
    BINARY("1", 6),

    /**
     * Matriz de longitud variable de valores binarios, en mysql el valor maximo es de 21844
     * pero en sql server es de 8000
     */
    VARBINARY("8000", 6),

    /**
     * Matriz de valores binarios de cualquier longitud (varios megabytes)
     * SQL Server
     */
    LONGVARBINARY("MAX", 6),


    /**
     * Cadena binaria de ancho variable
     */
    IMAGE("", 6),


    /**
     * Setea que el tipo de dato será un Object
     */
    OBJECT("", 7),

    /**
     * Setea que el tipo de dato será un JSON
     */
    JSON("", 7),


    /**
     * Valor de fecha
     */
    DATE("", 8),


    /**
     * Valor del tiempo
     */
    TIME("", 9),


    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    TIMESTAMP("", 10),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    SMALLDATETIME("", 10),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    DATETIME("", 10),


    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    DATETIME2("", 10);


    private String size;

    private int orden;

    private DataType(String Size, int orden) {
        this.size = Size;
        this.orden = orden;
    }

    /**
     * Obtiene el Valor que tendra entre parentecis el tipo de dato, por lo general sería
     * Varchar(size), pero de ser otro tipo de dato por ejemplo Identity(1,1), si usted desea modificar
     * el contenido de identity entre parentecis puede hacerlo a travez del metodo SetSize(Size);
     *
     * @return el Valor que tendra entre parentecis el tipo de dato.
     */
    public String getSize() {
        return size;
    }

    /**
     * Obtiene el nombre del tipo de Dato en SQL
     *
     * @return Retorna el nombre del tipo de dato en SQL si este no necesita la especificación de un tamaño.
     * Ejemplo: Datatime retornara Datatime
     * Varchar retornara Varchar(Size).
     * El Size puede ser manipulado a travez del metodo SetSize(Size);
     */
    public String toString() {
        if (stringIsNullOrEmpty(this.getSize())) {
            return this.name();
        } else {
            return this.name() + "(" + this.getSize() + ")";
        }
    }

    /**
     * Setea el Valor que tendra entre parentecis el tipo de dato, por lo general sería
     * Varchar(size), pero de ser otro tipo de dato por ejemplo Identity(1,1), si usted desea modificar
     * el contenido de identity entre parentecis puede hacerlo a travez del metodo SetSize(Size);
     *
     * @param Size Cadena que representa el contenido del tipo de dato entre Parentesis.
     */
    private void setSize(String Size) {
        this.size = Size;
    }

    /**
     * Obtiene el orden del tipo de dato
     *
     * @return Retorna el nivel de orden definido para el tipo de dato
     */
    public int getOrden() {
        return orden;
    }


    /**
     * Retorna la numeración correspondiente al nombre proporcionado
     *
     * @param name Nombre de la Numeración que se desea obtener
     * @return Numeración correspondiente al nombre proporcionado
     */
    public DataType getNumeracionforName(String name) {
        Class<DataType> esta = DataType.class;
        DataType[] temp = esta.getEnumConstants();
        List<DataType> numeraciones = Arrays.asList(temp);
        for (DataType numeracion : numeraciones) {
            if (numeracion.name().equalsIgnoreCase(name)) {
                /*LogsJB.info("Nombre: "+numeracion.name()+" Posicion Ordinal: "+numeracion.ordinal()
                        +" operador: "+numeracion.getOperador());*/
                return numeracion;
            }
        }
        return null;
    }
}

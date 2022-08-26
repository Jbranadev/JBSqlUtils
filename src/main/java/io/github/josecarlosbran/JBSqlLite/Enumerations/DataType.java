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
package io.github.josecarlosbran.JBSqlLite.Enumerations;


import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * @author Jose Bran
 * Enumeración que sirve para identificar el tipo de dato SQL que tiene la columna en el servidor.
 */
public enum DataType {

    /**
     * Cadena de caracteres de longitud fija
     */
    CHAR("1"),

    /**
     * Cadena de caracteres de longitud variable
     */
    VARCHAR("254"),

    /**
     * Cadenas de cualquier longitud (varios megabytes)
     */
    LONGVARCHAR(""),

    /**
     * Valores decimales de precisión absoluta
     */
    NUMERIC(""),

    /**
     * Valor decimal de precisión absoluta
     */
    DECIMAL(""),

    /**
     * Valor decimal de precisión absoluta
     */
    MONEY(""),

    /**
     * Valor decimal de precisión absoluta
     */
    SMALLMONEY(""),

    /**
     * Bit único/valor binario (activado o desactivado)
     */
    BIT(""),

    /**
     * Valor Booleano
     */
    BOOLEAN(""),

    /**
     * Valor Booleano
     */
    BOOL(""),

    /**
     * entero de 16 bits
     */
    SMALLINT(""),

    /**
     * entero de 16 bits
     */
    TINYINT(""),

    //java.lang.Integer

    /**
     * Entero de 32 bits con signo
     */
    INTEGER(""),

    /**
     * Entero de 32 bits con signo
     */
    INT(""),

    /**
     * Tipo de dato auto incrementable en SQL Server
     */
    IDENTITY("1,1"),

    /**
     * Tipo de dato auto incrementable en PostgreSQL
     */
    SERIAL("SERIAL"),
    //java.lang.Integer

    /**
     * Valor de coma flotante
     */
    REAL(""),

    /**
     * Valor de coma flotante
     */
    FLOAT(""),

    //double

    //java.lang.Double

    /**
     * Gran valor de punto flotante
     */
    DOUBLE(""),

    /**
     * Matriz de valores binarios
     */
    BINARY(""),

    /**
     * Matriz de longitud variable de valores binarios
     */
    VARBINARY(""),

    /**
     * Matriz de valores binarios de cualquier longitud (varios megabytes)
     */
    LONGVARBINARY(""),


    /**
     * Valor de fecha
     */
    DATE(""),

    /**
     * Valor del tiempo
     */
    TIME(""),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    TIMESTAMP(""),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    SMALLDATETIME(""),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    DATETIME(""),

    /**
     * Valor de tiempo con campo adicional de nanosegundos
     */
    DATETIME2("");
    private String size;

    private DataType(String Size){
        this.size=Size;
    }

    /**
     * Obtiene el Valor que tendra entre parentecis el tipo de dato, por lo general sería
     * Varchar(size), pero de ser otro tipo de dato por ejemplo Identity(1,1), si usted desea modificar
     * el contenido de identity entre parentecis puede hacerlo a travez del metodo SetSize(Size);
     * @return el Valor que tendra entre parentecis el tipo de dato.
     */
    public String getSize() {
        return size;
    }

    /**
     * Obtiene el nombre del tipo de Dato en SQL
     * @return Retorna el nombre del tipo de dato en SQL si este no necesita la especificación de un tamaño.
     * Ejemplo: Datatime retornara Datatime
     *          Varchar retornara Varchar(Size).
     * El Size puede ser manipulado a travez del metodo SetSize(Size);
     */
    public String toString(){
        if(stringIsNullOrEmpty(this.getSize())){
            return this.name();
        }else{
            return this.name()+"("+this.getSize()+")";
        }
    }

    /**
     * Setea el Valor que tendra entre parentecis el tipo de dato, por lo general sería
     * Varchar(size), pero de ser otro tipo de dato por ejemplo Identity(1,1), si usted desea modificar
     * el contenido de identity entre parentecis puede hacerlo a travez del metodo SetSize(Size);
     * @param Size Cadena que representa el contenido del tipo de dato entre Parentesis.
     */
    public void setSize(String Size){
        this.size=Size;
    }
}

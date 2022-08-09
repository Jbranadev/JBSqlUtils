package io.github.josecarlosbran.JBSqlLite.Enumerations;


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

    /***
     * Obtiene el nombre de la clase del tipo equivalente en java al tipo SQL.
     * @return Retorna el nombre de la clase del tipo equivalente en java al tipo SQL.
     */
    public String getSize() {
        return size;
    }

    public void setSize(String Size){
        this.size=Size;
    }
}

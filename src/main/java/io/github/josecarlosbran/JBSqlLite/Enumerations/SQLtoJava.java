package io.github.josecarlosbran.JBSqlLite.Enumerations;

public enum SQLtoJava {
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
     * Bit único/valor binario (activado o desactivado)
     */
    BIT("java.lang.Boolean"),

    /**
     * entero de 16 bits
     */
    SMALLINT("short"),

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
    TIMESTAMP("java.sql.Timestamp");

    private String typeJava;

    private SQLtoJava(String tipo){
        this.typeJava=tipo;
    }

    /***
     * Obtiene el nombre de la clase del tipo equivalente en java al tipo SQL.
     * @return Retorna el nombre de la clase del tipo equivalente en java al tipo SQL.
     */
    public String getTypeJava() {
        return typeJava;
    }


}

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

    /***
     * Obtiene el nombre de la clase del tipo equivalente en java al tipo SQL.
     * @return Retorna el nombre de la clase del tipo equivalente en java al tipo SQL.
     */
    public String getSize() {
        return size;
    }

    public String toString(){
        if(stringIsNullOrEmpty(this.getSize())){
            return this.name();
        }else{
            return this.name()+"("+this.getSize()+")";
        }
    }

    public void setSize(String Size){
        this.size=Size;
    }
}

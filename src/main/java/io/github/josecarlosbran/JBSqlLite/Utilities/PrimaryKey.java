package io.github.josecarlosbran.JBSqlLite.Utilities;


public class PrimaryKey {

    /**
     * TABLE_CAT String => catálogo de tablas (puede ser nulo)
     */
    private String TABLE_CAT =null;

    /**
     * TABLE_SCHEM String => esquema de tabla (puede ser nulo)
     */
    private String TABLE_SCHEM =null;

    /**
     * TABLE_NAME Cadena => nombre de la tabla
     */
    private String TABLE_NAME =null;
    /**
     * COLUMN_NAME Cadena => nombre de columna
     */
    private String COLUMN_NAME =null;


    /**
     * KEY_SEQ short => número de secuencia dentro de la clave principal
     * (un valor de 1 representa la primera columna de la clave principal, un valor de 2 representaría la segunda
     * columna dentro de la clave principal).
     */
    private short KEY_SEQ =0;

    /**
     * PK_NAME Cadena => nombre de clave principal (puede ser nulo)
     */
    private String PK_NAME=null;


    /**
     * TABLE_CAT String => catálogo de tablas (puede ser nulo)
     */
    public String getTABLE_CAT() {
        return TABLE_CAT;
    }

    public void setTABLE_CAT(String TABLE_CAT) {
        this.TABLE_CAT = TABLE_CAT;
    }

    /**
     * TABLE_SCHEM String => esquema de tabla (puede ser nulo)
     */
    public String getTABLE_SCHEM() {
        return TABLE_SCHEM;
    }

    public void setTABLE_SCHEM(String TABLE_SCHEM) {
        this.TABLE_SCHEM = TABLE_SCHEM;
    }

    /**
     * TABLE_NAME Cadena => nombre de la tabla
     */
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    /**
     * COLUMN_NAME Cadena => nombre de columna
     */
    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    /**
     * KEY_SEQ short => número de secuencia dentro de la clave principal
     * (un valor de 1 representa la primera columna de la clave principal, un valor de 2 representaría la segunda
     * columna dentro de la clave principal).
     */
    public short getKEY_SEQ() {
        return KEY_SEQ;
    }

    public void setKEY_SEQ(short KEY_SEQ) {
        this.KEY_SEQ = KEY_SEQ;
    }

    /**
     * PK_NAME Cadena => nombre de clave principal (puede ser nulo)
     */
    public String getPK_NAME() {
        return PK_NAME;
    }

    public void setPK_NAME(String PK_NAME) {
        this.PK_NAME = PK_NAME;
    }
}

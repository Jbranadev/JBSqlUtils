package io.github.josecarlosbran.JBSqlLite.Utilities;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose Bran
 * Clase que sirve para identificar las caracteristicas de la tabla correspondiente al modelo
 */
public class TablesSQL {
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
     * TABLE_TYPE Cadena => tipo de tabla. Los tipos típicos son "TABLE", "VIEW", "SYSTEM TABLE",
     * "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
     * OBSERVACIONES String => comentario explicativo sobre la tabla
     */
    private String TABLE_TYPE =null;

    /**
     *
     */
    private String REMARKS =null;

    /**
     * TYPE_CAT String => el catálogo de tipos (puede ser nulo)
     */
    private String TYPE_CAT =null;

    /**
     * TYPE_SCHEM String => el esquema de tipos (puede ser nulo)
     */
    private String TYPE_SCHEM =null;

    /**
     * TYPE_NAME Cadena => nombre de tipo (puede ser nulo)
     */
    private String TYPE_NAME =null;

    /**
     * SELF_REFERENCING_COL_NAME Cadena => nombre de la columna de "identificador"
     * designada de una tabla escrita (puede ser nulo)
     */
    private String SELF_REFERENCING_COL_NAME=null;
    /**
     * REF_GENERATION String => especifica cómo se crean los valores en SELF_REFERENCING_COL_NAME.
     * Los valores son "SISTEMA", "USUARIO", "DERIVADO". (puede ser nulo)
     */
    private String REF_GENERATION =null;

    /**
     * Clave Primaria correspondiente a la tabla
     */
    private PrimaryKey claveprimaria=null;

    /**
     * Lista de las columnas que tiene la tabla correspondiente al modelo
     */
    protected List<ColumnsSQL> Columnas = new ArrayList<>();


    protected static List<TablesSQL> Tablas=new ArrayList<>();

    public synchronized static List<TablesSQL> getTablas() {
        return Tablas;
    }

    /**
     * Obtiene la lista de las columnas que posee la tabla correspondiente al modelo.
     * @return Lista de las columnas que posee la tabla correspondiente al modelo.
     */
    public List<ColumnsSQL> getColumnas() {
        return this.Columnas;
    }

    public void setColumnas(List<ColumnsSQL> columnas) {
        this.Columnas=columnas;
    }


    public String getTABLE_CAT() {
        return TABLE_CAT;
    }

    public void setTABLE_CAT(String TABLE_CAT) {
        this.TABLE_CAT = TABLE_CAT;
    }

    public String getTABLE_SCHEM() {
        return TABLE_SCHEM;
    }

    public void setTABLE_SCHEM(String TABLE_SCHEM) {
        this.TABLE_SCHEM = TABLE_SCHEM;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getTABLE_TYPE() {
        return TABLE_TYPE;
    }

    public void setTABLE_TYPE(String TABLE_TYPE) {
        this.TABLE_TYPE = TABLE_TYPE;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getTYPE_CAT() {
        return TYPE_CAT;
    }

    public void setTYPE_CAT(String TYPE_CAT) {
        this.TYPE_CAT = TYPE_CAT;
    }

    public String getTYPE_SCHEM() {
        return TYPE_SCHEM;
    }

    public void setTYPE_SCHEM(String TYPE_SCHEM) {
        this.TYPE_SCHEM = TYPE_SCHEM;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }

    public String getSELF_REFERENCING_COL_NAME() {
        return SELF_REFERENCING_COL_NAME;
    }

    public void setSELF_REFERENCING_COL_NAME(String SELF_REFERENCING_COL_NAME) {
        this.SELF_REFERENCING_COL_NAME = SELF_REFERENCING_COL_NAME;
    }

    public String getREF_GENERATION() {
        return REF_GENERATION;
    }

    public void setREF_GENERATION(String REF_GENERATION) {
        this.REF_GENERATION = REF_GENERATION;
    }


    /**
     * Clave Primaria correspondiente a la tabla
     */
    public PrimaryKey getClaveprimaria() {
        return claveprimaria;
    }

    public void setClaveprimaria(PrimaryKey claveprimaria) {
        this.claveprimaria = claveprimaria;
    }
}



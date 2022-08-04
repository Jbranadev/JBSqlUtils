package io.github.josecarlosbran.JBSqlLite.Utilities;


import java.util.LinkedList;
import java.util.List;

public class TablesSQL {
    private String TABLE_CAT =null;
    private String TABLE_SCHEM =null;
    private String TABLE_NAME =null;
    private String TABLE_TYPE =null;
    private String REMARKS =null;
    private String TYPE_CAT =null;
    private String TYPE_SCHEM =null;
    private String TYPE_NAME =null;
    private String SELF_REFERENCING_COL_NAME=null;
    private String REF_GENERATION =null;

    protected static List<TablesSQL> Tablas=new LinkedList<>();

    public synchronized static List<TablesSQL> getTablas() {
        return Tablas;
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




}



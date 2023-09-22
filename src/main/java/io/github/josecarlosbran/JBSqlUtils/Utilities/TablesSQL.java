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
package io.github.josecarlosbran.JBSqlUtils.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que sirve para identificar las caracteristicas de la tabla correspondiente al modelo
 *
 * @author Jose Bran
 */
public class TablesSQL {
    /**
     * Lista de las columnas que tiene la tabla correspondiente al modelo
     */
    protected List<ColumnsSQL> Columnas = new ArrayList<>();
    /**
     * TABLE_CAT String => catálogo de tablas (puede ser nulo)
     */
    private String TABLE_CAT = null;
    /**
     * TABLE_SCHEM String => esquema de tabla (puede ser nulo)
     */
    private String TABLE_SCHEM = null;
    /**
     * TABLE_NAME Cadena => nombre de la tabla
     */
    private String TABLE_NAME = null;
    /**
     * TABLE_TYPE Cadena => tipo de tabla. Los tipos típicos son "TABLE", "VIEW", "SYSTEM TABLE",
     * "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
     * OBSERVACIONES String => comentario explicativo sobre la tabla
     */
    private String TABLE_TYPE = null;
    /**
     * String => explanatory comment on the table
     */
    private String REMARKS = null;
    /**
     * TYPE_CAT String => el catálogo de tipos (puede ser nulo)
     */
    private String TYPE_CAT = null;
    /**
     * TYPE_SCHEM String => el esquema de tipos (puede ser nulo)
     */
    private String TYPE_SCHEM = null;
    /**
     * TYPE_NAME Cadena => nombre de tipo (puede ser nulo)
     */
    private String TYPE_NAME = null;
    /**
     * SELF_REFERENCING_COL_NAME Cadena => nombre de la columna de "identificador"
     * designada de una tabla escrita (puede ser nulo)
     */
    private String SELF_REFERENCING_COL_NAME = null;
    /**
     * REF_GENERATION String => especifica cómo se crean los valores en SELF_REFERENCING_COL_NAME.
     * Los valores son "SISTEMA", "USUARIO", "DERIVADO". (puede ser nulo)
     */
    private String REF_GENERATION = null;
    /**
     * Clave Primaria correspondiente a la tabla
     */
    private PrimaryKey claveprimaria = null;
    /**
     * Lista de los nombres de las columnas que existen
     */
    private List<String> columnsExist = new ArrayList<>();

    /**
     * Obtiene la lista de las columnas que posee la tabla correspondiente al modelo.
     *
     * @return Lista de las columnas que posee la tabla correspondiente al modelo.
     */
    public synchronized List<ColumnsSQL> getColumnas() {
        return this.Columnas;
    }

    /**
     * Obtiene la Clave Primaria correspondiente a la tabla
     *
     * @return Clave Primaria correspondiente a la tabla
     */
    public PrimaryKey getClaveprimaria() {
        return claveprimaria;
    }

    /**
     * Setea la clave primaria de la tabla
     *
     * @param claveprimaria Clave primaria de la tabla en BD's
     */
    public void setClaveprimaria(PrimaryKey claveprimaria) {
        this.claveprimaria = claveprimaria;
    }

    /**
     * TABLE_CAT String => catálogo de tablas (puede ser nulo)
     *
     * @return TABLE_CAT String => catálogo de tablas (puede ser nulo)
     */
    public String getTABLE_CAT() {
        return UtilitiesJB.stringIsNullOrEmpty(TABLE_CAT) ? null : TABLE_CAT;
    }

    /**
     * Setea la categoría de la tabla en BD's
     *
     * @param TABLE_CAT TABLE_CAT String => catálogo de tablas (puede ser nulo)
     */
    public void setTABLE_CAT(String TABLE_CAT) {
        this.TABLE_CAT = TABLE_CAT;
    }

    /**
     * TABLE_SCHEM String => esquema de tabla (puede ser nulo)
     *
     * @return Retorna el esquema de la tabla en BD's
     */
    public String getTABLE_SCHEM() {
        return UtilitiesJB.stringIsNullOrEmpty(TABLE_SCHEM) ? null : TABLE_SCHEM;
    }

    /**
     * Setea el table Schem en BD's
     *
     * @param TABLE_SCHEM TABLE_SCHEM String => esquema de tabla (puede ser nulo)
     */
    public void setTABLE_SCHEM(String TABLE_SCHEM) {
        this.TABLE_SCHEM = TABLE_SCHEM;
    }

    /**
     * TABLE_NAME Cadena => nombre de la tabla
     *
     * @return retorna el nombre de la tabla en BD's
     */
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    /**
     * Setea el nombre de la tabla a la cual pertenece la columna
     *
     * @param TABLE_NAME TABLE_NAME String → table name
     */
    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }
    /**
     * Obtiene el tipo de tabla que es.
     *
     * @return TABLE_TYPE Cadena => tipo de tabla. Los tipos típicos son "TABLE", "VIEW", "SYSTEM TABLE",
     * "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
     * OBSERVACIONES String => comentario explicativo sobre la tabla
     *//*
    public String getTABLE_TYPE() {
        return UtilitiesJB.stringIsNullOrEmpty(TABLE_TYPE)?null:TABLE_TYPE;
    }*/

    /**
     * Setea el tipo de tabla en BD's
     *
     * @param TABLE_TYPE TABLE_TYPE Cadena => tipo de tabla. Los tipos típicos son "TABLE", "VIEW", "SYSTEM TABLE",
     *                   * "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
     *                   * OBSERVACIONES String => comentario explicativo sobre la tabla
     */
    public void setTABLE_TYPE(String TABLE_TYPE) {
        this.TABLE_TYPE = TABLE_TYPE;
    }
    /**
     * Obtiene el comentario de la tabla
     *
     * @return String => explanatory comment on the table
     *//*
    public String getREMARKS() {
        return UtilitiesJB.stringIsNullOrEmpty(REMARKS)?null:REMARKS;
    }*/

    /**
     * Setea el comentario correspondiente a la tabla
     *
     * @param REMARKS String => explanatory comment on the table
     */
    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }
    /**
     * Obtiene el catalogo de tipos
     *
     * @return TYPE_CAT String => el catálogo de tipos (puede ser nulo)
     *//*
    public String getTYPE_CAT() {
        return UtilitiesJB.stringIsNullOrEmpty(TYPE_CAT)?null:TYPE_CAT;
    }
*/

    /**
     * Setea el TYPE_CAT String => el catálogo de tipos (puede ser nulo)
     *
     * @param TYPE_CAT TYPE_CAT String => el catálogo de tipos (puede ser nulo)
     */
    public void setTYPE_CAT(String TYPE_CAT) {
        this.TYPE_CAT = TYPE_CAT;
    }
    /**
     * Obtiene el tipo de esquema
     *
     * @return TYPE_SCHEM String => el esquema de tipos (puede ser nulo)
     *//*
    public String getTYPE_SCHEM() {
        return UtilitiesJB.stringIsNullOrEmpty(TYPE_SCHEM)?null:TYPE_SCHEM;
    }*/

    /**
     * Setea el tipo de esquema.
     *
     * @param TYPE_SCHEM TYPE_SCHEM String => el esquema de tipos (puede ser nulo)
     */
    public void setTYPE_SCHEM(String TYPE_SCHEM) {
        this.TYPE_SCHEM = TYPE_SCHEM;
    }
    /**
     * Retorna el nombre de tipo de la tabla en BD's
     *
     * @return TYPE_NAME Cadena => nombre de tipo (puede ser nulo)
     *//*
    public String getTYPE_NAME() {
        return UtilitiesJB.stringIsNullOrEmpty(TYPE_NAME)?null:TYPE_NAME;
    }
*/

    /**
     * Setea el nombre de tipo de la tabla en BD's
     *
     * @param TYPE_NAME TYPE_NAME Cadena => nombre de tipo (puede ser nulo)
     */
    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }
    /**
     * Obtiene el nombre de la columna de identificación de la tabla
     *
     * @return SELF_REFERENCING_COL_NAME Cadena => nombre de la columna de "identificador"
     * designada de una tabla escrita (puede ser nulo)
     */

    /*
    public String getSELF_REFERENCING_COL_NAME() {
        return UtilitiesJB.stringIsNullOrEmpty(SELF_REFERENCING_COL_NAME)?null:SELF_REFERENCING_COL_NAME;
    }
*/

    /**
     * Setea el nombre de la columna de identificación de la tabla
     *
     * @param SELF_REFERENCING_COL_NAME SELF_REFERENCING_COL_NAME Cadena => nombre de la columna de "identificador"
     *                                  * designada de una tabla escrita (puede ser nulo)
     */
    public void setSELF_REFERENCING_COL_NAME(String SELF_REFERENCING_COL_NAME) {
        this.SELF_REFERENCING_COL_NAME = SELF_REFERENCING_COL_NAME;
    }
    /**
     * @return REF_GENERATION String => especifica cómo se crean los valores en SELF_REFERENCING_COL_NAME.
     * Los valores son "SISTEMA", "USUARIO", "DERIVADO". (puede ser nulo)
     *//*
    public String getREF_GENERATION() {
        return UtilitiesJB.stringIsNullOrEmpty(REF_GENERATION)?null:REF_GENERATION;
    }*/

    /**
     * Setea como se crean los valores de referencia
     *
     * @param REF_GENERATION REF_GENERATION String => especifica cómo se crean los valores en SELF_REFERENCING_COL_NAME.
     *                       * Los valores son "SISTEMA", "USUARIO", "DERIVADO". (puede ser nulo)
     */
    public void setREF_GENERATION(String REF_GENERATION) {
        this.REF_GENERATION = REF_GENERATION;
    }

    /**
     * Lista de los nombres de las columnas que existen
     *
     * @return Retorna una lista de los nombres de las columnas que existen en BD's
     */
    public List<String> getColumnsExist() {
        return columnsExist;
    }

    /**
     * Setea una lista de los nombres de las columnas que existen en BD's
     *
     * @param columnsExist Lista de los nombres de las columnas que existen en BD's
     */
    public void setColumnsExist(List<String> columnsExist) {
        this.columnsExist = columnsExist;
    }
}



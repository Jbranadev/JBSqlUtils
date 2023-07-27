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

/**
 * @author Jose Bran
 * Clase que permite tener la información de la clave primaria de la tabla correspondiente al modelo en BD's
 */
public class PrimaryKey {

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
     * COLUMN_NAME Cadena => nombre de columna
     */
    private String COLUMN_NAME = null;


    /**
     * KEY_SEQ short => número de secuencia dentro de la clave principal
     * (un valor de 1 representa la primera columna de la clave principal, un valor de 2 representaría la segunda
     * columna dentro de la clave principal).
     */
    private short KEY_SEQ = 0;

    /**
     * PK_NAME Cadena => nombre de clave principal (puede ser nulo)
     */
    private String PK_NAME = null;


    /**
     * @return TABLE_CAT String => catálogo de tablas (puede ser nulo)
     */
    public String getTABLE_CAT() {
        if (UtilitiesJB.stringIsNullOrEmpty(TABLE_CAT)) {
            return null;
        }
        return TABLE_CAT;
    }

    /**
     * Sete la categoría de la tabla
     *
     * @param TABLE_CAT TABLE_CAT String → table catalog (may be null)
     */
    public void setTABLE_CAT(String TABLE_CAT) {
        this.TABLE_CAT = TABLE_CAT;
    }

    /**
     * @return TABLE_SCHEM String => esquema de tabla (puede ser nulo)
     */
    public String getTABLE_SCHEM() {
        if (UtilitiesJB.stringIsNullOrEmpty(TABLE_SCHEM)) {
            return null;
        }
        return TABLE_SCHEM;
    }

    /**
     * Setea el Schem de la tabla
     *
     * @param TABLE_SCHEM TABLE_SCHEM String → table schema (may be null)
     */
    public void setTABLE_SCHEM(String TABLE_SCHEM) {
        this.TABLE_SCHEM = TABLE_SCHEM;
    }

    /**
     * @return TABLE_NAME Cadena => nombre de la tabla
     */
    public String getTABLE_NAME() {
        if (UtilitiesJB.stringIsNullOrEmpty(TABLE_NAME)) {
            return null;
        }
        return TABLE_NAME;
    }

    /**
     * Setea el nombre de la tabla
     *
     * @param TABLE_NAME TABLE_NAME Cadena => nombre de la tabla
     */
    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    /**
     * @return COLUMN_NAME Cadena => nombre de columna
     */
    public String getCOLUMN_NAME() {
        if (UtilitiesJB.stringIsNullOrEmpty(COLUMN_NAME)) {
            return null;
        }
        return COLUMN_NAME;
    }

    /**
     * Nombre de la columna a la que hace referencia esta clave primaría
     *
     * @param COLUMN_NAME COLUMN_NAME Cadena => nombre de columna
     */
    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    /**
     * @return KEY_SEQ short => número de secuencia dentro de la clave principal
     * (un valor de 1 representa la primera columna de la clave principal, un valor de 2 representaría la segunda
     * columna dentro de la clave principal).
     */
    public short getKEY_SEQ() {
        return KEY_SEQ;
    }

    /**
     * Setea el orden en el que esta la clave si el modelo tiene una clave primara compuesta por 2 o más registros.
     *
     * @param KEY_SEQ KEY_SEQ short => número de secuencia dentro de la clave principal
     *                * (un valor de 1 representa la primera columna de la clave principal, un valor de 2 representaría la segunda
     *                * columna dentro de la clave principal).
     */
    public void setKEY_SEQ(short KEY_SEQ) {
        this.KEY_SEQ = KEY_SEQ;
    }

    /**
     * @return PK_NAME Cadena => nombre de clave principal (puede ser nulo)
     */
    public String getPK_NAME() {
        if (UtilitiesJB.stringIsNullOrEmpty(PK_NAME)) {
            return null;
        }
        return PK_NAME;
    }

    /**
     * Setea el nombre de la clave primaria
     *
     * @param PK_NAME PK_NAME Cadena => nombre de clave principal (puede ser nulo)
     */
    public void setPK_NAME(String PK_NAME) {
        this.PK_NAME = PK_NAME;
    }
}

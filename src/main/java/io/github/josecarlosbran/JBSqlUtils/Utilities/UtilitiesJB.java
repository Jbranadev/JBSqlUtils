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

import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Clase que brinda acceso a funcionalidades comunes, sin necesidad de crear una instancia de la misma
 *
 * @author Jose Bran
 */
public class UtilitiesJB {

    /****
     * Verifica si una cadena esta vacía o es nula
     * @param cadena Cadena a Validar
     * @return Retorna True si la cadena envíada esta vacía o nula, de lo contrario retorna false
     */
    public static boolean stringIsNullOrEmpty(String cadena) {
        if (Objects.isNull(cadena) || cadena.isEmpty()) {
            return true;
        }
        return false;
    }

    /***
     * Obtener el valor booleano de un numero
     * @param numero numero que se evaluara
     * @return si el numero es mayor o igual a uno, retorna true, de lo contrario, retorna false.
     */
    public static boolean getBooleanfromInt(int numero) {
        if (numero >= 1) {
            return true;
        }
        return false;
    }

    /**
     * @param temp Valor Booleano a ser evaluado.
     * @return Si el valor es NULL o False, retorna 0, si el valor es True retorna 1.
     * @author Jose Bran
     * Medoto que convierte el valor Booleano en un entero, si el valor es NULL o False, retorna 0.
     * Si el valor es True retorna 1.
     */
    public static int getIntFromBoolean(Boolean temp) {
        if (temp == null || temp == false) {
            return 0;
        }
        return 1;
    }

    /**
     * Retorna una columna SQL correspondiente al objeto envíado como parametro
     *
     * @param valor Valor del cual se extraera la columna
     * @return Retorna la Columna generada.
     */
    public static Column getColumn(Object valor) {
        if (valor instanceof String) {
            Column<String> columna = new Column<>(DataType.VARCHAR);
            columna.setValor((String) valor);
            return columna;
        } else if (valor instanceof Double) {
            Column<Double> columna = new Column<>(DataType.DOUBLE);
            columna.setValor((Double) valor);
            return columna;
        } else if (valor instanceof Boolean) {
            Column<Boolean> columna = new Column<>(DataType.BOOLEAN);
            columna.setValor((Boolean) valor);
            return columna;
        } else if (valor instanceof Integer) {
            Column<Integer> columna = new Column<>(DataType.INTEGER);
            columna.setValor((Integer) valor);
            return columna;
        } else if (valor instanceof Float) {
            Column<Float> columna = new Column<>(DataType.FLOAT);
            columna.setValor((Float) valor);
            return columna;
        } else if (valor instanceof byte[]) {
            Column<byte[]> columna = new Column<>(DataType.VARBINARY);
            columna.setValor((byte[]) valor);
            return columna;
        } else if (valor instanceof Date) {
            Column<Date> columna = new Column<>(DataType.DATE);
            columna.setValor((Date) valor);
            return columna;
        } else if (valor instanceof Time) {
            Column<Time> columna = new Column<>(DataType.TIME);
            columna.setValor((Time) valor);
            return columna;
        } else if (valor instanceof Timestamp) {
            Column<Timestamp> columna = new Column<>(DataType.TIMESTAMP);
            columna.setValor((Timestamp) valor);
            return columna;
        }
        Column<Object> temp = new Column<>(DataType.OBJECT);
        temp.setValor((Object) valor);
        return temp;
    }
}

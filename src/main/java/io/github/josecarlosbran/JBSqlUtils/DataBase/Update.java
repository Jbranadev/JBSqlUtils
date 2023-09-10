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
package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona los métodos necesarios para la lógica de un Update en BD's sin necesidad
 * de tener un modelo de la tabla que se desea actualizar.
 *
 * @author Jose Bran
 */
public class Update {
    private String sql;

    /**
     * Constructor que recibe como parámetro:
     *
     * @param TableName El nombre de la tabla sobre la cual se desea realizar el Update.
     * @throws ValorUndefined Lanza esta excepción si el parámetro proporcionado está vacío o es NULL
     */
    protected Update(String TableName) throws ValorUndefined {
        String respuesta = "";
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        this.sql = "UPDATE " + TableName + " ";
    }

    /**
     * Entrega la capacidad de setear otro valor antes de ejecutar la sentencia Upddate
     *
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value     Valor que se asignara a la columna.
     * @return Retorna un objeto Set que entrega la capacidad de setear otro valor
     * antes de ejecutar la sentencia Upddate
     * @throws ValorUndefined        ValorUndefined ValorUndefined Lanza esta Excepción si
     *                               alguno de los parámetros proporcionados esta vacío o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public Set set(String columName, Object value) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        return new Set(columName, value, this.sql);
    }


}

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

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona los metodos necesarios para la lógica de un Delete en BD's sin necesidad
 * de tener un modelo de la tabla que se desea Eliminar registros.
 *
 * @author Jose Bran
 */
public class Delete {
    private String sql;

    /**
     * Constructor que recibe como parámetro:
     *
     * @param TableName El nombre de la tabla sobre la cual se desea realizar el Update.
     * @throws ValorUndefined Lanza esta excepción si el parámetro proporcionado está vacío o es NULL
     */
    protected Delete(String TableName) throws ValorUndefined {
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        this.sql = "DELETE FROM " + TableName;
    }

    /**
     * Proporciona un punto de entrada para agregar la lógica de una sentencia WHERE a la sentencia SQL que
     * deseamos ejecutar
     *
     * @param columna  Columna que sera evaluada
     * @param operador Operador por medio del cual se evaluara la columna
     * @param value    Valor contra el que se evaluara la columna
     * @return Punto de entrada a metodos que permiten seguir modificando la expresión de filtro u obtener el o los
     * modelos que hacen match con la consulta generada
     * @throws ValorUndefined Lanza esta excepción si alguno de los parámetros proporcionados esta
     *                        Vacío o es Null
     */
    public Where where(String columna, Operator operador, Object value) throws ValorUndefined {
        return new Where(columna, operador, value, this.sql);
    }
}

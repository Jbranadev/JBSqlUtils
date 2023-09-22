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

import com.josebran.LogsJB.LogsJB;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

import java.util.Objects;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.getColumn;
import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona la lógica para setear un valor antes de ejecutar la sentencia Update.
 *
 * @author Jose Bran
 */
public class Set extends AndSet {

    /**
     * Constructor que recibe como parámetro:
     *
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value     Valor que se asignara a la columna.
     * @param sql       Sentencia SQL a la cual se agregara la columna y valor a setear.
     * @throws ValorUndefined        ValorUndefined Lanza esta Excepción si
     *                               alguno de los parámetros proporcionados esta vacío o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected Set(String columName, Object value, String sql) throws ValorUndefined {
        super();
        if (stringIsNullOrEmpty(columName)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(value)) {
            LogsJB.warning("El valor proporcionado para la columna esta vacío o es NULL");
            LogsJB.warning(sql);
            LogsJB.warning(columName);
            LogsJB.warning((String) value);
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        this.parametros.add(getColumn(value));
        this.sql = sql + "SET " + columName + "=" + "?";
    }
}

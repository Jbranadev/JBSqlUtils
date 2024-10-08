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
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.getColumn;
import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona la lógica para setear otro valor antes de ejecutar la sentencia Update.
 *
 * @author Jose Bran
 */
public class AndSet extends MethodsAndSet {
    /**
     * Constructor Utilizado para que la clase Set pueda acceder a los metodos heredados de esta clase
     */
    protected AndSet() {
        super();
    }

    /**
     * Constructor que recibe como parámetro:
     *
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value     Valor que se asignara a la columna.
     * @param sql       Sentencia SQL a la cual se agregara la columna y valor a setear.
     * @throws ValorUndefined ValorUndefined Lanza esta Excepción si
     *                        alguno de los parámetros proporcionados esta vacío o es Null
     */
    protected AndSet(String columName, Object value, String sql, List<Column> parametros) throws ValorUndefined {
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
        this.parametros = parametros;
        this.parametros.add(getColumn(value));
        this.sql = sql + ", " + columName + "=" + "?";
    }

    /**
     * Entrega la capacidad de setear otro valor antes de ejecutar la sentencia Upddate
     *
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value     Valor que se asignara a la columna.
     * @return Retorna un objeto AndSet que entrega la capacidad de setear otro valor
     * antes de ejecutar la sentencia Upddate
     * @throws ValorUndefined ValorUndefined ValorUndefined Lanza esta Excepción si
     *                        alguno de los parametros proporcionados esta vacío o es Null
     */
    public AndSet andSet(String columName, Object value) throws ValorUndefined {
        return new AndSet(columName, value, this.sql, this.parametros);
    }

    /**
     * Proporciona un punto de entrada para agregar la logica de una sentencia WHERE a la sentencia SQL que
     * deseamos ejecutar
     *
     * @param columna  Columna que sera evaluada
     * @param operador Operador por medio del cual se evaluara la columna
     * @param value    Valor contra el que se evaluara la columna
     * @return Punto de entrada a métodos que permiten seguir modificando la expresión de filtro u obtener el o los
     * modelos que hacen match con la consulta generada
     * @throws ValorUndefined Lanza esta excepción si alguno de los parametros proporcionados esta
     *                        Vacío o es Null
     */
    public Where where(String columna, Operator operador, Object value) throws ValorUndefined {
        return new Where(columna, operador, value, this.sql, this.parametros);
    }

    /**
     * Proporciona un punto de entrada para agregar la logica de una sentencia Having a la sentencia SQL que
     * deseamos ejecutar
     *
     * @param columna  Columna que sera evaluada
     * @param operador Operador por medio del cual se evaluara la columna
     * @param value    Valor contra el que se evaluara la columna
     * @return Punto de entrada a métodos que permiten seguir modificando la expresión de filtro u obtener el o los
     * modelos que hacen match con la consulta generada
     * @throws ValorUndefined Lanza esta excepción si alguno de los parametros proporcionados esta
     *                        Vacío o es Null
     */
    public Having having(String columna, Operator operador, Object value) throws ValorUndefined {
        return new Having(columna, operador, value, this.sql, this.parametros);
    }
}

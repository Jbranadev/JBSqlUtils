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
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.getColumn;
import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona la logica para agregar una sentencia HAVING a la consulta a realizar.
 *
 * @author Jose Bran
 */
public class Having<T> extends MethodsWhere {
    /**
     * Constructor que recibe como parámetro:
     *
     * @param columna  Columna a evaluar dentro de la sentencia Having
     * @param operador Operador con el cual se evaluara la columna
     * @param valor    Valor contra el que se evaluara la columna
     * @param modelo   Modelo que invocara los métodos de esta clase
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected Having(String columna, Operator operador, Object valor, T modelo) throws ValorUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        this.modelo = modelo;
        this.parametros.add(getColumn(valor));
        this.sql = " HAVING " + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parámetro:
     *
     * @param columna           Columna a evaluar dentro de la sentencia Having
     * @param operador          Operador con el cual se evaluara la columna
     * @param valor             Valor contra el que se evaluara la columna
     * @param modelo            Modelo que invocara los métodos de esta clase
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected Having(String columna, Operator operador, Object valor, T modelo, Boolean getPropertySystem) throws ValorUndefined {
        super(getPropertySystem);
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        this.modelo = modelo;
        this.parametros.add(getColumn(valor));
        this.sql = " HAVING " + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parámetro:
     *
     * @param columna  Columna a evaluar dentro de la sentencia Having
     * @param operador Operador con el cual se evaluara la columna
     * @param valor    Valor contra el que se evaluara la columna
     * @param sql      Sentencia SQL a la cual se agregara la Sentencia Having
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected Having(String columna, Operator operador, Object valor, String sql, List<Column> parametros) throws ValorUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        this.parametros = parametros;
        this.parametros.add(getColumn(valor));
        this.sql = sql + " HAVING " + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parámetro:
     *
     * @param columna  Columna a evaluar dentro de la sentencia Having
     * @param operador Operador con el cual se evaluara la columna
     * @param valor    Valor contra el que se evaluara la columna
     * @param sql      Sentencia SQL a la cual se agregara la Sentencia Having
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected Having(String columna, Operator operador, Object valor, String sql) throws ValorUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        this.parametros.add(getColumn(valor));
        this.sql = sql + " HAVING" + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }
}

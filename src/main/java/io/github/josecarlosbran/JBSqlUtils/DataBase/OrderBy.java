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

import io.github.josecarlosbran.JBSqlUtils.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona la capacidad de agregar una sentencia OrderBy a la consulta trasladada como parámetro
 *
 * @author Jose Bran
 */
public class OrderBy<T> extends MethodsOrderBy {
    /**
     * Constructor que recibe como parámetro:
     *
     * @param sql        Sentencia SQL a la que se agregara la logica ORDER BY
     * @param columna    Columna a evaluar dentro de la sentencia ORDER BY
     * @param orderType  Tipo de ordenamiento que se realizara
     * @param modelo     Modelo que invoca la ejecución de los metodos.
     * @param parametros Lista de parámetros a ser agregados a la sentencia SQL
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected OrderBy(String sql, String columna, OrderType orderType, T modelo, List<Column> parametros) throws ValorUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(orderType)) {
            throw new ValorUndefined("El tipo de ordenamiento proporcionado es NULL");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + " ORDER BY " + columna + orderType.getValor();
    }

    /**
     * Constructor que recibe como parámetro:
     *
     * @param sql               Sentencia SQL a la que se agregara la logica ORDER BY
     * @param columna           Columna a evaluar dentro de la sentencia ORDER BY
     * @param orderType         Tipo de ordenamiento que se realizara
     * @param modelo            Modelo que invoca la ejecución de los metodos.
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected OrderBy(String sql, String columna, OrderType orderType, T modelo, List<Column> parametros, Boolean getPropertySystem) throws ValorUndefined {
        super(getPropertySystem);
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(orderType)) {
            throw new ValorUndefined("El tipo de ordenamiento proporcionado es NULL");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + " ORDER BY " + columna + orderType.getValor();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la que se agregara la logica ORDER BY
     * @param columna    Columna a evaluar dentro de la sentencia ORDER BY
     * @param orderType  Tipo de ordenamiento que se realizara
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected OrderBy(String sql, String columna, OrderType orderType, List<Column> parametros) throws ValorUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(orderType)) {
            throw new ValorUndefined("El tipo de ordenamiento proporcionado es NULL");
        }
        this.parametros = parametros;
        this.sql = sql + " ORDER BY " + columna + orderType.getValor();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
}

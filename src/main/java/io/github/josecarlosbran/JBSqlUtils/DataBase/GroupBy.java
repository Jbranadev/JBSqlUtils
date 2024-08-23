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

import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.List;
import java.util.Objects;

/**
 * Clase que proporciona la logica para agrupar una cantidad de resultados, siendo el criterio de agrupación el
 * parametro para el constructor.
 *
 * @author Jose Bran
 */
public class GroupBy<T> extends MethodsTake {
    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la cual se agregara la logica de agrupamiento.
     * @param modelo     Modelo que solicita la creación de esta clase
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param columnas   Lista de columnas a ser agrupadas
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected GroupBy(String sql, T modelo, List<Column> parametros, String... columnas) throws ValorUndefined {
        super();
        if (Objects.isNull(columnas) || columnas.length == 0) {
            throw new ValorUndefined("No se proporcionaron columnas para realizar el agrupamiento");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        // Build the GROUP BY clause
        StringBuilder groupByClause = new StringBuilder(" GROUP BY ");
        for (int i = 0; i < columnas.length; i++) {
            groupByClause.append(columnas[i]);
            if (i < columnas.length - 1) {
                groupByClause.append(", ");
            }
        }
        groupByClause.append("; ");
        // Append the GROUP BY clause and LIMIT clause to the SQL statement
        this.sql = sql + groupByClause;
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql               Sentencia SQL a la cual se agregara la logica de agrupamiento.
     * @param modelo            Modelo que solicita la creación de esta clase
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @param columnas          Lista de columnas a ser agrupadas
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected GroupBy(String sql, T modelo, List<Column> parametros, Boolean getPropertySystem, String... columnas) throws ValorUndefined {
        super(getPropertySystem);
        if (Objects.isNull(columnas) || columnas.length == 0) {
            throw new ValorUndefined("No se proporcionaron columnas para realizar el agrupamiento");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        // Build the GROUP BY clause
        StringBuilder groupByClause = new StringBuilder(" GROUP BY ");
        for (int i = 0; i < columnas.length; i++) {
            groupByClause.append(columnas[i]);
            if (i < columnas.length - 1) {
                groupByClause.append(", ");
            }
        }
        groupByClause.append("; ");
        // Append the GROUP BY clause and LIMIT clause to the SQL statement
        this.sql = sql + groupByClause;
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la cual se agregara la logica de agrupamiento.
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param columnas   Lista de columnas a ser agrupadas
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected GroupBy(String sql, List<Column> parametros, String... columnas) throws ValorUndefined {
        super();
        if (Objects.isNull(columnas) || columnas.length == 0) {
            throw new ValorUndefined("No se proporcionaron columnas para realizar el agrupamiento");
        }
        this.parametros = parametros;
        // Build the GROUP BY clause
        StringBuilder groupByClause = new StringBuilder(" GROUP BY ");
        for (int i = 0; i < columnas.length; i++) {
            groupByClause.append(columnas[i]);
            if (i < columnas.length - 1) {
                groupByClause.append(", ");
            }
        }
        groupByClause.append("; ");
        // Append the GROUP BY clause and LIMIT clause to the SQL statement
        this.sql = sql + groupByClause;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
}

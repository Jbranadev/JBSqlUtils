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
package io.github.josecarlosbran.JBSqlLite.DataBase;


import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.LogsJB.LogsJB;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;
/**
 * @author Jose Bran
 * Clase que proporciona la lógica para setear un valor antes de ejecutar la sentencia Update.
 */
public class Set {

    private String sql;

    /**
     * Constructor que recibe como parametro:
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value Valor que se asignara a la columna.
     * @param sql Sentencia SQL a la cual se agregara la columna y valor a setear.
     * @throws ValorUndefined ValorUndefined Lanza esta Excepción si
     * alguno de los parametros proporcionados esta vacío o es Null
     */
    protected Set(String columName, String value, String sql) throws ValorUndefined {
        if (stringIsNullOrEmpty(columName)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (stringIsNullOrEmpty(value)) {
            LogsJB.warning("El valor proporcionado para la columna esta vacío o es NULL");
        }
        this.sql= sql + "SET "+columName+"="+value;
    }

    /**
     * Entrega la capacidad de setear otro valor antes de ejecutar la sentencia Upddate
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value Valor que se asignara a la columna.
     * @return Retorna un objeto AndSet que entrega la capacidad de setear otro valor
     * antes de ejecutar la sentencia Upddate
     * @throws ValorUndefined ValorUndefined ValorUndefined Lanza esta Excepción si
     * alguno de los parametros proporcionados esta vacío o es Null
     */
    public AndSet andSet(String columName, String value) throws ValorUndefined {
        return new AndSet(columName,value, this.sql);
    }

    /**
     * Proporciona un punto de entrada para agregar la lógica de una sentencia WHERE a la sentencia SQL que
     * deseamos ejecutar
     * @param columna Columna que sera evaluada
     * @param operador Operador por medio del cual se evaluara la columna
     * @param value Valor contra el que se evaluara la columna
     * @return Punto de entrada a metodos que permiten seguir modificando la expresión de filtro u obtener el o los
     * modelos que hacen match con la consulta generada
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     * @throws ValorUndefined Lanza esta excepción si alguno de los parametros proporcionados esta
     * Vacío o es Null
     */
    public Where where(String columna, Operator operador, String value) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Where(columna, operador, value, this.sql);
    }




    /**
     * Ejecuta la sentencia SQL proporcionada y retorna la cantidad de filas afectadas
     * @return Retorna un Entero que representa la cantidad de filas afectadas al ejecutar la sentencia SQL
     * proporcionada.
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public int execute() throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Execute(this.sql).execute();
    }




}

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

import io.github.josecarlosbran.JBSqlUtils.Column;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.JBSqlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Jose Bran
 * Clase que proporciona la logica para tomar una cantidad de resultados, siendo esa cantidad el limite trasladado como
 * parametro para el constructor.
 */
public class Take<T> extends Get {
    private String sql;
    private T modelo = null;

    /**
     * Lista de los parametros a envíar
     */
    protected List<Column> parametros = new ArrayList<>();

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la cual se agregara la logica del limite.
     * @param limite     Entero que representa la cantidad maxima de valores recuperados.
     * @param modelo     Modelo que solicita la creación de esta clase
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected Take(String sql, int limite, T modelo, List<Column> parametros) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (Objects.isNull(limite)) {
            throw new ValorUndefined("El Limite proporcionado es 0 o inferior, por lo cual no se puede" +
                    "realizar la consulta a BD's");
        }
        if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + "LIMIT " + limite;
    }


    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la cual se agregara la logica del limite.
     * @param limite     Entero que representa la cantidad maxima de valores recuperados.
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected Take(String sql, int limite, List<Column> parametros) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        super();
        if (Objects.isNull(limite)) {
            throw new ValorUndefined("El Limite proporcionado es 0 o inferior, por lo cual no se puede" +
                    "realizar la consulta a BD's");
        }
        this.parametros = parametros;
        this.sql = sql + "LIMIT " + limite;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @throws InstantiationException Lanza esta excepción si ocurre un error al crear una nueva instancia
     *                                del tipo de modelo proporcionado
     * @throws IllegalAccessException Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     */
    public <T extends JBSqlUtils> List<T> get() throws InstantiationException, IllegalAccessException {
        return (List<T>) super.getAll((T) this.modelo, this.sql, this.parametros);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Ejecuta la sentencia SQL proporcionada y retorna la cantidad de filas afectadas
     *
     * @return Retorna un Entero que representa la cantidad de filas afectadas al ejecutar la sentencia SQL
     * proporcionada.
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public int execute() throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Execute(this.sql, this.parametros).execute();
    }


}

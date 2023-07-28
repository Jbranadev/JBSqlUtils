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
import io.github.josecarlosbran.JBSqlUtils.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.JBSqlUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * @author Jose Bran
 * Clase que proporciona la capacidad de agregar una sentencia OrderBy a la consulta trasladada como parametro
 */
public class OrderBy<T> extends Get {
    private String sql;
    private T modelo;

    /**
     * Lista de los parametros a envíar
     */
    protected List<Column> parametros = new ArrayList<>();

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la que se agregara la logica ORDER BY
     * @param columna    Columna a evaluar dentro de la sentencia ORDER BY
     * @param orderType  Tipo de ordenamiento que se realizara
     * @param modelo     Modelo que invoca la ejecución de los metodos.
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected OrderBy(String sql, String columna, OrderType orderType, T modelo, List<Column> parametros) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(orderType)) {
            throw new ValorUndefined("El tipo de ordenamiento proporcionado es NULL");
        }
        if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + " ORDER BY " + columna + orderType.getValor();
    }


    /**
     * Constructor que recibe como parametro:
     *
     * @param sql               Sentencia SQL a la que se agregara la logica ORDER BY
     * @param columna           Columna a evaluar dentro de la sentencia ORDER BY
     * @param orderType         Tipo de ordenamiento que se realizara
     * @param modelo            Modelo que invoca la ejecución de los metodos.
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected OrderBy(String sql, String columna, OrderType orderType, T modelo, List<Column> parametros, Boolean getPropertySystem) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super(getPropertySystem);
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(orderType)) {
            throw new ValorUndefined("El tipo de ordenamiento proporcionado es NULL");
        }
        if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
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
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected OrderBy(String sql, String columna, OrderType orderType, List<Column> parametros) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
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


    /**
     * Retorna un objeto del tipo Take que permite agregar esta sentencia a la Logica de la sentencia
     * SQL a ejecutar.
     *
     * @param limite Entero que representa la cantidad maxima de valores recuperados.
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public Take take(int limite) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if (Objects.isNull(this.modelo)) {
            return new Take(this.sql, limite, this.parametros);
        } else {
            if (!this.getGetPropertySystem()) {
                Take take = new Take(this.sql, limite, this.modelo, this.parametros, false);
                take.llenarPropertiesFromModel(this);
                return take;
            }
            return new Take(this.sql, limite, this.modelo, this.parametros);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Llena el modelo que invoca este metodo con la información que obtiene de BD's
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     */
    public <T extends JBSqlUtils> void get() {
        super.get((T) this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene un modelo del tipo que invoca este metodo con la información que obtiene de BD's
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna un un modelo del tipo que invoca este metodo con la información que obtiene de BD's.
     */
    public <T extends JBSqlUtils> T first() {
        return (T) super.first((T) this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene un modelo del tipo que invoca este metodo con la información que obtiene de BD's
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna un un modelo del tipo que invoca este metodo con la información que obtiene de BD's.
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    public <T extends JBSqlUtils> T firstOrFail() throws ModelNotFound {
        return (T) super.firstOrFail((T) this.modelo, this.sql, this.parametros);
    }

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
    public <T extends JBSqlUtils> List<T> getAll() throws InstantiationException, IllegalAccessException {
        return (List<T>) super.getAll((T) this.modelo, this.sql, this.parametros);
    }


    /**
     * Obtiene una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     *
     * @param columnas Lista con los nombres de las columnas que se desea recuperar, si se desea obtener
     *                 odas las columnas de la tabla especificada envíar NULL como parametro
     * @return Retorna una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     */
    public List<JSONObject> getInJsonObjects(List<String> columnas) {
        return super.get(this.sql, this.parametros, columnas);
    }

}

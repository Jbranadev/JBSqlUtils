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
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.List;

/**
 * Clase que proporciona la logica para tomar una cantidad de resultados, siendo esa cantidad el limite trasladado como
 * parametro para el constructor.
 *
 * @author Jose Bran
 */
public class Take<T> extends MethodsTake {

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
        if (limite <= 0) {
            throw new ValorUndefined("El Limite proporcionado es 0 o inferior, por lo cual no se puede" +
                    "realizar la consulta a BD's");
        }
        /*if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
        }*/
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + "LIMIT " + limite;
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql               Sentencia SQL a la cual se agregara la logica del limite.
     * @param limite            Entero que representa la cantidad maxima de valores recuperados.
     * @param modelo            Modelo que solicita la creación de esta clase
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected Take(String sql, int limite, T modelo, List<Column> parametros, Boolean getPropertySystem) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super(getPropertySystem);
        if (limite <= 0) {
            throw new ValorUndefined("El Limite proporcionado es 0 o inferior, por lo cual no se puede" +
                    "realizar la consulta a BD's");
        }
        /*if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
        }*/
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
        if (limite <= 0) {
            throw new ValorUndefined("El Limite proporcionado es 0 o inferior, por lo cual no se puede" +
                    "realizar la consulta a BD's");
        }
        this.parametros = parametros;
        this.sql = sql + " LIMIT " + limite;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return Retorna una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> List<T> get() throws Exception {
        return (List<T>) super.getAll((T) this.modelo, this.sql, this.parametros);
    }
}

package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

import java.util.Objects;


/**
 * @author Jose Bran
 * Clase que brinda acceso a la logica de los metodos de una sentencia where
 */
public class MethodsWhere<T> extends MethodsOrderBy {


    /**
     * Constructor por default de la clase MethodsWhere, que inicializa la clase
     *
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected MethodsWhere() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    /**
     * Constructor por default de la clase MethodsWhere, que inicializa la clase
     *
     * @param getGetPropertiesSystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected MethodsWhere(Boolean getGetPropertiesSystem) throws DataBaseUndefind, PropertiesDBUndefined {
        super(getGetPropertiesSystem);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Retorna un objeto del tipo ORDER BY que permite agregar esta expresión a la sentencia SQL
     *
     * @param columna   Columna a evaluar dentro de la sentencia ORDER BY
     * @param orderType Tipo de ordenamiento que se realizara
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public OrderBy orderBy(String columna, OrderType orderType) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if (Objects.isNull(this.modelo)) {
            return new OrderBy(this.sql, columna, orderType, this.parametros);
        } else {
            if (!this.getGetPropertySystem()) {
                OrderBy orderby = new OrderBy(this.sql, columna, orderType, this.modelo, this.parametros, false);
                //orderby.llenarPropertiesFromModel(this);
                return orderby;
            }
            return new OrderBy(this.sql, columna, orderType, this.modelo, this.parametros);
        }
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
                //take.llenarPropertiesFromModel(this);
                return take;
            }
            return new Take(this.sql, limite, this.modelo, this.parametros);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Ejecuta la sentencia SQL proporcionada y retorna la cantidad de filas afectadas
     *
     * @return Retorna un Entero que representa la cantidad de filas afectadas al ejecutar la sentencia SQL
     * proporcionada.
     * @throws Exception Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    public int execute() throws Exception {
        return new Execute(this.sql, this.parametros).execute();
    }


}
package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;


/**
 * Clase que proporciona la logica para agregar un Cierre de Parentecis a una consulta SQL
 *
 * @author Jose Bran
 */
public class closeParentecis<T> extends MethodsWhere {


    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la que se agregara el cierre de parentecis
     * @param modelo     Modelo que invocara los métodos de esta clase
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected closeParentecis(String sql, T modelo, List<Column> parametros) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + Operator.CLOSE_PARENTESIS;
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql                    Sentencia SQL a la que se agregara el cierre de parentecis
     * @param modelo                 Modelo que invocara los métodos de esta clase
     * @param parametros             Lista de parametros a ser agregados a la sentencia SQL
     * @param getGetPropertiesSystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected closeParentecis(String sql, T modelo, List<Column> parametros, Boolean getGetPropertiesSystem) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super(getGetPropertiesSystem);
        if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + Operator.CLOSE_PARENTESIS;
    }


    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la que se agregara el cierre de parentecis
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected closeParentecis(String sql, List<Column> parametros) throws DataBaseUndefind, PropertiesDBUndefined {
        super();
        this.parametros = parametros;
        this.sql = sql + Operator.CLOSE_PARENTESIS.getOperador();
    }


    /**
     * Constructor que recibe como parametro:
     *
     * @param sql          Sentencia SQL a la que se agregara el cierre de parentecis
     * @param modelo       Modelo que invocara los métodos de esta clase
     * @param parametros   Lista de parametros a ser agregados a la sentencia SQL
     * @param operatorPost Operador posterior a colocar despues del cierre de parentecis
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected closeParentecis(String sql, T modelo, List<Column> parametros, Operator operatorPost) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + Operator.CLOSE_PARENTESIS + operatorPost.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql                    Sentencia SQL a la que se agregara el cierre de parentecis
     * @param modelo                 Modelo que invocara los métodos de esta clase
     * @param parametros             Lista de parametros a ser agregados a la sentencia SQL
     * @param operatorPost           Operador posterior a colocar despues del cierre de parentecis
     * @param getGetPropertiesSystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected closeParentecis(String sql, T modelo, List<Column> parametros, Operator operatorPost, Boolean getGetPropertiesSystem) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super(getGetPropertiesSystem);
        if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
        }
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + Operator.CLOSE_PARENTESIS + operatorPost.getOperador();
    }


    /**
     * Constructor que recibe como parametro:
     *
     * @param sql          Sentencia SQL a la que se agregara el cierre de parentecis
     * @param parametros   Lista de parametros a ser agregados a la sentencia SQL
     * @param operatorPost Operador posterior a colocar despues del cierre de parentecis
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected closeParentecis(String sql, List<Column> parametros, Operator operatorPost) throws DataBaseUndefind, PropertiesDBUndefined {
        super();
        this.parametros = parametros;
        this.sql = sql + Operator.CLOSE_PARENTESIS.getOperador() + operatorPost.getOperador();
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

    /**
     * Obtiene una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     *
     * @param columnas Lista con los nombres de las columnas que se desea recuperar, si se desea obtener
     *                 odas las columnas de la tabla especificada envíar NULL como parametro
     * @return Retorna una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public List<JSONObject> getInJsonObjects(List<String> columnas) throws Exception {
        return super.get(this.sql, this.parametros, columnas);
    }


}

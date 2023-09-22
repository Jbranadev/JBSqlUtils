package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.List;

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
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected closeParentecis(String sql, T modelo, List<Column> parametros) throws ValorUndefined {
        super();
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql                    Sentencia SQL a la que se agregara el cierre de parentecis
     * @param modelo                 Modelo que invocara los métodos de esta clase
     * @param parametros             Lista de parametros a ser agregados a la sentencia SQL
     * @param getGetPropertiesSystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected closeParentecis(String sql, T modelo, List<Column> parametros, Boolean getGetPropertiesSystem) throws ValorUndefined {
        super(getGetPropertiesSystem);
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la que se agregara el cierre de parentecis
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     */
    protected closeParentecis(String sql, List<Column> parametros) {
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
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected closeParentecis(String sql, T modelo, List<Column> parametros, Operator operatorPost) throws ValorUndefined {
        super();
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + Operator.CLOSE_PARENTESIS.getOperador() + operatorPost.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql                    Sentencia SQL a la que se agregara el cierre de parentecis
     * @param modelo                 Modelo que invocara los métodos de esta clase
     * @param parametros             Lista de parametros a ser agregados a la sentencia SQL
     * @param operatorPost           Operador posterior a colocar despues del cierre de parentecis
     * @param getGetPropertiesSystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected closeParentecis(String sql, T modelo, List<Column> parametros, Operator operatorPost, Boolean getGetPropertiesSystem) throws ValorUndefined {
        super(getGetPropertiesSystem);
        this.parametros = parametros;
        this.modelo = modelo;
        this.sql = sql + Operator.CLOSE_PARENTESIS.getOperador() + operatorPost.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql          Sentencia SQL a la que se agregara el cierre de parentecis
     * @param parametros   Lista de parametros a ser agregados a la sentencia SQL
     * @param operatorPost Operador posterior a colocar despues del cierre de parentecis
     */
    protected closeParentecis(String sql, List<Column> parametros, Operator operatorPost) {
        super();
        this.parametros = parametros;
        this.sql = sql + Operator.CLOSE_PARENTESIS.getOperador() + operatorPost.getOperador();
    }
}

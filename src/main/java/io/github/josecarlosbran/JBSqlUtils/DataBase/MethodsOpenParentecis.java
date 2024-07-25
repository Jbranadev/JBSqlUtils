package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose Bran
 * Clase que brinda acceso a la logica de los metodos de una sentencia que incluye la apertura de un parentecis
 */
public class MethodsOpenParentecis<T> extends Get {
    protected String sql;
    protected T modelo = null;
    /**
     * Lista de los parámetros a envíar
     */
    protected List<Column> parametros = new ArrayList<>();

    /**
     * Constructor por default de la clase MethodsWhere, que inicializa la clase
     */
    protected MethodsOpenParentecis() {
        super();
    }

    /**
     * Constructor por default de la clase MethodsWhere, que inicializa la clase
     *
     * @param getGetPropertiesSystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    protected MethodsOpenParentecis(Boolean getGetPropertiesSystem) {
        super(getGetPropertiesSystem);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Retorna un objeto del tipo AND que permite agregar esta expresión a la sentencia SQL
     *
     * @param columna  Columna a evaluar dentro de la sentencia AND
     * @param operador Operador con el cual se evaluara la columna
     * @param valor    Valor contra el que se evaluara la columna
     * @return objeto del tipo AND que permite agregar esta expresión a la sentencia SQL
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public And and(String columna, Operator operador, Object valor) throws ValorUndefined {
        return new CommonsMethods().and(columna, operador, valor, this.sql, (JBSqlUtils) this.modelo, this.parametros, this.getGetPropertySystem());
    }

    /**
     * Retorna un objeto del tipo OR que permite agregar esta expresión a la sentencia SQL
     *
     * @param columna  Columna a evaluar dentro de la sentencia OR
     * @param operador Operador con el cual se evaluara la columna
     * @param valor    Valor contra el que se evaluara la columna
     * @return objeto del tipo OR que permite agregar esta expresión a la sentencia SQL
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public Or or(String columna, Operator operador, Object valor) throws ValorUndefined {
        return new CommonsMethods().or(columna, operador, valor, this.sql, (JBSqlUtils) this.modelo, this.parametros, this.getGetPropertySystem());
    }

    /**
     * @param operatorPrev Operador a colocar antes de la apertura de parentecis
     * @param columna      Columna a evaluar dentro de la sentencia AND
     * @param operador     Operador con el cual se evaluara la columna
     * @param valor        Valor contra el que se evaluara la columna
     * @return Retorna un objeto OpenParentecis el cual proporciona acceso a los métodos necesarios
     * para filtrar de una mejor manera nuestra consulta, No olvide llamar al metodo close parentecis cuando
     * haya finalizado la logica dentro de sus parentecis
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public openParentecis openParentecis(Operator operatorPrev, String columna, Operator operador, Object valor) throws ValorUndefined {
        return new CommonsMethods().openParentecis(operatorPrev, columna, operador, valor, this.sql, (JBSqlUtils) this.modelo, this.parametros, this.getGetPropertySystem());
    }

    /**
     * Agrega la posibilidad de realizar un cierre de parentecis dentro de la logica de nuestra sentencia SQL
     *
     * @param operatorPost Operador a colocar despues del cierre de parentecis
     * @return Retorna un objeto closeParentecis, el cual da acceso al resto de métodos que podemos llamar.
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public closeParentecis closeParentecis(Operator operatorPost) throws ValorUndefined {
        return new CommonsMethods().closeParentecis(operatorPost, this.sql, (JBSqlUtils) this.modelo, this.parametros, this.getGetPropertySystem());
    }
}

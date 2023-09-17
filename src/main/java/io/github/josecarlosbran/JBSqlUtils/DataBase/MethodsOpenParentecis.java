package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     *
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected MethodsOpenParentecis() throws DataBaseUndefind, PropertiesDBUndefined {
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
    protected MethodsOpenParentecis(Boolean getGetPropertiesSystem) throws DataBaseUndefind, PropertiesDBUndefined {
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
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public And and(String columna, Operator operador, Object valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if (Objects.isNull(this.modelo)) {
            return new And(this.sql, columna, operador, valor, this.parametros);
        } else {
            if (!this.getGetPropertySystem()) {
                And and = new And(this.sql, columna, operador, valor, this.modelo, this.parametros, false);
                //and.llenarPropertiesFromModel(this);
                return and;
            }
            return new And(this.sql, columna, operador, valor, this.modelo, this.parametros);
        }
    }

    /**
     * Retorna un objeto del tipo OR que permite agregar esta expresión a la sentencia SQL
     *
     * @param columna  Columna a evaluar dentro de la sentencia OR
     * @param operador Operador con el cual se evaluara la columna
     * @param valor    Valor contra el que se evaluara la columna
     * @return objeto del tipo OR que permite agregar esta expresión a la sentencia SQL
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public Or or(String columna, Operator operador, Object valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if (Objects.isNull(this.modelo)) {
            return new Or(this.sql, columna, operador, valor, this.parametros);
        } else {
            if (!this.getGetPropertySystem()) {
                Or or = new Or(this.sql, columna, operador, valor, this.modelo, this.parametros, false);
                //or.llenarPropertiesFromModel(this);
                return or;
            }
            return new Or(this.sql, columna, operador, valor, this.modelo, this.parametros);
        }
    }

    /**
     * @param operatorPrev Operador a colocar antes de la apertura de parentecis
     * @param columna      Columna a evaluar dentro de la sentencia AND
     * @param operador     Operador con el cual se evaluara la columna
     * @param valor        Valor contra el que se evaluara la columna
     * @return Retorna un objeto OpenParentecis el cual proporciona acceso a los métodos necesarios
     * para filtrar de una mejor manera nuestra consulta, No olvide llamar al metodo close parentecis cuando
     * haya finalizado la logica dentro de sus parentecis
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public openParentecis openParentecis(Operator operatorPrev, String columna, Operator operador, Object valor) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        if (Objects.isNull(this.modelo)) {
            if (Objects.isNull(operatorPrev)) {
                return new openParentecis(this.sql, this.parametros, columna, operador, valor);
            } else {
                return new openParentecis(this.sql, this.parametros, operatorPrev, columna, operador, valor);
            }
        } else {
            if (Objects.isNull(operatorPrev)) {
                if (!this.getGetPropertySystem()) {
                    openParentecis open = new openParentecis(this.sql, this.modelo, this.parametros, columna, operador, valor, false);
                    //open.llenarPropertiesFromModel(this);
                    return open;
                }
                return new openParentecis(this.sql, this.modelo, this.parametros, columna, operador, valor);
            } else {
                if (!this.getGetPropertySystem()) {
                    openParentecis open = new openParentecis(this.sql, this.modelo, this.parametros, operatorPrev, columna, operador, valor, false);
                    //open.llenarPropertiesFromModel(this);
                    return open;
                }
                return new openParentecis(this.sql, this.modelo, this.parametros, operatorPrev, columna, operador, valor);
            }
        }
    }

    /**
     * Agrega la posibilidad de realizar un cierre de parentecis dentro de la logica de nuestra sentencia SQL
     *
     * @param operatorPost Operador a colocar despues del cierre de parentecis
     * @return Retorna un objeto closeParentecis, el cual da acceso al resto de métodos que podemos llamar.
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public closeParentecis closeParentecis(Operator operatorPost) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        if (Objects.isNull(this.modelo)) {
            if (Objects.isNull(operatorPost)) {
                return new closeParentecis(this.sql, this.parametros);
            } else {
                return new closeParentecis(this.sql, this.parametros, operatorPost);
            }
        } else {
            if (Objects.isNull(operatorPost)) {
                if (!this.getGetPropertySystem()) {
                    closeParentecis close = new closeParentecis(this.sql, this.modelo, this.parametros, false);
                    //close.llenarPropertiesFromModel(this);
                    return close;
                }
                return new closeParentecis(this.sql, this.modelo, this.parametros);
            } else {
                if (!this.getGetPropertySystem()) {
                    closeParentecis close = new closeParentecis(this.sql, this.modelo, this.parametros, operatorPost, false);
                    //close.llenarPropertiesFromModel(this);
                    return close;
                }
                return new closeParentecis(this.sql, this.modelo, this.parametros, operatorPost);
            }
        }
    }
}

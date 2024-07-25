package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.List;
import java.util.Objects;

/**
 * @author Jose Bran
 * Clase que brinda acceso a la logica de los metodos comunes que comparte mas de una clase a extender en comun
 */
public class CommonsMethods<T> {
    /**
     * Constructor por default de la clase CommonsMethods, que inicializa la clase
     */
    protected CommonsMethods() {
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Retorna un objeto del tipo AND que permite agregar esta expresión a la sentencia SQL
     *
     * @param columna           Columna a evaluar dentro de la sentencia AND
     * @param operador          Operador con el cual se evaluara la columna
     * @param valor             Valor contra el que se evaluara la columna
     * @param sql               Sentencia SQL a la que se agregara la apertura de parentecis
     * @param modelo            Modelo que invocara los métodos de esta clase
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @return objeto del tipo AND que permite agregar esta expresión a la sentencia SQL
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected <T extends JBSqlUtils> And and(String columna, Operator operador, Object valor, String sql, T modelo, List<Column> parametros, Boolean getPropertySystem) throws ValorUndefined {
        if (Objects.isNull(modelo)) {
            return new And(sql, columna, operador, valor, parametros);
        } else {
            if (!getPropertySystem) {
                And and = new And(sql, columna, operador, valor, modelo, parametros, false);
                return and;
            }
            return new And(sql, columna, operador, valor, modelo, parametros);
        }
    }

    /**
     * Retorna un objeto del tipo OR que permite agregar esta expresión a la sentencia SQL
     *
     * @param columna           Columna a evaluar dentro de la sentencia OR
     * @param operador          Operador con el cual se evaluara la columna
     * @param valor             Valor contra el que se evaluara la columna
     * @param sql               Sentencia SQL a la que se agregara la apertura de parentecis
     * @param modelo            Modelo que invocara los métodos de esta clase
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @return objeto del tipo OR que permite agregar esta expresión a la sentencia SQL
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected <T extends JBSqlUtils> Or or(String columna, Operator operador, Object valor, String sql, T modelo, List<Column> parametros, Boolean getPropertySystem) throws ValorUndefined {
        if (Objects.isNull(modelo)) {
            return new Or(sql, columna, operador, valor, parametros);
        } else {
            if (!getPropertySystem) {
                Or or = new Or(sql, columna, operador, valor, modelo, parametros, false);
                return or;
            }
            return new Or(sql, columna, operador, valor, modelo, parametros);
        }
    }

    /**
     * @param operatorPrev      Operador a colocar antes de la apertura de parentecis
     * @param columna           Columna a evaluar dentro de la sentencia AND
     * @param operador          Operador con el cual se evaluara la columna
     * @param valor             Valor contra el que se evaluara la columna
     * @param sql               Sentencia SQL a la que se agregara la apertura de parentecis
     * @param modelo            Modelo que invocara los métodos de esta clase
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @return Retorna un objeto OpenParentecis el cual proporciona acceso a los métodos necesarios
     * para filtrar de una mejor manera nuestra consulta, No olvide llamar al metodo close parentecis cuando
     * haya finalizado la logica dentro de sus parentecis
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected <T extends JBSqlUtils> openParentecis openParentecis(Operator operatorPrev, String columna, Operator operador, Object valor, String sql, T modelo, List<Column> parametros, Boolean getPropertySystem) throws ValorUndefined {
        if (Objects.isNull(modelo)) {
            if (Objects.isNull(operatorPrev)) {
                return new openParentecis(sql, parametros, columna, operador, valor);
            } else {
                return new openParentecis(sql, parametros, operatorPrev, columna, operador, valor);
            }
        } else {
            if (Objects.isNull(operatorPrev)) {
                if (!getPropertySystem) {
                    return new openParentecis(sql, modelo, parametros, columna, operador, valor, false);
                }
                return new openParentecis(sql, modelo, parametros, columna, operador, valor);
            } else {
                if (!getPropertySystem) {
                    return new openParentecis(sql, modelo, parametros, operatorPrev, columna, operador, valor, false);
                }
                return new openParentecis(sql, modelo, parametros, operatorPrev, columna, operador, valor);
            }
        }
    }

    /**
     * Agrega la posibilidad de realizar un cierre de parentecis dentro de la logica de nuestra sentencia SQL
     *
     * @param operatorPost      Operador a colocar despues del cierre de parentecis
     * @param sql               Sentencia SQL a la que se agregara la apertura de parentecis
     * @param modelo            Modelo que invocara los métodos de esta clase
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @return Retorna un objeto closeParentecis, el cual da acceso al resto de métodos que podemos llamar.
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    protected <T extends JBSqlUtils> closeParentecis closeParentecis(Operator operatorPost, String sql, T modelo, List<Column> parametros, Boolean getPropertySystem) throws ValorUndefined {
        if (Objects.isNull(modelo)) {
            if (Objects.isNull(operatorPost)) {
                return new closeParentecis(sql, parametros);
            } else {
                return new closeParentecis(sql, parametros, operatorPost);
            }
        } else {
            if (Objects.isNull(operatorPost)) {
                if (!getPropertySystem) {
                    closeParentecis close = new closeParentecis(sql, modelo, parametros, false);
                    return close;
                }
                return new closeParentecis(sql, modelo, parametros);
            } else {
                if (!getPropertySystem) {
                    return new closeParentecis(sql, modelo, parametros, operatorPost, false);
                }
                return new closeParentecis(sql, modelo, parametros, operatorPost);
            }
        }
    }
}

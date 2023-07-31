package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Column;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
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


/**
 * @author Jose Bran
 * Clase que proporciona la logica para agregar un Cierre de Parentecis a una consulta SQL
 */
public class closeParentecis<T> extends Get {

    private T modelo = null;

    private String sql;

    /**
     * Lista de los parametros a envíar
     */
    protected List<Column> parametros = new ArrayList<>();


    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la que se agregara el cierre de parentecis
     * @param modelo     Modelo que invocara los metodos de esta clase
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
     * @param modelo                 Modelo que invocara los metodos de esta clase
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
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
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
     * @param modelo       Modelo que invocara los metodos de esta clase
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
     * @param modelo                 Modelo que invocara los metodos de esta clase
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
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
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


    /**
     * Retorna un objeto del tipo AND que permite agregar esta expresión a la sentencia SQL
     *
     * @param columna  Columna a evaluar dentro de la sentencia AND
     * @param operador Operador con el cual se evaluara la columna
     * @param valor    Valor contra el que se evaluara la columna
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
            if(!this.getGetPropertySystem()){
                And and =new And(this.sql, columna, operador, valor, this.modelo, this.parametros, false);
                and.llenarPropertiesFromModel(this);
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
            if(!this.getGetPropertySystem()){
                Or or=new Or(this.sql, columna, operador, valor, this.modelo, this.parametros, false);
                or.llenarPropertiesFromModel(this);
                return or;
            }
            return new Or(this.sql, columna, operador, valor, this.modelo, this.parametros);
        }
    }

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
                orderby.llenarPropertiesFromModel(this);
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
                take.llenarPropertiesFromModel(this);
                return take;
            }
            return new Take(this.sql, limite, this.modelo, this.parametros);
        }
    }


    /**
     * @param operatorPrev Operador a colocar antes de la apertura de parentecis
     * @param columna      Columna a evaluar dentro de la sentencia AND
     * @param operador     Operador con el cual se evaluara la columna
     * @param valor        Valor contra el que se evaluara la columna
     * @return Retorna un objeto OpenParentecis el cual proporciona acceso a los metodos necesarios
     * para filtrar de una mejor manera nuestra consulta, No olvide llamar al metodo close parentecis cuando
     * haya finalizado la logica dentro de sus parentecis
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
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
                    open.llenarPropertiesFromModel(this);
                    return open;
                }
                return new openParentecis(this.sql, this.modelo, this.parametros, columna, operador, valor);
            } else {
                if (!this.getGetPropertySystem()) {
                    openParentecis open = new openParentecis(this.sql, this.modelo, this.parametros, operatorPrev, columna, operador, valor, false);
                    open.llenarPropertiesFromModel(this);
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
     * @return Retorna un objeto closeParentecis, el cual da acceso al resto de metodos que podemos llamar.
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
                    close.llenarPropertiesFromModel(this);
                    return close;
                }
                return new closeParentecis(this.sql, this.modelo, this.parametros);
            } else {
                if (!this.getGetPropertySystem()) {
                    closeParentecis close = new closeParentecis(this.sql, this.modelo, this.parametros, operatorPost, false);
                    close.llenarPropertiesFromModel(this);
                    return close;
                }
                return new closeParentecis(this.sql, this.modelo, this.parametros, operatorPost);
            }
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
    public <T extends JBSqlUtils> void get() throws Exception {
        super.get((T) this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene un modelo del tipo que invoca este metodo con la información que obtiene de BD's
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @return Retorna un un modelo del tipo que invoca este metodo con la información que obtiene de BD's.
     */
    public <T extends JBSqlUtils> T first() throws Exception {
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
    public <T extends JBSqlUtils> List<T> getAll() throws Exception {
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

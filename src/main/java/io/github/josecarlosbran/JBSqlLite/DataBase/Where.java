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
package io.github.josecarlosbran.JBSqlLite.DataBase;


import io.github.josecarlosbran.JBSqlLite.Column;
import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.getColumn;
import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * @author Jose Bran
 * Clase que proporciona la logica para agregar una sentencia WHERE a la consulta a realizar.
 */
public class Where<T> extends Get {
    private String sql;
    private T modelo=null;

    /**
     * Lista de los parametros a envíar
     */
    protected List<Column> parametros = new ArrayList<>();

    /**
     * Constructor que recibe como parametro:
     * @param columna Columna a evaluar dentro de la sentencia WHERE
     * @param operador Operador con el cual se evaluara la columna
     * @param valor Valor contra el que se evaluara la columna
     * @param modelo Modelo que invocara los metodos de esta clase
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public Where(String columna, Operator operador, Object valor, T modelo) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        if (Objects.isNull(modelo)) {
            throw new ValorUndefined("El Modelo proporcionado es NULL");
        }
        this.modelo = modelo;
        this.parametros.add(getColumn(valor));
        this.sql = " WHERE "+Operator.OPEN_PARENTESIS.getOperador()+columna + operador.getOperador() + "?" +Operator.CLOSE_PARENTESIS.getOperador();
    }



    /**
     * Constructor que recibe como parametro:
     * @param columna Columna a evaluar dentro de la sentencia WHERE
     * @param operador Operador con el cual se evaluara la columna
     * @param valor Valor contra el que se evaluara la columna
     * @param sql Sentencia SQL a la cual se agregara la Sentencia WHERE
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public Where(String columna, Operator operador, Object valor, String sql, List<Column> parametros) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        this.parametros= parametros;
        this.parametros.add(getColumn(valor));
        this.sql = sql+" WHERE "+Operator.OPEN_PARENTESIS.getOperador()+columna + operador.getOperador() + "?" +Operator.CLOSE_PARENTESIS.getOperador();
    }




    /**
     * Retorna un objeto del tipo AND que permite agregar esta expresión a la sentencia SQL
     * @param columna Columna a evaluar dentro de la sentencia AND
     * @param operador Operador con el cual se evaluara la columna
     * @param valor Valor contra el que se evaluara la columna
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public And and(String columna, Operator operador, Object valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if(Objects.isNull(this.modelo)){
            return new And(this.sql, columna, operador, valor, this.parametros);
        }else{
            return new And(this.sql, columna, operador, valor, this.modelo, this.parametros);
        }
    }

    /**
     * Retorna un objeto del tipo OR que permite agregar esta expresión a la sentencia SQL
     * @param columna Columna a evaluar dentro de la sentencia OR
     * @param operador Operador con el cual se evaluara la columna
     * @param valor Valor contra el que se evaluara la columna
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public Or or(String columna, Operator operador, Object valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if(Objects.isNull(this.modelo)){
            return new Or(this.sql, columna, operador, valor, this.parametros);
        }else{
            return new Or(this.sql, columna, operador, valor, this.modelo, this.parametros);
        }
    }



    /**
     * Retorna un objeto del tipo ORDER BY que permite agregar esta expresión a la sentencia SQL
     * @param columna Columna a evaluar dentro de la sentencia ORDER BY
     * @param orderType Tipo de ordenamiento que se realizara
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public OrderBy orderBy(String columna, OrderType orderType) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new OrderBy(this.sql, columna, orderType, this.modelo, this.parametros);
    }

    /**
     * Retorna un objeto del tipo Take que permite agregar esta sentencia a la Logica de la sentencia
     * SQL a ejecutar.
     * @param limite Entero que representa la cantidad maxima de valores recuperados.
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public Take take(int limite) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if(Objects.isNull(this.modelo)){
            return new Take(this.sql, limite, this.parametros);
        }else{
            return new Take(this.sql, limite, this.modelo, this.parametros);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Llena el modelo que invoca este metodo con la información que obtiene de BD's
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     */
    public <T extends Methods_Conexion> void get(){
        super.get((T)this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene un modelo del tipo que invoca este metodo con la información que obtiene de BD's
     * @return Retorna un un modelo del tipo que invoca este metodo con la información que obtiene de BD's.
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     */
    public <T extends Methods_Conexion> T first(){
        return (T) super.first((T) this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene un modelo del tipo que invoca este metodo con la información que obtiene de BD's
     * @return Retorna un un modelo del tipo que invoca este metodo con la información que obtiene de BD's.
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     * SQL realizada.
     */
    public <T extends Methods_Conexion> T firstOrFail() throws ModelNotFound {
        return (T) super.firstOrFail((T)this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @return Retorna una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo.
     * @throws InstantiationException Lanza esta excepción si ocurre un error al crear una nueva instancia
     * del tipo de modelo proporcionado
     * @throws IllegalAccessException Lanza esta excepción si hubiera algun problema al invocar el metodo Set
     */
    public <T extends Methods_Conexion> List<T> getAll() throws InstantiationException, IllegalAccessException {
        return (List<T>) super.getAll((T)this.modelo, this.sql, this.parametros);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Ejecuta la sentencia SQL proporcionada y retorna la cantidad de filas afectadas
     * @return Retorna un Entero que representa la cantidad de filas afectadas al ejecutar la sentencia SQL
     * proporcionada.
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public int execute() throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Execute(this.sql, this.parametros).execute();
    }




}

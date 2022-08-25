package io.github.josecarlosbran.JBSqlLite.DataBase;

import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;

import java.util.List;
import java.util.Objects;

/**
 * @author Jose Bran
 * Clase que proporciona la logica para tomar una cantidad de resultados, siendo esa cantidad el limite trasladado como
 * parametro para el constructor.
 */
public class Take<T> extends Get{
    private String sql;
    private T modelo=null;

    /**
     * Constructor que recibe como parametro:
     * @param sql Sentencia SQL a la cual se agregara la logica del limite.
     * @param limite Entero que representa la cantidad maxima de valores recuperados.
     * @param modelo Modelo que solicita la creación de esta clase
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected Take(String sql, int limite, T modelo) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();

        if (Objects.isNull(limite)) {
            throw new ValorUndefined("El Limite proporcionado es 0 o inferior, por lo cual no se puede" +
                    "realizar la consulta a BD's");
        }
        this.modelo=modelo;
        this.sql=sql+ "LIMIT "+limite;
    }


    /**
     * Constructor que recibe como parametro:
     * @param sql Sentencia SQL a la cual se agregara la logica del limite.
     * @param limite Entero que representa la cantidad maxima de valores recuperados.
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected Take(String sql, int limite) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        super();
        if (Objects.isNull(limite)) {
            throw new ValorUndefined("El Limite proporcionado es 0 o inferior, por lo cual no se puede" +
                    "realizar la consulta a BD's");
        }
        this.sql=sql+ "LIMIT "+limite;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    public <T extends Methods_Conexion> List<T> get() throws InstantiationException, IllegalAccessException {
        return (List<T>) super.getAll((T)this.modelo, this.sql);
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
        return new Execute(this.sql).execute();
    }


}

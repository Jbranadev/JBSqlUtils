package io.github.josecarlosbran.JBSqlLite.DataBase;

import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * @author Jose Bran
 * Clase que proporciona los metodos necesarios para la lógica de un Update en BD's sin necesidad
 * de tener un modelo de la tabla que se desea actualizar.
 */
public class Update{
    private String sql;

    /**
     * Constructor que recibe como parametro:
     * @param TableName El nombre de la tabla sobre la cual se desea realizar el Update.
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    public Update(String TableName) throws ValorUndefined {
        String respuesta = "";
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        this.sql= "UPDATE "+TableName+" ";
    }

    /**
     * Entrega la capacidad de setear otro valor antes de ejecutar la sentencia Upddate
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value Valor que se asignara a la columna.
     * @return Retorna un objeto Set que entrega la capacidad de setear otro valor
     * antes de ejecutar la sentencia Upddate
     * @throws ValorUndefined ValorUndefined ValorUndefined Lanza esta Excepción si
     * alguno de los parametros proporcionados esta vacío o es Null
     */
    public Set set(String columName, String value) throws ValorUndefined {
        return new Set(columName, value, this.sql);
    }



}

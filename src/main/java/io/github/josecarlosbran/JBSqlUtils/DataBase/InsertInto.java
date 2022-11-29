package io.github.josecarlosbran.JBSqlUtils.DataBase;


import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class InsertInto {

    private String sql;
    /**
     * Constructor que recibe como parametro:
     *
     * @param TableName El nombre de la tabla sobre la cual se desea realizar el Update.
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    public InsertInto(String TableName) throws ValorUndefined {
        String respuesta = "";
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        this.sql = "INSERT INTO " + TableName + " ";
    }

    /**
     * Entrega la capacidad de setear otro valor antes de ejecutar la sentencia Upddate
     *
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value     Valor que se asignara a la columna.
     * @return Retorna un objeto Set que entrega la capacidad de setear otro valor
     * antes de ejecutar la sentencia Upddate
     * @throws ValorUndefined ValorUndefined ValorUndefined Lanza esta Excepción si
     *                        alguno de los parametros proporcionados esta vacío o es Null
     */
    public Value value(String columName, Object value) throws ValorUndefined {
        return new Value(columName, value, this.sql);
    }


}

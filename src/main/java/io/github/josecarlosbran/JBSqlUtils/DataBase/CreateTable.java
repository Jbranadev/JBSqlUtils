package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona un punto de entrada para poder crear una tabla en BD's
 *
 * @author Jose Bran
 */
public class CreateTable {
    private final String tableName;

    /**
     * Constructor que recibe como parametro:
     *
     * @param TableName El nombre de la tabla que se desea eliminar.
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    protected CreateTable(String TableName) throws ValorUndefined {
        super();
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        this.tableName = TableName;
    }

    /**
     * Agrega una columna a la sentencia SQL a ejecutar al momento de llamar al metodo creteTable()
     *
     * @param columna Columna a agregar
     * @return retorna un objeto del tipo AddColumn que brinda la capacidad de dar una mayor logica a la operación de createTable
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    public AddColumn addColumn(Column columna) throws ValorUndefined {
        return new AddColumn(this.tableName, columna);
    }
}

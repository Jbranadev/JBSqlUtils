package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Agrega una columna a la sentencia SQL a ejecutar al momento de llamar al metodo creteTable()
 *
 * @author Jose Bran
 */
public class AddColumn extends Methods_Conexion {

    private String tableName;

    private List<Column> columnas = new ArrayList<>();

    /**
     * Constructor que recibe como parametro:
     *
     * @param TableName El nombre de la tabla que se desea crear.
     * @param columna   Columna a agregar
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    protected AddColumn(String TableName, Column columna) throws ValorUndefined {
        super();
        if (Objects.isNull(columna)) {
            throw new ValorUndefined("La columna proporcionada es NULL");
        }
        this.tableName = TableName;
        this.columnas.add(columna);
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param TableName El nombre de la tabla que se desea crear.
     * @param columna   Columna a agregar
     * @param columnas  Lista de columnas que trae la sentencia previamente
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    protected AddColumn(String TableName, Column columna, List<Column> columnas) throws ValorUndefined {
        super();
        if (Objects.isNull(columna)) {
            throw new ValorUndefined("La columna proporcionada es NULL");
        }
        this.columnas = columnas;
        this.tableName = TableName;
        this.columnas.add(columna);
        this.setTableName(TableName);
    }

    /**
     * Agrega una columna a la sentencia SQL a ejecutar al momento de llamar al metodo creteTable()
     *
     * @param columna Columna a agregar
     * @return retorna un objeto del tipo AddColumn que brinda la capacidad de dar una mayor logica a la operación de createTable
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    public AddColumn addColumn(Column columna) throws ValorUndefined {
        return new AddColumn(this.tableName, columna, this.columnas);
    }

    /**
     * Ejecuta la sentencia SQL para crear la tabla en la BD's especificada
     *
     * @return True si la tabla a sido creada, false si la tabla ya existe en BD's o si sucede un error
     * al momento de ejecutar la sentencia SQL
     * @throws Exception Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    public Boolean createTable() throws Exception {
        return this.crateTableJSON(this.columnas);
    }
}

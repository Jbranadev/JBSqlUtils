package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Column;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Methods_Conexion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class AddColumn extends Methods_Conexion {

    private String tableName;

    private List<Column> columnas= new ArrayList<>();

    /**
     * Constructor que recibe como parametro:
     *
     * @param TableName El nombre de la tabla que se desea crear.
     * @param columna Columna a agregar
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    public AddColumn(String TableName, Column columna) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        if(Objects.isNull(columna)){
            throw new ValorUndefined("La columna proporcionada es NULL");
        }
        this.tableName=TableName;
        this.columnas.add(columna);

    }



    /**
     * Constructor que recibe como parametro:
     *
     * @param TableName El nombre de la tabla que se desea crear.
     * @param columna Columna a agregar
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    public AddColumn(String TableName, Column columna, List<Column> columnas) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        if(Objects.isNull(columna)){
            throw new ValorUndefined("La columna proporcionada es NULL");
        }
        this.columnas=columnas;
        this.tableName=TableName;
        this.columnas.add(columna);
        this.setTableName(TableName);
    }


    public AddColumn addColumn(Column columna) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        return new AddColumn(this.tableName, columna, this.columnas);
    }

    public Boolean createTable(){
        return this.crateTableJSON(this.columnas);
    }




}

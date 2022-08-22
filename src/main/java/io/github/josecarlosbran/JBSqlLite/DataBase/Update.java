package io.github.josecarlosbran.JBSqlLite.DataBase;

import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class Update{
    private String sql;
    public Update(String TableName) throws ValorUndefined {
        String respuesta = "";
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        this.sql= "UPDATE "+TableName+" ";
    }


    public Set set(String columna, String value) throws ValorUndefined {
        return new Set(columna, value, this.sql);
    }



}

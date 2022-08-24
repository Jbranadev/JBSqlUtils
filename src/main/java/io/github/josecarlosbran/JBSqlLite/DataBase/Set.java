package io.github.josecarlosbran.JBSqlLite.DataBase;


import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Search.Where;
import io.github.josecarlosbran.LogsJB.LogsJB;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class Set {

    private String sql;

    public Set(String columName, String value, String sql) throws ValorUndefined {
        if (stringIsNullOrEmpty(columName)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (stringIsNullOrEmpty(value)) {
            LogsJB.warning("El valor proporcionado para la columna esta vacío o es NULL");
        }
        this.sql= sql + "SET "+columName+"="+value;
    }


    public AndSet andSet(String columna, String value) throws ValorUndefined {
        return new AndSet(columna,value, this.sql);
    }


    public Where where(String columna, Operator operador, String value) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Where(columna, operador, value, this.sql);
    }





    public int execute() throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Execute(this.sql).execute();
    }




}
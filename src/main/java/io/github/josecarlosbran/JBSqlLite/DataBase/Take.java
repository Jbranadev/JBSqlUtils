package io.github.josecarlosbran.JBSqlLite.DataBase;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;

import java.util.List;
import java.util.Objects;


public class Take<T> extends Get{
    private String sql;
    private T modelo=null;

    protected Take(String sql, int limite, T modelo) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();

        if (Objects.isNull(limite)) {
            throw new ValorUndefined("El Limite proporcionado es 0 o inferior, por lo cual no se puede" +
                    "realizar la consulta a BD's");
        }
        this.modelo=modelo;
        this.sql=sql+ "LIMIT "+limite;
    }



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

    public <T extends Methods_Conexion> List<T> get() throws InstantiationException, IllegalAccessException {
        return (List<T>) super.getAll((T)this.modelo, this.sql);
    }


}

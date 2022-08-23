package io.github.josecarlosbran.JBSqlLite.DataBase;


import io.github.josecarlosbran.JBSqlLite.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;

import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class OrderBy<T> extends Get {
    private String sql;
    private T modelo;

    protected OrderBy(String sql, String columna, OrderType orden, T modelo) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(orden)) {
            throw new ValorUndefined("El tipo de ordenamiento proporcionado es NULL");
        }
        this.modelo = modelo;
        this.sql=sql+" ORDER BY "+columna+orden.getValor();
    }

    public Take take(int limite) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if(Objects.isNull(this.modelo)){
            return new Take(this.sql, limite);
        }else{
            return new Take(this.sql, limite, this.modelo);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public <T extends Methods_Conexion> void get(){
        super.get((T)this.modelo, this.sql);
    }

    public <T extends Methods_Conexion> T first(){
        return (T) super.first((T) this.modelo, this.sql);
    }

    public <T extends Methods_Conexion> T firstOrFail() throws ModelNotFound {
        return (T) super.firstOrFail((T)this.modelo, this.sql);
    }

    public <T extends Methods_Conexion> List<T> getAll() throws InstantiationException, IllegalAccessException {
        return (List<T>) super.getAll((T)this.modelo, this.sql);
    }

}

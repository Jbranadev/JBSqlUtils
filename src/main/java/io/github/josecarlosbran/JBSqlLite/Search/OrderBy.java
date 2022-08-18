package io.github.josecarlosbran.JBSqlLite.Search;


import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;

import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class OrderBy extends GET{
    private String sql;

    protected OrderBy(String sql, String columna, OrderType orden) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(orden)) {
            throw new ValorUndefined("El tipo de ordenamiento proporcionado es NULL");
        }
        this.sql=sql+" ORDER BY "+columna+orden.getValor();
    }

    public <T extends Methods_Conexion> void get(T modelo){
        super.get(modelo, this.sql);
    }

    public <T extends Methods_Conexion> T first(T modelo){
        return (T) super.first(modelo, this.sql);
    }

    public <T extends Methods_Conexion> T firstOrFail(T modelo) throws ModelNotFound {
        return (T) super.firstOrFail(modelo, this.sql);
    }

    public <T extends Methods_Conexion> List<T> getAll(T modelo) throws InstantiationException, IllegalAccessException {
        return super.getAll(modelo, this.sql);
    }

}

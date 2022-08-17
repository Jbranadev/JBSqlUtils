package io.github.josecarlosbran.JBSqlLite.Search;


import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;

import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class And extends GET{
    private String sql;

    protected And(String sql, String columna, Operator operador, String valor) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (stringIsNullOrEmpty(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        this.sql = sql+Operator.AND.getOperador()+Operator.OPEN_PARENTESIS.getOperador()+columna + operador.getOperador() + valor+Operator.CLOSE_PARENTESIS.getOperador();
    }

    public And(String sql, String expresion) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        super();
        if (stringIsNullOrEmpty(expresion)) {
            throw new ValorUndefined("La expresion proporcionada esta vacía o es NULL");
        }
        this.sql = sql+Operator.AND.getOperador()+Operator.OPEN_PARENTESIS.getOperador()+expresion+Operator.CLOSE_PARENTESIS.getOperador();
    }

    public And and(String columna, Operator operador, String valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new And(this.sql, columna, operador, valor);
    }

    public Or or(String columna, Operator operador, String valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Or(this.sql, columna, operador, valor);
    }

    public And and(String expresion) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new And(this.sql, expresion);
    }

    public Or or(String expresion) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Or(this.sql, expresion);
    }

    public OrderBy orderBy(String columna, OrderType orderType) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new OrderBy(this.sql, columna, orderType);
    }

    public <T extends Methods_Conexion> void get(){
        super.get(this.sql);
    }

    public <T extends Methods_Conexion> T first(){
        return (T) super.first(this.sql);
    }

    public <T extends Methods_Conexion> T firstOrFail() throws ModelNotFound {
        return (T) super.firstOrFail(this.sql);
    }

    public <T extends Methods_Conexion> List<T> getAll() throws InstantiationException, IllegalAccessException {
        return super.getAll(this.sql);
    }


}

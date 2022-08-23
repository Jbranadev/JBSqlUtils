package io.github.josecarlosbran.JBSqlLite.DataBase;


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

public class Where<T> extends Get {
    private String sql;
    private T modelo=null;

    public Where(String columna, Operator operador, String valor, T modelo) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
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
        this.modelo=modelo;
        this.sql = " WHERE "+Operator.OPEN_PARENTESIS.getOperador()+columna + operador.getOperador() + valor+Operator.CLOSE_PARENTESIS.getOperador();
    }

    public Where(String columna, Operator operador, String valor, String sql) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
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
        this.sql = sql+" WHERE "+Operator.OPEN_PARENTESIS.getOperador()+columna + operador.getOperador() + valor+Operator.CLOSE_PARENTESIS.getOperador();
    }





    public And and(String columna, Operator operador, String valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if(Objects.isNull(this.modelo)){
            return new And(this.sql, columna, operador, valor);
        }else{
            return new And(this.sql, columna, operador, valor, this.modelo);
        }
    }

    public Or or(String columna, Operator operador, String valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if(Objects.isNull(this.modelo)){
            return new Or(this.sql, columna, operador, valor);
        }else{
            return new Or(this.sql, columna, operador, valor, this.modelo);
        }
    }

    public And and(String expresion) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if(Objects.isNull(this.modelo)){
            return new And(this.sql, expresion);
        }else{
            return new And(this.sql, expresion, this.modelo);
        }
    }

    public Or or(String expresion) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        if(Objects.isNull(this.modelo)){
            return new Or(this.sql, expresion);
        }else{
            return new Or(this.sql, expresion, this.modelo);
        }

    }


    public OrderBy orderBy(String columna, OrderType orderType) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new OrderBy(this.sql, columna, orderType, this.modelo);
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



    


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int execute() throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Execute(this.sql).execute();
    }









}

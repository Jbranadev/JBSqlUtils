package io.github.josecarlosbran.JBSqlLite;


import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;

public class Column<T> {
    private T valor=null;

    private DataType dataTypeSQL=null;

    private Constraint[] restriccion =null;

    Column(DataType tipo_de_dato){
        this.setDataTypeSQL(tipo_de_dato);
    }

    Column(T Valor, DataType tipo_de_dato){
        this.setValor(Valor);
        this.setDataTypeSQL(tipo_de_dato);
    }


    Column(T Valor, DataType tipo_de_dato, Constraint[] restriccion){
        this.setValor(Valor);
        this.setDataTypeSQL(tipo_de_dato);
        this.setRestriccion(restriccion);
    }

    Column(DataType tipo_de_dato, Constraint[] restriccion){
        this.setDataTypeSQL(tipo_de_dato);
        this.setRestriccion(restriccion);
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public DataType getDataTypeSQL() {
        return dataTypeSQL;
    }

    public void setDataTypeSQL(DataType dataTypeSQL) {
        this.dataTypeSQL = dataTypeSQL;
    }

    public Constraint[] getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(Constraint[] restriccion) {
        this.restriccion = restriccion;
    }
}

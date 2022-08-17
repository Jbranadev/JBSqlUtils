package io.github.josecarlosbran.JBSqlLite.Enumerations;


public enum OrderType {
    ASC(" ASC "),

    DESC(" DESC ");

    private String valor;

    private OrderType(String s){
        this.valor=s;
    }

    public String getValor(){
        return this.valor;
    }

}

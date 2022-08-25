package io.github.josecarlosbran.JBSqlLite.Enumerations;

/**
 * @author Jose Bran
 * Numeración que proporciona los tipos de ordenamiento SQL disponibles.
 */
public enum OrderType {

    /**
     * Indica que deseamos se ordene de manera Ascendente los registros
     */
    ASC(" ASC "),

    /**
     * Indica que deseamos se ordene de manera Descendente los registros
     */
    DESC(" DESC ");

    private String valor;

    private OrderType(String s){
        this.valor=s;
    }

    /**
     * Obtiene el valor de ordenamiento correspondiente a la numeración.
     * @return Retorna el valor de ordenamiento correspondiente a la numeración.
     */
    public String getValor(){
        return this.valor;
    }

}

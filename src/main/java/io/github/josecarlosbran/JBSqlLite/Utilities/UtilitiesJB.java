package io.github.josecarlosbran.JBSqlLite.Utilities;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UtilitiesJB {
    /*
    try{

        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el metodo execute, el cual llama la creación del hilo: "+ e.toString());
            LogsJB.fatal("Tipo de Excepción : "+e.getClass());
            LogsJB.fatal("Causa de la Excepción : "+e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
            LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
        }
    */

    /****
     * Verifica si una cadena esta vacía o es nula
     * @param cadena Cadena a Validar
     * @return Retorna True si la cadena envíada esta vacía o nula, de lo contrario retorna false
     */
    public static boolean stringIsNullOrEmpty(String cadena){
        //System.out.println(cadena);
        if( Objects.isNull(cadena)||cadena.isEmpty()){
            return true;
        }
        return false;
    }
    /***
     * Obtener el valor booleano de un numero
     * @param numero numero que se evaluara
     * @return si el numero es mayor o igual a uno, retorna true, de lo contrario, retorna false.
     */
    public static boolean getBooleanfromInt(int numero){
        if (numero >= 1) {
            return true;
        }
        return false;
    }

    /**
     * @author Jose Bran
     * Medoto que convierte el valor Booleano en un entero, si el valor es NULL o False, retorna 0.
     * Si el valor es True retorna 1.
     * @param temp Valor Booleano a ser evaluado.
     * @return Si el valor es NULL o False, retorna 0, si el valor es True retorna 1.
     */
    public static int getIntFromBoolean(Boolean temp){
        if (temp == null||temp==false) {
            return 0;
        }
        return 1;
    }



    public static String where(String expresion) throws ValorUndefined {
        String respuesta = "";
        if (stringIsNullOrEmpty(expresion)) {
            throw new ValorUndefined("La expresion proporcionada esta vacía o es NULL");
        }
        respuesta = "WHERE ";
        respuesta = respuesta + expresion;

        return respuesta;
    }

    public static String expresion(String columna, Operator operador, String valor) throws ValorUndefined {
        String respuesta = "";
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (stringIsNullOrEmpty(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        respuesta = columna + operador.getOperador() + valor;
        return respuesta;
    }


    public static String between(String valorInferior, String valorSuperior) throws ValorUndefined {
        String respuesta = "";

        if (stringIsNullOrEmpty(valorInferior)) {
            throw new ValorUndefined("El valor inferior proporcionado esta vacío o es NULL");
        }
        if (stringIsNullOrEmpty(valorSuperior)) {
            throw new ValorUndefined("El valor superior proporcionado esta vacío o es NULL");
        }
        respuesta = "Between " + valorInferior + Operator.AND.getOperador() + valorSuperior;

        return respuesta;
    }

    public static String openParentesis(){
        return " (";
    }

    public static String cerrarParentesis(){
        return " )";
    }




    //Obtener un objeto generico de una lista
    public static <T> T getObject(int id, List<T> lista){
        return lista.get(id);
    }

    //agregar un objeto generico de una lista
    public static <T> void addDato(List<T> lista, T dato){
        lista.add(dato);
    }

    public static <T> List<T> getall(T a, T b, T c){
        List<T> lista=new ArrayList<>();
        lista.add(a);
        lista.add(b);
        lista.add(c);
        return lista;
    }

    public void imprimirnombreclase(){
        System.out.println("Nombre de la clase: "+this.getClass().getName());
    }
}

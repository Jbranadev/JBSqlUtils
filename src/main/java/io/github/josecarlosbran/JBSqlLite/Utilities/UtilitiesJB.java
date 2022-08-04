package io.github.josecarlosbran.JBSqlLite.Utilities;

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

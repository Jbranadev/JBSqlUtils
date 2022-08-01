package io.github.josecarlosbran.JBSqlLite.Utilities;

import java.util.Objects;

public class UtilitiesJB {
    /****
     * Verifica si una cadena esta vacía o es nula
     * @param cadena Cadena a Validar
     * @return Retorna True si la cadena envíada esta vacía o nula, de lo contrario retorna false
     */
    public static boolean stringIsNullOrEmpty(String cadena){
        if(cadena.isEmpty()|| Objects.isNull(cadena)){
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
}

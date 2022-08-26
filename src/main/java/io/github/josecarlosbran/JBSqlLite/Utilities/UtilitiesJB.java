/***
 * Copyright (C) 2022 El proyecto de código abierto JBSqlUtils de José Bran
 *
 * Con licencia de Apache License, Versión 2.0 (la "Licencia");
 * no puede usar este archivo excepto de conformidad con la Licencia.
 * Puede obtener una copia de la Licencia en
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * A menos que lo exija la ley aplicable o se acuerde por escrito, el software
 * distribuido bajo la Licencia se distribuye "TAL CUAL",
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
 * Consulte la Licencia para conocer el idioma específico que rige los permisos y
 * limitaciones bajo la Licencia.
 */
package io.github.josecarlosbran.JBSqlLite.Utilities;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;

import java.util.Objects;
/**
 * @author Jose Bran
 * Clase que brinda acceso a funcionalidades comunes, sin necesidad de crear una instancia de la misma
 */
public class UtilitiesJB {


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


    /**
     * Retorna una clausala where la cual puede ser utilizada para filtrar resultados de BD's
     * @param expresion Expresión que contendra el Where
     * @return " WHERE "+ expresion;
     * @throws ValorUndefined Lanza esta excepción si alguno de los valores proporcionados esta vacío o es Null
     */
    public static String where(String expresion) throws ValorUndefined {
        String respuesta = "";
        if (stringIsNullOrEmpty(expresion)) {
            throw new ValorUndefined("La expresion proporcionada esta vacía o es NULL");
        }
        respuesta = " WHERE ";
        respuesta = respuesta + expresion;

        return respuesta;
    }

    /**
     * Retorna una expresión SQL util para usar en una clausula Where
     * @param columna Columna, que desea evaluar
     * @param operador Operador bajo el cual se evaluara la columna
     * @param valor Valor contra el que se evaluara la columna
     * @return columna + operador.getOperador() + valor;
     * @throws ValorUndefined Lanza esta excepción si alguno de los valores proporcionados esta vacío o es Null
     */
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


    /**
     * Función que permite realizar una expreción Between
     * @param valorInferior Valor inferior
     * @param valorSuperior Valor superior
     * @return "Between " + valorInferior + Operator.AND.getOperador() + valorSuperior;
     * @throws ValorUndefined Lanza esta excepción si alguno de los valores proporcionados esta vacío o es Null
     */
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

    /**
     * Funcion que inserta una apertura de parentesis, esta puede ser utilizada cuando se desea crear una expresión
     * y envíarla como parametro a una consulta
     * @return (
     */
    public static String openParentesis(){
        return " (";
    }

    /**
     * Funcion que inserta un cierre de parentesis, esta puede ser utilizada cuando se desea crear una expresión
     * y envíarla como parametro a una consulta
     * @return )
     */
    public static String cerrarParentesis(){
        return " )";
    }


}

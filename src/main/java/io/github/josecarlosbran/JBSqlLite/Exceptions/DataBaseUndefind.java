package io.github.josecarlosbran.JBSqlLite.Exceptions;

/**
 * @author Jose Bran
 * Excepción que indica que no a sido especificado el tipo de Base de Datos al cual se conectara el modelo
 */
public class DataBaseUndefind extends Exception{
    public DataBaseUndefind(String mensaje) {
        super(mensaje);
    }
}

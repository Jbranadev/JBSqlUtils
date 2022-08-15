package io.github.josecarlosbran.JBSqlLite.Exceptions;

/**
 * @author Jose Bran
 * Exepción que indica que no se a realizado una conexión del modelo, por lo cual no se puede obtener la misma
 */
public class ConexionUndefind extends Exception{
    public ConexionUndefind(String mensaje) {
        super(mensaje);
    }
}

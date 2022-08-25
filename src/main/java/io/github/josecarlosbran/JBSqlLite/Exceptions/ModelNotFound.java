package io.github.josecarlosbran.JBSqlLite.Exceptions;

/**
 * @author Jose Bran
 * Excepción que indica que no a sido posible encontrar el Modelo en BD's con las caracteristicas
 * proporcionadas a travez de la consulta realizada.
 */
public class ModelNotFound extends Exception{
    public ModelNotFound(String mensaje) {
        super(mensaje);
    }

}

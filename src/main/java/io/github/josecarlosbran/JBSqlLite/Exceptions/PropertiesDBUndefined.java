package io.github.josecarlosbran.JBSqlLite.Exceptions;

/**
 * @author Jose Bran
 * Exepción que indica que no han sido especificadas las propiedades de conexion necesarias
 * para el tipo de BD's a la que se conectara el modelo
 */
public class PropertiesDBUndefined extends Exception{
    public PropertiesDBUndefined(String mensaje) {
        super(mensaje);
    }
}

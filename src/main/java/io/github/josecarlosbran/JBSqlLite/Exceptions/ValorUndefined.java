package io.github.josecarlosbran.JBSqlLite.Exceptions;

/**
 * @author Jose Bran
 * Excepción que indica que un valor proporcionado a alguna de las expresiones SQL es null o vacío
 * por lo cual el metodo no puede retornar un resultado y en su lugar lanza la excepción
 */
public class ValorUndefined extends Exception{
    public ValorUndefined(String mensaje) {
        super(mensaje);
    }
}

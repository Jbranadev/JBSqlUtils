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
package io.github.josecarlosbran.JBSqlUtils.Exceptions;

/**
 * Excepción que indica que no a sido especificado el tipo de Base de Datos al cual se conectara el modelo
 * @author Jose Bran
 */
public class DataBaseUndefind extends Exception {
    /**
     * Crea una excepción del tipo DataBaseUndefined
     * @param mensaje Mensaje de la excepción que se mostrara al dispararse una excepción de este tipo
     */
    public DataBaseUndefind(String mensaje) {
        super(mensaje);
    }
}

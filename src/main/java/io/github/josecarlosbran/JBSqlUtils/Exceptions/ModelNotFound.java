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
 * Excepción que indica que no a sido posible encontrar el Modelo en BD's con las caracteristicas
 * proporcionadas a travez de la consulta realizada.
 *
 * @author Jose Bran
 */
public class ModelNotFound extends Exception {
    /**
     * Constructor por default para excepción del tipo Model Not Found
     *
     * @param mensaje Mensaje que deseamos tenga la excepción al momento de dispararse
     */
    public ModelNotFound(String mensaje) {
        super(mensaje);
    }

}

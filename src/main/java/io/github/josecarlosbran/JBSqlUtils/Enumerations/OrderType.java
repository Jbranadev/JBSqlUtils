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
package io.github.josecarlosbran.JBSqlUtils.Enumerations;

/**
 * @author Jose Bran
 * Numeración que proporciona los tipos de ordenamiento SQL disponibles.
 */
public enum OrderType {

    /**
     * Indica que deseamos se ordene de manera Ascendente los registros
     */
    ASC(" ASC "),

    /**
     * Indica que deseamos se ordene de manera Descendente los registros
     */
    DESC(" DESC ");

    private String valor;

    private OrderType(String s) {
        this.valor = s;
    }

    /**
     * Obtiene el valor de ordenamiento correspondiente a la numeración.
     *
     * @return Retorna el valor de ordenamiento correspondiente a la numeración.
     */
    public String getValor() {
        return this.valor;
    }

}

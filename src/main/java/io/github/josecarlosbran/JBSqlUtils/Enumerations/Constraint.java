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

import java.util.Arrays;
import java.util.List;

/**
 * Enumeración que permite indicar las restricciones que puede tener una columna al momento de su creación.
 * @author Jose Bran
 */
public enum Constraint {

    /**
     * Indica que la columna no acepta valores Nullos.
     */
    NOT_NULL("NOT NULL"),

    /**
     * El valor de esta columna tiene que ser unico
     */
    UNIQUE("UNIQUE"),

    /**
     * Restriccion que permite indicarle que tipo de valores si serán aceptados por la columna
     * Lo puede realizar a travez del metodo setRestriccion(String restriccion); de esta numeración.
     * considerar que la misma restricción se aplicara para el resto de columnas que tengan un valor Check.

    CHECK("CHECK"),*/

    /**
     * Indica que la columna funciona como clave primaria del modelo.
     */
    PRIMARY_KEY("PRIMARY KEY"),


    /**
     * Indica que la columna funciona como clave foranea del modelo.

    FOREIGN_KEY("FOREIGN KEY"),*/

    /**
     * Indica que el campo tendra como valor por default el TimeStamp del momento en que se almacene el modelo.
     */
    CURRENT_TIMESTAMP("CURRENT_TIMESTAMP"),


    /**
     * Indica que la columna tendra un valor por default
     */
    DEFAULT("DEFAULT"),

    /**
     * Indica que la columna autoincrementara su valor cada vez que se almacene un registro en la tabla correspondiente al modelo.
     */
    AUTO_INCREMENT("AUTO_INCREMENT");

    private String restriccion;

    private Constraint(String Restriccion) {
        this.setRestriccion(Restriccion);
    }


    /**
     * Obtiene la restricción correspondiente a la numeración.
     *
     * @return Retorna la numeración correspondiente como un String.
     */
    public String getRestriccion() {
        return restriccion;
    }


    private void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    /**
     * Retorna la numeración correspondiente al nombre proporcionado
     *
     * @param name Nombre de la Numeración que se desea obtener
     * @return Numeración correspondiente al nombre proporcionado
     */
    public Constraint getNumeracionforName(String name) {
        Class<Constraint> esta = Constraint.class;
        Constraint[] temp = esta.getEnumConstants();
        List<Constraint> numeraciones = Arrays.asList(temp);
        for (Constraint numeracion : numeraciones) {
            if (numeracion.name().equalsIgnoreCase(name)) {
                /*LogsJB.info("Nombre: "+numeracion.name()+" Posicion Ordinal: "+numeracion.ordinal()
                        +" operador: "+numeracion.getOperador());*/
                return numeracion;
            }
        }
        return null;
    }

}

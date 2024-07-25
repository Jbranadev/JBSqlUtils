package io.github.josecarlosbran.JBSqlUtils.Enumerations;

import com.josebran.LogsJB.LogsJB;

import java.util.Arrays;
import java.util.List;

public enum Action {
    RESTRICT(" RESTRICT "),
    CASCADE(" CASCADE "),
    SET_NULL(" SET NULL "),
    NO_ACTION(" NO ACTION "),
    SET_DEFAULT(" SET DEFAULT ");
    private String operacion;

    private Action(String s) {
        this.operacion = s;
    }

    /**
     * Obtiene la Operación
     *
     * @return Representación String de la Operación en cuestión
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * Retorna la numeración correspondiente al nombre proporcionado
     *
     * @param name Nombre de la Numeración que se desea obtener
     * @return Numeración correspondiente al nombre proporcionado
     */
    public Action getNumeracionforName(String name) {
        Class<Action> esta = Action.class;
        Action[] temp = esta.getEnumConstants();
        List<Action> numeraciones = Arrays.asList(temp);
        for (Action numeracion : numeraciones) {
            if (numeracion.name().equalsIgnoreCase(name)) {
                LogsJB.trace("Nombre: " + numeracion.name() + " Posicion Ordinal: " + numeracion.ordinal()
                        + " Operacion: " + numeracion.getOperacion());
                return numeracion;
            }
        }
        return null;
    }
}

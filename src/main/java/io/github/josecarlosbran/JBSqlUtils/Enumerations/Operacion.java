package io.github.josecarlosbran.JBSqlUtils.Enumerations;

import com.josebran.LogsJB.LogsJB;

public enum Operacion {
    INSERT(" ON INSERT "),
    UPDATE(" ON UPDATE "),
    DELETE(" ON DELETE ");
    private final String operador;

    Operacion(String s) {
        this.operador = s;
    }

    /**
     * Obtiene el operador
     *
     * @return Representación String del Operador en cuestión
     */
    public String getOperador() {
        return operador;
    }

    /**
     * Retorna la numeración correspondiente al nombre proporcionado
     *
     * @param name Nombre de la Numeración que se desea obtener
     * @return Numeración correspondiente al nombre proporcionado
     */
    public Operacion getNumeracionforName(String name) {
        Class<Operacion> esta = Operacion.class;
        Operacion[] temp = esta.getEnumConstants();
        Operacion[] numeraciones = temp;
        for (Operacion numeracion : numeraciones) {
            if (numeracion.name().equalsIgnoreCase(name)) {
                LogsJB.trace("Nombre: " + numeracion.name() + " Posicion Ordinal: " + numeracion.ordinal()
                        + " Operador: " + numeracion.getOperador());
                return numeracion;
            }
        }
        return null;
    }
}

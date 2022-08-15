package io.github.josecarlosbran.JBSqlLite.Enumerations;

/**
 * @author Jose Bran
 * Enumeración que permite indicar las restricciones que puede tener una columna al momento de su creación.
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
     */
    CHECK("CHECK"),

    /**
     * Indica que la columna funciona como clave primaria del modelo.
     */
    PRIMARY_KEY("PRIMARY KEY"),


    /**
     * Indica que la columna funciona como clave foranea del modelo.
     */
    FOREIGN_KEY("FOREIGN KEY"),

    /**
     * Indica que el campo tendra como valor por default el TimeStamp del momento en que se almacene el modelo.
     */
    CURRENT_TIMESTAMP("CURRENT_TIMESTAMP"),


    /**
     * Indica que la columna autoincrementara su valor cada vez que se almacene un registro en la tabla correspondiente al modelo.
     */
    AUTO_INCREMENT("AUTO_INCREMENT");

    private String restriccion;

    private Constraint(String Restriccion){
        this.setRestriccion(Restriccion);
    }


    public String getRestriccion() {
        return restriccion;
    }


    private void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

}

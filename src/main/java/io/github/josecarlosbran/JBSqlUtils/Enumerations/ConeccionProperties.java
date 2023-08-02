package io.github.josecarlosbran.JBSqlUtils.Enumerations;

/**
 * @author Jose Bran
 * Enumeración que permite indicar la propiedad de conección que se esta seteando entre las variables de entorno de la JVM.
 */
public enum ConeccionProperties {


    /**
     * Tipo de BD's a la cual se conectara.
     */
    DBTYPE("DataBase"),

    /**
     * Host en el cual se encuentra la BD's a la cual se conectara.
     */
    DBHOST("DataBaseHost"),

    /**
     * Puerto en el cual esta escuchando la BD's a la cual nos vamos a conectar.
     */
    DBPORT("DataBasePort"),

    /**
     * Usuario con el que se establecera conexión a la BD's
     */
    DBUSER("DataBaseUser"),
    /**
     * Contraseña del Usuario con el que se establecera conexión a la BD's
     */
    DBPASSWORD("DataBasePassword"),

    /**
     * Propiedades extra para la url de conexión a BD's por ejemplo
     * ?autoReconnect=true&useSSL=false
     */
    DBPROPERTIESURL("DBpropertisUrl"),

    /**
     * Nombre de la BD's a la cual nos conectaremos.
     */
    DBNAME("DataBaseBD");


    /**
     * Indica la propieda que se estara setiando
     */
    private String propiertie;


    /**
     * Constructor de la numeración
     *
     * @param propiertie La propiedad correspondiente a la numeración seleccionada
     */
    private ConeccionProperties(String propiertie) {
        this.setPropiertie(propiertie);
    }


    /**
     * Obtiene la propiedad que posee la numeración
     *
     * @return Propiedad que posee la numeración
     */
    public String getPropiertie() {
        return propiertie;
    }

    /**
     * Setea la propiedad de la numeración
     *
     * @param propiertie Propiedad que se setea a la numeración
     */
    private void setPropiertie(String propiertie) {
        this.propiertie = propiertie;
    }


}

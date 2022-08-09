package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;

public class Test extends JBSqlUtils{
    public Test() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    private int id;
    private String name;
    private String apellido;

    private Boolean isMayor;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Boolean getMayor() {
        return isMayor;
    }

    public void setMayor(Boolean mayor) {
        isMayor = mayor;
    }
}

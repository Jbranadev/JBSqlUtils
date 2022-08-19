package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;

public class Empleado extends JBSqlUtils{
    Empleado() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    private Column<Integer> Ficha=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.NOT_NULL,Constraint.PRIMARY_KEY});

    private Column<String> Nombre=new Column<>(DataType.VARCHAR, new Constraint[]{Constraint.NOT_NULL});

    private Column<String> Puesto=new Column<>(DataType.VARCHAR, new Constraint[]{Constraint.NOT_NULL});

    public Column<Integer> getFicha() {
        return Ficha;
    }

    public void setFicha(Column<Integer> ficha) {
        Ficha = ficha;
    }

    public Column<String> getNombre() {
        return Nombre;
    }

    public void setNombre(Column<String> nombre) {
        Nombre = nombre;
    }

    public Column<String> getPuesto() {
        return Puesto;
    }

    public void setPuesto(Column<String> puesto) {
        Puesto = puesto;
    }
}

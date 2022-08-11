package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;

public class Test extends JBSqlUtils{
    public Test() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    private Column<Integer> id=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.AUTO_INCREMENT,Constraint.PRIMARY_KEY});

    private Column<String> name=new Column<>(DataType.VARCHAR);
    private Column<String> apellido=new Column<>(DataType.VARCHAR);;
    private Column<Boolean> isMayor=new Column<>(Boolean.FALSE, DataType.BIT);;

    public Column<Integer> getId() {
        return id;
    }


    public void setId(Column<Integer> id) {
        this.id = id;
    }

    public Column<String> getName() {
        return name;
    }

    public void setName(Column<String> name) {
        this.name = name;
    }

    public Column<String> getApellido() {
        return apellido;
    }

    public void setApellido(Column<String> apellido) {
        this.apellido = apellido;
    }

    public Column<Boolean> getIsMayor() {
        return isMayor;
    }

    public void setIsMayor(Column<Boolean> isMayor) {
        this.isMayor = isMayor;
    }
}

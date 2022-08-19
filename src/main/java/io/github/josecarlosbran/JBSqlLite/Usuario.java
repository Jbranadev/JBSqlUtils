package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;

public class Usuario extends JBSqlUtils{

    Usuario() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }
    private Column<Integer> Id_Usuario=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.AUTO_INCREMENT,Constraint.PRIMARY_KEY});

    private Column<String> Usuario=new Column<>(DataType.VARCHAR, new Constraint[]{Constraint.NOT_NULL});

    private Column<String> Contraseña=new Column<>(DataType.VARCHAR, new Constraint[]{Constraint.NOT_NULL});


    public Column<Integer> getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(Column<Integer> id_Usuario) {
        Id_Usuario = id_Usuario;
    }

    public Column<String> getUsuario() {
        return Usuario;
    }

    public void setUsuario(Column<String> usuario) {
        Usuario = usuario;
    }

    public Column<String> getContraseña() {
        return Contraseña;
    }

    public void setContraseña(Column<String> contraseña) {
        Contraseña = contraseña;
    }
}

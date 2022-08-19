package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.JBSqlUtils;

public class Material extends JBSqlUtils {

    Material() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    private Column<Integer> Id_Material=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.AUTO_INCREMENT,Constraint.PRIMARY_KEY});

    private Column<String> Descripcion=new Column<>(DataType.VARCHAR, new Constraint[]{Constraint.NOT_NULL});

    private Column<Integer> Cantidad_Existente=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.NOT_NULL});


    public Column<Integer> getId_Material() {
        return Id_Material;
    }

    public void setId_Material(Column<Integer> id_Material) {
        Id_Material = id_Material;
    }

    public Column<String> getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(Column<String> descripcion) {
        Descripcion = descripcion;
    }

    public Column<Integer> getCantidad_Existente() {
        return Cantidad_Existente;
    }

    public void setCantidad_Existente(Column<Integer> cantidad_Existente) {
        Cantidad_Existente = cantidad_Existente;
    }
}

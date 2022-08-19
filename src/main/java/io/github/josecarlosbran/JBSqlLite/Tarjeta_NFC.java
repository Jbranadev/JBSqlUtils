package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;


public class Tarjeta_NFC extends JBSqlUtils{
    public Tarjeta_NFC() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    private Column<Integer> Id_Tarjeta_NFC=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.AUTO_INCREMENT,Constraint.PRIMARY_KEY});

    private Column<Integer> Id_Material=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.NOT_NULL});


    public Column<Integer> getId_Tarjeta_NFC() {
        return Id_Tarjeta_NFC;
    }

    public void setId_Tarjeta_NFC(Column<Integer> id_Tarjeta_NFC) {
        Id_Tarjeta_NFC = id_Tarjeta_NFC;
    }

    public Column<Integer> getId_Material() {
        return Id_Material;
    }

    public void setId_Material(Column<Integer> id_Material) {
        Id_Material = id_Material;
    }
}

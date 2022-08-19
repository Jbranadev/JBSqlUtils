package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;

import java.sql.Timestamp;

public class Registro extends JBSqlUtils{
    Registro() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    private Column<Integer> Id_Registro=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.AUTO_INCREMENT,Constraint.PRIMARY_KEY});

    private Column<Integer> Id_Usuario=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.NOT_NULL});

    private Column<Integer> Id_Material=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.NOT_NULL});

    private Column<Integer> Ficha=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.NOT_NULL});

    private Column<Integer> Cantidad_Entregada=new Column<>(DataType.INTEGER, new Constraint[]{Constraint.NOT_NULL});

    private Column<Timestamp> Fecha_Entrega=new Column<>(DataType.TIMESTAMP, new Constraint[]{Constraint.NOT_NULL});


    public Column<Integer> getId_Registro() {
        return Id_Registro;
    }

    public void setId_Registro(Column<Integer> id_Registro) {
        Id_Registro = id_Registro;
    }

    public Column<Integer> getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(Column<Integer> id_Usuario) {
        Id_Usuario = id_Usuario;
    }

    public Column<Integer> getId_Material() {
        return Id_Material;
    }

    public void setId_Material(Column<Integer> id_Material) {
        Id_Material = id_Material;
    }

    public Column<Integer> getFicha() {
        return Ficha;
    }

    public void setFicha(Column<Integer> ficha) {
        Ficha = ficha;
    }

    public Column<Integer> getCantidad_Entregada() {
        return Cantidad_Entregada;
    }

    public void setCantidad_Entregada(Column<Integer> cantidad_Entregada) {
        Cantidad_Entregada = cantidad_Entregada;
    }

    public Column<Timestamp> getFecha_Entrega() {
        return Fecha_Entrega;
    }

    public void setFecha_Entrega(Column<Timestamp> fecha_Entrega) {
        Fecha_Entrega = fecha_Entrega;
    }
}

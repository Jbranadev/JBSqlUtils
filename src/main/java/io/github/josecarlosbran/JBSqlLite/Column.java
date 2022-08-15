package io.github.josecarlosbran.JBSqlLite;


import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;

/**
 * @author  José Bran
 * Clase que permite crear los atributos del modelo, especificando el tipo de dato en java, el tipo de dato en SQL
 * y las restricciones que debería tener cada columna.
 * @param <T> Tipo de dato correspondiente en Java.
 */
public class Column<T> {

    private T valor=null;

    private DataType dataTypeSQL=null;

    private Constraint[] restriccion =null;

    /**
     * Inicializa la columna indicando el tipo de dato SQL que tendra la columna
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     */
    Column(DataType tipo_de_dato){
        this.setDataTypeSQL(tipo_de_dato);
    }

    /**
     * Inicializa la columna indicando el valor y el tipo de dato SQL que tendra la columna
     * @param Valor Valor que tendra la columna.
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     */
    Column(T Valor, DataType tipo_de_dato){
        this.setValor(Valor);
        this.setDataTypeSQL(tipo_de_dato);
    }


    /**
     * Inicializa la columna indicando el valor, el tipo de dato SQL que tendra la columna y las restricciones
     * SQL que tendra.
     * @param Valor Valor que tendra la columna.
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     * @param restriccion Indica las restricciones SQL que tendra este campo.
     */
    Column(T Valor, DataType tipo_de_dato, Constraint[] restriccion){
        this.setValor(Valor);
        this.setDataTypeSQL(tipo_de_dato);
        this.setRestriccion(restriccion);
    }

    /**
     * Inicializa la columna indicando el tipo de dato SQL  que tendra la columna y las restricciones SQL que tendra
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     * @param restriccion Indica las restricciones SQL que tendra este campo.
     */
    Column(DataType tipo_de_dato, Constraint[] restriccion){
        this.setDataTypeSQL(tipo_de_dato);
        this.setRestriccion(restriccion);
    }

    /**
     * Obtiene el valor de la columna
     * @return Retorna el valor almacenado en la columna.
     */
    public T getValor() {
        return valor;
    }

    /**
     * Setea el valor de la columna.
     * @param valor Valor que tendra la columna en este modelo
     */
    public void setValor(T valor) {
        this.valor = valor;
    }

    /**
     * Obtiene le tipo de dato SQL que esta almacenando esta columna.
     * @return Retorna un objeto DataType que representa el tipo de dato SQL que tiene la columna en BD's
     */
    public DataType getDataTypeSQL() {
        return dataTypeSQL;
    }

    /**
     * Setea el tipo de dato SQL que esta almacenando esta columna.
     * @param dataTypeSQL DataType que representa el tipo de dato SQL que tiene la columna en BD's
     */
    public void setDataTypeSQL(DataType dataTypeSQL) {
        this.dataTypeSQL = dataTypeSQL;
    }

    /**
     * Obtiene el array de las restricciones SQL que puede tener la columna
     * @return Array de restricciones SQL que puede tener la columna
     */
    public Constraint[] getRestriccion() {
        return restriccion;
    }

    /**
     * Setea las restricciones SQL que puede tener la columna
     * @param restriccion Array de restricciones SQL que puede tener la columna.
     */
    public void setRestriccion(Constraint[] restriccion) {
        this.restriccion = restriccion;
    }
}

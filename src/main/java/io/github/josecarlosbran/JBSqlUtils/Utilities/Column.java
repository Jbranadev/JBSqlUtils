/***
 * Copyright (C) 2022 El proyecto de código abierto JBSqlUtils de José Bran
 *
 * Con licencia de Apache License, Versión 2.0 (la "Licencia");
 * no puede usar este archivo excepto de conformidad con la Licencia.
 * Puede obtener una copia de la Licencia en
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * A menos que lo exija la ley aplicable o se acuerde por escrito, el software
 * distribuido bajo la Licencia se distribuye "TAL CUAL",
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
 * Consulte la Licencia para conocer el idioma específico que rige los permisos y
 * limitaciones bajo la Licencia.
 */
package io.github.josecarlosbran.JBSqlUtils.Utilities;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import lombok.ToString;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que permite crear los atributos del modelo, especificando el tipo de dato en java, el tipo de dato en SQL
 * y las restricciones que debería tener cada columna.
 *
 * @param <T> Tipo de dato correspondiente en Java.
 * @author José Bran
 */
@ToString
public class Column<T> {
    private String name = null;
    private T valor = null;
    private String default_value = null;
    @ToString.Exclude
    private DataType dataTypeSQL = null;
    @ToString.Exclude
    private Constraint[] restriccion = null;
    private Boolean columnExist = false;
    private String size = "";

    /**
     * Inicializa la columna indicando el tipo de dato SQL que tendra la columna
     *
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     */
    public Column(DataType tipo_de_dato) {
        this.setDataTypeSQL(tipo_de_dato);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el valor y el tipo de dato SQL que tendra la columna
     *
     * @param Valor        Valor que tendra la columna.
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     */
    public Column(T Valor, DataType tipo_de_dato) {
        this.setValor(Valor);
        this.setDataTypeSQL(tipo_de_dato);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el valor, el tipo de dato SQL que tendra la columna y las restricciones
     * SQL que tendra.
     *
     * @param Valor        Valor que tendra la columna.
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     * @param restriccion  Indica las restricciones SQL que tendra este campo.
     */
    public Column(T Valor, DataType tipo_de_dato, Constraint... restriccion) {
        this.setValor(Valor);
        this.setDataTypeSQL(tipo_de_dato);
        Constraint[] restricciones = new Constraint[restriccion.length];
        this.setRestriccion(restriccion);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el tipo de dato SQL  que tendra la columna y las restricciones SQL que tendra
     *
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     * @param restriccion  Indica las restricciones SQL que tendra este campo.
     */
    public Column(DataType tipo_de_dato, Constraint... restriccion) {
        this.setDataTypeSQL(tipo_de_dato);
        this.setRestriccion(restriccion);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el tipo de dato SQL  que tendra la columna y las restricciones SQL que tendra
     *
     * @param name          Nombre de la columna
     * @param tipo_de_dato  DataType que indica el tipo de dato SQL que almacenara la columna.
     * @param restriccion   Indica las restricciones SQL que tendra este campo.
     * @param default_value Indica el valor por default que tendra la columna en BD's
     */
    public Column(String name, DataType tipo_de_dato, String default_value, Constraint... restriccion) {
        this.setName(name);
        this.setDataTypeSQL(tipo_de_dato);
        this.setRestriccion(restriccion);
        this.setDefault_value(default_value);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el tipo de dato SQL  que tendra la columna y las restricciones SQL que tendra
     *
     * @param tipo_de_dato  DataType que indica el tipo de dato SQL que almacenara la columna.
     * @param restriccion   Indica las restricciones SQL que tendra este campo.
     * @param default_value Indica el valor por default que tendra la columna en BD's
     */
    public Column(DataType tipo_de_dato, String default_value, Constraint... restriccion) {
        this.setDataTypeSQL(tipo_de_dato);
        this.setRestriccion(restriccion);
        this.setDefault_value(default_value);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el tipo de dato SQL que tendra la columna
     *
     * @param name         Nombre de la columna
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     */
    public Column(String name, DataType tipo_de_dato) {
        this.setName(name);
        this.setDataTypeSQL(tipo_de_dato);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el valor y el tipo de dato SQL que tendra la columna
     *
     * @param name         Nombre de la columna
     * @param Valor        Valor que tendra la columna.
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     */
    public Column(String name, T Valor, DataType tipo_de_dato) {
        this.setName(name);
        this.setValor(Valor);
        this.setDataTypeSQL(tipo_de_dato);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el valor, el tipo de dato SQL que tendra la columna y las restricciones
     * SQL que tendra.
     *
     * @param name         Nombre de la columna
     * @param Valor        Valor que tendra la columna.
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     * @param restriccion  Indica las restricciones SQL que tendra este campo.
     */
    public Column(String name, T Valor, DataType tipo_de_dato, Constraint... restriccion) {
        this.setName(name);
        this.setValor(Valor);
        this.setDataTypeSQL(tipo_de_dato);
        Constraint[] restricciones = new Constraint[restriccion.length];
        this.setRestriccion(restriccion);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el tipo de dato SQL  que tendra la columna y las restricciones SQL que tendra
     *
     * @param name         Nombre de la columna
     * @param tipo_de_dato DataType que indica el tipo de dato SQL que almacenara la columna.
     * @param restriccion  Indica las restricciones SQL que tendra este campo.
     */
    public Column(String name, DataType tipo_de_dato, Constraint... restriccion) {
        this.setName(name);
        this.setDataTypeSQL(tipo_de_dato);
        this.setRestriccion(restriccion);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Inicializa la columna indicando el valor, el tipo de dato SQL que tendra la columna y las restricciones
     * SQL que tendra.
     *
     * @param name          Nombre de la columna
     * @param Valor         Valor que tendra la columna.
     * @param tipo_de_dato  DataType que indica el tipo de dato SQL que almacenara la columna.
     * @param restriccion   Indica las restricciones SQL que tendra este campo.
     * @param default_value Indica el valor por default que tendra la columna en BD's
     */
    public Column(String name, T Valor, String default_value, DataType tipo_de_dato, Constraint... restriccion) {
        this.setName(name);
        this.setValor(Valor);
        this.setDataTypeSQL(tipo_de_dato);
        this.setRestriccion(restriccion);
        this.setDefault_value(default_value);
        this.setSize(this.getDataTypeSQL().getSize());
    }

    /**
     * Obtiene el valor de la columna
     *
     * @return Retorna el valor almacenado en la columna.
     */
    public T getValor() {
        return valor;
    }

    /**
     * Setea el valor de la columna.
     *
     * @param valor Valor que tendra la columna en este modelo
     */
    public void setValor(T valor) {
        this.valor = valor;
    }

    /**
     * Obtiene le tipo de dato SQL que esta almacenando esta columna.
     *
     * @return Retorna un objeto DataType que representa el tipo de dato SQL que tiene la columna en BD's
     */
    public DataType getDataTypeSQL() {
        return dataTypeSQL;
    }

    /**
     * Setea el tipo de dato SQL que esta almacenando esta columna.
     *
     * @param dataTypeSQL DataType que representa el tipo de dato SQL que tiene la columna en BD's
     */
    public void setDataTypeSQL(DataType dataTypeSQL) {
        this.dataTypeSQL = dataTypeSQL;
    }

    /**
     * Obtiene el array de las restricciones SQL que puede tener la columna
     *
     * @return Array de restricciones SQL que puede tener la columna
     */
    public Constraint[] getRestriccion() {
        return restriccion;
    }

    /**
     * Setea las restricciones SQL que puede tener la columna
     *
     * @param restriccion Array de restricciones SQL que puede tener la columna.
     */
    public void setRestriccion(Constraint[] restriccion) {
        this.restriccion = restriccion;
    }

    /**
     * Obtiene el valor por default establecido para la columna.
     *
     * @return Retorna el valor por default establecido para la columna.
     */
    public String getDefault_value() {
        return default_value;
    }

    /**
     * Setea el valor por default para la columna
     *
     * @param default_value Valor por default establecido para la columna
     */
    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    /**
     * Obtiene la bandera que indica si la columna Existe en BD's.
     *
     * @return True si la columna existe en la tabla correspondiente al modelo.
     */
    public Boolean getColumnExist() {
        return columnExist;
    }

    /**
     * Setea la bandera que indica si la columna existe en BD's
     *
     * @param columnExist True si la columna existe, False si la columna no existe
     */
    public void setColumnExist(Boolean columnExist) {
        this.columnExist = columnExist;
    }

    /**
     * Obtiene el nombre del tipo de Dato en SQL
     *
     * @return Retorna el nombre del tipo de dato en SQL si este no necesita la especificación de un tamaño.
     * Ejemplo: Datatime retornara Datatime
     * Varchar retornara Varchar(Size).
     * El Size puede ser manipulado a travez del método SetSize(Size);
     */
    public String columnToString() {
        if (stringIsNullOrEmpty(this.getSize())) {
            return this.dataTypeSQL.name();
        } else {
            return this.dataTypeSQL.name() + "(" + this.getSize() + ")";
        }
    }

    /**
     * Obtiene el Valor que tendra entre parentecis el tipo de dato, por lo general sería
     * Varchar(size), pero de ser otro tipo de dato por ejemplo Identity(1,1), si usted desea modificar
     * el contenido de identity entre parentecis puede hacerlo a travez del método SetSize(Size);
     *
     * @return el Valor que tendra entre parentecis el tipo de dato.
     */
    public String getSize() {
        return size;
    }

    /**
     * Setea el Valor que tendra entre parentecis el tipo de dato, por lo general sería
     * Varchar(size), pero de ser otro tipo de dato por ejemplo Identity(1,1), si usted desea modificar
     * el contenido de identity entre parentecis puede hacerlo a travez del método SetSize(Size);
     *
     * @param Size Cadena que representa el contenido del tipo de dato entre Parentesis.
     */
    public void setSize(String Size) {
        this.size = Size;
    }

    /**
     * Obtiene el nombre de la columna SQL
     *
     * @return Nombre de la columna SQL
     */
    public String getName() {
        return name;
    }

    /**
     * Setea el nombre de la Columna SQL
     *
     * @param name Nombre de la columna
     */
    public void setName(String name) {
        this.name = name;
    }
}

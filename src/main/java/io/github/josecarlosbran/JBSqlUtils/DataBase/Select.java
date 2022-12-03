package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Column;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class Select extends Get{

    private String sql;

    /**
     * Lista de los parametros a envíar
     */
    protected List<Column> parametros = new ArrayList<>();

    /**
     * Constructor que recibe como parametro:
     *
     * @param TableName El nombre de la tabla sobre la cual se desea realizar el Select.
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    public Select(String TableName) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        String respuesta = "";
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        this.sql = "SELECT * FROM " + TableName + "";
    }


    /**
     * Proporciona un punto de entrada para agregar la lógica de una sentencia WHERE a la sentencia SQL que
     * deseamos ejecutar
     *
     * @param columna  Columna que sera evaluada
     * @param operador Operador por medio del cual se evaluara la columna
     * @param value    Valor contra el que se evaluara la columna
     * @return Punto de entrada a metodos que permiten seguir modificando la expresión de filtro u obtener el o los
     * modelos que hacen match con la consulta generada
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     * @throws ValorUndefined        Lanza esta excepción si alguno de los parametros proporcionados esta
     *                               Vacío o es Null
     */
    public Where where(String columna, Operator operador, Object value) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Where(columna, operador, value, this.sql);
    }


    /**
     * Obtiene una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     * @param columnas Lista con los nombres de las columnas que se desea recuperar, si se desea obtener
     *      odas las columnas de la tabla especificada envíar NULL como parametro
     * @return Retorna una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     *      Envíada como parametro
     */
    public List<JSONObject> getInJsonObjects(List<String> columnas) {
        return super.get(this.sql, this.parametros, columnas);
    }


}

package io.github.josecarlosbran.JBSqlUtils.DataBase;


import com.josebran.LogsJB.LogsJB;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

import java.util.Objects;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.getColumn;
import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona la funcionalidad de setear un valor a una columna en una sentencia SQL
 * al momento de se llamada a travez de un InsertInto
 *
 * @author José Bran
 */
public class Value extends AndValue {


    /**
     * Constructor que recibe como parámetro:
     *
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value     Valor que se asignara a la columna.
     * @param sql       Sentencia SQL a la cual se agregara la columna y valor a setear.
     * @throws ValorUndefined ValorUndefined Lanza esta Excepción si
     *                        alguno de los parámetros proporcionados esta vacío o es Null
     */
    protected Value(String columName, Object value, String sql) throws ValorUndefined {
        super();
        if (stringIsNullOrEmpty(columName)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(value)) {
            LogsJB.warning("El valor proporcionado para la columna esta vacío o es NULL");
            LogsJB.warning(sql);
            LogsJB.warning(columName);
            LogsJB.warning((String) value);
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        this.parametros.add(getColumn(value));
        this.sql = sql + "(" + columName + ")";
    }


}

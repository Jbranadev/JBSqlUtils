package io.github.josecarlosbran.JBSqlUtils.DataBase;


import com.josebran.LogsJB.LogsJB;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.getColumn;
import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona la lógica para insertar otro valor a una columna a travez de la sentencia
 * InsertInto
 * @author Jose Bran
 */
public class AndValue {


    private String sql;

    /**
     * Lista de los parametros a envíar
     */
    private List<Column> parametros = new ArrayList<>();


    /**
     * Constructor que recibe como parametro:
     *
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value     Valor que se asignara a la columna.
     * @param sql       Sentencia SQL a la cual se agregara la columna y valor a setear.
     * @throws ValorUndefined ValorUndefined Lanza esta Excepción si
     *                        alguno de los parametros proporcionados esta vacío o es Null
     */
    protected AndValue(String columName, Object value, String sql, List<Column> parametros) throws ValorUndefined {
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
        this.parametros = parametros;
        this.parametros.add(getColumn(value));
        String newcampo = ", " + columName + ")";
        this.sql = sql.replace(")", newcampo);
    }

    /**
     * Entrega la capacidad de setear otro valor antes de ejecutar la sentencia Upddate
     *
     * @param columName El nombre de la columna a la cual se asignara el valor porporcionado.
     * @param value     Valor que se asignara a la columna.
     * @return Retorna un objeto AndSet que entrega la capacidad de setear otro valor
     * antes de ejecutar la sentencia Upddate
     * @throws ValorUndefined ValorUndefined ValorUndefined Lanza esta Excepción si
     *                        alguno de los parametros proporcionados esta vacío o es Null
     */
    public AndValue andValue(String columName, Object value) throws ValorUndefined {
        return new AndValue(columName, value, this.sql, this.parametros);
    }


    /**
     * Ejecuta la sentencia SQL proporcionada y retorna la cantidad de filas afectadas
     *
     * @return Retorna un Entero que representa la cantidad de filas afectadas al ejecutar la sentencia SQL
     * proporcionada.
     * @throws Exception Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    public int execute() throws Exception {
        String values = " VALUES (";
        //Setea los parametros de la consulta
        for (int i = 0; i < this.parametros.size(); i++) {
            //Obtengo la información de la columna
            if (i == 0) {
                values = values + "?";
            } else if (i == this.parametros.size() - 1) {
                values = values + ",?)";
            } else {
                values = values + ",?";
            }
        }
        this.sql = this.sql + values;
        return new Execute(this.sql, this.parametros).execute();
    }


}

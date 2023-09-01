package io.github.josecarlosbran.JBSqlUtils.DataBase;


import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que permite el poder eliminar una tabla en BD's a travez del método Execute()
 * @author José Bran
 */
public class DropTableIfExist extends Methods_Conexion {

    /**
     * Constructor que recibe como parámetro:
     *
     * @param TableName El nombre de la tabla que se desea eliminar.
     * @throws ValorUndefined        Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     *                               BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    protected DropTableIfExist(String TableName) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        this.setTableName(TableName);
    }


    /**
     * Ejecuta la sentencia SQL encargada de eliminar la tabla en BD's
     *
     * @return True si la tabla fue eliminada en BD's, Flase si la tabla no existe en BD's o si
     * sucede algún problema al ejecutar la sentencia SQL.
     * @throws Exception Si sucede una excepción en la ejecución asincrona de la sentencia en BD's lanza esta excepción
     */
    public Boolean execute() throws Exception {
        return this.dropTableIfExist();
    }
}

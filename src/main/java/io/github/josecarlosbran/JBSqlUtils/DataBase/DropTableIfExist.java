package io.github.josecarlosbran.JBSqlUtils.DataBase;


import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Methods_Conexion;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * @author José Bran
 * Clase que permite el poder eliminar una tabla en BD's a travez del metodo Execute()
 */
public class DropTableIfExist extends Methods_Conexion {

    /**
     * Constructor que recibe como parametro:
     *
     * @param TableName El nombre de la tabla que se desea eliminar.
     * @throws ValorUndefined Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     */
    public DropTableIfExist(String TableName) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(TableName)) {
            throw new ValorUndefined("El nombre de la Tabla proporcionado esta vacío o es NULL");
        }
        this.setTableName(TableName);
    }


    /**
     * Ejecuta la sentencia SQL encargada de eliminar la tabla en BD's
     * @return True si la tabla fue eliminada en BD's, Flase si la tabla no existe en BD's o si
     * sucede algun problema al ejecutar la sentencia SQL.
     */
    public Boolean execute(){
        return this.dropTableIfExist();
    }
}

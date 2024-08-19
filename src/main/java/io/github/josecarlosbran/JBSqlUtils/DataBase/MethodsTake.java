package io.github.josecarlosbran.JBSqlUtils.DataBase;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jose Bran
 * Clase que brinda acceso a la logica de los metodos de una sentencia que incluye la apertura de un parentecis
 */
public class MethodsTake<T> extends MethodsAndSet {
    protected T modelo = null;

    /**
     * Constructor por default de la clase MethodsWhere, que inicializa la clase
     */
    protected MethodsTake() {
        super();
    }

    /**
     * Constructor por default de la clase MethodsWhere, que inicializa la clase
     *
     * @param getGetPropertiesSystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    protected MethodsTake(Boolean getGetPropertiesSystem) {
        super(getGetPropertiesSystem);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Obtiene una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     *
     * @param columnas Lista con los nombres de las columnas que se desea recuperar, si se desea obtener
     *                 odas las columnas de la tabla especificada envíar NULL como parametro
     * @return Retorna una lista de Json Object la cual contiene cada uno de los registros que cumple con la sentencia sql
     * Envíada como parametro
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public List<JSONObject> getInJsonObjects(String... columnas) throws Exception {
        return super.get(this.sql, this.parametros, columnas);
    }
}

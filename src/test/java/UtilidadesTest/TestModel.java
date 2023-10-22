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
package UtilidadesTest;

import io.github.josecarlosbran.JBSqlUtils.Anotations.ColumnDefined;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.testng.annotations.Ignore;

/**
 * @author Jose Bran
 * Clase de Pruebas
 */
@Ignore
@ToString
@Getter
@Setter
public class TestModel extends JBSqlUtils {

    /**
     * Declara un miembro del modelo, el cual en java almacenara un dato de tipo Integer.
     * <p>
     * Si deseamos la tabla sea creada en Bd's a traves de JBSqlUtils, podemos definir las propiedades que tendría cada columna y
     * cual sería su representación en Java a traves de la etiqueta ColumnDefined, configurando así la información que deseamos
     * JBSqlUtils tenga en consideración al momento de crear la tabla.
     * <p>
     * Agregamos dos restricciones SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
     * desde nuestra aplicación en caso esta no exista, de lo contrario no es necesario que agreguemos restricciones.
     */

    @ColumnDefined(name = "Id", dataTypeSQL = DataType.INTEGER, constraints = {
            Constraint.PRIMARY_KEY, Constraint.AUTO_INCREMENT
    })
    private Integer id;

    /**
     * Declara un miembro del modelo, el cual en java almacenara un dato de tipo String.
     * <p>
     * Agregamos un parámetro extra el cual es el default_value antes de indicar las restricciones,
     * este parámetro es de tipo String, por medio de este parámetro podemos definir el valor que deseamos tenga la
     * columna por default (Puede ser un valor del tipo de dato SQL, o una funcion SQL que retorne un valor del
     * tipo de dato SQL de la columna) cuando se inserte un registro y no se especifique un dato para esa columna,
     * este unicamente funcionara cuando tenga la restriccion DEFAULT definida y se definira unicamente si se
     * crea la tabla en BD's desde nuestra aplicación a través del método modelo.crateTable().
     * <p>
     * Agregamos una restriccion SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
     * desde nuestra aplicación en caso esta no exista a través del método modelo.crateTable(), de lo contrario no es necesario que agreguemos restricciones.
     */

    @ColumnDefined(name = "name", dataTypeSQL = DataType.VARCHAR, constraints = {
            Constraint.DEFAULT
    }, default_value = "'Daniel'", size = "200")
    private String name;
    /**
     * Por convención el nombre de cada miembro correspondiente a una columna en BD's debe tener el mismo
     * nombre que la columna en BD's.
     *
     * En el caso que deseemos mapear el campo en java con una columna que posee otro nombre en BD's podemos indicar esto
     * a traves de la propiedad name en la etiqueta ColumnDefined
     *
     */
    /**
     * Declara un miembro del modelo, el cual en java almacenara un dato de tipo String.
     * <p>
     * Indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
     * Varchar.
     */

    @ColumnDefined(name = "apellido", dataTypeSQL = DataType.VARCHAR, size = "200")
    private String apellido;

    /**
     * Declara un miembro del modelo, el cual en java almacenara un dato de tipo Boolean.
     * <p>
     * En la definición de la columna indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
     * BIT.
     * <p>
     * En este ejemplo seteamos 'true' como default_value, debido a que este modelo se conectara a un SQLServer,
     * en PostgreSQL la sintaxis es true. Por lo cual es importante tener claro la sintaxis de la BD's a la cual
     * se estará conectando el modelo.
     * <p>
     * Agregamos una restriccion SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
     * desde nuestra aplicación en caso esta no exista a través del método modelo.crateTable(), de lo contrario no es necesario que agreguemos restricciones.
     */

    @ColumnDefined(name = "isMayor", dataTypeSQL = DataType.BIT, constraints = {
            Constraint.DEFAULT
    }, default_value = "true")
    private Boolean isMayor;

    /**
     * En el constructor de nuestra clase que utilizaremos como modelo al heredar la clase JBSqlutils
     * hacemos el llamado al constructor de la clase JBSqlUtils la cual inicializara el modelo para poder
     * ser utilizado, una vez instanciado el modelo, podremos obtener uno o varios registros de la tabla
     * correspondiente al modelo, insertar, actualizar o eliminar registros.
     * <p>
     * Es importante que antes de instanciar un modelo que herede la clase JBSqlUtils se hayan definido
     * las propiedades de conexión como variables del sistema.
     */
    public TestModel() {
        /**
         * Hacemos el llamado al constructor de la Clase JBSqlUtils
         */
        super();
        this.setTableName("testModel");
    }

    /**
     * Si deseamos que nuestro modelo no utilice las propiedades de conexión globales, podemos crear un constructor que
     * reciba un único parametro de tipo Booleano el cual enviamos como parametro al constructor de la clase padre JBSqlUtils
     *
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema,
     *                          true si deseamos que obtenga las variables globales y false si deseamos que no obtenga las
     *                          variables globales
     */
    public TestModel(Boolean getPropertySystem) throws DataBaseUndefind, PropertiesDBUndefined {
        super(getPropertySystem);
        this.setTableName("testModel");
    }

    public Integer getOne(Integer numero) {
        return numero;
    }

    public void setOne(Integer numero, Integer numero2) {
    }

    public void setOne() {
    }
}

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

import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
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
@Getter @Setter
public class TestModel extends JBSqlUtils {

    /**
     * Declara un miembro del modelo, el cual en java almacenara un dato de tipo Integer, se define Integer,
     * ya que la clase Column es una clase generica y no puede trabajar con datos primivitos como int, pero si con
     * clases contenedoras como Integer.
     * <p>
     * En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
     * Integer.
     * <p>
     * Agregamos dos restricciones SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
     * desde nuestra aplicación en caso esta no exista, de lo contrario no es necesario que agreguemos restricciones.
     */
    private Column<Integer> id1 = new Column<>(DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Id", dataTypeSQL = DataType.INTEGER, constraints = {
            Constraint.PRIMARY_KEY, Constraint.AUTO_INCREMENT
    })
    private Integer id;

    /**
     * Declara un miembro del modelo, el cual en java almacenara un dato de tipo String.
     * <p>
     * En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
     * Varchar.
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
    private Column<String> name1 = new Column<>(DataType.VARCHAR, "'Daniel'", Constraint.DEFAULT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "name", dataTypeSQL = DataType.VARCHAR, constraints = {
            Constraint.DEFAULT
    }, default_value = "'Daniel'", size = "200")
    private String name;
    /**
     * Para poder utilizar JBSqlUtils es necesario que los miembros de la clase modelo, que correspondan
     * a una columna de la tabla correspondiente al modelo, sean del tipo Column, especificando el tipo de dato
     * en java y por medio del constructor del objeto Column se pase como parámetro el tipo de dato SQL
     * de la columna, adicional a esto se pueden definir restricciones, como valor por defecto para la columna
     * si se desea utilizar el modelo para crear la tabla en BD's, pero estos últimos son opcionales
     * el único parámetro obligatorio es el DataType de la columna en BD's.
     *
     * Por convención el nombre de cada miembro correspondiente a una columna en BD's debe tener el mismo
     * nombre que la columna en BD's. y estos deben tener sus respectivos metodos set an get, teniendo estos
     * por convención el nombre setColumnName, getColumName.
     *
     * Por ejemplo, para la columna Id = El miembro del modelo será Id, JBSqlUtils no es case sensitive,
     * así que en BD's la columna puede ser ID y en el modelo id, que JBSqlUtils hará el match entre la columna
     * y el miembro de la clase modelo.
     *
     */
    /**
     * Declara un miembro del modelo, el cual en java almacenara un dato de tipo String.
     * <p>
     * En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
     * Varchar.
     */
    private Column<String> apellido1 = new Column<>(DataType.VARCHAR);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "apellido", dataTypeSQL = DataType.VARCHAR, size = "200")
    private String apellido;


    /**
     * Declara un miembro del modelo, el cual en java almacenara un dato de tipo Boolean.
     * <p>
     * En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
     * BIT.
     * <p>
     * En este ejemplo seteamos 'true' como default_value, debido a que este modelo se conectara a un SQLServer,
     * en PostgreSQL la sintaxis es true. Por lo cual es importante tener claro la sintaxis de la BD's a la cual
     * se estará conectando el modelo.
     * <p>
     * Agregamos una restriccion SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
     * desde nuestra aplicación en caso esta no exista a través del método modelo.crateTable(), de lo contrario no es necesario que agreguemos restricciones.
     */
    private Column<Boolean> isMayor1 = new Column<>(DataType.BIT, "true", Constraint.DEFAULT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "isMayor", dataTypeSQL = DataType.BIT, constraints = {
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
    public TestModel() throws DataBaseUndefind, PropertiesDBUndefined {
        /**
         * Hacemos el llamado al constructor de la Clase JBSqlUtils
         */
        super();
        this.setTableName("testModel");
        /**
         * Setear un tamaño especifico para una columna en BD's
         * Esto nos permitira que cuando creemos la tabla desde nuestra aplicación podamos personalizar el tamaña de las columnas
         * de acuerdo a las opciónes que nos brinda cada Servidor de BD's, en este caso es un VARCHAR(1000) al cual indicamos que deseamos
         * tenga una longitud de mil
         */
        this.getName1().setSize("200");
        this.getApellido1().setSize("200");
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
        this.getName1().setSize("200");
        this.getApellido1().setSize("200");
    }

    public Integer getOne(Integer numero) {
        return numero;
    }

    public void setOne(Integer numero, Integer numero2) {

    }

    public void setOne() {

    }

}

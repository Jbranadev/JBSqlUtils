# Configuración de Modelos :gear:

Utilizar JBSqlUtils es muy fácil.

## ¿Cómo crear modelos a través de JBSqlUtils?

Para poder crear clases que funcionen cómo modelos en nuestra aplicación,
únicamente es necesario heredar la clase JBSqlUtils, declarar los miembros de la clase
que corresponden a cada una de las columnas de la tabla con las que queremos poder interactuar,
acá vemos un ejemplo:

~~~

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

~~~

***

## ¿Cómo instanciar un modelo que utilice sus propias propiedades de conexión?

Para poder crear un modelo que se conecte a una BD's diferente a la configurada
a través de las variables globales, se realizara de la siguiente manera

~~~
/**
 * Instanciamos el modelo indicando que no obtendra las variables globales de conexión
 * de la JVM
 */
this.testModel = new TestModel(false);
/**
 * Seteamos las propiedades de conexión del modelo 
 */
this.testModel.setPort(String port);
this.testModel.setHost(String host);
this.testModel.setUser(String user);
this.testModel.setPassword(String password);
this.testModel.setBD(String NameBD);
this.testModel.setDataBaseType(DataBase dataBaseType);
this.testModel.setPropertisURL(String propertiesUrlConection);
~~~

***

## ¿Cómo trasladar las propiedades de conexión de un modelo a otro?

Para poder crear un modelo que se conecte a una BD's diferente a la configurada
a través de las variables globales, se realizara de la siguiente manera

~~~
/**
 * Instanciamos el modelo indicando que no obtendra las variables globales de conexión
 * de la JVM
 */
TestModel model = new TestModel(false);
/**
 * Trasladamos las propiedades de conexión al modelo a traves del método llenarPropertiesFromModel
 * enviamos como parámetro el modelo desde el cual se obtendran las variables de conexión.
 * @param proveedor Modelo desde el que se obtendran las propiedades de conexión
 */
model.llenarPropertiesFromModel(this.testModel);
~~~

***
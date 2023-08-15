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
public class Test extends JBSqlUtils {

  /**
   * En el constructor de nuestra clase que utilizaremos cómo modelo al heredar la clase JBSqlutils
   * hacemos el llamado al constructor de la clase JBSqlUtils la cual inicializara el modelo para poder
   * ser utilizado, una vez instanciado el modelo, podremos obtener uno o varios registros de la tabla
   * correspondiente al modelo, insertar, actualizar o eliminar registros.
   *
   * Es importante que antes de instanciar un modelo que herede la clase JBSqlUtils se hayan definido
   * las propiedades de conexión cómo variables del sistema.
   *
   * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
   * BD's a la cual se conectara el modelo.
   * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
   * propiedades de conexión necesarias para conectarse a la BD's especificada.
   * @throws ValorUndefined Lanza esta excepción si la bandera proporcionada es Null
   */
  public Test() throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
    /**
     * Hacemos el llamado al constructor de la Clase JBSqlUtils
     */
    super();
    /**
    * Si deseamos setear un nombre personalizado de la tabla a la cual representa el modelo
    * podemos hacerlo a través del metodo setTableName
    */
    this.setTableName("testModel");

    /**
     * Setear un tamaño especifico para una columna en BD's
     * Esto nos permitira que cuando creemos la tabla desde nuestra aplicación podamos personalizar el tamaña de las columnas
     * de acuerdo a las opciónes que nos brinda cada Servidor de BD's, en este caso es un VARCHAR(1000) al cual indicamos que deseamos
     * tenga una longitud de mil
     */
    this.getName().setSize("1000");
    
  }

   /**
    * Si deseamos que nuestro modelo no utilice las propiedades de conexión globales, podemos crear un constructor que 
    * reciba un único parametro de tipo Booleano el cual enviamos como parametro al constructor de la clase padre JBSqlUtils
    * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema,
    *                          true si deseamos que obtenga las variables globales y false si deseamos que no obtenga las 
    *                          variables globales
    * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
    *                               BD's a la cual se conectara el modelo.
    * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
    *                               propiedades de conexión necesarias para conectarse a la BD's especificada.
    */
   public TestModel(Boolean getPropertySystem) throws DataBaseUndefind, PropertiesDBUndefined {
       super(getPropertySystem);
       /**
       * Si deseamos setear un nombre personalizado de la tabla a la cual representa el modelo
       * podemos hacerlo a través del metodo setTableName
       */
       this.setTableName("testModel");
       /**
       * Tambien podemos modificar el tamaño que tendrá cada columna al momento de solicitar
       * crear la tabla, a través del metodo setSize
       */
       this.getName().setSize("200");
       this.getApellido().setSize("200");
   }

  /**
   * Para poder utilizar JBSqlUtils es necesario que los miembros de la clase modelo, que correspondan
   * a una columna de la tabla correspondiente al modelo, sean del tipo Column, especificando el tipo de dato
   * en java y por medio del constructor del objeto Column se pase cómo parametro el tipo de dato SQL
   * de la columna, adicional a esto se pueden definir restricciones, cómo valor por defecto para la columna
   * si se desea utilizar el modelo para crear la tabla en BD's, pero estos últimos son opcionales
   * el único parametro obligatorio es el DataType de la columna en BD's.
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
   * Declara un miembro del modelo, el cual en java almacenara un dato de tipo Integer, se define Integer,
   * ya que la clase Column es una clase generica y no puede trabajar con datos primivitos cómo int, pero si con
   * clases contenedoras cómo Integer.
   *
   * En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
   * Integer.
   *
   * Agregamos dos restricciones SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
   * desde nuestra aplicación en caso esta no exista, de lo contrario no es necesario que agreguemos restricciones.
   */
  private Column<Integer> id=new Column<>(DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);

  /**
   * Declara un miembro del modelo, el cual en java almacenara un dato de tipo String.
   *
   * En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
   * Varchar.
   *
   * Agregamos un parametro extra el cual es el default_value antes de indicar las restricciones,
   * este parametro es de tipo String, por medio de este parametro podemos definir el valor que deseamos tenga la
   * columna por default (Puede ser un valor del tipo de dato SQL, o una funcion SQL que retorne un valor del
   * tipo de dato SQL de la columna) cuando se inserte un registro y no se especifique un dato para esa columna,
   * este únicamente funcionara cuando tenga la restriccion DEFAULT definida y se definira únicamente si se
   * crea la tabla en BD's desde nuestra aplicación a través del método modelo.crateTable().
   *
   * Agregamos una restriccion SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
   * desde nuestra aplicación en caso esta no exista a través del método modelo.crateTable(), de lo contrario no es necesario que agreguemos restricciones.
   */
  private Column<String> name=new Column<>(DataType.VARCHAR, "'Daniel'", Constraint.DEFAULT);

  /**
   * Declara un miembro del modelo, el cual en java almacenara un dato de tipo String.
   *
   * En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
   * Varchar.
   *
   */
  private Column<String> apellido=new Column<>(DataType.VARCHAR);

  /**
   * Declara un miembro del modelo, el cual en java almacenara un dato de tipo Boolean.
   *
   * En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
   * BIT.
   *
   * En este ejemplo seteamos 'true' cómo default_value, debido a que este modelo se conectara a un SQLServer,
   * en PostgreSQL y SQLite la sintaxis es true. Por lo cual es importante tener claro la sintaxis de la BD's a la cual
   * se estará conectando el modelo.
   *
   * Agregamos una restriccion SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
   * desde nuestra aplicación en caso esta no exista a través del método modelo.crateTable(), de lo contrario no es necesario que agreguemos restricciones.
   */
  private Column<Boolean> isMayor=new Column<>(DataType.BIT, "'true'", Constraint.DEFAULT);

  public Column<Integer> getId() {
    return id;
  }


  public void setId(Column<Integer> id) {
    this.id = id;
  }

  public Column<String> getName() {
    return name;
  }

  public void setName(Column<String> name) {
    this.name = name;
  }

  public Column<String> getApellido() {
    return apellido;
  }

  public void setApellido(Column<String> apellido) {
    this.apellido = apellido;
  }

  public Column<Boolean> getIsMayor() {
    return isMayor;
  }

  public void setIsMayor(Column<Boolean> isMayor) {
    this.isMayor = isMayor;
  }
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
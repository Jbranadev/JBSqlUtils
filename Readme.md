# JBSqlUtils :computer:

JBSqlUtils es un ORM desarrollado en java por José Carlos Alfredo Bran Aguirre, que permite gestionar BD's SQLite,
MySQL,
PostgreSQL y SQLServer, de una manera fácil y rápida sin interrumpir la ejecución del hilo principal del programa,
lo cual la hace un potente ORM, por medio del cual tendrá acceso a un CRUD, configurando únicamente la conexión del
modelo,
los atributos que posee la tabla en BD's cómo variables que pertenecerán al modelo en su aplicación.

JBSqlUtils también proporciona un potente generador de consultas que le permitirá actualizar o eliminar registros
de una tabla en su BD's sin necesidad de instanciar un objeto cómo tal, únicamente tendrá que configurar previamente
la conexión a su BD's.
* * *

## Estado del Proyecto :atom:

JBSqlUtils actualmente está en una etapa de desarrollo continuo, por lo cual sus observaciones y recomendaciones,
son bienvenidas para mejorar el proyecto.
***

## Configuración :gear:

Utilizar JBSqlUtils es muy fácil.

### Configurar variables de conexión globales

- Lo primero es setear las variables globales de conexión

Al setear las variables globales de conexión estas se almacenan cómo variables del sistema
del entorno de ejecución de la aplicación, las cuales no pueden ser accedidas más que por la misma aplicación
que las configuro y se eliminan, cuando la aplicación termina su ejecución.

Configuración necesaria para SQLite:

~~~ 
/*En este ejemplo, se estaría utilizando una BD's SQLite en el directorio de la aplicación, dentro
del cual existe una carpeta llamada BD y dentro de esta carpeta se crearía la BD's JBSqlUtils al establecer
la conexión si esta no existiera, de existir, únicamente se establecería la conexión.
*/ 

      
String separador=System.getProperty("file.separator");
String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
    "BD" +
    separador +
    "JBSqlUtils.db");
   
/**
 * Setea el nombre de la Base de Datos global a la que se conectaran los modelos que no tengan una configuración
 * personalizada.
 * @param BD Nombre de la Base de Datos.
 */
setDataBaseGlobal(BDSqlite);

/**
 * Setea el tipo de BD's global a la cual se estarán conectando los modelos que no tengan una configuración personalizada.
 * @param dataBase Tipo de BD's a la cual se estarán los modelos que no tengan una configuración personalizada, los tipos disponibles son
 *         MySQL,
 *         SQLServer,
 *         PostgreSQL,
 *         SQLite.
 */
setDataBaseTypeGlobal(DataBase.SQLite);
~~~

Configuración necesaria para MySQL, PostgreSQL y SQLServer:

~~~ 
/*En este ejemplo, se estaría utilizando una BD's que tiene que existir en el servidor con las
caracteristicas especificadas.
*/ 
   
   
/**
 * Setea el nombre de la Base de Datos global a la que se conectaran los modelos que no tengan una configuración
 * personalizada.
 * @param BD Nombre de la Base de Datos.
 */
public static void setDataBaseGlobal(String BD);


/**
 * Setea la Contraseña del usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
 * @param password Contraseña del usuario con el cual se conectara a la BD's.
 */
public static void setPasswordGlobal(String password);


/**
 * Setea el Usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
 * @param user Usuario con el cual se conectara a la BD's.
 */
public static void setUserGlobal(String user);


/**
 * Setea el puerto global con el que se conectaran los modelos que no tengan una configuración personalizada.
 * @param port Puerto en el cual se encuentra escuchando la BD's a la cual se pegaran los modelos.
 */
public static void setPortGlobal(String port);


/**
 * Setea el host en el cual se encuentra la BD's global a la cual se conectaran los modelos que no tengan una configuración personalizada.
 * @param host Host en el cual se encuentra la BD's a la que nos queremos conectar.
 */
public static void setHostGlobal(String host);


/**
 * Setea el tipo de BD's global a la cual se estarán conectando los modelos que no tengan una configuración personalizada.
 * @param dataBase Tipo de BD's a la cual se estarán los modelos que no tengan una configuración personalizada, los tipos disponibles son
 *         MySQL,
 *         SQLServer,
 *         PostgreSQL,
 *         SQLite.
 */
public static void setDataBaseTypeGlobal(DataBase dataBase);
~~~

Al haber configurado las variables de conexión globales, su aplicación estará lista
para instanciar objetos que hereden la clase JBSqlUtils, los cuales fungirán cómo modelos y le permitirán
realizar operaciones sobre la tabla correspondiente a cada modelo.

De no desear usar Modelos en su aplicación hasta este punto, podrá utilizar JBSqlUtils cómo un generador de
sentencias SQL que le permitirán actualizar o eliminar registros de una tabla de acuerdo a la lógica que brinde a la
sentencia SQL a ejecutar, todo esto sin necesidad de instanciar un modelo, únicamente habiendo configurado sus
variables de conexión.

***

## ¿Cómo utilizar JBSqlUtils sin necesidad de crear un Modelo?

JBSqlUtils puede ser utilizada cómo un generador de sentencias SQL, para Crear, Eliminar tablas,
Insertar, Actualizar, Eliminar o Seleccionar registros en
una determinada tabla de forma masiva, de acuerdo a la lógica que se le dé a la consulta.

- Crear una tabla.

Para crear una tabla utilizamos el metodo createTable despues de haber definido el nombre de la tabla que deseamos crear 
y las columnas que deseamos tenga nuestra tabla.

~~~

/**
* Definimos las columnas que deseamos posea nuestra tabla 
*/
 
/**
* Para poder utilizar JBSqlUtils es necesario que los miembros de la clase modelo, que correspondan
* a una columna de la tabla correspondiente al modelo, sean del tipo Column, especificando el tipo de dato
* en java y por medio del constructor del objeto Column se pase cómo parametro el tipo de dato SQL
* de la columna, adicional a esto se pueden definir restricciones, cómo valor por defecto para la columna 
* para crear la tabla en BD's, pero estos últimos son opcionales
* el único parametro obligatorio es el DataType de la columna en BD's.
*
* Por convención el nombre de cada miembro correspondiente a una columna en BD's debe tener el mismo
* nombre que la columna en BD's. y estos deben tener sus respectivos métodos set an get, teniendo estos
* por convención el nombre setColumnName, getColumName.
*
* Por ejemplo, para la columna Id = El miembro del modelo será Id, JBSqlUtils no es case sensitive,
* así que en BD's la columna puede ser ID y en el modelo id, que JBSqlUtils hará el match entre la columna
* y el miembro de la clase modelo.
*
*/



/**
* Declara un miembro de la tabla que deseamos crear, el cual en java almacenara un dato de tipo Integer, se define Integer,
* ya que la clase Column es una clase generica y no puede trabajar con datos primivitos cómo int, pero si con
* clases contenedoras cómo Integer.
*
* En el constructor mandamos como primer parametro el nombre que deseamos tenga la columna.
*
* En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
* Integer.
*
* Agregamos dos restricciones SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
* desde nuestra aplicación en caso esta no exista, de lo contrario no es necesario que agreguemos restricciones.
*/
Column<Integer> Id = new Column<>("Id", DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);


/**
* Declara un miembro de la tabla, el cual en java almacenara un dato de tipo String.
* <p>
*
* En el constructor mandamos como primer parametro el nombre que deseamos tenga la columna.
*
* En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
* Varchar.
*/
Column<String> Name = new Column<>("Name", DataType.VARCHAR);


/**
* Declara un miembro de la tabla, el cual en java almacenara un dato de tipo String.
* <p>
*
* En el constructor mandamos como primer parametro el nombre que deseamos tenga la columna.
*
* En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
* Varchar.
*/
Column<String> Apellido = new Column<>("Apellido",DataType.VARCHAR);


/**
* Declara un miembro del modelo, el cual en java almacenara un dato de tipo Boolean.
* <p>
*
* En el constructor mandamos como primer parametro el nombre que deseamos tenga la columna.
*
* En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
* BOOLEAN.
* <p>
* En este ejemplo seteamos 'true' como default_value, debido a que este modelo se conectara a un SQLServer,
* en PostgreSQL la sintaxis es true. Por lo cual es importante tener claro la sintaxis de la BD's a la cual
* se estará conectando el modelo.
* <p>
* Agregamos una restriccion SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
* desde nuestra aplicación en caso esta no exista a través del metodo modelo.crateTable(), de lo contrario no es necesario que agreguemos restricciones.
*/
Column<Boolean> Estado = new Column<>("Estado", DataType.BOOLEAN, "true", Constraint.DEFAULT);

/**
* Para crear una tabla utilizamos el metodo createTable despues de haber definido el nombre de la tabla que deseamos Crear 
* y las columnas que deseamos tenga nuestra tabla
*/
createTable("Proveedor").addColumn(Name).addColumn(Id).addColumn(Apellido).addColumn(Estado).createTable();

~~~


- Eliminar una tabla.

Para eliminar una tabla de BD's utilizamos el metodo execute de la clase dropTableIfExist a la cual mandamos como parametro 
el nombre de la tabla que queremos eliminar.

~~~

/**
* Para eliminar una tabla de BD's utilizamos el metodo execute de la clase dropTableIfExist a la cual mandamos como parametro
* el nombre de la tabla que queremos eliminar.
*/
dropTableIfExist("Proveedor").execute();

~~~

- Insertar Registros en una tabla.

Para insertar registros hacemos uso del metodo execute que esta disponible en la clase value y andValue a las cuales podemos acceder 
a traves de la clase insertInto a la cual enviamos como parametro el nombre de la tabla a la que queremos insertar, a traves de los metodos value 
y andValue definimos los valores que queremos insertar en determinada columna, el metodo execute retorna la cantidad de registros insertados.

De suceder algun error durante la ejecución de la sentencia insertInto retorna 0, de lo contrario retorna 1, ya que solo se puede insertar un registro a la vez.

~~~

/**
* Para insertar registros hacemos uso del metodo execute que esta disponible en la clase value y andValue a las cuales podemos acceder 
* a traves de la clase insertInto a la cual enviamos como parametro el nombre de la tabla a la que queremos insertar, a traves de los metodos value
* y andValue definimos los valores que queremos insertar en determinada columna.
*/
int registros=insertInto("Proveedor").value("Name", "Daniel").andValue("Apellido", "Quiñonez").andValue("Estado", false).execute();

~~~

- Actualizar registros.

Para actualizar registros sin necesidad de instanciar un modelo, puede hacerlo a través del
update método estático de la clase JBSqlUtils, el cual brinda los métodos necesarios, para poder
llegar al método execute, el cual ejecuta la sentencia SQL generada y retorna el número de
filas afectadas por la ejecución de la sentencia SQL.

~~~

/**
 * Actualizar todas las filas de una tabla X (Test), senteando un valor Y(Jose Carlos) a una columna Z(name).
 * El método update recibe cómo parametro el nombre de la tabla que se desea actualizar y proporciona acceso
 * al método set el cual recibe cómo primer parametro el nombre de la columna que se desea modificar y el valor
 * que se desea setear a la columna, el método set proporciona acceso al método execute el cual se encarga de
 * ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
 */
int rows_afected=update("Test").set("name", "Jose Carlos").execute();

/**
 * Podemos agregar una sentencia Where, por medio del cual podemos acceder a los métodos necesarios para
 * filtrar la cantidad de filas que queremos modificar, una vez hemos terminado de brindar la lógica hacemos el
 * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
 * afectadas.
 */
rows_afected=update("Test").set("name", "Jose Carlos").where("Id", Operator.MAYOR_QUE, 2).and("apellido", Operator.LIKE, "Bran%").execute();


/**
 * Podemos actualizar mas de una columna a través del método andSet, el cual nos proporciona la capacidad de
 * modificar el valor de otra columna y acceso a los métodos andSet para setear otro valor a otra columna y el método
 * where por medio del cual podemos filtrar las filas que se veran afectadas al llamar al método execute, el cual
 * se encargara de ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
 */
rows_afected=update("Test").set("name", "Jose Carlos").andSet("IsMayor", true).execute();

~~~

- Eliminar registros.

Para eliminar registros sin necesidad de instanciar un modelo, puede hacerlo a través del
delete método estático de la clase JBSqlUtils, el cual brinda los métodos necesarios, para poder
llegar al método execute, el cual ejecuta la sentencia SQL generada y retorna el número de
filas afectadas por la ejecución de la sentencia SQL.

~~~

/**
 * Eliminar todas las filas de una tabla X (Test), donde la columna Y(Id) tiene un valor MAYOR O IGUAL a Z(2).
 * El método delete recibe cómo parametro el nombre de la tabla que se desea eliminar registros y proporciona acceso
 * al método Where, por medio del cual podemos acceder a los métodos necesarios para
 * filtrar la cantidad de filas que queremos eliminar, una vez hemos terminado de brindar la lógica hacemos el
 * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
 * afectadas.
 */
int rows_afected=delete("Test").where("Id", Operator.MAYOR_IGUAL_QUE, 2).execute();

~~~


- Seleccionar registros.

Para obtener los registros de una tabla de BD's podemos hacerlo a traves del metodo select envíando como parametro 
el nombre de la tabla de la cual deseamos obtener los registros, así mismo podemos filtrar los resultados a traves del metodo 
where el cual proporciona acceso a metodos por medio de los cuales podemos filtrar los resultados.

~~~

/**
* Si deseamos obtener todas las columnas de la tabla envíamos el parametro columnas del metodo 
* getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parametro 
* del metodo select
*/
List<String> columnas=null;

/**
* Si deseamos obtener unicamente determinadas columnas, es necesario envíar como parametro una lista de strings
* con los nombres de las columnas que deseamos obtener del metodo getInJsonObjects
*/
columnas= new ArrayList<>();
columnas.add("Id");
columnas.add("Name");

/**
* Para obtener los registros de una tabla de BD's podemos hacerlo a traves del metodo select envíando como parametro
* el nombre de la tabla de la cual deseamos obtener los registros, así mismo podemos filtrar los resultados a traves del metodo 
* where el cual proporciona acceso a metodos por medio de los cuales podemos filtrar los resultados.
*/
List<JSONObject> lista=select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
    .and("Apellido", Operator.LIKE, "%m%").take(3).getInJsonObjects(columnas);

/**
* Imprimimos los registros obtenidos
*/
lista.forEach( fila -> {
LogsJB.info(fila.toString());
});

~~~

* * *

## ¿Cómo crear modelos a través de JBSqlUtils?

Para poder crear clases que funcionen cómo modelos en nuestra aplicación,
únicamente es necesario heredar la clase JBSqlUtils, declarar los miembros de la clase
que corresponden a cada una de las columnas de la tabla con las que queremos poder interactuar,
acá vemos un ejemplo:

~~~

package io.github.josecarlosbran.JBSqlUtils.Pruebas;

import io.github.josecarlosbran.JBSqlUtils.Column;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.JBSqlUtils;

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
   * nombre que la columna en BD's. y estos deben tener sus respectivos métodos set an get, teniendo estos
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

* * *

## ¿Cómo eliminar la tabla correspondiente a un modelo?

Para poder eliminar la tabla correspondiente a un modelo únicamente necesitamos crear una
instancia del mismo y llamar al método dropTableIfExist().

~~~

/**
* Instanciamos el modelo
*/
Test test = new Test();

/**
* Elimina la tabla correspondiente al modelo en BD's
* @return True si la tabla correspondiente al modelo en BD's existe y fue eliminada, de no existir la tabla correspondiente
* en BD's retorna False.
*/
test.dropTableIfExist();

~~~

* * *

## ¿Cómo crear la tabla correspondiente a un modelo?

Para poder crear la tabla correspondiente a un modelo únicamente necesitamos crear una
instancia del mismo y llamar al método crateTable().

~~~

/**
* Instanciamos el modelo
*/
Test test = new Test();

/**
* Crea la tabla correspondiente al modelo en BD's si esta no existe.
* @return True si la tabla correspondiente al modelo en BD's no existe y fue creada exitosamente,
* False si la tabla correspondiente al modelo ya existe en BD's
*/
test.crateTable();

~~~

![](Imagenes/Table1.jpg)

Por default la tabla correspondiente al modelo, incluirá las columnas created_at y update_at

Si deseamos que JBSqlUtils no gestione las columnas created_at y update_at, al momento de crear la tabla,
insertar o actualizar un registro, basta con llamar el método setTimestamps(false), enviando cómo parametro true
si queremos que JBSqlUtils gestione las columnas o false si queremos que JBSqlUtils No gestione estas columnas.
por default JBSqlUtils esta configurada para manejar la columnas created_at y update_at.

~~~

/**
* Setea la bandera que define si el modelo desea que JBSqlUtils maneje las timestamps Created_at, Update_at.
* @param timestamps True si las timestamps serán manejadas por JBSqlUtils, False, si el modelo no tiene estas
*          columnas.
*/
test.setTimestamps(false);


/**
* Crea la tabla correspondiente al modelo en BD's si esta no existe.
* @return True si la tabla correspondiente al modelo en BD's no existe y fue creada exitosamente,
* False si la tabla correspondiente al modelo ya existe en BD's
*/
test.crateTable();

~~~

![](Imagenes/Table2.jpg)

* * *

## ¿Cómo almacenar un modelo en BD's?

Para poder insertar un modelo en la tabla correspondiente al mismo en BD's únicamente necesitamos llamar
al método save(), una vez estemos seguros de que el modelo posee la información necesaria para insertar el registro.

~~~

/**
* Asignamos valores a las columnas del modelo, luego llamamos al método save(),
* el cual se encarga de insertar un registro en la tabla correspondiente al modelo con la información del mismo
* si este no existe, de existir actualiza el registro por medio de la clave primaria del modelo.
*/
test.getName().setValor("Jose");
test.getApellido().setValor("Bran");
/**
* En este primer ejemplo no seteamos un valor a la columna IsMayor, para comprobar que el valor
* por default configurado al crear la tabla, se este asignando a la respectiva columna.
*/
test.save();

/**
 * Si queremos utilizar el mismo modelo para insertar otro registro con valores diferentes,
 * es necesario que esperemos a que el modelo no este realizando ninguna tarea, relacionada con lectura o
 * escritura en la BD's, debido a que estas tareas JBSqlUtils las realiza en segundo plano, para no interrumpir
 * el hilo de ejecución principal y entregar un mejor rendimiento, por si necesitamos realizar alguna otra
 * instrucción mientras el modelo esta trabajando en segundo plano. para poder saber si el modelo actualmente esta
 * ocupado, podemos hacerlo a traves del método getTaskIsReady(), el cual obtiene la bandera que indica si
 * la tarea que estaba realizando el modelo ha sido terminada
 * @return True si el modelo actualmente no esta realizando una tarea. False si el modelo esta realizando una tarea
 * actualmente.
 * 
 * De utilizar otro modelo, no es necesario esperar a que el primer modelo este libre.
 * 
 */
while (!test.getTaskIsReady()){

}

/**
* Una vez hemos comprobado que el modelo no esta escribiendo u obteniendo información en segundo plano
* podemos utilizarlo, para insertar otro registro totalmente diferente al que insertamos anteriormente.
*/
test.getName().setValor("Daniel");
test.getApellido().setValor("Quiñonez");
test.getIsMayor().setValor(false);

/**
* Le indicamos a JBSqlUtils que de este segundo registro a insertar, no queremos que maneje
* las columnas created_at y updated_at.
*/
test.setTimestamps(false);

/**
* En este segundo ejemplo si seteamos un valor a la columna IsMayor, ya que no queremos que esta
* tenga el valor configurado por default para esta columna al momento de crear la tabla.
*/
test.save();

~~~

![](Imagenes/Insertar.jpg)

* * *

## ¿Cómo obtener un registro de BD's?

Para obtener un registro de BD's JBSqlUtils proporciona diferentes métodos los cuales veremos a continuación.

### Obtener el registro en el modelo que realiza la búsqueda.

~~~

/**
* Podemos obtener un registro de la tabla correspondiente al modelo en BD's a través del método get()
* el cual llena el modelo que realiza la invocación del método con la información obtenida.
*
* Para poder filtrar la búsqueda y tener acceso al método get(), es necesario que llamemos al método
* where() el cual nos proporciona un punto de entrada para otros métodos, por medio de los cuales podemos
* brindar una lógica un poco más compleja a la búsqueda del registro que deseamos obtener.
*/
test.where("name", Operator.LIKE, "Jos%").and("apellido", Operator.IGUAL_QUE, "Bran").get();

/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}

/**
* Mostramos la información obtenida
*/
LogsJB.info(test.getId().getValor()+"  "+test.getName().getValor()+"  "+test.getApellido().getValor()
  +"  "+test.getIsMayor().getValor()+"  "+test.getCreated_at().getValor()+"  "+test.getUpdated_at().getValor());

~~~

![](Imagenes/getmodel.jpg)

* * *

### Obtener el registro en un modelo diferente al modelo que realiza la búsqueda.

~~~

/**
* Podemos obtener un registro de la tabla correspondiente al modelo en BD's a través del método first()
* el cual obtiene un nuevo modelo del tipo que realiza la invocación del método con la información obtenida,
* únicamente casteamos el resultado al tipo de modelo que recibira la información.
*
* Para poder filtrar la búsqueda y tener acceso al método first(), es necesario que llamemos al método
* where() el cual nos proporciona un punto de entrada para otros métodos, por medio de los cuales podemos
* brindar una lógica un poco más compleja a la búsqueda del registro que deseamos obtener.
*/
Test test2= (Test) test.where("isMayor", Operator.IGUAL_QUE, false).first();

/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}
/**
* Mostramos la información obtenida
*/
LogsJB.info(test2.getId().getValor()+"  "+test2.getName().getValor()+"  "+test2.getApellido().getValor()
  +"  "+test2.getIsMayor().getValor()+"  "+test2.getCreated_at().getValor()+"  "+test2.getUpdated_at().getValor());

~~~

![](Imagenes/first.jpg)

Vemos que la columna created_at y update_at retornan cómo valor la fecha y hora actual, debido a que en BD's
estas no poseen un valor, entonces el valor de la columna es Null y por defecto el modelo retorna la fecha y
hora actual.

* * *

### Obtener el registro en un modelo, en caso de no encontrarlo lanzar una excepción.

Si lo que necesitamos es buscar un registro y obtenerlo en un modelo, pero en caso de no existir, deseamos se
dispare una excepción, podemos utilizar el método firstOrFail(), el cual lanza un ModelNotFound Excepción en caso
de no encontrar el registro para el modelo.

- En el siguiente Ejemplo obtendrá la información para el modelo

~~~

/**
* Podemos obtener un registro de la tabla correspondiente al modelo en BD's a través del método firstOrFail()
* el cual obtiene un nuevo modelo del tipo que realiza la invocación del método con la información obtenida,
* únicamente casteamos el resultado al tipo de modelo que recibira la información.
*
* En caso de no encontrar el registro que se desea obtener lanzara una excepción ModelNotFound, la cual
* nos indicará que no fue posible encontrar la información para el modelo.
*
* Para poder filtrar la busqueda y tener acceso al método firstOrFail(), es necesario que llamemos al método
* where() el cual nos proporciona un punto de entrada para otros métodos, por medio de los cuales podemos
* brindar una lógica un poco más compleja a la busqueda del registro que deseamos obtener.
*/
Test test2= (Test) test.where("Name", Operator.IGUAL_QUE, "Jose").firstOrFail();

/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}
/**
* Mostramos la información obtenida
*/
LogsJB.info(test2.getId().getValor()+"  "+test2.getName().getValor()+"  "+test2.getApellido().getValor()
  +"  "+test2.getIsMayor().getValor()+"  "+test2.getCreated_at().getValor()+"  "+test2.getUpdated_at().getValor());


~~~

Información en BD's SQLite

![](Imagenes/firstorfail.jpg)

Información obtenida por el modelo

![](Imagenes/firstorfail1.jpg)

- En el siguiente Ejemplo Lanzara la excepción ModelNotFound

~~~

/**
* Podemos obtener un registro de la tabla correspondiente al modelo en BD's a través del método firstOrFail()
* el cual obtiene un nuevo modelo del tipo que realiza la invocación del método con la información obtenida,
* únicamente casteamos el resultado al tipo de modelo que recibira la información.
*
* En caso de no encontrar el registro que se desea obtener lanzara una excepción ModelNotFound, la cual
* nos indicará que no fue posible encontrar la información para el modelo.
*
* Para poder filtrar la busqueda y tener acceso al método firstOrFail(), es necesario que llamemos al método
* where() el cual nos proporciona un punto de entrada para otros métodos, por medio de los cuales podemos
* brindar una lógica un poco más compleja a la busqueda del registro que deseamos obtener.
*/
Test test2= (Test) test.where("Name", Operator.IGUAL_QUE, "Jose").and("IsMayor", Operator.IGUAL_QUE, false).firstOrFail();


/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}
/**
* Mostramos la información obtenida
*/
LogsJB.info(test2.getId().getValor()+"  "+test2.getName().getValor()+"  "+test2.getApellido().getValor()
  +"  "+test2.getIsMayor().getValor()+"  "+test2.getCreated_at().getValor()+"  "+test2.getUpdated_at().getValor());


~~~

Información en BD's SQLite

![](Imagenes/firstorfail.jpg)

Excepción disparada, manejo la excepción para que me muestre la información y causa de la
misma.

![](Imagenes/firstorfail2.jpg)

* * *

## ¿Cómo obtener multiples registros de BD's?

Podemos obtener multiples registros de BD's a través de los siguientes métodos

- Método getALL()

Obtiene una lista de modelos que coinciden con la búsqueda realizada por medio de la consulta SQL

~~~

/**
* Declaramos una lista de modelos del tipo Test, en la cual almacenaremos la información obtenida de BD's'
*/
List<Test> lista=new ArrayList<>();

/**
* Obtenemos todos los registros cuyos Id son mayores a 2, el método getALL()
* Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
*/
lista=test.where("id", Operator.MAYOR_QUE, 2).getAll();

/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}

/**
* Declaramos una función anonima que recibira cómo parametro un obtjeto del tipo Test
* el cual es el tipo de modelo que obtendremos y dentro de esta función imprimiremos
* la información del modelo.
*/
Consumer<Test> showFilas = fila -> {
LogsJB.info(fila.getId().getValor()+"  "+fila.getName().getValor()+"  "+fila.getApellido().getValor()+"  "+fila.getIsMayor().getValor()+"  "+fila.getCreated_at().getValor()+"  "+fila.getUpdated_at().getValor());
};

/**
* Mostramos la información obtenida iterando sobre los modelos obtenidos de BD's y mostrando
* su contenido por medio de la función anonima que declaramos ateriormente.
*/
lista.forEach(showFilas);



~~~

Información en BD's SQLite

![](Imagenes/getall.jpg)

Lista de modelos obtenidos de BD's

![](Imagenes/getall1.jpg)

Si deseamos limitar la cantidad de resultados, podemos hacerlo a través del método take(Cantidad).get()
el cual retornara una lista de modelos coincidentes con la consulta SQL menor o igual a la cantidad especificada
en el método take, ya que la cantidad de registros obtenidos, depende de los registros que coinciden en BD's

- Método take()

Obtiene una lista de modelos que coinciden con la búsqueda realizada por medio de la consulta SQL, limitada
por la cantidad de registros especificados en el método take()

~~~

/**
* Declaramos una lista de modelos del tipo Test, en la cual almacenaremos la información obtenida de BD's'
*/
List<Test> lista=new ArrayList<>();

/**
* Obtenemos todos los registros cuyos Id son mayores a 2, el método take(Cantidad).get();
* Obtiene una lista de modelos que coinciden con la búsqueda realizada por medio de la consulta SQL
* limitada a la cantidad de registros especificados en el método take().
*/
lista=test.where("id", Operator.MAYOR_QUE, 2).take(2).get();

/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}

/**
* Declaramos una función anonima que recibira cómo parametro un obtjeto del tipo Test
* el cual es el tipo de modelo que obtendremos y dentro de esta función imprimiremos
* la información del modelo.
*/
Consumer<Test> showFilas = fila -> {
LogsJB.info(fila.getId().getValor()+"  "+fila.getName().getValor()+"  "+fila.getApellido().getValor()+"  "+fila.getIsMayor().getValor()+"  "+fila.getCreated_at().getValor()+"  "+fila.getUpdated_at().getValor());
};

/**
* Mostramos la información obtenida iterando sobre los modelos obtenidos de BD's y mostrando
* su contenido por medio de la función anonima que declaramos ateriormente.
*/
lista.forEach(showFilas);



~~~

Información en BD's SQLite

![](Imagenes/getall.jpg)

Lista de modelos obtenidos de BD's

![](Imagenes/take.jpg)

* * *

## ¿Cómo ordenar los registros obtenidos de BD's?

Podemos ordenar los registros que serán obtenidos de BD's por medio del método orderBy()

- Método orderBy()

Ordena los registros obtenidos de BD's de acuerdo a la columna que enviamos cómo parametro y el
tipo de ordenamiento que le especificamos.

~~~

/**
* Declaramos una lista de modelos del tipo Test, en la cual almacenaremos la información obtenida de BD's'
*/
List<Test> lista=new ArrayList<>();

/**
* Obtenemos todos los registros cuyos Id son mayores a 2, el método orderBy() los ordena de acuerdo a la columna
* que enviamos cómo parametro y el tipo de ordenamiento que le especificamos.
* El método orderBy() proporciona acceso a todos los métodos que hemos visto anteriormente, los cuales nos
* permiten obtener uno o multiples registros, de acuerdo a la lógica que brindemos a nuestra sentencia SQL.
*/
lista=test.where("id", Operator.MAYOR_QUE, 2).orderBy("id", OrderType.DESC).take(2).get();

/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}

/**
* Declaramos una función anonima que recibira cómo parametro un obtjeto del tipo Test
* el cual es el tipo de modelo que obtendremos y dentro de esta función imprimiremos
* la información del modelo.
*/
Consumer<Test> showFilas = fila -> {
LogsJB.info(fila.getId().getValor()+"  "+fila.getName().getValor()+"  "+fila.getApellido().getValor()+"  "+fila.getIsMayor().getValor()+"  "+fila.getCreated_at().getValor()+"  "+fila.getUpdated_at().getValor());
};

/**
* Mostramos la información obtenida iterando sobre los modelos obtenidos de BD's y mostrando
* su contenido por medio de la función anonima que declaramos ateriormente.
*/
lista.forEach(showFilas);



~~~

Información en BD's SQLite

![](Imagenes/getall.jpg)

Lista de modelos obtenidos de BD's

![](Imagenes/orderBy.jpg)

* * *

## ¿Cómo actualizar un Modelo en BD's?

Podemos actualizar un modelo que hayamos obtenido de BD's, a través del método save(), es importante
que para que se actualice el registro, este haya sido obtenido de BD's, de esa manera el modelo tendrá
un valor válido en su atributo correspondiente a la primaryKey de la tabla en BD's y la propiedad ModelExist
estará configurada con un valor true.

En caso no hayamos obtenido el modelo de BD's es importante que configuremos la propiedad ModelExist cómo true,
lo cual podemos hacerlo a través del método setModelExist(), adicional a esto, debemos asegurarnos, de que el
modelo en su columna correspondiente a la primaryKey, tenga el valor del registro que queremos actualizar.

~~~

/**
*Obtenemos el registro que coincide con la sentencia SQL generada por el modelo
*/
test.where("Apellido", Operator.IGUAL_QUE, "Cabrera").and("IsMayor", Operator.IGUAL_QUE, false).get();

/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}
/**
* Mostramos la información obtenida
*/
LogsJB.info(test.getId().getValor()+"  "+test.getName().getValor()+"  "+test.getApellido().getValor()
  +"  "+test.getIsMayor().getValor()+"  "+test.getCreated_at().getValor()+"  "+test.getUpdated_at().getValor());


/**
* Modificamos el valor de la columna IsMayor a true
*/
test.getIsMayor().setValor(true);

/**
* LLamamos al método save, el cual se encargará de actualizar el registro en BD's.
*/
test.save();

~~~

Información en BD's SQLite antes de actualizar el registro

![](Imagenes/update.jpg)

Actividad registrada por JBSqlUtils

![](Imagenes/update1.jpg)

Información en BD's SQLite después de actualizar el registro

![](Imagenes/update2.jpg)

* * *

## ¿Cómo actualizar multiples Modelos en BD's?

Podemos actualizar multiples modelos que hayamos obtenido de BD's, a través del método saveALL(), es importante
que para que se actualice el registro, este haya sido obtenido de BD's, de esa manera el modelo tendrá
un valor válido en su atributo correspondiente a la primaryKey de la tabla en BD's y la propiedad ModelExist
estará configurada con un valor true.

En caso no hayamos obtenido los modelos de BD's es importante que configuremos la propiedad ModelExist cómo true en
cada uno de los modelos que vayamos a actualizar, lo cual podemos hacerlo a través del método setModelExist(),
adicional a esto, debemos asegurarnos, de que cada uno de los modelos en su columna correspondiente a la primaryKey,
tenga el valor del registro que queremos actualizar.

~~~

/**
* Declaramos una lista de modelos del tipo Test, en la cual almacenaremos la información obtenida de BD's'
*/
List<Test> lista=new ArrayList<>();

/**
* Obtenemos todos los modelos que su Id se encuentra entre 1 y 5
*/
lista=test.where("id", Operator.MAYOR_IGUAL_QUE, 1).and("id", Operator.MENOR_IGUAL_QUE, 5).getAll();

/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}

/**
* Declaramos una función anonima que recibira cómo parametro un obtjeto del tipo Test
* el cual es el tipo de modelo que obtendremos y dentro de esta función imprimiremos
* la información del modelo y modificaremos el valor de la columna Is mayor, de ser True
* ahora será False, de ser False, ahora será True.
*/
Consumer<Test> showFilas = fila -> {
//Mostramos la Información     
LogsJB.info(fila.getId().getValor()+"  "+fila.getName().getValor()+"  "+fila.getApellido().getValor()+"  "+fila.getIsMayor().getValor()+"  "+fila.getCreated_at().getValor()+"  "+fila.getUpdated_at().getValor());
//Modificamos el valor de la columna IsMayor
fila.getIsMayor().setValor(!fila.getIsMayor().getValor());
};

/**
* Mostramos la información obtenida iterando sobre los modelos obtenidos de BD's y mostrando
* su contenido por medio de la función anonima que declaramos ateriormente.
*/
lista.forEach(showFilas);


/**
* Almacena la información de los modelos proporcionados en BD's
* @param modelos Lista de modelos que serán Insertados o Actualizados
*/
test.saveALL(lista);
    

~~~

Información en BD's SQLite antes de actualizar los registros

![](Imagenes/updateall.jpg)

Actividad registrada por JBSqlUtils, Cómo JBSqlUtils ejecuta las operaciones de escritura cómo de lectura en segundo
plano
para que el hilo de ejecución principal no se vea afectado y pueda realizar alguna otra actividad en paralelo, por cada
modelo
que se actualiza, JBSqlUtils crea un subproceso para cada modelo, de esta manera las operaciones de escritura en BD's
se realizan en paralelo, mejorando el rendimiento de nuestra aplicación.

Desde que se hizo el llamado al método saveAll(), hasta que se actualizó el último registro a JBSqlUtils le tomo
40 milésimas de segundo actualizar 5 registros en BD's, es un tiempo insignificante, considerando, que se realizó la
conexión a BD's, se fabricó la sentencia SQL a ejecutar, se preparó el preparedStatement, se ejecutó la instrucción y
cerro la conexión a BD's.

![](Imagenes/updateall1.jpg)

Información en BD's SQLite después de actualizar los registros

![](Imagenes/updateall2.jpg)

* * *

## ¿Cómo eliminar un Modelo en BD's?

Podemos eliminar un modelo que hayamos obtenido de BD's, a través del método delete(), es importante
que para que se elimine el registro, este haya sido obtenido de BD's, de esa manera el modelo tendrá
un valor válido en su atributo correspondiente a la primaryKey de la tabla en BD's.

En caso no hayamos obtenido el modelo de BD's es importante asegurarnos de que el
modelo en su columna correspondiente a la primaryKey, tenga el valor del registro que queremos eliminar.

~~~

/**
*Obtenemos el registro que coincide con la sentencia SQL generada por el modelo
*/
test.where("Apellido", Operator.IGUAL_QUE, "Cabrera").and("Name", Operator.IGUAL_QUE, "Marleny").get();

/**
* Esperamos a que el modelo termine de obtener la información de BD's
*/
while (!test.getTaskIsReady()){

}
/**
* Mostramos la información obtenida
*/
LogsJB.info(test.getId().getValor()+"  "+test.getName().getValor()+"  "+test.getApellido().getValor()
  +"  "+test.getIsMayor().getValor()+"  "+test.getCreated_at().getValor()+"  "+test.getUpdated_at().getValor());

/**
* LLamamos al metodo delete, el cual se encargará de eliminar el registro en BD's.
*/
test.delete();

~~~

Información en BD's SQLite antes de eliminar el registro

![](Imagenes/delete.jpg)

Actividad registrada por JBSqlUtils

![](Imagenes/delete1.jpg)

Información en BD's SQLite después de eliminar el registro

![](Imagenes/delete2.jpg)

* * *

## ¿Cómo eliminar multiples Modelos en BD's?

Podemos eliminar multiples modelos que hayamos obtenido de BD's, a través del método deleteALL(), es importante
que para que se elimine el registro, este haya sido obtenido de BD's, de esa manera el modelo tendrá
un valor válido en su atributo correspondiente a la primaryKey de la tabla en BD's.

En caso no hayamos obtenido los modelos de BD's es importante asegurarnos de que cada uno de los
modelos que queremos eliminar tengan en su columna correspondiente a la primaryKey, el valor del registro que queremos
eliminar.

~~~

    

~~~

Información en BD's SQLite antes de eliminar los registros

![](Imagenes/deleteall.jpg)

Actividad registrada por JBSqlUtils, Cómo JBSqlUtils ejecuta las operaciones de escritura cómo de lectura en segundo
plano
para que el hilo de ejecución principal no se vea afectado y pueda realizar alguna otra actividad en paralelo, por cada
modelo
que se elimina, JBSqlUtils crea un subproceso para cada modelo, de esta manera las operaciones de escritura en BD's
se realizan en paralelo, mejorando el rendimiento de nuestra aplicación.

Desde que se hizo el llamado al método deleteALL(), hasta que se eliminó el último registro a JBSqlUtils le tomo
60 milésimas de segundo eliminar 5 registros en BD's, es un tiempo insignificante, considerando, que se realizó la
conexión a BD's, se fabricó la sentencia SQL a ejecutar, se preparó el preparedStatement, se ejecutó la instrucción y
cerro la conexión a BD's.

![](Imagenes/deleteall1.jpg)

Información en BD's SQLite después de eliminar los registros

![](Imagenes/deleteall2.jpg)

* * *

## ¿Cómo poder hacer un seguimiento a lo que sucede dentro de JBSqlUtils?

JBSqlUtils utiliza la librería LogsJB, para el registro de todo lo que sucede al momento
de realizar una inserción, actualización, consulta o eliminar un registro en BD's, por default se
registra toda aquella actividad de nivel INFO y superior, si desea debuggear o modificar el nivel
de log que reporta JBSqlUtils será necesario que importe en su proyecto la librería LogsJB y
llame al método LogsJB.setGradeLog(), enviando cómo parametro el grado de Log, desde el cual
desea que JBSqlUtils registre su actividad.

Puedes obtener la librería LogsJB de la siguiente manera

Maven

~~~
<!-- Dependencia para el manejo de los Logs -->
    <dependency>
        <groupId>io.github.josecarlosbran</groupId>
        <artifactId>LogsJBSupport</artifactId>
        <version>0.2</version>
        <scope>compile</scope>
    </dependency>
~~~

Gradle

~~~
implementation 'io.github.josecarlosbran:LogsJBSupport:0.2'
~~~

Modificar el Nivel de Log que queremos tener sobre JBSqlUtils

~~~

/***
* Setea el NivelLog desde el cual deseamos se escriba en el Log de la aplicación actual.
* @param GradeLog Nivel Log desde el cual hacía arriba en la jerarquia de logs, deseamos se reporten
*   * Trace = 200,
*   * Debug = 400,
*   * Info = 500,
*   * Warning = 600,
*   * Error = 800,
*   * Fatal = 1000.
* El valor por defaul es Info. Lo cual hace que se reporten los Logs de grado Info, Warning, Error y Fatal.
*/
LogsJB.setGradeLog(NivelLog.INFO);

~~~

Encontraremos los Logs de JBSqlUtils en el directorio de nuestra aplicación en ejecución, se creará la carpeta
Logs, dentro de la cual se creara una carpeta por cada día y dentro de la misma se almacenaran los Logs de la
aplicación, para mayor información visitar el siguiente Link

<https://github.com/JoseCarlosBran/LogsJB/blob/master/Readme.md>

![](Imagenes/Logs.jpg)

* * *

## ¿Cómo obtener JBSqlUtils para usarlo en mi proyecto?

Puedes obtener la librería JBSqlUtils de la siguiente manera

Maven

~~~
<dependency>
  <groupId>io.github.josecarlosbran</groupId>
  <artifactId>JBSqlUtils</artifactId>
  <version>1.1.4.5</version>
</dependency>
~~~

Gradle

~~~
implementation 'io.github.josecarlosbran:JBSqlUtils:1.1.4.5'
~~~

Para mayor información sobre cómo descargar JBSqlUtils desde otros
administradores de paquetes, puedes ir al siguiente Link
<https://search.maven.org/artifact/io.github.josecarlosbran/JBSqlUtils>

***

## Licencia :balance_scale:

JBSqlUtils es un ORM open source desarrollado por José Bran, para gestionar BD's SQLite,
MySQL, PostgreSQL y SQLServer, de una manera fácil y rápida, con licencia de Apache License, Versión 2.0;

No puede usar este ORM excepto de conformidad con la Licencia.
Puede obtener una copia de la Licencia en http://www.apache.org/licenses/LICENSE-2.0

A menos que lo exija la ley aplicable o se acuerde por escrito, el software
distribuido bajo la Licencia se distribuye "TAL CUAL",
SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
Consulte la Licencia para conocer el idioma específico que rige los permisos y
limitaciones bajo la Licencia.

***

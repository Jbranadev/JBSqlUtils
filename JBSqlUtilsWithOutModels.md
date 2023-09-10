# ¿Cómo utilizar JBSqlUtils sin necesidad de crear un Modelo?

JBSqlUtils puede ser utilizada cómo un generador de sentencias SQL, para Crear, Eliminar tablas,
Insertar, Actualizar, Eliminar o Seleccionar registros en
una determinada tabla de forma masiva, de acuerdo a la lógica que se le dé a la consulta.
***

## Crear una tabla.

Para crear una tabla utilizamos el método createTable despues de haber definido el nombre de la tabla que deseamos crear
y las columnas que deseamos tenga nuestra tabla.

~~~
/**
* Definimos las columnas que deseamos posea nuestra tabla 
*/
 
/**
* Para poder utilizar JBSqlUtils es necesario que los miembros de la clase modelo, que correspondan
* a una columna de la tabla correspondiente al modelo, sean del tipo Column, especificando el tipo de dato
* en java y por medio del constructor del objeto Column se pase cómo parámetro el tipo de dato SQL
* de la columna, adicional a esto se pueden definir restricciones, cómo valor por defecto para la columna 
* para crear la tabla en BD's, pero estos últimos son opcionales
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
* Declara un miembro de la tabla que deseamos crear, el cual en java almacenara un dato de tipo Integer, se define Integer,
* ya que la clase Column es una clase generica y no puede trabajar con datos primivitos cómo int, pero si con
* clases contenedoras cómo Integer.
*
* En el constructor mandamos como primer parámetro el nombre que deseamos tenga la columna.
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
* En el constructor mandamos como primer parámetro el nombre que deseamos tenga la columna.
*
* En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
* Varchar.
*/
Column<String> Name = new Column<>("Name", DataType.VARCHAR);

/**
* Declara un miembro de la tabla, el cual en java almacenara un dato de tipo String.
* <p>
*
* En el constructor mandamos como primer parámetro el nombre que deseamos tenga la columna.
*
* En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
* Varchar.
*/
Column<String> Apellido = new Column<>("Apellido",DataType.VARCHAR);

/**
* Declara un miembro del modelo, el cual en java almacenara un dato de tipo Boolean.
* <p>
*
* En el constructor mandamos como primer parámetro el nombre que deseamos tenga la columna.
*
* En el constructor indicamos que el tipo de dato SQL de la columna correspondiente a este miembro es de tipo
* BOOLEAN.
* <p>
* En este ejemplo seteamos 'true' como default_value, debido a que este modelo se conectara a un SQLServer,
* en PostgreSQL la sintaxis es true. Por lo cual es importante tener claro la sintaxis de la BD's a la cual
* se estará conectando el modelo.
* <p>
* Agregamos una restriccion SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
* desde nuestra aplicación en caso esta no exista a través del método modelo.crateTable(), de lo contrario no es necesario que agreguemos restricciones.
*/
Column<Boolean> Estado = new Column<>("Estado", DataType.BOOLEAN, "true", Constraint.DEFAULT);
Name.setSize("1000");
Apellido.setSize("1000");

/**
* Para crear una tabla utilizamos el método createTable despues de haber definido el nombre de la tabla que deseamos Crear 
* y las columnas que deseamos tenga nuestra tabla
*/
JBSqlUtils.createTable("Proveedor").addColumn(Name).addColumn(Id).addColumn(Apellido).addColumn(Estado).createTable();
~~~

***

## Eliminar una tabla.

Para eliminar una tabla de BD's utilizamos el método execute de la clase dropTableIfExist a la cual mandamos como
parámetro
el nombre de la tabla que queremos eliminar.

~~~
/**
* Para eliminar una tabla de BD's utilizamos el método execute de la clase dropTableIfExist a la cual mandamos como parámetro
* el nombre de la tabla que queremos eliminar.
*/
JBSqlUtils.dropTableIfExist("Proveedor").execute();
~~~

***

## Insertar Registros en una tabla.

Para insertar registros hacemos uso del método execute que está disponible en la clase value y andValue a las cuales
podemos acceder
a través de la clase insertInto a la cual enviamos como parámetro el nombre de la tabla a la que queremos insertar, a
través de los metodos value
y andValue definimos los valores que queremos insertar en determinada columna, el método execute retorna la cantidad de
registros insertados.

De suceder algún error durante la ejecución de la sentencia insertInto retorna 0, de lo contrario retorna 1, ya que solo
se puede insertar un registro a la vez.

~~~
/**
* Para insertar registros hacemos uso del método execute que está disponible en la clase value y andValue a las cuales podemos acceder 
* a través de la clase insertInto a la cual enviamos como parámetro el nombre de la tabla a la que queremos insertar, a través de los metodos value
* y andValue definimos los valores que queremos insertar en determinada columna.
*/
int registros=JBSqlUtils.insertInto("Proveedor").value("Name", "Daniel").andValue("Apellido", "Quiñonez").andValue("Estado", false).execute();
~~~

***

## Seleccionar registros.

Para obtener los registros de una tabla de BD's podemos hacerlo a través del método select envíando como parametro
el nombre de la tabla de la cual deseamos obtener los registros, así mismo podemos filtrar los resultados a través del
método where el cual proporciona acceso a metodos por medio de los cuales podemos filtrar los resultados.

Los resultados serán devueltos en una List<JSONObject> del tipo JSONObject de
la implementación json de org.json

~~~
/**
* Si deseamos obtener todas las columnas de la tabla envíamos el parametro columnas del método 
* getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parametro 
* del método select
*/
List<String> columnas=null;

/**
* Si deseamos obtener unicamente determinadas columnas, es necesario envíar como parametro una lista de strings
* con los nombres de las columnas que deseamos obtener del método getInJsonObjects
*/
columnas= new ArrayList<>();
columnas.add("Id");
columnas.add("Name");

/**
* Para obtener los registros de una tabla de BD's podemos hacerlo a través del método select envíando como parametro
* el nombre de la tabla de la cual deseamos obtener los registros, así mismo podemos filtrar los resultados a través del método 
* where el cual proporciona acceso a metodos por medio de los cuales podemos filtrar los resultados.
*/
List<JSONObject> lista=JBSqlUtils.select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
    .and("Apellido", Operator.LIKE, "%m%").take(3).getInJsonObjects(columnas);

/**
* Imprimimos los registros obtenidos
*/
lista.forEach( fila -> {
LogsJB.info(fila.toString());
});
~~~

***

## Actualizar registros.

Para actualizar registros sin necesidad de instanciar un modelo, puede hacerlo a través del
update método estático de la clase JBSqlUtils, el cual brinda los metodos necesarios, para poder
llegar al método execute, el cual ejecuta la sentencia SQL generada y retorna el número de
filas afectadas por la ejecución de la sentencia SQL.

~~~
/**
 * Actualizar todas las filas de una tabla X (Test), senteando un valor Y(Jose Carlos) a una columna Z(name).
 * El método update recibe cómo parámetro el nombre de la tabla que se desea actualizar y proporciona acceso
 * al método set el cual recibe cómo primer parámetro el nombre de la columna que se desea modificar y el valor
 * que se desea setear a la columna, el método set proporciona acceso al método execute el cual se encarga de
 * ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
 */
int rows_afected=JBSqlUtils.update("Test").set("name", "Jose Carlos").execute();

/**
 * Podemos agregar una sentencia Where, por medio del cual podemos acceder a los metodos necesarios para
 * filtrar la cantidad de filas que queremos modificar, una vez hemos terminado de brindar la lógica hacemos el
 * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
 * afectadas.
 */
rows_afected=JBSqlUtils.update("Test").set("name", "Jose Carlos").where("Id", Operator.MAYOR_QUE, 2).and("apellido", Operator.LIKE, "Bran%").execute();

/**
 * Podemos actualizar mas de una columna a través del método andSet, el cual nos proporciona la capacidad de
 * modificar el valor de otra columna y acceso a los metodos andSet para setear otro valor a otra columna y el método
 * where por medio del cual podemos filtrar las filas que se veran afectadas al llamar al método execute, el cual
 * se encargara de ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
 */
rows_afected=JBSqlUtils.update("Test").set("name", "Jose Carlos").andSet("IsMayor", true).execute();
~~~

***

## Eliminar registros.

Para eliminar registros sin necesidad de instanciar un modelo, puede hacerlo a través del
delete método estático de la clase JBSqlUtils, el cual brinda los metodos necesarios, para poder
llegar al método execute, el cual ejecuta la sentencia SQL generada y retorna el número de
filas afectadas por la ejecución de la sentencia SQL.

~~~

/**
 * Eliminar todas las filas de una tabla X (Test), donde la columna Y(Id) tiene un valor MAYOR O IGUAL a Z(2).
 * El método delete recibe cómo parámetro el nombre de la tabla que se desea eliminar registros y proporciona acceso
 * al método Where, por medio del cual podemos acceder a los metodos necesarios para
 * filtrar la cantidad de filas que queremos eliminar, una vez hemos terminado de brindar la lógica hacemos el
 * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
 * afectadas.
 */
int rows_afected=JBSqlUtils.delete("Test").where("Id", Operator.MAYOR_IGUAL_QUE, 2).execute();

~~~

***
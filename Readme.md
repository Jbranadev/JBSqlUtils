# JBSqlUtils :computer:

JBSqlUtils es una librería java que permite gestionar BD's SQLite, MySQL, PostgreSQL y SQLServer, de una manera fácil y rápida
sin interrumpir la ejecución del hilo principal del programa, lo cual la hace un potente ORM, por medio del cual tendrá
acceso a un CRUD, configurando únicamente la conexión del modelo, los atributos que posee la tabla en BD's como
variables que pertenecerán al modelo en su aplicación.

JBSqlUtils también proporciona un potente generador de consultas que le permitira actualizar o eliminar registros
de una tabla en su BD's sin necesidad de instanciar un objeto como tal, unicamente tendrá que configurar previamente
la conexión a su BD's.
* * *

## Estado del Proyecto :atom:

JBSqlUtils actualmente está en una etapa de desarrollo continuo, por lo cual sus observaciones y recomendaciones,
son bienvenidas para mejorar el proyecto.
***

## Configuración :gear:

Utilizar JBSqlUtils es muy fácil.

### Configurar variables de conexión globales

- Lo primero es setear la variables globales de conexión

Al setear las variables globales de conexión estas se almacenan como variables del sistema
del entorno de ejecución de la aplicación, las cuales no pueden ser accedidas más que por la misma aplicación
que las configuro y se eliminan, cuando la aplicación termina su ejecución.

Configuración necesaria para SQLite:

~~~ 
/*En este ejemplo, se estaría utilizando una BD's SQLite en el directorio de la aplicación, dentro
del cual existe una carpeta llamada BD y dentro de esta carpeta se crearía la BD's JBSqlUtils al establecer
la conexión si esta no existiera, de existir, unicamente se establecería la conexión.
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
 * Setea el tipo de BD's global a la cual se estaran conectando los modelos que no tengan una configuración personalizada.
 * @param dataBase Tipo de BD's a la cual se estaran los modelos que no tengan una configuración personalizada, los tipos disponibles son
 *                 MySQL,
 *                 SQLServer,
 *                 PostgreSQL,
 *                 SQLite.
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
 * Setea el tipo de BD's global a la cual se estaran conectando los modelos que no tengan una configuración personalizada.
 * @param dataBase Tipo de BD's a la cual se estaran los modelos que no tengan una configuración personalizada, los tipos disponibles son
 *                 MySQL,
 *                 SQLServer,
 *                 PostgreSQL,
 *                 SQLite.
 */
public static void setDataBaseTypeGlobal(DataBase dataBase);
~~~

Al haber configurado las variables de conexión globales, su aplicación estara lista
para instanciar objetos que hereden la clase JBSqlUtils, los cuales fungiran como modelos y le permitiran
realizar operaciones sobre la tabla correspondiente a cada modelo.

De no desear usar Modelos en su aplicación hasta este punto, podra utilizar JBSqlUtils como un generador de
sentencias SQL que le permitiran actualizar o eliminar registros de una tabla de acuerdo a la logica que brinde a la
sentencia SQL a ejecutar, todo esto sin necesidad de instanciar un modelo, unicamente habiendo configurado sus 
variables de conexión.

***

## ¿Como utilizar JBSqlUtils como un generador de consultas?

JBSqlUtils puede ser utilizada como un generador de consultas, para actualizar o eliminar registros en 
una determinada tabla de forma masiva, de acuerdo a la lógica que se le de a la consulta.

- Actualizar registros.

Para actualizar registros sin necesidad de instanciar un modelo, puede hacerlo a través  del 
update método estático de la clase JBSqlUtils, el cual brinda los métodos necesarios, para poder 
llegar al método execute, el cual ejecuta la sentencia SQL generada y retorna el número de 
filas afectadas por la ejecución de la sentencia SQL.

~~~

/**
 * Actualizar todas las filas de una tabla X (Test), senteando un valor Y(Jose Carlos) a una columna Z(name).
 * El método update recibe como parametro el nombre de la tabla que se desea actualizar y proporciona acceso
 * al método set el cual recibe como primer parametro el nombre de la columna que se desea modificar y el valor
 * que se desea setear a la columna, el método set proporciona acceso al método execute el cual se encarga de
 * ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
 */
int rows_afected=update("Test").set("name", "Jose Carlos").execute();

/**
 * Podemos agregar una sentencia Where, por medio del cual podemos acceder a los métodos necesarios para
 * filtrar la cantidad de filas que queremos modificar, una vez hemos terminado de brindar la logica hacemos el
 * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
 * afectadas.
 */
rows_afected=update("Test").set("name", "Jose Carlos").where("Id", Operator.MAYOR_QUE, "2").and("apellido", Operator.LIKE, "Bran%").execute();


/**
 * Podemos actualizar mas de una columna a través  del método andSet, el cual nos proporciona la capacidad de
 * modificar el valor de otra columna y acceso a los métodos andSet para setear otro valor a otra columna y el método
 * where por medio del cual podemos filtrar las filas que se veran afectadas al llamar al método execute, el cual
 * se encargara de ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
 */
rows_afected=update("Test").set("name", "Jose Carlos").andSet("IsMayor", "true").execute();

~~~

- Eliminar registros.

Para eliminar registros sin necesidad de instanciar un modelo, puede hacerlo a través del
delete método estático de la clase JBSqlUtils, el cual brinda los métodos necesarios, para poder
llegar al método execute, el cual ejecuta la sentencia SQL generada y retorna el número de
filas afectadas por la ejecución de la sentencia SQL.

~~~

/**
 * Eliminar todas las filas de una tabla X (Test), donde la columna Y(Id) tiene un valor MAYOR O IGUAL a Z(2).
 * El método delete recibe como parametro el nombre de la tabla que se desea eliminar registros y proporciona acceso
 * al método Where, por medio del cual podemos acceder a los métodos necesarios para
 * filtrar la cantidad de filas que queremos eliminar, una vez hemos terminado de brindar la lógica hacemos el
 * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
 * afectadas.
 */
int rows_afected=delete("Test").where("Id", Operator.MAYOR_IGUAL_QUE, "2").execute();

~~~

* * *

## ¿Cómo Crear y utilizar modelos a través de JBSqlUtils?

Para poder crear clases que funcionen como modelos en nuestra aplicación, unicamente es necesario heredar la clase
JBSqlUtils



* * *


## ¿Cómo Obtener JBSqlUtils para usarlo en mi proyecto?

Puedes obtener la librería JBSqlUtils de la siguiente manera

Maven

~~~
<dependency>
    <groupId>io.github.josecarlosbran</groupId>
    <artifactId>JBSqlUtils</artifactId>
    <version>1.0</version>
</dependency>
~~~

Gradle

~~~
implementation 'io.github.josecarlosbran:JBSqlUtils:1.0'
~~~

Para mayor información sobre como descargar JBSqlUtils desde otros
administradores de paquetes, puedes ir al siguiente Link
<https://search.maven.org/artifact/io.github.josecarlosbran/JBSqlUtils>

***

## Licencia :balance_scale:

JBSqlUtils es una librería open source desarrollada por José Bran, para la administración
de los registros de un programa, con licencia de Apache License, Versión 2.0;

No puede usar esta librería excepto de conformidad con la Licencia.
Puede obtener una copia de la Licencia en http://www.apache.org/licenses/LICENSE-2.0

A menos que lo exija la ley aplicable o se acuerde por escrito, el software
distribuido bajo la Licencia se distribuye "TAL CUAL",
SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
Consulte la Licencia para conocer el idioma específico que rige los permisos y
limitaciones bajo la Licencia.

***

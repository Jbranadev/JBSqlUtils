# JBSqlUtils :computer:

JBSqlUtiLs es una librería java que permite crear modelos de BD's en su aplicación por medio de los cuales tendra
acceso a un CRUD, configurando únicamente la conexión del modelo, los atributos que posee la tabla en BD's como
variables que pertenecerán al modelo en su aplicación.

JBSqlUtils también proporciona un potente generador de consultas que le permitira actualizar o eliminar registros
de una tabla en su BD's sin necesidad de instanciar un objeto como tal, unicamente tendra que configurar previamente
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

### ¿Configuración de JBSqlUtils de acuerdo a las necesidades de mi implementación?

JBSqlUtils puede ser configurada de acuerdo a las necesidades de la implementación que usted esté realizando.

- Modificar la ruta de almacenamiento de los registros.

Usted puede modificar la ruta de almacenamiento de los registros de su implementación de la siguiente manera.

~~~
/**
 * Setea la ruta en la cual se desea que escriba el Log.
 * @param Ruta Ruta del archivo .Txt donde se desea escribir el Log.
 */
JBSqlUtils.setRuta(Ruta);
~~~

- Modificar el tamaño máximo que puede tener su archivo de registros.

Usted puede modificar el tamaño que desea que tenga cada archivo de registros de su implementación.

~~~
/***
 * Setea el tamaño maximo para el archivo Log de la aplicación actual.
 * @param SizeLog Tamaño maximo del archivo sobre el cual se estara escribiendo el Log.
 *      * Little_Little = 125Mb,
 *      * Little = 250Mb,
 *      * Small_Medium = 500Mb,
 *      * Medium = 1,000Mb,
 *      * Small_Large = 2,000Mb,
 *      * Large = 4,000Mb.
 * El valor por defaul es Little_Little.
 */
JBSqlUtils.setSizeLog(SizeLog.Little_Little);
~~~

- Modificar el grado de registros que se estarán reportando.

~~~
/***
 * Setea el NivelLog desde el cual deseamos se escriba en el Log de la aplicación actual.
 * @param GradeLog Nivel Log desde el cual hacía arriba en la jerarquia de logs, deseamos se reporten
 *      * Trace = 200,
 *      * Debug = 400,
 *      * Info = 500,
 *      * Warning = 600,
 *      * Error = 800,
 *      * Fatal = 1000.
 * El valor por defaul es Info. Lo cual hace que se reporten los Logs de grado Info, Warning, Error y Fatal.
 */
JBSqlUtils.setGradeLog(NivelLog.INFO);
~~~

- Modificar el usuario que se graba en el registro.

~~~
/***
 * Setea el nombre del usuario del sistema sobre el cual corre la aplicación
 * @param Usuario Usuario actual del sistema que se desea indicar al Log.
 */
JBSqlUtils.setUsuario(Usuario);
~~~

* * *

## ¿Cómo usar JBSqlUtils?

Usar JBSqlUtils es más fácil que hacer un llamado a System.out.println(mensaje), ya que al llamar a los métodos de
registro
de JBSqlUtils se escribe el mensaje en la salida de la terminal del programa y en el archivo Log.txt, con menos esfuerzo
del necesario
para hacer un System.out.println(mensaje).

~~~
/**
* Una vez se a importado los métodos estáticos de JBSqlUtils
* Se puede hacer el llamado invocando al método estático de las siguientes dos maneras:
* JBSqlUtils.debug(Mensaje);
* debug(Mensaje);
* @param Mensaje es un String que indica el mensaje que queremos registrar en la salida de la terminal,
* como en el archivo Logs.txt
*/
 
//Comentario grado Trace
trace( "Primer comentario grado Trace");
//Comentario grado Debug
debug( "Primer comentario grado Debug");
//Comentario grado Info
info( "Primer comentario grado Info");
//Comentario grado Warning
warning( "Primer comentario grado Warning");
//Comentario grado Error
error( "Primer comentario grado Error");
//Comentario grado Fatal
fatal( "Primer comentario grado Fatal"); 
~~~

Salida en la terminal
![](Imagenes/Terminal_output.png)

Salida en Log.txt
![](Imagenes/Txt_output.png)


* * *

## ¿Cómo Obtener JBSqlUtils para usarlo en mi proyecto?

Puedes obtener la librería JBSqlUtils de la siguiente manera

Maven

~~~
<dependency>
    <groupId>io.github.josecarlosbran</groupId>
    <artifactId>JBSqlUtils</artifactId>
    <version>0.5</version>
</dependency>
~~~

Gradle

~~~
implementation 'io.github.josecarlosbran:JBSqlUtils:0.5'
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

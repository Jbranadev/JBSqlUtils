
# Configuración Global :gear:

Utilizar JBSqlUtils es muy fácil.

## Configurar variables de conexión globales

- Lo primero es setear las variables globales de conexión

Al setear las variables globales de conexión estas se almacenan cómo variables del sistema
del entorno de ejecución de la aplicación, las cuales no pueden ser accedidas más que por
la misma aplicación que las configuro y se eliminan, cuando la aplicación termina su ejecución.
***
### Configuración necesaria para SQLite:

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
JBSqlUtils.setDataBaseGlobal(BDSqlite);

/**
 * Setea el tipo de BD's global a la cual se estarán conectando los modelos que no tengan una configuración personalizada.
 * @param dataBase Tipo de BD's a la cual se estarán los modelos que no tengan una configuración personalizada, los tipos disponibles son
 *         MySQL,
 *         SQLServer,
 *         PostgreSQL,
 *         SQLite.
 */
JBSqlUtils.setDataBaseTypeGlobal(DataBase.SQLite);
~~~
***
### Configuración necesaria para MySQL, PostgreSQL y SQLServer:

~~~ 
/*En este ejemplo, se estaría utilizando una BD's que tiene que existir en el servidor con las
caracteristicas especificadas.
*/ 
   
/**
 * Setea el nombre de la Base de Datos global a la que se conectaran los modelos que no tengan una configuración
 * personalizada.
 * @param BD Nombre de la Base de Datos.
 */
JBSqlUtils.setDataBaseGlobal(String BD);

/**
 * Setea la Contraseña del usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
 * @param password Contraseña del usuario con el cual se conectara a la BD's.
 */
JBSqlUtils.setPasswordGlobal(String password);

/**
 * Setea el Usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
 * @param user Usuario con el cual se conectara a la BD's.
 */
JBSqlUtils.setUserGlobal(String user);

/**
 * Setea el puerto global con el que se conectaran los modelos que no tengan una configuración personalizada.
 * @param port Puerto en el cual se encuentra escuchando la BD's a la cual se pegaran los modelos.
 */
JBSqlUtils.setPortGlobal(String port);

/**
 * Setea el host en el cual se encuentra la BD's global a la cual se conectaran los modelos que no tengan una configuración personalizada.
 * @param host Host en el cual se encuentra la BD's a la que nos queremos conectar.
 */
JBSqlUtils.setHostGlobal(String host);

/**
* Setea las propiedades extra de conexión url DB que pueden utilizar los modelos para conectarse a BD's
*
* @param propertisUrl Propiedades extra para la url de conexión a BD's por ejemplo
*                     ?autoReconnect=true&useSSL=false
*/
JBSqlUtils.setPropertisUrlConexionGlobal("?autoReconnect=true&useSSL=false");     

/**
 * Setea el tipo de BD's global a la cual se estarán conectando los modelos que no tengan una configuración personalizada.
 * @param dataBase Tipo de BD's a la cual se estarán los modelos que no tengan una configuración personalizada, los tipos disponibles son
 *         MySQL,
 *         SQLServer,
 *         PostgreSQL,
 *         SQLite.
 */
JBSqlUtils.setDataBaseTypeGlobal(DataBase dataBase);
~~~

Al haber configurado las variables de conexión globales, su aplicación estará lista
para instanciar objetos que hereden la clase JBSqlUtils, los cuales fungirán cómo modelos y le permitirán
realizar operaciones sobre la tabla correspondiente a cada modelo.

De no desear usar Modelos en su aplicación hasta este punto, podrá utilizar JBSqlUtils cómo un generador de
sentencias SQL que le permitirán crear o eliminar una tabla en BD's, 
insertar, seleccionar, actualizar o eliminar registros de una tabla de acuerdo a la lógica que brinde a la
sentencia SQL a ejecutar, todo esto sin necesidad de instanciar un modelo, únicamente habiendo configurado sus
variables de conexión.

***
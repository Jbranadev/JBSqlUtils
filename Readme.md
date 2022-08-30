# JBSqlUtils :computer:

JBSqlUtils es una librería java que permite gestionar BD's SQLite, MySQL, PostgreSQL y SQLServer, de una manera fácil y rápida
sin interrumpir la ejecución del hilo principal del programa, lo cual la hace un potente ORM, por medio del cual tendrá
acceso a un CRUD, configurando únicamente la conexión del modelo, los atributos que posee la tabla en BD's como
variables que pertenecerán al modelo en su aplicación.

JBSqlUtils también proporciona un potente generador de consultas que le permitira actualizar o eliminar registros
de una tabla en su BD's sin necesidad de instanciar un objeto como tal, únicamente tendrá que configurar previamente
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

Al setear las variables globales de conexión estas se almacenan como variables del sistema
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
sentencia SQL a ejecutar, todo esto sin necesidad de instanciar un modelo, únicamente habiendo configurado sus 
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

## ¿Cómo crear modelos a través de JBSqlUtils?

Para poder crear clases que funcionen como modelos en nuestra aplicación, 
únicamente es necesario heredar la clase JBSqlUtils, declarar los miembros de la clase
que corresponden a cada una de las columnas de la tabla con las que queremos poder interactuar, 
acá vemos un ejemplo:

~~~

package io.github.josecarlosbran.JBSqlLite.Pruebas;

import io.github.josecarlosbran.JBSqlLite.Column;
import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.JBSqlUtils;

/**
 * @author Jose Bran
 * Clase de Pruebas
 */
public class Test extends JBSqlUtils {

    /**
     * En el constructor de nuestra clase que utilizaremos como modelo al heredar la clase JBSqlutils
     * hacemos el llamado al constructor de la clase JBSqlUtils la cual inicializara el modelo para poder
     * ser utilizado, una vez instanciado el modelo, podremos obtener uno o varios registros de la tabla
     * correspondiente al modelo, insertar, actualizar o eliminar registros.
     *
     * Es importante que antes de instanciar un modelo que herede la clase JBSqlUtils se hayan definido
     * las propiedades de conexión como variables del sistema.
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
     * en java y por medio del constructor del objeto Column se pase como parametro el tipo de dato SQL
     * de la columna, adicional a esto se pueden definir restricciones, como valor por defecto para la columna
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
     * ya que la clase Column es una clase generica y no puede trabajar con datos primivitos como int, pero si con
     * clases contenedoras como Integer.
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
     * crea la tabla en BD's desde nuestra aplicación a través del metodo modelo.crateTable().
     *
     * Agregamos una restriccion SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
     * desde nuestra aplicación en caso esta no exista a través del metodo modelo.crateTable(), de lo contrario no es necesario que agreguemos restricciones.
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
     * En este ejemplo seteamos 'true' como default_value, debido a que este modelo se conectara a un SQLServer,
     * en PostgreSQL la sintaxis es true. Por lo cual es importante tener claro la sintaxis de la BD's a la cual
     * se estará conectando el modelo.
     *
     * Agregamos una restriccion SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
     * desde nuestra aplicación en caso esta no exista a través del metodo modelo.crateTable(), de lo contrario no es necesario que agreguemos restricciones.
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


## ¿Cómo obtener JBSqlUtils para usarlo en mi proyecto?

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

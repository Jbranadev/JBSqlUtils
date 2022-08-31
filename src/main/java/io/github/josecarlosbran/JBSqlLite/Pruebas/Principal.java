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
package io.github.josecarlosbran.JBSqlLite.Pruebas;

import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.LogsJB.LogsJB;
import io.github.josecarlosbran.LogsJB.Numeracion.NivelLog;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static io.github.josecarlosbran.JBSqlLite.JBSqlUtils.*;

public class Principal {


    public static void main(String[] args) {
        try {

            LogsJB.setGradeLog(NivelLog.INFO);
            String DB = "JBSQLUTILS";
            //setDataBaseTypeGlobal(DataBase.MySQL);
            //setDataBaseGlobal(BDSqlite);
            //setDataBaseGlobal(DB);
            //setDataBaseTypeGlobal(DataBase.MySQL);
            //setHostGlobal("localhost");
            //setPortGlobal("5076");
            //setUserGlobal("Bran");
            //setUserGlobal("postgres");
            //setPasswordGlobal("Bran");
            //LogsJB.info(BDSqlite);

            setPortGlobal("5077");
            setHostGlobal("localhost");
            setUserGlobal("Bran");
            setPasswordGlobal("Bran");
            setDataBaseGlobal("JBSQLUTILS");
            setDataBaseTypeGlobal(DataBase.SQLServer);


            /**
             * Instanciamos el modelo
             */
            Test test = new Test();
            new Principal().SQLITE(new Test());

            //new Principal().MySQL(new Test());

            //new Principal().PostgreSQL(new Test());

           // new Principal().SQLServer(new Test());






        } catch (DataBaseUndefind | PropertiesDBUndefined | InstantiationException | IllegalAccessException |
                 ValorUndefined | ModelNotFound
                /*|IllegalAccessException|InvocationTargetException*/ e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }


    void SQLITE(Test test) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined, InstantiationException, IllegalAccessException, ModelNotFound {
        String separador=System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        test.setGetPropertySystem(false);
        test.setBD(BDSqlite);
        test.setDataBaseType(DataBase.SQLite);
        long inicio = System.currentTimeMillis();



        //test.refresh();
        /**
         * Setea la bandera que define si el modelo desea que JBSqlUtils maneje las timestamps Created_at, Update_at.
         * @param timestamps True si las timestamps serán manejadas por JBSqlUtils, False, si el modelo no tiene estas
         *                   columnas.
         */
        //test.setTimestamps(false);
        /**
         * Elimina la tabla correspondiente al modelo en BD's
         *
         * @return True si la tabla correspondiente al modelo en BD's existe y fue eliminada, de no existir la tabla correspondiente
         * en BD's retorna False.
         */
        //test.dropTableIfExist();

        /**
         * Crea la tabla correspondiente al modelo en BD's si esta no existe.
         * @return True si la tabla correspondiente al modelo en BD's no existe y fue creada exitosamente,
         * False si la tabla correspondiente al modelo ya existe en BD's
         */
        //test.crateTable();

        /**
         * Asignamos valores a las columnas del modelo, luego llamamos al método save(),
         * el cual se encarga de insertar un registro en la tabla correspondiente al modelo con la información del mismo
         * si este no existe, de existir actualiza el registro por medio de la clave primaria del modelo.
         */
        /*test.getName().setValor("Marcos");
        test.getApellido().setValor("Cabrera");*/
        //test.getIsMayor().setValor(false);
        /**
         * En este primer ejemplo no seteamos un valor a la columna IsMayor, para comprobar que el valor
         * por default configurado al crear la tabla, se este asignando a la respectiva columna.
         */
        //test.save();

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
        /*while (!test.getTaskIsReady()){

        }*/

        /**
         * Una vez hemos comprobado que el modelo no esta escribiendo u obteniendo información en segundo plano
         * podemos utilizarlo, para insertar otro registro totalmente diferente al que insertamos anteriormente.
         */
        /*test.getName().setValor("Elsa");
        test.getApellido().setValor("Aguirre");
        test.getIsMayor().setValor(false);*/

        /**
         * Le indicamos a JBSqlUtils que de este segundo registro a insertar, no queremos que maneje
         * las columnas created_at y updated_at.
         */
        //test.setTimestamps(false);

        /**
         * En este segundo ejemplo si seteamos un valor a la columna IsMayor, ya que no queremos que esta
         * tenga el valor configurado por default para esta columna al momento de crear la tabla.
         */
        //test.save();


        /**
         * Declaramos una lista de modelos del tipo Test, en la cual almacenaremos la información obtenida de BD's'
         */
        List<Test> lista=new ArrayList<>();

        /**
         * Obtenemos todos los registros cuyos Id son mayores a 2, el metodo orderBy() los ordena de acuerdo a la columna
         * que enviamos como parametro y el tipo de ordenamiento que le especificamos.
         * El método orderBy() proporciona acceso a todos los métodos que hemos visto anteriormente, los cuales nos
         * permiten obtener uno o multiples registros, de acuerdo a la lógica que brindemos a nuestra sentencia SQL.
         */
        lista=test.where("id", Operator.MAYOR_QUE, 2).orderBy("id", OrderType.DESC).getAll();
        //lista=test.where("id", Operator.MAYOR_QUE, "0").take(10).get();


        //test.where("",null, "").and("", null, "").or("", null, "").and("").getAll();

        /**
         * Podemos obtener un registro de la tabla correspondiente al modelo en BD's a través del método firstOrFail()
         * el cual obtiene un nuevo modelo del tipo que realiza la invocación del método con la información obtenida,
         * unicamente casteamos el resultado al tipo de modelo que recibira la información.
         *
         * En caso de no encontrar el registro que se desea obtener lanzara una excepción ModelNotFound, la cual
         * nos indicará que no fue posible encontrar la información para el modelo.
         *
         * Para poder filtrar la busqueda y tener acceso al método firstOrFail(), es necesario que llamemos al método
         * where() el cual nos proporciona un punto de entrada para otros métodos, por medio de los cuales podemos
         * brindar una lógica un poco más compleja a la busqueda del registro que deseamos obtener.
         */
        //Test test2= (Test) test.where("Name", Operator.IGUAL_QUE, "Jose").and("IsMayor", Operator.IGUAL_QUE, false).firstOrFail();


        /**
         * Esperamos a que el modelo termine de obtener la información de BD's
         */
        while (!test.getTaskIsReady()){

        }
        /**
         * Mostramos la información obtenida
         */
        /*LogsJB.info(test2.getId().getValor()+"   "+test2.getName().getValor()+"   "+test2.getApellido().getValor()
                +"   "+test2.getIsMayor().getValor()+"   "+test2.getCreated_at().getValor()+"   "+test2.getUpdated_at().getValor());

        test2.getIsMayor().setValor(true);*/
        //test2.save();
        //test.getIsMayor().setValor(!test.getIsMayor().getValor());

        //LogsJB.info("Cantidad de resultado lista: "+lista.size());

        /**
         * Declaramos una función anonima que recibira como parametro un obtjeto del tipo Test
         * el cual es el tipo de modelo que obtendremos y dentro de esta función imprimiremos
         * la información del modelo.
         */
        Consumer<Test> showFilas = fila -> {
/*
            String separador=System.getProperty("file.separator");
            String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                    "BD" +
                    separador +
                    "JBSqlUtils.db");
            fila.setGetPropertySystem(false);
            fila.setBD(BDSqlite);
            fila.setDataBaseType(DataBase.SQLite);
            fila.setTableExist(false);
            fila.getId().setValor(null);
            fila.getIsMayor().setValor(!fila.getIsMayor().getValor());*/
            LogsJB.info(fila.getId().getValor()+"   "+fila.getName().getValor()+"   "+fila.getApellido().getValor()+"   "+fila.getIsMayor().getValor()+"   "+fila.getCreated_at().getValor()+"   "+fila.getUpdated_at().getValor());
        };

        /**
         * Mostramos la información obtenida iterando sobre los modelos obtenidos de BD's y mostrando
         * su contenido por medio de la función anonima que declaramos ateriormente.
         */
        lista.forEach(showFilas);


        //test.saveALL(lista);
        //test.save();
        //test.delete();
        /*while (!test.getTaskIsReady()){

        }*/



        /**
         * Actualizar todas las filas de una tabla X (Test), senteando un valor Y(Jose Carlos) a una columna Z(name).
         * El método update recibe como parametro el nombre de la tabla que se desea actualizar y proporciona acceso
         * al método set el cual recibe como primer parametro el nombre de la columna que se desea modificar y el valor
         * que se desea setear a la columna, el método set proporciona acceso al método execute el cual se encarga de
         * ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
         */
        //int rows_afected=update("Test").set("name", "Jose Carlos").execute();

        /**
         * Podemos agregar una sentencia Where, por medio del cual podemos acceder a los métodos necesarios para
         * filtrar la cantidad de filas que queremos modificar, una vez hemos terminado de brindar la lógica hacemos el
         * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
         * afectadas.
         */
        //int rows_afected=update("Test").set("IsMayor", "1").where("Id", Operator.MAYOR_QUE, "6").execute();


        /**
         * Podemos actualizar mas de una columna a travez del método andSet, el cual nos proporciona la capacidad de
         * modificar el valor de otra columna y acceso a los métodos andSet para setear otro valor a otra columna y el método
         * where por medio del cual podemos filtrar las filas que se veran afectadas al llamar al método execute, el cual
         * se encargara de ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
         */
        //rows_afected=update("Test").set("name", "Jose Carlos").andSet("IsMayor", "true").execute();


        /**
         * Eliminar todas las filas de una tabla X (Test), donde la columna Y(Id) tiene un valor MAYOR O IGUAL a Z(2).
         * El método delete recibe como parametro el nombre de la tabla que se desea eliminar registros y proporciona acceso
         * al método Where, por medio del cual podemos acceder a los métodos necesarios para
         * filtrar la cantidad de filas que queremos eliminar, una vez hemos terminado de brindar la lógica hacemos el
         * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
         * afectadas.
         */
        //rows_afected=delete("Test").where("Id", Operator.MAYOR_IGUAL_QUE, "1").or("apellido", Operator.LIKE, "'Camey%'").execute();

        long fin = System.currentTimeMillis();
        //double tiempo = (double) ((fin - inicio)/1000);
        double tiempo = (double) ((fin - inicio));
        LogsJB.warning(tiempo +" mili segundos");
        //LogsJB.warning("Filas afectadas por el update: "+rows_afected);

    }

    void MySQL(Test test) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined, InstantiationException, IllegalAccessException {

        test.setGetPropertySystem(false);
        test.setPort("5076");
        test.setHost("localhost");
        test.setUser("Bran");
        test.setPassword("Bran");
        test.setBD("JBSQLUTILS");
        test.setDataBaseType(DataBase.MySQL);
        long inicio = System.currentTimeMillis();

        //test.dropTableIfExist();
        //test.crateTable();

/*
        test.getName().setValor("Ligia");
        test.getApellido().setValor("Camey");
        //test.getIsMayor().setValor(true);
        test.save();
        while (!test.getTaskIsReady()){

        }

        test.getName().setValor("Isabel");
        test.getApellido().setValor("Peralta");
        test.getIsMayor().setValor(false);
        test.save();*/


        Consumer<Test> showFilas = fila -> {
/*
            String separador=System.getProperty("file.separator");
            String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                    "BD" +
                    separador +
                    "JBSqlUtils.db");
            fila.setGetPropertySystem(false);
            fila.setBD(BDSqlite);
            fila.setDataBaseType(DataBase.SQLite);
            fila.setTableExist(false);
            fila.getId().setValor(null);*/
            fila.getIsMayor().setValor(!fila.getIsMayor().getValor());
            LogsJB.info(fila.getId().getValor()+"   "+fila.getName().getValor()+"   "+fila.getApellido().getValor()+"   "+fila.getIsMayor().getValor()+"   "+fila.getCreated_at().getValor()+"   "+fila.getUpdated_at().getValor());
        };

        List<Test> lista=new ArrayList<>();
        lista=test.where("id", Operator.MAYOR_QUE, "0").getAll();
        //lista=test.where("id", Operator.MAYOR_QUE, "0").take(4).get();


        //test.where("",null, "").and("", null, "").or("", null, "").and("").getAll();
        //test.where("name", Operator.IGUAL_QUE, "'Jose'").and("apellido", Operator.IGUAL_QUE, "'Bran'").get();
        while (!test.getTaskIsReady()){

        }
        //LogsJB.info(test.getId().getValor()+"   "+test.getName().getValor()+"   "+test.getApellido().getValor()+"   "+test.getIsMayor().getValor());

        //test.getIsMayor().setValor(!test.getIsMayor().getValor());

        //LogsJB.info("Cantidad de resultado lista: "+lista.size());

        lista.forEach(showFilas);


        test.saveALL(lista);
        //test.save();
        //test.delete();
        while (!test.getTaskIsReady()){

        }

        long fin = System.currentTimeMillis();
        //double tiempo = (double) ((fin - inicio)/1000);
        double tiempo = (double) ((fin - inicio));
        LogsJB.warning(tiempo +" mili segundos");
        //LogsJB.fatal("Termino de invocar los métodos que almacena los modelos en BD's");

    }

    void PostgreSQL(Test test) throws InterruptedException, ValorUndefined, DataBaseUndefind, PropertiesDBUndefined, InstantiationException, IllegalAccessException, ModelNotFound {
        test.setGetPropertySystem(false);
        test.setPort("5075");
        test.setHost("localhost");
        test.setUser("postgres");
        test.setPassword("Bran");
        test.setBD("JBSQLUTILS");
        test.setDataBaseType(DataBase.PostgreSQL);

        //test.dropTableIfExist();
        //test.crateTable();

/*
        test.getName().setValor("Ligia");
        test.getApellido().setValor("Camey");
        //test.getIsMayor().setValor(true);
        test.save();
        while (!test.getTaskIsReady()){

        }

        test.getName().setValor("Isabel");
        test.getApellido().setValor("Peralta");
        test.getIsMayor().setValor(false);
        test.save();*/


        Consumer<Test> showFilas = fila -> {
/*
            String separador=System.getProperty("file.separator");
            String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                    "BD" +
                    separador +
                    "JBSqlUtils.db");
            fila.setGetPropertySystem(false);
            fila.setBD(BDSqlite);
            fila.setDataBaseType(DataBase.SQLite);
            fila.setTableExist(false);
            fila.getId().setValor(null);*/
            fila.getIsMayor().setValor(!fila.getIsMayor().getValor());
            LogsJB.info(fila.getId().getValor()+"   "+fila.getName().getValor()+"   "+fila.getApellido().getValor()+"   "+fila.getIsMayor().getValor());

        };

        List<Test> lista=new ArrayList<>();
        //lista=test.where("name", Operator.IGUAL_QUE, "'Jose'").and("isMayor", Operator.IGUAL_QUE, "false").getAll();
        lista=test.where("isMayor", Operator.IGUAL_QUE, "true").take(2).get();


        //test.where("",null, "").and("", null, "").or("", null, "").and("").getAll();
        //test.where("name", Operator.IGUAL_QUE, "'Jose'").and("apellido", Operator.IGUAL_QUE, "'Bran'").get();
        while (!test.getTaskIsReady()){

        }
        //LogsJB.info(test.getId().getValor()+"   "+test.getName().getValor()+"   "+test.getApellido().getValor()+"   "+test.getIsMayor().getValor());

        //test.getIsMayor().setValor(!test.getIsMayor().getValor());

        //LogsJB.info("Cantidad de resultado lista: "+lista.size());

        lista.forEach(showFilas);
        long inicio = System.currentTimeMillis();

        //test.saveALL(lista);
        //test.save();
        //test.delete();
        /*while (!test.getTaskIsReady()){

        }*/

        long fin = System.currentTimeMillis();
        //double tiempo = (double) ((fin - inicio)/1000);
        double tiempo = (double) ((fin - inicio));
        LogsJB.warning(tiempo +" mili segundos");
        //LogsJB.fatal("Termino de invocar los métodos que almacena los modelos en BD's");

    }



    void SQLServer(Test test) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined, InstantiationException, IllegalAccessException, ModelNotFound {
        test.setGetPropertySystem(false);
        test.setPort("5077");
        test.setHost("localhost");
        test.setUser("Bran");
        test.setPassword("Bran");
        test.setBD("JBSQLUTILS");
        test.setDataBaseType(DataBase.SQLServer);

        long inicio = System.currentTimeMillis();

        //test.refresh();
        /**
         * Setea la bandera que define si el modelo desea que JBSqlUtils maneje las timestamps Created_at, Update_at.
         * @param timestamps True si las timestamps serán manejadas por JBSqlUtils, False, si el modelo no tiene estas
         *                   columnas.
         */
        //test.setTimestamps(false);
        /**
         * Elimina la tabla correspondiente al modelo en BD's
         *
         * @return True si la tabla correspondiente al modelo en BD's existe y fue eliminada, de no existir la tabla correspondiente
         * en BD's retorna False.
         */
        //test.dropTableIfExist();

        /**
         * Crea la tabla correspondiente al modelo en BD's si esta no existe.
         * @return True si la tabla correspondiente al modelo en BD's no existe y fue creada exitosamente,
         * False si la tabla correspondiente al modelo ya existe en BD's
         */
        //test.crateTable();

        /**
         * Asignamos valores a las columnas del modelo, luego llamamos al método save(),
         * el cual se encarga de insertar un registro en la tabla correspondiente al modelo con la información del mismo
         * si este no existe, de existir actualiza el registro por medio de la clave primaria del modelo.
         */
        /*test.getName().setValor("Jose");
        test.getApellido().setValor("Bran");*/
        //test.getIsMayor().setValor(false);
        /**
         * En este primer ejemplo no seteamos un valor a la columna IsMayor, para comprobar que el valor
         * por default configurado al crear la tabla, se este asignando a la respectiva columna.
         */
        //test.save();

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
        /*while (!test.getTaskIsReady()){

        }*/

        /**
         * Una vez hemos comprobado que el modelo no esta escribiendo u obteniendo información en segundo plano
         * podemos utilizarlo, para insertar otro registro totalmente diferente al que insertamos anteriormente.
         */
        /*test.getName().setValor("Daniel");
        test.getApellido().setValor("Quiñonez");
        test.getIsMayor().setValor(false);*/

        /**
         * Le indicamos a JBSqlUtils que de este segundo registro a insertar, no queremos que maneje
         * las columnas created_at y updated_at.
         */
        //test.setTimestamps(false);

        /**
         * En este segundo ejemplo si seteamos un valor a la columna IsMayor, ya que no queremos que esta
         * tenga el valor configurado por default para esta columna al momento de crear la tabla.
         */
        //test.save();


        Consumer<Test> showFilas = fila -> {
/*
            String separador=System.getProperty("file.separator");
            String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                    "BD" +
                    separador +
                    "JBSqlUtils.db");
            fila.setGetPropertySystem(false);
            fila.setBD(BDSqlite);
            fila.setDataBaseType(DataBase.SQLite);
            fila.setTableExist(false);
            fila.getId().setValor(null);*/
            fila.getIsMayor().setValor(!fila.getIsMayor().getValor());
            LogsJB.info(fila.getId().getValor()+"   "+fila.getName().getValor()+"   "+fila.getApellido().getValor()+"   "+fila.getIsMayor().getValor()+"   "+fila.getCreated_at().getValor()+"   "+fila.getUpdated_at().getValor());
        };

        List<Test> lista=new ArrayList<>();
        //lista=test.where("id", Operator.MAYOR_QUE, "0").getAll();
        //lista=test.where("id", Operator.MAYOR_QUE, "0").take(10).get();


        //test.where("",null, "").and("", null, "").or("", null, "").and("").getAll();

        /**
         * Podemos obtener un registro de la tabla correspondiente al modelo en BD's a través del método first()
         * el cual obtiene un nuevo modelo del tipo que realiza la invocación del método con la información obtenida,
         * unicamente casteamos el resultado al tipo de modelo que recibira la información.
         *
         * Para poder filtrar la busqueda y tener acceso al método first(), es necesario que llamemos al método
         * where() el cual nos proporciona un punto de entrada para otros métodos, por medio de los cuales podemos
         * brindar una lógica un poco más compleja a la busqueda del registro que deseamos obtener.
         */
        Test test2= (Test) test.where("name", Operator.LIKE, "Jos%").and("apellido", Operator.IGUAL_QUE, "Bran").first();

        /**
         * Esperamos a que el modelo termine de obtener la información de BD's
         */
        while (!test.getTaskIsReady()){

        }
        /**
         * Mostramos la información obtenida
         */
        LogsJB.info(test2.getId().getValor()+"   "+test2.getName().getValor()+"   "+test2.getApellido().getValor()
                +"   "+test2.getIsMayor().getValor()+"   "+test2.getCreated_at().getValor()+"   "+test2.getUpdated_at().getValor());

        //test.getIsMayor().setValor(!test.getIsMayor().getValor());

        //LogsJB.info("Cantidad de resultado lista: "+lista.size());

        //lista.forEach(showFilas);


        //test.saveALL(lista);
        //test.save();
        //test.delete();
        /*while (!test.getTaskIsReady()){

        }*/



        /**
         * Actualizar todas las filas de una tabla X (Test), senteando un valor Y(Jose Carlos) a una columna Z(name).
         * El método update recibe como parametro el nombre de la tabla que se desea actualizar y proporciona acceso
         * al método set el cual recibe como primer parametro el nombre de la columna que se desea modificar y el valor
         * que se desea setear a la columna, el método set proporciona acceso al método execute el cual se encarga de
         * ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
         */
        //int rows_afected=update("Test").set("name", "Jose Carlos").execute();

        /**
         * Podemos agregar una sentencia Where, por medio del cual podemos acceder a los métodos necesarios para
         * filtrar la cantidad de filas que queremos modificar, una vez hemos terminado de brindar la lógica hacemos el
         * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
         * afectadas.
         */
        //int rows_afected=update("Test").set("IsMayor", "1").where("Id", Operator.MAYOR_QUE, "6").execute();


        /**
         * Podemos actualizar mas de una columna a travez del método andSet, el cual nos proporciona la capacidad de
         * modificar el valor de otra columna y acceso a los métodos andSet para setear otro valor a otra columna y el método
         * where por medio del cual podemos filtrar las filas que se veran afectadas al llamar al método execute, el cual
         * se encargara de ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
         */
        //rows_afected=update("Test").set("name", "Jose Carlos").andSet("IsMayor", "true").execute();


        /**
         * Eliminar todas las filas de una tabla X (Test), donde la columna Y(Id) tiene un valor MAYOR O IGUAL a Z(2).
         * El método delete recibe como parametro el nombre de la tabla que se desea eliminar registros y proporciona acceso
         * al método Where, por medio del cual podemos acceder a los métodos necesarios para
         * filtrar la cantidad de filas que queremos eliminar, una vez hemos terminado de brindar la lógica hacemos el
         * llamado al método execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
         * afectadas.
         */
        //rows_afected=delete("Test").where("Id", Operator.MAYOR_IGUAL_QUE, "1").or("apellido", Operator.LIKE, "'Camey%'").execute();

        long fin = System.currentTimeMillis();
        //double tiempo = (double) ((fin - inicio)/1000);
        double tiempo = (double) ((fin - inicio));
        LogsJB.warning(tiempo +" mili segundos");
        //LogsJB.warning("Filas afectadas por el update: "+rows_afected);

    }

}

package io.github.josecarlosbran.JBSqlLite.Pruebas;

import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
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
            setDataBaseTypeGlobal(DataBase.MySQL);
            //setDataBaseGlobal(BDSqlite);
            setDataBaseGlobal(DB);
            //setDataBaseTypeGlobal(DataBase.MySQL);
            setHostGlobal("localhost");
            setPortGlobal("5076");
            setUserGlobal("Bran");
            //setUserGlobal("postgres");
            setPasswordGlobal("Bran");
            //LogsJB.info(BDSqlite);


            Test test = new Test();
            //new Principal().SQLITE(new Test());

            //new Principal().MySQL(new Test());

            //new Principal().PostgreSQL(new Test());

            new Principal().SQLServer(new Test());






        } catch (DataBaseUndefind | PropertiesDBUndefined  | InstantiationException |
                 IllegalAccessException  |ValorUndefined
                /*|IllegalAccessException|InvocationTargetException*/ e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }


    void SQLITE(Test test) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined, InstantiationException, IllegalAccessException {
        String separador=System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        test.setGetPropertySystem(false);
        test.setBD(BDSqlite);
        test.setDataBaseType(DataBase.SQLite);
        long inicio = System.currentTimeMillis();
        //test.dropTableIfExist();
        //test.crateTable();

/*
        test.getName().setValor("Alfredo");
        test.getApellido().setValor("Bran");
        test.getIsMayor().setValor(false);
        test.save();
        while (!test.getTaskIsReady()){

        }

        test.getName().setValor("Porfirio");
        test.getApellido().setValor("Quiñonez");
        test.getIsMayor().setValor(true);
        test.save();*/


        Consumer<Test> showFilas = fila -> {
            /*fila.setModelExist(false);
            fila.setGetPropertySystem(false);
            fila.setPort("5076");
            fila.setHost("localhost");
            fila.setUser("Bran");
            fila.setPassword("Bran");
            fila.setBD("JBSQLUTILS");
            fila.setDataBaseType(DataBase.MySQL);*/
            fila.getIsMayor().setValor(!fila.getIsMayor().getValor());
            LogsJB.info(fila.getId().getValor()+"   "+fila.getName().getValor()+"   "+fila.getApellido().getValor()+"   "+fila.getIsMayor().getValor()+"   "+fila.getCreated_at().getValor()+"   "+fila.getUpdated_at().getValor());

        };

        List<Test> lista=new ArrayList<>();
        lista=test.where("id", Operator.MAYOR_QUE, "0").getAll();
        //lista=test.where("isMayor", Operator.IGUAL_QUE, "true").take(3).get();


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
        //LogsJB.fatal("Termino de invocar los metodos que almacena los modelos en BD's");
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
        //LogsJB.fatal("Termino de invocar los metodos que almacena los modelos en BD's");

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
        //LogsJB.fatal("Termino de invocar los metodos que almacena los modelos en BD's");

    }



    void SQLServer(Test test) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined, InstantiationException, IllegalAccessException {
        test.setGetPropertySystem(false);
        test.setPort("5077");
        test.setHost("localhost");
        test.setUser("Bran");
        test.setPassword("Bran");
        test.setBD("JBSQLUTILS");
        test.setDataBaseType(DataBase.SQLServer);

        long inicio = System.currentTimeMillis();

        //test.refresh();

        //test.dropTableIfExist();
        //test.crateTable();


        /*test.getName().setValor("Arturo");
        test.getApellido().setValor("Camey");
        //test.getIsMayor().setValor(true);
        test.save();
        while (!test.getTaskIsReady()){

        }

        test.getName().setValor(null);
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
        //lista=test.where("id", Operator.MAYOR_QUE, "0").getAll();
        lista=test.where("id", Operator.MAYOR_QUE, "0").take(10).get();


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
        //LogsJB.fatal("Termino de invocar los metodos que almacena los modelos en BD's");

        /**
         * Actualizar todas las filas de una tabla X (Test), senteando un valor Y(Jose Carlos) a una columna Z(name).
         * El metodo update recibe como parametro el nombre de la tabla que se desea actualizar y proporciona acceso
         * al metodo set el cual recibe como primer parametro el nombre de la columna que se desea modificar y el valor
         * que se desea setear a la columna, el metodo set proporciona acceso al metodo execute el cual se encarga de
         * ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
         */
        int rows_afected=update("Test").set("name", "Jose Carlos").execute();

        /**
         * Podemos agregar una sentencia Where, por medio de la cual podemos acceder a los metodos necesarios para
         * filtrar la cantidad de filas que queremos modificar, una vez hemos terminado de brindar la logica hacemos el
         * llamado al metodo execute el cual se encarga de ejecutar la sentencia SQL generada y retorna el numero de filas
         * afectadas.
         */
        rows_afected=update("Test").set("name", "Jose Carlos").where("Id", Operator.MAYOR_QUE, "2").and("apellido", Operator.LIKE, "Bran%").execute();


        /**
         * Podemos actualizar mas de una columna a travez del metodo andSet, el cual nos proporciona la capacidad de
         * modificar el valor de otra columna y acceso a los metodos andSet para setear otro valor a otra columna y el metodo
         * where por medio del cual podemos filtrar las filas que se veran afectadas al llamar al metodo execute, el cual
         * se encargara de ejecutar la sentencia SQL generada y retorna el numero de filas afectadas.
         */
        rows_afected=update("Test").set("name", "Jose Carlos").andSet("IsMayor", "true").execute();


    }

}

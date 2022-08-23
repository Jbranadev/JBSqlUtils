package io.github.josecarlosbran.JBSqlLite.Pruebas;

import com.google.gson.Gson;
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

            LogsJB.setGradeLog(NivelLog.DEBUG);
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

            new Principal().PostgreSQL(new Test());

            //new Principal().SQLServer(new Test());






        } catch (DataBaseUndefind | PropertiesDBUndefined | InterruptedException | InstantiationException |
                 IllegalAccessException | ModelNotFound |ValorUndefined
                /*|IllegalAccessException|InvocationTargetException*/ e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }


    void SQLITE(Test test) {
        String separador=System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        test.setGetPropertySystem(false);
        test.setBD(BDSqlite);
        test.setDataBaseType(DataBase.SQLite);

        //test.closeConnection(test.getConnection());
        test.dropTableIfExist();
        test.crateTable();

        test.getName().setValor("Jose");
        test.getApellido().setValor("Bran");
        test.getIsMayor().setValor(true);
        //test.save();
    }

    void MySQL(Test test) {

        test.setGetPropertySystem(false);
        test.setPort("5076");
        test.setHost("localhost");
        test.setUser("Bran");
        test.setPassword("Bran");
        test.setBD("JBSQLUTILS");
        test.setDataBaseType(DataBase.MySQL);


        //test.closeConnection(test.getConnection());
        //test.dropTableIfExist();
        //test.crateTable();

        test.getName().setValor("Jose");
        test.getApellido().setValor("Bran");
        test.getIsMayor().setValor(true);
        test.save();

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


    void refrescar(Test test){
        //test.closeConnection(test.getConnection());
        test.refresh();
    }

    void SQLServer(Test test){
        test.setGetPropertySystem(false);
        test.setPort("5077");
        test.setHost("localhost");
        test.setUser("Bran");
        test.setPassword("Bran");
        test.setBD("JBSQLUTILS");
        test.setDataBaseType(DataBase.SQLServer);

        //test.closeConnection(test.getConnection());
        //test.dropTableIfExist();
        test.crateTable();

        test.getName().setValor("Jose");
        test.getApellido().setValor("Bran");
        test.getIsMayor().setValor(true);
        //test.save();

    }

}

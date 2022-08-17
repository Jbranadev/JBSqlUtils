package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.LogsJB.LogsJB;
import io.github.josecarlosbran.LogsJB.Numeracion.NivelLog;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static io.github.josecarlosbran.JBSqlLite.JBSqlUtils.*;
import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.expresion;
import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.where;

public class Principal {

    /*
    public static void main(String[] args){
        List<String> cadenas= new ArrayList<>();
        addDato(cadenas, "Jose");
        addDato(cadenas, "Carlos");
        addDato(cadenas, "Alfredo");
        addDato(cadenas, "Bran");
        addDato(cadenas, "Aguirre");
        List<Integer> numeros= new ArrayList<>();
        numeros.add(50);
        numeros.add(100);
        System.out.println("primer numero de la lista: "+getObject(0, numeros));
        System.out.println("3 Cadena de la lista: "+getObject(2, cadenas));
        getall(1, 5, 10).forEach(System.out::println);

    }*/

    public static void main(String[] args) {
        try {

            LogsJB.setGradeLog(NivelLog.INFO);
            String DB = "JBSQLUTILS";
            setDataBaseTypeGlobal(DataBase.PostgreSQL);
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
            new Principal().SQLITE(new Test());

            //new Principal().MySQL(new Test());

            new Principal().PostgreSQL(new Test());

            //new Principal().SQLServer(new Test());

        } catch (DataBaseUndefind | PropertiesDBUndefined | InterruptedException | ValorUndefined |
                 InstantiationException | IllegalAccessException
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

    void PostgreSQL(Test test) throws InterruptedException, ValorUndefined, DataBaseUndefind, PropertiesDBUndefined, InstantiationException, IllegalAccessException {
        test.setGetPropertySystem(false);
        test.setPort("5075");
        test.setHost("localhost");
        test.setUser("postgres");
        test.setPassword("Bran");
        test.setBD("JBSQLUTILS");
        test.setDataBaseType(DataBase.PostgreSQL);

        //test.closeConnection(test.getConnection());
        test.refresh();
        //Thread.sleep(1500);
        /*Consumer<ColumnsSQL> showColumnas = columna -> {

            System.out.println(columna.getCOLUMN_NAME()+"   "+columna.getORDINAL_POSITION()+"   "+columna.getTYPE_NAME());

        };

        Thread.sleep(3000);
        test.getColumnas().forEach(showColumnas);*/


        //test.dropTableIfExist();
        //test.crateTable();


        /*test.getName().setValor("Jose Alfredo");
        test.getApellido().setValor("Bran Lara");
        test.getIsMayor().setValor(false);
        test.save();*/

        Consumer<Test> showFilas = fila -> {

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
            //LogsJB.info(fila.getId().getValor()+"   "+fila.getName().getValor()+"   "+fila.getApellido().getValor()+"   "+fila.getIsMayor().getValor());

        };

        //test.get(where(expresion(expresion("name", Operator.IGUAL_QUE, "'Jose Carlos'"), Operator.AND, expresion("isMayor", Operator.IGUAL_QUE, "false"))));
        List<Test> lista=new ArrayList<>();
        lista=test.getALL(test, where(expresion(expresion("name", Operator.LIKE, "'Jose%'"), Operator.AND, expresion("isMayor", Operator.IGUAL_QUE, "true"))));
        //Thread.sleep(5000);
        test.where("",null, "").and("", null, "").or("", null, "").and("").getAll();
        while (!test.getTaskIsReady()){

        }
        /*LogsJB.info("Resultado es: "+test.getId().getValor()+"    "+test.getName().getValor()+"    "
        +test.getApellido().getValor()+"    "
        +test.getIsMayor().getValor()+"    ");*/
        //LogsJB.info("Tamaño de la lista obtenida: "+lista.size());
        lista.forEach(showFilas);

        //test.getConnection();
        //test.refresh();
        LogsJB.fatal("Invocara los metodos que almacena los modelos en BD's");
        test.saveALL(lista);

        LogsJB.fatal("Termino de invocar los metodos que almacena los modelos en BD's");

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

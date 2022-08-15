package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ConexionUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Utilities.ColumnsSQL;
import io.github.josecarlosbran.LogsJB.LogsJB;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static io.github.josecarlosbran.JBSqlLite.JBSqlUtils.*;

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
            new Principal().SQLITE(test);

            new Principal().MySQL(test);

            new Principal().PostgreSQL(test);

            new Principal().SQLServer(test);

        } catch (DataBaseUndefind | /*ConexionUndefind |*/ PropertiesDBUndefined /*|InterruptedException*/
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

        test.closeConnection(test.getConnection());
        //test.dropTableIfExist();
        //test.crateTable();

        test.getName().setValor("Jose");
        test.getApellido().setValor("Bran");
        test.getIsMayor().setValor(true);
        test.save();
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

    void PostgreSQL(Test test){
        test.setGetPropertySystem(false);
        test.setPort("5075");
        test.setHost("localhost");
        test.setUser("postgres");
        test.setPassword("Bran");
        test.setBD("JBSQLUTILS");
        test.setDataBaseType(DataBase.PostgreSQL);

        //test.closeConnection(test.getConnection());
        //test.dropTableIfExist();
        //test.crateTable();

        test.getName().setValor("Jose");
        test.getApellido().setValor("Bran");
        test.getIsMayor().setValor(true);
        test.save();
        //LogsJB.info("Resultado es: "+test.save());
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
        //test.crateTable();

        test.getName().setValor("Jose");
        test.getApellido().setValor("Bran");
        test.getIsMayor().setValor(true);
        test.save();

    }

}

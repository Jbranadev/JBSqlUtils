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

    public static void main(String[] args){
        try{


            String BDSqlite= (Paths.get("").toAbsolutePath().normalize().toString()+"/BD/JBSqlUtils.db").replace("\\","/");
            String DB="JBSQLUTILS";

            setDataBaseGlobal(DB);
            setDataBaseTypeGlobal(DataBase.MySQL);
            setHostGlobal("localhost");
            setPortGlobal("5076");
            setUserGlobal("Bran");
            setPasswordGlobal("Bran");



            Test test=new Test();
            //test.getConnection();
            //Thread.sleep(5000);
            /*System.out.println("");
            System.out.println("");
            System.out.println("");
            //Thread.sleep(20000);
            test.dropTableIfExist();

            /*Thread.sleep(20000);
            System.out.println("");
            System.out.println("");
            System.out.println("");*/
            test.crateTable();

        }catch (DataBaseUndefind | /*ConexionUndefind |*/ PropertiesDBUndefined /*|InterruptedException*/
                /*|IllegalAccessException|InvocationTargetException*/ e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada: "+ e.toString());
            LogsJB.fatal("Tipo de Excepción : "+e.getClass());
            LogsJB.fatal("Causa de la Excepción : "+e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
            LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
        }

    }



}

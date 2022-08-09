package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ConexionUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Utilities.ColumnsSQL;
import io.github.josecarlosbran.LogsJB.LogsJB;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
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
            setPasswordGlobal("bran");
            Consumer<ColumnsSQL> consumer= ColumnsSQL-> {
                System.out.println("TableName: "+ColumnsSQL.getTABLE_NAME());
                System.out.println("ColumnName: "+ColumnsSQL.getCOLUMN_NAME());
                System.out.println("Tipo de Dato: "+ColumnsSQL.getTYPE_NAME());
                System.out.println("");
            };

            Consumer<Method> imprimirMetodos= metodo-> {
                System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass().getSimpleName());
            };

            Test test=new Test();
            List<Method> metodos=new LinkedList<>();
            metodos=test.getMethodsModel();
            System.out.println(" ");
            Thread.sleep(5000);
            metodos=getMethodsGetOfModel(metodos);
            //metodos.stream().forEach(imprimirMetodos);

            /*test.setConnect(test.getConnection());
            Thread.sleep(2000);
            test.closeConnection(test.getConnect());
            Thread.sleep(1000);
            test.getColumnas().stream().forEach(consumer);*/
        }catch (/*DataBaseUndefind | ConexionUndefind | PropertiesDBUndefined |InterruptedException*/ Exception e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada: "+ e.toString());
            LogsJB.fatal("Tipo de Excepción : "+e.getClass());
            LogsJB.fatal("Causa de la Excepción : "+e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
            LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
        }

    }



}

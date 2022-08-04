package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ConexionUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.LogsJB.LogsJB;

import java.nio.file.Paths;

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
            JBSqlUtils.setDataBaseGlobal(BDSqlite);
            JBSqlUtils.setDataBaseTypeGlobal(DataBase.SQLite);
            Thread.sleep(1000);
            Test test=new Test();
            test.setConnect(test.getConnection());
            test.closeConnection(test.getConnect());
        }catch (DataBaseUndefind | ConexionUndefind | PropertiesDBUndefined |InterruptedException e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada: "+ e.toString());
            LogsJB.fatal("Tipo de Excepción : "+e.getClass());
            LogsJB.fatal("Causa de la Excepción : "+e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
            LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
        }

    }

}

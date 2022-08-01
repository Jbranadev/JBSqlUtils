package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Exception.DataBase;
import io.github.josecarlosbran.LogsJB.LogsJB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Methods {

    private DataBase dataBase=setearDB();


/*
try{

    }catch (Exception e) {
        LogsJB.fatal("Excepción disparada en el metodo execute, el cual llama la creación del hilo: "+ e.toString());
        LogsJB.fatal("Tipo de Excepción : "+e.getClass());
        LogsJB.fatal("Causa de la Excepción : "+e.getCause());
        LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
        LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
    }
*/
    private DataBase setearDB(){
        String dataBase=System.getProperty("DataBase");
        if(Objects.isNull(dataBase)){
            //Si la propiedad del sistema no esta definida, Lanza una Exepción

        }else{
            if(dataBase.equals(DataBase.MySQL.name())){
                setDataBase(DataBase.MySQL);
                return DataBase.MySQL;
            }
            if(dataBase.equals(DataBase.SQLite.name())){
                setDataBase(DataBase.SQLite);
                return DataBase.SQLite;
            }
            if(dataBase.equals(DataBase.SQLServer.name())){
                setDataBase(DataBase.SQLServer);
                return DataBase.SQLServer;
            }
            if(dataBase.equals(DataBase.PostgreSQL.name())){
                setDataBase(DataBase.PostgreSQL);
                return DataBase.PostgreSQL;
            }
        }

        return null;
    }

    public Connection getConnection(String DB){
        Connection connect=null;
        try{
            connect = DriverManager.getConnection("jdbc:sqlite:"+DB);
            connect.setAutoCommit(false);
            if (connect!=null) {
                LogsJB.info("Conexión a BD's "+ DB+" Realizada exitosamente" );
            }
        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada: "+ e.toString());
            LogsJB.fatal("Tipo de Excepción : "+e.getClass());
            LogsJB.fatal("Causa de la Excepción : "+e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
            LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
        }
        return connect;
    }

    public void closeConnection(Connection connect){
        try{
            connect.close();
            LogsJB.info("Conexión a BD's cerrada");
        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada cerrar la conexión a la BD's: "+ e.toString());
            LogsJB.fatal("Tipo de Excepción : "+e.getClass());
            LogsJB.fatal("Causa de la Excepción : "+e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
            LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
        }
    }




    //Obtener un objeto generico de una lista
    public static <T> T getObject(int id, List<T> lista){
        return lista.get(id);
    }

    //agregar un objeto generico de una lista
    public static <T> void addDato(List<T> lista, T dato){
        lista.add(dato);
    }

    public static <T> List<T> getall(T a, T b, T c){
        List<T> lista=new ArrayList<>();
        lista.add(a);
        lista.add(b);
        lista.add(c);
        return lista;
    }

    public void imprimirnombreclase(){
        System.out.println("Nombre de la clase: "+this.getClass().getName());
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
        System.out.println("SystemProperty Seteada: "+System.getProperty("DataBase"));
    }
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

}

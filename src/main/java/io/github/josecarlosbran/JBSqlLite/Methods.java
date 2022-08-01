package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB;
import io.github.josecarlosbran.LogsJB.LogsJB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Methods extends UtilitiesJB {

    private DataBase dataBase=setearDB();

    private Properties prop=new Properties();

    public Methods() throws DataBaseUndefind {
    }


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

    
    private DataBase setearDB() throws DataBaseUndefind {
        String dataBase=System.getProperty("DataBase");
        if(stringIsNullOrEmpty(dataBase)){
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new DataBaseUndefind("No se a seteado la DataBase que índica a que BD's deseamos se pegue JBSqlUtils");
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
    private DataBase setearPropertiesDB() throws PropertiesDBUndefined {
        String user=System.getProperty("PropertiesDB.user");
        String password=System.getProperty("PropertiesDB.password");
        String puerto=System.getProperty("PropertiesDB.puerto");
        if(stringIsNullOrEmpty(user)){
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new PropertiesDBUndefined("No se a seteado la Propiedades de conexión a la BD's deseamos se pegue JBSqlUtils");
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
            if(this.getDataBase()==DataBase.PostgreSQL){
                //Carga el controlador de PostgreSQL
                Class.forName("org.postgresql.Driver");
                String url="";
                String usuario="";
                String contraseña="";

                connect = DriverManager.getConnection(url, prop);


            }
            if(this.getDataBase()==DataBase.MySQL){
                //Carga el controlador de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                String url="";
                String usuario="";
                String contraseña="";
                connect = DriverManager.getConnection(url, prop);
            }

            if(this.getDataBase()==DataBase.SQLServer){
                //Carga el controlador de SQLServer
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="";
                String usuario="";
                String contraseña="";
                connect = DriverManager.getConnection(url, prop);
            }


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
        System.setProperty("DataBase",dataBase.name());
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

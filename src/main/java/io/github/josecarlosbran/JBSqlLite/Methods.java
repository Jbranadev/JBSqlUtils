package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB;
import io.github.josecarlosbran.LogsJB.LogsJB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Methods extends UtilitiesJB {

    private DataBase dataBase=setearDB();
    private String host=setearHost();
    private String port=setearPort();
    private String user=setearUser();
    private String password=setearPassword();
    private Boolean getPropertySystem=true;


    public Methods() throws DataBaseUndefind, PropertiesDBUndefined {

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
        if(this.getGetPropertySystem()){
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
        }
        return null;
    }

    private String setearHost() throws PropertiesDBUndefined {
        if(this.getGetPropertySystem()){
            String host=System.getProperty("DataBase.Host");
            if(stringIsNullOrEmpty(host)){
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new PropertiesDBUndefined("No se a seteado el host en el que se encuentra la BD's a la cual deseamos se pegue JBSqlUtils");
            }else{
                this.setHost(host);
            }
        }
        return null;
    }

    private String setearPort() throws PropertiesDBUndefined {
        if(this.getGetPropertySystem()){
            String port=System.getProperty("DataBase.Port");
            if(stringIsNullOrEmpty(port)){
                if(this.getDataBase()!=DataBase.SQLite){
                    //Si la propiedad del sistema no esta definida, Lanza una Exepción
                    throw new PropertiesDBUndefined("No se a seteado el puerto en el que se encuentra escuchando la BD's a la cual deseamos se pegue JBSqlUtils");
                }
            }else{
                this.setPort(port);
            }
        }
        return null;
    }

    private String setearUser() throws PropertiesDBUndefined {
        if(this.getGetPropertySystem()){
            String user=System.getProperty("DataBase.User");
            if(stringIsNullOrEmpty(user)){
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new PropertiesDBUndefined("No se a seteado el usuario de la BD's a la cual deseamos se pegue JBSqlUtils");
            }else{
                this.setUser(user);
            }
        }
        return null;
    }

    private String setearPassword() throws PropertiesDBUndefined {
        if(this.getGetPropertySystem()){
            String password=System.getProperty("DataBase.Password");
            if(stringIsNullOrEmpty(password)){
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new PropertiesDBUndefined("No se a seteado la contraseña del usuario de la BD's a la cual deseamos se pegue JBSqlUtils");
            }else{
                this.setPassword(password);
            }
        }
        return null;
    }

    public Connection getConnection(String DB){
        Connection connect=null;
        try{
            String url1="jdbc:"+this.getDataBase().getDBType();
            if(this.getDataBase()==DataBase.PostgreSQL){
                //Carga el controlador de PostgreSQL
                Class.forName("org.postgresql.Driver");
                String url="";
                String usuario="";
                String contraseña="";

                //connect = DriverManager.getConnection(url, prop);



            }
            if(this.getDataBase()==DataBase.MySQL){
                //Carga el controlador de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                String url="";
                String usuario="";
                String contraseña="";
                //connect = DriverManager.getConnection(url, prop);
            }

            if(this.getDataBase()==DataBase.SQLServer){
                //Carga el controlador de SQLServer
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="";
                String usuario="";
                String contraseña="";
                //connect = DriverManager.getConnection(url, prop);
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
        if(this.getGetPropertySystem()){
            System.setProperty("DataBase",dataBase.name());
            System.out.println("SystemProperty Seteada: "+System.getProperty("DataBase"));
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
        if(this.getGetPropertySystem()){
            System.setProperty("DataBase.Host",host);
            System.out.println("SystemProperty Seteada: "+System.getProperty("DataBase.Host"));
        }
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
        if(this.getGetPropertySystem()){
            System.setProperty("DataBase.Port",port);
            System.out.println("SystemProperty Seteada: "+System.getProperty("DataBase.Port"));
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        if(this.getGetPropertySystem()){
            System.setProperty("DataBase.User",user);
            System.out.println("SystemProperty Seteada: "+System.getProperty("DataBase.User"));
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        if(this.getGetPropertySystem()){
            System.setProperty("DataBase.Password",password);
            System.out.println("SystemProperty Seteada: "+System.getProperty("DataBase.Password"));
        }
    }

    public Boolean getGetPropertySystem() {
        return getPropertySystem;
    }

    public void setGetPropertySystem(Boolean getPropertySystem) {
        this.getPropertySystem = getPropertySystem;
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

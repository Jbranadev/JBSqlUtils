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
import java.util.Objects;
import java.util.Properties;

public class Methods extends UtilitiesJB {
    //https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-usagenotes-connect-drivermanager.html#connector-j-examples-connection-drivermanager
    //https://docs.microsoft.com/en-us/sql/connect/jdbc/using-the-jdbc-driver?view=sql-server-ver16
    //https://www.dev2qa.com/how-to-load-jdbc-configuration-from-properties-file-example/
    //https://www.tutorialspoint.com/how-to-connect-to-postgresql-database-using-a-jdbc-program





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
/*
    public Connection getConnection(){
        Connection connect=null;
        try{
            String url1="jdbc:"+this.getDataBaseType().getDBType()+ "://"+
                    this.getHost()+":"+this.getPort()+"/";
            if(this.getDataBaseType()==DataBase.PostgreSQL){
                //Carga el controlador de PostgreSQL
                Class.forName("org.postgresql.Driver");
                String url="";
                String usuario="";
                String contraseña="";

                //connect = DriverManager.getConnection(url, prop);



            }
            if(this.getDataBaseType()==DataBase.MySQL){
                //Carga el controlador de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                String url="";
                String usuario="";
                String contraseña="";
                //connect = DriverManager.getConnection(url, prop);
            }

            if(this.getDataBaseType()==DataBase.SQLServer){
                //Carga el controlador de SQLServer
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="";
                String usuario="";
                String contraseña="";
                //connect = DriverManager.getConnection(url, prop);
            }


            //connect = DriverManager.getConnection("jdbc:sqlite:"+DB);
            connect.setAutoCommit(false);
            if (connect!=null) {
                //LogsJB.info("Conexión a BD's "+ DB+" Realizada exitosamente" );
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
*/


}

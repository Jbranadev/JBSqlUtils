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

public class Methods extends Conexion {
    public Methods() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }
    //https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-usagenotes-connect-drivermanager.html#connector-j-examples-connection-drivermanager
    //https://docs.microsoft.com/en-us/sql/connect/jdbc/using-the-jdbc-driver?view=sql-server-ver16
    //https://www.dev2qa.com/how-to-load-jdbc-configuration-from-properties-file-example/
    //https://www.tutorialspoint.com/how-to-connect-to-postgresql-database-using-a-jdbc-program


    /**
     * Obtiene la conexión del modelo a la BD's con las propiedades definidas.
     * @return Retorna la conexión del modelo a la BD's con las propiedades definidas.
     */
    public Connection getConnection(){
        Connection connect=null;
        try{
            String url=null;
            if(this.getDataBaseType()==DataBase.PostgreSQL){
                //Carga el controlador de PostgreSQL
                url=null;
                connect=null;
                Class.forName("org.postgresql.Driver");
                url="jdbc:"+this.getDataBaseType().getDBType()+ "://"+
                        this.getHost()+":"+this.getPort()+"/"+this.getBD();
                String usuario=this.getUser();
                String contraseña=this.getPassword();
                connect = DriverManager.getConnection(url, usuario, contraseña);
            }else if(this.getDataBaseType()==DataBase.MySQL){
                url=null;
                connect=null;
                //Carga el controlador de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                url="jdbc:"+this.getDataBaseType().getDBType()+ "://"+
                        this.getHost()+":"+this.getPort()+"/"+this.getBD();
                String usuario=this.getUser();
                String contraseña=this.getPassword();
                connect = DriverManager.getConnection(url, usuario, contraseña);
            }else if(this.getDataBaseType()==DataBase.SQLServer){
                url=null;
                connect=null;
                //Carga el controlador de SQLServer
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                url="jdbc:"+this.getDataBaseType().getDBType()+ "://"+
                        this.getHost()+":"+this.getPort()+";databaseName="+this.getBD();
                String usuario=this.getUser();
                String contraseña=this.getPassword();
                connect = DriverManager.getConnection(url, usuario, contraseña);
            }else if(this.getDataBaseType()==DataBase.SQLite){
                url=null;
                connect=null;
                url="jdbc:"+this.getDataBaseType().getDBType()+":"+this.getBD();
                connect = DriverManager.getConnection(url);
            }

            if (!Objects.isNull(connect)) {
                LogsJB.info("Conexión a BD's "+ this.getBD()+" Realizada exitosamente" );
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

    /**
     * Cierra la conexión a BD's
     * @param connect Conexión que se desea cerrar.
     */
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




}

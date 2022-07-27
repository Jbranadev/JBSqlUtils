package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.LogsJB.LogsJB;

import java.sql.Connection;
import java.sql.DriverManager;

public class Methods {
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

}

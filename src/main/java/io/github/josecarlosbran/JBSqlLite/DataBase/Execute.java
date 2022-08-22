package io.github.josecarlosbran.JBSqlLite.DataBase;


import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;
import io.github.josecarlosbran.LogsJB.LogsJB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class Execute extends Methods_Conexion {
    private String sql;

    public Execute(String sql) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(sql)) {
            throw new ValorUndefined("La cadena que contiene la sentencia SQL esta vacío o es NULL");
        }
        this.sql=sql;
    }


    public int execute(){
        int result = 0;
        try {
            Callable<Integer> Ejecutar_Sentencia = () -> {
                int filas=0;
                try {
                    Connection connect=this.getConnection();
                    LogsJB.info(this.sql);
                    PreparedStatement ejecutor = connect.prepareStatement(this.sql);
                    if (ejecutor.executeUpdate() == 1) {
                        filas = ejecutor.getUpdateCount();
                        LogsJB.info("Cantidad de filas afectadas: " + filas);
                    }
                    this.closeConnection(connect);
                    return filas;

                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que ejecuta la sentencia SQL transmitida: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    LogsJB.fatal("Sentencia SQL: " + this.sql);
                }
                return filas;
            };
            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<Integer> future = executor.submit(Ejecutar_Sentencia);
            while (!future.isDone()) {

            }
            executor.shutdown();
            result = future.get();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que ejecuta la sentencia SQL transmitida: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            LogsJB.fatal("Sentencia SQL: " + this.sql);
        }
        return result;
    }



}

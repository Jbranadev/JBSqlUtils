package io.github.josecarlosbran.JBSqlLite.Search;


import io.github.josecarlosbran.JBSqlLite.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlLite.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;
import io.github.josecarlosbran.LogsJB.LogsJB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class Where<T> extends GET {
    private String sql;
    private T modelo;

    public Where(String columna, Operator operador, String valor, T modelo) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        String respuesta = "";
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (stringIsNullOrEmpty(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        this.modelo=modelo;
        this.sql = respuesta+" WHERE "+Operator.OPEN_PARENTESIS.getOperador()+columna + operador.getOperador() + valor+Operator.CLOSE_PARENTESIS.getOperador();
    }






    public void getFirst() {
        try {
            this.setTaskIsReady(false);
            if (!this.getTableExist()) {
                this.refresh();
            }
            Connection connect = this.getConnection();
            Runnable get = () -> {
                try {
                    if (this.getTableExist()) {
                        String sql = "SELECT * FROM " + this.getClass().getSimpleName();
                        if (!stringIsNullOrEmpty(this.sql)) {
                            sql = sql + this.sql;
                        }
                        sql = sql + ";";
                        LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        ResultSet registros = ejecutor.executeQuery();
                        if (registros.next()) {
                            procesarResultSet(this, registros);
                        }
                        this.closeConnection(connect);
                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "recuperar el Registro");
                    }
                    this.setTaskIsReady(true);
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    this.setTaskIsReady(true);
                }
            };
            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            ejecutor.submit(get);
            ejecutor.shutdown();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }



    public And and(String columna, Operator operador, String valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new And(this.sql, columna, operador, valor, this.modelo);
    }

    public Or or(String columna, Operator operador, String valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Or(this.sql, columna, operador, valor, this.modelo);
    }

    public And and(String expresion) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new And(this.sql, expresion, this.modelo);
    }

    public Or or(String expresion) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Or(this.sql, expresion, this.modelo);
    }

    public OrderBy orderBy(String columna, OrderType orderType) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new OrderBy(this.sql, columna, orderType, this.modelo);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public <T extends Methods_Conexion> void get(){
        super.get((T)this.modelo, this.sql);
    }

    public <T extends Methods_Conexion> T first(){
        return (T) super.first((T) this.modelo, this.sql);
    }

    public <T extends Methods_Conexion> T firstOrFail() throws ModelNotFound {
        return (T) super.firstOrFail((T)this.modelo, this.sql);
    }

    public <T extends Methods_Conexion> List<T> getAll() throws InstantiationException, IllegalAccessException {
        return (List<T>) super.getAll((T)this.modelo, this.sql);
    }










}

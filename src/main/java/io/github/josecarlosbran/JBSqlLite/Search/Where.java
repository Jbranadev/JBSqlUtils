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

public class Where extends GET {
    private String sql;

    public Where(String columna, Operator operador, String valor) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
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
        this.sql = respuesta+" WHERE "+Operator.OPEN_PARENTESIS.getOperador()+columna + operador.getOperador() + valor+Operator.CLOSE_PARENTESIS.getOperador();
    }

    public Where() throws DataBaseUndefind, PropertiesDBUndefined {
        super();

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
        return new And(this.sql, columna, operador, valor);
    }

    public Or or(String columna, Operator operador, String valor) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Or(this.sql, columna, operador, valor);
    }

    public And and(String expresion) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new And(this.sql, expresion);
    }

    public Or or(String expresion) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new Or(this.sql, expresion);
    }

    public OrderBy orderBy(String columna, OrderType orderType) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return new OrderBy(this.sql, columna, orderType);
    }

    public <T extends Methods_Conexion> void get(T modelo){
        super.get(modelo, this.sql);
    }

    public <T extends Methods_Conexion> T first(T modelo){
        return (T) super.first(modelo, this.sql);
    }

    public <T extends Methods_Conexion> T firstOrFail(T modelo) throws ModelNotFound {
        return (T) super.firstOrFail(modelo, this.sql);
    }

    public <T extends Methods_Conexion> List<T> getAll(T modelo) throws InstantiationException, IllegalAccessException {
        return super.getAll(modelo, this.sql);
    }











}

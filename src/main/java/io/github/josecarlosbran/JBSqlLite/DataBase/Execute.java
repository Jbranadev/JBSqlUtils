/***
 * Copyright (C) 2022 El proyecto de código abierto JBSqlUtils de José Bran
 *
 * Con licencia de Apache License, Versión 2.0 (la "Licencia");
 * no puede usar este archivo excepto de conformidad con la Licencia.
 * Puede obtener una copia de la Licencia en
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * A menos que lo exija la ley aplicable o se acuerde por escrito, el software
 * distribuido bajo la Licencia se distribuye "TAL CUAL",
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
 * Consulte la Licencia para conocer el idioma específico que rige los permisos y
 * limitaciones bajo la Licencia.
 */
package io.github.josecarlosbran.JBSqlLite.DataBase;


import io.github.josecarlosbran.JBSqlLite.Column;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlLite.Methods_Conexion;
import io.github.josecarlosbran.LogsJB.LogsJB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * @author Jose Bran
 * Clase que proporciona el metodo para ejecutar sentencias SQL sin necesidad de instanciar
 * un modelo u obtenerlo de BD's
 */
class Execute extends Methods_Conexion {
    private String sql;

    /**
     * Lista de los parametros a envíar
     */
    protected List<Column> parametros = new ArrayList<>();

    /**
     * Constructor que recibe como parametro:
     * @param sql Sentencia SQL a ser Ejecutada
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de
     * BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las
     * propiedades de conexión necesarias para conectarse a la BD's especificada.
     */
    public Execute(String sql, List<Column> parametros) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(sql)) {
            throw new ValorUndefined("La cadena que contiene la sentencia SQL esta vacío o es NULL");
        }
        this.parametros = parametros;
        this.sql=sql;
    }

    /**
     * Ejecuta la sentencia SQL que recibe la clase al ser instanciada.
     * @return Retorna la cantidad de filas que se han visto afectadas al ejecutar la sentencia SQL.
     */
    public int execute(){
        int result = 0;
        try {
            Callable<Integer> Ejecutar_Sentencia = () -> {
                int filas=0;
                try {
                    Connection connect=this.getConnection();
                    this.sql=this.sql+";";
                    //LogsJB.info(this.sql);
                    PreparedStatement ejecutor = connect.prepareStatement(this.sql);

                    //Setea los parametros de la consulta
                    for(int i=0; i< this.parametros.size(); i++){
                        //Obtengo la información de la columna
                        Column columnsSQL = this.parametros.get(i);
                        convertJavaToSQL(columnsSQL, ejecutor, i+1);
                    }

                    LogsJB.info(ejecutor.toString());

                    ejecutor.executeUpdate();
                    filas = ejecutor.getUpdateCount();
                    LogsJB.info("Cantidad de filas afectadas: " + filas);

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

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
package io.github.josecarlosbran.JBSqlUtils.DataBase;

import com.josebran.LogsJB.LogsJB;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Jose Bran
 * Clase que proporciona metodos que permiten al modelo ejecutar sentencias DML, así como crear o eliminar la tabla
 * correspondiente al modelo
 */
class Methods extends Methods_Conexion {
    /**
     * Constructor por default de la clase Methods
     */
    protected Methods() {
        super();
    }

    /**
     * Constructor por default de la clase Methods
     *
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    protected Methods(Boolean getPropertySystem) {
        super(getPropertySystem);
    }

    /**
     * Almacena la información del modelo que hace el llamado en BD's.'
     *
     * @return La cantidad de filas insertadas o actualizadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public Integer save() throws Exception {
        return saveModel(this);
    }

    /**
     * Almacena la información de los modelos proporcionados en BD's
     *
     * @param modelos Lista de modelos que serán Insertados o Actualizados
     * @param <T>     Tipo de parámetro que hace que el método sea generico para poder ser
     *                llamado por diferentes tipos de objetos, siempre y cuando estos hereden la clase Methods Conexion.
     * @return La cantidad de filas insertadas o actualizadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> Integer saveALL(List<T> modelos) throws Exception {
        Integer result = 0;
        T temp = null;
        boolean tableInfoCached = false;
        for (T modelo : modelos) {
            if (tableInfoCached) {
                modelo.setTabla(temp.getTabla());
                modelo.setTableExist(temp.getTableExist());
                modelo.setTableName(temp.getTableName());
                modelo.getTabla().setColumnsExist(temp.getTabla().getColumnsExist());
                modelo.llenarPropertiesFromModel(temp);
            } else {
                modelo.llenarPropertiesFromModel(this);
                temp = this.obtenerInstanciaOfModel(modelo);
                temp.refresh();
                tableInfoCached = true;
            }
            result = result + modelo.saveModel(modelo);
        }
        return result;
    }

    /**
     * Elimina la información del modelo que hace el llamado en BD´s
     *
     * @return La cantidad de filas eliminadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public Integer delete() throws Exception {
        return deleteModel(this);
    }

    /**
     * Elimina la información de los modelos proporcionados en BD's
     *
     * @param modelos Lista de modelos que serán Eliminados
     * @param <T>     Tipo de parámetro que hace que el método sea generico para poder ser
     *                llamado por diferentes tipos de objetos, siempre y cuando estos hereden la clase Methods Conexion.
     * @return La cantidad de filas eliminadas en BD's
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> Integer deleteALL(List<T> modelos) throws Exception {
        Integer result = 0;
        T temp = null;
        boolean tableInfoCached = false;
        for (T modelo : modelos) {
            if (tableInfoCached) {
                modelo.setTabla(temp.getTabla());
                modelo.setTableExist(temp.getTableExist());
                modelo.setTableName(temp.getTableName());
                modelo.llenarPropertiesFromModel(temp);
            } else {
                modelo.llenarPropertiesFromModel(this);
                temp = this.obtenerInstanciaOfModel(modelo);
                temp.refresh();
                tableInfoCached = true;
            }
            result = result + modelo.deleteModel(modelo);
        }
        return result;
    }

    /**
     * Almacena la información del modelo que hace el llamado, esperando a que la operación termine de ser realizada
     *
     * @return Retorna True cuando se a terminado de insertar o actualizar la información del modelo en BD's
     */
    public Boolean saveBoolean() throws Exception {
        Integer resultado = saveModel(this);
        this.waitOperationComplete();
        return resultado >= 1;
    }

    /**
     * Proporciona un punto de entrada para obtener uno o mas modelos del tipo de modelo que invoca este procedimiento
     *
     * @param columna  Columna que sera evaluada
     * @param operador Operador por medio del cual se evaluara la columna
     * @param valor    Valor contra el cual se evaluara la columna
     * @return Punto de entrada a metodos que permiten seguir modificando la expresión de filtro u obtener el o los
     * modelos que hacen match con la consulta generada
     * @throws ValorUndefined Lanza esta excepción si alguno de los parametros proporcionados esta
     *                        Vacío o es Null
     */
    public Where where(String columna, Operator operador, Object valor) throws ValorUndefined {
        if (!this.getGetPropertySystem()) {
            Where where = new Where(columna, operador, valor, this, false);
            return where;
        }
        return new Where(columna, operador, valor, this);
    }

    /**
     * Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return Retorna una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> List<T> getAll() throws Exception {
        this.setTaskIsReady(false);
        List<T> lista;
        this.validarTableExist(this);
        Callable<ResultAsync<List<T>>> get = () -> {
            List<T> listatemp = new ArrayList<T>();
            try (Connection connect = this.getConnection();) {
                String sql = "SELECT * FROM " + this.getTableName();
                sql = sql + ";";
                LogsJB.info(sql);
                PreparedStatement ejecutor = connect.prepareStatement(sql);
                ResultSet registros = ejecutor.executeQuery();
                while (registros.next()) {
                    listatemp.add(procesarResultSet((T) this, registros));
                }
                this.closeConnection(connect);
                return new ResultAsync<>(listatemp, null);
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que Recupera la lista de registros que cumplen con la sentencia" +
                        "SQL de la BD's, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
                return new ResultAsync<>(listatemp, e);
            }
        };
        Future<ResultAsync<List<T>>> future = ejecutor.submit(get);
        while (!future.isDone()) {
        }
        ResultAsync<List<T>> resultado = future.get();
        this.setTaskIsReady(true);
        if (!Objects.isNull(resultado.getException())) {
            throw resultado.getException();
        }
        lista = resultado.getResult();
        return lista;
    }

    /**
     * Setea null en el campo valor de cada columna que posee el modelo.
     *
     * @param <T> Tipo de dato del modelo, acepta unicamente aquellos que heredan de la clase JBSqlUtils
     */
    public <T extends JBSqlUtils> void cleanModel() {
        try {
            this.setModelExist(false);
            //Obtiene los metodos get del modelo
            List<Field> campos = this.getFieldsOfModel();
            for (Field campo : campos) {
                if (campo.getType().isAssignableFrom(String.class)) {
                    //Caracteres y cadenas de Texto
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Double.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Integer.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Float.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Boolean.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(byte[].class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Date.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Time.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else if (campo.getType().isAssignableFrom(Timestamp.class)) {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                } else {
                    FieldUtils.writeField(this, campo.getName(), null, true);
                }
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al limpiar el modelo, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }
}

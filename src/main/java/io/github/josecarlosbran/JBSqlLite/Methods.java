package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Utilities.ColumnsSQL;
import io.github.josecarlosbran.LogsJB.LogsJB;
import org.apache.commons.lang3.StringUtils;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB.stringIsNullOrEmpty;

public class Methods extends Methods_Conexion {
    public Methods() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }

    //https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-usagenotes-connect-drivermanager.html#connector-j-examples-connection-drivermanager
    //https://docs.microsoft.com/en-us/sql/connect/jdbc/using-the-jdbc-driver?view=sql-server-ver16
    //https://www.dev2qa.com/how-to-load-jdbc-configuration-from-properties-file-example/
    //https://www.tutorialspoint.com/how-to-connect-to-postgresql-database-using-a-jdbc-program


    /**
     * Crea la tabla correspondiente al modelo en BD's si esta no existe.
     *
     * @return True si la tabla correspondiente al modelo en BD's no existe y fue creada exitosamente,
     * False si la tabla correspondiente al modelo ya existe en BD's
     */
    public Boolean crateTable() {
        Boolean result = false;
        try {
            Callable<Boolean> createtabla = () -> {
                try {
                    if (this.tableExist()) {
                        LogsJB.info("La tabla correspondiente al modelo ya existe en la BD's, por lo cual no será creada.");
                        return false;
                    } else {
                        String sql = "CREATE TABLE " + this.getClass().getSimpleName() + "(";
                        List<Method> metodos = new ArrayList<>();
                        metodos = this.getMethodsGetOfModel(this.getMethodsModel());
                        for (int i = 0; i < metodos.size(); i++) {
                            //Obtengo el metodo
                            Method metodo = metodos.get(i);
                            //Obtengo la información de la columna
                            Column columnsSQL = (Column) metodo.invoke(this, null);
                            String columnName = metodo.getName();
                            columnName = StringUtils.remove(columnName, "get");
                            DataType columnType = columnsSQL.getDataTypeSQL();
                            Constraint[] columnRestriccion = columnsSQL.getRestriccion();
                            String restricciones = "";
                            String tipo_de_columna = columnType.toString();
                            if ((((this.getDataBaseType() == DataBase.PostgreSQL)) || ((this.getDataBaseType() == DataBase.MySQL))
                                    || ((this.getDataBaseType() == DataBase.SQLite))) &&
                                    (columnType == DataType.BIT)) {
                                tipo_de_columna = DataType.BOOLEAN.toString();
                            }
                            if (!Objects.isNull(columnRestriccion)) {
                                for (Constraint restriccion : columnRestriccion) {
                                    if ((DataBase.PostgreSQL == this.getDataBaseType()) &&
                                            (restriccion == Constraint.AUTO_INCREMENT)) {
                                        tipo_de_columna = DataType.SERIAL.name();
                                    } else if ((DataBase.SQLServer == this.getDataBaseType()) &&
                                            (restriccion == Constraint.AUTO_INCREMENT)) {
                                        //tipo_de_columna = DataType.IDENTITY.toString();
                                        restricciones = restricciones + DataType.IDENTITY.toString() + " ";
                                    } else if ((DataBase.SQLite == this.getDataBaseType()) &&
                                            (restriccion == Constraint.AUTO_INCREMENT)) {
                                        restricciones = restricciones + "";
                                    } else {
                                        restricciones = restricciones + restriccion.getRestriccion() + " ";
                                    }
                                }
                            }

                            String columna = columnName + " " + tipo_de_columna + " " + restricciones;


                            sql = sql + columna;
                            int temporal = metodos.size() - 1;
                            if (i < temporal) {
                                sql = sql + ", ";
                            } else if (i == temporal) {
                                sql = sql + ");";
                            }

                        }
                        Connection connect = this.getConnection();
                        Statement ejecutor = connect.createStatement();
                        LogsJB.info(sql);
                        if (!ejecutor.execute(sql)) {
                            LogsJB.info("Sentencia para crear tabla de la BD's ejecutada exitosamente");
                            LogsJB.info("Tabla " + this.getClass().getSimpleName() + " Creada exitosamente");
                            LogsJB.info(sql);

                            this.closeConnection(connect);
                            this.refresh();
                            return true;
                        }
                        ejecutor.close();
                        this.closeConnection(connect);

                    }
                    return false;
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Crea la tabla correspondiente al modelo: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                }
                return false;
            };

            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            Future<Boolean> future = ejecutor.submit(createtabla);
            while (!future.isDone()) {

            }
            ejecutor.shutdown();
            result = future.get();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Crea la tabla correspondiente al modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }

    /**
     * Elimina la tabla correspondiente al modelo en BD's
     *
     * @return True si la tabla correspondiente al modelo en BD's existe y fue eliminada, de no existir la tabla correspondiente
     * en BD's retorna False.
     */
    public Boolean dropTableIfExist() {
        Boolean result = false;
        try {
            Connection connect = this.getConnection();
            Callable<Boolean> dropTable = () -> {
                try {
                    if (this.getTableExist()) {
                        String sql = "";
                        if (this.getDataBaseType() == DataBase.MySQL || this.getDataBaseType() == DataBase.PostgreSQL || this.getDataBaseType() == DataBase.SQLite) {
                            sql = "DROP TABLE IF EXISTS " + this.getClass().getSimpleName();
                            //+ " RESTRICT";
                        } else if (this.getDataBaseType() == DataBase.SQLServer) {
                            sql = "if exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '" +
                                    this.getClass().getSimpleName() +
                                    "' AND TABLE_SCHEMA = 'dbo')\n" +
                                    "    drop table dbo." +
                                    this.getClass().getSimpleName();
                            //+" RESTRICT;";
                        }
                        LogsJB.info(sql);

                        Statement ejecutor = connect.createStatement();

                        if (!ejecutor.execute(sql)) {
                            LogsJB.info("Sentencia para eliminar tabla de la BD's ejecutada exitosamente");
                            LogsJB.info("Tabla " + this.getClass().getSimpleName() + " Eliminada exitosamente");
                            LogsJB.info(sql);
                            //this.setTableExist(false);
                            this.refresh();
                            return true;
                        }
                        ejecutor.close();
                        this.closeConnection(connect);

                    } else {
                        LogsJB.info("Tabla correspondiente al modelo no existe en BD's por eso no pudo ser eliminada");
                        return false;
                    }
                    return false;
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Elimina la tabla correspondiente al modelo: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                }
                return false;
            };

            ExecutorService ejecutor = Executors.newFixedThreadPool(1);
            Future<Boolean> future = ejecutor.submit(dropTable);
            while (!future.isDone()) {

            }
            ejecutor.shutdown();
            result = future.get();
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Elimina la tabla correspondiente al modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }

    /**
     * Almacena la información del modelo que hace el llamado en BD's.'
     */
    public void save() {
        try {
            saveModel(this);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }


    public void get(String expresion) {
        try {
            this.setTaskIsReady(false);
            if (!this.getTableExist()) {
                this.refresh();
            }
            Connection connect = this.getConnection();
            Runnable get = () -> {
                try {
                    if (this.getTableExist()) {
                        String sql = "SELECT * FROM " + this.getTableName() + " ";
                        if(!stringIsNullOrEmpty(expresion)){
                            sql= sql +expresion;
                        }
                        sql=sql+";";
                        LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        ResultSet registros=ejecutor.executeQuery();
                        if (registros.next()) {
                            LogsJB.debug("Obtuvo un resultado de BD's, procedera a llenar el modelo");
                            List<Method> metodosSet = new ArrayList<>();
                            LogsJB.trace("Inicializa el array list de los metodos set");
                            metodosSet = this.getMethodsSetOfModel(this.getMethodsModel());
                            LogsJB.trace("obtuvo los metodos set");
                            LogsJB.debug("Cantidad de columnas : "+this.getColumnas().size());
                            //Llena la información del modelo
                            for(int i=0; i < this.getColumnas().size(); i++){
                                ColumnsSQL columna=this.getColumnas().get(i);
                                String columnName=columna.getCOLUMN_NAME();
                                LogsJB.trace("Columna : "+columnName);
                                LogsJB.debug("Cantidad de metodos set: "+metodosSet.size());
                                //Recorrera los metodos set del modelo para ver cual es el que corresponde a la columna
                                for (int j = 0; j < metodosSet.size(); j++) {
                                    Method metodo= metodosSet.get(j);
                                    String metodoName = metodo.getName();
                                    metodoName = StringUtils.remove(metodoName, "set");
                                    if(StringUtils.equalsIgnoreCase(metodoName, columnName)){
                                        LogsJB.trace("Nombre de la columna, nombre del metodo set: "+columnName+"   "+metodoName);
                                        List<Method> metodosget = new ArrayList<>();
                                        metodosget=this.getMethodsGetOfModel(this.getMethodsModel());
                                        LogsJB.trace("Cantidad de metodos get: "+metodosget.size());
                                        //Llena la información de las columnas que se insertaran
                                        for (int a = 0; a < metodosget.size(); a++) {
                                            //Obtengo el metodo
                                            Method metodoget = metodosget.get(a);
                                            //Obtengo la información de la columna
                                            Column columnsSQL = (Column) metodoget.invoke(this, null);
                                            String NameMetodoGet = metodoget.getName();
                                            NameMetodoGet = StringUtils.remove(NameMetodoGet, "get");
                                            if(StringUtils.equalsIgnoreCase(NameMetodoGet, columnName)){
                                                LogsJB.trace("Nombre de la columna, nombre del metodo get: "+columnName+"   "+NameMetodoGet);
                                                LogsJB.debug("Coincide el nombre de los metodos con la columna: "+columnName);

                                                convertSQLtoJava(columna, registros, metodo, columnsSQL);
                                            }
                                        }
                                    }
                                }


                            }
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

/*
    void leerCachedRow(){

        Runnable mapear = () -> {
            try{

                T temp= (T) modelo.getClass().newInstance();
                temp.setColumnas(modelo.getColumnas());
                LogsJB.debug("Obtuvo un resultado de BD's, procedera a llenar el modelo");
                List<Method> metodosSet = new ArrayList<>();
                LogsJB.trace("Inicializa el array list de los metodos set");
                metodosSet = temp.getMethodsSetOfModel(temp.getMethodsModel());
                LogsJB.trace("obtuvo los metodos set");
                LogsJB.debug("Cantidad de columnas : "+temp.getColumnas().size());

                //Llena la información del modelo
                for(int i=0; i < temp.getColumnas().size(); i++){
                    ColumnsSQL columna=temp.getColumnas().get(i);
                    String columnName=columna.getCOLUMN_NAME();
                    LogsJB.trace("Columna : "+columnName);
                    LogsJB.debug("Cantidad de metodos set: "+metodosSet.size());
                    //Recorrera los metodos set del modelo para ver cual es el que corresponde a la columna
                    for (int j = 0; j < metodosSet.size(); j++) {
                        Method metodo= metodosSet.get(j);
                        String metodoName = metodo.getName();
                        metodoName = StringUtils.remove(metodoName, "set");
                        if(StringUtils.equalsIgnoreCase(metodoName, columnName)){
                            LogsJB.trace("Nombre de la columna, nombre del metodo set: "+columnName+"   "+metodoName);
                            List<Method> metodosget = new ArrayList<>();
                            metodosget=temp.getMethodsGetOfModel(temp.getMethodsModel());
                            LogsJB.trace("Cantidad de metodos get: "+metodosget.size());
                            //Llena la información de las columnas que se insertaran
                            for (int a = 0; a < metodosget.size(); a++) {
                                //Obtengo el metodo
                                Method metodoget = metodosget.get(a);
                                //Obtengo la información de la columna
                                Column columnsSQL = (Column) metodoget.invoke(temp, null);
                                String NameMetodoGet = metodoget.getName();
                                NameMetodoGet = StringUtils.remove(NameMetodoGet, "get");
                                if(StringUtils.equalsIgnoreCase(NameMetodoGet, columnName)){
                                    LogsJB.trace("Nombre de la columna, nombre del metodo get: "+columnName+"   "+NameMetodoGet);
                                    LogsJB.debug("Coincide el nombre de los metodos con la columna: "+columnName);

                                    convertSQLtoJava(columna, resulttemp, metodo, columnsSQL);
                                }
                            }
                        }
                    }
                }
                lista.add(temp);
            }catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que mapea los resultados del modelo de la BD's: " + e.toString());
                LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            }
        };
        mapeador.submit(mapear);
    }
    */

    public <T extends Methods_Conexion> List<T> getALL(T modelo) {
        modelo.setTaskIsReady(false);
        List<T> lista=new ArrayList<>();
        try {
            if (!modelo.getTableExist()) {
                modelo.refresh();
            }
            Connection connect = modelo.getConnection();
            Runnable get = () -> {
                try {
                    if (modelo.getTableExist()) {
                        String sql = "SELECT * FROM " + modelo.getTableName() + ";";
                        LogsJB.info(sql);
                        PreparedStatement ejecutor = connect.prepareStatement(sql);
                        ResultSet registros = ejecutor.executeQuery();
                        CachedRowSet filas = RowSetProvider.newFactory().createCachedRowSet();
                        filas.populate(registros);

                        ExecutorService mapeador = Executors.newFixedThreadPool(10);
                        for (int z = 1; z < filas.size(); z++) {
                            int indicefila=z;
                            Runnable mapear = () -> {
                                try{
                                    T temp = (T) modelo.getClass().newInstance();
                                    temp.setColumnas(modelo.getColumnas());
                                    LogsJB.debug("Obtuvo un resultado de BD's, procedera a llenar el modelo");
                                    List<Method> metodosSet = new ArrayList<>();
                                    LogsJB.trace("Inicializa el array list de los metodos set");
                                    metodosSet = temp.getMethodsSetOfModel(temp.getMethodsModel());
                                    LogsJB.trace("obtuvo los metodos set");
                                    LogsJB.debug("Cantidad de columnas : " + temp.getColumnas().size());
                                    //Llena la información del modelo
                                    for (int i = 0; i < temp.getColumnas().size(); i++) {
                                        ColumnsSQL columna = temp.getColumnas().get(i);
                                        String columnName = columna.getCOLUMN_NAME();
                                        LogsJB.trace("Columna : " + columnName);
                                        LogsJB.debug("Cantidad de metodos set: " + metodosSet.size());
                                        //Recorrera los metodos set del modelo para ver cual es el que corresponde a la columna
                                        for (int j = 0; j < metodosSet.size(); j++) {
                                            Method metodo = metodosSet.get(j);
                                            String metodoName = metodo.getName();
                                            metodoName = StringUtils.remove(metodoName, "set");
                                            if (StringUtils.equalsIgnoreCase(metodoName, columnName)) {
                                                LogsJB.trace("Nombre de la columna, nombre del metodo set: " + columnName + "   " + metodoName);
                                                List<Method> metodosget = new ArrayList<>();
                                                metodosget = temp.getMethodsGetOfModel(temp.getMethodsModel());
                                                LogsJB.trace("Cantidad de metodos get: " + metodosget.size());
                                                //Llena la información de las columnas que se insertaran
                                                for (int a = 0; a < metodosget.size(); a++) {
                                                    //Obtengo el metodo
                                                    Method metodoget = metodosget.get(a);
                                                    //Obtengo la información de la columna
                                                    Column columnsSQL = (Column) metodoget.invoke(temp, null);
                                                    String NameMetodoGet = metodoget.getName();
                                                    NameMetodoGet = StringUtils.remove(NameMetodoGet, "get");
                                                    if (StringUtils.equalsIgnoreCase(NameMetodoGet, columnName)) {
                                                        LogsJB.trace("Nombre de la columna, nombre del metodo get: " + columnName + "   " + NameMetodoGet);
                                                        LogsJB.debug("Coincide el nombre de los metodos con la columna: " + columnName);
                                                        convertSQLtoJava(columna, filas, metodo, columnsSQL, indicefila);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    lista.add(temp);
                                }catch (Exception e) {
                                    LogsJB.fatal("Excepción disparada en el método que mapea los resultados del modelo de la BD's: " + e.toString());
                                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                                }
                            };
                            mapeador.submit(mapear);

                        }


                        /*
                        while(registros.next()) {
                            T temp= (T) modelo.getClass().newInstance();
                            temp.setColumnas(modelo.getColumnas());
                            LogsJB.debug("Obtuvo un resultado de BD's, procedera a llenar el modelo");
                            List<Method> metodosSet = new ArrayList<>();
                            LogsJB.trace("Inicializa el array list de los metodos set");
                            metodosSet = temp.getMethodsSetOfModel(temp.getMethodsModel());
                            LogsJB.trace("obtuvo los metodos set");
                            LogsJB.debug("Cantidad de columnas : "+temp.getColumnas().size());

                            //Llena la información del modelo
                            for(int i=0; i < temp.getColumnas().size(); i++){
                                ColumnsSQL columna=temp.getColumnas().get(i);
                                String columnName=columna.getCOLUMN_NAME();
                                LogsJB.trace("Columna : "+columnName);
                                LogsJB.debug("Cantidad de metodos set: "+metodosSet.size());
                                //Recorrera los metodos set del modelo para ver cual es el que corresponde a la columna
                                for (int j = 0; j < metodosSet.size(); j++) {
                                    Method metodo= metodosSet.get(j);
                                    String metodoName = metodo.getName();
                                    metodoName = StringUtils.remove(metodoName, "set");
                                    if(StringUtils.equalsIgnoreCase(metodoName, columnName)){
                                        LogsJB.trace("Nombre de la columna, nombre del metodo set: "+columnName+"   "+metodoName);
                                        List<Method> metodosget = new ArrayList<>();
                                        metodosget=temp.getMethodsGetOfModel(temp.getMethodsModel());
                                        LogsJB.trace("Cantidad de metodos get: "+metodosget.size());
                                        //Llena la información de las columnas que se insertaran
                                        for (int a = 0; a < metodosget.size(); a++) {
                                            //Obtengo el metodo
                                            Method metodoget = metodosget.get(a);
                                            //Obtengo la información de la columna
                                            Column columnsSQL = (Column) metodoget.invoke(temp, null);
                                            String NameMetodoGet = metodoget.getName();
                                            NameMetodoGet = StringUtils.remove(NameMetodoGet, "get");
                                            if(StringUtils.equalsIgnoreCase(NameMetodoGet, columnName)){
                                                LogsJB.trace("Nombre de la columna, nombre del metodo get: "+columnName+"   "+NameMetodoGet);
                                                LogsJB.debug("Coincide el nombre de los metodos con la columna: "+columnName);

                                                convertSQLtoJava(columna, registros, metodo, columnsSQL);
                                            }
                                        }
                                    }
                                }
                            }
                            lista.add(temp);
                        }*/

                        mapeador.shutdown();
                        modelo.closeConnection(connect);
                    } else {
                        LogsJB.warning("Tabla correspondiente al modelo no existe en BD's por esa razón no se pudo" +
                                "recuperar el Registro");
                    }
                    modelo.setTaskIsReady(true);
                } catch (Exception e) {
                    LogsJB.fatal("Excepción disparada en el método que Obtiene la información del modelo de la BD's: " + e.toString());
                    LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
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
        return lista;
    }




/*
    public Boolean saveBoolean(){
        Boolean result = false;
        try{
            saveModel(this);
            while(!this.getTaskIsReady()){
            }
            result = this.getTaskIsReady();
        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Guarda el modelo en la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }

*/


    public void getWhereId(String id) {


    }

    void convertirSQLtoJava() {

    }


    public static <T, G> G getObject(T dato) {
        return (G) dato;
    }

    Function funcion = new Function<Integer, Boolean>() {
        public Boolean apply(Integer driver) {
            if (driver >= 1) {
                return true;
            }
            return false;
        }

        public Boolean apply(Double driver) {
            if (driver >= 1) {
                return true;
            }
            return false;
        }
    };


    public static <T, G> G Convertir_entero_a_boleano(T a, Function<T, G> funcion) {
        return funcion.apply(a);
    }

    public static <T, G> List<G> fromArrayToList(T[] a, Function<T, G> mapperFunction) {

        return Arrays.stream(a)
                .map(mapperFunction)
                .collect(Collectors.toList());
    }


}

package io.github.josecarlosbran.JBSqlLite;

import io.github.josecarlosbran.JBSqlLite.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ConexionUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Utilities.ColumnsSQL;
import io.github.josecarlosbran.JBSqlLite.Utilities.TablesSQL;
import io.github.josecarlosbran.JBSqlLite.Utilities.UtilitiesJB;
import io.github.josecarlosbran.LogsJB.LogsJB;

import javax.print.attribute.ResolutionSyntax;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     *
     * @return Retorna la conexión del modelo a la BD's con las propiedades definidas.
     */
    public Connection getConnection() {
        Connection connect = null;
        try {
            String url = null;
            if (this.getDataBaseType() == DataBase.PostgreSQL) {
                //Carga el controlador de PostgreSQL
                url = null;
                connect = null;
                Class.forName("org.postgresql.Driver");
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + "/" + this.getBD();
                String usuario = this.getUser();
                String password = this.getPassword();
                connect = DriverManager.getConnection(url, usuario, password);
            } else if (this.getDataBaseType() == DataBase.MySQL) {
                url = null;
                connect = null;
                //Carga el controlador de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + "/" + this.getBD();
                String usuario = this.getUser();
                String password = this.getPassword();
                connect = DriverManager.getConnection(url, usuario, password);
            } else if (this.getDataBaseType() == DataBase.SQLServer) {
                url = null;
                connect = null;
                //Carga el controlador de SQLServer
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                url = "jdbc:" + this.getDataBaseType().getDBType() + "://" +
                        this.getHost() + ":" + this.getPort() + ";databaseName=" + this.getBD();
                String usuario = this.getUser();
                String password = this.getPassword();
                connect = DriverManager.getConnection(url, usuario, password);
            } else if (this.getDataBaseType() == DataBase.SQLite) {
                url = null;
                connect = null;
                url = "jdbc:" + this.getDataBaseType().getDBType() + ":" + this.getBD();
                connect = DriverManager.getConnection(url);
            }

            if (!Objects.isNull(connect)) {
                LogsJB.info("Conexión a BD's " + this.getBD() + " Realizada exitosamente");
                this.setConnect(connect);
                tableExist(connect);
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al obtener la conexión a la BD's proporcionada: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return connect;
    }

    /**
     * Cierra la conexión a BD's
     *
     * @param connect Conexión que se desea cerrar.
     */
    public void closeConnection(Connection connect) {
        try {
            connect.close();
            LogsJB.info("Conexión a BD's cerrada");
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada cerrar la conexión a la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    public void closeConnection() {
        try {
            this.getConnection().close();
            LogsJB.info("Conexión a BD's cerrada");
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada cerrar la conexión a la BD's: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    protected void tableExist(Connection connexion) {
        Runnable VerificarExistencia = () -> {
            try {
                if (!this.getTableExist()) {

                    DatabaseMetaData metaData = connexion.getMetaData();
                    ResultSet tables = metaData.getTables(null, null, "%", null);
                    //Obtener las tablas disponibles
                    TablesSQL.getTablas().clear();
                    while (tables.next()) {
                        TablesSQL temp = new TablesSQL();
                        temp.setTABLE_CAT(tables.getString(1));
                        temp.setTABLE_SCHEM(tables.getString(2));
                        temp.setTABLE_NAME(tables.getString(3));
                        temp.setTABLE_TYPE(tables.getString(4));
                        temp.setREMARKS(tables.getString(5));
                        temp.setTYPE_CAT(tables.getString(6));
                        temp.setTYPE_SCHEM(tables.getString(7));
                        temp.setTYPE_NAME(tables.getString(8));
                        temp.setSELF_REFERENCING_COL_NAME(tables.getString(9));
                        temp.setREF_GENERATION(tables.getString(10));
                        TablesSQL.getTablas().add(temp);
                        String NameModel = this.getClass().getSimpleName();
                        String NameTable = temp.getTABLE_NAME();
                        if (NameModel.equalsIgnoreCase(NameTable)) {
                            this.setTableExist(Boolean.TRUE);
                            this.setTableName(NameTable);
                            LogsJB.info("La tabla correspondiente a este modelo, existe en BD's");
                            getColumnsTable(metaData);
                        }
                    }
                    if (!this.getTableExist()) {
                        LogsJB.info("La tabla correspondiente a este modelo, No existe en BD's");
                    }
                } else {
                    LogsJB.info("La tabla correspondiente a este modelo, existe en BD's");
                }

            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que verifica si existe la tabla correspondiente al modelo: " + e.toString());
                LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(VerificarExistencia);
        executor.shutdown();
    }

    protected void getColumnsTable(DatabaseMetaData metaData) {
        Runnable ObtenerColumnas = () -> {
            try {
                ResultSet columnas = metaData.getColumns(null, null, this.getTableName(), null);
                //Obtener las tablas disponibles
                this.getColumnas().clear();
                while (columnas.next()) {
                    ColumnsSQL temp = new ColumnsSQL();
                    temp.setTABLE_CAT(columnas.getString(1));
                    temp.setTABLE_SCHEM(columnas.getString(2));
                    temp.setTABLE_NAME(columnas.getString(3));
                    temp.setCOLUMN_NAME(columnas.getString(4));
                    temp.setDATA_TYPE(columnas.getInt(5));
                    temp.setTYPE_NAME(columnas.getString(6));
                    temp.setCOLUMN_SIZE(columnas.getInt(7));

                    temp.setDECIMAL_DIGITS(columnas.getInt(9));
                    temp.setNUM_PREC_RADIX(columnas.getInt(10));
                    temp.setNULLABLE(columnas.getInt(11));
                    temp.setREMARKS(columnas.getString(12));
                    temp.setCOLUMN_DEF(columnas.getString(13));

                    temp.setCHAR_OCTET_LENGTH(columnas.getInt(16));
                    temp.setORDINAL_POSITION(columnas.getInt(17));
                    temp.setIS_NULLABLE(columnas.getString(18));
                    temp.setSCOPE_CATALOG(columnas.getString(19));
                    temp.setSCOPE_SCHEMA(columnas.getString(20));
                    temp.setSCOPE_TABLE(columnas.getString(21));
                    temp.setSOURCE_DATA_TYPE(columnas.getShort(22));
                    temp.setIS_AUTOINCREMENT(columnas.getString(23));
                    temp.setIS_GENERATEDCOLUMN(columnas.getString(24));
                    this.getColumnas().add(temp);
                    //Types.ARRAY

                }
            } catch (Exception e) {
                LogsJB.fatal("Excepción disparada en el método que obtiene las columnas de la tabla que corresponde al modelo: " + e.toString());
                LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(ObtenerColumnas);
        executor.shutdown();


    }

    public Boolean crateTable() {
        Boolean result=false;
        try{
            Callable<Boolean> createtabla = () -> {
                try {
                    if(this.getTableExist()){
                        LogsJB.info("La tabla correspondiente al modelo ya existe en la BD's, por lo cual no será creada.");
                        return false;
                    }else{
                        List<Method> metodos=new LinkedList<>();
                        metodos=this.getMethodsModel();
                        List<ColumnsSQL> Campos=new LinkedList<>();

                        String sql="";
                        if(this.getDataBaseType()==DataBase.MySQL||this.getDataBaseType()==DataBase.PostgreSQL||this.getDataBaseType()==DataBase.SQLite){
                            sql="DROP TABLE IF EXIST "+this.getClass().getSimpleName()+" RESTRICT";
                        } else if (this.getDataBaseType()==DataBase.SQLServer) {
                            sql="if exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '" +
                                    this.getClass().getSimpleName() +
                                    "' AND TABLE_SCHEMA = 'dbo')\n" +
                                    "    drop table dbo." +
                                    this.getClass().getSimpleName()+
                                    " RESTRICT;";
                        }
                        Statement ejecutor= this.getConnection().createStatement();
                        if(!ejecutor.execute(sql)){
                            LogsJB.info("Sentencia para eliminar tabla de la BD's ejecutada exitosamente");
                            int modificados=ejecutor.getUpdateCount();
                            if(modificados>0){
                                LogsJB.info("Tabla "+this.getClass().getSimpleName()+" Eliminada exitosamente");
                                return true;
                            }else {
                                LogsJB.info("Tabla "+this.getClass().getSimpleName()+" No pudo ser Eliminada ya que no existe");
                                LogsJB.info(sql);
                                return false;
                            }
                        }
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

            ExecutorService ejecutor=Executors.newFixedThreadPool(1);
            Future<Boolean> future=ejecutor.submit(createtabla);
            while(!future.isDone()){

            }
            ejecutor.shutdown();
            result=future.get();
        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Elimina la tabla correspondiente al modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }

    public Boolean dropTableIfExist(){
        Boolean result=false;
        try{
            Callable<Boolean> dropTable = () -> {
                try {
                    String sql="";
                    if(this.getDataBaseType()==DataBase.MySQL||this.getDataBaseType()==DataBase.PostgreSQL||this.getDataBaseType()==DataBase.SQLite){
                        sql="DROP TABLE IF EXIST "+this.getClass().getSimpleName()+" RESTRICT";
                    } else if (this.getDataBaseType()==DataBase.SQLServer) {
                        sql="if exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '" +
                                this.getClass().getSimpleName() +
                                "' AND TABLE_SCHEMA = 'dbo')\n" +
                                "    drop table dbo." +
                                this.getClass().getSimpleName()+
                                " RESTRICT;";
                    }
                    Statement ejecutor= this.getConnection().createStatement();
                    if(!ejecutor.execute(sql)){
                        LogsJB.info("Sentencia para eliminar tabla de la BD's ejecutada exitosamente");
                        int modificados=ejecutor.getUpdateCount();
                        if(modificados>0){
                            LogsJB.info("Tabla "+this.getClass().getSimpleName()+" Eliminada exitosamente");
                            return true;
                        }else {
                            LogsJB.info("Tabla "+this.getClass().getSimpleName()+" No pudo ser Eliminada ya que no existe");
                            LogsJB.info(sql);
                            return false;
                        }
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

            ExecutorService ejecutor=Executors.newFixedThreadPool(1);
            Future<Boolean> future=ejecutor.submit(dropTable);
            while(!future.isDone()){

            }
            ejecutor.shutdown();
            result=future.get();
        }catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Elimina la tabla correspondiente al modelo: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
        return result;
    }

    public void getWhereId(String id) {


    }

    void convertirSQLtoJava() {

    }

    //Obtener un objeto generico de una lista
    public <T> List<Method> getMethodsModel(){
        Method[] metodos=this.getClass().getMethods();
        List<Method> result=new LinkedList<>();
        // Los muestro en consola
        for (Method metodo : metodos) {
            String clase=metodo.getDeclaringClass().getSimpleName();
            String returntype=metodo.getReturnType().getSimpleName();

            if((clase.equals("Object")||clase.equals("Conexion")||clase.equals("Methods")||clase.equals("JBSqlUtils"))&&!returntype.equals("Column") ){

            }else{
                System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+returntype);
                result.add(metodo);
            }
            //System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+returntype);
        }
        return result;
    }

    //Obtener unicamente los metodos get del modelo
    public static <T> List<Method> getMethodsGetOfModel(List<Method> metodos){
        // Los muestro en consola
        for (Method metodo : metodos) {
            String returntype=metodo.getReturnType().getSimpleName();
            if(returntype.equals("Column")){

            }else{
                System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+returntype);
                metodos.remove(metodo);
            }
        }
        return metodos;
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

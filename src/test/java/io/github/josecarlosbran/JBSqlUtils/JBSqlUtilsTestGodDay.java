package io.github.josecarlosbran.JBSqlUtils;

import UtilidadesTest.TestModel;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.*;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static UtilidadesTest.Utilities.logParrafo;
import static io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils.dropTableIfExist;
import static io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils.select;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestGodDay {

    TestModel testModel;

    public JBSqlUtilsTestGodDay() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    @Test(testName = "Setear Properties Conexión Globales")
    public void setPropertiesConexion() {
        String separador = System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        logParrafo("Seteara las propiedades de conexión Globales para SQLite");
        JBSqlUtils.setDataBaseGlobal(BDSqlite);
        JBSqlUtils.setDataBaseTypeGlobal(DataBase.SQLite);
        logParrafo("Ha seteado las propiedades de conexión globales para SQLite");
        Assert.assertTrue(BDSqlite.equalsIgnoreCase(System.getProperty(ConeccionProperties.DBNAME.getPropiertie())),
                "Propiedad Nombre BD's no ha sido seteada correctamente");
        Assert.assertTrue(DataBase.SQLite.name().equalsIgnoreCase(System.getProperty(ConeccionProperties.DBTYPE.getPropiertie())),
                "Propiedad Tipo de BD's no ha sido seteada correctamente");
    }

    @Test(testName = "Create Table JBSqlUtils",
            dependsOnMethods = "setPropertiesConexion")
    public void creteTableJBSqlUtils() throws Exception {
        /**
         * Para eliminar una tabla de BD's utilizamos el método execute de la clase dropTableIfExist a la cual mandamos como parámetro
         * el nombre de la tabla que queremos eliminar
         */
        logParrafo("Eliminara la tabla Proveedor de BD's en caso de que exista");
        logParrafo("Resultado de solicitar eliminar la tabla en BD's: " + dropTableIfExist("Proveedor").execute());
        /**
         * Definimos las columnas que deseamos posea nuestra tabla
         */
        Column<Integer> Id = new Column<>("Id", DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);
        Column<String> Name = new Column<>("Name", DataType.VARCHAR);
        Column<String> Apellido = new Column<>("Apellido", DataType.VARCHAR);
        Column<Boolean> Estado = new Column<>("Estado", DataType.BOOLEAN, "true", Constraint.DEFAULT);
        Name.setSize("1000");
        Apellido.setSize("1000");
        logParrafo("Se solicitara la creación de la tabla Proveedor, la cual tendra las siguientes columnas, Id, Name, Apellido y Estado");
        /**
         * Para crear una tabla utilizamos el método createTable despues de haber definido el nombre de la tabla que deseamos Crear
         * y las columnas que deseamos tenga nuestra tabla
         */
        Boolean result = false;
        result = JBSqlUtils.createTable("Proveedor").addColumn(Name).addColumn(Id).addColumn(Apellido).
                addColumn(Estado).createTable();
        logParrafo("Resultado de solicitar la creación de la tabla en BD's: " + result);
        Assert.assertTrue(result, "La Tabla no pudo ser creada debido a que ya existe en BD's");
        logParrafo("Solicitara la creación de la tabla Proveedor cuando esta ya existe en BD's");
        result = true;
        result = JBSqlUtils.createTable("Proveedor").addColumn(Name).addColumn(Id).addColumn(Apellido).
                addColumn(Estado).createTable();
        logParrafo("Resultado de solicitar la creación de la tabla en BD's: " + result);
        Assert.assertFalse(result, "Retorna que la tabla a sido creada cuando esta ya existe en BD's");
    }

    @Test(testName = "Insert Into JBSqlUtils",
            dependsOnMethods = "creteTableJBSqlUtils")
    public void insertIntoJBSqlUtils() throws Exception {
        int registros = 0;
        logParrafo("Se insertaran 5 registros en la tabla Proveedor");
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Erick").andValue("Apellido", "Ramos")
                .execute();
        logParrafo("Resultado de insertar el registro de Erick en la tabla Proveedor: " + registros);
        Assert.assertTrue(registros == 1, "No se pudo insertar el registro de Erick en la tabla Proveedor de BD's");
        registros = 0;
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Daniel").andValue("Apellido", "Quiñonez").
                andValue("Estado", false).execute();
        logParrafo("Resultado de insertar el registro de Daniel en la tabla Proveedor: " + registros);
        Assert.assertTrue(registros == 1, "No se pudo insertar el registro de Daniel en la tabla Proveedor de BD's");
        registros = 0;
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Ligia").andValue("Apellido", "Camey")
                .andValue("Estado", true).execute();
        logParrafo("Resultado de insertar el registro de Ligia en la tabla Proveedor: " + registros);
        Assert.assertTrue(registros == 1, "No se pudo insertar el registro de Ligia en la tabla Proveedor de BD's");
        registros = 0;
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Elsa").andValue("Apellido", "Aguirre")
                .andValue("Estado", false).execute();
        logParrafo("Resultado de insertar el registro de Elsa en la tabla Proveedor: " + registros);
        Assert.assertTrue(registros == 1, "No se pudo insertar el registro de Elsa en la tabla Proveedor de BD's");
        registros = 0;
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Alex").andValue("Apellido", "Garcia")
                .execute();
        logParrafo("Resultado de insertar el registro de Alex en la tabla Proveedor: " + registros);
        Assert.assertTrue(registros == 1, "No se pudo insertar el registro de Alex en la tabla Proveedor de BD's");
    }

    @Test(testName = "Get In JsonObjects JBSqlUtils",
            dependsOnMethods = "insertIntoJBSqlUtils")
    public void getInJsonObjectsJBSqlUtils() throws Exception {
        /**
         * Para obtener los registros de una tabla de BD's podemos hacerlo a través del método select envíando como parámetro
         * el nombre de la tabla de la cual deseamos obtener los registros, así mismo podemos filtrar los resultados a través del método
         * where el cual proporciona acceso a metodos por medio de los cuales podemos filtrar los resultados.
         */
        List<JSONObject> lista = select("Proveedor").getInJsonObjects(null);
        logParrafo("Visualizamos los registros obtenidos de BD's: ");
        /**
         * Imprimimos los registros obtenidos
         */
        lista.forEach(fila -> {
            logParrafo(fila.toString());
        });
        Assert.assertTrue(lista.size() == 5, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
    }

    @Test(testName = "Update JBSqlUtils",
            dependsOnMethods = "getInJsonObjectsJBSqlUtils")
    public void updateJBSqlUtils() throws Exception {
        /**
         * Si deseamos obtener todas las columnas de la tabla envíamos el parámetro columnas del método
         * getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parámetro
         * del metodo select
         */
        List<String> columnas = null;
        logParrafo("Obtendra los primeros 2 registros cuyo estado sea true y en su apellido posea la letra a");
        List<JSONObject> lista = select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
                .orderBy("Name", OrderType.ASC).take(5).getInJsonObjects(columnas);
        /**
         * Imprimimos los registros obtenidos
         */
        int rowsUpdate = 0;
        for (JSONObject fila : lista) {
            rowsUpdate += JBSqlUtils.update("Proveedor").set("Estado", !fila.getBoolean("ESTADO")).execute();
        }
        logParrafo("Filas afectadas: " + rowsUpdate);
        Assert.assertTrue(rowsUpdate == 15, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(testName = "Where Colum Name JBSqlUtils",
            dependsOnMethods = "updateJBSqlUtils")
    public void whereColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("Estado", true).where("Estado", Operator.IGUAL_QUE, true).execute();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Colum Name JBSqlUtils",
            dependsOnMethods = "whereColumnNameJBSqlUtils")
    public void andColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("Estado", true).where("Estado", Operator.IGUAL_QUE, true).and("Estado", Operator.IGUAL_QUE, true).execute();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Colum Name JBSqlUtils",
            dependsOnMethods = "andColumnNameJBSqlUtils")
    public void orColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("Estado", true).where("Estado", Operator.IGUAL_QUE, true).or("Estado", Operator.IGUAL_QUE, true).execute();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Colum Name JBSqlUtils",
            dependsOnMethods = "orColumnNameJBSqlUtils")
    public void openParentecisColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("Estado", true).where("Estado", Operator.IGUAL_QUE, true)
                .openParentecis(Operator.OR, "Estado", Operator.IGUAL_QUE, true).or("Estado", Operator.IGUAL_QUE, true).closeParentecis(null)
                .execute();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Operator Null Colum Name JBSqlUtils",
            dependsOnMethods = "openParentecisColumnNameJBSqlUtils",
            expectedExceptions = Exception.class)
    public void openParentecisOperatorNullColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("Estado", true).where("Estado", Operator.IGUAL_QUE, true)
                .openParentecis(null, "Estado", Operator.IGUAL_QUE, true).and("Estado", Operator.IGUAL_QUE, true).closeParentecis(null)
                .execute();
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Take Limite Igual o Inferior a 0 JBSqlUtils",
            dependsOnMethods = "openParentecisOperatorNullColumnNameJBSqlUtils")
    public void takeLimiteJBSqlUtils() throws Exception {
        select("Proveedor").where("Estado", Operator.IGUAL_QUE, true).take(1).getInJsonObjects(null);
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Colum Name JBSqlUtilsModel",
            dependsOnMethods = "takeLimiteJBSqlUtils")
    public void whereColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("IsMayor", Operator.IGUAL_QUE, false).orderBy("Id", OrderType.ASC).get();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Colum Name JBSqlUtilsModel",
            dependsOnMethods = "whereColumnNameJBSqlUtilsModel")
    public void andColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("IsMayor", Operator.IGUAL_QUE, false).and("IsMayor", Operator.IGUAL_QUE, false).getAll();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Colum Name JBSqlUtilsModel",
            dependsOnMethods = "andColumnNameJBSqlUtilsModel")
    public void orColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("IsMayor", Operator.IGUAL_QUE, false).or("IsMayor", Operator.IGUAL_QUE, false).get();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Colum Name JBSqlUtilsModel",
            dependsOnMethods = "orColumnNameJBSqlUtilsModel")
    public void openParentecisColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("IsMayor", Operator.IGUAL_QUE, false).openParentecis(Operator.OR, "IsMayor", Operator.IGUAL_QUE, false)
                .closeParentecis(null).or("Id", Operator.IGUAL_QUE, 3).get();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Colum Name JBSqlUtilsModel",
            dependsOnMethods = "openParentecisColumnNameJBSqlUtilsModel",
            expectedExceptions = Exception.class)
    public void openParentecisOperatorNullColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("IsMayor", Operator.IGUAL_QUE, false).openParentecis(null, "IsMayor", Operator.IGUAL_QUE, false)
                .closeParentecis(Operator.OR).openParentecis(null, "Id", Operator.IGUAL_QUE, 3).closeParentecis(null).get();
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Order By Colum Name JBSqlUtilsModel",
            dependsOnMethods = "openParentecisOperatorNullColumnNameJBSqlUtilsModel")
    public void orderByColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("IsMayor", Operator.IGUAL_QUE, true).orderBy("Id", OrderType.ASC).getAll();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Take Limite Igual o Inferior a 0 JBSqlUtilsModel",
            dependsOnMethods = "orderByColumnNameJBSqlUtilsModel")
    public void takeLimiteJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("IsMayor", Operator.IGUAL_QUE, true).take(3);
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "takeLimiteJBSqlUtilsModel")
    public void whereColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        String separador = System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        this.testModel = new TestModel(false);
        logParrafo("Se setearan las propiedades de conexión del modelo para SQLite");
        this.testModel.setGetPropertySystem(false);
        this.testModel.setBD(BDSqlite);
        this.testModel.setDataBaseType(DataBase.SQLite);
        this.testModel.where("IsMayor", Operator.IGUAL_QUE, true).get();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "whereColumnNameJBSqlUtilsModelPropertyFalse")
    public void andColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        String separador = System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        this.testModel = new TestModel(false);
        logParrafo("Se setearan las propiedades de conexión del modelo para SQLite");
        this.testModel.setGetPropertySystem(false);
        this.testModel.setBD(BDSqlite);
        this.testModel.setDataBaseType(DataBase.SQLite);
        this.testModel.where("IsMayor", Operator.IGUAL_QUE, true).and("IsMayor", Operator.IGUAL_QUE, true).getAll();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "andColumnNameJBSqlUtilsModelPropertyFalse")
    public void orColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        String separador = System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        this.testModel = new TestModel(false);
        logParrafo("Se setearan las propiedades de conexión del modelo para SQLite");
        this.testModel.setGetPropertySystem(false);
        this.testModel.setBD(BDSqlite);
        this.testModel.setDataBaseType(DataBase.SQLite);
        this.testModel.where("IsMayor", Operator.IGUAL_QUE, true).or("IsMayor", Operator.IGUAL_QUE, true).take(5).get();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "orColumnNameJBSqlUtilsModelPropertyFalse")
    public void openParentecisColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        String separador = System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        this.testModel = new TestModel(false);
        logParrafo("Se setearan las propiedades de conexión del modelo para SQLite");
        this.testModel.setGetPropertySystem(false);
        this.testModel.setBD(BDSqlite);
        this.testModel.setDataBaseType(DataBase.SQLite);
        this.testModel.where("IsMayor", Operator.IGUAL_QUE, false).openParentecis(Operator.OR, "IsMayor", Operator.IGUAL_QUE, false)
                .closeParentecis(null).or("Id", Operator.IGUAL_QUE, 4).get();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Operator Null Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "openParentecisColumnNameJBSqlUtilsModelPropertyFalse")
    public void openParentecisOperatorNullColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        String separador = System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        this.testModel = new TestModel(false);
        logParrafo("Se setearan las propiedades de conexión del modelo para SQLite");
        this.testModel.setGetPropertySystem(false);
        this.testModel.setBD(BDSqlite);
        this.testModel.setDataBaseType(DataBase.SQLite);
        this.testModel.where("IsMayor", Operator.IGUAL_QUE, false).openParentecis(Operator.OR, "IsMayor", Operator.IGUAL_QUE, false)
                .openParentecis(Operator.OR, "IsMayor", Operator.IGUAL_QUE, false).closeParentecis(null).closeParentecis(null)
                .or("Id", Operator.IGUAL_QUE, 4).get();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Order By Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "openParentecisOperatorNullColumnNameJBSqlUtilsModelPropertyFalse")
    public void orderByColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        String separador = System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        this.testModel = new TestModel(false);
        logParrafo("Se setearan las propiedades de conexión del modelo para SQLite");
        this.testModel.setGetPropertySystem(false);
        this.testModel.setBD(BDSqlite);
        this.testModel.setDataBaseType(DataBase.SQLite);
        this.testModel.where("IsMayor", Operator.IGUAL_QUE, true).orderBy("Id", OrderType.ASC).get();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Take Limite Igual o Inferior a 0 JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "orderByColumnNameJBSqlUtilsModelPropertyFalse")
    public void takeLimiteJBSqlUtilsModelPropertyFalse() throws Exception {
        String separador = System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                "BD" +
                separador +
                "JBSqlUtils.db");
        this.testModel = new TestModel(false);
        logParrafo("Se setearan las propiedades de conexión del modelo para SQLite");
        this.testModel.setGetPropertySystem(false);
        this.testModel.setBD(BDSqlite);
        this.testModel.setDataBaseType(DataBase.SQLite);
        this.testModel.where("IsMayor", Operator.IGUAL_QUE, true).take(1).get();
        Assert.assertTrue(true,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(testName = "Setear Properties Modelo",
            dependsOnMethods = {"takeLimiteJBSqlUtilsModelPropertyFalse"})
    public void setPropertiesModels() throws DataBaseUndefind, PropertiesDBUndefined {
        this.testModel = new TestModel();
        this.testModel.setPrimaryKey("id");
        this.testModel.setTimestamps(true);
        this.testModel.setPrimaryKeyIsIncremental(true);
        this.testModel.setUpdateAT("updated_at");
        this.testModel.setCreatedAt("created_at");
        logParrafo("Ha seteado las propiedades de modelo");
        Assert.assertTrue(this.testModel.getPrimaryKey().equalsIgnoreCase("id"),
                "Propiedad PrimaryKey no ha sido seteada correctamente");
        Assert.assertTrue(this.testModel.getUpdateAT().equalsIgnoreCase("updated_at"),
                "Propiedad updated_at no ha sido seteada correctamente");
        Assert.assertTrue(this.testModel.getCreatedAt().equalsIgnoreCase("created_at"),
                "Propiedad created_at no ha sido seteada correctamente");
        Assert.assertTrue(this.testModel.getPrimaryKeyIsIncremental(),
                "Propiedad PrimaryKeyIsIncremental no ha sido seteada correctamente");
    }

    @Test(testName = "Drop Table If Exists from Model",
            dependsOnMethods = {"setPropertiesModels"})
    public void dropTableIfExists() throws Exception {
        logParrafo("Se creara la tabla " + this.testModel.getTableName() + " en BD's");
        this.testModel = new TestModel();
        this.testModel.createTable();
        logParrafo("La tabla a sido creada en BD's");
        logParrafo("Se procedera a eliminar la tabla en BD's");
        Assert.assertTrue(this.testModel.dropTableIfExist(), "No se pudo eliminar la tabla en BD's");
        Assert.assertFalse(this.testModel.getTableExist(), "La tabla No existe en BD's y aun así responde que si la elimino");
        logParrafo("La tabla a sido eliminada en BD's");
    }

    @Test(testName = "Insert Model",
            dependsOnMethods = "dropTableIfExists"
    )
    public void insertModel() throws Exception {
        this.testModel.createTable();
        this.testModel.waitOperationComplete();
        logParrafo("Insertaremos un modelo cuyo nombre será Marcos, Apellido Cabrera y sera menor de edad");
        /**
         * Asignamos valores a las columnas del modelo, luego llamamos al método save(),
         * el cual se encarga de insertar un registro en la tabla correspondiente al modelo con la información del mismo
         * si este no existe, de existir actualiza el registro por medio de la clave primaria del modelo.
         */
        this.testModel.setName("Marcos");
        this.testModel.setApellido("Cabrera");
        this.testModel.setIsMayor(false);
        logParrafo(this.testModel.toString());
        Boolean rowsInsert = this.testModel.saveBoolean();
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.testModel.waitOperationComplete();
        logParrafo("Insertamos el Modelo a través del método save");
        logParrafo("Filas insertadas en BD's: " + rowsInsert + " " + this.testModel.toString());
        Assert.assertTrue(rowsInsert,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "First Or Fail",
            dependsOnMethods = "insertModel",
            expectedExceptions = Exception.class)
    public void firstOrFail() throws Exception {
        TestModel temp = (TestModel) this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Apellido", Operator.IGUAL_QUE,
                "Cabrerassss").firstOrFail();
        Assert.assertTrue(false,
                "No se Lanzo la exepción como se esperaba");
    }

    @Test(testName = "Update Model", dependsOnMethods = "firstOrFail")
    public void updateModel() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        logParrafo("Obtenemos el modelo que tiene por nombre Marcos, Apellido Cabrera");
        TestModel temp = new TestModel();
        temp.setId(5);
        temp.setName("Marcos");
        temp.setApellido("Cabrera");
        temp.setIsMayor(false);
        temp.setModelExist(true);
        logParrafo(temp.toString());
        /**
         * Actualizamos la información
         */
        logParrafo("Actualizamos el nombre del modelo a MarcosEfrain y asígnamos que será mayor de edad");
        temp.setName("MarcosEfrain");
        temp.setIsMayor(true);
        logParrafo(temp.toString());
        /**
         * Eliminamos el modelo en BD's
         */
        Integer rowsUpdate = temp.save();
        logParrafo("Guardamos el modelo en BD's");
        Assert.assertTrue(rowsUpdate == 0,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "Delete Model", dependsOnMethods = "updateModel")
    public void deleteModel() throws Exception {
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        this.testModel.setId(5);
        this.testModel.setName("Marcos");
        this.testModel.setApellido("Cabrera");
        this.testModel.setIsMayor(false);
        this.testModel.setModelExist(true);
        logParrafo(this.testModel.toString());
        /**
         * Eliminamos el modelo en BD's
         */
        logParrafo("Eliminamos el modelo a través del metodo delete");
        Integer rowsDelete = this.testModel.delete();
        Assert.assertTrue(rowsDelete == 0,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "Insert Models",
            dependsOnMethods = "deleteModel")
    public void insertModels() throws Exception {
        logParrafo("Preparamos los modelos a insertar: ");
        List<TestModel> models = new ArrayList<TestModel>();
        //Llenamos la lista de mdelos a insertar en BD's
        for (int i = 0; i < 10; i++) {
            TestModel model = new TestModel();
            model.llenarPropertiesFromModel(this.testModel);
            model.setName("Modelo #" + i);
            model.setApellido("Apellido #" + i);
            if (i % 2 == 0) {
                model.setIsMayor(false);
            }
            models.add(model);
            logParrafo(model.toString());
        }
        logParrafo("Enviamos a guardar los modelos");
        Integer rowsInsert = this.testModel.saveALL(models);
        logParrafo("Filas insertadas en BD's: " + rowsInsert);
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.testModel.waitOperationComplete();
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertTrue(rowsInsert == 10,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "Get Model",
            dependsOnMethods = "insertModels")
    public void getModel() throws Exception {
        this.testModel.setModelExist(false);
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Apellido", Operator.IGUAL_QUE,
                "Cabrerassss").get();
        this.testModel.waitOperationComplete();
        Assert.assertTrue(!this.testModel.getModelExist(),
                "No se Lanzo la exepción como se esperaba");
    }

    @Test(testName = "Get First Model",
            dependsOnMethods = "getModel")
    public void firstModel() throws Exception {
        this.testModel.setModelExist(false);
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        TestModel temp = (TestModel) this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Apellido", Operator.IGUAL_QUE,
                "Cabrerassss").first();
        this.testModel.waitOperationComplete();
        Assert.assertTrue(!temp.getModelExist(),
                "No se Lanzo la exepción como se esperaba");
    }

    @Test(testName = "Take Models",
            dependsOnMethods = "firstModel")
    public void takeModels() throws Exception {
        List<TestModel> models = new ArrayList<TestModel>();
        logParrafo("Recuperamos los primeros seis modelos que en su nombre poseen el texto Modelo #");
        models = this.testModel.where("Name", Operator.LIKE, "%Modelo #%").take(6).get();
        this.testModel.waitOperationComplete();
        Assert.assertTrue(models.size() == 6,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "Get All Models",
            dependsOnMethods = "takeModels")
    public void getAllModels() throws Exception {
        List<TestModel> models = new ArrayList<TestModel>();
        logParrafo("Obtenemos los modelos que poseen nombre es Modelo #5 U #8 o su apellido es #3");
        models = this.testModel.where("Name", Operator.LIKE, "%Modelo #5%").or(
                "Name", Operator.LIKE, "Modelo #8").or("Apellido", Operator.IGUAL_QUE, "Apellido #3").getAll();
        this.testModel.waitOperationComplete();
        Assert.assertTrue(models.size() == 3,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "Update Models",
            dependsOnMethods = "getAllModels")
    public void updateModels() throws Exception {
        logParrafo("Obtenemos los modelos a actualizar: ");
        List<TestModel> models = new ArrayList<TestModel>();
        //Llenamos la lista de mdelos a insertar en BD's
        for (int i = 0; i < 10; i++) {
            TestModel model = new TestModel();
            model.llenarPropertiesFromModel(this.testModel);
            model.setName("Modelo #" + i);
            model.setApellido("Apellido #" + i);
            if (i % 2 == 0) {
                model.setIsMayor(false);
            }
            model.setId(i);
            model.setModelExist(true);
            models.add(model);
            logParrafo(model.toString());
        }
        logParrafo("Enviamos a guardar los modelos a través del método saveALL");
        Integer rowsUpdate = this.testModel.saveALL(models);
        logParrafo("Filas actualizadas en BD's: " + rowsUpdate);
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.testModel.waitOperationComplete();
        Assert.assertTrue(rowsUpdate == 9,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "Delete Models",
            dependsOnMethods = "updateModels")
    public void deleteModels() throws Exception {
        logParrafo("Obtenemos los modelos a actualizar: ");
        List<TestModel> models = new ArrayList<TestModel>();
        //Llenamos la lista de mdelos a insertar en BD's
        for (int i = 0; i < 10; i++) {
            TestModel model = new TestModel();
            model.llenarPropertiesFromModel(this.testModel);
            model.setName("Modelo #" + i);
            model.setApellido("Apellido #" + i);
            if (i % 2 == 0) {
                model.setIsMayor(false);
            }
            model.setId(i);
            model.setModelExist(true);
            models.add(model);
            logParrafo(model.toString());
        }
        logParrafo("Se recuperaron " + models.size() + " Para eliminar: " + models.toString());
        Integer rowsDelete = this.testModel.deleteALL(models);
        this.testModel.waitOperationComplete();
        Assert.assertTrue(rowsDelete == 9,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "Drop Table JBSqlUtils2",
            dependsOnMethods = "deleteModels")
    public void dropTableJBSqlUtils2() throws Exception {
        Boolean result = false;
        /**
         * Para eliminar una tabla de BD's utilizamos el metodo execute de la clase dropTableIfExist a la cual mandamos como parámetro
         * el nombre de la tabla que queremos eliminar
         */
        logParrafo("Eliminara la tabla Proveedor de BD's");
        result = dropTableIfExist("Proveedor").execute();
        logParrafo("Resultado de solicitar eliminar la tabla en BD's: " + result);
    }

    @Test(testName = "Insert Into JBSqlUtils",
            dependsOnMethods = "dropTableJBSqlUtils2", expectedExceptions = Exception.class)
    public void insertIntoJBSqlUtils1() throws Exception {
        int registros = 0;
        logParrafo("Se insertaran 5 registros en la tabla Proveedor");
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Erick").andValue("Apellido", "Ramos")
                .execute();
        logParrafo("Resultado de insertar el registro de Erick en la tabla Proveedor: " + registros);
        Assert.assertTrue(false,
                "No se Lanzo la exepción como se esperaba");
    }

    @Test(testName = "Get In JsonObjects JBSqlUtils",
            dependsOnMethods = "insertIntoJBSqlUtils1")
    public void getInJsonObjectsJBSqlUtils1() throws Exception {
        /**
         * Si deseamos obtener todas las columnas de la tabla envíamos el parámetro columnas del método
         * getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parámetro
         * del método select
         */
        List<String> columnas = null;
        /**
         * Si deseamos obtener unicamente determinadas columnas, es necesario envíar como parámetro una lista de strings
         * con los nombres de las columnas que deseamos obtener del método getInJsonObjects
         */
        columnas = new ArrayList<>();
        columnas.add("Id");
        columnas.add("Name");
        logParrafo("Obtendra los primeros 2 registros cuyo estado sea true y en su apellido posea la letra a");
        /**
         * Para obtener los registros de una tabla de BD's podemos hacerlo a través del método select envíando como parámetro
         * el nombre de la tabla de la cual deseamos obtener los registros, así mismo podemos filtrar los resultados a través del método
         * where el cual proporciona acceso a metodos por medio de los cuales podemos filtrar los resultados.
         */
        List<JSONObject> lista = select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
                .and("Apellido", Operator.LIKE, "%a%").take(2).getInJsonObjects(columnas);
        logParrafo("Visualizamos los registros obtenidos de BD's: ");
        Assert.assertTrue(lista.size() == 0,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "Update JBSqlUtils",
            dependsOnMethods = "getInJsonObjectsJBSqlUtils1", expectedExceptions = Exception.class)
    public void updateJBSqlUtils1() throws Exception {
        int rowsUpdate = 0;
        rowsUpdate += JBSqlUtils.update("Proveedor").set("Name", "Futura").andSet("Apellido", "Prometida")
                .where("Id", Operator.IGUAL_QUE, 5).execute();
        logParrafo("Filas actualizadas en BD's: " + rowsUpdate);
        Assert.assertTrue(false,
                "No se Lanzo la exepción como se esperaba");
    }

    @Test(testName = "Delete JBSqlUtils",
            dependsOnMethods = "updateJBSqlUtils1", expectedExceptions = Exception.class)
    public void deleteJBSqlUtils1() throws Exception {
        int rowsDelete = 0;
        rowsDelete += JBSqlUtils.delete("Proveedor").where("Id", Operator.IGUAL_QUE, 5).execute();
        logParrafo("Filas eliminadas en BD's: " + rowsDelete);
        Assert.assertTrue(false,
                "No se Lanzo la exepción como se esperaba");
    }

    @Test(testName = "Setear Properties Conexión for Model", dependsOnMethods = "deleteJBSqlUtils1")
    public void getPropertiesConexiontoModel() throws DataBaseUndefind, PropertiesDBUndefined {
        this.testModel = new TestModel();
        Assert.assertTrue(Objects.isNull(this.testModel.getPort()),
                "La propiedad obtenida del modelo no es null");
        Assert.assertTrue(Objects.isNull(this.testModel.getHost()),
                "La propiedad obtenida del modelo no es null");
        Assert.assertTrue(Objects.isNull(this.testModel.getUser()),
                "La propiedad obtenida del modelo no es null");
        Assert.assertTrue(Objects.isNull(this.testModel.getPassword()),
                "La propiedad obtenida del modelo no es null");
        Assert.assertTrue(Objects.isNull(this.testModel.getDataBaseType()),
                "La propiedad obtenida del modelo no es null");
        Assert.assertTrue(Objects.isNull(this.testModel.getBD()),
                "La propiedad obtenida del modelo no es null");
    }
}
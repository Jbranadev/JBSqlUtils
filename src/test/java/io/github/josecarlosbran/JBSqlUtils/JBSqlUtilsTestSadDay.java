package io.github.josecarlosbran.JBSqlUtils;


import UtilidadesTest.TestModel;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.*;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static UtilidadesTest.Utilities.logParrafo;
import static io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils.*;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestSadDay {

    public JBSqlUtilsTestSadDay() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    TestModel testModel;

    @Test(testName = "Properties DB Undefined",
            expectedExceptions = PropertiesDBUndefined.class)
    public void propertiesDBUndefined() throws DataBaseUndefind, PropertiesDBUndefined {
        this.testModel = new TestModel(false);
        this.testModel.setDataBaseType(DataBase.SQLite);
        this.testModel.getBD();
        Assert.assertTrue(false,
                "No se Lanzo la exepción PropertiesDBUndefined como se esperaba");
    }

    @Test(testName = "Data Base Type Undefined",
            dependsOnMethods = "propertiesDBUndefined",
            expectedExceptions = DataBaseUndefind.class)
    public void DataBaseTypeUndefined() throws DataBaseUndefind, PropertiesDBUndefined {
        this.testModel = new TestModel(false);
        /**
         * Seteamos las propiedades de conexión del modelo
         */
        this.testModel.setPort("6078");
        this.testModel.setHost("127.0.0.1");
        this.testModel.setUser("root");
        this.testModel.setPassword("Bran");
        this.testModel.setBD("JBSQLUTILS");
        this.testModel.getDataBaseType();
        Assert.assertTrue(false,
                "No se Lanzo la exepción PropertiesDBUndefined como se esperaba");
    }

    @Test(testName = "Conexion Undefind",
            dependsOnMethods = "DataBaseTypeUndefined")
    public void conexionUndefined() throws DataBaseUndefind, PropertiesDBUndefined {
        this.testModel = new TestModel(false);
        this.testModel.closeConnection(null);
        Assert.assertTrue(true,
                "No se Lanzo la exepción ConexionUndefind como se esperaba");
    }

    @Test(testName = "Setear Properties Conexión Globales",
            dependsOnMethods = "conexionUndefined")
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

    @Test(testName = "Drop Table If Exist",
            dependsOnMethods = "setPropertiesConexion",
            expectedExceptions = ValorUndefined.class)
    public void dropTableJBSqlUtils() throws Exception {
        Boolean result = false;
        dropTableIfExist(null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Delete From Table",
            dependsOnMethods = "dropTableJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void deleteTableJBSqlUtils() throws Exception {
        Boolean result = false;
        delete(null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Insert Into Table",
            dependsOnMethods = "deleteTableJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void insertIntoTableJBSqlUtils() throws Exception {
        Boolean result = false;
        insertInto(null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Create Table",
            dependsOnMethods = "insertIntoTableJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void creteTableJBSqlUtils() throws Exception {
        Boolean result = false;
        createTable(null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Update Table",
            dependsOnMethods = "creteTableJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void updateTableJBSqlUtils() throws Exception {
        Boolean result = false;
        update(null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Select Table",
            dependsOnMethods = "updateTableJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void selectTableJBSqlUtils() throws Exception {
        Boolean result = false;
        select(null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Add Column Table JBSqlUtils",
            dependsOnMethods = "selectTableJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void addColumnTableJBSqlUtils() throws Exception {
        Column<Integer> Id = new Column<>("Id", DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);
        Column<String> Name = new Column<>("Name", DataType.VARCHAR);
        Column<String> Apellido = new Column<>("Apellido", DataType.VARCHAR);
        Column<Boolean> Estado = new Column<>("Estado", DataType.BOOLEAN, "true", Constraint.DEFAULT);
        Name.setSize("1000");
        Apellido.setSize("1000");
        JBSqlUtils.createTable("Proveedor").addColumn(null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Add Column a Column of Table JBSqlUtils",
            dependsOnMethods = "addColumnTableJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void addColumnTable2JBSqlUtils() throws Exception {
        Column<Integer> Id = new Column<>("Id", DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);
        Column<String> Name = new Column<>("Name", DataType.VARCHAR);
        Column<String> Apellido = new Column<>("Apellido", DataType.VARCHAR);
        Column<Boolean> Estado = new Column<>("Estado", DataType.BOOLEAN, "true", Constraint.DEFAULT);
        Name.setSize("1000");
        Apellido.setSize("1000");
        JBSqlUtils.createTable("Proveedor").addColumn(Id).addColumn(null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Add Column a Column a Column of Table JBSqlUtils",
            dependsOnMethods = "addColumnTable2JBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void addColumnTable3JBSqlUtils() throws Exception {
        Column<Integer> Id = new Column<>("Id", DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);
        Column<String> Name = new Column<>("Name", DataType.VARCHAR);
        Column<String> Apellido = new Column<>("Apellido", DataType.VARCHAR);
        Column<Boolean> Estado = new Column<>("Estado", DataType.BOOLEAN, "true", Constraint.DEFAULT);
        Name.setSize("1000");
        Apellido.setSize("1000");
        JBSqlUtils.createTable("Proveedor").addColumn(Id).addColumn(Name).addColumn(null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Value Column Name JBSqlUtils",
            dependsOnMethods = "addColumnTable3JBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void valueColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.insertInto("Proveedor").value(null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Value Column Value JBSqlUtils",
            dependsOnMethods = "valueColumnNameJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void valueColumnValueJBSqlUtils() throws Exception {
        JBSqlUtils.insertInto("Proveedor").value("ColumnName", null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Value Column Name JBSqlUtils",
            dependsOnMethods = "valueColumnValueJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void andValueColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.insertInto("Proveedor").value("ColumnName", true).andValue(null, "valor");
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Value Column Value JBSqlUtils",
            dependsOnMethods = "andValueColumnNameJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void andValueColumnValueJBSqlUtils() throws Exception {
        JBSqlUtils.insertInto("Proveedor").value("ColumnName", true).andValue("ColumnName2", null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Set Value Column Name JBSqlUtils",
            dependsOnMethods = "andValueColumnValueJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void setValueColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set(null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Set Value Column Value JBSqlUtils",
            dependsOnMethods = "setValueColumnNameJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void setValueColumnValueJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "AndSet Value Column Name JBSqlUtils",
            dependsOnMethods = "setValueColumnValueJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void andSetValueColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).andSet(null, "valor");
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "AndSet Value Column Value JBSqlUtils",
            dependsOnMethods = "andSetValueColumnNameJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void andSetValueColumnValueJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).andSet("ColumnName2", null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Where Colum Name JBSqlUtils",
            dependsOnMethods = "andSetValueColumnValueJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void whereColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Operator JBSqlUtils",
            dependsOnMethods = "whereColumnNameJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void whereOperatorJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Value JBSqlUtils",
            dependsOnMethods = "whereOperatorJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void whereValueJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "And Colum Name JBSqlUtils",
            dependsOnMethods = "whereValueJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void andColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).and(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Operator JBSqlUtils",
            dependsOnMethods = "andColumnNameJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void andOperatorJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).and("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Value JBSqlUtils",
            dependsOnMethods = "andOperatorJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void andValueJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).and("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Or Colum Name JBSqlUtils",
            dependsOnMethods = "andValueJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void orColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).or(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Operator JBSqlUtils",
            dependsOnMethods = "andColumnNameJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void orOperatorJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).or("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Value JBSqlUtils",
            dependsOnMethods = "andOperatorJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void orValueJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).or("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Open Parentecis Colum Name JBSqlUtils",
            dependsOnMethods = "orValueJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).openParentecis(Operator.OR, null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Operator JBSqlUtils",
            dependsOnMethods = "openParentecisColumnNameJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisOperatorJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).openParentecis(Operator.OR, "null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Value JBSqlUtils",
            dependsOnMethods = "openParentecisOperatorJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisValueJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).openParentecis(Operator.OR, "null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Order By Colum Name JBSqlUtils",
            dependsOnMethods = "openParentecisValueJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void orderByColumnNameJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).orderBy(null, OrderType.ASC);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Order By OrderType JBSqlUtils",
            dependsOnMethods = "orderByColumnNameJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void orderByOperatorJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).orderBy("null", null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }










    @Test(testName = "Where Colum Name JBSqlUtilsModel",
            dependsOnMethods = "andSetValueColumnValueJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void whereColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Operator JBSqlUtilsModel",
            dependsOnMethods = "whereColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void whereOperatorJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Value JBSqlUtilsModel",
            dependsOnMethods = "whereOperatorJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void whereValueJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "And Colum Name JBSqlUtilsModel",
            dependsOnMethods = "whereValueJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void andColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).and(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Operator JBSqlUtilsModel",
            dependsOnMethods = "andColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void andOperatorJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).and("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Value JBSqlUtilsModel",
            dependsOnMethods = "andOperatorJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void andValueJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).and("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Or Colum Name JBSqlUtilsModel",
            dependsOnMethods = "andValueJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).or(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Operator JBSqlUtilsModel",
            dependsOnMethods = "andColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orOperatorJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).or("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Value JBSqlUtilsModel",
            dependsOnMethods = "andOperatorJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orValueJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).or("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Open Parentecis Colum Name JBSqlUtilsModel",
            dependsOnMethods = "orValueJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).openParentecis(Operator.OR, null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Operator JBSqlUtilsModel",
            dependsOnMethods = "openParentecisColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisOperatorJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).openParentecis(Operator.OR, "null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Value JBSqlUtilsModel",
            dependsOnMethods = "openParentecisOperatorJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisValueJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).openParentecis(Operator.OR, "null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Order By Colum Name JBSqlUtilsModel",
            dependsOnMethods = "openParentecisValueJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orderByColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).orderBy(null, OrderType.ASC);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Order By OrderType JBSqlUtilsModel",
            dependsOnMethods = "orderByColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orderByOperatorJBSqlUtilsModel() throws Exception {
        TestModel model =new TestModel();
        model.where("null", Operator.LIKE, true).orderBy("null", null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }





}
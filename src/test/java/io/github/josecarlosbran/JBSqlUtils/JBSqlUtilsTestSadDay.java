package io.github.josecarlosbran.JBSqlUtils;


import UtilidadesTest.TestModel;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.*;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static UtilidadesTest.Utilities.logParrafo;
import static io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils.*;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestSadDay {

    TestModel testModel;

    public JBSqlUtilsTestSadDay() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

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

    @Test(testName = "Take Limite Igual o Inferior a 0 JBSqlUtils",
            dependsOnMethods = "orderByOperatorJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void takeLimiteJBSqlUtils() throws Exception {
        JBSqlUtils.update("Proveedor").set("ColumnName", true).where("null", Operator.LIKE, true).take(0);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Where Colum Name JBSqlUtilsModel",
            dependsOnMethods = "takeLimiteJBSqlUtils",
            expectedExceptions = ValorUndefined.class)
    public void whereColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Operator JBSqlUtilsModel",
            dependsOnMethods = "whereColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void whereOperatorJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Value JBSqlUtilsModel",
            dependsOnMethods = "whereOperatorJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void whereValueJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "And Colum Name JBSqlUtilsModel",
            dependsOnMethods = "whereValueJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void andColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).and(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Operator JBSqlUtilsModel",
            dependsOnMethods = "andColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void andOperatorJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).and("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Value JBSqlUtilsModel",
            dependsOnMethods = "andOperatorJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void andValueJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).and("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Or Colum Name JBSqlUtilsModel",
            dependsOnMethods = "andValueJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).or(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Operator JBSqlUtilsModel",
            dependsOnMethods = "andColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orOperatorJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).or("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Value JBSqlUtilsModel",
            dependsOnMethods = "andOperatorJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orValueJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).or("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Open Parentecis Colum Name JBSqlUtilsModel",
            dependsOnMethods = "orValueJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).openParentecis(Operator.OR, null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Operator JBSqlUtilsModel",
            dependsOnMethods = "openParentecisColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisOperatorJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).openParentecis(Operator.OR, "null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Value JBSqlUtilsModel",
            dependsOnMethods = "openParentecisOperatorJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisValueJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).openParentecis(Operator.OR, "null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Order By Colum Name JBSqlUtilsModel",
            dependsOnMethods = "openParentecisValueJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orderByColumnNameJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).orderBy(null, OrderType.ASC);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Order By OrderType JBSqlUtilsModel",
            dependsOnMethods = "orderByColumnNameJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void orderByOperatorJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).orderBy("null", null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Take Limite Igual o Inferior a 0 JBSqlUtilsModel",
            dependsOnMethods = "orderByOperatorJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void takeLimiteJBSqlUtilsModel() throws Exception {
        TestModel model = new TestModel();
        model.where("null", Operator.LIKE, true).take(-1);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Where Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "takeLimiteJBSqlUtilsModel",
            expectedExceptions = ValorUndefined.class)
    public void whereColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Operator JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "whereColumnNameJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void whereOperatorJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Where Value JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "whereOperatorJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void whereValueJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "And Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "whereValueJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void andColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).and(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Operator JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "andColumnNameJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void andOperatorJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).and("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "And Value JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "andOperatorJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void andValueJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).and("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Or Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "andValueJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void orColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).or(null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Operator JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "andColumnNameJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void orOperatorJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).or("null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Or Value JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "andOperatorJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void orValueJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).or("null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Open Parentecis Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "orValueJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).openParentecis(Operator.OR, null, Operator.LIKE, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Operator JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "openParentecisColumnNameJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisOperatorJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).openParentecis(Operator.OR, "null", null, true);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Open Parentecis Value JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "openParentecisOperatorJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void openParentecisValueJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).openParentecis(Operator.OR, "null", Operator.LIKE, null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    @Test(testName = "Order By Colum Name JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "openParentecisValueJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void orderByColumnNameJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).orderBy(null, OrderType.ASC);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Order By OrderType JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "orderByColumnNameJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void orderByOperatorJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).orderBy("null", null);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }

    @Test(testName = "Take Limite Igual o Inferior a 0 JBSqlUtilsModelPropertyFalse",
            dependsOnMethods = "orderByOperatorJBSqlUtilsModelPropertyFalse",
            expectedExceptions = ValorUndefined.class)
    public void takeLimiteJBSqlUtilsModelPropertyFalse() throws Exception {
        TestModel model = new TestModel(false);
        model.where("null", Operator.LIKE, true).take(-1);
        Assert.assertTrue(false,
                "No se Lanzo la exepción ValorUndefined como se esperaba");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(testName = "Create Table from Model",
            dependsOnMethods = "takeLimiteJBSqlUtilsModelPropertyFalse")
    public void createTableModel() throws Exception {
        this.testModel = new TestModel();
        logParrafo("Se creara la tabla " + this.testModel.getTableName() + " en BD's");
        this.testModel.crateTable();
        this.testModel.waitOperationComplete();
        Assert.assertTrue(this.testModel.getTableExist(), "La tabla No existe en BD's ");
        logParrafo("La tabla a sido creada en BD's");
    }


    @Test(testName = "First Or Fail",
            dependsOnMethods = "createTableModel"
            ,expectedExceptions = Exception.class)
    public void firstOrFail() throws Exception {
        TestModel temp = (TestModel) this.testModel.where("Estado", Operator.IGUAL_QUE, "Marcossss").and("Apellido", Operator.IGUAL_QUE,
                "Cabrerassss").firstOrFail();
        Assert.assertTrue(false,
                "No se Lanzo la exepción como se esperaba");
    }

    @Test(testName = "Get Model",
            dependsOnMethods = "firstOrFail"
            ,expectedExceptions = Exception.class)
    public void getModel() throws Exception {
        this.testModel.setModelExist(false);
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Estado", Operator.IGUAL_QUE,
                "Cabrerassss").get();
        this.testModel.waitOperationComplete();
        Assert.assertTrue(!this.testModel.getModelExist(),
                "No se Lanzo la exepción como se esperaba");
    }

    @Test(testName = "Get First Model",
            dependsOnMethods = "getModel"
            ,expectedExceptions = Exception.class)
    public void firstModel() throws Exception {
        this.testModel.setModelExist(false);
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        TestModel temp = (TestModel) this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Estado", Operator.IGUAL_QUE,
                "Cabrerassss").first();
        this.testModel.waitOperationComplete();
        Assert.assertTrue(!temp.getModelExist(),
                "No se Lanzo la exepción como se esperaba");
    }

    @Test(testName = "Take Models",
            dependsOnMethods = "firstModel"
            ,expectedExceptions = Exception.class)
    public void takeModels() throws Exception {
        List<TestModel> models = new ArrayList<TestModel>();
        logParrafo("Recuperamos los primeros seis modelos que en su nombre poseen el texto Modelo #");
        models = this.testModel.where("Estado", Operator.LIKE, "%Modelo #%").take(6).get();
        this.testModel.waitOperationComplete();
        Assert.assertTrue(models.size() == 0,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

    @Test(testName = "Get All Models",
            dependsOnMethods = "takeModels"
            ,expectedExceptions = Exception.class)
    public void getAllModels() throws Exception {

        List<TestModel> models = new ArrayList<TestModel>();
        logParrafo("Obtenemos los modelos que poseen nombre es Modelo #5 U #8 o su apellido es #3");
        models = this.testModel.where("Name", Operator.LIKE, "%Modelo #5%").or(
                "Name", Operator.LIKE, "Modelo #8").or("Estado", Operator.IGUAL_QUE, "Apellido #3").getAll();
        this.testModel.waitOperationComplete();
        Assert.assertTrue(models.size() == 0,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }


    @Test(testName = "Create Table JBSqlUtils",
            dependsOnMethods = "getAllModels")
    public void creteTableJBSqlUtils2() throws Exception {
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

        Column<String> Name = new Column<>("Name", DataType.VARCHAR, Constraint.NOT_NULL);

        Column<String> Apellido = new Column<>("Apellido", DataType.VARCHAR, Constraint.NOT_NULL);

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
            dependsOnMethods = "creteTableJBSqlUtils2", expectedExceptions = Exception.class)
    public void insertIntoJBSqlUtils() throws Exception {
        int registros = 0;
        logParrafo("Se insertaran 5 registros en la tabla Proveedor");
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Erick").andValue("Estado", "Ramos")
                .execute();
        logParrafo("Resultado de insertar el registro de Erick en la tabla Proveedor: " + registros);
        Assert.assertTrue(false,
                "No se Lanzo la exepción como se esperaba");
    }


    @Test(testName = "Get In JsonObjects JBSqlUtils",
            dependsOnMethods = "insertIntoJBSqlUtils")
    public void getInJsonObjectsJBSqlUtils() throws Exception {
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
                .and("Estado", Operator.LIKE, "%a%").take(2).getInJsonObjects(columnas);
        logParrafo("Visualizamos los registros obtenidos de BD's: ");
        Assert.assertTrue(lista.size() == 0,
                "La cantidad de filas obtenidas, no corresponde a lo esperado");
    }

/*
    @Test(testName = "Update JBSqlUtils",
            dependsOnMethods = "getInJsonObjectsJBSqlUtils", expectedExceptions = Exception.class)
    public void updateJBSqlUtils() throws Exception {
        int rowsUpdate = 0;
        rowsUpdate += JBSqlUtils.update("Proveedor").set("Name", "Futura").andSet("Apellido", "Prometida")
                .where("Estado", Operator.LIKE, 5).execute();
        logParrafo("Filas actualizadas en BD's: " + rowsUpdate);
        Assert.assertTrue(false,
                "No se Lanzo la exepción como se esperaba");
    }*/

    /*
    @Test(testName = "Delete JBSqlUtils",
            dependsOnMethods = "getInJsonObjectsJBSqlUtils", expectedExceptions = Exception.class)
    public void deleteJBSqlUtils() throws Exception {
        int rowsDelete = 0;
        rowsDelete += JBSqlUtils.delete("Proveedor").where("Estado", Operator.IGUAL_QUE, 5).execute();
        logParrafo("Filas eliminadas en BD's: " + rowsDelete);
        Assert.assertTrue(false,
                "No se Lanzo la exepción como se esperaba");
    }
    */

}
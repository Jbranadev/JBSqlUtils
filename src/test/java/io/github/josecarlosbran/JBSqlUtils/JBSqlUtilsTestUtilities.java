package io.github.josecarlosbran.JBSqlUtils;

import UtilidadesTest.TestModel;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.ConeccionProperties;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import static UtilidadesTest.Utilities.logParrafo;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestUtilities {
    public JBSqlUtilsTestUtilities() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    @Test(testName = "Get Boolean From Int")
    public void getBooleanFromInt() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertTrue(UtilitiesJB.getBooleanfromInt(2),
                "El valor Booleano obtenido no corresponde al esperado");
        Assert.assertTrue(UtilitiesJB.getBooleanfromInt(1),
                "El valor Booleano obtenido no corresponde al esperado");
        Assert.assertFalse(UtilitiesJB.getBooleanfromInt(0),
                "El valor Booleano obtenido no corresponde al esperado");
        Assert.assertFalse(UtilitiesJB.getBooleanfromInt(-1),
                "El valor Booleano obtenido no corresponde al esperado");
    }

    @Test(testName = "Get Int From Boolean")
    public void getIntFromBoolean() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertEquals(UtilitiesJB.getIntFromBoolean(null), 0, "El valor Entero obtenido no corresponde al esperado");
        Assert.assertEquals(UtilitiesJB.getIntFromBoolean(false), 0, "El valor Entero obtenido no corresponde al esperado");
        Assert.assertEquals(UtilitiesJB.getIntFromBoolean(true), 1, "El valor Entero obtenido no corresponde al esperado");
    }

    @Test(testName = "Get Column",
            dependsOnMethods = "getIntFromBoolean")
    public void geColumn() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertTrue(UtilitiesJB.getColumn("String").getValor() instanceof String,
                "El tipo de Columna obtenido no corresponde al esperado");
        Double tempDouble = 1052.255;
        Assert.assertTrue(UtilitiesJB.getColumn(tempDouble).getValor() instanceof Double,
                "El tipo de Columna obtenido no corresponde al esperado");
        Boolean tempBoolean = true;
        Assert.assertTrue(UtilitiesJB.getColumn(tempBoolean).getValor() instanceof Boolean,
                "El tipo de Columna obtenido no corresponde al esperado");
        Integer tempInteger = 255;
        Assert.assertTrue(UtilitiesJB.getColumn(tempInteger).getValor() instanceof Integer,
                "El tipo de Columna obtenido no corresponde al esperado");
        Float tempFloat = (float) (5 / 2);
        Assert.assertTrue(UtilitiesJB.getColumn(tempFloat).getValor() instanceof Float,
                "El tipo de Columna obtenido no corresponde al esperado");
        byte[] tempBytes = "564654".getBytes();
        Assert.assertTrue(UtilitiesJB.getColumn(tempBytes).getValor() instanceof byte[],
                "El tipo de Columna obtenido no corresponde al esperado");
        java.sql.Date tempDate = new java.sql.Date(System.currentTimeMillis());
        Assert.assertTrue(UtilitiesJB.getColumn(tempDate).getValor() instanceof Date,
                "El tipo de Columna obtenido no corresponde al esperado");
        Time tempTime = new Time(tempDate.getTime());
        Assert.assertTrue(UtilitiesJB.getColumn(tempTime).getValor() instanceof Time,
                "El tipo de Columna obtenido no corresponde al esperado");
        Timestamp tempTimeStamp = new Timestamp(tempDate.getTime());
        Assert.assertTrue(UtilitiesJB.getColumn(tempTimeStamp).getValor() instanceof Timestamp,
                "El tipo de Columna obtenido no corresponde al esperado");
        Object tempObject = new Object();
        Assert.assertTrue(UtilitiesJB.getColumn(tempObject).getValor() instanceof Object,
                "El tipo de Columna obtenido no corresponde al esperado");
    }

    /*
    @Test(testName = "Llenar Modelo",
            dependsOnMethods = "geColumn")
    public void llenarModelo() throws DataBaseUndefind, PropertiesDBUndefined {
        TestModel model = new TestModel(false);
        TestController controller = new TestController();
        controller.setName("Controller");
        controller.setApellido("Apellido");
        controller.setIsMayor(false);
        controller.setId(5075);
        model.llenarModelo(controller, model);
        Assert.assertTrue(controller.getId() == model.getId(),
                "El valor que contiene modelo no corresponde al que posee el controlador");
        Assert.assertTrue(controller.getIsMayor() == model.getIsMayor(),
                "El valor que contiene modelo no corresponde al que posee el controlador");
        Assert.assertTrue(controller.getName().equalsIgnoreCase(model.getName()),
                "El valor que contiene modelo no corresponde al que posee el controlador");
        Assert.assertTrue(controller.getApellido().equalsIgnoreCase(model.getApellido()),
                "El valor que contiene modelo no corresponde al que posee el controlador");
    }

    @Test(testName = "Llenar Controlador",
            dependsOnMethods = "llenarModelo")
    public void llenarControlador() throws DataBaseUndefind, PropertiesDBUndefined {
        TestModel model = new TestModel(false);
        TestController controller = new TestController();
        model.setName("Controller");
        model.setApellido("Apellido");
        model.setIsMayor(false);
        model.setId(5075);
        model.llenarControlador(controller, model);
        Assert.assertTrue(controller.getId() == model.getId(),
                "El valor que contiene controlador no corresponde al que posee el modelo");
        Assert.assertTrue(controller.getIsMayor() == model.getIsMayor(),
                "El valor que contiene controlador no corresponde al que posee el modelo");
        Assert.assertTrue(controller.getName().equalsIgnoreCase(model.getName()),
                "El valor que contiene controlador no corresponde al que posee el modelo");
        Assert.assertTrue(controller.getApellido().equalsIgnoreCase(model.getApellido()),
                "El valor que contiene controlador no corresponde al que posee el modelo");
    }
    */
    @Test(testName = "Setear Properties Conexión Globales",
            dependsOnMethods = "geColumn")
    public void setPropertiesConexion() {
        String separador = System.getProperty("file.separator");
        String BDSqlite = (Paths.get("").toAbsolutePath().normalize() + separador +
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

    @Test(testName = "Refresh Model",
            dependsOnMethods = {"setPropertiesConexion"})
    public void refreshModel() throws Exception {
        TestModel testModel = new TestModel();
        logParrafo("Se refrescará el modelo con la información existente en BD's");
        testModel.refresh();
        testModel.waitOperationComplete();
        logParrafo("Se refresco el modelo con la información existente en BD's");
    }
}
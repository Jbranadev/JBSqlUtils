package io.github.josecarlosbran.JBSqlUtils;


import io.github.josecarlosbran.JBSqlUtils.Enumerations.*;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestUtilities {

    public JBSqlUtilsTestUtilities() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }


    @Test(testName = "Get Int From Boolean")
    public void getIntFromBoolean() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertTrue(UtilitiesJB.getBooleanfromInt(2),
                "El valor Booleano obtenido no corresponde al esperado");
        Assert.assertTrue(UtilitiesJB.getBooleanfromInt(1),
                "El valor Booleano obtenido no corresponde al esperado");
        Assert.assertFalse(UtilitiesJB.getBooleanfromInt(0),
                "El valor Booleano obtenido no corresponde al esperado");
        Assert.assertFalse(UtilitiesJB.getBooleanfromInt(-1),
                "El valor Booleano obtenido no corresponde al esperado");
    }

    @Test(testName = "Get Column",
    dependsOnMethods = "getIntFromBoolean")
    public void geColumn() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertTrue(UtilitiesJB.getColumn("String").getValor() instanceof String,
                "El tipo de Columna obtenido no corresponde al esperado");
        Double tempDouble=1052.255;
        Assert.assertTrue(UtilitiesJB.getColumn(tempDouble).getValor() instanceof Double,
                "El tipo de Columna obtenido no corresponde al esperado");

        Boolean tempBoolean=true;
        Assert.assertTrue(UtilitiesJB.getColumn(tempBoolean).getValor() instanceof Boolean,
                "El tipo de Columna obtenido no corresponde al esperado");

        Integer tempInteger=255;
        Assert.assertTrue(UtilitiesJB.getColumn(tempInteger).getValor() instanceof Integer,
                "El tipo de Columna obtenido no corresponde al esperado");


        Float tempFloat= (float) (5/2);
        Assert.assertTrue(UtilitiesJB.getColumn(tempFloat).getValor() instanceof Float,
                "El tipo de Columna obtenido no corresponde al esperado");


        byte[] tempBytes="564654".getBytes();
        Assert.assertTrue(UtilitiesJB.getColumn(tempBytes).getValor() instanceof byte[],
                "El tipo de Columna obtenido no corresponde al esperado");

        Date tempDate=new Date();
        Assert.assertTrue(UtilitiesJB.getColumn(tempDate).getValor() instanceof Date,
                "El tipo de Columna obtenido no corresponde al esperado");

        Time tempTime= new Time(tempDate.getTime());
        Assert.assertTrue(UtilitiesJB.getColumn(tempTime).getValor() instanceof Time,
                "El tipo de Columna obtenido no corresponde al esperado");

        Timestamp tempTimeStamp=new Timestamp(tempDate.getTime());
        Assert.assertTrue(UtilitiesJB.getColumn(tempTimeStamp).getValor() instanceof Timestamp,
                "El tipo de Columna obtenido no corresponde al esperado");

        Object tempObject=1052.255;
        Assert.assertTrue(UtilitiesJB.getColumn(tempObject).getValor() instanceof Object,
                "El tipo de Columna obtenido no corresponde al esperado");
    }
    

}
package io.github.josecarlosbran.JBSqlUtils;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.*;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Objects;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestEnumerations {

    public JBSqlUtilsTestEnumerations() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    @Test(testName = "Get Numeración For Name Constraint")
    public void getNumeracionConstraint() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertTrue(Constraint.NOT_NULL == Constraint.UNIQUE.getNumeracionforName(Constraint.NOT_NULL.name()),
                "La Enumeración obtenida no corresponde a la esperada");
        Assert.assertTrue(Objects.isNull(Constraint.UNIQUE.getNumeracionforName("null")),
                "La Enumeración obtenida no corresponde a la esperada");
    }

    @Test(testName = "Get Numeración For Name Data Base Type")
    public void getNumeracionDataBaseType() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertTrue(DataBase.SQLServer == DataBase.MySQL.getNumeracionforName(DataBase.SQLServer.name()),
                "La Enumeración obtenida no corresponde a la esperada");
        Assert.assertTrue(Objects.isNull(DataBase.SQLServer.getNumeracionforName("null")),
                "La Enumeración obtenida no corresponde a la esperada");
    }

    @Test(testName = "Get Numeración For Name Data Type")
    public void getNumeracionDataType() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertTrue(DataType.NVARCHAR == DataType.JSON.getNumeracionforName(DataType.NVARCHAR.name()),
                "La Enumeración obtenida no corresponde a la esperada");
        Assert.assertTrue(Objects.isNull(DataType.NVARCHAR.getNumeracionforName("null")),
                "La Enumeración obtenida no corresponde a la esperada");
    }

    @Test(testName = "Get Numeración For Name Operator")
    public void getNumeracionOperator() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertTrue(Operator.IN == Operator.OR.getNumeracionforName(Operator.IN.name()),
                "La Enumeración obtenida no corresponde a la esperada");
        Assert.assertTrue(Objects.isNull(Operator.IN.getNumeracionforName("nombrex")),
                "La Enumeración obtenida no corresponde a la esperada");
    }

    @Test(testName = "Get Numeración For Name Order Type")
    public void getNumeracionOrderType() throws DataBaseUndefind, PropertiesDBUndefined {
        Assert.assertTrue(OrderType.DESC == OrderType.ASC.getNumeracionforName(OrderType.DESC.name()),
                "La Enumeración obtenida no corresponde a la esperada");
        Assert.assertTrue(Objects.isNull(OrderType.DESC.getNumeracionforName("null")),
                "La Enumeración obtenida no corresponde a la esperada");
    }
}
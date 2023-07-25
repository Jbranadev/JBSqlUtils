package io.github.josecarlosbran.JBSqlUtils;


import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestMySQL {

    public JBSqlUtilsTestMySQL(){

    }

    TestModel testModel;

    @BeforeClass
    public void beforeClass() throws DataBaseUndefind, PropertiesDBUndefined{
        JBSqlUtils.setDataBaseGlobal("JBSQLUTILS");
        JBSqlUtils.setPortGlobal("5076");
        JBSqlUtils.setHostGlobal("127.0.0.1");
        JBSqlUtils.setUserGlobal("Bran");
        JBSqlUtils.setPasswordGlobal("Bran");
        JBSqlUtils.setDataBaseTypeGlobal(DataBase.MySQL);
        this.testModel =new TestModel();
        testModel.setGetPropertySystem(false);
        testModel.setPort("5076");
        testModel.setHost("localhost");
        testModel.setUser("Bran");
        testModel.setPassword("Bran");
        testModel.setBD("JBSQLUTILS");
        testModel.setDataBaseType(DataBase.MySQL);
        testModel.setPropertisURL("");
    }

    @Test(description = "Create Table")
    public void createTable(){
        this.testModel.dropTableIfExist();
        Assert.assertTrue(this.testModel.crateTable(), "La Tabla No fue creada en BD's");
        Assert.assertFalse(this.testModel.getTableExist(), "La tabla No existe en BD's ");
    }

    @Test(description = "Drop Table If Exists")
    public void dropTableIfExists(){
        this.testModel.crateTable();
        Assert.assertTrue(this.testModel.dropTableIfExist(), "No se pudo eliminar la tabla en BD's");
        Assert.assertFalse(this.testModel.dropTableIfExist(), "La tabla No existe en BD's y aun así responde que si la elimino");
    }



}
package io.github.josecarlosbran.JBSqlUtils;


import com.josebran.LogsJB.LogsJB;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.ConeccionProperties;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestMySQL {
    TestModel testModel;

    @Test(description = "Setear Properties Conexión Globales")
    public void setPropertiesConexion() {
        JBSqlUtils.setDataBaseGlobal("JBSQLUTILS");
        JBSqlUtils.setPortGlobal("5076");
        JBSqlUtils.setHostGlobal("127.0.0.1");
        JBSqlUtils.setUserGlobal("Bran");
        JBSqlUtils.setPasswordGlobal("Bran");
        JBSqlUtils.setDataBaseTypeGlobal(DataBase.MySQL);
        JBSqlUtils.setPropertisUrlConexionGlobal("?autoReconnect=true&useSSL=false");
        Assert.assertTrue("JBSQLUTILS".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBNAME.getPropiertie())),
                "Propiedad Nombre BD's no ha sido seteada correctamente");
        Assert.assertTrue("5076".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBPORT.getPropiertie())),
                "Propiedad Puerto BD's no ha sido seteada correctamente");
        Assert.assertTrue("127.0.0.1".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBHOST.getPropiertie())),
                "Propiedad Host BD's no ha sido seteada correctamente");
        Assert.assertTrue("Bran".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBUSER.getPropiertie())),
                "Propiedad Usuario BD's no ha sido seteada correctamente");
        Assert.assertTrue("Bran".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBPASSWORD.getPropiertie())),
                "Propiedad Password BD's no ha sido seteada correctamente");
        Assert.assertTrue(DataBase.MySQL.name().equalsIgnoreCase(System.getProperty(ConeccionProperties.DBTYPE.getPropiertie())),
                "Propiedad Tipo de BD's no ha sido seteada correctamente");
        Assert.assertTrue("?autoReconnect=true&useSSL=false".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBPROPERTIESURL.getPropiertie())),
                "Propiedad Propiedades de conexión no ha sido seteada correctamente");
    }

    @Test(description = "Setear Properties Conexión for Model")
    public void setPropertiesConexiontoModel() throws DataBaseUndefind, PropertiesDBUndefined {
        this.testModel = new TestModel(false);
        this.testModel.setPort("5076");
        this.testModel.setHost("127.0.0.1");
        this.testModel.setUser("Bran");
        this.testModel.setPassword("Bran");
        this.testModel.setBD("JBSQLUTILS");
        this.testModel.setDataBaseType(DataBase.MySQL);
        this.testModel.setPropertisURL("?autoReconnect=true&useSSL=false");
        Assert.assertTrue("JBSQLUTILS".equalsIgnoreCase(this.testModel.getBD()),
                "Propiedad Nombre BD's no ha sido seteada correctamente");
        Assert.assertTrue("5076".equalsIgnoreCase(this.testModel.getPort()),
                "Propiedad Puerto BD's no ha sido seteada correctamente");
        Assert.assertTrue("127.0.0.1".equalsIgnoreCase(this.testModel.getHost()),
                "Propiedad Host BD's no ha sido seteada correctamente");
        Assert.assertTrue("Bran".equalsIgnoreCase(this.testModel.getUser()),
                "Propiedad Usuario BD's no ha sido seteada correctamente");
        Assert.assertTrue("Bran".equalsIgnoreCase(this.testModel.getPassword()),
                "Propiedad Password BD's no ha sido seteada correctamente");
        Assert.assertTrue(DataBase.MySQL.name().equalsIgnoreCase(this.testModel.getDataBaseType().name()),
                "Propiedad Tipo de BD's no ha sido seteada correctamente");
        Assert.assertTrue("?autoReconnect=true&useSSL=false".equalsIgnoreCase(this.testModel.getPropertisURL()),
                "Propiedad Propiedades de conexión no ha sido seteada correctamente");
    }

    @Test(description = "Drop Table If Exists",
            dependsOnMethods = {"setPropertiesConexiontoModel"})
    public void dropTableIfExists() throws Exception {
        //
        this.testModel.crateTable();
        Assert.assertTrue(this.testModel.dropTableIfExist(), "No se pudo eliminar la tabla en BD's");
        Assert.assertFalse(this.testModel.getTableExist(), "La tabla No existe en BD's y aun así responde que si la elimino");
    }

    @Test(description = "Create Table",
            dependsOnMethods = "dropTableIfExists")
    public void createTable() throws Exception {
        Assert.assertTrue(this.testModel.crateTable(), "La Tabla No fue creada en BD's");
        Assert.assertTrue(this.testModel.getTableExist(), "La tabla No existe en BD's ");
    }

    @Test(description = "Insert Model",
            dependsOnMethods = "createTable")
    public void insertModel() throws Exception {
        /**
         * Asignamos valores a las columnas del modelo, luego llamamos al método save(),
         * el cual se encarga de insertar un registro en la tabla correspondiente al modelo con la información del mismo
         * si este no existe, de existir actualiza el registro por medio de la clave primaria del modelo.
         */
        this.testModel.getName().setValor("Marcos");
        this.testModel.getApellido().setValor("Cabrera");
        this.testModel.getIsMayor().setValor(false);
        Integer rowsInsert = this.testModel.save();

        LogsJB.info("Filas insertadas en BD's: " + rowsInsert + " " + this.testModel.toString());
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.testModel.waitOperationComplete();
        Assert.assertTrue(rowsInsert == 1, "El registro no fue insertado en BD's");

    }

    @Test(description = "Update Model", dependsOnMethods = "insertModel")
    public void updateModel() throws Exception {
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        this.testModel.where("Name", Operator.IGUAL_QUE, "Marcos").and("Apellido", Operator.IGUAL_QUE,
                "Cabrera").firstOrFail();
        /**
         * Esperamos ejecute la operación en BD's
         */
        this.testModel.waitOperationComplete();
        /**
         * Actualizamos la información
         */
        this.testModel.getName().setValor("MarcosEfrain");
        this.testModel.getIsMayor().setValor(true);
        /**
         * Eliminamos el modelo en BD's
         */
        Integer rowsUpdate=this.testModel.save();
        Assert.assertTrue(rowsUpdate==1, "El registro no fue actualizado en la BD's");
    }

    @Test(description = "Delete Model", dependsOnMethods = "updateModel")
    public void deleteModel() throws Exception {
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        this.testModel.where("Name", Operator.IGUAL_QUE, "MarcosEfrain").and("Apellido", Operator.IGUAL_QUE,
                "Cabrera").and("isMayor", Operator.IGUAL_QUE, true).firstOrFail();
        /**
         * Esperamos ejecute la operación en BD's
         */
        this.testModel.waitOperationComplete();
        /**
         * Eliminamos el modelo en BD's
         */
        Integer rowsDelete=this.testModel.delete();
        Assert.assertTrue(rowsDelete==1, "El registro no fue eliminado en la BD's");
    }


}
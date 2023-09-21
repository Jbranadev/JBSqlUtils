package io.github.josecarlosbran.JBSqlUtils;

import UtilidadesTest.TestModel;
import UtilidadesTest.UsuarioModel;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.*;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static UtilidadesTest.Utilities.logParrafo;
import static io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils.dropTableIfExist;
import static io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils.select;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestSQLServer {

    TestModel testModel;
    UsuarioModel usuarioModel;

    public JBSqlUtilsTestSQLServer() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    @Test(testName = "Setear Properties Conexión for Model")
    public void setPropertiesConexiontoModel() throws DataBaseUndefind, PropertiesDBUndefined {
        this.testModel = new TestModel(false);
        this.testModel.getIsMayor().setDefault_value("1");
        logParrafo("Se setearan las propiedades de conexión del modelo para SQLServer");
        this.testModel.setGetPropertySystem(false);
        this.testModel.setPort("6077");
        this.testModel.setHost("127.0.0.1");
        this.testModel.setUser("SA");
        this.testModel.setPassword("BranSQLSERVEr1");
        this.testModel.setBD("JBSQLUTILS");
        this.testModel.setDataBaseType(DataBase.SQLServer);
        this.testModel.setPropertisURL(";TrustServerCertificate=true");
        logParrafo("Obtendra la conexión del modelo a BD's");
        Assert.assertFalse(Objects.isNull(this.testModel.getConnection()),
                "No se logro establecer la conexión del modelo a BD's, asegurese de haber configurado correctamente" +
                        "las propiedades de conexión a su servidor de BD's en el metodo setPropertiesConexiontoModel()");
        logParrafo("Obtuvo la conexión del modelo a BD's");
        logParrafo("Se setearan las propiedades de conexión del modelo para SQLServer");
    }

    @Test(testName = "Refresh Model",
            dependsOnMethods = {"setPropertiesConexiontoModel"})
    public void refreshModel() throws Exception {
        logParrafo("Se refrescará el modelo con la información existente en BD's");
        this.testModel.refresh();
        this.testModel.waitOperationComplete();
        logParrafo("Se refresco el modelo con la información existente en BD's");
    }

    @Test(testName = "Drop Table If Exists from Model",
            dependsOnMethods = {"refreshModel"})
    public void dropTableIfExists() throws Exception {
        logParrafo("Se creara la tabla " + this.testModel.getTableName() + " en BD's");
        this.testModel.createTable();
        logParrafo("La tabla a sido creada en BD's");
        logParrafo("Se procedera a eliminar la tabla en BD's");
        Assert.assertTrue(this.testModel.dropTableIfExist(), "No se pudo eliminar la tabla en BD's");
        Assert.assertFalse(this.testModel.getTableExist(), "La tabla No existe en BD's y aun así responde que si la elimino");
        logParrafo("La tabla a sido eliminada en BD's");
    }

    @Test(testName = "Create Table from Model",
            dependsOnMethods = "dropTableIfExists")
    public void createTable() throws Exception {
        logParrafo("Se creara la tabla " + this.testModel.getTableName() + " en BD's");
        Assert.assertTrue(this.testModel.createTable(), "La Tabla No fue creada en BD's");
        Assert.assertTrue(this.testModel.getTableExist(), "La tabla No existe en BD's ");
        logParrafo("La tabla a sido creada en BD's");
    }

    @Test(testName = "Insert Model",
            dependsOnMethods = "createTable")
    public void insertModel() throws Exception {
        logParrafo("Insertaremos un modelo cuyo nombre será Marcos, Apellido Cabrera y sera menor de edad");
        /**
         * Asignamos valores a las columnas del modelo, luego llamamos al método save(),
         * el cual se encarga de insertar un registro en la tabla correspondiente al modelo con la información del mismo
         * si este no existe, de existir actualiza el registro por medio de la clave primaria del modelo.
         */
        this.testModel.getName().setValor("Marcos");
        this.testModel.getApellido().setValor("Cabrera");
        this.testModel.getIsMayor().setValor(false);
        logParrafo(this.testModel.toString());
        Integer rowsInsert = this.testModel.save();
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.testModel.waitOperationComplete();
        logParrafo("Insertamos el Modelo a través del metodo save");
        logParrafo("Filas insertadas en BD's: " + rowsInsert + " " + this.testModel.toString());
        Assert.assertTrue(rowsInsert == 1, "El registro no fue insertado en BD's");
    }

    @Test(testName = "Clean Model",
            dependsOnMethods = "insertModel")
    public void cleanModel() throws Exception {
        Assert.assertTrue(this.testModel.getName().getValor().equalsIgnoreCase("Marcos"));
        Assert.assertTrue(this.testModel.getApellido().getValor().equalsIgnoreCase("Cabrera"));
        Assert.assertTrue(this.testModel.getIsMayor().getValor() == Boolean.FALSE);
        Assert.assertTrue(this.testModel.getModelExist());
        logParrafo("Limpiaremos el Modelo: " + this.testModel.toString());
        this.testModel.cleanModel();
        logParrafo("Modelo despues de realizar la limpieza: " + this.testModel.toString());
        Assert.assertTrue(Objects.isNull(this.testModel.getName().getValor()), "No limpio la columna Name del Modelo");
        Assert.assertTrue(Objects.isNull(this.testModel.getApellido().getValor()), "No limpio la columna Apellido del Modelo");
        Assert.assertTrue(Objects.isNull(this.testModel.getIsMayor().getValor()), "No limpio la columna IsMayor del Modelo");
        Assert.assertFalse(this.testModel.getModelExist(), "No limpio la bandera que indica que el modelo no existe en BD's");
    }

    @Test(testName = "First Or Fail",
            dependsOnMethods = "cleanModel",
            expectedExceptions = ModelNotFound.class)
    public void firstOrFail() throws Exception {
        TestModel temp = (TestModel) this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Apellido", Operator.IGUAL_QUE,
                "Cabrerassss").firstOrFail();
    }

    @Test(testName = "Update Model", dependsOnMethods = "firstOrFail")
    public void updateModel() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        logParrafo("Obtenemos el modelo que tiene por nombre Marcos, Apellido Cabrera");
        TestModel temp = (TestModel) this.testModel.where("Name", Operator.IGUAL_QUE, "Marcos").and("Apellido", Operator.IGUAL_QUE,
                "Cabrera").firstOrFail();
        /**
         * Esperamos ejecute la operación en BD's
         */
        this.testModel.waitOperationComplete();
        logParrafo(temp.toString());
        /**
         * Actualizamos la información
         */
        logParrafo("Actualizamos el nombre del modelo a MarcosEfrain y asígnamos que será mayor de edad");
        temp.getName().setValor("MarcosEfrain");
        temp.getIsMayor().setValor(true);
        logParrafo(temp.toString());
        /**
         * Eliminamos el modelo en BD's
         */
        Integer rowsUpdate = temp.save();
        logParrafo("Guardamos el modelo en BD's");
        Assert.assertTrue(rowsUpdate == 1, "El registro no fue actualizado en la BD's");
    }

    @Test(testName = "Delete Model", dependsOnMethods = "updateModel")
    public void deleteModel() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        logParrafo("Obtenemos el modelo que tiene por nombre MarcosEfrain, Apellido Cabrera y es Mayor de Edad");
        TestModel temp = (TestModel) this.testModel.where("Name", Operator.IGUAL_QUE, "MarcosEfrain").and("Apellido", Operator.IGUAL_QUE,
                "Cabrera").and("isMayor", Operator.IGUAL_QUE, true).firstOrFail();
        /**
         * Esperamos ejecute la operación en BD's
         */
        this.testModel.waitOperationComplete();
        logParrafo(temp.toString());
        /**
         * Eliminamos el modelo en BD's
         */
        logParrafo("Eliminamos el modelo a través del metodo delete");
        Integer rowsDelete = temp.delete();
        Assert.assertTrue(rowsDelete == 1, "El registro no fue eliminado en la BD's");
    }

    @Test(testName = "Insert Models",
            dependsOnMethods = "deleteModel")
    public void insertModels() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        logParrafo("Preparamos los modelos a insertar: ");
        List<TestModel> models = new ArrayList<TestModel>();
        //Llenamos la lista de mdelos a insertar en BD's
        for (int i = 0; i < 10; i++) {
            TestModel model = new TestModel(false);
            model.llenarPropertiesFromModel(this.testModel);
            model.getName().setValor("Modelo #" + i);
            model.getApellido().setValor("Apellido #" + i);
            if (i % 2 == 0) {
                model.getIsMayor().setValor(false);
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
        Assert.assertTrue(rowsInsert == 10, "Los registros no fueron insertados correctamente en BD's");
    }

    @Test(testName = "Get Model",
            dependsOnMethods = "insertModels")
    public void getModel() throws Exception {
        //Incluir metodo que permita limpiar el modelo
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Apellido", Operator.IGUAL_QUE,
                "Cabrerassss").get();
        this.testModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que no existe, el resultado es: " + this.testModel.getModelExist());
        logParrafo(this.testModel.toString());
        Assert.assertFalse(this.testModel.getModelExist(), "Obtuvo un registro que no existe en BD's");
        logParrafo("Obtenemos un modelo cuyo nombre sea Modelo # y sea mayor de edad");
        this.testModel.where("Name", Operator.LIKE, "%Modelo #%").and("IsMayor", Operator.IGUAL_QUE,
                true).get();
        this.testModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que sí existe, el resultado es: " + this.testModel.getModelExist());
        logParrafo(this.testModel.toString());
        Assert.assertTrue(this.testModel.getModelExist(), "No obtuvo un registro que no existe en BD's");
    }

    @Test(testName = "Get First Model",
            dependsOnMethods = "getModel")
    public void firstModel() throws Exception {
        //Incluir metodo que permita limpiar el modelo
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        TestModel temp = (TestModel) this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Apellido", Operator.IGUAL_QUE,
                "Cabrerassss").first();
        this.testModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que no existe, el resultado es: " + temp.getModelExist());
        logParrafo(temp.toString());
        Assert.assertFalse(temp.getModelExist(), "Obtuvo un registro que no existe en BD's");
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Modelo # y sea mayor de edad");
        temp = (TestModel) this.testModel.where("Name", Operator.LIKE, "%Modelo #%").and("IsMayor", Operator.IGUAL_QUE,
                true).first();
        this.testModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que sí existe, el resultado es: " + temp.getModelExist());
        logParrafo(temp.toString());
        Assert.assertTrue(temp.getModelExist(), "No obtuvo un registro que no existe en BD's");
    }

    @Test(testName = "Take Models",
            dependsOnMethods = "firstModel")
    public void takeModels() throws Exception {
        logParrafo("Limpiamos el modelo");
        //Incluir metodo que permita limpiar el modelo
        this.testModel.cleanModel();
        List<TestModel> models = new ArrayList<TestModel>();
        logParrafo("Recuperamos los primeros seis modelos que en su nombre poseen el texto Modelo #");
        models = this.testModel.where("Name", Operator.LIKE, "%Modelo #%").take(6).get();
        this.testModel.waitOperationComplete();
        logParrafo("Se recuperaron " + models.size() + " los cuales son: " + models.toString());
        Assert.assertTrue(models.size() == 6, "Los modelos no fueron recuperados de BD's como se definio en la sentencia Take");
    }

    @Test(testName = "Get All Models",
            dependsOnMethods = "takeModels")
    public void getAllModels() throws Exception {
        //Incluir metodo que permita limpiar el modelo
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        List<TestModel> models = new ArrayList<TestModel>();
        logParrafo("Obtenemos los modelos que poseen nombre es Modelo #5 U #8 o su apellido es #3");
        models = this.testModel.where("Name", Operator.LIKE, "%Modelo #5%").or(
                "Name", Operator.LIKE, "Modelo #8").or("Apellido", Operator.IGUAL_QUE, "Apellido #3").getAll();
        this.testModel.waitOperationComplete();
        logParrafo("Se recuperaron " + models.size() + " los cuales son: " + models.toString());
        Assert.assertTrue(models.size() == 3, "Los modelos no fueron recuperados de BD's");
    }

    @Test(testName = "Update Models",
            dependsOnMethods = "getAllModels")
    public void updateModels() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        logParrafo("Obtenemos los modelos a actualizar: ");
        List<TestModel> models = new ArrayList<TestModel>();
        //Llenamos la lista de mdelos a insertar en BD's
        models = this.testModel.getAll();
        this.testModel.waitOperationComplete();
        logParrafo("Operamos los cambios a realizar en los modelos obtenidos de BD's");
        models.forEach(modelo -> {
            logParrafo("Modelo obtenido: " + modelo.toString());
            modelo.getIsMayor().setValor(!modelo.getIsMayor().getValor());
            logParrafo("Modelo a actualizar: " + modelo.toString());
        });
        logParrafo("Enviamos a guardar los modelos a través del metodo saveALL");
        Integer rowsUpdate = this.testModel.saveALL(models);
        logParrafo("Filas actualizadas en BD's: " + rowsUpdate);
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.testModel.waitOperationComplete();
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertTrue(rowsUpdate == 10, "Los registros no fueron insertados correctamente en BD's");
    }

    @Test(testName = "Delete Models",
            dependsOnMethods = "updateModels")
    public void deleteModels() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        List<TestModel> models = new ArrayList<TestModel>();
        logParrafo("Obtenemos los modelos que poseen nombre es Modelo #5 U #8 o su apellido es #3");
        models = this.testModel.where("Name", Operator.LIKE, "%Modelo #5%").or(
                "Name", Operator.LIKE, "Modelo #8").or("Apellido", Operator.IGUAL_QUE, "Apellido #3").getAll();
        this.testModel.waitOperationComplete();
        logParrafo("Se recuperaron " + models.size() + " Para eliminar: " + models.toString());
        Integer rowsDelete = this.testModel.deleteALL(models);
        this.testModel.waitOperationComplete();
        logParrafo("Filas eliminadas en BD's: " + rowsDelete + " " + models);
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertTrue(rowsDelete == 3, "Los registros no fueron eliminados correctamente en BD's");
    }

    @Test(testName = "Setear Properties Conexión Globales",
            dependsOnMethods = "deleteModels")
    public void setPropertiesConexion() {
        logParrafo("Seteara las propiedades de conexión Globales para SQLServer");
        JBSqlUtils.setDataBaseGlobal("JBSQLUTILS");
        JBSqlUtils.setPortGlobal("6077");
        JBSqlUtils.setHostGlobal("127.0.0.1");
        JBSqlUtils.setUserGlobal("SA");
        JBSqlUtils.setPasswordGlobal("BranSQLSERVEr1");
        JBSqlUtils.setDataBaseTypeGlobal(DataBase.SQLServer);
        JBSqlUtils.setPropertisUrlConexionGlobal(";TrustServerCertificate=true");
        logParrafo("Ha seteado las propiedades de conexión globales para SQLServer");
        Assert.assertTrue("JBSQLUTILS".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBNAME.getPropiertie())),
                "Propiedad Nombre BD's no ha sido seteada correctamente");
        Assert.assertTrue("6077".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBPORT.getPropiertie())),
                "Propiedad Puerto BD's no ha sido seteada correctamente");
        Assert.assertTrue("127.0.0.1".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBHOST.getPropiertie())),
                "Propiedad Host BD's no ha sido seteada correctamente");
        Assert.assertTrue("SA".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBUSER.getPropiertie())),
                "Propiedad Usuario BD's no ha sido seteada correctamente");
        Assert.assertTrue("BranSQLSERVEr1".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBPASSWORD.getPropiertie())),
                "Propiedad Password BD's no ha sido seteada correctamente");
        Assert.assertTrue(DataBase.SQLServer.name().equalsIgnoreCase(System.getProperty(ConeccionProperties.DBTYPE.getPropiertie())),
                "Propiedad Tipo de BD's no ha sido seteada correctamente");
        Assert.assertTrue(";TrustServerCertificate=true".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBPROPERTIESURL.getPropiertie())),
                "Propiedad Propiedades de conexión no ha sido seteada correctamente");
    }

    @Test(testName = "Create Table JBSqlUtils",
            dependsOnMethods = "setPropertiesConexion")
    public void creteTableJBSqlUtils() throws Exception {
        /**
         * Para eliminar una tabla de BD's utilizamos el metodo execute de la clase dropTableIfExist a la cual mandamos como parametro
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
        Estado.setDefault_value("1");
        logParrafo("Se solicitara la creación de la tabla Proveedor, la cual tendra las siguientes columnas, Id, Name, Apellido y Estado");
        /**
         * Para crear una tabla utilizamos el metodo createTable despues de haber definido el nombre de la tabla que deseamos Crear
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
         * Si deseamos obtener todas las columnas de la tabla envíamos el parametro columnas del metodo
         * getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parametro
         * del metodo select
         */
        List<String> columnas = null;
        /**
         * Si deseamos obtener unicamente determinadas columnas, es necesario envíar como parametro una lista de strings
         * con los nombres de las columnas que deseamos obtener del metodo getInJsonObjects
         */
        columnas = new ArrayList<>();
        columnas.add("Id");
        columnas.add("Name");
        logParrafo("Obtendra los primeros 2 registros cuyo estado sea true y en su apellido posea la letra a");
        /**
         * Para obtener los registros de una tabla de BD's podemos hacerlo a través del metodo select envíando como parametro
         * el nombre de la tabla de la cual deseamos obtener los registros, así mismo podemos filtrar los resultados a través del metodo
         * where el cual proporciona acceso a metodos por medio de los cuales podemos filtrar los resultados.
         */
        List<JSONObject> lista = select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
                .and("Apellido", Operator.LIKE, "%a%").take(2).getInJsonObjects(columnas);
        logParrafo("Visualizamos los registros obtenidos de BD's: ");
        /**
         * Imprimimos los registros obtenidos
         */
        lista.forEach(fila -> {
            logParrafo(fila.toString());
        });
        Assert.assertTrue(lista.size() == 2, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
    }

    @Test(testName = "Update JBSqlUtils",
            dependsOnMethods = "getInJsonObjectsJBSqlUtils")
    public void updateJBSqlUtils() throws Exception {
        /**
         * Si deseamos obtener todas las columnas de la tabla envíamos el parametro columnas del metodo
         * getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parametro
         * del metodo select
         */
        List<String> columnas = null;
        logParrafo("Obtendra los primeros 2 registros cuyo estado sea true y en su apellido posea la letra a");
        List<JSONObject> lista = select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
                .and("Apellido", Operator.LIKE, "%a%").take(2).getInJsonObjects(columnas);
        /**
         * Imprimimos los registros obtenidos
         */
        int rowsUpdate = 0;
        for (JSONObject fila : lista) {
            if (fila.getString("Name").equalsIgnoreCase("Ligia") && fila.getString("Apellido").equalsIgnoreCase("Camey")) {
                logParrafo("Actualizara la fila que corresponde a Ligia Camey: ");
                logParrafo(fila.toString());
                rowsUpdate += JBSqlUtils.update("Proveedor").set("Name", "Futura").andSet("Apellido", "Prometida")
                        .where("Id", Operator.IGUAL_QUE, fila.getInt("Id")).execute();
                logParrafo("Filas actualizadas en BD's: " + rowsUpdate);
            }
        }
        Assert.assertTrue(rowsUpdate == 1, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
    }

    @Test(testName = "Delete JBSqlUtils",
            dependsOnMethods = "updateJBSqlUtils")
    public void deleteJBSqlUtils() throws Exception {
        /**
         * Si deseamos obtener todas las columnas de la tabla envíamos el parametro columnas del metodo
         * getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parametro
         * del metodo select
         */
        List<String> columnas = null;
        logParrafo("Obtenedra todos los registros que tienen estado True y en su apellido tienen una letra a");
        List<JSONObject> lista = select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
                .and("Apellido", Operator.LIKE, "%a%").take(5).getInJsonObjects(columnas);
        /**
         * Imprimimos los registros obtenidos
         */
        int rowsDelete = 0;
        for (JSONObject fila : lista) {
            if (fila.getInt("Id") == 5) {
                logParrafo("VIsualizamos la Fila a eliminar, cuyo Id es igual a 5: ");
                logParrafo(fila.toString());
                rowsDelete += JBSqlUtils.delete("Proveedor").where("Id", Operator.IGUAL_QUE, fila.getInt("Id")).execute();
                logParrafo("Filas eliminadas en BD's: " + rowsDelete);
            }
        }
        Assert.assertTrue(rowsDelete == 1, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
    }

    @Test(testName = "Drop Table JBSqlUtils",
            dependsOnMethods = "deleteJBSqlUtils")
    public void dropTableJBSqlUtils() throws Exception {
        Boolean result = false;
        /**
         * Para eliminar una tabla de BD's utilizamos el metodo execute de la clase dropTableIfExist a la cual mandamos como parametro
         * el nombre de la tabla que queremos eliminar
         */
        logParrafo("Eliminara la tabla Proveedor de BD's");
        result = dropTableIfExist("Proveedor").execute();
        logParrafo("Resultado de solicitar eliminar la tabla en BD's: " + result);
        Assert.assertTrue(result, "La Tabla no pudo ser eliminada porque no existe en BD's");
        result = true;
        logParrafo("Solicitara eliminar la tabla de BD's cuando esta ya no existe");
        result = dropTableIfExist("Proveedor").execute();
        logParrafo("Resultado de solicitar la eliminar la tabla cuando no existe en BD's: " + result);
        Assert.assertFalse(result, "Retorna que la tabla a sido eliminada cuando esta ya no existe en BD's");
    }

    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    @Test(testName = "Drop Table If Exists from Model Usuario",
            dependsOnMethods = {"dropTableJBSqlUtils"})
    public void dropTableIfExistsUsuario() throws Exception {
        this.usuarioModel = new UsuarioModel();
        logParrafo("Se creara la tabla " + this.usuarioModel.getTableName() + " en BD's");
        this.usuarioModel.createTable();
        logParrafo("La tabla a sido creada en BD's");
        logParrafo("Se procedera a eliminar la tabla en BD's");
        Assert.assertTrue(this.usuarioModel.dropTableIfExist(), "No se pudo eliminar la tabla en BD's");
        Assert.assertFalse(this.usuarioModel.getTableExist(), "La tabla No existe en BD's y aun así responde que si la elimino");
        logParrafo("La tabla a sido eliminada en BD's");
    }

    @Test(testName = "Create Table from Model Usuario",
            dependsOnMethods = "dropTableIfExistsUsuario")
    public void createTableUsuario() throws Exception {
        logParrafo("Se creara la tabla " + this.usuarioModel.getTableName() + " en BD's");
        Assert.assertTrue(this.usuarioModel.createTable(), "La Tabla No fue creada en BD's");
        Assert.assertTrue(this.usuarioModel.getTableExist(), "La tabla No existe en BD's ");
        logParrafo("La tabla a sido creada en BD's");
    }

    @Test(testName = "Insert Model Usuario",
            dependsOnMethods = "createTableUsuario")
    public void insertModelUsuario() throws Exception {
        logParrafo("Insertaremos un modelo cuyo nombre será Marcos, Apellido Cabrera y sera menor de edad");
        /**
         * Asignamos valores a las columnas del modelo, luego llamamos al método save(),
         * el cual se encarga de insertar un registro en la tabla correspondiente al modelo con la información del mismo
         * si este no existe, de existir actualiza el registro por medio de la clave primaria del modelo.
         */
        this.usuarioModel.getId_Subestación().setValor(2.0);
        this.usuarioModel.getNombre().setValor("NombrePrimerModelo");
        this.usuarioModel.getTelefono().setValor(null);
        this.usuarioModel.getCorreo().setValor("CorreoPrueba");
        this.usuarioModel.getEstado().setValor(false);
        logParrafo(this.usuarioModel.toString());
        Integer rowsInsert = this.usuarioModel.save();
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.usuarioModel.waitOperationComplete();
        logParrafo("Insertamos el Modelo a través del método save");
        logParrafo("Filas insertadas en BD's: " + rowsInsert + " " + this.usuarioModel.toString());
        Assert.assertTrue(rowsInsert == 1, "El registro no fue insertado en BD's");
    }

    @Test(testName = "Update Model Usuario", dependsOnMethods = "insertModelUsuario")
    public void updateModelUsuario() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        logParrafo("Obtenemos el modelo que tiene por nombre Marcos, Apellido Cabrera");
        UsuarioModel temp = (UsuarioModel) this.usuarioModel.where("Nombre", Operator.LIKE, "NombrePrimerModelo").and("Estado", Operator.IGUAL_QUE,
                false).firstOrFail();
        /**
         * Esperamos ejecute la operación en BD's
         */
        this.usuarioModel.waitOperationComplete();
        logParrafo(temp.toString());
        /**
         * Actualizamos la información
         */
        logParrafo("Actualizamos el nombre del modelo a MarcosEfrain y asígnamos que será mayor de edad");
        temp.getNombre().setValor("MarcosEfrain");
        temp.getEstado().setValor(true);
        logParrafo(temp.toString());
        /**
         * Eliminamos el modelo en BD's
         */
        Integer rowsUpdate = temp.save();
        logParrafo("Guardamos el modelo en BD's");
        Assert.assertTrue(rowsUpdate == 1, "El registro no fue actualizado en la BD's");
    }

    @Test(testName = "Delete Model Usuario", dependsOnMethods = "updateModelUsuario")
    public void deleteModelUsuario() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        logParrafo("Obtenemos el modelo que tiene por nombre MarcosEfrain, Apellido Cabrera y es Mayor de Edad");
        UsuarioModel temp = (UsuarioModel) this.usuarioModel.where("Nombre", Operator.LIKE, "MarcosEfrain").and("Estado", Operator.IGUAL_QUE, true).firstOrFail();
        /**
         * Esperamos ejecute la operación en BD's
         */
        this.usuarioModel.waitOperationComplete();
        logParrafo(temp.toString());
        /**
         * Eliminamos el modelo en BD's
         */
        logParrafo("Eliminamos el modelo a través del metodo delete");
        Integer rowsDelete = temp.delete();
        Assert.assertTrue(rowsDelete == 1, "El registro no fue eliminado en la BD's");
    }

    @Test(testName = "Insert Models Usuario",
            dependsOnMethods = "deleteModelUsuario")
    public void insertModelsUsuario() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        logParrafo("Preparamos los modelos a insertar: ");
        List<UsuarioModel> models = new ArrayList<UsuarioModel>();
        //Llenamos la lista de mdelos a insertar en BD's
        for (int i = 0; i < 10; i++) {
            UsuarioModel model = new UsuarioModel();
            model.getId_Subestación().setValor((1.0 * i));
            model.getNombre().setValor("NombreModelo#" + i);
            model.getCorreo().setValor("CorreoPrueba#" + i);
            if (i % 2 == 0) {
                model.getEstado().setValor(false);
            } else {
                model.getEstado().setValor(true);
            }
            models.add(model);
            logParrafo(model.toString());
        }
        logParrafo("Enviamos a guardar los modelos");
        Integer rowsInsert = this.usuarioModel.saveALL(models);
        logParrafo("Filas insertadas en BD's: " + rowsInsert);
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.usuarioModel.waitOperationComplete();
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertTrue(rowsInsert == 10, "Los registros no fueron insertados correctamente en BD's");
    }

    @Test(testName = "Get Model Usuario",
            dependsOnMethods = "insertModelsUsuario")
    public void getModelUsuario() throws Exception {
        //Incluir método que permita limpiar el modelo
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        this.usuarioModel.where("Nombre", Operator.LIKE, "Marcossss").and("Correo", Operator.LIKE,
                "Cabrerassss").get();
        this.usuarioModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que no existe, el resultado es: " + this.usuarioModel.getModelExist());
        logParrafo(this.usuarioModel.toString());
        Assert.assertFalse(this.usuarioModel.getModelExist(), "Obtuvo un registro que no existe en BD's");
        logParrafo("Obtenemos un modelo cuyo nombre sea Modelo # y sea mayor de edad");
        this.usuarioModel.where("Nombre", Operator.LIKE, "%Modelo#%").and("Estado", Operator.IGUAL_QUE,
                true).get();
        this.usuarioModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que sí existe, el resultado es: " + this.usuarioModel.getModelExist());
        logParrafo(this.usuarioModel.toString());
        Assert.assertTrue(this.usuarioModel.getModelExist(), "No obtuvo un registro que no existe en BD's");
    }

    @Test(testName = "Get First Model Usuario",
            dependsOnMethods = "getModelUsuario")
    public void firstModelUsuario() throws Exception {
        //Incluir metodo que permita limpiar el modelo
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su Correo sea Cabrerassss, el cual no existe");
        UsuarioModel temp = (UsuarioModel) this.usuarioModel.where("Nombre", Operator.LIKE, "Marcossss").and("Correo", Operator.LIKE,
                "Cabrerassss").first();
        this.usuarioModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que no existe, el resultado es: " + temp.getModelExist());
        logParrafo(temp.toString());
        Assert.assertFalse(temp.getModelExist(), "Obtuvo un registro que no existe en BD's");
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Modelo# y sea mayor de edad");
        temp = (UsuarioModel) this.usuarioModel.where("Nombre", Operator.LIKE, "%Modelo#%").and("Estado", Operator.IGUAL_QUE,
                true).first();
        this.usuarioModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que sí existe, el resultado es: " + temp.getModelExist());
        logParrafo(temp.toString());
        Assert.assertTrue(temp.getModelExist(), "No obtuvo un registro que no existe en BD's");
    }

    @Test(testName = "Take Models Usuario",
            dependsOnMethods = "firstModelUsuario")
    public void takeModelsUsuario() throws Exception {
        logParrafo("Limpiamos el modelo");
        //Incluir método que permita limpiar el modelo
        this.usuarioModel.cleanModel();
        List<UsuarioModel> models = new ArrayList<UsuarioModel>();
        logParrafo("Recuperamos los primeros seis modelos que en su nombre poseen el texto Modelo#");
        models = this.usuarioModel.where("Nombre", Operator.LIKE, "%Modelo#%").take(6).get();
        this.usuarioModel.waitOperationComplete();
        logParrafo("Se recuperaron " + models.size() + " los cuales son: " + models.toString());
        Assert.assertTrue(models.size() == 6, "Los modelos no fueron recuperados de BD's como se definio en la sentencia Take");
    }

    @Test(testName = "Get All Models Usuario",
            dependsOnMethods = "takeModelsUsuario")
    public void getAllModelsUsuario() throws Exception {
        //Incluir método que permita limpiar el modelo
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        List<UsuarioModel> models = new ArrayList<UsuarioModel>();
        logParrafo("Obtenemos los modelos que poseen nombre es Modelo#5 U #8 o su Correo es #3");
        models = this.usuarioModel.where("Nombre", Operator.LIKE, "%Modelo#5%").or(
                "Nombre", Operator.LIKE, "%Modelo#8%").getAll();
        this.usuarioModel.waitOperationComplete();
        logParrafo("Se recuperaron " + models.size() + " los cuales son: " + models.toString());
        Assert.assertTrue(models.size() == 2, "Los modelos no fueron recuperados de BD's");
    }

    @Test(testName = "Update Models Usuario",
            dependsOnMethods = "getAllModelsUsuario")
    public void updateModelsUsuario() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        logParrafo("Obtenemos los modelos a actualizar: ");
        List<UsuarioModel> models = new ArrayList<UsuarioModel>();
        //Llenamos la lista de mdelos a insertar en BD's
        models = this.usuarioModel.getAll();
        this.usuarioModel.waitOperationComplete();
        logParrafo("Operamos los cambios a realizar en los modelos obtenidos de BD's");
        models.forEach(modelo -> {
            logParrafo("Modelo obtenido: " + modelo.toString());
            modelo.getEstado().setValor(!modelo.getEstado().getValor());
            logParrafo("Modelo a actualizar: " + modelo.toString());
        });
        logParrafo("Enviamos a guardar los modelos a través del método saveALL");
        Integer rowsUpdate = this.usuarioModel.saveALL(models);
        logParrafo("Filas actualizadas en BD's: " + rowsUpdate);
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.usuarioModel.waitOperationComplete();
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertTrue(rowsUpdate == 10, "Los registros no fueron insertados correctamente en BD's");
    }

    @Test(testName = "Delete Models Usuario",
            dependsOnMethods = "updateModelsUsuario")
    public void deleteModelsUsuario() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        List<UsuarioModel> models = new ArrayList<UsuarioModel>();
        logParrafo("Obtenemos los modelos que poseen nombre es Modelo#5 U #8 o su Correo es #3");
        models = this.usuarioModel.where("Nombre", Operator.LIKE, "%Modelo#5%").or(
                "Nombre", Operator.LIKE, "%Modelo#8%").getAll();
        this.usuarioModel.waitOperationComplete();
        logParrafo("Se recuperaron " + models.size() + " Para eliminar: " + models.toString());
        Integer rowsDelete = this.usuarioModel.deleteALL(models);
        this.usuarioModel.waitOperationComplete();
        logParrafo("Filas eliminadas en BD's: " + rowsDelete + " " + models);
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertTrue(rowsDelete == 2, "Los registros no fueron eliminados correctamente en BD's");
    }

    @Test(testName = "Get In JsonObjects JBSqlUtils Usuario",
            dependsOnMethods = "deleteModelsUsuario")
    public void getInJsonObjectsJBSqlUtilsUsuario() throws Exception {
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
        logParrafo("Obtendra los primeros 2 registros cuyo estado sea true y en su apellido posea la letra a");
        /**
         * Para obtener los registros de una tabla de BD's podemos hacerlo a través del método select envíando como parámetro
         * el nombre de la tabla de la cual deseamos obtener los registros, así mismo podemos filtrar los resultados a través del método
         * where el cual proporciona acceso a metodos por medio de los cuales podemos filtrar los resultados.
         */
        List<JSONObject> lista = select("UsuarioTableTest").getInJsonObjects(columnas);
        logParrafo("Visualizamos los registros obtenidos de BD's: ");
        /**
         * Imprimimos los registros obtenidos
         */
        lista.forEach(fila -> {
            logParrafo(fila.toString());
        });
        Assert.assertTrue(lista.size() == 8, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
    }
    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
}

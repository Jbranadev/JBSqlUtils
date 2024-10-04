package io.github.josecarlosbran.JBSqlUtils;

import UtilidadesTest.TestModel;
import UtilidadesTest.TestModel2;
import UtilidadesTest.UsuarioModel;
import com.josebran.LogsJB.LogsJB;
import com.josebran.LogsJB.Numeracion.NivelLog;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.*;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static UtilidadesTest.Utilities.logParrafo;
import static io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils.dropTableIfExist;
import static io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils.select;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class JBSqlUtilsTestMariaDB {
    TestModel testModel;
    UsuarioModel usuarioModel;

    public JBSqlUtilsTestMariaDB() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        LogsJB.setGradeLog(NivelLog.WARNING);
    }

    @Test(testName = "Setear Properties Conexión for Model")
    public void setPropertiesConexiontoModel() throws DataBaseUndefind, PropertiesDBUndefined {
        /**
         * Instanciamos el modelo indicando que no obtendra las variables globales de conexión
         * de la JVM
         */
        this.testModel = new TestModel(false);
        logParrafo("Se setearan las propiedades de conexión del modelo para MariaDB");
        this.testModel.setGetPropertySystem(false);
        /**
         * Seteamos las propiedades de conexión del modelo
         */
        this.testModel.setPort("6078");
        this.testModel.setHost("127.0.0.1");
        this.testModel.setUser("root");
        this.testModel.setPassword("Bran");
        this.testModel.setBD("JBSQLUTILS");
        this.testModel.setDataBaseType(DataBase.MariaDB);
        logParrafo("Obtendra la conexión del modelo a BD's");
        Assert.assertFalse(Objects.isNull(this.testModel.getConnection()),
                "No se logro establecer la conexión del modelo a BD's, asegurese de haber configurado correctamente" +
                        "las propiedades de conexión a su servidor de BD's en el metodo setPropertiesConexiontoModel()");
        logParrafo("Obtuvo la conexión del modelo a BD's");
        logParrafo("Se setearon las propiedades de conexión del modelo para MariaDB");
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
        TestModel2 testModel2 = new TestModel2(false);
        testModel2.llenarPropertiesFromModel(this.testModel);
        testModel2.dropTableIfExist();
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

    @Test(testName = "Create Table Foraign Key",
            dependsOnMethods = "createTable")
    public void createTableForaignKey() throws Exception {
        TestModel2 testModel2 = new TestModel2(false);
        testModel2.llenarPropertiesFromModel(this.testModel);
        logParrafo("Se creara la tabla " + testModel2.getTableName() + " en BD's");
        Assert.assertTrue(testModel2.createTable(), "La Tabla No fue creada en BD's");
        Assert.assertTrue(testModel2.getTableExist(), "La tabla No existe en BD's ");
        logParrafo("La tabla a sido creada en BD's");
    }

    @Test(testName = "Insert Model",
            dependsOnMethods = "createTableForaignKey")
    public void insertModel() throws Exception {
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
        Integer rowsInsert = this.testModel.save();
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.testModel.waitOperationComplete();
        logParrafo("Insertamos el Modelo a través del metodo save");
        logParrafo("Filas insertadas en BD's: " + rowsInsert + " " + this.testModel.toString());
        Assert.assertEquals((int) rowsInsert, 1, "El registro no fue insertado en BD's");
    }

    @Test(testName = "Clean Model",
            dependsOnMethods = "insertModel")
    public void cleanModel() throws Exception {
        Assert.assertTrue(this.testModel.getName().equalsIgnoreCase("Marcos"));
        Assert.assertTrue(this.testModel.getApellido().equalsIgnoreCase("Cabrera"));
        Assert.assertSame(this.testModel.getIsMayor(), Boolean.FALSE);
        Assert.assertTrue(this.testModel.getModelExist());
        logParrafo("Limpiaremos el Modelo: " + this.testModel.toString());
        this.testModel.cleanModel();
        logParrafo("Modelo despues de realizar la limpieza: " + this.testModel.toString());
        Assert.assertTrue(Objects.isNull(this.testModel.getName()), "No limpio la columna Name del Modelo");
        Assert.assertTrue(Objects.isNull(this.testModel.getApellido()), "No limpio la columna Apellido del Modelo");
        Assert.assertTrue(Objects.isNull(this.testModel.getIsMayor()), "No limpio la columna IsMayor del Modelo");
        Assert.assertFalse(this.testModel.getModelExist(), "No limpio la bandera que indica que el modelo no existe en BD's");
    }

    /**
     *Carla: Metodo original
     */
    @Test(testName = "First Or Fail",
            dependsOnMethods = "cleanModel",
            expectedExceptions = ModelNotFound.class)
    public void firstOrFail() throws Exception {
        TestModel temp = (TestModel) this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Apellido", Operator.IGUAL_QUE,
                "Cabrerassss").firstOrFail();
    }

    /**
     *Carla: Metodo con uso del Completable Feature
     */
    @Test(testName = "First Or Fail Completable Feature",
            dependsOnMethods = "firstOrFail",
            expectedExceptions = ModelNotFound.class)
    public void firstOrFailCompletableFeature() throws Exception {
        CompletableFuture<TestModel> future = CompletableFuture.supplyAsync(() -> {
            try {
                return (TestModel) this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss")
                        .and("Apellido", Operator.IGUAL_QUE, "Cabrerassss")
                        .firstOrFailCompletableFeature()  // Llama al metodo asíncrono
                        .join(); // Espera el resultado
            } catch (ValorUndefined e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Intentamos obtener el modelo, se espera que lance ModelNotFound
        TestModel temp = future.join(); // Bloquea hasta que se complete

        // Si llegamos aquí, no se lanzó la excepción, lo que no debería ocurrir
        Assert.fail("Se esperaba una ModelNotFound excepción");
    }

    /**
     *Carla: Metodo original
     */
    @Test(testName = "First Or Fail Get", dependsOnMethods = "firstOrFailCompletableFeature"
            , expectedExceptions = ModelNotFound.class)
    public void firstOrFailGet() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        logParrafo("Obtenemos el modelo que tiene por nombre Marcos, Apellido Cabrera");
        this.testModel.where("Name", Operator.IGUAL_QUE, "Marcos").and("Apellido", Operator.IGUAL_QUE,
                "Cabrera").firstOrFailGet();
        /**
         * Esperamos ejecute la operación en BD's
         */
        this.testModel.waitOperationComplete();
        logParrafo(this.testModel.toString());
        Assert.assertTrue(this.testModel.getModelExist(), "El Modelo no fue Obtenido de BD's como esperabamos");
        Assert.assertFalse(this.testModel.getIsMayor(), "El Modelo no fue Obtenido de BD's como esperabamos");
        this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss").and("Apellido", Operator.IGUAL_QUE,
                "Cabrerassss").firstOrFailGet();
    }

    /**
     *Carla: Metodo usando Completable Feature
     */
    @Test(testName = "First Or Fail Get", dependsOnMethods = "firstOrFailGet", expectedExceptions = ModelNotFound.class)
    public void firstOrFailGetCompletableFeature() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();

        logParrafo("Obtenemos el modelo que tiene por nombre Marcos, Apellido Cabrera");

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                this.testModel.where("Name", Operator.IGUAL_QUE, "Marcos")
                        .and("Apellido", Operator.IGUAL_QUE, "Cabrera")
                        .firstOrFailGetCompletableFeature();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Esperamos que se complete la operación en la base de datos
        future.join();  // Bloquea hasta que la operación se complete

        logParrafo(this.testModel.toString());
        Assert.assertTrue(this.testModel.getModelExist(), "El Modelo no fue Obtenido de BD's como esperabamos");
        Assert.assertFalse(this.testModel.getIsMayor(), "El Modelo no fue Obtenido de BD's como esperabamos");

        // Intentamos obtener un modelo que no existe para lanzar la excepción
        CompletableFuture<Void> futureNotFound = CompletableFuture.runAsync(() -> {
            try {
                this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss")
                        .and("Apellido", Operator.IGUAL_QUE, "Cabrerassss")
                        .firstOrFailGetCompletableFeature();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Verificamos que se lance la excepción
        Exception exception = Assert.expectThrows(ModelNotFound.class, futureNotFound::join);
        Assert.assertNotNull(exception, "Se esperaba una ModelNotFound excepción");
    }



    @Test(testName = "Reload Model", dependsOnMethods = "firstOrFailGetCompletableFeature")
    public void reloadModel() throws Exception {
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
        temp.setName("MarcosEfrain");
        temp.setIsMayor(true);
        logParrafo(temp.toString());
        /**
         * Recargamos el modelo con la información de BD's
         */
        Boolean reloadModel = temp.reloadModel();
        logParrafo("Refrescamos el Modelo a traves del metodo reloadModel");
        logParrafo(temp.toString());
        Assert.assertTrue(reloadModel, "El Modelo no fue recargado de BD's como esperabamos");
        reloadModel = StringUtils.containsIgnoreCase(temp.getName(), "Marcos");
        Assert.assertTrue(reloadModel, "El Modelo no fue recargado de BD's como esperabamos");
        Assert.assertFalse(temp.getIsMayor(), "El Modelo no fue recargado de BD's como esperabamos");
        /**
         * Actualizamos la información
         */
        logParrafo("Actualizamos el nombre del modelo a Jose, Apellido a Bran y asígnamos que será mayor de edad");
        temp.setName("Jose");
        temp.setApellido("Bran");
        temp.setIsMayor(true);
        logParrafo(temp.toString());
        /**
         * Recargamos el modelo con la información de BD's
         */
        temp.refresh();
        logParrafo("Refrescamos el Modelo a traves del metodo reloadModel");
        logParrafo(temp.toString());
        reloadModel = StringUtils.containsIgnoreCase(temp.getName(), "Marcos");
        Assert.assertTrue(reloadModel, "El Modelo no fue recargado de BD's como esperabamos");
        Assert.assertTrue(StringUtils.containsIgnoreCase(temp.getApellido(), "Cabrera"), "El Modelo no fue recargado de BD's como esperabamos");
        Assert.assertFalse(temp.getIsMayor(), "El Modelo no fue recargado de BD's como esperabamos");
    }

    @Test(testName = "Update Model", dependsOnMethods = "reloadModel")
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
        temp.setName("MarcosEfrain");
        temp.setIsMayor(true);
        logParrafo(temp.toString());
        /**
         * Eliminamos el modelo en BD's
         */
        Integer rowsUpdate = temp.save();
        logParrafo("Guardamos el modelo en BD's");
        Assert.assertEquals((int) rowsUpdate, 1, "El registro no fue actualizado en la BD's");
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
        Assert.assertEquals((int) rowsDelete, 1, "El registro no fue eliminado en la BD's");
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
            /**
             * Instanciamos el modelo indicando que no obtendra las variables de conexión globales
             */
            TestModel model = new TestModel(false);
            /**
             * Trasladamos las propiedades de conexión al modelo a traves del método llenarPropertiesFromModel
             * enviamos como parámetro el modelo desde el cual se obtendran las variables de conexión.
             * @param proveedor Modelo desde el que se obtendran las propiedades de conexión
             */
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
        Assert.assertEquals((int) rowsInsert, 10, "Los registros no fueron insertados correctamente en BD's");
    }

    /**
     *Carla: Metodo original
     */
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


    /**
     *Carla: Metodo aplicando el CompleteableFeature
     */
    @Test(testName = "Get Model CompleteableFeature",
            dependsOnMethods = "getModel")
    public void getModelCompleteableFeature() throws Exception {
        // Incluir metodo que permita limpiar el modelo
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();

        logParrafo("Obtenemos el primer modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        CompletableFuture<Void> future1 = this.testModel.where("Name", Operator.IGUAL_QUE, "Marcossss")
                .and("Apellido", Operator.IGUAL_QUE, "Cabrerassss")
                .getCompletableFeature();

        future1.exceptionally(ex -> {
            // Manejo de excepciones si ocurre
            logParrafo("Error al obtener el modelo: " + ex.getMessage());
            return null;
        }).join(); // Espera a que la operación se complete

        logParrafo("Trato de obtener un modelo que no existe, el resultado es: " + this.testModel.getModelExist());
        logParrafo(this.testModel.toString());
        Assert.assertFalse(this.testModel.getModelExist(), "Obtuve un registro que no existe en BD's");

        logParrafo("Obtenemos un modelo cuyo nombre sea Modelo # y sea mayor de edad");
        CompletableFuture<Void> future2 = this.testModel.where("Name", Operator.LIKE, "%Modelo #%")
                .and("IsMayor", Operator.IGUAL_QUE, true)
                .getCompletableFeature();

        future2.exceptionally(ex -> {
            // Manejo de excepciones si ocurre
            logParrafo("Error al obtener el modelo: " + ex.getMessage());
            return null;
        }).join(); // Espera a que la operación se complete

        logParrafo("Trato de obtener un modelo que sí existe, el resultado es: " + this.testModel.getModelExist());
        logParrafo(this.testModel.toString());
        Assert.assertTrue(this.testModel.getModelExist(), "No obtuvo un registro que no existe en BD's");
    }


    @Test(testName = "Get First Model",
            dependsOnMethods = "getModelCompleteableFeature")
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

    @Test(testName = "Get First Model CompleteableFeature",
            dependsOnMethods = "firstModel")
    public void firstModelCompleteableFeature() throws Exception {
        //Incluir metodo que permita limpiar el modelo
        logParrafo("Limpiamos el modelo");
        this.testModel.cleanModel();
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        CompletableFuture<TestModel> future = this.testModel
                .where("Name", Operator.IGUAL_QUE, "Marcossss")
                .and("Apellido", Operator.IGUAL_QUE, "Cabrerassss")
                .firstCompleteableFeature();
        // Esperar el resultado de la operación asíncrona
        TestModel temp = future.join(); // o puedes usar get() si prefieres manejar la excepción
        this.testModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que no existe, el resultado es: " + temp.getModelExist());
        logParrafo(temp.toString());
        Assert.assertFalse(temp.getModelExist(), "Obtuvo un registro que no existe en BD's");
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Modelo # y sea mayor de edad");
        future = this.testModel
                .where("Name", Operator.LIKE, "%Modelo #%")
                .and("IsMayor", Operator.IGUAL_QUE, true)
                .firstCompleteableFeature();
        // Esperar el resultado de la operación asíncrona
        temp = future.join(); // o get()
        this.testModel.waitOperationComplete();
        logParrafo("Trato de obtener un modelo que sí existe, el resultado es: " + temp.getModelExist());
        logParrafo(temp.toString());
        Assert.assertTrue(temp.getModelExist(), "No obtuvo un registro que no existe en BD's");
    }

    @Test(testName = "Take Models",
            dependsOnMethods = "firstModelCompleteableFeature")
    public void takeModels() throws Exception {
        logParrafo("Limpiamos el modelo");
        //Incluir metodo que permita limpiar el modelo
        this.testModel.cleanModel();
        List<TestModel> models = new ArrayList<TestModel>();
        logParrafo("Recuperamos los primeros seis modelos que en su nombre poseen el texto Modelo #");
        models = this.testModel.where("Name", Operator.LIKE, "%Modelo #%").take(6).get();
        this.testModel.waitOperationComplete();
        logParrafo("Se recuperaron " + models.size() + " los cuales son: " + models);
        Assert.assertEquals(models.size(), 6, "Los modelos no fueron recuperados de BD's como se definio en la sentencia Take");
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
        logParrafo("Se recuperaron " + models.size() + " los cuales son: " + models);
        Assert.assertEquals(models.size(), 3, "Los modelos no fueron recuperados de BD's");
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
            modelo.setIsMayor(!modelo.getIsMayor());
            logParrafo("Modelo a actualizar: " + modelo);
        });
        logParrafo("Enviamos a guardar los modelos a través del metodo saveALL");
        Integer rowsUpdate = this.testModel.saveALL(models);
        logParrafo("Filas actualizadas en BD's: " + rowsUpdate);
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.testModel.waitOperationComplete();
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertEquals((int) rowsUpdate, 10, "Los registros no fueron insertados correctamente en BD's");
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
        logParrafo("Se recuperaron " + models.size() + " Para eliminar: " + models);
        Integer rowsDelete = this.testModel.deleteALL(models);
        this.testModel.waitOperationComplete();
        logParrafo("Filas eliminadas en BD's: " + rowsDelete + " " + models);
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertEquals((int) rowsDelete, 3, "Los registros no fueron eliminados correctamente en BD's");
    }

    @Test(testName = "Setear Properties Conexión Globales",
            dependsOnMethods = "deleteModels")
    public void setPropertiesConexion() {
        logParrafo("Seteara las propiedades de conexión Globales para MariaDB");
        JBSqlUtils.setDataBaseGlobal("JBSQLUTILS");
        JBSqlUtils.setPortGlobal("6078");
        JBSqlUtils.setHostGlobal("127.0.0.1");
        JBSqlUtils.setUserGlobal("root");
        JBSqlUtils.setPasswordGlobal("Bran");
        JBSqlUtils.setDataBaseTypeGlobal(DataBase.MariaDB);
        JBSqlUtils.setPropertisUrlConexionGlobal("?autoReconnect=true&useSSL=false");
        logParrafo("Ha seteado las propiedades de conexión globales para MariaDB");
        Assert.assertTrue("JBSQLUTILS".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBNAME.getPropiertie())),
                "Propiedad Nombre BD's no ha sido seteada correctamente");
        Assert.assertTrue("6078".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBPORT.getPropiertie())),
                "Propiedad Puerto BD's no ha sido seteada correctamente");
        Assert.assertTrue("127.0.0.1".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBHOST.getPropiertie())),
                "Propiedad Host BD's no ha sido seteada correctamente");
        Assert.assertTrue("root".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBUSER.getPropiertie())),
                "Propiedad Usuario BD's no ha sido seteada correctamente");
        Assert.assertTrue("Bran".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBPASSWORD.getPropiertie())),
                "Propiedad Password BD's no ha sido seteada correctamente");
        Assert.assertTrue(DataBase.MariaDB.name().equalsIgnoreCase(System.getProperty(ConeccionProperties.DBTYPE.getPropiertie())),
                "Propiedad Tipo de BD's no ha sido seteada correctamente");
        Assert.assertTrue("?autoReconnect=true&useSSL=false".equalsIgnoreCase(System.getProperty(ConeccionProperties.DBPROPERTIESURL.getPropiertie())),
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
        Assert.assertEquals(registros, 1, "No se pudo insertar el registro de Erick en la tabla Proveedor de BD's");
        registros = 0;
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Daniel").andValue("Apellido", "Quiñonez").
                andValue("Estado", false).execute();
        logParrafo("Resultado de insertar el registro de Daniel en la tabla Proveedor: " + registros);
        Assert.assertEquals(registros, 1, "No se pudo insertar el registro de Daniel en la tabla Proveedor de BD's");
        registros = 0;
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Ligia").andValue("Apellido", "Camey")
                .andValue("Estado", true).execute();
        logParrafo("Resultado de insertar el registro de Ligia en la tabla Proveedor: " + registros);
        Assert.assertEquals(registros, 1, "No se pudo insertar el registro de Ligia en la tabla Proveedor de BD's");
        registros = 0;
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Elsa").andValue("Apellido", "Aguirre")
                .andValue("Estado", false).execute();
        logParrafo("Resultado de insertar el registro de Elsa en la tabla Proveedor: " + registros);
        Assert.assertEquals(registros, 1, "No se pudo insertar el registro de Elsa en la tabla Proveedor de BD's");
        registros = 0;
        registros += JBSqlUtils.insertInto("Proveedor").value("Name", "Alex").andValue("Apellido", "Garcia")
                .execute();
        logParrafo("Resultado de insertar el registro de Alex en la tabla Proveedor: " + registros);
        Assert.assertEquals(registros, 1, "No se pudo insertar el registro de Alex en la tabla Proveedor de BD's");
    }

    @Test(testName = "Get In JsonObjects JBSqlUtils",
            dependsOnMethods = "insertIntoJBSqlUtils")
    public void getInJsonObjectsJBSqlUtils() throws Exception {
        /**
         * Si deseamos obtener todas las columnas de la tabla envíamos el parametro columnas del metodo
         * getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parametro
         * del metodo select
         */
        /**
         * Si deseamos obtener unicamente determinadas columnas, es necesario envíar como parametro una lista de strings
         * con los nombres de las columnas que deseamos obtener del metodo getInJsonObjects
         */
        logParrafo("Obtendra los primeros 2 registros cuyo estado sea true y en su apellido posea la letra a");
        /**
         * Para obtener los registros de una tabla de BD's podemos hacerlo a través del metodo select envíando como parametro
         * el nombre de la tabla de la cual deseamos obtener los registros, así mismo podemos filtrar los resultados a través del metodo
         * where el cual proporciona acceso a metodos por medio de los cuales podemos filtrar los resultados.
         */
        List<JSONObject> lista = select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
                .and("Apellido", Operator.LIKE, "%a%").take(2).getInJsonObjects("Id", "Name");
        logParrafo("Visualizamos los registros obtenidos de BD's: ");
        /**
         * Imprimimos los registros obtenidos
         */
        lista.forEach(fila -> {
            logParrafo(fila.toString());
        });
        Assert.assertEquals(lista.size(), 2, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
    }

    @Test(testName = "Update JBSqlUtils",
            dependsOnMethods = "getInJsonObjectsJBSqlUtils")
    public void updateJBSqlUtils() throws Exception {
        /**
         * Si deseamos obtener todas las columnas de la tabla envíamos el parametro columnas del metodo
         * getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parametro
         * del metodo select
         */
        logParrafo("Obtendra los primeros 2 registros cuyo estado sea true y en su apellido posea la letra a");
        List<JSONObject> lista = select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
                .and("Apellido", Operator.LIKE, "%a%").take(2).getInJsonObjects(null);
        /**
         * Imprimimos los registros obtenidos
         */
        int rowsUpdate = 0;
        for (JSONObject fila : lista) {
            if (fila.getString("NAME").equalsIgnoreCase("Ligia") && fila.getString("APELLIDO").equalsIgnoreCase("Camey")) {
                logParrafo("Actualizara la fila que corresponde a Ligia Camey: ");
                logParrafo(fila.toString());
                rowsUpdate += JBSqlUtils.update("Proveedor").set("Name", "Futura").andSet("Apellido", "Prometida")
                        .where("Id", Operator.IGUAL_QUE, fila.getInt("ID")).execute();
                logParrafo("Filas actualizadas en BD's: " + rowsUpdate);
            }
        }
        Assert.assertEquals(rowsUpdate, 1, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
    }

    @Test(testName = "Delete JBSqlUtils",
            dependsOnMethods = "updateJBSqlUtils")
    public void deleteJBSqlUtils() throws Exception {
        /**
         * Si deseamos obtener todas las columnas de la tabla envíamos el parametro columnas del metodo
         * getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parametro
         * del metodo select
         */
        logParrafo("Obtenedra todos los registros que tienen estado True y en su apellido tienen una letra a");
        List<JSONObject> lista = select("Proveedor").where("Estado", Operator.IGUAL_QUE, true)
                .and("Apellido", Operator.LIKE, "%a%").take(5).getInJsonObjects(null);
        /**
         * Imprimimos los registros obtenidos
         */
        int rowsDelete = 0;
        for (JSONObject fila : lista) {
            if (fila.getInt("ID") == 5) {
                logParrafo("VIsualizamos la Fila a eliminar, cuyo Id es igual a 5: ");
                logParrafo(fila.toString());
                rowsDelete += JBSqlUtils.delete("Proveedor").where("Id", Operator.IGUAL_QUE, fila.getInt("ID")).execute();
                logParrafo("Filas eliminadas en BD's: " + rowsDelete);
            }
        }
        Assert.assertEquals(rowsDelete, 1, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
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
        this.usuarioModel.setId_Subestación(2.0);
        this.usuarioModel.setNombre("NombrePrimerModelo");
        this.usuarioModel.setTelefono(null);
        this.usuarioModel.setCorreo("CorreoPrueba");
        this.usuarioModel.setEstado(false);
        logParrafo(this.usuarioModel.toString());
        Integer rowsInsert = this.usuarioModel.save();
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.usuarioModel.waitOperationComplete();
        logParrafo("Insertamos el Modelo a través del método save");
        logParrafo("Filas insertadas en BD's: " + rowsInsert + " " + this.usuarioModel.toString());
        Assert.assertEquals((int) rowsInsert, 1, "El registro no fue insertado en BD's");
    }

    @Test(testName = "Reload Model Usuario", dependsOnMethods = "insertModelUsuario")
    public void reloadModelUsuario() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        logParrafo("Obtenemos el modelo que tiene por nombre Marcos, Apellido Cabrera");
        UsuarioModel temp = (UsuarioModel) this.usuarioModel.where("Nombre", Operator.IGUAL_QUE, "NombrePrimerModelo").and("Estado", Operator.IGUAL_QUE,
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
        temp.setNombre("MarcosEfrain");
        temp.setEstado(true);
        logParrafo(temp.toString());
        /**
         * Recargamos el modelo con la información de BD's
         */
        Boolean reloadModel = temp.reloadModel();
        logParrafo("Refrescamos el Modelo a traves del metodo reloadModel");
        logParrafo(temp.toString());
        Assert.assertTrue(reloadModel, "El Modelo no fue recargado de BD's como esperabamos");
        reloadModel = StringUtils.containsIgnoreCase(temp.getNombre(), "NombrePrimerModelo");
        Assert.assertTrue(reloadModel, "El Modelo no fue recargado de BD's como esperabamos");
        Assert.assertFalse(temp.getEstado(), "El Modelo no fue recargado de BD's como esperabamos");
        /**
         * Actualizamos la información
         */
        logParrafo("Actualizamos el nombre del modelo a Jose, Apellido a Bran y asígnamos que será mayor de edad");
        temp.setNombre("Jose");
        temp.setCorreo("Bran");
        temp.setEstado(true);
        logParrafo(temp.toString());
        /**
         * Recargamos el modelo con la información de BD's
         */
        temp.refresh();
        logParrafo("Refrescamos el Modelo a traves del metodo reloadModel");
        logParrafo(temp.toString());
        reloadModel = StringUtils.containsIgnoreCase(temp.getNombre(), "NombrePrimerModelo");
        Assert.assertTrue(reloadModel, "El Modelo no fue recargado de BD's como esperabamos");
        Assert.assertTrue(StringUtils.containsIgnoreCase(temp.getCorreo(), "CorreoPrueba"), "El Modelo no fue recargado de BD's como esperabamos");
        Assert.assertFalse(temp.getEstado(), "El Modelo no fue recargado de BD's como esperabamos");
    }

    @Test(testName = "Update Model Usuario", dependsOnMethods = "reloadModelUsuario")
    public void updateModelUsuario() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        logParrafo("Obtenemos el modelo que tiene por nombre Marcos, Apellido Cabrera");
        UsuarioModel temp = (UsuarioModel) this.usuarioModel.where("Nombre", Operator.IGUAL_QUE, "NombrePrimerModelo").and("Estado", Operator.IGUAL_QUE,
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
        temp.setNombre("MarcosEfrain");
        temp.setEstado(true);
        temp.setId_Subestación(null);
        logParrafo(temp.toString());
        /**
         * Eliminamos el modelo en BD's
         */
        Integer rowsUpdate = temp.save();
        logParrafo("Guardamos el modelo en BD's");
        Assert.assertEquals((int) rowsUpdate, 1, "El registro no fue actualizado en la BD's");
    }

    @Test(testName = "Delete Model Usuario", dependsOnMethods = "updateModelUsuario")
    public void deleteModelUsuario() throws Exception {
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        /**
         * Obtenemos el modelo de BD's de lo contrario lanza ModelNotFoundException
         */
        logParrafo("Obtenemos el modelo que tiene por nombre MarcosEfrain, Apellido Cabrera y es Mayor de Edad");
        UsuarioModel temp = (UsuarioModel) this.usuarioModel.where("Nombre", Operator.IGUAL_QUE, "MarcosEfrain").and("Estado", Operator.IGUAL_QUE, true).firstOrFail();
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
        Assert.assertEquals((int) rowsDelete, 1, "El registro no fue eliminado en la BD's");
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
            model.setId_Subestación((1.0 * i));
            model.setNombre("NombreModelo#" + i);
            model.setCorreo("CorreoPrueba#" + i);
            if (i % 2 == 0) {
                model.setEstado(false);
                model.setTimestamps(false);
            } else {
                model.setEstado(true);
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
        Assert.assertEquals((int) rowsInsert, 10, "Los registros no fueron insertados correctamente en BD's");
    }

    @Test(testName = "Get Model Usuario",
            dependsOnMethods = "insertModelsUsuario")
    public void getModelUsuario() throws Exception {
        //Incluir método que permita limpiar el modelo
        logParrafo("Limpiamos el modelo");
        this.usuarioModel.cleanModel();
        logParrafo("Obtenemos el primero modelo cuyo nombre sea Marcossss y su apellido sea Cabrerassss, el cual no existe");
        this.usuarioModel.where("Nombre", Operator.IGUAL_QUE, "Marcossss").and("Correo", Operator.IGUAL_QUE,
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
        UsuarioModel temp = (UsuarioModel) this.usuarioModel.where("Nombre", Operator.IGUAL_QUE, "Marcossss").and("Correo", Operator.IGUAL_QUE,
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
        logParrafo("Se recuperaron " + models.size() + " los cuales son: " + models);
        Assert.assertEquals(models.size(), 6, "Los modelos no fueron recuperados de BD's como se definio en la sentencia Take");
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
        logParrafo("Se recuperaron " + models.size() + " los cuales son: " + models);
        Assert.assertEquals(models.size(), 2, "Los modelos no fueron recuperados de BD's");
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
            modelo.setEstado(!modelo.getEstado());
            logParrafo("Modelo a actualizar: " + modelo);
        });
        logParrafo("Enviamos a guardar los modelos a través del método saveALL");
        Integer rowsUpdate = this.usuarioModel.saveALL(models);
        logParrafo("Filas actualizadas en BD's: " + rowsUpdate);
        /**
         * Esperamos se ejecute la instrucción en BD's
         */
        this.usuarioModel.waitOperationComplete();
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertEquals((int) rowsUpdate, 10, "Los registros no fueron insertados correctamente en BD's");
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
        logParrafo("Se recuperaron " + models.size() + " Para eliminar: " + models);
        Integer rowsDelete = this.usuarioModel.deleteALL(models);
        this.usuarioModel.waitOperationComplete();
        logParrafo("Filas eliminadas en BD's: " + rowsDelete + " " + models);
        //Verificamos que la cantidad de filas insertadas corresponda a la cantidad de modelos enviados a realizar el insert
        Assert.assertEquals((int) rowsDelete, 2, "Los registros no fueron eliminados correctamente en BD's");
    }

    @Test(testName = "Get In JsonObjects JBSqlUtils Usuario",
            dependsOnMethods = "deleteModelsUsuario")
    public void getInJsonObjectsJBSqlUtilsUsuario() throws Exception {
        /**
         * Si deseamos obtener todas las columnas de la tabla envíamos el parámetro columnas del método
         * getInJsonObjects como null, de esa manera nos obtendra todas las columnas de la tabla especificada como parámetro
         * del método select
         */
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
        List<JSONObject> lista = select("UsuarioTableTest").getInJsonObjects(null);
        logParrafo("Visualizamos los registros obtenidos de BD's: ");
        /**
         * Imprimimos los registros obtenidos
         */
        lista.forEach(fila -> {
            logParrafo(fila.toString());
        });
        Assert.assertEquals(lista.size(), 8, "No se pudo obtener las tuplas que cumplen con los criterios de busqueda en una lista de JsonObject");
    }
    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
}
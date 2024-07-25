/***
 * Copyright (C) 2022 El proyecto de código abierto JBSqlUtils de José Bran
 *
 * Con licencia de Apache License, Versión 2.0 (la "Licencia");
 * no puede usar este archivo excepto de conformidad con la Licencia.
 * Puede obtener una copia de la Licencia en
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * A menos que lo exija la ley aplicable o se acuerde por escrito, el software
 * distribuido bajo la Licencia se distribuye "TAL CUAL",
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
 * Consulte la Licencia para conocer el idioma específico que rige los permisos y
 * limitaciones bajo la Licencia.
 */
package UtilidadesTest;

import io.github.josecarlosbran.JBSqlUtils.Anotations.Actions;
import io.github.josecarlosbran.JBSqlUtils.Anotations.ColumnDefined;
import io.github.josecarlosbran.JBSqlUtils.Anotations.ForeignKey;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Action;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operacion;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.testng.annotations.Ignore;

/**
 * @author Jose Bran
 * Clase de Pruebas
 */
@Ignore
@ToString
@Getter
@Setter
public class TestModel2 extends JBSqlUtils {

    /**
     * Declara un miembro del modelo, el cual en java almacenara un dato de tipo Integer.
     * <p>
     * Si deseamos la tabla sea creada en Bd's a traves de JBSqlUtils, podemos definir las propiedades que tendría cada columna y
     * cual sería su representación en Java a traves de la etiqueta ColumnDefined, configurando así la información que deseamos
     * JBSqlUtils tenga en consideración al momento de crear la tabla.
     * <p>
     * Agregamos dos restricciones SQL las cuales serán útiles si deseamos utilizar el modelo para crear la tabla en BD's
     * desde nuestra aplicación en caso esta no exista, de lo contrario no es necesario que agreguemos restricciones.
     */

    @ColumnDefined(name = "Id", dataTypeSQL = DataType.INTEGER, constraints = {
            Constraint.PRIMARY_KEY, Constraint.AUTO_INCREMENT
    })
    private Integer id;

    @ColumnDefined(name = "Id2", dataTypeSQL = DataType.INTEGER,
            foreignkey = @ForeignKey(columName = "id2", tableReference = "testModel",
                    columnReference = "id",
            actions = {@Actions(operacion = Operacion.UPDATE, action = Action.CASCADE)}))
    private Integer id2;


    @ColumnDefined(name = "id3", dataTypeSQL = DataType.INTEGER,
            foreignkey = @ForeignKey(columName = "id3", tableReference = "testModel", columnReference = "id",
            actions = {@Actions(operacion = Operacion.DELETE, action = Action.CASCADE),
                    @Actions(operacion = Operacion.UPDATE, action = Action.NO_ACTION)}))
    private Integer id3;


    @ColumnDefined(name = "isMayor", dataTypeSQL = DataType.BIT, constraints = {
            Constraint.DEFAULT
    }, default_value = "true")
    private Boolean isMayor;

    /**
     * En el constructor de nuestra clase que utilizaremos como modelo al heredar la clase JBSqlutils
     * hacemos el llamado al constructor de la clase JBSqlUtils la cual inicializara el modelo para poder
     * ser utilizado, una vez instanciado el modelo, podremos obtener uno o varios registros de la tabla
     * correspondiente al modelo, insertar, actualizar o eliminar registros.
     * <p>
     * Es importante que antes de instanciar un modelo que herede la clase JBSqlUtils se hayan definido
     * las propiedades de conexión como variables del sistema.
     */
    public TestModel2() {
        /**
         * Hacemos el llamado al constructor de la Clase JBSqlUtils
         */
        super();
        this.setTableName("testModel2");
    }

    /**
     * Si deseamos que nuestro modelo no utilice las propiedades de conexión globales, podemos crear un constructor que
     * reciba un único parametro de tipo Booleano el cual enviamos como parametro al constructor de la clase padre JBSqlUtils
     *
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema,
     *                          true si deseamos que obtenga las variables globales y false si deseamos que no obtenga las
     *                          variables globales
     */
    public TestModel2(Boolean getPropertySystem) throws DataBaseUndefind, PropertiesDBUndefined {
        super(getPropertySystem);
        this.setTableName("testModel2");
    }

    public Integer getOne(Integer numero) {
        return numero;
    }

    public void setOne(Integer numero, Integer numero2) {
    }

    public void setOne() {
    }
}

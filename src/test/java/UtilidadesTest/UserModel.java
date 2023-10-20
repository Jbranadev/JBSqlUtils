package UtilidadesTest;

import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.Ignore;

/**
 * clase UserModel hereda de la clase JbSqlUtils los metodos
 */
@Ignore
@Setter
@Getter
public class UserModel extends JBSqlUtils {

    /**
     * Definiendo los atributos del modelo, uno por cada columna en la tabla correspondiente al modelo
     *
     * @Getter con esta etiqueta creamos los get por medio de la dependencia lombok
     * @Setter con esta etiqueta creamos los set por medio de la dependencia lombok
     */


    private Column<Integer> Id_Usuario1 = new Column<>(DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Id_Usuario", dataTypeSQL = DataType.INTEGER, constraints = {
            Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY
    })
    private Integer Id_Usuario;

    private Column<String> Usuario1 = new Column<>(DataType.VARCHAR);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Usuario", dataTypeSQL = DataType.VARCHAR, size = "200")
    private String Usuario;

    private Column<String> PasswordUser1 = new Column<>(DataType.VARCHAR);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "PasswordUser", dataTypeSQL = DataType.VARCHAR, size = "200")
    private String PasswordUser;

    private Column<String> Rol1 = new Column<>(DataType.VARCHAR, "'Despachador'", Constraint.DEFAULT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Rol", dataTypeSQL = DataType.VARCHAR, constraints = {
            Constraint.DEFAULT
    }, default_value = "'Despachador'", size = "200")
    private String Rol;

    private Column<String> TokenActual1 = new Column<>(DataType.VARCHAR);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "TokenActual", dataTypeSQL = DataType.VARCHAR, size = "2000")
    private String TokenActual;

    private Column<String> TokenAnterior1 = new Column<>(DataType.VARCHAR);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "TokenAnterior", dataTypeSQL = DataType.VARCHAR, size = "2000")
    private String TokenAnterior;

    /**
     * @throws DataBaseUndefind      se realiza las exepciones DataBaseUndefind si no encuentra definida el tipo de BD`S a la que se
     *                               conectara el modelo
     * @throws PropertiesDBUndefined Muestra la excepcion si en las propiedades del sistema no estan definidas las propiedades
     *                               de conxeion para conectarse  ala BD's especificada
     * @throws ValorUndefined
     */

    public UserModel() throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        super();
        this.setTableName("Usuario");
        this.getUsuario1().setSize("100");
        this.getPasswordUser1().setSize("200");
        this.getRol1().setSize("100");
        this.getTokenActual1().setSize("2000");
        this.getTokenAnterior1().setSize("2000");
    }
}

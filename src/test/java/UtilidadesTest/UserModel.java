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
public class UserModel extends JBSqlUtils {

    /**
     * Definiendo los atributos del modelo, uno por cada columna en la tabla correspondiente al modelo
     *
     * @Getter con esta etiqueta creamos los get por medio de la dependencia lombok
     * @Setter con esta etiqueta creamos los set por medio de la dependencia lombok
     */

    @Getter
    @Setter
    private Column<Integer> Id_Usuario = new Column<>(DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);
    @Getter
    @Setter
    private Column<String> Usuario = new Column<>(DataType.VARCHAR);
    @Getter
    @Setter
    private Column<String> PasswordUser = new Column<>(DataType.VARCHAR);
    @Getter
    @Setter
    private Column<String> Rol = new Column<>(DataType.VARCHAR, "'Despachador'", Constraint.DEFAULT);
    @Getter
    @Setter
    private Column<String> TokenActual = new Column<>(DataType.VARCHAR);
    @Getter
    @Setter
    private Column<String> TokenAnterior = new Column<>(DataType.VARCHAR);

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
        this.getUsuario().setSize("100");
        this.getPasswordUser().setSize("200");
        this.getRol().setSize("100");
        this.getTokenActual().setSize("2000");
        this.getTokenAnterior().setSize("2000");

    }

}

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
    Column<Integer> Id_Usuari1o = new Column<Integer>("Id_Usuario", DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);
    Column<Float> Id_Rol1 = new Column<>((float) 1.5, DataType.FLOAT, Constraint.NOT_NULL);
    Column<Double> Id_Subestación1 = new Column<>(DataType.DOUBLE);
    Column<String> Usuario2 = new Column<>("Usuario", DataType.TEXT, "'Daniel'", Constraint.DEFAULT);
    Column<String> Contraseña1 = new Column<>("Contraseña", DataType.TEXT, "'ContraseñaDefault'", Constraint.DEFAULT);
    Column<String> Nombre1 = new Column<>(DataType.TEXT);
    Column<String> CUI1 = new Column<>("CUI", "3214764330502", DataType.TEXT);
    Column<String> Telefono1 = new Column<>("Telefono", "50834811", "'TelefonoDefault'", DataType.TEXT, Constraint.DEFAULT);
    Column<String> Correo1 = new Column<>("Correo", DataType.TEXT, "'Correo Default'", Constraint.DEFAULT);
    Column<String> Token_Oaut1 = new Column<>("Token", DataType.TEXT);
    Column<String> Token_Actual1 = new Column<>("Token_Actual", "Token", DataType.TEXT, Constraint.NOT_NULL);
    Column<String> Token_Anterior1 = new Column<>(DataType.TEXT);
    Column<Boolean> Estado1 = new Column<>(true, DataType.BOOLEAN);
    /**
     * Definiendo los atributos del modelo, uno por cada columna en la tabla correspondiente al modelo
     *
     * @Getter con esta etiqueta creamos los get por medio de la dependencia lombok
     * @Setter con esta etiqueta creamos los set por medio de la dependencia lombok
     */
    private Column<Integer> Id_Usuario1 = new Column<>(DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);
    private Column<String> Usuario1 = new Column<>(DataType.VARCHAR);
    private Column<String> PasswordUser1 = new Column<>(DataType.VARCHAR);
    private Column<String> Rol1 = new Column<>(DataType.VARCHAR, "'Despachador'", Constraint.DEFAULT);
    private Column<String> TokenActual1 = new Column<>(DataType.VARCHAR);
    private Column<String> TokenAnterior1 = new Column<>(DataType.VARCHAR);
    private Column<Boolean> isMayor1 = new Column<>(DataType.BIT, "true", Constraint.DEFAULT);
    private Column<String> apellido1 = new Column<>(DataType.VARCHAR);
    private Column<String> name1 = new Column<>(DataType.VARCHAR, "'Daniel'", Constraint.DEFAULT);

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
        this.getName1().setSize("200");
        this.getApellido1().setSize("200");
    }
}

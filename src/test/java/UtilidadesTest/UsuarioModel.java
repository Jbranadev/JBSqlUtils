package UtilidadesTest;

import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel extends JBSqlUtils {

    Column<Integer> Id_Usuari1o = new Column<Integer>("Id_Usuario", DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Id_Usuario", dataTypeSQL = DataType.INTEGER, constraints = {
            Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY
    })
    private Integer Id_Usuario;

    Column<Float> Id_Rol1 = new Column<>((float) 1.5, DataType.FLOAT, Constraint.NOT_NULL);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Id_Rol", dataTypeSQL = DataType.FLOAT, constraints = {
            Constraint.NOT_NULL, Constraint.DEFAULT
    }, default_value = "1.5")
    private Float Id_Rol;

    Column<Double> Id_Subestación1 = new Column<>(DataType.DOUBLE);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Id_Subestación", dataTypeSQL = DataType.DOUBLE)
    private Double Id_Subestación;

    Column<String> Usuario1 = new Column<>("Usuario", DataType.TEXT, "'Daniel'", Constraint.DEFAULT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Usuario", dataTypeSQL = DataType.TEXT, constraints = {
             Constraint.DEFAULT
    }, default_value = "'Daniel'")
    private String Usuario;


    Column<String> Contraseña1 = new Column<>("Contraseña", DataType.TEXT, "'ContraseñaDefault'", Constraint.DEFAULT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Contraseña", dataTypeSQL = DataType.TEXT, constraints = {
            Constraint.DEFAULT
    }, default_value = "'ContraseñaDefault'")
    private String Contraseña;

    Column<String> Nombre1 = new Column<>(DataType.TEXT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Nombre", dataTypeSQL = DataType.TEXT)
    private String Nombre;

    Column<String> CUI1 = new Column<>("CUI", "3214764330502", DataType.TEXT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "CUI", dataTypeSQL = DataType.TEXT)
    private String CUI;


    Column<String> Telefono1 = new Column<>("Telefono", "50834811", "'TelefonoDefault'", DataType.TEXT, Constraint.DEFAULT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Telefono", dataTypeSQL = DataType.TEXT, constraints = {
            Constraint.DEFAULT
    }, default_value = "'TelefonoDefault'")
    private String Telefono;

    Column<String> Correo1 = new Column<>("Correo", DataType.TEXT, "'Correo Default'", Constraint.DEFAULT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Correo", dataTypeSQL = DataType.TEXT, constraints = {
            Constraint.DEFAULT
    }, default_value = "'Correo Default'")
    private String Correo;

    Column<String> Token_Oaut1 = new Column<>("Token", DataType.TEXT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Token_Oaut", dataTypeSQL = DataType.TEXT)
    private String Token_Oaut;


    Column<String> Token_Actual1 = new Column<>("Token_Actual", "Token", DataType.TEXT, Constraint.NOT_NULL);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Token_Actual", dataTypeSQL = DataType.TEXT)
    private String Token_Actual;


    Column<String> Token_Anterior1 = new Column<>(DataType.TEXT);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(name = "Token_Anterior", dataTypeSQL = DataType.TEXT)
    private String Token_Anterior;


    Column<Boolean> Estado1 = new Column<>(true, DataType.BOOLEAN);

    @io.github.josecarlosbran.JBSqlUtils.Anotations.Column(dataTypeSQL = DataType.BOOLEAN)
    private Boolean Estado;

    public UsuarioModel() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
        this.setTableName("UsuarioTableTest");
    }
}

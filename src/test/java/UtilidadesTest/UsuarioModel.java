package UtilidadesTest;

import io.github.josecarlosbran.JBSqlUtils.Anotations.ColumnDefined;
import io.github.josecarlosbran.JBSqlUtils.Anotations.Index;
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
    Column<Float> Id_Rol1 = new Column<>((float) 1.5, DataType.FLOAT, Constraint.NOT_NULL);
    Column<Double> Id_Subestación1 = new Column<>(DataType.DOUBLE);
    Column<String> Usuario1 = new Column<>("Usuario", DataType.TEXT, "'Daniel'", Constraint.DEFAULT);
    Column<String> Contraseña1 = new Column<>("Contraseña", DataType.TEXT, "'ContraseñaDefault'", Constraint.DEFAULT);
    Column<String> Nombre1 = new Column<>(DataType.TEXT);
    Column<String> CUI1 = new Column<>("CUI", "3214764330502", DataType.TEXT);
    Column<String> Telefono1 = new Column<>("Telefono", "50834811", "'TelefonoDefault'", DataType.TEXT, Constraint.DEFAULT);
    Column<String> Correo1 = new Column<>("Correo", DataType.TEXT, "'Correo Default'", Constraint.DEFAULT);
    Column<String> Token_Oaut1 = new Column<>("Token", DataType.TEXT);
    Column<String> Token_Actual1 = new Column<>("Token_Actual", "Token", DataType.TEXT, Constraint.NOT_NULL);
    Column<String> Token_Anterior1 = new Column<>(DataType.TEXT);
    Column<Boolean> Estado1 = new Column<>(true, DataType.BOOLEAN);
    @ColumnDefined(name = "Id_Usuario", dataTypeSQL = DataType.INTEGER, constraints = {
            Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY
    })
    private Integer Id_Usuario;
    @ColumnDefined(name = "Id_Rol", dataTypeSQL = DataType.FLOAT, constraints = {
            Constraint.NOT_NULL, Constraint.DEFAULT
    }, default_value = "1.5")
    private Float Id_Rol;
    @ColumnDefined(name = "Id_Subestación", dataTypeSQL = DataType.DOUBLE)
    @Index
    private Double Id_Subestación;
    @ColumnDefined(name = "Usuario", dataTypeSQL = DataType.TEXT, constraints = {
            Constraint.DEFAULT
    }, default_value = "'Daniel'")
    private String Usuario;
    @ColumnDefined(name = "Contraseña", dataTypeSQL = DataType.TEXT, constraints = {
            Constraint.DEFAULT
    }, default_value = "'ContraseñaDefault'")
    private String Contraseña;
    @ColumnDefined(name = "Nombre", dataTypeSQL = DataType.TEXT)
    private String Nombre;
    @ColumnDefined(name = "CUI", dataTypeSQL = DataType.TEXT)
    private String CUI;
    @ColumnDefined(name = "Telefono", dataTypeSQL = DataType.TEXT, constraints = {
            Constraint.DEFAULT
    }, default_value = "'TelefonoDefault'")
    private String Telefono;
    @ColumnDefined(name = "Correo", dataTypeSQL = DataType.TEXT, constraints = {
            Constraint.DEFAULT
    }, default_value = "'Correo Default'")
    private String Correo;
    @ColumnDefined(name = "Token_Oaut", dataTypeSQL = DataType.TEXT)
    private String Token_Oaut;
    @ColumnDefined(name = "Token_Actual", dataTypeSQL = DataType.TEXT)
    private String Token_Actual;
    @ColumnDefined(name = "Token_Anterior", dataTypeSQL = DataType.TEXT)
    private String Token_Anterior;
    @ColumnDefined(dataTypeSQL = DataType.BOOLEAN)
    private Boolean Estado;

    public UsuarioModel() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
        this.setTableName("UsuarioTableTest");
    }
}

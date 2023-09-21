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

    Column<Integer> Id_Usuario = new Column<Integer>("Id_Usuario", DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);
    Column<Float> Id_Rol = new Column<>((float) 1.5, DataType.FLOAT, Constraint.NOT_NULL);
    Column<Double> Id_Subestación = new Column<>(DataType.DOUBLE);
    Column<String> Usuario = new Column<>("Usuario", DataType.TEXT, "'Daniel'", Constraint.DEFAULT);
    Column<String> Contraseña = new Column<>("Contraseña", DataType.TEXT, "'ContraseñaDefault'", Constraint.DEFAULT);
    Column<String> Nombre = new Column<>(DataType.TEXT);
    Column<String> CUI = new Column<>("CUI", "3214764330502", DataType.TEXT);
    Column<String> Telefono = new Column<>("Telefono", "50834811", "'TelefonoDefault'", DataType.TEXT, Constraint.DEFAULT);
    Column<String> Correo = new Column<>("Correo", DataType.TEXT, "'Correo Default'", Constraint.DEFAULT);
    Column<String> Token_Oaut = new Column<>("Token", DataType.TEXT);
    Column<String> Token_Actual = new Column<>("Token_Actual", "Token", DataType.TEXT, Constraint.NOT_NULL);
    Column<String> Token_Anterior = new Column<>(DataType.TEXT);
    Column<Boolean> Estado = new Column<>(true, DataType.BOOLEAN);

    public UsuarioModel() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
        this.setTableName("UsuarioTableTest");
    }
}

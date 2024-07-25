package UtilidadesTest;

import io.github.josecarlosbran.JBSqlUtils.Anotations.ColumnDefined;
import io.github.josecarlosbran.JBSqlUtils.Anotations.Index;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel extends JBSqlUtils {
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

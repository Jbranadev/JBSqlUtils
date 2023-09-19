package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;

import java.util.List;
import java.util.Objects;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.getColumn;
import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que proporciona la logica para agregar una Apertura de Parentecis a una consulta SQL
 *
 * @author Jose Bran
 */
public class openParentecis<T> extends MethodsOpenParentecis {

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql          Sentencia SQL a la que se agregara la apertura de parentecis
     * @param modelo       Modelo que invocara los métodos de esta clase
     * @param parametros   Lista de parametros a ser agregados a la sentencia SQL
     * @param operatorPrev Operador a colocar antes de la apertura de parentecis
     * @param columna      Columna a evaluar dentro de la sentencia AND
     * @param operador     Operador con el cual se evaluara la columna
     * @param valor        Valor contra el que se evaluara la columna
     * @throws ValorUndefined        Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si las propiedades de conexión a BD's no estan definidas
     * @throws DataBaseUndefind      Lanza esta exepción si no a sido definida la BD's en la cual se creara la tabla
     */
    protected openParentecis(String sql, T modelo, List<Column> parametros, Operator operatorPrev, String columna, Operator operador, Object valor) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }


        this.parametros = parametros;
        this.modelo = modelo;
        this.parametros.add(getColumn(valor));
        this.sql = sql + operatorPrev.getOperador()
                + Operator.OPEN_PARENTESIS.getOperador()
                + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql               Sentencia SQL a la que se agregara la apertura de parentecis
     * @param modelo            Modelo que invocara los métodos de esta clase
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @param operatorPrev      Operador a colocar antes de la apertura de parentecis
     * @param columna           Columna a evaluar dentro de la sentencia AND
     * @param operador          Operador con el cual se evaluara la columna
     * @param valor             Valor contra el que se evaluara la columna
     * @throws ValorUndefined        Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si las propiedades de conexión a BD's no estan definidas
     * @throws DataBaseUndefind      Lanza esta exepción si no a sido definida la BD's en la cual se creara la tabla
     */
    protected openParentecis(String sql, T modelo, List<Column> parametros, Operator operatorPrev, String columna, Operator operador, Object valor, Boolean getPropertySystem) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super(getPropertySystem);
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }


        this.parametros = parametros;
        this.modelo = modelo;
        this.parametros.add(getColumn(valor));
        this.sql = sql + operatorPrev.getOperador()
                + Operator.OPEN_PARENTESIS.getOperador()
                + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql          Sentencia SQL a la que se agregara la apertura de parentecis
     * @param parametros   Lista de parametros a ser agregados a la sentencia SQL
     * @param operatorPrev Operador a colocar antes de la apertura de parentecis
     * @param columna      Columna a evaluar dentro de la sentencia AND
     * @param operador     Operador con el cual se evaluara la columna
     * @param valor        Valor contra el que se evaluara la columna
     * @throws ValorUndefined        Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si las propiedades de conexión a BD's no estan definidas
     * @throws DataBaseUndefind      Lanza esta exepción si no a sido definida la BD's en la cual se creara la tabla
     */
    protected openParentecis(String sql, List<Column> parametros, Operator operatorPrev, String columna, Operator operador, Object valor) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }

        this.parametros = parametros;
        this.parametros.add(getColumn(valor));
        this.sql = sql + operatorPrev.getOperador()
                + Operator.OPEN_PARENTESIS.getOperador()
                + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la que se agregara la apertura de parentecis
     * @param modelo     Modelo que invocara los métodos de esta clase
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param columna    Columna a evaluar dentro de la sentencia AND
     * @param operador   Operador con el cual se evaluara la columna
     * @param valor      Valor contra el que se evaluara la columna
     * @throws ValorUndefined        Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si las propiedades de conexión a BD's no estan definidas
     * @throws DataBaseUndefind      Lanza esta exepción si no a sido definida la BD's en la cual se creara la tabla
     */
    protected openParentecis(String sql, T modelo, List<Column> parametros, String columna, Operator operador, Object valor) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }


        this.parametros = parametros;
        this.modelo = modelo;
        this.parametros.add(getColumn(valor));
        this.sql = sql
                + Operator.OPEN_PARENTESIS.getOperador()
                + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql               Sentencia SQL a la que se agregara la apertura de parentecis
     * @param modelo            Modelo que invocara los métodos de esta clase
     * @param parametros        Lista de parametros a ser agregados a la sentencia SQL
     * @param columna           Columna a evaluar dentro de la sentencia AND
     * @param operador          Operador con el cual se evaluara la columna
     * @param valor             Valor contra el que se evaluara la columna
     * @param getPropertySystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     * @throws ValorUndefined        Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si las propiedades de conexión a BD's no estan definidas
     * @throws DataBaseUndefind      Lanza esta exepción si no a sido definida la BD's en la cual se creara la tabla
     */
    protected openParentecis(String sql, T modelo, List<Column> parametros, String columna, Operator operador, Object valor, Boolean getPropertySystem) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super(getPropertySystem);
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }


        this.parametros = parametros;
        this.modelo = modelo;
        this.parametros.add(getColumn(valor));
        this.sql = sql
                + Operator.OPEN_PARENTESIS.getOperador()
                + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }

    /**
     * Constructor que recibe como parametro:
     *
     * @param sql        Sentencia SQL a la que se agregara la apertura de parentecis
     * @param parametros Lista de parametros a ser agregados a la sentencia SQL
     * @param columna    Columna a evaluar dentro de la sentencia AND
     * @param operador   Operador con el cual se evaluara la columna
     * @param valor      Valor contra el que se evaluara la columna
     * @throws ValorUndefined        Lanza esta excepción si el parametro proporcionado está vacío o es NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si las propiedades de conexión a BD's no estan definidas
     * @throws DataBaseUndefind      Lanza esta exepción si no a sido definida la BD's en la cual se creara la tabla
     */
    protected openParentecis(String sql, List<Column> parametros, String columna, Operator operador, Object valor) throws ValorUndefined, DataBaseUndefind, PropertiesDBUndefined {
        super();
        if (stringIsNullOrEmpty(columna)) {
            throw new ValorUndefined("El nombre de la columna proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(valor)) {
            throw new ValorUndefined("El valor proporcionado esta vacío o es NULL");
        }
        if (Objects.isNull(operador)) {
            throw new ValorUndefined("El operador proporcionado es NULL");
        }
        this.parametros = parametros;
        this.parametros.add(getColumn(valor));
        this.sql = sql
                + Operator.OPEN_PARENTESIS.getOperador()
                + Operator.OPEN_PARENTESIS.getOperador() + columna + operador.getOperador() + "?" + Operator.CLOSE_PARENTESIS.getOperador();
    }
}

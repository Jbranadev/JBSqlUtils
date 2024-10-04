package io.github.josecarlosbran.JBSqlUtils.DataBase;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ModelNotFound;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author Jose Bran
 * Clase que brinda acceso a la logica de los metodos comunes que comparte un OrderBy Object
 */
public class MethodsOrderBy<T> extends MethodsTake {
    /**
     * Constructor por default de la clase MethodsWhere, que inicializa la clase
     */
    protected MethodsOrderBy() {
        super();
    }

    /**
     * Constructor por default de la clase MethodsWhere, que inicializa la clase
     *
     * @param getGetPropertiesSystem Indica si el modelo obtendra las propiedades de conexión de las propiedades del sistema
     */
    protected MethodsOrderBy(Boolean getGetPropertiesSystem) {
        super(getGetPropertiesSystem);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Retorna un objeto del tipo Take que permite agregar esta sentencia a la Logica de la sentencia
     * SQL a ejecutar.
     *
     * @param limite Entero que representa la cantidad maxima de valores recuperados.
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public Take take(int limite) throws ValorUndefined {
        if (Objects.isNull(this.modelo)) {
            return new Take(this.sql, limite, this.parametros);
        } else {
            if (!this.getGetPropertySystem()) {
                Take take = new Take(this.sql, limite, this.modelo, this.parametros, false);
                return take;
            }
            return new Take(this.sql, limite, this.modelo, this.parametros);
        }
    }

    /**
     * Retorna un objeto del tipo GroupBy que permite agregar la logica de agrupación a la sentencia
     * SQL a ejecutar.
     *
     * @param columnas Lista de columnas a ser agrupadas.
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public GroupBy groupBy(String... columnas) throws ValorUndefined {
        if (Objects.isNull(this.modelo)) {
            return new GroupBy(this.sql, this.parametros, columnas);
        } else {
            if (!this.getGetPropertySystem()) {
                GroupBy groupBy = new GroupBy(this.sql, this.modelo, this.parametros, false, columnas);
                return groupBy;
            }
            return new GroupBy(this.sql, this.modelo, this.parametros, columnas);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Retorna un objeto del tipo AND que permite agregar esta expresión a la sentencia SQL
     *
     * @param columna  Columna a evaluar dentro de la sentencia AND
     * @param operador Operador con el cual se evaluara la columna
     * @param valor    Valor contra el que se evaluara la columna
     * @return objeto del tipo AND que permite agregar esta expresión a la sentencia SQL
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public And and(String columna, Operator operador, Object valor) throws ValorUndefined {
        return new CommonsMethods().and(columna, operador, valor, this.sql, (JBSqlUtils) this.modelo, this.parametros, this.getGetPropertySystem());
    }

    /**
     * Retorna un objeto del tipo OR que permite agregar esta expresión a la sentencia SQL
     *
     * @param columna  Columna a evaluar dentro de la sentencia OR
     * @param operador Operador con el cual se evaluara la columna
     * @param valor    Valor contra el que se evaluara la columna
     * @return objeto del tipo OR que permite agregar esta expresión a la sentencia SQL
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public Or or(String columna, Operator operador, Object valor) throws ValorUndefined {
        return new CommonsMethods().or(columna, operador, valor, this.sql, (JBSqlUtils) this.modelo, this.parametros, this.getGetPropertySystem());
    }

    /**
     * @param operatorPrev Operador a colocar antes de la apertura de parentecis
     * @param columna      Columna a evaluar dentro de la sentencia AND
     * @param operador     Operador con el cual se evaluara la columna
     * @param valor        Valor contra el que se evaluara la columna
     * @return Retorna un objeto OpenParentecis el cual proporciona acceso a los métodos necesarios
     * para filtrar de una mejor manera nuestra consulta, No olvide llamar al metodo close parentecis cuando
     * haya finalizado la logica dentro de sus parentecis
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public openParentecis openParentecis(Operator operatorPrev, String columna, Operator operador, Object valor) throws ValorUndefined {
        return new CommonsMethods().openParentecis(operatorPrev, columna, operador, valor, this.sql, (JBSqlUtils) this.modelo, this.parametros, this.getGetPropertySystem());
    }

    /**
     * Agrega la posibilidad de realizar un cierre de parentecis dentro de la logica de nuestra sentencia SQL
     *
     * @param operatorPost Operador a colocar despues del cierre de parentecis
     * @return Retorna un objeto closeParentecis, el cual da acceso al resto de métodos que podemos llamar.
     * @throws ValorUndefined Lanza esta Excepción si la sentencia sql proporcionada esta vacía o es Null
     */
    public closeParentecis closeParentecis(Operator operatorPost) throws ValorUndefined {
        return new CommonsMethods().closeParentecis(operatorPost, this.sql, (JBSqlUtils) this.modelo, this.parametros, this.getGetPropertySystem());
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Llena el modelo que invoca este método con la información que obtiene de BD's
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> void get() throws Exception {
        super.get((T) this.modelo, this.sql, this.parametros);
    }

    /**
     * Carla: Creamos metodo con uso del CompleteableFeature
     * Llena el modelo CompletableFeature que invoca este metodo con la información que obtiene de BD's
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el metodo
     * @return
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> CompletableFuture<T> getCompletableFeature() throws Exception {
        return super.getCompletableFuture((T) this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene un modelo del tipo que invoca este método con la información que obtiene de BD's
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return Retorna un un modelo del tipo que invoca este método con la información que obtiene de BD's.
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> T first() throws Exception {
        return (T) super.first((T) this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene un CompleteableFeature que representa el modelo del tipo que invoca este método con la información que obtiene de BD's
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return Retorna un CompleteableFeature que representa el modelo del tipo que invoca este método con la información que obtiene de BD's.
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> CompletableFuture<T> firstCompleteableFeature() throws Exception {
        return super.firstCompleteableFeature((T) this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene un modelo del tipo que invoca este método con la información que obtiene de BD's
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return Retorna un un modelo del tipo que invoca este método con la información que obtiene de BD's.
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    public <T extends JBSqlUtils> T firstOrFail() throws Exception {
        return (T) super.firstOrFail((T) this.modelo, this.sql, this.parametros);
    }

    /** Carla: Versiòn original
     * Llena el modelo que invoca este metodo con la información que obtiene de BD's
     *
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    public <T extends JBSqlUtils> void firstOrFailGet() throws Exception {
        super.firstOrFailGet((T) this.modelo, this.sql, this.parametros);
    }

    /** Carla: Versiòn com aplicacion del Complete Feature
     * Llena el modelo que invoca este metodo con la información que obtiene de BD's
     *
     * @throws ModelNotFound Lanza esta excepción si no logra encontrar el registro correspondiente a la consulta
     *                       SQL realizada.
     */
    public <T extends JBSqlUtils> CompletableFuture<T>  firstOrFailGetCompletableFeature() throws Exception {
        return super.firstOrFailGetCompleteableFeature((T) this.modelo, this.sql, this.parametros);
    }

    /**
     * Obtiene una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     *
     * @param <T> Definición del procedimiento que indica que cualquier clase podra invocar el método.
     * @return Retorna una lista de modelos que coinciden con la busqueda realizada por medio de la consulta SQL
     * proporcionada
     * @throws Exception Si sucede una excepción en la ejecución asyncrona de la sentencia en BD's
     *                   captura la excepción y la lanza en el hilo principal
     */
    public <T extends JBSqlUtils> List<T> getAll() throws Exception {
        return (List<T>) super.getAll((T) this.modelo, this.sql, this.parametros);
    }
}


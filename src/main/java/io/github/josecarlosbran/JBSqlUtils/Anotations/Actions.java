package io.github.josecarlosbran.JBSqlUtils.Anotations;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Action;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operacion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Actions {
    Operacion operacion() default Operacion.DELETE;

    Action action() default Action.NO_ACTION;
}

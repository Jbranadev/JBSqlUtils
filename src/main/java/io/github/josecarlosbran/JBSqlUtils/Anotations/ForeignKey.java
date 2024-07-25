package io.github.josecarlosbran.JBSqlUtils.Anotations;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Action;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operacion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {

    String columName() ;

    String tableReference();
    String columnReference();

    Actions[] actions() default { @Actions(operacion = Operacion.DELETE, action = Action.NO_ACTION),
            @Actions(operacion = Operacion.UPDATE, action = Action.CASCADE)};


}

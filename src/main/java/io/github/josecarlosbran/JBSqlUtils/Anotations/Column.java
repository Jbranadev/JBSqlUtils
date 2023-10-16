package io.github.josecarlosbran.JBSqlUtils.Anotations;

import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import lombok.ToString;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    String name() default "";

    String default_value() default "";

    DataType dataTypeSQL();

    Constraint[] restriccion();

    Boolean columnExist = false;

    String size() default "";


}

package io.github.josecarlosbran.JBSqlUtils.Interfaces;

import com.josebran.LogsJB.LogsJB;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface IsResource {
    /**
     * método que permite validar si los campos envíados como parametros, estan llenos
     *
     * @param Campos Se definen los campos que se desea validar que tengan contenido
     * @return True si todos los campos que se envían como parametro, tienen información
     * False, si alguno de los campos indicados como parametro, vienen vacíos.
     */
    default Boolean isFull(String... Campos) {
        int campos_con_informacion = 0;
        try {
            List<String> campos = new ArrayList<>(Arrays.asList(Campos));
            for (String campo : campos) {
                List<Field> variables = new ArrayList<>(Arrays.asList(this.getClass().getDeclaredFields()));
                for (Field field : variables) {
                    String controllerName = field.getName();
                    LogsJB.trace("Validara si el contenido es Null: " + controllerName);
                    Object contenido = FieldUtils.readField(field, this, true);
                    LogsJB.debug("Nombre del campo del objeto: " + controllerName + " a validar el contenido del campo: " + contenido);
                    //Si el contenido es null, continua, no tiene caso hacer el resto
                    if (Objects.isNull(contenido) && controllerName.equalsIgnoreCase(campo)) {
                        LogsJB.debug("El campo " + controllerName + " no tiene contenido");
                        return false;
                    } else {
                        campos_con_informacion++;
                    }
                }
            }
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al verificar que los valores esten llenos, " + "Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
        LogsJB.debug("Cantidad de campos que si tienen información: " + campos_con_informacion);
        return true;
    }
}

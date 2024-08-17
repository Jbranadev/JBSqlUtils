package io.github.josecarlosbran.JBSqlUtils.Adaptadores;

import com.josebran.LogsJB.LogsJB;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.config.PropertyVisibilityStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Clase que implementa la interfaz PropertyVisibilityStrategy para definir la visibilidad de los campos y métodos
 * de los modelos que heredan de JBSQLUtils.
 * Puede utilizarla de la siguiente forma
 * <p>
 * JsonbVisibility(CustomVisibilityStrategy.class)
 * public class MiModelo extends JBSqlUtils{
 */
public class VisibilitySerializableModel implements PropertyVisibilityStrategy {
    public boolean isVisible(Field field) {
        boolean declarinClass = false;
        boolean anotacionPresent = false;
        try {
            declarinClass = field.getDeclaringClass().getPackage().hashCode() == this.getClass().getPackage().hashCode();
            anotacionPresent = !field.isAnnotationPresent(JsonbTransient.class);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al mapear el Json con el Obtjeto en cuestion " + field.toString() + ": " + ExceptionUtils.getStackTrace(e));
        } finally {
            return declarinClass &&
                    anotacionPresent;
        }
    }

    public boolean isVisible(Method method) {
        boolean declarinClass = false;
        boolean anotacionPresent = false;
        boolean handleAmbiguity = false;
        try {
            declarinClass = method.getDeclaringClass().getPackage().hashCode() == this.getClass().getPackage().hashCode();
            anotacionPresent = !method.isAnnotationPresent(JsonbTransient.class);
            handleAmbiguity = handleAmbiguity(method);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al mapear el Json con el Obtjeto en cuestion " + method.toString() + ": " + ExceptionUtils.getStackTrace(e));
        } finally {
            return declarinClass &&
                    anotacionPresent && handleAmbiguity;
        }
    }

    private boolean handleAmbiguity(Method member) {
        // Lógica para manejar la ambigüedad
        // Puedes lanzar una excepción, loggear un mensaje, o tomar alguna otra acción
        // según tus necesidades.
        if (isAmbiguous(member)) {
            // Manejo de la ambigüedad
            LogsJB.trace("Ambiguous field/method: " + member.getName());
            return false;
        }
        return true;
    }

    private boolean isAmbiguous(Method member) {
        // Lógica para determinar si hay ambigüedad
        // Por ejemplo, puedes verificar si hay un campo y un método con el mismo nombre
        // Puedes adaptar esta lógica según tus necesidades específicas.
        return Arrays.stream(member.getDeclaringClass().getDeclaredFields())
                .anyMatch(field -> {
                    boolean getter = StringUtils.equalsIgnoreCase(field.getName(), StringUtils.removeStartIgnoreCase(member.getName(), "get"));
                    boolean setter = StringUtils.equalsIgnoreCase(field.getName(), StringUtils.removeStartIgnoreCase(member.getName(), "set"));
                    return getter || setter;
                });
    }
}

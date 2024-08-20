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

    /**
     * El metodo isVisible esta creado para determinar si es visible o no,
     * este mismo se vasa en 3 criterios:
     * 1 - si esta en el mismo paquete que la clase que contiene este metodo.
     * 2 - si no tiene la anotacion JsonbTransient.
     * 3 - si se satisface algun criterio de ambiguedad.
     *
     * @param method
     * @return
     */
    public boolean isVisible(Method method) {
        boolean declarinClass = false;
        boolean anotacionPresent = false;
        boolean handleAmbiguity = false;
        try {
            //En esta linea se realiza la verificacion si el metodo declarado pertenece al mismo paquete
            //que la clase actual (this).
            //y se utiliza hashCode para realizar la comparacion de ambas clases.
            declarinClass = method.getDeclaringClass().getPackage().hashCode() == this.getClass().getPackage().hashCode();
            //En esta linea se realiza el chequeo de las anotaciones.
            //si el metodo tiene alguna anotacion, devolvera True, y sera visible.
            anotacionPresent = !method.isAnnotationPresent(JsonbTransient.class);
            //en esta linea se lleva el manejo de ambiguedad
            handleAmbiguity = handleAmbiguity(method);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada al mapear el Json con el Obtjeto en cuestion " + method.toString() + ": " + ExceptionUtils.getStackTrace(e));
        } finally {
            return declarinClass &&
                    anotacionPresent && handleAmbiguity;
        }
    }

    /**
     * Este es un metodo privado donde el proposito principal es controlar la ambiguedad,
     * dentro de las ambiguedades se puede controlar si hay multiples sobrecargas en el
     * mismo metodo.
     * @param member
     * @return
     */
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

    /**
     *En este metodo se puede verificar los getter o setter si estan en conflicto con un campo en su clase,
     * tambien para verificar si algun campo tiene el mismo nombre que el que esperaria para
     * un metodo dado.
     * @param member
     * @return
     */
    private boolean isAmbiguous(Method member) {
        // Lógica para determinar si hay ambigüedad
        // Por ejemplo, puedes verificar si hay un campo y un método con el mismo nombre
        // Puedes adaptar esta lógica según tus necesidades específicas.
        return Arrays.stream(member.getDeclaringClass().getDeclaredFields())
                //este metodo realiza una busqueda y verifica si existe por lo menos un campo
                //que satisfaga la condicion especificada.
                .anyMatch(field -> {
                    boolean getter = StringUtils.equalsIgnoreCase(field.getName(), StringUtils.removeStartIgnoreCase(member.getName(), "get"));
                    boolean setter = StringUtils.equalsIgnoreCase(field.getName(), StringUtils.removeStartIgnoreCase(member.getName(), "set"));
                    return getter || setter;
                });
    }
}

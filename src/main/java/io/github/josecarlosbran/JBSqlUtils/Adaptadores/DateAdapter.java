package io.github.josecarlosbran.JBSqlUtils.Adaptadores;

import com.josebran.LogsJB.LogsJB;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Clase que sirve para adaptar objetos tipo Date a un formato de tipo String.
 * se establece un patron donde se define el año, mes, dia, etc.
 * se realizo de esta manera para para establecer su propia configuaracion en cada hilo y asi evitar los errores comunes.
 */
public class DateAdapter implements JsonbAdapter<Date, String> {
    private static final ThreadLocal<SimpleDateFormat> threadLocalDateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));

    /**
     * En este fragmento de codigo los usuarios de la clase DateAdapter pueden estrablecer dinamicamente,
     * el formato en que deseen manejar la fecha, sin afectar a los demas hilos.
     * se esta seteando o estableciendo el threadLocalDateFormat al new SimpleDateFormat(pattern)
     *
     * @param pattern Es el patron que se desea establecer.
     */
    public static void setDateFormatPattern(String pattern) {
        threadLocalDateFormat.set(new SimpleDateFormat(pattern));
    }

    /**
     * Metodo publico adaptToJson que acepta un parametro de tipo Date.
     * este metodo devuelve o retorna un String.
     * y puede lanzar una excepcion en caso que algo salga mal.
     *
     * @param date Es el objeto de tipo Date que se desea convertir a un String.
     * @return Retorna un String que representa la fecha recibida como parametro
     */
    @Override
    public String adaptToJson(Date date) {
        //aqui se obtiene el dato de Fecha del hilo actual.
        SimpleDateFormat dateFormat = threadLocalDateFormat.get();
        return dateFormat.format(date);
    }

    /**
     * Este metodo es el que convierte las cadenas en objetos tipo Date.
     *
     * @param dateString Es la cadena que se desea convertir a un objeto tipo Date.
     * @return Retorna un objeto tipo Date con la fecha que se recibio como parametro.
     */
    @Override
    public Date adaptFromJson(String dateString) {
        try {
            //aqui se obtiene el dato de Fecha del hilo actual.
            SimpleDateFormat dateFormat = threadLocalDateFormat.get();
            //en esta linea se parsea la cadena de fecha, y se convierte en un objeto tipo Date
            return new Date(dateFormat.parse(dateString).getTime());
        } catch (Exception e) {
            LogsJB.fatal("Error capturado al convertir la fecha: " + dateString);
            LogsJB.fatal("Stacktrace de la excepción: " + ExceptionUtils.getStackTrace(e));
            return null;
        }
    }
}

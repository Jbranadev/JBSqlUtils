package io.github.josecarlosbran.JBSqlUtils.Adaptadores;

import jakarta.json.bind.adapter.JsonbAdapter;

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
     *En este fragmento de codigo los usuarios de la clase DateAdapter pueden estrablecer dinamicamente,
     * el formato en que deseen manejar la fecha, sin afectar a los demas hilos.
     * se esta seteando o estableciendo el threadLocalDateFormat al new SimpleDateFormat(pattern)
     *
     * @param pattern
     */
    public static void setDateFormatPattern(String pattern) {
        threadLocalDateFormat.set(new SimpleDateFormat(pattern));
    }

    /**
     *Metodo publico adaptToJson que acepta un parametro de tipo Date.
     * este metodo devuelve o retorna un String.
     * y puede lanzar una excepcion en caso que algo salga mal.
     * @param date
     * @return
     * @throws Exception
     */

    @Override
    public String adaptToJson(Date date) throws Exception {
        //aqui se obtiene el dato de Fecha del hilo actual.
        SimpleDateFormat dateFormat = threadLocalDateFormat.get();
        return dateFormat.format(date);
    }

    /**
     * Este metodo es el que convierte las cadenas en objetos tipo Date.
     * @param dateString
     * @return
     * @throws Exception
     */
    @Override
    public Date adaptFromJson(String dateString) throws Exception {
        //aqui se obtiene el dato de Fecha del hilo actual.
        SimpleDateFormat dateFormat = threadLocalDateFormat.get();
        //en esta linea se parsea la cadena de fecha, y se convierte en un objeto tipo Date
        return new Date(dateFormat.parse(dateString).getTime());
    }
}

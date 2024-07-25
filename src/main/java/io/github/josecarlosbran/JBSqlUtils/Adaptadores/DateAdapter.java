package io.github.josecarlosbran.JBSqlUtils.Adaptadores;

import jakarta.json.bind.adapter.JsonbAdapter;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateAdapter implements JsonbAdapter<Date, String> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public String adaptToJson(Date date) throws Exception {
        // Aquí puedes elegir el formato de la fecha que deseas en la cadena JSON
        return dateFormat.format(date);
    }

    @Override
    public Date adaptFromJson(String dateString) throws Exception {
        // Aquí puedes convertir la cadena JSON de vuelta a java.sql.Date
        return new Date(dateFormat.parse(dateString).getTime());
    }
}

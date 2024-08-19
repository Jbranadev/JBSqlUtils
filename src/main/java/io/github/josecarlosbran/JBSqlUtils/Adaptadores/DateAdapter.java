package io.github.josecarlosbran.JBSqlUtils.Adaptadores;

import jakarta.json.bind.adapter.JsonbAdapter;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateAdapter implements JsonbAdapter<Date, String> {
    private static final ThreadLocal<SimpleDateFormat> threadLocalDateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));

    public static void setDateFormatPattern(String pattern) {
        threadLocalDateFormat.set(new SimpleDateFormat(pattern));
    }

    @Override
    public String adaptToJson(Date date) throws Exception {
        SimpleDateFormat dateFormat = threadLocalDateFormat.get();
        return dateFormat.format(date);
    }

    /**
     * vsvsvcsv
     * @param dateString
     * @return
     * @throws Exception
     */
    @Override
    public Date adaptFromJson(String dateString) throws Exception {
        SimpleDateFormat dateFormat = threadLocalDateFormat.get();
        return new Date(dateFormat.parse(dateString).getTime());
    }
}

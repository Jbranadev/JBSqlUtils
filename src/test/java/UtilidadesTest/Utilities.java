package UtilidadesTest;

import com.josebran.LogsJB.LogsJB;
import org.testng.Reporter;
import org.testng.annotations.Ignore;

@Ignore
public class Utilities {

    public static void logParrafo(String log) {
        LogsJB.info(log);
        String s = "<p><font size=\"2\">" + log + "</font></p><br>";
        Reporter.log(s);
    }

}

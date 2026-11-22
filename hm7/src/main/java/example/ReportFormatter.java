package example;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class ReportFormatter {

    public static String formatWindow(MaintenanceWindow window, ZoneId zone, Locale locale) {
        if (window == null) {
            throw new NullPointerException("Window must be not null");
        }
        if (zone == null) {
            throw new NullPointerException("Zone must be not null");
        }
        if (locale == null) {
            throw new NullPointerException("Locale must be not null");
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm XXX (VV)", locale).withZone(zone);
        return format.format(window.start()) + " – " + format.format(window.end());
    }
}

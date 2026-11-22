package example;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

public final class LegacyDateAdapter {
    private LegacyDateAdapter() {
    }

    public static Instant toInstant(Date date) {
        if (date == null) {
            throw new NullPointerException("date");
        }
        return Instant.ofEpochMilli(date.getTime());
    }

    public static Date toDate(Instant instant) {
        if (instant == null) {
            throw new NullPointerException("instant");
        }
        return Date.from(instant);
    }

    public static ZonedDateTime toZonedDateTime(GregorianCalendar calendar) {
        if (calendar == null) {
            throw new NullPointerException("calendar");
        }
        return calendar.toZonedDateTime();
    }

    public static GregorianCalendar toGregorianCalendar(ZonedDateTime dateTime) {
        if (dateTime == null) {
            throw new NullPointerException("dateTime");
        }
        return GregorianCalendar.from(dateTime);
    }
}

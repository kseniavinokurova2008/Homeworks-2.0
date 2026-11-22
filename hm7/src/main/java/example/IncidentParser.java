package example;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public final class IncidentParser {
    public static Incident parse(String line) {
        if (line == null) {
            throw new NullPointerException("Line must not be null");
        }
        String[] fields = line.split(";", -1);
        if (fields.length != 4) {
            throw new IllegalArgumentException("Length is not valid");
        }
        return new Incident(fields[0], fields[1], parseInstant(fields[2]), parseInstant(fields[3]));
    }

    private static Instant parseInstant(String value) {
        try {
            return Instant.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Value must be Instant");
        }
    }
}

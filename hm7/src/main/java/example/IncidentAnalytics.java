package example;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public final class IncidentAnalytics {
    private static final BigInteger NANO = BigInteger.valueOf(1_000_000_000);
    private static final Comparator<Incident> comparator = new IncidentComparator();

    public static Duration totalDowntime(List<Incident> incidents) {
        if (incidents == null) {
            throw new NullPointerException("Incidents must not be null");
        }
        Duration duration = Duration.ZERO;
        for (Incident incident : incidents) {
            if (incident == null) {
                throw new NullPointerException("Incident must not be null");
            }
            duration = duration.plus(incident.duration());
        }
        return duration;
    }

    public static Duration averageDowntime(List<Incident> incidents) {
        if (incidents == null) {
            throw new NullPointerException("incidents");
        }
        if (incidents.isEmpty()) {
            return Duration.ZERO;
        }
        BigInteger totalNanos = BigInteger.ZERO;
        for (Incident incident : incidents) {
            if (incident == null) {
                throw new NullPointerException("Incident must not be null");
            }
            Duration duration = incident.duration();
            BigInteger nanos = BigInteger.valueOf(duration.getSeconds()).multiply(NANO).add(BigInteger.valueOf(duration.getNano()));
            totalNanos = totalNanos.add(nanos);
        }
        BigInteger averageNanos = totalNanos.divide(BigInteger.valueOf(incidents.size()));
        long seconds = averageNanos.divide(NANO).longValueExact();
        long nanos = averageNanos.remainder(NANO).longValueExact();
        return Duration.ofSeconds(seconds, nanos);
    }

    public static Optional<Incident> longestIncident(List<Incident> incidents) {
        if (incidents == null) {
            throw new NullPointerException("Incidents must not be null");
        }
        Incident longest = null;
        for (Incident incident : incidents) {
            if (incident == null) {
                throw new NullPointerException("Incident must not be null");
            }
            if (longest == null) {
                longest = incident;
                continue;
            }
            int durationComparison = incident.duration().compareTo(longest.duration());
            if (durationComparison > 0) {
                longest = incident;
            } else if (durationComparison == 0 && comparator.compare(incident, longest) < 0) {
                longest = incident;
            }
        }
        if (longest == null) {
            return Optional.empty();
        }
        return Optional.of(longest);
    }

    public static Map<LocalDate, List<Incident>> groupByStartDate(List<Incident> incidents, ZoneId zone) {
        if (zone == null) {
            throw new NullPointerException("Zone must not be null");
        }
        if (incidents == null) {
            throw new NullPointerException("Incidents must not be null");
        }
        Map<LocalDate, List<Incident>> groups = new TreeMap<>();
        for (Incident incident : incidents) {
            if (incident == null) {
                throw new NullPointerException("Incident must not be null");
            }
            LocalDate date = incident.startedAt().atZone(zone).toLocalDate();
            List<Incident> group = groups.get(date);
            if (group == null) {
                group = new ArrayList<>();
                groups.put(date, group);
            }
            group.add(incident);
        }
        for (List<Incident> group : groups.values()) {
            group.sort(comparator);
        }
        return groups;
    }
}

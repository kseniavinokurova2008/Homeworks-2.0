package example;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeApiTest {
    ZoneId BERLIN = ZoneId.of("Europe/Berlin");
    String INCIDENT = "INC-1;billing;2024-03-30T22:30:00Z;2024-03-31T01:00:00Z";
    MaintenanceScheduler SCHEDULER = new MaintenanceScheduler(Clock.fixed(Instant.parse("2024-03-29T23:00:00Z"), ZoneOffset.UTC));

    @Test
    void parseIncident() {
        Incident incident = IncidentParser.parse(INCIDENT);
        assertEquals(Duration.ofMinutes(150), incident.duration());
    }

    @Test
    void invalidIncident() {
        assertThrows(IllegalArgumentException.class, () -> IncidentParser.parse("INC-1;billing;invalid-date;2024-03-31T01:00:00Z"));
        assertThrows(IllegalArgumentException.class, () -> IncidentParser.parse(";billing;2024-03-30T22:30:00Z;2024-03-31T01:00:00Z"));
        assertThrows(IllegalArgumentException.class, () -> IncidentParser.parse("INC-1;billing;2024-03-30T22:30:00Z;2024-03-29T01:00:00Z"));
    }

    @Test
    void downtime() {
        Instant start = Instant.EPOCH;
        Incident first = new Incident("A", "billing", start, start.plusNanos(2));
        Incident second = new Incident("B", "billing", start, start.plusNanos(3));
        List<Incident> incidents = List.of(first, second);

        assertEquals(Duration.ofNanos(5), IncidentAnalytics.totalDowntime(incidents));
        assertEquals(Duration.ofNanos(2), IncidentAnalytics.averageDowntime(incidents));
        assertEquals(Optional.of(second), IncidentAnalytics.longestIncident(incidents));
    }

    @Test
    void emptyList() {
        assertEquals(Duration.ZERO, IncidentAnalytics.totalDowntime(List.of()));
        assertEquals(Duration.ZERO, IncidentAnalytics.averageDowntime(List.of()));
        assertEquals(Optional.empty(), IncidentAnalytics.longestIncident(List.of()));
    }

    @Test
    void groupByDate() {
        Incident first = IncidentParser.parse(INCIDENT);
        Incident second = IncidentParser.parse("INC-2;gateway;2024-03-30T23:30:00Z;2024-03-31T00:15:00Z");
        Map<LocalDate, List<Incident>> groups = IncidentAnalytics.groupByStartDate(List.of(second, first), BERLIN);
        LocalDate march30 = LocalDate.of(2024, 3, 30);
        LocalDate march31 = LocalDate.of(2024, 3, 31);

        assertEquals(List.of(march30, march31), List.copyOf(groups.keySet()));
        assertEquals(List.of(first), groups.get(march30));
        assertEquals(List.of(second), groups.get(march31));
    }

    @Test
    void springGap() {
        assertThrows(DateTimeException.class, () -> SCHEDULER.nextWindow(LocalDate.of(2024, 3, 31), LocalTime.of(2, 30), Duration.ofHours(1), BERLIN, OverlapPolicy.EARLIER));
    }

    @Test
    void autumnOverlap() {
        LocalTime start = LocalTime.of(2, 30);
        Duration duration = Duration.ofHours(1);
        LocalDate overlap = LocalDate.of(2024, 10, 27);
        MaintenanceWindow earlier = SCHEDULER.nextWindow(overlap, start, duration, BERLIN, OverlapPolicy.EARLIER);
        MaintenanceWindow later = SCHEDULER.nextWindow(overlap, start, duration, BERLIN, OverlapPolicy.LATER);
        assertEquals(ZoneOffset.ofHours(2), earlier.start().atZone(BERLIN).getOffset());
        assertEquals(ZoneOffset.ofHours(1), later.start().atZone(BERLIN).getOffset());
        assertEquals(duration, Duration.between(earlier.start(), later.start()));
    }

    @Test
    void dailyWindows() {
        LocalTime start = LocalTime.of(3, 30);
        Duration duration = Duration.ofMinutes(30);
        List<MaintenanceWindow> windows = SCHEDULER.upcomingWindows(2, start, duration, BERLIN, OverlapPolicy.EARLIER);

        assertEquals(2, windows.size());
        assertEquals(LocalDate.of(2024, 3, 30).atTime(start), windows.get(0).start().atZone(BERLIN).toLocalDateTime());
        assertEquals(LocalDate.of(2024, 3, 31).atTime(start), windows.get(1).start().atZone(BERLIN).toLocalDateTime());
        assertEquals(Duration.ofHours(23), Duration.between(windows.get(0).start(), windows.get(1).start()));
        assertEquals(windows, SCHEDULER.upcomingWindows(2, start, duration, BERLIN, OverlapPolicy.EARLIER));
    }

    @Test
    void formatWindow() {
        Instant start = Instant.parse("2024-03-31T01:00:00Z");
        MaintenanceWindow window = new MaintenanceWindow(start, start.plusSeconds(3600));
        assertEquals("31.03.2024 03:00 +02:00 (Europe/Berlin) – 31.03.2024 04:00 +02:00 (Europe/Berlin)", ReportFormatter.formatWindow(window, BERLIN, Locale.ROOT));
    }

    @Test
    void convertDate() {
        Instant precise = Instant.parse("2024-03-31T01:00:00.123Z");
        Date date = new Date(precise.toEpochMilli());
        assertEquals(precise, LegacyDateAdapter.toInstant(date));
        assertEquals(date, LegacyDateAdapter.toDate(LegacyDateAdapter.toInstant(date)));
    }
}

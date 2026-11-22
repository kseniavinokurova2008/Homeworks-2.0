package example;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public final class MaintenanceScheduler {
    private final Clock clock;

    public MaintenanceScheduler(Clock clock) {
        if (clock == null) {
            throw new NullPointerException("Сlock must be not null");
        }
        this.clock = clock;
    }

    public MaintenanceWindow nextWindow(LocalDate date, LocalTime localStart, Duration duration, ZoneId zone, OverlapPolicy policy) {
        if (date == null) {
            throw new NullPointerException("Date must be not null");
        }
        if (localStart == null) {
            throw new NullPointerException("LocalStart must not be null");
        }
        if (duration == null) {
            throw new NullPointerException("Duration must not be null");
        }
        if (zone == null) {
            throw new NullPointerException("Zone must not be null");
        }
        if (policy == null) {
            throw new NullPointerException("Policy must not be null");
        }
        if (duration.isZero() || duration.isNegative()) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        return createWindow(date, localStart, duration, zone, policy);
    }

    public List<MaintenanceWindow> upcomingWindows(int days, LocalTime localStart, Duration duration, ZoneId zone, OverlapPolicy policy) {
        if (days < 0) {
            throw new IllegalArgumentException("Days must not be negative");
        }
        if (localStart == null) {
            throw new NullPointerException("LocalStart must be not null");
        }
        if (duration == null) {
            throw new NullPointerException("Duration must be not null");
        }
        if (zone == null) {
            throw new NullPointerException("Zone must be not null");
        }
        if (policy == null) {
            throw new NullPointerException("Policy must be not null");
        }
        if (duration.isZero() || duration.isNegative()) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        if (days == 0) {
            return List.of();
        }
        LocalDate today = LocalDate.now(clock.withZone(zone));
        List<MaintenanceWindow> windows = new ArrayList<>();
        for (int day = 0; day < days; day++) {
            windows.add(createWindow(today.plusDays(day), localStart, duration, zone, policy));
        }
        return List.copyOf(windows);
    }

    private static MaintenanceWindow createWindow(LocalDate date, LocalTime localStart, Duration duration, ZoneId zone, OverlapPolicy policy) {
        LocalDateTime localDateTime = date.atTime(localStart);
        if (zone.getRules().getValidOffsets(localDateTime).isEmpty()) {
            throw new DateTimeException("Local time falling within a gap\n");
        }
        ZonedDateTime zonedStart = localDateTime.atZone(zone);
        Instant start;
        if (policy == OverlapPolicy.EARLIER) {
            start = zonedStart.withEarlierOffsetAtOverlap().toInstant();
        } else {
            start = zonedStart.withLaterOffsetAtOverlap().toInstant();
        }
        return new MaintenanceWindow(start, start.plus(duration));
    }
}

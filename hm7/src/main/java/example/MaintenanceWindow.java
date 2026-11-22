package example;

import java.time.Duration;
import java.time.Instant;

public record MaintenanceWindow(Instant start, Instant end) {
    public MaintenanceWindow {
        if (start == null) {
            throw new NullPointerException("Start must be not null");
        }
        if (end == null) {
            throw new NullPointerException("End must be not null");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End must be after start");
        }
    }

    public Duration duration() {
        return Duration.between(start, end);
    }
}

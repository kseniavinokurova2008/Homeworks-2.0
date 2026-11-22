package example;

import java.time.Duration;
import java.time.Instant;

public record Incident(String id, String service, Instant startedAt, Instant resolvedAt) {
    public Incident {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id must not be null or blank");
        }
        if (service == null || service.isBlank()) {
            throw new IllegalArgumentException("Service must not be null or blank");
        }
        if (startedAt == null) {
            throw new NullPointerException("StartedAt must not be null");
        }
        if (resolvedAt == null) {
            throw new NullPointerException("ResolvedAt must not be null");
        }
        if (resolvedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("ResolvedAt must not be before startedAt");
        }
    }

    public Duration duration() {
        return Duration.between(startedAt, resolvedAt);
    }
}

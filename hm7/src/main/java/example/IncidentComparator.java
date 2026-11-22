package example;

import java.util.Comparator;

final class IncidentComparator implements Comparator<Incident> {
    @Override
    public int compare(Incident first, Incident second) {
        int result = first.startedAt().compareTo(second.startedAt());
        if (result != 0) {
            return result;
        }
        return first.id().compareTo(second.id());
    }
}

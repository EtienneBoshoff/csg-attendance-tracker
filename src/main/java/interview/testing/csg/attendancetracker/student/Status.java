package interview.testing.csg.attendancetracker.student;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple Enum of status values for attendance sheet
 *
 * @author Etienne Boshoff
 */
public enum Status {

    PRESENT("Present for Class"),
    ABSENT("Absent for Class");

    private String label;

    Status(String label) {
        this.label = label;
    }

    private static final Map<String, Status> BY_LABEL = new HashMap<>();

    static {
        for (Status status: values()) {
            BY_LABEL.put(status.label, status);
        }
    }

    public static Status valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}

package interview.testing.csg.attendancetracker.student;

import interview.testing.csg.attendancetracker.attendance.Attendance;
import interview.testing.csg.attendancetracker.classrooms.ClassRoom;
import interview.testing.csg.attendancetracker.model.Person;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import java.util.*;

/**
 * A simple business object representing a student
 *
 * @author Etienne Boshoff
 */
@Entity
@Table(name = "student")
public class Student extends Person {

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassRoom classRoom;

    @Transient
    private Set<Attendance> attendances;

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    protected Set<Attendance> getAttendancesInternal() {
        if (this.attendances == null) {
            this.attendances = new HashSet<>();
        }
        return this.attendances;
    }

    public void setAttendancesInternal(Collection<Attendance> attendances) {
        this.attendances = new LinkedHashSet<>(attendances);
    }

    public List<Attendance> getAttendances() {
        List<Attendance> sortedAttendances = new ArrayList<>(getAttendancesInternal());
        PropertyComparator.sort(sortedAttendances, new MutableSortDefinition("dateTime", false, false));
        return Collections.unmodifiableList(sortedAttendances);
    }

    public void addAttendance(Attendance attendance) {
        getAttendancesInternal().add(attendance);
        attendance.setStudentId(this.getId());
    }
}

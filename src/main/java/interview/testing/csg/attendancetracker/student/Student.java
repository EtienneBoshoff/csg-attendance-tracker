package interview.testing.csg.attendancetracker.student;

import interview.testing.csg.attendancetracker.classroom.ClassRoom;
import interview.testing.csg.attendancetracker.model.Person;

import javax.persistence.*;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<Attendance> attendances;
}

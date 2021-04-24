package interview.testing.csg.attendancetracker.teacher;

import interview.testing.csg.attendancetracker.classroom.ClassRoom;
import interview.testing.csg.attendancetracker.model.Person;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * A simple business object representing a teacher
 *
 * @author Etienne Boshoff
 */
@Entity
@Table(name = "teacher")
public class Teacher extends Person {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<ClassRoom> classes;

    public Set<ClassRoom> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClassRoom> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "classes=" + classes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getClasses(), teacher.getClasses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getClasses());
    }
}

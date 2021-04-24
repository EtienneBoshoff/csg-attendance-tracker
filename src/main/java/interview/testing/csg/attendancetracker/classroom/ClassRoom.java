package interview.testing.csg.attendancetracker.classroom;

import interview.testing.csg.attendancetracker.model.BaseEntity;
import interview.testing.csg.attendancetracker.student.Student;
import interview.testing.csg.attendancetracker.teacher.Teacher;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

/**
 * A simple business object representing a class room
 *
 * @author Etienne Boshoff
 */
@Entity
@Table(name = "class_room")
public class ClassRoom extends BaseEntity {

    @Column
    @NotEmpty
    private String name;

    @Column
    @NotEmpty
    private String grade;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classRoom")
    private Set<Student> students;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassRoom)) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return getName().equals(classRoom.getName()) && getGrade().equals(classRoom.getGrade()) && getTeacher().equals(classRoom.getTeacher()) && Objects.equals(getStudents(), classRoom.getStudents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGrade(), getTeacher(), getStudents());
    }
}

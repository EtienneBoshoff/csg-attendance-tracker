package interview.testing.csg.attendancetracker.classrooms;

import interview.testing.csg.attendancetracker.model.BaseEntity;
import interview.testing.csg.attendancetracker.student.Student;
import interview.testing.csg.attendancetracker.teacher.Teacher;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

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

    protected Set<Student> getProtectedStudentsInternal() {
        if (this.students == null) {
            this.students = new HashSet<>();
        }
        return this.students;
    }

    public List<Student> getStudents() {
        List<Student> sortedList = new ArrayList<>(getProtectedStudentsInternal());
        PropertyComparator.sort(sortedList, new MutableSortDefinition("surname", true, true));
        return Collections.unmodifiableList(sortedList);
    }

    public void addStudent(Student student) {
        if (student.isNew()) {
            getProtectedStudentsInternal().add(student);
        }
        student.setClassRoom(this);
    }

    public Student getStudent(Student studentToCompare, boolean ignoreIfNew) {
        String nameToCompare = calculateFullName(studentToCompare);
        for (Student student : getProtectedStudentsInternal()) {
            if (BooleanUtils.isFalse(student.isNew()) || ignoreIfNew) {
                String fullName = calculateFullName(student);
                if (fullName.equals(nameToCompare)) {
                    return student;
                }
            }
        }

        return null;
    }

    private String calculateFullName(Student student) {

        StringBuilder fullNameBuilder = new StringBuilder(100);
        fullNameBuilder.append(student.getFirstName().toLowerCase().trim());
        fullNameBuilder.append(" ");
        fullNameBuilder.append(student.getSurname().toLowerCase().trim());

        return fullNameBuilder.toString();
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
}

package interview.testing.csg.attendancetracker.teacher;

import interview.testing.csg.attendancetracker.classrooms.ClassRoom;
import interview.testing.csg.attendancetracker.model.Person;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import java.util.*;

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

    public List<ClassRoom> getClasses() {
        List<ClassRoom> sortedClassRooms = new ArrayList<>(getClassRoomsInternal());
        PropertyComparator.sort(sortedClassRooms, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedClassRooms);
    }

    protected Set<ClassRoom> getClassRoomsInternal() {
        if (this.classes == null) {
            this.classes = new HashSet<>();
        }
        return this.classes;
    }

    protected void setClassRoomsInternal(Set<ClassRoom> classes) {
        this.classes = classes;
    }

    public void addClassRoom(ClassRoom classRoom) {
        if (classRoom.isNew()) {
            getClassRoomsInternal().add(classRoom);
        }
        classRoom.setTeacher(this);
    }

    public ClassRoom getClassRoom(String name, String grade, boolean ignoreIfNew) {

        for (ClassRoom classRoom : getClassRoomsInternal()) {
            if (BooleanUtils.isFalse(ignoreIfNew) || BooleanUtils.isFalse(classRoom.isNew())) {
                String nameToCompare = classRoom.getName().toLowerCase().trim();
                String gradeToCompare = classRoom.getGrade().toLowerCase().trim();
                if (nameToCompare.equals(name.toLowerCase().trim()) && gradeToCompare.equals(grade.toLowerCase().trim())) {
                    return classRoom;
                }
            }
        }
        return null;
    }
}

package interview.testing.csg.attendancetracker.student;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Student</code> domain objects
 *
 * @author Etienne Boshoff
 */
public interface StudentRepository extends Repository<Student, Integer> {

    @Transactional(readOnly = true)
    Student findById(Integer id);

    void save(Student student);
}

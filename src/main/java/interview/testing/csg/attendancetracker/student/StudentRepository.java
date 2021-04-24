package interview.testing.csg.attendancetracker.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface StudentRepository extends Repository<Student, Integer> {

    @Query("SELECT DISTINCT student FROM Student student inner join fetch student.attendances where student.surname LIKE :surname%")
    @Transactional(readOnly = true)
    Student findBySurname(String surname);

    @Transactional(readOnly = true)
    Student findById(Integer id);

    void save(Student student);
}

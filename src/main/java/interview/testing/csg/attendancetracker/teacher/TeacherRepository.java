package interview.testing.csg.attendancetracker.teacher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface TeacherRepository extends Repository<Teacher, Integer> {

    @Query("SELECT DISTINCT teacher FROM Teacher teacher left join fetch teacher.classes WHERE teacher.surname LIKE :surname%")
    @Transactional(readOnly = true)
    Collection<Teacher> findBySurname(@Param("surname") String surname);

    @Query("SELECT teacher FROM Teacher teacher left join fetch teacher.classes WHERE teacher.id =:id")
    @Transactional(readOnly = true)
    Teacher findById(@Param("id") Integer id);

    void save(Teacher teacher);

}

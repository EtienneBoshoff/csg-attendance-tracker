package interview.testing.csg.attendancetracker.classrooms;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClassRoomRepository extends Repository<ClassRoom, Integer> {

    @Query("SELECT classRoom FROM ClassRoom classRoom left join fetch classRoom.teacher WHERE classRoom.teacher.id =:id")
    @Transactional(readOnly = true)
    ClassRoom findById(@Param("id") Integer id);

    void save(ClassRoom classRoom);
}

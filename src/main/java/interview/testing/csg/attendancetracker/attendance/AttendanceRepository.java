package interview.testing.csg.attendancetracker.attendance;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends Repository<Attendance, Integer> {

    void save(Attendance attendance) throws DataAccessException;

    List<Attendance> findByStudentId(Integer studentId);

    List<Attendance> findByDateTimeBetween(LocalDateTime after, LocalDateTime before);
}

package interview.testing.csg.attendancetracker.student;

import interview.testing.csg.attendancetracker.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Simple business object representing attendance
 *
 * @author Etienne Boshoff
 */

@Entity
@Table(name = "attendance")
public class Attendance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "created_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    @Column
    private String status;

}

package interview.testing.csg.attendancetracker.teacher;

import interview.testing.csg.attendancetracker.attendance.Attendance;
import interview.testing.csg.attendancetracker.attendance.AttendanceRepository;
import interview.testing.csg.attendancetracker.student.Student;
import interview.testing.csg.attendancetracker.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class AttendanceController {

    private final AttendanceRepository attendances;

    private final StudentRepository students;

    @Autowired
    public AttendanceController(AttendanceRepository attendances, StudentRepository students) {
        this.attendances = attendances;
        this.students = students;
    }

    @InitBinder
    public void setAllowableFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("attendance")
    public Attendance loadStudentWithAttendance(@PathVariable("studentId") int studentId, Map<String, Object> model) {
        Student student = this.students.findById(studentId);
        student.setAttendancesInternal(this.attendances.findByStudentId(studentId));
        model.put("student", student);
        Attendance attendance = new Attendance();
        student.addAttendance(attendance);
        return attendance;
    }

    @GetMapping("/teacher/*/classroom/*/students/{studentId}/attendance/new")
    public String initNewAttendanceForm(@PathVariable("studentId") int studentId, Map<String, Object> model) {
        return "students/createOrUpdateAttendanceForm";
    }

    @PostMapping("teacher/{teacherId}/classroom/{classId}/students/{studentId}/attendance/new")
    public String processAttendanceForm(@Valid Attendance attendance, BindingResult result) {
        if (result.hasErrors()) {
            return "students/createOrUpdateAttendanceForm";
        } else {
            this.attendances.save(attendance);
            return "redirect:teacher/{teacherId}/classroom/{classId}";
        }
    }
}

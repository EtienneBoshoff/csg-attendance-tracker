package interview.testing.csg.attendancetracker.teacher;

import interview.testing.csg.attendancetracker.classrooms.ClassRoom;
import interview.testing.csg.attendancetracker.classrooms.ClassRoomRepository;
import interview.testing.csg.attendancetracker.student.Status;
import interview.testing.csg.attendancetracker.student.Student;
import interview.testing.csg.attendancetracker.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Etienne Boshoff
 */

@Controller
@RequestMapping("teacher/{teacherId}/classroom/{classId}")
public class StudentController {

    private static final String VIEWS_STUDENT_CREATE_OR_UPDATE_FORM = "students/createOrUpdateStudentForm";

    private final StudentRepository students;

    private final ClassRoomRepository classRooms;

    @Autowired
    public StudentController(StudentRepository students, ClassRoomRepository classRooms) {
        this.students = students;
        this.classRooms = classRooms;
    }

    @ModelAttribute("status")
    public Collection<Status> populateAttendanceStatuses() {
        return Arrays.asList(Status.values());
    }

    @ModelAttribute
    public ClassRoom findClassRoom(@PathVariable("classId") int classRoomId) {
        return classRooms.findById(classRoomId);
    }

    @InitBinder("classRoom")
    public void initClassRoomBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/students/new")
    public String initCreationForm(ClassRoom classRoom, ModelMap model) {
        Student student = new Student();
        classRoom.addStudent(student);
        model.put("student", student);
        return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/students/new")
    public String processStudentAddForm(ClassRoom classRoom, Student student, BindingResult result, ModelMap model) {
        if (hasNameAndSurname(student)
                && student.isNew()
                && classRoom.getStudent(student, true) != null
        ) {
            result.rejectValue("firstName", "duplicate", "already exists");
            result.rejectValue("surname", "duplicate", "already exists");
        }
        classRoom.addStudent(student);

        if (result.hasErrors()) {
            model.put("student", student);
            return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
        } else {
            this.students.save(student);
            return "redirect:teacher/{teacherId}/classroom/{classId}";
        }
    }

    @GetMapping("/students/{studentId}/edit")
    public String setUpUpdateForm(@PathVariable("studentId") int studentId, ModelMap model) {
        Student student = this.students.findById(studentId);
        model.put("student", student);
        return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/students/{studentId}/edit")
    public String processStudentRegisterForm(Student student, BindingResult result, ClassRoom classRoom, ModelMap model) {
        if (hasNameAndSurname(student)) {
            result.rejectValue("firstName", "duplicate", "already exists");
            result.rejectValue("surname", "duplicate", "already exists");
        }
        if (result.hasErrors()) {
            model.put("student", student);
            return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
        } else {
            classRoom.addStudent(student);
            this.students.save(student);
            return "redirect:/classRoom/{classId}";
        }
    }

    private boolean hasNameAndSurname(Student student) {
        return StringUtils.hasLength(student.getFirstName().trim()) && StringUtils.hasLength(student.getSurname().trim());
    }

}

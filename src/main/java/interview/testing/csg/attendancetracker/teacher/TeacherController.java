package interview.testing.csg.attendancetracker.teacher;

import interview.testing.csg.attendancetracker.attendance.AttendanceRepository;
import interview.testing.csg.attendancetracker.classrooms.ClassRoom;
import interview.testing.csg.attendancetracker.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@Controller
public class TeacherController {

    private static final String VIEWS_TEACHER_CREATE_OR_UPDATE_FORM = "teachers/createOrUpdateTeacherForm";

    private final TeacherRepository teachers;

    private final AttendanceRepository attendances;


    @Autowired
    public TeacherController(TeacherRepository teachers, AttendanceRepository attendances) {
        this.teachers = teachers;
        this.attendances = attendances;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/teachers/new")
    public String initCreateForm(Map<String, Object> model) {
        Teacher teacher = new Teacher();
        model.put("teacher", teacher);
        return VIEWS_TEACHER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/teachers/new")
    public String processCreateForm(@Valid Teacher teacher, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_TEACHER_CREATE_OR_UPDATE_FORM;
        } else {
            this.teachers.save(teacher);
            return "redirect:/teachers/" + teacher.getId();
        }
    }

    @GetMapping("/teachers/find")
    public String initFindForm(Map<String, Object> model) {
        model.put("teacher", new Teacher());
        return "teachers/findTeachers";
    }

    @GetMapping("/teachers")
    public String processFindForm(Teacher teacher, BindingResult result, Map<String, Object> model) {

        if (teacher.getSurname() == null) {
            teacher.setSurname("");  // In Query "" is appended to % which means search all
        }

        Collection<Teacher> results = this.teachers.findBySurname(teacher.getSurname());
        if (results.isEmpty()) {
            result.rejectValue("surname", "notFound", "not found");
            return "teachers/findTeachers";
        } else if (results.size() == 1) {
            teacher = results.iterator().next();
            return "redirect:/teachers/" + teacher.getId();
        } else {
            model.put("selections", results);
            return "teachers/teachersList";
        }
    }

    @GetMapping("/teachers/{teacherId}/edit")
    public String initUpdateTeacherForm(@PathVariable("teacherId") int teacherId, Model model) {
        Teacher teacher = this.teachers.findById(teacherId);
        model.addAttribute(teacher);
        return VIEWS_TEACHER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/teachers/{teacherId}/edit")
    public String processUpdateTeacherForm(@Valid Teacher teacher, BindingResult result,
                                           @PathVariable("teacherId") int teacherId) {
        if (result.hasErrors()) {
            return VIEWS_TEACHER_CREATE_OR_UPDATE_FORM;
        } else {
            teacher.setId(teacherId);
            this.teachers.save(teacher);
            return "redirect:/teachers/{teacherId}";
        }
    }

    @GetMapping("/teachers/{teacherId}")
    public ModelAndView showTeacher(@PathVariable("teacherId") int teacherId) {
        ModelAndView modelAndView = new ModelAndView("teachers/teacherDetails");
        Teacher teacher = this.teachers.findById(teacherId);
        for (ClassRoom classRoom : teacher.getClasses()) {
            for (Student student : classRoom.getStudents()) {
                student.setAttendancesInternal(attendances.findByStudentId(student.getId()));
            }
        }
        modelAndView.addObject(teacher);
        return modelAndView;
    }
}

package interview.testing.csg.attendancetracker.teacher;

import interview.testing.csg.attendancetracker.classrooms.ClassRoom;
import interview.testing.csg.attendancetracker.classrooms.ClassRoomRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ClassRoomController {

    private static final String VIEWS_CLASS_ROOM_CREATE_OR_UPDATE_FORM = "classrooms/createOrUpdateClassRoomForm";

    private final ClassRoomRepository classes;

    private final TeacherRepository teachers;

    public ClassRoomController(ClassRoomRepository classes, TeacherRepository teachers) {
        this.classes = classes;
        this.teachers = teachers;
    }

    @ModelAttribute("teacher")
    public Teacher findTeacher(@PathVariable("teacherId") int teacherId) {
        return this.teachers.findById(teacherId);
    }

    @InitBinder
    public void initTeacherBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/teachers/{teacherId}/classrooms/new")
    public String initCreationForm(Teacher teacher, ModelMap model) {
        ClassRoom classRoom = new ClassRoom();
        teacher.addClassRoom(classRoom);
        model.put("classroom", classRoom);
        return VIEWS_CLASS_ROOM_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/teachers/{teacherId}/classrooms/new")
    public String processCreateClassRoomForm(Teacher teacher, @Valid ClassRoom classRoom, BindingResult result, ModelMap model) {
        if (hasNameAndGrade(classRoom) && classRoom.isNew() && teacher.getClassRoom(classRoom.getName(), classRoom.getGrade(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
            result.rejectValue("grade", "duplicate", "already exists");
        }
        teacher.addClassRoom(classRoom);
        if (result.hasErrors()) {
            model.put("classroom", classRoom);
            return VIEWS_CLASS_ROOM_CREATE_OR_UPDATE_FORM;
        } else {
            this.classes.save(classRoom);
            return "redirect:/teachers/{teacherId}";
        }
    }

    @GetMapping("/teachers/{teacherId}/classrooms/{classId}/edit")
    public String initUpdateForm(@PathVariable("classId") int classRoomId, ModelMap model) {
        ClassRoom classRoom = this.classes.findById(classRoomId);
        model.put("classroom", classRoom);
        return VIEWS_CLASS_ROOM_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/teachers/{teacherId}/classrooms/{classId}/edit")
    public String processUpdateForm(ClassRoom classRoom, BindingResult result, Teacher teacher, ModelMap model) {
        if (BooleanUtils.isFalse(hasNameAndGrade(classRoom))) {
            classRoom.setTeacher(teacher);
            model.put("classroom", classRoom);
            return VIEWS_CLASS_ROOM_CREATE_OR_UPDATE_FORM;
        }
        else {
            teacher.addClassRoom(classRoom);
            this.classes.save(classRoom);
            return "redirect:/teachers/{teacherId}";
        }
    }

    private boolean hasNameAndGrade(ClassRoom classRoom) {
        return StringUtils.hasLength(classRoom.getName()) && StringUtils.hasLength(classRoom.getGrade());
    }
}

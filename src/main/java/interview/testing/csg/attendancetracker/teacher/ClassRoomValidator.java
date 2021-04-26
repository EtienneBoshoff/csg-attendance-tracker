package interview.testing.csg.attendancetracker.teacher;

import interview.testing.csg.attendancetracker.classrooms.ClassRoom;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ClassRoomValidator implements Validator {

    private static final String REQUIRED = "required";


    @Override
    public boolean supports(Class<?> aClass) {
        return ClassRoom.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ClassRoom classRoom = (ClassRoom) o;

        if (BooleanUtils.isFalse(StringUtils.hasLength(classRoom.getName()))) {
            errors.rejectValue("name", REQUIRED, REQUIRED);
        }

        if (BooleanUtils.isFalse(StringUtils.hasLength(classRoom.getGrade()))) {
            errors.rejectValue("grade", REQUIRED, REQUIRED);
        }
    }
}

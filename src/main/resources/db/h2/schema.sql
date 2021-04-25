DROP TABLE IF EXISTS class_room CASCADE;
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS teacher CASCADE;
DROP TABLE IF EXISTS attendance CASCADE;

CREATE TABLE teacher (
    id          INTEGER IDENTITY PRIMARY KEY,
    first_name  VARCHAR(30),
    surname     VARCHAR(30)
);
CREATE INDEX teacher_surname ON teacher (surname);

CREATE TABLE class_room (
    id          INTEGER IDENTITY PRIMARY KEY,
    grade       VARCHAR(10),
    name        VARCHAR(30),
    teacher_id  INTEGER NOT NULL
);
ALTER TABLE class_room ADD CONSTRAINT fk_teacher_class_room FOREIGN KEY (teacher_id) REFERENCES teacher (id);
CREATE INDEX class_room_grade ON class_room (grade);

CREATE TABLE student (
    id          INTEGER IDENTITY PRIMARY KEY,
    first_name  VARCHAR(30),
    surname     VARCHAR(30),
    class_id    INTEGER NOT NULL
);

ALTER TABLE student ADD CONSTRAINT fk_class_room_student FOREIGN KEY (class_id) REFERENCES class_room (id);

CREATE TABLE attendance (
    id              INTEGER IDENTITY PRIMARY KEY,
    created_date    DATE,
    status          VARCHAR(30),
    student_id      INTEGER NOT NULL
);
ALTER TABLE attendance ADD CONSTRAINT fk_student_attendance FOREIGN KEY (student_id) REFERENCES student (id);
CREATE INDEX attendance_student_created_date ON attendance (student_id, created_date);
CREATE INDEX attendance_created_date ON attendance (created_date);


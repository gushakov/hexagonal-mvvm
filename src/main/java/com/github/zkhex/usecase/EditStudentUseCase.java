package com.github.zkhex.usecase;

import com.github.zkhex.domain.Student;
import com.github.zkhex.exception.DuplicateStudentError;
import com.github.zkhex.port.EditStudentInputPort;
import com.github.zkhex.port.EditStudentOutputPort;
import com.github.zkhex.port.StudentCrudOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// This is our principle use case: editing a student information.
// For this example we consider creating a new student scenario
// only. This is a core business functionality which should not
// have any dependencies on the classes from the outer layers.
// The use case will be accessed (from the controller or the view-model)
// exclusively through the methods declared in it's input port
// interface.
@Slf4j
@RequiredArgsConstructor
public class EditStudentUseCase implements EditStudentInputPort {

    private final EditStudentOutputPort editStudentOutputPort;
    private final StudentCrudOutputPort studentCrudOutputPort;

    // Perform necessary business logic for this use case:
    // check if the student with the same name exist already
    // and create the new one it she does not exist. We can
    // use several output ports from the same use case to perform
    // the necessary business logic. Any exceptional situation
    // should be signaled to the caller (view-model) by means of
    // a well-defined domain error.
    @Override
    public void createNewStudent(Student student) {
        if (studentCrudOutputPort.studentExists(student.getFirstName(), student.getLastName())) {
            throw new DuplicateStudentError("Student with this name exists already");
        }

        String uuid = studentCrudOutputPort.saveStudent(student);
        this.editStudentOutputPort.setNewStudentUuid(uuid);
    }
}

package com.github.zkhex.vm;

import com.github.zkhex.domain.Student;
import com.github.zkhex.exception.DuplicateStudentError;
import com.github.zkhex.map.StudentMapper;
import com.github.zkhex.port.EditStudentInputPort;
import com.github.zkhex.port.EditStudentOutputPort;
import com.github.zkhex.vmbean.StudentVmBean;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zkplus.spring.SpringUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * View-model will be initialized and bound to the view by ZK. It has its own scope
 * and lifecycle which are different from the bean in the Spring application
 * context. View-model can be seen as a combination of "a presenter" and "a controller".
 *
 * @see SpringUtil#getApplicationContext()
 */
@Slf4j
public class EditStudentVm implements EditStudentOutputPort {

    // Mapper for mapping between view-model beans and domain objects
    private StudentMapper studentMapper;

    // JSR 303 validator
    private Validator validator;

    // Bean to bound to the "new student" from in the view
    @Getter
    private StudentVmBean newStudent;

    // Principal business use case for this view-model: editing (creating) new user
    private EditStudentInputPort editStudentUseCase;

    /**
     * Called once on upon the initial request.
     */
    @Init
    public void init() {

        // Get all the collaborator beans from the Spring application context.
        this.studentMapper = SpringUtil.getApplicationContext().getBean(StudentMapper.class);
        this.validator = SpringUtil.getApplicationContext().getBean(Validator.class);

        // Initialize bean used by the presenter
        this.newStudent = new StudentVmBean();

        // This is where we construct a prototype-scoped bean for edit student
        // use case. Using BeanFactory "getBean()" method allows us to decouple
        // this view-model from the concrete implementation of the use case.
        // We avoid using "new" and use only input port (interface) to obtain
        // an instance of the use case. The actual creation is defined in UseCaseConfig
        // bean declaration.

        this.editStudentUseCase = SpringUtil.getApplicationContext()
                .getBean(EditStudentInputPort.class, this);

        log.debug("[Edit VM] Initialized view-model with use case instance: {}", editStudentUseCase);
    }

    @Override
    public void setNewStudentUuid(String studentUuid) {
        this.newStudent.setStudentUuid(studentUuid);

        // This will trigger the refresh of the attributes of newStudent
        // bean bound to the view
        BindUtils.postNotifyChange(null, null, this, "newStudent");
        showInfo("New student successfully created");
    }

    @Command
    public void createStudent() {
        log.debug("[Edit VM] Processing command to create new student");

        // First we validate view-model bean for input errors
        Set<ConstraintViolation<StudentVmBean>> validationErrors = this.validator.validate(this.newStudent);
        if (!validationErrors.isEmpty()) {
            this.handleValidationErrors(validationErrors);
            return;
        }

        Student student = studentMapper.fromVmBean(this.newStudent);
        try {
            this.editStudentUseCase.createNewStudent(student);
        } catch (DuplicateStudentError e) {
            showError(e.getMessage());
        }
    }

    private void handleValidationErrors(Set<ConstraintViolation<StudentVmBean>> validationErrors) {
        // just show the first error
        validationErrors.stream().findAny()
                .ifPresent(error -> showError(error.getPropertyPath() + ": " + error.getMessage()));
    }

    private void showInfo(String info) {
        Notification.show(info,
                Notification.TYPE_INFO, null,
                400,
                400, 2000, false);
    }

    private void showError(String error) {
        Notification.show(error,
                Notification.TYPE_ERROR, null,
                400,
                400, 2000, false);
    }
}

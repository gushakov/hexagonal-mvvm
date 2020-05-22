package com.github.zkhex.usecase;

import com.github.zkhex.domain.Student;
import com.github.zkhex.exception.DuplicateStudentError;
import com.github.zkhex.port.EditStudentInputPort;
import com.github.zkhex.port.EditStudentOutputPort;
import com.github.zkhex.port.StudentCrudOutputPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Standalone JUnit 5 unit test of {@link EditStudentUseCase} uses only
 * {@link Mockito} for testing.
 */
@ExtendWith(MockitoExtension.class)
class EditStudentUseCaseTest {

    // mock the ports for this use case

    @Mock
    private EditStudentOutputPort editStudentOutputPort;

    @Mock
    private StudentCrudOutputPort studentCrudOutputPort;

    @Test
    void testCreateNewStudent_NoConflict_ReturnsUuid() {

        String uuid = UUID.randomUUID().toString();

        // Given:
        // - no conflicting student entry exists
        // - saving student entry returns a specific UUID
        when(studentCrudOutputPort.studentExists(anyString(), anyString()))
                .thenReturn(false);
        when(studentCrudOutputPort.saveStudent(anyStudent()))
                .thenReturn(uuid);
        // When:
        // - executing an EditStudentUseCase to create new student
        EditStudentInputPort useCase = new EditStudentUseCase(editStudentOutputPort,
                studentCrudOutputPort);
        useCase.createNewStudent(new Student(null, "first", "last"));

        // Then:
        // - StudentCrudOutputPort#studentExists() is called with two string arguments
        // - StudentCrudOutputPort#saveStudent() is called with a Student
        // - EditStudentOutputPort#setNewStudentUuid() is called with the matching UUID

        verify(studentCrudOutputPort, times(1))
                .studentExists(anyString(), anyString());

        verify(studentCrudOutputPort, times(1))
                .saveStudent(anyStudent());

        ArgumentCaptor<String> uuidArg = ArgumentCaptor.forClass(String.class);
        verify(editStudentOutputPort, times(1))
                .setNewStudentUuid(uuidArg.capture());

        assertThat(uuidArg.getValue()).isEqualTo(uuid);
    }

    @Test
    void testCreateNewStudent_WithConflict_ThrowsDuplicateStudentError() {

        // Given:
        // - conflicting entry exists already
        when(studentCrudOutputPort.studentExists(anyString(), anyString()))
                .thenReturn(true);

        // When:
        // - executing an EditStudentUseCase to create new student
        EditStudentInputPort useCase = new EditStudentUseCase(editStudentOutputPort,
                studentCrudOutputPort);

        // Then:
        // - DuplicateStudentError is thrown
        // - StudentCrudOutputPort#saveStudent() is not called
        Assertions.assertThrows(DuplicateStudentError.class,
                () -> useCase.createNewStudent(new Student(null, "first", "last")));

        verify(studentCrudOutputPort, times(0))
                .saveStudent(anyStudent());
    }

    private Student anyStudent() {
        return any(Student.class);
    }
}
package com.github.zkhex.map;

import com.github.zkhex.domain.Student;
import com.github.zkhex.entity.StudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.zkhex.assertj.Assertions.assertThat;

public class DefaultStudentMapperTest {

    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        this.studentMapper = new DefaultStudentMapper();
    }

    @Test
    void testMapStudentToStudentEntity() {
        Student student = new Student("1", "Brad", "Pitt");
        StudentEntity entity = studentMapper.toEntity(student);
        assertThat(entity)
                .hasStudentUuid("1")
                .hasFirstName("Brad")
                .hasLastName("Pitt");
    }

    @Test
    void testMapStudentEntityToStudent() {
        StudentEntity entity = new StudentEntity();
        entity.setStudentUuid("1");
        entity.setFirstName("Brad");
        entity.setLastName("Pitt");

        Student student = studentMapper.fromEntity(entity);

        assertThat(student)
                .hasStudentUuid("1")
                .hasFirstName("Brad")
                .hasLastName("Pitt");
    }
}

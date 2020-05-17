package com.github.zkhex.repository;

import com.github.zkhex.entity.StudentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static com.github.zkhex.assertj.Assertions.assertThat;

@DataJpaTest
public class StudentJpaRepositoryTest {

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Test
    public void testSaveAndFindNewStudent() {
        StudentEntity entity = new StudentEntity();
        String uuid = UUID.randomUUID().toString();
        entity.setStudentUuid(uuid);
        entity.setFirstName("Brad");
        entity.setLastName("Pitt");
        studentJpaRepository.save(entity);
        assertThat(studentJpaRepository.findByStudentUuid(uuid))
                .hasStudentUuid(uuid)
                .hasFirstName("Brad")
                .hasLastName("Pitt");
    }
}
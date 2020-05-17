package com.github.zkhex.adapter;

import com.github.zkhex.domain.Student;
import com.github.zkhex.entity.StudentEntity;
import com.github.zkhex.map.StudentMapper;
import com.github.zkhex.port.StudentCrudOutputPort;
import com.github.zkhex.repository.StudentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentCrudJpaAdapter implements StudentCrudOutputPort {

    private final StudentMapper studentMapper;
    private final StudentJpaRepository studentJpaRepository;

    @Override
    public String saveStudent(Student student) {
        StudentEntity entity = studentMapper.toEntity(student);
        entity.setStudentUuid(UUID.randomUUID().toString());
        return studentJpaRepository.save(entity).getStudentUuid();
    }

    @Override
    public boolean studentExists(String firstName, String lastName) {
        return studentJpaRepository.findByFirstNameAndLastName(firstName, lastName) != null;
    }
}

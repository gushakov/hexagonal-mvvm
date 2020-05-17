package com.github.zkhex.repository;

import com.github.zkhex.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * JPA repository for {@link StudentEntity}.
 */
public interface StudentJpaRepository extends CrudRepository<StudentEntity, String> {

    StudentEntity findByStudentUuid(String studentUuid);

    StudentEntity findByFirstNameAndLastName(String firstName, String lastName);

}

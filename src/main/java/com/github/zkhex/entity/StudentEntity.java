package com.github.zkhex.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * JPA entity to be persisted via CRUD repository using Spring Data. Lombok
 * is used for usual boilerplate code. The table name is set to "student".
 */
@Data
@Entity(name = "student")
public class StudentEntity {

    @Id
    String studentUuid;

    String firstName;

    String lastName;
}

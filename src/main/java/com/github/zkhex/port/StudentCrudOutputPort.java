package com.github.zkhex.port;

import com.github.zkhex.domain.Student;

public interface StudentCrudOutputPort {

    String saveStudent(Student student);

    boolean studentExists(String firstName, String lastName);
}

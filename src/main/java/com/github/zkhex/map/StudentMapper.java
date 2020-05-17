package com.github.zkhex.map;

import com.github.zkhex.domain.Student;
import com.github.zkhex.entity.StudentEntity;
import com.github.zkhex.vmbean.StudentVmBean;

public interface StudentMapper {

    Student fromEntity(StudentEntity entity);
    StudentEntity toEntity(Student student);

    Student fromVmBean(StudentVmBean vmBean);
    StudentVmBean toVmBean(Student student);

}

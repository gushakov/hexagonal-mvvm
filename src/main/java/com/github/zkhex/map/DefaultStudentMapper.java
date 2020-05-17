package com.github.zkhex.map;


import com.github.zkhex.domain.Student;
import com.github.zkhex.entity.StudentEntity;
import com.github.zkhex.vmbean.StudentVmBean;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.metadata.MappingDirection;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link StudentMapper} using Orika mappers.
 */
@Service
public class DefaultStudentMapper extends ConfigurableMapper implements StudentMapper {

    // Optimize mappers using bounded facades.
    private BoundMapperFacade<Student, StudentEntity> studentEntityBoundMapperFacade;
    private BoundMapperFacade<Student, StudentVmBean> studentVmBeanBoundMapperFacade;

    @Override
    protected void configure(MapperFactory factory) {

        // Register bidirectional Orika class mappers. Orika
        // allows for automatic field mapping by matching names.
        factory.classMap(Student.class, StudentEntity.class)
                .byDefault(MappingDirection.BIDIRECTIONAL)
                .register();

        factory.classMap(Student.class, StudentVmBean.class)
                .byDefault(MappingDirection.BIDIRECTIONAL)
                .register();

        // Create all the mapping facades for Student to entity and Student to view-model
        // bean conversions.
        this.studentEntityBoundMapperFacade = factory.getMapperFacade(Student.class,
                StudentEntity.class, false);
        this.studentVmBeanBoundMapperFacade = factory.getMapperFacade(Student.class,
                StudentVmBean.class, false);
    }

    @Override
    public Student fromEntity(StudentEntity entity) {
        return this.studentEntityBoundMapperFacade.mapReverse(entity);
    }

    @Override
    public StudentEntity toEntity(Student student) {
        return this.studentEntityBoundMapperFacade.map(student);
    }

    @Override
    public Student fromVmBean(StudentVmBean vmBean) {
        return this.studentVmBeanBoundMapperFacade.mapReverse(vmBean);
    }

    @Override
    public StudentVmBean toVmBean(Student student) {
        return this.studentVmBeanBoundMapperFacade.map(student);
    }
}

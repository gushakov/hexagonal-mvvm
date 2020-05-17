package com.github.zkhex.usecase;

import com.github.zkhex.port.EditStudentInputPort;
import com.github.zkhex.port.EditStudentOutputPort;
import com.github.zkhex.port.StudentCrudOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * {@code Configuration} used to declare use case beans.
 */
@Configuration
public class UseCaseConfig {

    @Autowired
    private StudentCrudOutputPort studentCrudOutputPort;

    // We will create use case beans using BeanFactory's "getBean()" method from
    // the view-model. This bean is declared with scope "prototype" since it
    // will be created on-demand with output port (view-model instance) being
    // passed as a constructor argument. Notice, we autowire the rest of the
    // dpendenices needed for the use case construction.
    @Bean(autowireCandidate = false)
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public EditStudentInputPort editStudentInputPort(EditStudentOutputPort editStudentOutputPort){
        return new EditStudentUseCase(editStudentOutputPort, studentCrudOutputPort);
    }

}

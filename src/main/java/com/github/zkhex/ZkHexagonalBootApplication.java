package com.github.zkhex;

import com.github.zkhex.repository.StudentJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Starts Spring Boot application. We tell Spring where to scan for JPA
 * repositories.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = StudentJpaRepository.class)
public class ZkHexagonalBootApplication {

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZkHexagonalBootApplication.class, args);
    }

}

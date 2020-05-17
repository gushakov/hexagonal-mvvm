package com.github.zkhex.vmbean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentVmBean {

    private String studentUuid;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}

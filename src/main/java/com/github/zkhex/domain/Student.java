package com.github.zkhex.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Student {

    private final String studentUuid;
    private final String firstName;
    private final String lastName;
}

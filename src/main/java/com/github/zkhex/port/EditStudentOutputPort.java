package com.github.zkhex.port;

public interface EditStudentOutputPort {

    /**
     * Sets the UUID of the newly created student. Will be called
     * from the use case upon successful creation of the new user.
     *
     * @param studentUuid UUID of the newly created student
     */
    void setNewStudentUuid(String studentUuid);
}

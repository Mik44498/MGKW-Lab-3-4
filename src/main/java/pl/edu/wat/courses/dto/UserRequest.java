package pl.edu.wat.courses.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String surname;
    private String name;
    private String phoneNumber;
    private String courseId;
}

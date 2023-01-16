package pl.edu.wat.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String courseId;
}

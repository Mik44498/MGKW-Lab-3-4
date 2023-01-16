package pl.edu.wat.courses.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class User {
    @MongoId
    private String id;
    private String surname;
    private String name;
    private String phoneNumber;
    private String courseId;

}
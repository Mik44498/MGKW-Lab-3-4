package pl.edu.wat.courses.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Course {
    @MongoId
    private String id;
    private String courseName;
    private Integer participantsNumber;
    //private String UserId;
    public Course(String courseName, Integer participantsNumber){
        this.courseName = courseName;
        this.participantsNumber = participantsNumber;
    }
}

package pl.edu.wat.courses.dto;

import lombok.Data;

@Data
public class CourseRequest {
    //private String id;
    private String courseName;
    //private String courseDetails;
    private Integer participantsNumber;
}

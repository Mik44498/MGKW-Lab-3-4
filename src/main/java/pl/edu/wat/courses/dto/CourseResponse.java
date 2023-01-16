package pl.edu.wat.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseResponse {
    private String Id;
    private String courseName;
    private Integer participantsNumber;
    //private String courseDetails;
}

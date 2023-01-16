package pl.edu.wat.courses.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.courses.dto.CourseRequest;
import pl.edu.wat.courses.dto.CourseResponse;
import pl.edu.wat.courses.exception.EntityNotFound;
import pl.edu.wat.courses.service.CourseService;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public ResponseEntity<List<CourseResponse>> getAllCourse() {
        List<CourseResponse> userOptional = courseService.getAll();
        return new ResponseEntity<>(userOptional, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createCourse(@RequestBody CourseRequest userRequest) {
        return new ResponseEntity<>(courseService.save(userRequest).getId(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") String id) {
        try {
            courseService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
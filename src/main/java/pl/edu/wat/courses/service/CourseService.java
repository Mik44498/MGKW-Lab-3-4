package pl.edu.wat.courses.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.courses.dto.CourseRequest;
import pl.edu.wat.courses.dto.CourseResponse;
import pl.edu.wat.courses.dto.UserResponse;
import pl.edu.wat.courses.entity.Course;
import pl.edu.wat.courses.entity.User;
import pl.edu.wat.courses.exception.EntityNotFound;
import pl.edu.wat.courses.repository.CourseRepository;
import pl.edu.wat.courses.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    //private final ParticipantsNumberService participantsNumberService;


    @Autowired
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }



    public CourseResponse getCourseById(String id) throws EntityNotFound {
        Course course = courseRepository.findById(id).orElseThrow(EntityNotFound::new);
        return new CourseResponse(course.getId(), course.getCourseName(), course.getParticipantsNumber());
    }

    public CourseResponse save(CourseRequest CourseRequest) {
        Course course = new Course(CourseRequest.getCourseName(), CourseRequest.getParticipantsNumber());
        System.out.println(course);
        course = courseRepository.save(
                course
        );
        System.out.println(course);
        return new CourseResponse(course.getId(), course.getCourseName(), course.getParticipantsNumber());
    }

    public List<CourseResponse> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(course -> new CourseResponse(course.getId(), course.getCourseName(), course.getParticipantsNumber()))
                .toList();
    }

    public CourseResponse update(String id, String courseName, Integer participantsNumber) throws EntityNotFound {
        Course course = courseRepository.findById(id).orElseThrow(EntityNotFound::new);
        course.setCourseName(courseName);
        course.setParticipantsNumber(participantsNumber);
        course = courseRepository.save(course);
        return new CourseResponse(course.getId(), course.getCourseName(), course.getParticipantsNumber());
    }

    public CourseResponse delete(String id) throws EntityNotFound {
        Course course = courseRepository.findById(id).orElseThrow(EntityNotFound::new);

        courseRepository.deleteById(id);
        System.out.println("Usunięto z bazy: " + course.getCourseName() + " " + course.getParticipantsNumber());
        return new CourseResponse(course.getId(), course.getCourseName(), course.getParticipantsNumber());

    }
}

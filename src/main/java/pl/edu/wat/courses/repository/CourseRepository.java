package pl.edu.wat.courses.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.courses.entity.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
}

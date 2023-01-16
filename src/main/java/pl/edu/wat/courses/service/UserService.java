package pl.edu.wat.courses.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.courses.dto.UserRequest;
import pl.edu.wat.courses.dto.UserResponse;
import pl.edu.wat.courses.entity.User;
import pl.edu.wat.courses.exception.EntityNotFound;
import pl.edu.wat.courses.mapper.UserMapper;
import pl.edu.wat.courses.repository.CourseRepository;
import pl.edu.wat.courses.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.courseRepository = courseRepository;
    }

    public Optional<UserResponse> getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::map);
    }

    public UserResponse save(UserRequest userRequest) {
        User user = userMapper.map(userRequest);
        user = userRepository.save(
                user
        );
        return userMapper.map(user);
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    public UserResponse update(String id, String name, String surname, String courseId) throws EntityNotFound {
        User user = userRepository.findById(id).orElseThrow(EntityNotFound::new);
        if(StringUtils.isNotBlank(name)) {
            user.setName(name);
        }

        if(StringUtils.isNotBlank(surname)) {
            user.setSurname(surname);
        }
        if(StringUtils.isNotBlank(courseId)) {
            user.setCourseId(courseId);
        }

        user = userRepository.save(user);
        return userMapper.map(user);
    }

    public void delete(String id) throws EntityNotFound {
        userRepository.findById(id).orElseThrow(EntityNotFound::new);
        userRepository.deleteById(id);
    }
}




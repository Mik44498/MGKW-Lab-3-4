package pl.edu.wat.courses.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.courses.dto.UserRequest;
import pl.edu.wat.courses.dto.UserResponse;
import pl.edu.wat.courses.exception.EntityNotFound;
import pl.edu.wat.courses.service.UserService;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> userOptional = userService.getAll();
        return new ResponseEntity<>(userOptional, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserByIdVar(@PathVariable String id) {
        Optional<UserResponse> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @GetMapping("{id}/surname")
    public ResponseEntity<String> getUserSurnameById(@PathVariable String id) {
        Optional<UserResponse> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userOptional.get().getSurname(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserRequest userMapper) {
        return new ResponseEntity<>(userService.save(userMapper).getId(), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestParam(required = false) String name, @RequestParam(required = false) String surname,@RequestParam(required = false) String courseId) {
        try {
            return new ResponseEntity<>(userService.update(id, name, surname, courseId), HttpStatus.OK);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") String id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package pl.edu.wat.courses.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.courses.dto.UserRequest;
import pl.edu.wat.courses.dto.UserResponse;
import pl.edu.wat.courses.entity.User;

@Service
public class UserMapper {

    public User map(UserRequest userMapper) {
        User user = new User();
        user.setName(userMapper.getName());
        user.setSurname(userMapper.getSurname());
        user.setPhoneNumber(userMapper.getPhoneNumber());
        user.setCourseId(userMapper.getCourseId());
        fillUserRequest(user, userMapper);
        return user;
    }

    private void fillUserRequest(User user, UserRequest userMapper) {
//        user.setRank(userRequest.getRank());
        // empty for byte buddy
    }

    public UserResponse map(User user) {
        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getSurname(),user.getPhoneNumber(), user.getCourseId());
        fillUser(userResponse, user);
        return userResponse;
    }

    private void fillUser(UserResponse userResponse, User user) {
        //userResponse.setRank(user.getRank());
        // empty for byte buddy
    }


}

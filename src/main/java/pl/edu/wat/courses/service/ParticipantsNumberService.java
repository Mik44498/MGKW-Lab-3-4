package pl.edu.wat.courses.service;

import org.springframework.stereotype.Service;
import pl.edu.wat.courses.entity.Course;

import java.util.Random;

@Service
public class ParticipantsNumberService {
    public Integer getParticipantsNumber(Course course) {
        return new Random().nextInt(10); //TODO
    }
}

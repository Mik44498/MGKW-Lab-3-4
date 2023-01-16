package pl.edu.wat.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.wat.courses.reflection.FieldInformation;
import pl.edu.wat.courses.reflection.Reflection;
@SpringBootApplication
public class GraalvmZad3Application {

    public static void main(String[] args) {
        FieldInformation fieldInformation = new FieldInformation();
        Reflection.apply(fieldInformation.getFieldName(),fieldInformation.getFieldType());
        SpringApplication.run(GraalvmZad3Application.class, args);

    }

}

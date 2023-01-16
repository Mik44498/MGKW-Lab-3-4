package pl.edu.wat.courses.service;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.courses.repository.CourseRepository;
import pl.edu.wat.courses.repository.UserRepository;

@Service
@Slf4j
public class ScriptService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScriptService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("articleRepository", courseRepository);
            bindings.putMember("userRepository", userRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }
}

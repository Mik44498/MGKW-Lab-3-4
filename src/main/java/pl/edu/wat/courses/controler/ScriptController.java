package pl.edu.wat.courses.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.courses.service.ScriptService;

@RestController
@CrossOrigin
@RequestMapping("/api/script")
public class ScriptController {
    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PutMapping()
    public ResponseEntity<String> execScript() {
        //@RequestBody String script
        String script = """
                var User = Java.type('pl.edu.wat.courses.entity.User');
                var Course = Java.type('pl.edu.wat.courses.entity.Course');
                var Set = Java.type('java.util.Set');
                
                function changepn(){
                for(user of userRepository.findAll()){
                var userPhonenum = user.getPhoneNumber();
                var changepn = '+48 '+userPhonenum.substring(0, 3)+ '-'+userPhonenum.substring(3, 6)+'-'+userPhonenum.substring(6, 9);
                if (userPhonenum.length === 9){
                user.setPhoneNumber(changepn);
                userRepository.save(user);
                }
                }
                return userRepository.findAll();
                }
                changepn();
                """;
        return new ResponseEntity<>(scriptService.exec(script), HttpStatus.OK) ;
    }
}


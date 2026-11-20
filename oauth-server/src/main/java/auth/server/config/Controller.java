package auth.server.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: auth.server.config.Controller
 * @description:
 * @author: yang pan
 */
@RestController
public class Controller {

    @GetMapping("/getUser")
    public Map<String,String> getMessages() {
        Map<String, String> user = new HashMap<>();
        user.put("username", "1809168104");
        user.put("password", "123456");
        user.put("nickname", "杨十七");
        return user;
    }
    @GetMapping("/test")
    public String testSSl(){

        return "test";
    }
}

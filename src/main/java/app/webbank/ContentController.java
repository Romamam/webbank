package app.webbank;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    @GetMapping("/home")
    public String home(){
        return ("<h1>Welcome</h1>");
    }

    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return ("<h1>Admin</h1>");
    }

    @GetMapping("/user/home")
    public String handleUserHome(){
        return ("<h1>User</h1>");
    }
}

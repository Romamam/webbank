package app.webbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return "admin_home";
    }

    @GetMapping("/user/home")
    public String handleUserHome(){
        return "user_home";
    }

    @GetMapping("/login")
    public String handleLogin(){
        return "custom_login";
    }

    @GetMapping("/sign-up")
    public String registrationPage(){
        return "sign_up";
    }

    @GetMapping("/user/transaction")
    public String transactionPage(){
        return "transactions";
    }

    @GetMapping("/user/categories")
    public String categoriesPage(){
        return "categories";
    }

    @GetMapping("/user/reports")
    public String reportsPage(){
        return "reports";
    }
}

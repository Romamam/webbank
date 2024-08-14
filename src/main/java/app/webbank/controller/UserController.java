package app.webbank.controller;


import app.webbank.dao.UserRepository;
import app.webbank.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    final
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }

    @GetMapping("/user/profile/{id}")
    public Optional<User> findUser(@PathVariable Long id){
        return userRepository.findById(id);

    }
}

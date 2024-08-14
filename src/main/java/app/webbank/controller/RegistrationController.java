package app.webbank.controller;

import app.webbank.dao.CategoryRepository;
import app.webbank.dao.UserRepository;
import app.webbank.model.Category;
import app.webbank.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/sign-up/user")
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        List<Category> defaultCategories = List.of(
                new Category("Food", savedUser),
                new Category("Transport", savedUser),
                new Category("Entertainment", savedUser),
                new Category("Utilities", savedUser)
        );

        categoryRepository.saveAll(defaultCategories);

        return savedUser;
    }
}

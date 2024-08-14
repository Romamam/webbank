package app.webbank.controller;

import app.webbank.dao.CategoryRepository;
import app.webbank.dao.UserRepository;
import app.webbank.model.Category;
import app.webbank.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/user/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryController(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    @PostMapping()
    public String createCategory(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestParam String name,
                                   @RequestParam(required = false) String description){
        User user = userRepository.findByUsername(userDetails.getUsername());
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setUser(user);
        categoryRepository.save(category);
        return "redirect:/user/categories";
    }

    @GetMapping("/new")
    public String showCategoryForm() {
        return "new_category";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        Optional<Category> existingProduct = categoryRepository.findById(id);
        if(existingProduct.isPresent()){
            categoryRepository.delete(existingProduct.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

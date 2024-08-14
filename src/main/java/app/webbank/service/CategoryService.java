package app.webbank.service;

import app.webbank.model.Category;
import app.webbank.model.User;
import app.webbank.dao.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoriesForUser(Optional<User> user) {
        return categoryRepository.findByUser(user);
    }
}
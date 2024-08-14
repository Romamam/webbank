package app.webbank.controller;

import app.webbank.dao.CategoryRepository;
import app.webbank.dao.TransactionRepository;
import app.webbank.dao.UserRepository;
import app.webbank.model.Category;
import app.webbank.model.Transaction;
import app.webbank.model.User;
import app.webbank.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ContentController {

    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public ContentController(UserRepository userRepository, CategoryService categoryService, CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return "admin_home";
    }

    @GetMapping("/user/home")
    public String handleUserHome(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Transaction> transactions = transactionRepository.findByUser(user);
        model.addAttribute("transactions", transactions);
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
    public String transactionPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Transaction> transactions = transactionRepository.findByUser(user);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

    @GetMapping("/user/categories")
    public String categoriesPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Category> categories = categoryService.getCategoriesForUser(Optional.ofNullable(user));
        model.addAttribute("categories", categories);

        return "all_categories";
    }

    @GetMapping("/user/reports")
    public String reportsPage(){
        return "reports";
    }

    @GetMapping("/user/transaction-categories")
    public ResponseEntity<Map<String, Double>> getTransactionCategories(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Transaction> transactions = transactionRepository.findByUser(user);

        Map<String, Double> categorySums = transactions.stream()
                .filter(transaction -> transaction.getCategoryName() != null)
                .collect(Collectors.groupingBy(Transaction::getCategoryName,
                        Collectors.summingDouble(Transaction::getAmount)));

        return ResponseEntity.ok(categorySums);
    }
}

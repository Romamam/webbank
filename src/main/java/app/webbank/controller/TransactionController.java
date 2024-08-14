package app.webbank.controller;

import app.webbank.dao.CategoryRepository;
import app.webbank.dao.TransactionRepository;
import app.webbank.dao.UserRepository;
import app.webbank.model.Category;
import app.webbank.model.Transaction;
import app.webbank.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionController(UserRepository userRepository, TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/user/transaction/new")
    public String showTransactionForm(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Category> categories = categoryRepository.findByUser(Optional.ofNullable(user));
        model.addAttribute("categories", categories);
        model.addAttribute("userId", user != null ? user.getId() : null);
        return "new_transaction";
    }

    @PostMapping("/user/transactions")
    public String saveTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam double amount,
                                  @RequestParam Long categoryId) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        Category category = categoryRepository.getReferenceById(categoryId);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(java.sql.Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setCategoryName(category.getName());
        transactionRepository.save(transaction);
        return "redirect:/user/transaction";
    }

    @GetMapping("/user/all-transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Transaction> transactions = transactionRepository.findByUser(user);
        model.addAttribute("transactions", transactions);
        return ResponseEntity.ok(transactions);
    }
}

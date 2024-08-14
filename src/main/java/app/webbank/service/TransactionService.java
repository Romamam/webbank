package app.webbank.service;

import app.webbank.dao.TransactionRepository;
import app.webbank.model.Transaction;
import app.webbank.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionsForUser(User user){
        return transactionRepository.findByUser(user);
    }
}

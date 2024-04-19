package antifraud.web.controller;

import antifraud.service.TransactionService;
import antifraud.web.dto.TransactionDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/api/antifraud/transaction")
    ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(this.transactionService.newTransaction(transactionDto));
    }
}

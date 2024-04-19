package antifraud.service;

import antifraud.constants.TransactionResult;
import antifraud.web.dto.TransactionDto;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    static final int MAXIMUM_ALLOWED_AMOUNT = 200;
    static final int MAXIMUM_ALLOWED_MANUAL_PROCESSING = 1500;

    public TransactionDto newTransaction(TransactionDto transactionDto) {
        long amount = transactionDto.getAmount();
        return TransactionDto.builder()
                .result(amount <= MAXIMUM_ALLOWED_AMOUNT
                        ? TransactionResult.ALLOWED
                        : amount <= MAXIMUM_ALLOWED_MANUAL_PROCESSING
                        ? TransactionResult.MANUAL_PROCESSING
                        : TransactionResult.PROHIBITED
                )
                .build();
    }
}

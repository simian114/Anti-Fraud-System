package antifraud.web.mapper;

import antifraud.domain.Transaction;
import antifraud.web.dto.TransactionDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction toEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setIp(transactionDto.getIp());
        transaction.setNumber(transactionDto.getNumber());
        transaction.setRegion(transactionDto.getRegion());
        transaction.setDate(transactionDto.getDate());
        return transaction;
    }

    public TransactionDto toDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        dto.setNumber(transaction.getNumber());
        dto.setRegion(transaction.getRegion());
        dto.setIp(transaction.getIp());
        dto.setResult(transaction.getResult());
        dto.setFeedback(transaction.getFeedback());
        return dto;
    }
}

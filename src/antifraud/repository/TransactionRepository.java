package antifraud.repository;

import antifraud.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllTransactionByNumberAndDateBetween(String number, LocalDateTime startDate, LocalDateTime endDate);

    List<Transaction> findAllByNumberOrderByTransactionIdAsc(String number);

    @Query("SELECT t FROM Transaction t JOIN FETCH t.card WHERE t.transactionId = ?1")
    Optional<Transaction> findByIdWithCard(long transactionId);
}

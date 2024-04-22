package antifraud.repository;

import antifraud.domain.StolenCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StolenCardRepository extends JpaRepository<StolenCard, Long> {
    boolean existsByNumber(String number);

    Optional<StolenCard> findByNumber(String number);
}

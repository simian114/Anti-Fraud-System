package antifraud.service;

import antifraud.domain.Card;
import antifraud.domain.SuspiciousIp;
import antifraud.domain.Transaction;
import antifraud.repository.StolenCardRepository;
import antifraud.repository.SuspiciousIpRepository;
import antifraud.repository.TransactionRepository;
import antifraud.web.dto.StolenCardDto;
import antifraud.web.dto.SuspiciousIpDto;
import antifraud.web.dto.TransactionDto;
import antifraud.web.dto.TransactionFeedbackDto;
import antifraud.web.exception.exceptions.conflict.ConflictException;
import antifraud.web.exception.exceptions.notFound.NotFoundException;
import antifraud.web.exception.exceptions.unprocessable.UnProcessableException;
import antifraud.web.mapper.CardMapper;
import antifraud.web.mapper.SuspiciousIpMapper;
import antifraud.web.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AntifraudService {
    private final SuspiciousIpRepository suspiciousIpRepository;
    private final StolenCardRepository stolenCardRepository;
    private final TransactionRepository transactionRepository;

    private final SuspiciousIpMapper suspiciousIpMapper;
    private final CardMapper cardMapper;
    private final TransactionMapper transactionMapper;

    public SuspiciousIpDto newSuspiciousIP(SuspiciousIpDto suspiciousIpDto) {
        if (this.suspiciousIpRepository.findByIp(suspiciousIpDto.getIp()).isPresent()) {
            throw new ConflictException("Suspicious IP already exists");
        }
        return this.suspiciousIpMapper.toDto(
                this.suspiciousIpRepository.save(
                        this.suspiciousIpMapper.toEntity(suspiciousIpDto)
                )
        );
    }

    public void deleteSuspiciousIp(String ip) {
        SuspiciousIp suspiciousIp = findSuspiciousIpOrThrow(ip);
        this.suspiciousIpRepository.delete(suspiciousIp);
    }

    public List<SuspiciousIpDto> getAllSuspiciousIp() {
        return this.suspiciousIpRepository.findAll()
                .stream()
                .map(this.suspiciousIpMapper::toDto)
                .toList();
    }

    private SuspiciousIp findSuspiciousIpOrThrow(String ip) throws NotFoundException {
        return this.suspiciousIpRepository
                .findByIp(ip)
                .orElseThrow(() -> new NotFoundException(ip + " not found"));
    }


    public StolenCardDto addStolenCard(StolenCardDto stolenCardDto) {
        if (this.stolenCardRepository.existsByNumber(stolenCardDto.getNumber())) {
            throw new ConflictException(stolenCardDto.getNumber() + " already exist");
        }
        return this.cardMapper.toDto(this.stolenCardRepository.save(this.cardMapper.toEntity(stolenCardDto)));
    }

    public void deleteStolenCard(String number) {
        this.stolenCardRepository.delete(
                this.stolenCardRepository.findByNumber(number)
                        .orElseThrow(
                                () -> new NotFoundException(number + " not found")
                        )
        );
    }

    public List<StolenCardDto> getAllStolenCard() {
        return this.stolenCardRepository.findAll().stream()
                .map(this.cardMapper::toDto)
                .toList();
    }

    public List<TransactionDto> getAllTransaction() {
        return this.transactionRepository.findAll().stream()
                .map(this.transactionMapper::toDto)
                .toList();
    }

    public List<TransactionDto> getAllTransactionByNumber(String number) throws NotFoundException {
        List<TransactionDto> list = this.transactionRepository.findAllByNumberOrderByTransactionIdAsc(number).stream()
                .map(this.transactionMapper::toDto)
                .toList();
        if (list.isEmpty()) {
            throw new NotFoundException(number + " not found");
        }
        return list;
    }

    public TransactionDto setTransactionFeedback(TransactionFeedbackDto transactionFeedbackDto) {
        Transaction transaction = this.getTransactionByIdOrThrow(transactionFeedbackDto.getTransactionId());
        Card card = transaction.getCard();
        log.info("CARD ALLOW LIMIT: {}", transaction.getCard().getAllowLimit());
        log.info("CARD MANUAL LIMIT: {}", transaction.getCard().getManualLimit());

        int differenceLevel = transaction.getResult().getDifferenceLevel(transactionFeedbackDto.getFeedback());
        this.checkConflict(differenceLevel);

        this.checkAlreadyHaveFeedback(transaction);

        transaction.setFeedback(transactionFeedbackDto.getFeedback());

        // from, to, value
        card.changeLimit(
                transaction.getResult(),
                transactionFeedbackDto.getFeedback(),
                transaction.getAmount()
        );

        // save
        this.transactionRepository.save(transaction);

        return this.transactionMapper.toDto(transaction);
    }

    private Transaction getTransactionByIdOrThrow(long id) {
        return this.transactionRepository.findByIdWithCard(id)
                .orElseThrow(() -> new NotFoundException(id + " not found"));
    }

    private void checkConflict(int differenceLevel) {
        if (differenceLevel == 0) {
            throw new UnProcessableException();
        }
    }

    private void checkAlreadyHaveFeedback(Transaction transaction) throws ConflictException {
        if (transaction.getFeedback() != null) {
            throw new ConflictException(transaction.getTransactionId() + " already has feedback");
        }
    }
}

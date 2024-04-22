package antifraud.service;

import antifraud.constants.RegionCodeEnum;
import antifraud.constants.TransactionInfoEnum;
import antifraud.constants.TransactionResultEnum;
import antifraud.domain.Card;
import antifraud.domain.StolenCard;
import antifraud.domain.SuspiciousIp;
import antifraud.domain.Transaction;
import antifraud.repository.CardRepository;
import antifraud.repository.StolenCardRepository;
import antifraud.repository.SuspiciousIpRepository;
import antifraud.repository.TransactionRepository;
import antifraud.web.dto.TransactionDto;
import antifraud.web.dto.response.TransactionResponse;
import antifraud.web.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    static int MAXIMUM_MANUAL_CORRELATION_COUNT = 3;

    private final StolenCardRepository stolenCardRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final SuspiciousIpRepository suspiciousIpRepository;

    private final TransactionMapper transactionMapper;

    public TransactionResponse newTransaction(TransactionDto transactionDto) {
        SuspiciousIp suspiciousIp = this.suspiciousIpRepository.findByIp(transactionDto.getIp()).orElse(null);
        StolenCard stolenCard = this.stolenCardRepository.findByNumber(transactionDto.getNumber()).orElse(null);

        Card card = this.cardRepository.findByNumber(transactionDto.getNumber())
                .orElseGet(() -> this.cardRepository.save(new Card(transactionDto.getNumber())));

        List<Transaction> latestTransactions = this.transactionRepository.findAllTransactionByNumberAndDateBetween(
                transactionDto.getNumber(),
                transactionDto.getDate().minusHours(1),
                transactionDto.getDate()
        );

        Set<String> transactionIpList = latestTransactions.stream()
                .map(Transaction::getIp)
                .collect(Collectors.toSet());
        transactionIpList.add(transactionDto.getIp());

        Set<RegionCodeEnum> transactionRegionList = latestTransactions.stream()
                .map(Transaction::getRegion)
                .collect(Collectors.toSet());
        transactionRegionList.add(transactionDto.getRegion());

        TransactionResponse res = new TransactionResponse();

        List<TransactionInfoEnum> prohibitedList = this.checkTransactionProhibited(suspiciousIp, stolenCard, transactionIpList, transactionRegionList, transactionDto, card);
        List<TransactionInfoEnum> manualProcessingList = this.checkTransactionManualProcessing(transactionDto, transactionIpList, transactionRegionList, card);

        if (!prohibitedList.isEmpty()) {
            res.setResult(TransactionResultEnum.PROHIBITED);
            res.setInfo(prohibitedList.stream()
                    .map(TransactionInfoEnum::getValue)
                    .collect(Collectors.joining(", ")));
        } else if (!manualProcessingList.isEmpty()) {
            res.setResult(TransactionResultEnum.MANUAL_PROCESSING);
            res.setInfo(manualProcessingList
                    .stream().map(TransactionInfoEnum::getValue)
                    .collect(Collectors.joining(", ")));
        } else {
            res.setResult(TransactionResultEnum.ALLOWED);
            res.setInfo(TransactionInfoEnum.NONE.getValue());
        }

        Transaction entity = this.transactionMapper.toEntity(transactionDto);
        entity.setResult(res.getResult());
        entity.setCard(card);
        this.transactionRepository.save(entity);
        return res;
    }

    private List<TransactionInfoEnum> checkTransactionManualProcessing(TransactionDto transactionDto, Set<String> uniqueIpList, Set<RegionCodeEnum> uniqueRegionList, Card card) {
        List<TransactionInfoEnum> errorList = new ArrayList<>();

        if (transactionDto.getAmount() > card.getAllowLimit()
                && transactionDto.getAmount() <= card.getManualLimit()
        ) {
            errorList.add(TransactionInfoEnum.AMOUNT);
        }
        if (uniqueIpList.size() == MAXIMUM_MANUAL_CORRELATION_COUNT) {
            errorList.add(TransactionInfoEnum.IP_CORRELATION);
        }
        if (uniqueRegionList.size() == MAXIMUM_MANUAL_CORRELATION_COUNT) {
            errorList.add(TransactionInfoEnum.REGION_CORRELATION);
        }
        errorList.sort(Comparator.comparing(TransactionInfoEnum::getValue));
        return errorList;
    }

    private List<TransactionInfoEnum> checkTransactionProhibited(SuspiciousIp suspiciousIp, StolenCard stolenCard, Set<String> uniqueIpList, Set<RegionCodeEnum> uniqueRegionList, TransactionDto transactionDto, Card card) {
        List<TransactionInfoEnum> errorList = new ArrayList<>();
        if (transactionDto.getAmount() > card.getManualLimit()) {
            errorList.add(TransactionInfoEnum.AMOUNT);
        }
        if (stolenCard != null) {
            errorList.add(TransactionInfoEnum.CARD_NUMBER);
        }
        if (suspiciousIp != null) {
            errorList.add(TransactionInfoEnum.IP);
        }
        if (uniqueIpList.size() > MAXIMUM_MANUAL_CORRELATION_COUNT) {
            errorList.add(TransactionInfoEnum.IP_CORRELATION);
        }
        if (uniqueRegionList.size() > MAXIMUM_MANUAL_CORRELATION_COUNT) {
            errorList.add(TransactionInfoEnum.REGION_CORRELATION);
        }
        errorList.sort(Comparator.comparing(TransactionInfoEnum::getValue));
        return errorList;
    }
}

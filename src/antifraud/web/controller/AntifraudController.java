package antifraud.web.controller;

import antifraud.service.AntifraudService;
import antifraud.web.dto.TransactionFeedbackDto;
import antifraud.web.dto.response.StatusResponse;
import antifraud.web.dto.StolenCardDto;
import antifraud.web.dto.SuspiciousIpDto;
import antifraud.web.validator.annotation.CardNumber;
import antifraud.web.validator.annotation.Ip;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class AntifraudController {
    private final AntifraudService antifraudService;

    @PostMapping("/api/antifraud/suspicious-ip")
    ResponseEntity<?> newSuspiciousIp(@Valid @RequestBody SuspiciousIpDto suspiciousIpDto) {
        return ResponseEntity.ok(this.antifraudService.newSuspiciousIP(suspiciousIpDto));
    }

    @DeleteMapping("/api/antifraud/suspicious-ip/{ip}")
    ResponseEntity<?> deleteSuspiciousIp(@Ip @PathVariable String ip) {
        this.antifraudService.deleteSuspiciousIp(ip);
        return ResponseEntity.ok(new StatusResponse("IP " + ip + " successfully removed!"));
    }

    @GetMapping("/api/antifraud/suspicious-ip")
    ResponseEntity<?> showAllSuspiciousIp() {
        return ResponseEntity.ok(this.antifraudService.getAllSuspiciousIp());
    }

    @PostMapping("/api/antifraud/stolencard")
    ResponseEntity<?> addStolenCard(@Valid @RequestBody StolenCardDto stolenCardDto) {
        return ResponseEntity.ok(this.antifraudService.addStolenCard(stolenCardDto));
    }

    @DeleteMapping("/api/antifraud/stolencard/{number}")
    ResponseEntity<?> deleteStolenCard(@CardNumber @PathVariable String number) {
        this.antifraudService.deleteStolenCard(number);
        return ResponseEntity.ok(
                new StatusResponse("Card " + number + " successfully removed!")
        );
    }

    @GetMapping("/api/antifraud/stolencard")
    ResponseEntity<?> showAllStolenCard() {
        return ResponseEntity.ok(
                this.antifraudService.getAllStolenCard()
        );
    }

    @GetMapping("/api/antifraud/history")
    ResponseEntity<?> showAllHistory() {
        return ResponseEntity.ok(this.antifraudService.getAllTransaction());
    }

    @GetMapping("/api/antifraud/history/{number}")
    ResponseEntity<?> showHistory(@CardNumber @PathVariable String number) {
        return ResponseEntity.ok(this.antifraudService.getAllTransactionByNumber(number));
    }

    @PutMapping("/api/antifraud/transaction")
    ResponseEntity<?> setTransactionFeedback(@RequestBody TransactionFeedbackDto transactionFeedbackDto) {
        return ResponseEntity.ok(this.antifraudService.setTransactionFeedback(transactionFeedbackDto));
    }
}

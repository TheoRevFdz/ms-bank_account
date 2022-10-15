package com.nttdata.bootcamp.msbank_account.application;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.msbank_account.interfaces.IBankAccountService;
import com.nttdata.bootcamp.msbank_account.model.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BankAccountController {
    @Autowired
    private IBankAccountService service;

    final String endPoint = "bank_accounts";

    @PostMapping(endPoint)
    public ResponseEntity<?> createBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            final Mono<BankAccount> baccountMono = Mono.just(bankAccount);
            final Mono<BankAccount> response = service.createBankAccount(baccountMono);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        }
    }

    @GetMapping(endPoint)
    public ResponseEntity<?> findAllBankAccounts() {
        try {
            final Flux<BankAccount> response = service.findAllBankAccount();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al obtener las cuentas bancarias."));
        }
    }

    @PutMapping(endPoint)
    public ResponseEntity<?> updateBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            if (bankAccount != null && bankAccount.getId() != null) {
                Mono<BankAccount> response = service.updateBankAccount(bankAccount);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "La cuenta que intenta actualizar no existe."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error en servidor al actualizar la cuenta bancaria."));
        }
    }
}

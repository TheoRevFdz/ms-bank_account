package com.nttdata.bootcamp.msbank_account.application;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.msbank_account.dto.CustomerDTO;
import com.nttdata.bootcamp.msbank_account.interfaces.IBankAccountService;
import com.nttdata.bootcamp.msbank_account.interfaces.ICustomerService;
import com.nttdata.bootcamp.msbank_account.model.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BankAccountController {
    @Autowired
    private IBankAccountService service;

    @Autowired
    private ICustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            Optional<CustomerDTO> verifCustomer = customerService.findCustomerByNroDoc(bankAccount.getNroDoc());

            if (verifCustomer.isPresent()) {
                final Mono<BankAccount> baccountMono = Mono.just(bankAccount);
                final Mono<BankAccount> response = service.createBankAccount(baccountMono);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message",
                            "No se encontró cliente con número de documento: " +
                                    bankAccount.getNroDoc()));
        // } catch (FeignException ex) {
        //     if (ex.status() == HttpStatus.NOT_FOUND.value()) {
        //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
        //                 .body(Collections.singletonMap("message",
        //                         "No se encontró cliente con número de documento: " + bankAccount.getNroDoc()));
        //     }
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        //             .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        } catch (Exception e) {
            // if(e.responseS)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllBankAccounts() {
        try {
            final Flux<BankAccount> response = service.findAllBankAccount();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al obtener las cuentas bancarias."));
        }
    }

    @PutMapping
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

    @GetMapping("/byNroDoc/{nroDoc}")
    public ResponseEntity<?> findAccountByNroDoc(@PathVariable String nroDoc) {
        try {
            final Mono<BankAccount> response = service.findAccountByNroDoc(nroDoc);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message",
                            "Error en servidor al obetener la cuenta bancaria del cliente por número de documento."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBankAccount(@PathVariable String id) {
        try {
            final boolean resp = service.deleteBankAccount(id);
            if (resp) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "No se encontró la cuenta que intenta eliminar."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error en servidor al eliminar la cuenta bancaria."));
        }
    }
}

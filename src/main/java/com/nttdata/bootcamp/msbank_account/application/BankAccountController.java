package com.nttdata.bootcamp.msbank_account.application;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.web.client.HttpClientErrorException;

import com.nttdata.bootcamp.msbank_account.dto.CustomerDTO;
import com.nttdata.bootcamp.msbank_account.enums.CustomerTypes;
import com.nttdata.bootcamp.msbank_account.interfaces.IBankAccountService;
import com.nttdata.bootcamp.msbank_account.interfaces.ICustomerService;
import com.nttdata.bootcamp.msbank_account.model.BankAccount;
import com.nttdata.bootcamp.msbank_account.util.ValidatorUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BankAccountController {
    @Autowired
    private IBankAccountService service;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @PostMapping
    public ResponseEntity<?> createBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            Optional<CustomerDTO> existCustomer = customerService.findCustomerByNroDoc(bankAccount.getNroDoc());

            if (existCustomer.isPresent()) {
                String uniqNroAccount = String.format("%040d",
                        new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
                CustomerDTO dto = existCustomer.get();
                bankAccount.setNroAccount(uniqNroAccount);
                final Mono<BankAccount> baccountMono = Mono.just(bankAccount);

                if (dto.getTypePerson().equals(CustomerTypes.PERSONAL.toString())) {
                    ResponseEntity<?> valid = validatorUtil.validatePersonalAccount(bankAccount);
                    return saveAccount(valid, baccountMono);
                } else {
                    ResponseEntity<?> valid = validatorUtil.validateEmpresarialAccount(bankAccount);
                    return saveAccount(valid, baccountMono);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message",
                            "No se encontró cliente con número de documento: " +
                                    bankAccount.getNroDoc()));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message",
                                "No se encontró cliente con número de documento: " +
                                        bankAccount.getNroDoc()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        }
    }

    private ResponseEntity<?> saveAccount(ResponseEntity<?> valid, Mono<BankAccount> baccountMono) {
        if (valid.getStatusCodeValue() == HttpStatus.OK.value()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createBankAccount(baccountMono));
        }
        return valid;
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
            final List<BankAccount> response = service.findAccountByNroDoc(nroDoc);
            Flux<BankAccount> result = Flux.fromIterable(response);
            return ResponseEntity.ok(result);
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

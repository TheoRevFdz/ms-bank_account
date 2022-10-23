package com.nttdata.bootcamp.msaccounts.application;

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

import com.nttdata.bootcamp.msaccounts.dto.CustomerDTO;
import com.nttdata.bootcamp.msaccounts.enums.CustomerTypes;
import com.nttdata.bootcamp.msaccounts.interfaces.IAccountService;
import com.nttdata.bootcamp.msaccounts.interfaces.ICustomerService;
import com.nttdata.bootcamp.msaccounts.model.Account;
import com.nttdata.bootcamp.msaccounts.util.ValidatorUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {
    @Autowired
    private IAccountService service;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Optional<CustomerDTO> existCustomer = customerService.findCustomerByNroDoc(account.getNroDoc());

            if (existCustomer.isPresent()) {
                String uniqNroAccount = String.format("%040d",
                        new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
                CustomerDTO dto = existCustomer.get();
                account.setNroAccount(uniqNroAccount);
                UUID uid = UUID.randomUUID();
                account.setNroInterbakaryAccount(uid.toString());
                final Mono<Account> baccountMono = Mono.just(account);

                if (dto.getTypePerson().equals(CustomerTypes.PERSONAL.toString())) {
                    ResponseEntity<?> valid = validatorUtil.validatePersonalAccount(account);
                    return saveAccount(valid, baccountMono);
                } else {
                    ResponseEntity<?> valid = validatorUtil.validateEmpresarialAccount(account);
                    return saveAccount(valid, baccountMono);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message",
                            "No se encontró cliente con número de documento: " +
                                    account.getNroDoc()));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message",
                                "No se encontró cliente con número de documento: " +
                                        account.getNroDoc()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        }
    }

    private ResponseEntity<?> saveAccount(ResponseEntity<?> valid, Mono<Account> baccountMono) {
        if (valid.getStatusCodeValue() == HttpStatus.OK.value()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(baccountMono));
        }
        return valid;
    }

    @GetMapping
    public ResponseEntity<?> findAllAccounts() {
        try {
            final Flux<Account> response = service.findAllAccount();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al obtener las cuentas bancarias."));
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAccount(@RequestBody Account account) {
        try {
            if (account.getNroInterbakaryAccount() == null || account.getNroInterbakaryAccount().isEmpty()) {
                UUID uid = UUID.randomUUID();
                account.setNroInterbakaryAccount(uid.toString());
            }
            
            if (account != null && account.getId() != null) {
                Mono<Account> response = service.updateAccount(account);
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
            final List<Account> response = service.findAccountByNroDoc(nroDoc);
            Flux<Account> result = Flux.fromIterable(response);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message",
                            "Error en servidor al obetener la cuenta bancaria del cliente por número de documento."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id) {
        try {
            final boolean resp = service.deleteAccount(id);
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

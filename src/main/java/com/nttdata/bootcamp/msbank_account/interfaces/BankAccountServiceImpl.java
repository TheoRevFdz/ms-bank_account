package com.nttdata.bootcamp.msbank_account.interfaces;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msbank_account.infraestructure.IBankAccountRepository;
import com.nttdata.bootcamp.msbank_account.model.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements IBankAccountService {
    @Autowired
    @Qualifier("IBankAccountRepository")
    private IBankAccountRepository repository;

    @Override
    public Mono<BankAccount> createBankAccount(Mono<BankAccount> bankAccount) {
        return bankAccount.flatMap(repository::insert);
    }

    @Override
    public Flux<BankAccount> findAllBankAccount() {
        return repository.findAll().delayElements(Duration.ofSeconds(1)).log();
    }

    @Override
    public Mono<BankAccount> updateBankAccount(BankAccount bankAccount) {
        return repository.findById(bankAccount.getId())
                .map(ba -> bankAccount)
                .flatMap(repository::save);
    }

    @Override
    public boolean deleteBankAccount(String id) {
        Mono<BankAccount> baExist = repository.findById(id);
        if (baExist.block() != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Flux<BankAccount> findAccountByNroDoc(String nroDoc) {
        return repository.findByNroDoc(nroDoc);
    }

}

package com.nttdata.bootcamp.msbank_account.interfaces;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msbank_account.infraestructure.IBankAccountReactiveRepository;
import com.nttdata.bootcamp.msbank_account.infraestructure.IBankAccountRepository;
import com.nttdata.bootcamp.msbank_account.model.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements IBankAccountService {
    @Autowired
    private IBankAccountReactiveRepository reactiveRepo;
    @Autowired
    private IBankAccountRepository repository;

    @Override
    public Mono<BankAccount> createBankAccount(Mono<BankAccount> bankAccount) {
        return bankAccount.flatMap(reactiveRepo::insert);
    }

    @Override
    public Flux<BankAccount> findAllBankAccount() {
        return reactiveRepo.findAll().delayElements(Duration.ofSeconds(1)).log();
    }

    @Override
    public Mono<BankAccount> updateBankAccount(BankAccount bankAccount) {
        return reactiveRepo.findById(bankAccount.getId())
                .map(ba -> bankAccount)
                .flatMap(reactiveRepo::save);
    }

    @Override
    public boolean deleteBankAccount(String id) {
        Mono<BankAccount> baExist = reactiveRepo.findById(id);
        if (baExist.block() != null) {
            reactiveRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<BankAccount> findAccountByNroDoc(String nroDoc) {
        return repository.findByNroDoc(nroDoc);
    }

    @Override
    public List<BankAccount> findAccountByNroDocAndTypeAccount(String nroDoc, String typeAccount) {
        return repository.findByNroDocAndTypeAccount(nroDoc, typeAccount);
    }

}

package com.nttdata.bootcamp.msaccounts.interfaces;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msaccounts.infraestructure.IAccountReactiveRepository;
import com.nttdata.bootcamp.msaccounts.infraestructure.IAccountRepository;
import com.nttdata.bootcamp.msaccounts.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private IAccountReactiveRepository reactiveRepo;
    @Autowired
    private IAccountRepository repository;

    @Override
    public Mono<Account> createAccount(Mono<Account> account) {
        return account.flatMap(reactiveRepo::insert);
    }

    @Override
    public Flux<Account> findAllAccount() {
        return reactiveRepo.findAll().delayElements(Duration.ofSeconds(1)).log();
    }

    @Override
    public Mono<Account> updateAccount(Account account) {
        return reactiveRepo.findById(account.getId())
                .map(ba -> account)
                .flatMap(reactiveRepo::save);
    }

    @Override
    public boolean deleteAccount(String id) {
        Mono<Account> baExist = reactiveRepo.findById(id);
        if (baExist.block() != null) {
            reactiveRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Account> findAccountByNroDoc(String nroDoc) {
        return repository.findByNroDoc(nroDoc);
    }

    @Override
    public List<Account> findAccountByNroDocAndTypeAccount(String nroDoc, String typeAccount) {
        return repository.findByNroDocAndTypeAccount(nroDoc, typeAccount);
    }

}

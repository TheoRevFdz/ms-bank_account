package com.nttdata.bootcamp.msaccounts.interfaces;

import java.util.List;

import com.nttdata.bootcamp.msaccounts.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {
    public Mono<Account> createAccount(Mono<Account> account);

    public Flux<Account> findAllAccount();

    public Mono<Account> updateAccount(Account account);

    public boolean deleteAccount(String id);

    public List<Account> findAccountByNroDoc(String nroDoc);

    public List<Account> findAccountByNroDocAndTypeAccount(String nroDoc, String typeAccount);
}

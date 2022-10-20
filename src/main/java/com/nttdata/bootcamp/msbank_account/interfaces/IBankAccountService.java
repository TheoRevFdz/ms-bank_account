package com.nttdata.bootcamp.msbank_account.interfaces;

import com.nttdata.bootcamp.msbank_account.model.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {
    public Mono<BankAccount> createBankAccount(Mono<BankAccount> bankAccount);

    public Flux<BankAccount> findAllBankAccount();

    public Mono<BankAccount> updateBankAccount(BankAccount bankAccount);

    public boolean deleteBankAccount(String id);

    public Flux<BankAccount> findAccountByNroDoc(String nroDoc);
}

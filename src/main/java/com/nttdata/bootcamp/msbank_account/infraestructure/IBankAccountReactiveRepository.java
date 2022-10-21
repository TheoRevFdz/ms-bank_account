package com.nttdata.bootcamp.msbank_account.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msbank_account.model.BankAccount;

import reactor.core.publisher.Flux;

@Repository
public interface IBankAccountReactiveRepository extends ReactiveMongoRepository<BankAccount, String> {
    public Flux<BankAccount> findByNroDoc(String nroDoc);
}
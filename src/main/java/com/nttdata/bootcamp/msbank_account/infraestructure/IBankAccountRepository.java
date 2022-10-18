package com.nttdata.bootcamp.msbank_account.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msbank_account.model.BankAccount;

import reactor.core.publisher.Mono;

@Repository("IBankAccountRepository")
public interface IBankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {
    public Mono<BankAccount> findByNroDoc(String nroDoc);
}

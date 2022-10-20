package com.nttdata.bootcamp.msbank_account.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msbank_account.model.BankAccount;

import reactor.core.publisher.Flux;

@Repository("IBankAccountRepository")
public interface IBankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {
    public Flux<BankAccount> findByNroDoc(String nroDoc);
    // public Mono<Optional<BankAccount>> findBy
}

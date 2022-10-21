package com.nttdata.bootcamp.msbank_account.infraestructure;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msbank_account.model.BankAccount;

@Repository("IBankAccountRepository")
public interface IBankAccountRepository extends MongoRepository<BankAccount, String> {
    public List<BankAccount> findByNroDoc(String nroDoc);

    public List<BankAccount> findByNroDocAndTypeAccount(String nroDoc, String typeAccount);
}

package com.nttdata.bootcamp.msaccounts.infraestructure;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msaccounts.model.Account;

@Repository("IAccountRepository")
public interface IAccountRepository extends MongoRepository<Account, String> {
    public List<Account> findByNroDoc(String nroDoc);

    public List<Account> findByNroDocAndTypeAccount(String nroDoc, String typeAccount);
}

package com.nttdata.bootcamp.msbank_account.interfaces;

import java.text.ParseException;
import java.util.Optional;

import com.nttdata.bootcamp.msbank_account.dto.CustomerDTO;

public interface ICustomerService {
    public Optional<CustomerDTO> findCustomerByNroDoc(String nroDoc) throws ParseException;
}

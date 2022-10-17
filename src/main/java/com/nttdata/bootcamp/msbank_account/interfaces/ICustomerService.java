package com.nttdata.bootcamp.msbank_account.interfaces;

import java.text.ParseException;

import com.nttdata.bootcamp.msbank_account.dto.CustomerDTO;

public interface ICustomerService {
    public CustomerDTO findCustomerByNroDoc(String nroDoc) throws ParseException;
}

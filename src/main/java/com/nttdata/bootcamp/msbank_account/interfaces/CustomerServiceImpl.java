package com.nttdata.bootcamp.msbank_account.interfaces;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msbank_account.clients.ICustomerClientRest;
import com.nttdata.bootcamp.msbank_account.dto.CustomerDTO;

@Service("ICustomerService")
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerClientRest clientCustomer;

    @Override
    public CustomerDTO findCustomerByNroDoc(String nroDoc) throws ParseException {
        CustomerDTO dto = clientCustomer.findCustomerByNroDoc(nroDoc);
        return dto;
    }

}

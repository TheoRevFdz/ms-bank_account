package com.nttdata.bootcamp.msbank_account.interfaces;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msbank_account.dto.CustomerDTO;

@Service
public class CustomerServiceImpl implements ICustomerService {
    // @Autowired
    // private ICustomerClientRest clientCustomer;

    @Override
    public Optional<CustomerDTO> findCustomerByNroDoc(String nroDoc) throws ParseException {
        // Optional<CustomerDTO> dto = clientCustomer.findCustomerByNroDoc(nroDoc);
        // return dto;
        return null;
    }

}

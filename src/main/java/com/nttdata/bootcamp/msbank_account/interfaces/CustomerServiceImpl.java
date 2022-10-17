package com.nttdata.bootcamp.msbank_account.interfaces;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msbank_account.clients.ICustomerClientRest;
import com.nttdata.bootcamp.msbank_account.dto.CustomerDTO;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerClientRest clientCustomer;

    @Override
    public CustomerDTO findCustomerByNroDoc(String nroDoc) throws ParseException {
        Map<String, Object> res = clientCustomer.findCustomerByNroDoc(nroDoc);
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy");
        CustomerDTO dto = CustomerDTO.builder()
                .id(res.get("id").toString())
                .firstName(res.get("firstName").toString())
                .lastName(res.get("lastName").toString())
                .typeDoc(res.get("typeDoc").toString())
                .nroDoc(res.get("nroDoc").toString())
                .phone(res.get("phone").toString())
                .email(res.get("email").toString())
                .typePerson(res.get("typePerson").toString())
                .typeProduct(res.get("typeProduct").toString())
                .regDate(format.parse(res.get("regDate").toString()))
                .build();
        return dto;
    }

}

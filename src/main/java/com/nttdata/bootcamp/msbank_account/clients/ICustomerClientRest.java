package com.nttdata.bootcamp.msbank_account.clients;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.bootcamp.msbank_account.dto.CustomerDTO;

// @FeignClient(name = "ms-customers", url = "localhost:8081")
public interface ICustomerClientRest {
    @GetMapping(value = "/byNroDoc/{nroDoc}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Optional<CustomerDTO> findCustomerByNroDoc(@PathVariable String nroDoc);
}

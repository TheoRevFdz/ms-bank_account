package com.nttdata.bootcamp.msbank_account.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.bootcamp.msbank_account.config.FeignClientConfig;
import com.nttdata.bootcamp.msbank_account.dto.CustomerDTO;

@Primary
@FeignClient(name = "ms-customers", url = "localhost:8081", configuration = FeignClientConfig.class)
public interface ICustomerClientRest {
    @GetMapping(value = "customers/byNroDoc/{nroDoc}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO findCustomerByNroDoc(@PathVariable String nroDoc);
}

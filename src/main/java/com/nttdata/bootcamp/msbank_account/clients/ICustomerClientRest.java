package com.nttdata.bootcamp.msbank_account.clients;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ICustomerClientRest extends RestTemplate {

    // @GetMapping(value = "/byNroDoc/{nroDoc}", consumes =
    // MediaType.APPLICATION_JSON_VALUE)
    // public Optional<CustomerDTO> findCustomerByNroDoc(@PathVariable String
    // nroDoc);
}

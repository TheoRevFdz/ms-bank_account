package com.nttdata.bootcamp.msbank_account.clients;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-customers", url = "localhost:8081")
public interface ICustomerClientRest {
    @GetMapping("customers/byNroDoc/{nroDoc}")
    public Map<String,Object> findCustomerByNroDoc(@PathVariable String nroDoc);
}

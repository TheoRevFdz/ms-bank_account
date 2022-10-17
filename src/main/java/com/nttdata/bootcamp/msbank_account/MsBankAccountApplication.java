package com.nttdata.bootcamp.msbank_account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsBankAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBankAccountApplication.class, args);
	}

}

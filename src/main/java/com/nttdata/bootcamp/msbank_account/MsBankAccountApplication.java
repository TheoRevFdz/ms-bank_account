package com.nttdata.bootcamp.msbank_account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableFeignClients(basePackages = {
		"com.nttdata.bootcamp.msbank_account.clients"
})
@EnableMongoRepositories("com.nttdata.bootcamp.msbank_account.infraestructure")
public class MsBankAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBankAccountApplication.class, args);
	}

}

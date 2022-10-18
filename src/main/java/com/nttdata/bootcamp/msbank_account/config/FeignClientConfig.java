package com.nttdata.bootcamp.msbank_account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
// import feign.codec.Decoder;

@Configuration
public class FeignClientConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    // @Bean
    // public Decoder decoder() {
    //     return new JacksonDecoder();
    // }

    // @Bean
    // public Encoder encoder() {
    //     return new JacksonEncoder();
    // }
}

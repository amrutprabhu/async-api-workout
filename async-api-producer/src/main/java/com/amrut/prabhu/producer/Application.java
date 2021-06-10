
package com.amrut.prabhu.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.function.Supplier;


@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public Supplier<TransactionEventPayload> consumeTransactionEvent() {


        return () -> {
            TransactionEventPayload transactionEventPayload = new TransactionEventPayload();
            transactionEventPayload.setTransactionId(UUID.randomUUID().toString());
            transactionEventPayload.setAmount(1000);
            return transactionEventPayload;
        };

    }

}

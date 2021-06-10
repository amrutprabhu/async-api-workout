
package com.amrut.prabhu.consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;


@SpringBootApplication
public class ConsumerApplication {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class);
    }

    @Bean
    public Consumer<TransactionEventPayload> consumeTransactionEvent() {

        return (event) -> {
            System.out.println(event.toString());
        };
    }

}

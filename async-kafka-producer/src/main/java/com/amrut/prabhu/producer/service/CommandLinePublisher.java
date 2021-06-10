package com.amrut.prabhu.producer.service;

import com.amrut.prabhu.producer.model.TransactionEventPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class CommandLinePublisher implements CommandLineRunner {

    @Autowired
    PublisherService publisherService;

    @Override
    public void run(String... args) {
        System.out.println("******* Sending message: *******");
        TransactionEventPayload transactionEventPayload = new TransactionEventPayload();
        transactionEventPayload.setTransactionId(UUID.randomUUID().toString());
        transactionEventPayload.setAmount(1000);
        publisherService.consumeTransactionEvent((new Random()).nextInt(), transactionEventPayload);
            
        System.out.println("Message sent");
    }
}

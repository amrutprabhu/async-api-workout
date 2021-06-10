package com.amrut.prabhu.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amrut.prabhu.producer.model.TransactionEventPayload;
    

@Service
public class PublisherService {

    @Autowired
    private KafkaTemplate<Integer, Object> kafkaTemplate;
 
    /**
     * This contains events related to transactions.
     */
    public void consumeTransactionEvent(Integer key, TransactionEventPayload transactionEventPayload) {
        Message<TransactionEventPayload> message = MessageBuilder.withPayload(transactionEventPayload)
                .setHeader(KafkaHeaders.TOPIC, "banking.transaction.000")
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .build();
        kafkaTemplate.send(message);
    }
}

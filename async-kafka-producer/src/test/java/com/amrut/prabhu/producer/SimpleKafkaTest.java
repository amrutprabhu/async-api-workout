package com.amrut.prabhu.producer;

 
import com.amrut.prabhu.producer.model.TransactionEventPayload;
 
  
import com.amrut.prabhu.producer.service.PublisherService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Example of tests for kafka based on spring-kafka-test library
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleKafkaTest {
     
    private static final String CONSUMETRANSACTIONEVENT_TOPIC = "banking.transaction.000";
      
    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, false, 1, CONSUMETRANSACTIONEVENT_TOPIC);

    private static EmbeddedKafkaBroker embeddedKafkaBroker = embeddedKafka.getEmbeddedKafka();

    @DynamicPropertySource
    public static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", embeddedKafkaBroker::getBrokersAsString);
    }

    
    @Autowired
    private PublisherService publisherService;
     
    Consumer<Integer, TransactionEventPayload> consumerBankingTransaction000;
       
    @Before
    public void init() {
        
        Map<String, Object> consumerConfigs = new HashMap<>(KafkaTestUtils.consumerProps("consumer", "true", embeddedKafkaBroker));
        consumerConfigs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
         
        consumerBankingTransaction000 = new DefaultKafkaConsumerFactory<>(consumerConfigs, new IntegerDeserializer(), new JsonDeserializer<>(TransactionEventPayload.class)).createConsumer();
        consumerBankingTransaction000.subscribe(Collections.singleton(CONSUMETRANSACTIONEVENT_TOPIC));
        consumerBankingTransaction000.poll(Duration.ZERO);
           
    }
     
    @Test
    public void consumeTransactionEventProducerTest() {
        TransactionEventPayload payload = new TransactionEventPayload();
        Integer key = 1;

        KafkaTestUtils.getRecords(consumerBankingTransaction000);

        publisherService.consumeTransactionEvent(key, payload);

        ConsumerRecord<Integer, TransactionEventPayload> singleRecord = KafkaTestUtils.getSingleRecord(consumerBankingTransaction000, CONSUMETRANSACTIONEVENT_TOPIC);

        assertEquals("Key is wrong", key, singleRecord.key());
    }
         
    
}

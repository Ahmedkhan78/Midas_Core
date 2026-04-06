package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final String topic;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public KafkaProducer(@Value("${general.kafka-topic:trader-updates}") @NonNull String topic,
            @NonNull KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(@NonNull String transactionLine) {
        String[] transactionData = transactionLine.split(", ");
        Transaction transaction = new Transaction(
                Long.parseLong(transactionData[0]), // sender ID
                Long.parseLong(transactionData[1]), // recipient ID
                Float.parseFloat(transactionData[2]) // amount
        );
        kafkaTemplate.send(topic, transaction);
    }
}
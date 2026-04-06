package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    private final DatabaseConduit databaseConduit;

    public TransactionListener(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @KafkaListener(topics = "trader-updates", groupId = "midas-group")
    public void listen(Transaction transaction) {

        System.out.println("RECEIVED: " + transaction);

        databaseConduit.findUserById(transaction.getSenderId())
                .ifPresent(sender -> databaseConduit.findUserById(transaction.getRecipientId())
                        .ifPresent(recipient -> databaseConduit.processTransaction(
                                sender,
                                recipient,
                                transaction)));
    }
}
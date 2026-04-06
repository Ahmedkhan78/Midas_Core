package com.jpmc.midascore;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.foundation.User;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092"
})
public class TaskThreeTests {

    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepository;

    @Test
    void task_three_verifier() throws InterruptedException {

        // ✅ Step 1: Populate users
        userPopulator.populate();

        // ✅ Step 2: Send all transactions to Kafka
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }

        // ✅ Step 3: WAIT for Kafka to finish processing ALL messages
        Thread.sleep(3000); // 🔥 KEY FIX

        // ✅ Step 4: Fetch final state of waldorf
        User waldorf = userRepository.findByName("waldorf").orElseThrow();

        // ✅ Step 5: Print final balance (rounded down)
        int finalBalance = waldorf.getBalance().intValue();

        System.out.println("Final balance of waldorf (rounded down): " + finalBalance);
    }
}
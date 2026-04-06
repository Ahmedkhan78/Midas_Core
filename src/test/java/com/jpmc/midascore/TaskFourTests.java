package com.jpmc.midascore;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.jpmc.midascore.foundation.User;
import com.jpmc.midascore.repository.UserRepository;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class TaskFourTests {
    static final Logger logger = LoggerFactory.getLogger(TaskFourTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepository;

    @Test
    void task_four_verifier() throws InterruptedException {
        // 1️⃣ Populate users
        userPopulator.populate();

        // 2️⃣ Load transactions from file
        String[] transactionLines = fileLoader.loadStrings("/test_data/alskdjfh.fhdjsk");

        // 3️⃣ Send all transactions via Kafka
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }

        // 4️⃣ Wait for processing (adjust if needed)
        Thread.sleep(5000); // 5 seconds should be enough for small datasets

        // 5️⃣ Fetch Wilbur’s user object from repository
        User wilbur = userRepository.findByName("wilbur").orElseThrow();

        // 6️⃣ Print final balance (rounded down)
        int finalBalance = wilbur.getBalance().intValue();
        System.out.println("✅ Final balance of wilbur (rounded down): " + finalBalance);

        // Optional: log to SLF4J as well
        logger.info("Final balance of wilbur (rounded down): {}", finalBalance);
    }
}
package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.User;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class DatabaseConduit {

    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRepository;
    private final IncentiveClient incentiveClient;

    public DatabaseConduit(UserRepository userRepository,
            TransactionRecordRepository transactionRepository,
            IncentiveClient incentiveClient) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.incentiveClient = incentiveClient;
    }

    @Transactional
    public void processTransaction(@NonNull User sender,
            @NonNull User recipient,
            @NonNull Transaction transaction) {

        BigDecimal amount = new BigDecimal(String.valueOf(transaction.getAmount()));

        // ✅ VALIDATION
        if (sender.getBalance().compareTo(amount) < 0)
            return;

        // ✅ CALL INCENTIVE API
        Incentive incentiveResponse = incentiveClient.getIncentive(transaction);
        BigDecimal incentive = BigDecimal.valueOf(incentiveResponse.getAmount());

        // ✅ UPDATE BALANCES
        sender.setBalance(sender.getBalance().subtract(amount));

        recipient.setBalance(
                recipient.getBalance().add(amount).add(incentive));

        userRepository.save(sender);
        userRepository.save(recipient);

        // ✅ SAVE TRANSACTION
        TransactionRecord record = new TransactionRecord(sender, recipient, amount, incentive);

        transactionRepository.save(record);
    }

    public Optional<User> findUserById(@NonNull Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void saveUser(@NonNull User user) {
        userRepository.save(user);
    }
}
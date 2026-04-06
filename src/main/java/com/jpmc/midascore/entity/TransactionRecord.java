package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.jpmc.midascore.foundation.User;

@Entity
@Table(name = "transactions")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal incentive;

    public TransactionRecord() {
    }

    public TransactionRecord(User sender, User recipient, BigDecimal amount, BigDecimal incentive) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = incentive;
    }

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getIncentive() {
        return incentive;
    }

    public void setIncentive(BigDecimal incentive) {
        this.incentive = incentive;
    }
}
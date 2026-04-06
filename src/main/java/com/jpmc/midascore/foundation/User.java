package com.jpmc.midascore.foundation;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import com.jpmc.midascore.entity.TransactionRecord;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    // One user can send many transactions
    @OneToMany(mappedBy = "sender")
    private List<TransactionRecord> sentTransactions;

    // One user can receive many transactions
    @OneToMany(mappedBy = "recipient")
    private List<TransactionRecord> receivedTransactions;

    // Constructors
    public User() {
    }

    public User(Long id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.foundation.User;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class BalanceController {

    private final UserRepository userRepository;

    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET /balance?userId=1
    @GetMapping("/balance")
    public Balance getBalance(@RequestParam("userId") Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new Balance(user.getBalance().floatValue());
        } else {
            return new Balance(0.0f);
        }
    }
}
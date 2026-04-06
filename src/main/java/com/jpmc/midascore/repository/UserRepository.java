package com.jpmc.midascore.repository;

import com.jpmc.midascore.foundation.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
}
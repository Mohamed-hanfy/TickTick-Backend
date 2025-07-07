package com.ticktick.repository;

import com.ticktick.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByEmail(String email);
}

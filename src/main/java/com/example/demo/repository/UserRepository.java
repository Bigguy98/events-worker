package com.example.demo.repository;

import com.example.demo.repository.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE u.walletId = ?1")
    User findByWalletId(String walletId);

}

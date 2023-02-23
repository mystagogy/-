package com.miniproject.backend.user.repository;

import com.miniproject.backend.user.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, String> {

    Boolean existsByToken(String token);

    RefreshToken findByToken(String token);
}

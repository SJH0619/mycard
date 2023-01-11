package com.mycard.demo.subscrib;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SubscribRepository extends JpaRepository<Subscrib, Long>{
    Optional<Set<Subscrib>> findBySubUserId(String subUserId);
    Optional<Subscrib> findBySubUserIdAndPubUserId(String subUserId, String pubUserId);
    @Transactional
    void deleteBySubUserIdAndPubUserId(String subUserId, String pubUserId);
}

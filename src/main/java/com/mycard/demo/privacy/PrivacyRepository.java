package com.mycard.demo.privacy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycard.demo.user.User;

public interface PrivacyRepository extends JpaRepository<Privacy, Long>{
    Privacy findByUser(User user);
}

package com.mycard.demo.user;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;
import com.mycard.demo.privacy.Privacy;
import com.mycard.demo.privacy.PrivacyRepository;
import com.mycard.demo.qrcode.QrCodeProcess;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PrivacyRepository privacyRepository;
    private final PasswordEncoder passwordEncoder;
    private final QrCodeProcess qrCodeProcess;

    public UserService(UserRepository userRepository, PrivacyRepository privacyRepository,PasswordEncoder passwordEncoder, QrCodeProcess qrCodeProcess) {
        this.userRepository = userRepository;
        this.privacyRepository = privacyRepository;
        this.passwordEncoder = passwordEncoder;
        this.qrCodeProcess = qrCodeProcess;
    }

    public User createUser(String userId, String userPwd) {
        User user = User.builder()
            .userId(userId)
            .userPwd(passwordEncoder.encode(userPwd))
            .firstAccess(true)
            .build();

        this.userRepository.save(user);

        Privacy privacy = Privacy.builder()
                .user(user)
                .build();

        this.privacyRepository.save(privacy);

        try {
            this.qrCodeProcess.createQrCode(userId);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        
        return user;
    }

    public Boolean checkFirstAccess(String userId) {
        User user = this.userRepository.findByUserId(userId).get();

        return user.getFirstAccess();
    }

    public void changeFirstAccess(String userId) {
        User user = this.userRepository.findByUserId(userId).get();

        user.changeFirstAccess();
        this.userRepository.save(user);
    }

    public Boolean checkUserExist(String userId) {
        Optional<User> checkUserExist = this.userRepository.findByUserId(userId);

        return checkUserExist.isPresent();
    }
}

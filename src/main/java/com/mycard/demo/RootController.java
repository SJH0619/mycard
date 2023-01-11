package com.mycard.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycard.demo.privacy.PrivacyService;
import com.mycard.demo.user.UserService;

@Controller
public class RootController {
    private final UserService userService;
    private final PrivacyService privacyService;

    public RootController(UserService userService, PrivacyService privacyService) {
        this.userService = userService;
        this.privacyService = privacyService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/mycard";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mycard")
    public String getMyCard(Principal principal, Model model) {
        if (this.userService.checkFirstAccess(principal.getName())) {
            return "redirect:/setinfo";
        } else {
            model.addAttribute("privacy", this.privacyService.getPrivacy(principal.getName()));
            return "/mycard";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getimage")
    @Transactional
    public ResponseEntity<byte[]> getMyImage(
            @RequestParam(name = "type", required = true) String type,
            @RequestParam(name = "userId", required = false) String userId,
            Principal principal) {
        ResponseEntity<byte[]> result = null;
        File file;

        if (userId == null) {
            file = new File("C:\\upload\\" + principal.getName() + type);
        } else {
            file = new File("C:\\upload\\" + userId + type);
        }

        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}

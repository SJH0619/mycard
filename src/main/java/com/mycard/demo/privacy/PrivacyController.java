package com.mycard.demo.privacy;

import java.io.IOException;
import java.security.Principal;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mycard.demo.user.UserService;

@Controller
public class PrivacyController {
    private final PrivacyService privacyService;
    private final UserService userService;

    public PrivacyController(PrivacyService privacyService, UserService userService) {
        this.privacyService = privacyService;
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/setinfo")
    public String getPrivacy(Principal principal, Model model) {
        model.addAttribute("privacy", this.privacyService.getPrivacy(principal.getName()));
        return "/setinfo";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/saveprivacy")
    @ResponseStatus
    public ResponseEntity<?> savePrivacy(@RequestBody @Valid PrivacyUpdateForm privacyUpdateForm, Principal principal) throws IOException {
        String userId = principal.getName();

        try {
            this.privacyService.updatePrivacy(privacyUpdateForm, userId);
        } catch (IOException e) {
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (this.userService.checkFirstAccess(userId)) {
            this.userService.changeFirstAccess(userId);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

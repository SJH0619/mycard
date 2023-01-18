package com.mycard.demo.subscrib;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mycard.demo.user.UserService;

@Controller
public class SubscribController {
    private final SubscribService subscribService;
    private final UserService userService;

    public SubscribController(SubscribService subscribService, UserService userService) {
        this.subscribService = subscribService;
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wallet")
    public String getMyWallet(Model model, Principal principal) {
        List<SubscribDTO> subscribDTOs = this.subscribService.getMyWallet(principal.getName());
        model.addAttribute("subscribs", subscribDTOs);

        if (this.userService.checkFirstAccess(principal.getName())) {
            return "redirect:/setinfo";
        } else {
            return "wallet";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/addbusinesscard/{pubUserId}")
    public String addBusinessCard(@PathVariable("pubUserId") String pubUserId, Principal principal) {
        Boolean checkPubUserExist = this.userService.checkUserExist(pubUserId);
        this.subscribService.addBusinessCard(principal.getName(), pubUserId, checkPubUserExist);

        return "redirect:wallet";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/removebusinesscard/{pubUserId}")
    public String removeBusinessCard(@PathVariable("pubUserId") String pubUserId, Principal principal) {
        this.subscribService.removeBusinessCard(principal.getName(), pubUserId);

        return "redirect:wallet";
    }
}

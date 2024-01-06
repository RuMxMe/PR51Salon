package com.example.pr51salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.AuthenticationException;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public String loginForm() {
        return "register/login";
    }

    @PostMapping
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            if (authentication.isAuthenticated()) {
                return "register/users";
            } else {
                model.addAttribute("error");
                return "register/login";
            }
        } catch (AuthenticationException e) {
            model.addAttribute("error");
            return "register/login";
        }
    }
}

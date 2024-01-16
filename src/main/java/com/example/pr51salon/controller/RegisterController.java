package com.example.pr51salon.controller;

import java.util.List;

import com.example.pr51salon.model.User;
import com.example.pr51salon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/process_register")
    public String processRegister(User user, Model model) {
        user.setRole("USER");
        User existingEmailUser = userRepo.findByEmail(user.getEmail());
        User existingPhoneNumberUser = userRepo.findByPhoneNumber(user.getPhoneNumber());

        if (existingEmailUser != null) {
            model.addAttribute("emailError", "Email đã tồn tại");
            return "/register/signup_form";
        }

        if (existingPhoneNumberUser != null) {
            model.addAttribute("phoneError", "Số điện thoại đã tồn tại");
            return "/register/signup_form";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "/register/register_success";
    }


    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "/register/users";
    }
}

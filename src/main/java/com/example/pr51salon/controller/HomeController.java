package com.example.pr51salon.controller;

import com.example.pr51salon.model.ServiceItem;
import com.example.pr51salon.model.ServiceItemDetail;
import com.example.pr51salon.model.User;
import com.example.pr51salon.repository.ServiceItemDetailRepository;
import com.example.pr51salon.repository.ServiceItemRepository;
import com.example.pr51salon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ServiceItemDetailRepository serviceItemDetailRepository;

    @Autowired
    private ServiceItemRepository serviceItemRepository;


    @GetMapping("/home")
    public String home(Model model) {

        List<ServiceItemDetail> services = serviceItemDetailRepository.findAll();
        model.addAttribute("services", services);

        List<ServiceItem> serviceItems = serviceItemRepository.findAll();
        model.addAttribute("serviceitem", serviceItems);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = userRepository.findByEmail(authentication.getName());
            model.addAttribute("user", user);
        }
        return "viewhomepage";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "/register/signup_form";
    }

    @GetMapping("/haircut")
    public String showHaircut(Model model) {

        List<ServiceItemDetail> services = serviceItemDetailRepository.findAll();
        model.addAttribute("services", services);
        return "/cattoc";
    }

    @GetMapping("/curl-hair")
    public String showCurlhair() {
        return "/uondiahinh";
    }

    @GetMapping("/massage")
    public String showMassage() {
        return "/goimassage";
    }

    @GetMapping("/lay-ray-tai")
    public String showClean() {
        return "/layraytai";
    }


    @GetMapping("/change-hair-color")
    public String showChangehaircolor() {
        return "/thaydoimautoc";
    }

    @GetMapping("/login-form")
    public String showLoginform() {
        return "/register/login";
    }

    @GetMapping("/")
    public String showUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);

            if (user != null) {
                model.addAttribute("user", user);
            } else {
                System.out.println("hello");
            }
        }

        return "fragment/header";
    }
}





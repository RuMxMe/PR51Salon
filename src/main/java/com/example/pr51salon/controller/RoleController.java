package com.example.pr51salon.controller;

import com.example.pr51salon.model.User;
import com.example.pr51salon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/assign_role")
    public String assignRole(@RequestParam Long userId, @RequestParam String role, Model model) {
        User user = userRepo.findById(userId).orElse(null);

        if (user == null) {
            model.addAttribute("error", "Không tìm thấy người dùng");
        } else if ("ADMIN".equals(role)) {
            model.addAttribute("error", "Bạn không có quyền gán vai trò ADMIN");
        } else {
            user.setRole(role);
            userRepo.save(user);
            model.addAttribute("success", true);
            model.addAttribute("successMessage", "Vai trò đã được gán thành công cho người dùng với ID " + userId);
        }

        return "/role/assign_role";
    }
}

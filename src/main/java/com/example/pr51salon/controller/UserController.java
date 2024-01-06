package com.example.pr51salon.controller;

import com.example.pr51salon.model.User;
import com.example.pr51salon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            model.addAttribute("user", user);
            model.addAttribute("firstName", user.getFirstName());

            return "user/user-profile";
        } else {
            return "redirect:/login-form";
        }
    }

    @PostMapping("/profile/upload-avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile avatarFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username);

        if (!this.isImageFile(avatarFile.getOriginalFilename())) {

            RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
            redirectAttributes.addFlashAttribute("errors", "File không phù hợp");
            return "redirect:/profile";
        }


        if (user.getAvatarPath() != null) {
            backupOldAvatar(user.getAvatarPath(), user);
        }

        String originalFilename = avatarFile.getOriginalFilename();

        String avatarPath = uploadOrOverrideAvatar(avatarFile, user.getId());

        user.setAvatarPath(avatarPath);
        userRepository.save(user);

        return "redirect:/profile?success=true";

    }


    private void backupOldAvatar(String oldAvatarPath, User user) throws IOException {
        String oldFileName = Paths.get(oldAvatarPath).getFileName().toString();

        String backupFileName = "backup_" + user.getFirstName() + "_" + user.getLastName() + "_" + user.getId() + "_" + oldFileName;

        Path sourcePath = Paths.get("src/main/resources/static/" + oldAvatarPath);
        Path destinationPath = Paths.get("src/main/resources/static/image/backup_avt/" + backupFileName);
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }


    private String uploadOrOverrideAvatar(MultipartFile avatarFile, Long userId) throws IOException {
        String originalFilename = avatarFile.getOriginalFilename();


        String avatarPath = "image/user_avt/" + userId + "_avatar.jpg";
        byte[] bytes = avatarFile.getBytes();
        Path path = Paths.get("src/main/resources/static/" + avatarPath);
        Files.write(path, bytes);

        return avatarPath;
    }

    private boolean isImageFile(String fileName) {
        String fileExtension = FilenameUtils.getExtension(fileName);
        return fileExtension != null &&
                (fileExtension.equalsIgnoreCase("png") ||
                        fileExtension.equalsIgnoreCase("gif") ||
                        fileExtension.equalsIgnoreCase("jpeg") ||
                        fileExtension.equalsIgnoreCase("jpg"));
    }

}

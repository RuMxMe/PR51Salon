package com.example.pr51salon.controller;

import com.example.pr51salon.model.ServiceItem;
import com.example.pr51salon.repository.ServiceItemRepository;
import com.example.pr51salon.service.CustomResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/service-item")
public class ServiceItemController {

    @Autowired
    private ServiceItemRepository serviceItemRepository;

    @GetMapping
    public String getAllServiceItems(Model model) {
        List<ServiceItem> serviceItems = serviceItemRepository.findAll();
        model.addAttribute("seviceitem", serviceItems);
        return "/services/service_item";
    }

    @GetMapping("/{id}")
    public String getServiceItemById(@PathVariable Long id, Model model) {
        Optional<ServiceItem> serviceItem = serviceItemRepository.findById(id);
        serviceItem.ifPresent(item -> model.addAttribute("serviceItem", item));
        return "/services/service_item_detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        ServiceItem serviceItem = new ServiceItem();
        model.addAttribute("serviceItem", serviceItem);

        return "/services/new-service-item";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(ServiceItem serviceItem, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        serviceItem.setServiceImage("/image/home/" + fileName);
        ServiceItem serviceitem = serviceItemRepository.save(serviceItem);

        String uploadDir = "image/home/" + fileName;

        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get("src/main/resources/static/" + uploadDir);
        Files.write(path, bytes);
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/service-item";
    }

    public static class FileUploadUtil {

        public static void saveFile(String uploadDir, String fileName,
                                    MultipartFile multipartFile) throws IOException {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String editServiceItemForm(@PathVariable Long id, Model model) {
        ServiceItem serviceItem = serviceItemRepository.findById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Service item not found"));
        model.addAttribute("serviceItem", serviceItem);
        return "/services/edit_service_item";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public String updateServiceItem(@PathVariable Long id, @ModelAttribute ServiceItem serviceItem) {
        serviceItemRepository.save(serviceItem);
        return "redirect:/service-item";
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/delete/{id}")
    public String deleteServiceItem(@PathVariable(name = "id") Long id) {
        ServiceItem serviceItem = serviceItemRepository.findById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Service item not found"));
        if (serviceItem != null) {
            serviceItemRepository.delete(serviceItem);
            return "redirect:/service-item";
        } else {
            return "/services/service_item";
        }
    }
}

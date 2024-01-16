package com.example.pr51salon.controller;

import com.example.pr51salon.model.ServiceItem;
import com.example.pr51salon.repository.ServiceItemRepository;
import com.example.pr51salon.service.CustomResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/service-item")
public class ServiceItemController {

    @Autowired
    private ServiceItemRepository serviceItemRepository;

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String getAllServiceItems(Model model) {
        List<ServiceItem> serviceItems = serviceItemRepository.findAll();
        model.addAttribute("seviceitem", serviceItems);
        return "/services/service_item";
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String editServiceItemForm(@PathVariable Long id, Model model) {
        ServiceItem serviceitem = serviceItemRepository.findById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Không tìm thấy dịch vụ"));
        model.addAttribute("serviceitem", serviceitem);
        return "services/edit_service_item";
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/edit")
    public String updateServiceItem(@PathVariable Long id, @ModelAttribute ServiceItem serviceItem, RedirectAttributes redirectAttributes) {
        if (!serviceItemRepository.existsById(id)) {
            throw new CustomResourceNotFoundException("Không tìm thấy dịch vụ");
        }

        ServiceItem ServiceItem = serviceItemRepository.findById(id).orElseThrow();
        ServiceItem.setServiceName(serviceItem.getServiceName());
        ServiceItem.setServiceImage(serviceItem.getServiceImage());

        serviceItemRepository.save(ServiceItem);
        return "redirect:/service-item";
    }
}
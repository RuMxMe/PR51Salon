package com.example.pr51salon.controller;

import com.example.pr51salon.model.ServiceItem;
import com.example.pr51salon.repository.ServiceItemRepository;
import com.example.pr51salon.service.CustomResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    //    @GetMapping("/{id}")
//    public String getServiceItemById(@PathVariable Long id, Model model) {
//        Optional<ServiceItem> serviceItem = serviceItemRepository.findById(id);
//        serviceItem.ifPresent(item -> model.addAttribute("serviceItem", item));
//        return "/services/serviceItemDetails";
//    }
//
//    //    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping
//    public String createServiceItem(@ModelAttribute ServiceItem serviceItem) {
//        serviceItemRepository.save(serviceItem);
//        return "redirect:/service-item";
//    }
//
//    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String editServiceItemForm(@PathVariable Long id, Model model) {
        ServiceItem serviceItem = serviceItemRepository.findById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Service item not found"));
        model.addAttribute("serviceItem", serviceItem);
        return "/services/edit_service_item";
    }


    @PostMapping("/{id}")
    public String updateServiceItem(@PathVariable Long id, @ModelAttribute ServiceItem serviceItem) {
        serviceItemRepository.save(serviceItem);
        return "redirect:/service_items";
    }
//
//    //    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{id}")
//    public String deleteServiceItem(@PathVariable Long id) {
//        serviceItemRepository.deleteById(id);
//        return "redirect:/service-item";
//    }
}

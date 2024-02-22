//package com.example.pr51salon.controller;
//
//import com.example.pr51salon.model.ServiceItem;
//import com.example.pr51salon.model.ServiceItemDetail;
//import com.example.pr51salon.repository.ServiceItemDetailRepository;
//import com.example.pr51salon.repository.ServiceItemRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/service-item-detail")
//public class ServiceItemDetailController {
//
//    @Autowired
//    private ServiceItemDetailRepository serviceItemDetailRepository;
//
//    @GetMapping
//    public String getAllServiceItems(Model model) {
//        List<ServiceItemDetail> serviceItemDetails = serviceItemDetailRepository.findAll();
//        model.addAttribute("serviceItemDetail", serviceItemDetails);
//        return "/Uudai1";
//    }
//}
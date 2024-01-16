package com.example.pr51salon.controller;


import com.example.pr51salon.model.Booking;
import com.example.pr51salon.model.ServiceItem;
import com.example.pr51salon.repository.BookingRepository;
import com.example.pr51salon.repository.ServiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceItemRepository serviceItemRepository;

    @GetMapping("/booking-form")
    public String showBookingForm(Model model) {
        List<ServiceItem> serviceItems = serviceItemRepository.findAll();
        model.addAttribute("serviceItems", serviceItems);
        model.addAttribute("newBooking", new Booking());
        return "booking/booking-form";
    }

    @PostMapping("/booking")
    public String bookAppointment(@ModelAttribute("newBooking") Booking newBooking) {
        ServiceItem serviceItem = serviceItemRepository.findById(newBooking.getServiceItem().getId()).orElse(null);
        if (serviceItem != null) {
            newBooking.setServiceItem(serviceItem);
            newBooking.setStatus("Chưa xác nhận");
            bookingRepository.save(newBooking);
            return "redirect:/booking-manage";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/booking-manage")
    public String manageBookings(Model model) {
        List<Booking> bookings = bookingRepository.findAll();
        model.addAttribute("bookings", bookings);
        return "booking/booking-manage";
    }
}

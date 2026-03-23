package pankaj.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pankaj.service.ResendService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class ContactController {

    @Autowired
    private ResendService resendService;
    
    @GetMapping ("/contact")
    public String showForm () {
    	return "contact";
    }

   @PostMapping("/contact")
public String handleContactForm(@RequestParam String email,
                                @RequestParam String subject,
                                @RequestParam String message,
                                Model model) {
    try {
        // Build a structured body
        String fullMessage = "Email: " + email + "<br>" +
                             "Full Name: " + subject + "<br>" +
                             "Message: " + message;

        // Send to YOUR inbox
        resendService.sendEmail("pad88899123@gmail.com", subject, fullMessage);

        model.addAttribute("successMessage", "Your message has been sent successfully!");
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Failed to send message: " + e.getMessage());
    }
    return "contact";
}



}
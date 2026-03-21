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
        String html = "<p><strong>Email:</strong> " + email + "</p>"
                    + "<p><strong>Subject:</strong> " + subject + "</p>"
                    + "<p><strong>Message:</strong> " + message + "</p>";

        resendService.sendEmail("pad88899123@gmail.com", subject, html).block();

        model.addAttribute("successMessage", "Message sent successfully!");
        return "contact"; 

}
}
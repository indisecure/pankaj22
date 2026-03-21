package pankaj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import pankaj.model.Registration;
import pankaj.repo.RegistrationRepo;

@org.springframework.stereotype.Controller
public class Controller {	
	@Autowired
	private RegistrationRepo registrationRepo;	
	
	@GetMapping("/index")
	public String getHomePage() {
		return "index";
	}

	@GetMapping("/about")
	public String getAboutPage() {
		return "about";
	}
	@GetMapping("/skills")
	public String getSkillPage() {
		return "skills";
	}

	@GetMapping("/project")
	public String getProjectPage() {
		return "project";
	}
	
	@GetMapping("/register")
	public String getRegistrationPage() {
		return "register";
	}

	
	
//	Registration
	
	@PostMapping("/save")
	public String saveRegistration(@ModelAttribute Registration register,Model model) {
		registrationRepo.save(register);
		model.addAttribute("message", "User Created Succesfully\nKindly Login");
		return "redirect:/login";
	}

	
	@GetMapping("/login")
	public String loginPage() {
		return "login";		
	}
	@PostMapping("/login")
    public String login(String email, String password, HttpSession session) {
		Registration user=	registrationRepo.findByEmailAndPassword(email, password);
        if(user != null){
            session.setAttribute("user", email);
            return "education";
        }
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

	

}

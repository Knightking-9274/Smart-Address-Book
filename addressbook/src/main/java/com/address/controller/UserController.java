package com.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.address.entities.User;
import com.address.helper.Message;
import com.address.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userrepository;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title","Home - Address Book!");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","About - Address Book!");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("title","Register - Address Book!");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user")User user,BindingResult bindingResult,@RequestParam(value="agree",defaultValue="false")boolean agree,Model model,HttpSession session) {
		try {
			
			if(!agree) {
				System.out.println("You have not agreeed terms and condition!");
				throw new Exception("You have not agreeed terms and condition!");
			}
			
			if(bindingResult.hasErrors()) {
				 System.out.println("Error" +bindingResult.toString());
				model.addAttribute("user",user);
				return "signup";
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			
			
			System.out.println("Agreement "+agree);
			System.out.println("User "+user);
			User result = this.userrepository.save(user);
			
			model.addAttribute("User",new User());
			session.setAttribute("message",new Message("Successfully Registered!! ","alert-success"));
			return "signup";
		}
		catch(Exception e){
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message",new Message("Something went wrong!! "+e.getMessage(),"alert-danger"));
			return "signup";	
		}
		
		
	}
}

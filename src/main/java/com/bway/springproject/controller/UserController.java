package com.bway.springproject.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.springproject.model.User;
import com.bway.springproject.repository.ProductRepository;
import com.bway.springproject.service.UserService;
import com.bway.springproject.utils.VerifyRecaptcha;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("plist",prodRepo.findAll());
		return "CustomerHome";
	}

	@GetMapping("/login")
	public String getLogin() {

		log.info("--------Login page open---------");

		return "LoginForm";
	}

	@GetMapping("/home")
	public String getHome() {

		return "Home";
	}

	@PostMapping("/login")
	public String postLogin(@ModelAttribute User u, Model model, HttpSession session,
			@RequestParam("g-recaptcha-response") String reCaptcha) {

		try {
			if (VerifyRecaptcha.verify(reCaptcha)) {

				User user = userService.login(u.getUsername(), u.getPassword());

				if (user != null) {

					log.info("--------- login success ------------");

					session.setAttribute("activeUser", user);
					session.setMaxInactiveInterval(300);

//			model.addAttribute("uname", user.getFname());
					
					if(user.getRole().equalsIgnoreCase("CUSTOMER")) {
						
						model.addAttribute("plist",prodRepo.findAll());
						return "CustomerHome";
					}
					
					return "redirect:/home";
				}else {
					model.addAttribute("error", "user not exist!!");
					return "LoginForm";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.info("---------- login failed --------");

		model.addAttribute("error", "You are Robot");
		return "LoginForm";
	}

	@GetMapping("/signup")
	public String getSignUp() {

		return "SignUpForm";
	}

	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute User u, Model model) {

		if (u.getUsername() == null || u.getUsername().isEmpty() || u.getPassword() == null
				|| u.getPassword().isEmpty()) {

			model.addAttribute("nullValue", "please enter data!!");
			return "SignUpForm";
		}

		User user = userService.checkUsername(u.getUsername());

		if (user == null) {

			userService.SignUp(u);
			return "redirect:/login";
		} else {

			model.addAttribute("error", "username already exist!!");
			return "SignUpForm";
		}
	}

	@GetMapping("/logout")
	public String getLogout(HttpSession session) {

		log.info("------- logout success ---------");

		session.invalidate();
		return "redirect:/login";
	}

	@GetMapping("/profile")
	public String getProfile() {

		return "ProfileForm";
	}
}

package com.bway.springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.springproject.utils.MailUtils;

@Controller
public class ContactController {

	@Autowired
	private MailUtils mailUtils;
	
	@GetMapping("/contact")
	public String getContact() {
		
		return "ContactForm";
	}
	
	@PostMapping("/contact")
	public String postContact(Model model, @RequestParam("toEmail") String toEmail, @RequestParam("subject") String subject, @RequestParam("message") String message) {
		
		mailUtils.sendEmail(toEmail, subject, message);
		model.addAttribute("message", "mail sent successfully");
		
		return "ContactForm";
	}
}

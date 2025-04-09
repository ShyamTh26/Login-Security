package securityproject.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String Home()
	{
		return "Home";
	} 
	
	@GetMapping("/login")
	public String Login()
	{
		return "Login";
	}
	
	@GetMapping("/register")
	public String Register()
	{
		return "Register";
	}
	
	
	/* @GetMapping("/Home")
	public String MyHome()
	{
		return "MyHome";
	}  */
}

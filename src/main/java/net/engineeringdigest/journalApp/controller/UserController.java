package net.engineeringdigest.journalApp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@GetMapping
	public List<User> getAllUsers() {
		
		return null;
		
	}

}

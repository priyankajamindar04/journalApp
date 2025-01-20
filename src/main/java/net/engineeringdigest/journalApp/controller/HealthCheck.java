package net.engineeringdigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;


@RestController
@RequestMapping("/public")
public class HealthCheck {
	
	@Autowired
	private UserService  userService; 
	
	@GetMapping("/health-check")
	public String healthCheck() {
		return "OK";
	}
	
	@PostMapping("/saveUserEncrypted")
	public void saveUserEncrypted(@RequestBody User user) {
		userService.saveEntryEncrypted(user);
	}

}

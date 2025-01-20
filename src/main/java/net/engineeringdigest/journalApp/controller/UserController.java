package net.engineeringdigest.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@PostMapping
	public void saveUser(@RequestBody User user) {
		userService.saveEntry(user);
	}
	
	
	@PutMapping
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User userInDb =  userService.findByUserName(userName);
			if (userInDb!=null) {
				userInDb.setUserName(user.getUserName());
				userInDb.setPassword(user.getPassword());
				userService.saveEntryEncrypted(user);
			}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@DeleteMapping()
	public ResponseEntity<Object> deleteUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		userService.deleteByUserName(userName);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}

package net.engineeringdigest.journalApp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	public void saveEntry(User user) {
		Object t = userRepository.save(user);
		System.out.println(t);
	}
	
	public void saveEntryEncrypted(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER"));
		Object t = userRepository.save(user);
		System.out.println(t);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> updateByID(ObjectId id) {
		return userRepository.findById(id);
	}
	
	 
	public Optional<User> findById(ObjectId id) {
		return userRepository.findById(id);
	}
	
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	public User deleteByUserName(String userName) {
		return userRepository.deleteByUserName(userName);
	}

	public boolean deleteById(ObjectId id) {
		userRepository.deleteById(id);
		return true;
	}

}
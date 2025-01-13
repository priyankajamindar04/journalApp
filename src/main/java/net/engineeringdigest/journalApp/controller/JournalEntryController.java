package net.engineeringdigest.journalApp.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<JournalEntry> getAll() {
		return journalEntryService.getAll();
	}
	
	
	@GetMapping("/{userName}")
	public ResponseEntity<?> getAllJournalEntriesForUser(@PathVariable String userName) {
		User user =  userService.findByUserName(userName);
		if (!user.getJournalEntries().isEmpty()) {
			return new ResponseEntity<>(user.getJournalEntries(),HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@PostMapping
	public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
		try {
			journalEntryService.saveEntry(myEntry);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	@PostMapping("/{userName}")
	public ResponseEntity<?> createJournalEntriesForUser(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
		try {
			journalEntryService.saveJournalEntriesForUser(myEntry, userName);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("id/{myId}")
	public ResponseEntity<?> updateJournalEntryByID(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {
		try {
			Optional<JournalEntry> oldEntry = journalEntryService.findById(myId);
			if (oldEntry.isPresent()) {
				oldEntry.get().setDate(new Date());
				oldEntry.get().setContent(myEntry.getContent());
				oldEntry.get().setTitle(myEntry.getTitle());
				journalEntryService.saveEntry(oldEntry.get());
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("update/{userName}/{myId}")
	public ResponseEntity<?> updateJournalEntryByIDUsername(@PathVariable String userName ,@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {
		try {
			User user =  userService.findByUserName(userName);
			if (user!= null) {
				Optional<JournalEntry> identifiedEntry = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(myId)).findFirst();
				if (identifiedEntry.isPresent()) {
					journalEntryService.saveEntry(identifiedEntry.get(),myEntry);
					return new ResponseEntity<>(HttpStatus.CREATED);
				}
			}
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping("id/{myId}")
	public Optional<JournalEntry> getJournalEntryByID(@PathVariable ObjectId myId) {
		return journalEntryService.findById(myId);
	}
	
	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> deleteJournalEntryByID(@PathVariable ObjectId myId) {
		journalEntryService.deleteById(myId);
		return new ResponseEntity<>("Deleted",HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("{userName}/{myId}")
	public ResponseEntity<?> deleteJournalEntryByIDUsername(@PathVariable String userName , @PathVariable ObjectId myId) {
		try {
			User user =  userService.findByUserName(userName);
			if (user!= null) {
				Optional<JournalEntry> identifiedEntry = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(myId)).findFirst();
				if (identifiedEntry.isPresent()) {
					user.getJournalEntries().remove(identifiedEntry.get());
					userService.saveEntry(user);
					//journalEntryService.deleteById(myId);
					return new ResponseEntity<>(HttpStatus.CREATED);
				}
			}
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

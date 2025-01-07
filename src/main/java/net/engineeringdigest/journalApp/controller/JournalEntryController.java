package net.engineeringdigest.journalApp.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@GetMapping
	public List<JournalEntry> getAll() {
		return journalEntryService.getAll();
	}
	
	
	@PostMapping
	public boolean createEntry(@RequestBody JournalEntry myEntry) {
		myEntry.setDate(new Date());
		journalEntryService.saveEntry(myEntry);
		return true;
	}
	
	@PutMapping("id/{myId}")
	public boolean updateJournalEntryByID(@PathVariable String myId, @RequestBody JournalEntry myEntry) {
		System.out.println("hudj");
		ObjectId id1 = new ObjectId(myId);
		Optional<JournalEntry> oldEntry = journalEntryService.findById(id1);
		if (oldEntry.isPresent()) {
			oldEntry.get().setDate(new Date());
			oldEntry.get().setContent(myEntry.getContent());
			oldEntry.get().setTitle(myEntry.getTitle());
			journalEntryService.saveEntry(oldEntry.get());
			return true;
		}
		//myEntry.setDate(new Date());
		//journalEntryService.saveEntry(myEntry);
		System.out.println(oldEntry);
		return false;
	}
	
	@GetMapping("id/{myId}")
	public Optional<JournalEntry> getJournalEntryByID(@PathVariable ObjectId myId) {
		return journalEntryService.findById(myId);
	}
	
	@DeleteMapping("id/{myId}")
	public boolean deleteJournalEntryByID(@PathVariable ObjectId myId) {
		return journalEntryService.deleteById(myId);
	}
}

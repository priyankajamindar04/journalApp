package net.engineeringdigest.journalApp.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;

	public void saveEntry(JournalEntry journalEntry) {
		journalEntry.setDate(new Date());
		journalEntryRepository.save(journalEntry);
	}

	public List<JournalEntry> getAll() {
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> updateByID(ObjectId id) {
		journalEntryRepository.findById(id);
		return journalEntryRepository.findById(id);
	}
	
	 
	public Optional<JournalEntry> findById(ObjectId id) {
		return journalEntryRepository.findById(id);
	}

	public boolean deleteById(ObjectId id) {
		journalEntryRepository.deleteById(id);
		return true;
	}
	
	@Transactional
	public void saveJournalEntriesForUser(JournalEntry myEntry, String userName) {
		myEntry.setDate(new Date());
		User user = userService.findByUserName(userName);
		if (user!=null) {
			JournalEntry savedEntry = journalEntryRepository.save(myEntry);
			user.getJournalEntries().add(savedEntry);
			user.setUserName(null);
			userService.saveEntry(user);
		}
	}

	public void saveEntry(JournalEntry journalEntry, JournalEntry myEntry) {
		journalEntry.setContent(myEntry.getContent());
		journalEntry.setTitle(myEntry.getTitle());
		this.saveEntry(journalEntry);
		
	}

}
package net.engineeringdigest.journalApp.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;

	public void saveEntry(JournalEntry journalEntry) {
		Object t = journalEntryRepository.save(journalEntry);
		System.out.println(t);
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

}
package net.engineeringdigest.journalApp.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "journal_entries")
public class JournalEntry {
	
	@Id
    private ObjectId id;

    private String title;
    
    private String content;
    
    private Date date;
}

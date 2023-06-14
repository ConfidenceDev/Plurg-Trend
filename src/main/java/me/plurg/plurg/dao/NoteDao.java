package me.plurg.plurg.dao;

import me.plurg.plurg.entity.Note;
import me.plurg.plurg.entity.Trend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDao
        extends MongoRepository<Note, String> {
}
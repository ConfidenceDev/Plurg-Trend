package me.plurg.plurg.services;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.log4j.Log4j2;
import me.plurg.plurg.dao.NoteDao;
import me.plurg.plurg.dao.TrendDao;
import me.plurg.plurg.entity.Note;
import me.plurg.plurg.entity.Trend;
import me.plurg.plurg.exception.NoteException;
import me.plurg.plurg.model.NoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class TrendServiceImpl implements TrendService{

    @Autowired
    private TrendDao trendDao;

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String sortField = "utc";
    private static final String noteField = "note";

    @Override
    public Page<Trend> getTrend(int page, int size) {
        log.info("Arranging page: " + page + ", Size: " + size);
        Page<Trend> data = getAll(page, size);
        log.info(data);
        return data;
    }

    private Page<Trend> getAll(int page, int size) {
        return trendDao.findAll(PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, sortField)));
    }

    @Override
    public void postTrend(Trend trendItem) {
        log.info("Trend Added");
        log.info(trendItem);
        trendDao.insert(trendItem);
    }

    @Override
    public Note getNote() {
        Optional<Note> res = noteDao.findById(noteField);
        return res.orElseThrow(() -> new NoteException("Something went wrong", "NOT_FOUND",
                HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public NoteResponse postNote(Note note) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(noteField));
        Update update = new Update();
        update.set("note", note.getNote());
        update.set("email", note.getEmail());
        update.set("utc", note.getUtc());

        log.info("Updating note");
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Note.class);
        if (!updateResult.wasAcknowledged()){
            throw new NoteException("Something went wrong", "BAD_REQUEST", HttpStatus.BAD_REQUEST.value());
        }
        return NoteResponse.builder().msg("Note updated successfully").build();
    }
}

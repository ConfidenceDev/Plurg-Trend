package me.plurg.plurg.services;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.log4j.Log4j2;
import me.plurg.plurg.dao.NoteDao;
import me.plurg.plurg.dao.TrendDao;
import me.plurg.plurg.entity.Note;
import me.plurg.plurg.entity.Trend;
import me.plurg.plurg.exception.NoteException;
import me.plurg.plurg.model.NoteResponse;
import me.plurg.plurg.model.TrendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class TrendServiceImpl implements TrendService {

    @Autowired
    private TrendDao trendDao;

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String sortField = "utc";
    private static final String noteField = "note";

    @Override
    public TrendResponse getTrend(int page, int size) {
        //log.info("Arranging page: " + page + ", Size: " + size);
        return getAll(page, size);
    }

    private TrendResponse getAll(int page, int size) {
        final Sort sort = Sort.by(Sort.Direction.DESC, sortField);
        List<Trend> sortedTrends = trendDao.findAll(sort);
        if ((page * size) < sortedTrends.size()) {
            Page<Trend> pageTrends = getPage(sortedTrends, PageRequest.of(page, size));

            return TrendResponse.builder()
                    .total(sortedTrends.size())
                    .data(pageTrends.toList())
                    .build();
        }

        return null;
    }

    private static Page<Trend> getPage(List<Trend> list, Pageable pageable) {
        final int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        final int endIndex = Math.min(startIndex + pageable.getPageSize(), list.size());

        if (startIndex >= endIndex) {
            return Page.empty(pageable); // Return an empty page if page is out of range
        }

        List<Trend> pageItems = list.subList(startIndex, endIndex);
        return new PageImpl<>(pageItems, pageable, list.size());
    }

    @Override
    public void postTrend(Trend trendItem) {
        //log.info("Trend Added");
        //log.info(trendItem);
        trendDao.insert(trendItem);
    }

    @Override
    public void deleteTrend(String id) {
        //log.info("Start Trend Delete");
        trendDao.deleteById(id);
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

        //log.info("Updating note");
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Note.class);
        if (!updateResult.wasAcknowledged()) {
            throw new NoteException("Something went wrong", "BAD_REQUEST", HttpStatus.BAD_REQUEST.value());
        }
        return NoteResponse.builder().msg("Note updated successfully").build();
    }
}

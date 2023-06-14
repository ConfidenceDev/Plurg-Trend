package me.plurg.plurg.services;

import me.plurg.plurg.entity.Note;
import me.plurg.plurg.entity.Trend;
import me.plurg.plurg.model.NoteResponse;
import org.springframework.data.domain.Page;

public interface TrendService {
    Page<Trend> getTrend(int page, int size);

    void postTrend(Trend trendItem);

    Note getNote();

    NoteResponse postNote(Note note);
}

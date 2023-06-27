package me.plurg.plurg.services;

import me.plurg.plurg.entity.Note;
import me.plurg.plurg.entity.Trend;
import me.plurg.plurg.model.NoteResponse;
import me.plurg.plurg.model.TrendResponse;

public interface TrendService {
    TrendResponse getTrend(int page, int size);

    void postTrend(Trend trendItem);

    Note getNote();

    NoteResponse postNote(Note note);

    void deleteTrend(String id);
}

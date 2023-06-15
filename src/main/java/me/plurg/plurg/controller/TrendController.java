package me.plurg.plurg.controller;

import lombok.extern.log4j.Log4j2;
import me.plurg.plurg.entity.Note;
import me.plurg.plurg.entity.Trend;
import me.plurg.plurg.model.NoteResponse;
import me.plurg.plurg.services.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@Log4j2
public class TrendController {

    @Autowired
    private TrendService trendService;

    @GetMapping("/")
    public String hello(){
        return "Hello World!!!!";
    }

    @GetMapping("/trends/{page}/{size}")
    public ResponseEntity<Page<Trend>> getTrend(@PathVariable int page, @PathVariable int size){
        log.info("Retrieving items");
        Page<Trend> res = trendService.getTrend(page, size);

        log.info("Trend fetched");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/trends")
    public ResponseEntity<HttpStatus> postTrend(@RequestBody Trend trendItem){
        trendService.postTrend(trendItem);

        log.info("Trend posted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/note")
    public ResponseEntity<Note> getNote(){
        log.info("Fetching Note");
        return new ResponseEntity<>(trendService.getNote(), HttpStatus.OK);
    }

    @PostMapping("/note")
    public ResponseEntity<NoteResponse> postNote(@RequestBody Note note){
        NoteResponse res = trendService.postNote(note);

        log.info("Noted posted");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}


package com.example.journalApp.controller;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping("/{userName}")
    public ResponseEntity<?> getAll( @PathVariable String userName){
        User user = userService.findByUserName(userName);

        List<JournalEntry> allJournalEntry = user.getJournalEntries();
        if( allJournalEntry != null && !allJournalEntry.isEmpty()){
            return new ResponseEntity<>(allJournalEntry , HttpStatus.OK);

        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);




    }
    @GetMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId , @PathVariable String userName){
//        User user = userService.findByUserName(userName);
//
//
//        Optional<JournalEntry> journalEntry= user.getJournalEntries(myId);
//        if(journalEntry.isPresent()){
//            return new ResponseEntity<>(journalEntry , HttpStatus.OK);
//        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> putJournalEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try {
            User user = userService.findByUserName(userName);

            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry , userName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


    }
    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById (@PathVariable ObjectId myId  ,  @PathVariable String userName){

        journalEntryService.deleteById(myId , userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }
    @PutMapping("id/{myId}")
    public ResponseEntity<?> PutJournalEntryBuId (@PathVariable ObjectId myId , @RequestBody JournalEntry newEntry ){
        JournalEntry old = journalEntryService.findEntryById(myId).orElse(null);
        if ( old != null){
            old.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().equals("")   ? newEntry.getTitle() : old.getTitle() );
            old.setContent(newEntry.getContent()!= null && !newEntry.getContent().equals("")  ? newEntry.getContent() : newEntry.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }



}

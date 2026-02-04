package com.example.journalApp.service;


import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry , String userName){
        User user = userService.findByUserName(userName);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);

    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntry(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findEntryById(ObjectId id ){
        return journalEntryRepository.findById(id);

    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false ;

       try{
           User user = userService.findByUserName(userName);
           removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
           if( removed){
               userService.saveUser(user);
               journalEntryRepository.deleteById(id);

           }
           return removed;

       }
       catch (Exception e ){
           System.out.println(e);
           throw new RuntimeException("error while deleting the journal entry" , e);

       }




    }






}

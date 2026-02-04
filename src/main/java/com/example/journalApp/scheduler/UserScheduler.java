package com.example.journalApp.scheduler;


import com.example.journalApp.cache.AppCache;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.enums.Sentiment;
import com.example.journalApp.repository.UserRepositoryImpl;
import com.example.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private EmailService emailService;


    @Autowired
    private AppCache appCache;


//    @Scheduled(cron = "*/5 * * * *")
    public void FetchUserAndSendMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> filteredList = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for (Sentiment sentiment : filteredList) {
                if (sentiment != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
                }

            }
            Sentiment dominantSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    dominantSentiment = entry.getKey();

                }

            }
            if (dominantSentiment != null){
                String subject = "Your Weekly Sentiment Analysis Report";
                String body = "Dear" + user.getUserName() + ",\n\n" +
                        "Here is your sentiment analysis report for the past week:\n" +
                        "Dominant Sentiment: " + dominantSentiment + "\n\n" +
                        "Thank you for using our journaling app!\n" +
                        "Best regards,\n" +
                        "The Journal App Team";
                emailService.sendEmail(user.getEmail(), subject, body);


            }

        }
    }

//    @Scheduled(cron = "*/5 * * * *")
    public void clearAppCache() {
        appCache.init();

    }
}

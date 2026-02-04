package com.example.journalApp.cache;


import com.example.journalApp.entity.ConfigJouralAppEntity;
import com.example.journalApp.repository.ConfigJouralAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJouralAppRepository configJouralAppRepository;


    public Map<String , String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init(){
        List<ConfigJouralAppEntity> all = configJouralAppRepository.findAll();
        for (ConfigJouralAppEntity configJouralAppEntity:all){
            APP_CACHE.put(configJouralAppEntity.getKey() , configJouralAppEntity.getValue());
        }
    }

}

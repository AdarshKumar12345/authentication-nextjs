package com.example.journalApp.repository;

import com.example.journalApp.entity.ConfigJouralAppEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigJouralAppRepository extends MongoRepository<ConfigJouralAppEntity, Object> {


}

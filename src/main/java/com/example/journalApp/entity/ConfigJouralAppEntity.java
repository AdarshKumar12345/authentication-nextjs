package com.example.journalApp.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Getter
@NoArgsConstructor
public class ConfigJouralAppEntity {

    private String key ;
    private String value ;

}

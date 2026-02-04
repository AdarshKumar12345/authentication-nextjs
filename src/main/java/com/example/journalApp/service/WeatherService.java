package com.example.journalApp.service;

import com.example.journalApp.api.responce.WeatherResponce;
import com.example.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;


    private static final String apikey = "d8e947ba8a6975b9fb015cce34d191b4";
    public static  final String API = "http://api.weatherstack.com/current?access_key=YOUR_ACCESS_KEY&query=CITY";

    public WeatherResponce getWeather (String city) {
        WeatherResponce weatherResponce = redisService.get("weather_" + city, WeatherResponce.class);
        if( weatherResponce != null){
            return weatherResponce;

        }
        else{
            String newAPI = appCache.APP_CACHE.get("weather_api").replace("<city>", city ).replace( "<apiKey>" , apikey);
            ResponseEntity<WeatherResponce> response = restTemplate.exchange(newAPI , HttpMethod.GET , null , WeatherResponce.class);
            WeatherResponce body = response.getBody();
            if( body != null) {
                redisService.set("weather_" + city, body, 30 );
            }
            return body;
        }
    }
}

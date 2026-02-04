package com.example.journalApp.api.responce;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
public class WeatherResponce {
    private  Current current;
    @Component
    @Getter
    @Setter
    public static class Current{

        private  int temperature;

        private  int feelslike;
    }
}








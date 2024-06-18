package com.tpe.schedulerTask;

import com.tpe.domain.HavaDurumuResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SchedulerTask {

    private static final Logger log = LoggerFactory.getLogger(SchedulerTask.class); // console da gorunmesi icin
    private final RestTemplate restTemplate= new RestTemplate(); // client mis gibi davranip farkli api lere request gondermeye yariyor

    @Value("${app.weather.api.key}")
    private String apiKey;

    @Scheduled(fixedRate = 10000) // Her 10 saniyede bir bu method calissin
    public void HavaDurumuBilgisiniGetir(){

        String url = "https://api.openweathermap.org/data/2.5/weather?q=Istanbul&appid="+apiKey+"&units=metric&lang=tr";
        HavaDurumuResponse response = restTemplate.getForObject(url, HavaDurumuResponse.class);

        if (response != null){
            log.info("Istanbul icin hava durumu: {}, Sicaklik : {}",
                    response.getWeather().get(0).getDescription(),
                    response.getMain().getTemp());
        }
    }


}

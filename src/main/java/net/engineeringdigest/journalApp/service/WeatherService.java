package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholder;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Component
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey ;
    //        private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

//    private static final String apikey = "2def6ce80a120a13343c2ff958c37a31";
//    private static final String API = "http://api.openweathermap.org/geo/1.0/direct?appid=API_KEY&q=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
         } else {
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholder.CITY, city).replace(Placeholder.API_KEY, apikey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 300l);
            }
            return body;
        }
    }
    }
//        String finalAPI  = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholder.CITY, city).replace(Placeholder.API_KEY, apikey);
////        String finalAPI  = appCache.appCache.get("weather_api").replace("<city>", city).replace("<apikey>", apikey);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
////        response.getStatusCode();
////        System.out.println("API RESPONSE BODY: " + response.getBody());
//
////        System.out.println(finalAPI);
////        System.out.println(response.getBody());
//        WeatherResponse body = response.getBody();
//        return body;
//    }

//    public WeatherResponse getWeather(String city)
//    {
//        String finalAPI = API.replace("CITY", city).replace("API_KEY", apikey);
//        HttpHeaders.set("key", "value");
//        User user = User.builder().userName("shinu").password("shinu").build();
//        HttpEntity<User> httpEntity = new HttpEntity<>(user, HttpHeaders);
//
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, HttpEntity, WeatherResponse.class);
//        WeatherResponse body = response.getBody();
//        return body;
//    }

//}

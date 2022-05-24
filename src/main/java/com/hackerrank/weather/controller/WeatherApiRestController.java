package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class WeatherApiRestController {
    @Autowired
    WeatherRepository weatherRepository;
    @GetMapping("/weather")
    public ResponseEntity<List<Weather>> getWeather(@RequestParam(required = false)
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestParam(required = false) String sort,
        @RequestParam(required = false) String city) {
        List<Weather> weathers = null;
        if(null != city) {
            List<String> cities = Stream.of(city.split(",", -1))
                    .collect(Collectors.toList());
            cities.replaceAll(String::toLowerCase);
            weathers = weatherRepository.findAllByCityIn(cities);
        }
        if(date == null && city == null) {
            weathers = weatherRepository.findAll();
        } else if(date != null && city == null) {
            weathers = weatherRepository.findAllByDate(date);
        }


        if("-date".equals(sort)) {
            Collections.sort(weathers, Comparator.comparing(Weather::getDate, Comparator.reverseOrder()));

        }
        if("date".equals(sort)) {
            Collections.sort(weathers, Comparator.comparing(Weather::getDate).thenComparing(Weather::getId));
        }

        return ResponseEntity.ok( weathers);
    }

    @PostMapping("/weather")
    public ResponseEntity<Weather> createWeather(@RequestBody Weather request) {
        request.setTemps(request.getTemperatures().stream().map(String::valueOf).collect(Collectors.joining(",")));
        request.setCity(request.getCity().toLowerCase());
        Weather retVal = weatherRepository.save(request);
        return new ResponseEntity<Weather>(retVal, HttpStatus.CREATED);
    }
}

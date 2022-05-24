package com.hackerrank.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name="WEATHER")
public class Weather {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(name= "date")
    //@DateTimeFormat(pattern="YYYY-mm-DD")
    private Date date;

    @Column(name="lat")
    private Float lat;
    @Column(name="lon")
    private Float lon;
    @Column(name="city", length = 255)
    @Size(min=1, max=255, message="Invalid City")
    private String city;
    @Column(name="state", length = 255)
    @Size(min=1, max=255, message="Invalid State")
    private String state;

    @Column(name="temps", length = 4000)
    @JsonIgnore
    private String temps;

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    @Transient
    private List<Double> temperatures;
    public Weather(Integer id, Date date, Float lat, Float lon, String city, String state, List<Double> temperatures) {
        this.id = id;
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        this.state = state;
        this.temperatures = temperatures;
    }

    public Weather(Date date, Float lat, Float lon, String city, String state, List<Double> temperatures) {
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        this.state = state;
        this.temperatures = temperatures;
    }

    public Weather() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Double> getTemperatures() {
        if(this.temps != null) {
            String[] localTemps = temps.split(",");
            temperatures = Stream.of(localTemps).map(Double::valueOf).collect(Collectors.toList());
        }
        return temperatures;
    }

    public void setTemperatures(List<Double> temperatures) {

        this.temperatures = temperatures;
    }
}

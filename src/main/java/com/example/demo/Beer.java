package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Beer {
    private Long id;
    private String name;
    private Double abv;
    private String description;
    private List<String> food_pairing;
    private String image_url;

}


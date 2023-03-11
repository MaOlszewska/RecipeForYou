package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DinnerSet {
    // Dish
    private Long idMeal;
    private String strMeal;
    private String strCategory;
    private String strInstructions;
    private String strSource;
    private String strMealThumb;

    //Beer
    private Long id;
    private String name;
    private Double abv;
    private String description;
    private List<String> food_pairing;
    private String image_url;
}

package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dish {
    private Long idMeal;
    private String strMeal;
    private String strCategory;
    private String strInstructions;
    private String strSource;
    private String strMealThumb;
}

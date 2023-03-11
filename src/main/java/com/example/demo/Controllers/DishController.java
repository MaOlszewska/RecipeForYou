package com.example.demo.Controllers;

import com.example.demo.Services.DishService;
import com.example.demo.model.DishResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DishController {
    @Autowired
    private DishService dishService;
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("dishes/random")
    public String getRandomDish() throws IOException {
        String responseBody = dishService.getRandomDish();
        DishResponse dish = objectMapper.readValue(responseBody, DishResponse.class);
        return dish.getMeals().get(0).toString();
    }

    @GetMapping("dishes{i}")
    public String getDishByIngredient(@RequestParam("i") String ingredient) throws IOException {
        String responseBodyByIngredient = dishService.getDishByIngredient(ingredient);
        DishResponse dishByIngeredient = objectMapper.readValue(responseBodyByIngredient, DishResponse.class);

        String responseBody = dishService.getDeatilsDishById(dishByIngeredient.getMeals().get(0).getIdMeal().longValue());
        DishResponse dish = objectMapper.readValue(responseBody, DishResponse.class);
        return dish.getMeals().get(0).toString();

    }
}
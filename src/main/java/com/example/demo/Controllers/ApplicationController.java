package com.example.demo.Controllers;

import com.example.demo.Services.BeerService;
import com.example.demo.Services.DishService;
import com.example.demo.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
public class ApplicationController {
    @Autowired
    private DishService dishService;
    @Autowired
    private BeerService beerService;
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/form")
    public ModelAndView showForm() {
        Params params = new Params();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register_form");
        modelAndView.addObject("params", params);
        return modelAndView;
    }

    @GetMapping("/dinner_set")
    public ModelAndView getDinnerSet(@RequestParam("i") String ingredient) throws IOException {
        Dish dish = getDishByIngredient(ingredient);
        Beer beer = getBeerByIngredient(ingredient);
        System.out.println(beer.toString());

        DinnerSet set = objectMapper.convertValue(dish, DinnerSet.class);
        objectMapper.updateValue(set, beer);

        System.out.println(set);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dinnerSet");
        modelAndView.addObject("dinnerSet", set);
        return modelAndView;
    }

    @PostMapping("/dinner_set")
    public ModelAndView showDinnerSet(@ModelAttribute("params") Params params) throws IOException {
        Dish dish = getDishByIngredient(params.getIngredient());
        Beer beer = getBeerByIngredient(params.getIngredient());
        System.out.println(beer.toString());

        DinnerSet set = objectMapper.convertValue(dish, DinnerSet.class);
        objectMapper.updateValue(set, beer);

        System.out.println(set);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dinnerSet");
        modelAndView.addObject("dinnerSet", set);
        return modelAndView;
    }


    private Dish getDishByIngredient(String ingredient) throws IOException {
        String responseBodyByIngredient = dishService.getDishByIngredient(ingredient);
        DishResponse dishList = objectMapper.readValue(responseBodyByIngredient, DishResponse.class);
        String responseBody = dishService.getDeatilsDishById(dishList.getMeals().get(new Random().nextInt(dishList.getMeals().size())).getIdMeal().longValue());
        DishResponse dish = objectMapper.readValue(responseBody, DishResponse.class);
        return dish.getMeals().get(0);
    }

    private Beer getBeerByIngredient(String ingredient) throws IOException{
        String responseBody = beerService.getBeerByFood(ingredient);
        List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() {
        });

        return beers.get(new Random().nextInt(beers.size()));
    }

}

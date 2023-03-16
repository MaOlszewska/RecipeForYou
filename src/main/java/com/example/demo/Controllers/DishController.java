package com.example.demo.Controllers;

import com.example.demo.Exceptions.BeerNotFoundException;
import com.example.demo.Exceptions.DishNotFoundException;
import com.example.demo.Services.DishService;
import com.example.demo.model.Beer;
import com.example.demo.model.Dish;
import com.example.demo.model.DishResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class DishController {
    @Autowired
    private DishService dishService;
    ObjectMapper objectMapper = new ObjectMapper();

    private final String DISH_VIEW_NAME = "dishes";

    @GetMapping("dishes/random")
    public ResponseEntity<Object> getRandomDish() throws IOException {
        Optional<DishResponse> dishResponse = dishService.getRandomDish();

        if(dishResponse.isPresent()){
            return ResponseEntity.ok(dishResponse.get().getMeals().get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new DishNotFoundException().getMessage());
        }
    }

    @PostMapping("dishes/random")
    public ModelAndView getRandomDishAndShow() throws IOException {
        Optional<DishResponse> responseBodyRandom = dishService.getRandomDish();

        DishResponse dishResponse;
        if (responseBodyRandom.isPresent()) {
            dishResponse = responseBodyRandom.get();
        } else {
            throw new DishNotFoundException();
        }

        Optional<DishResponse> responseBody = dishService.getDeatilsDishById(dishResponse.getMeals().get(0).getIdMeal().longValue());

        DishResponse dish;
        if (responseBody.isPresent()) {
            dish = responseBodyRandom.get();
        } else {
            throw new DishNotFoundException();
        }

        return createDishView(DISH_VIEW_NAME, dish.getMeals().get(0));
    }


    @GetMapping("dishes")
    public ResponseEntity<Object> getDishByIngredient(@RequestParam("i") String ingredient) throws IOException {
        Optional<DishResponse> responseBodyRandom = dishService.getDishByIngredient(ingredient);

        DishResponse dishResponse;
        if (responseBodyRandom.isPresent()) {
            dishResponse = responseBodyRandom.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new DishNotFoundException(ingredient).getMessage());
        }

        Optional<DishResponse> responseBody = dishService.getDeatilsDishById(dishResponse.getMeals().get(0).getIdMeal().longValue());

        DishResponse dish;
        if (responseBody.isPresent()) {
            dish = responseBodyRandom.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new DishNotFoundException(ingredient).getMessage());
        }
        return ResponseEntity.ok(dish.getMeals().get(0));
    }

    private ModelAndView createDishView(String name, Dish dish){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(name);
        modelAndView.addObject("dish", dish);
        return modelAndView;
    }
}

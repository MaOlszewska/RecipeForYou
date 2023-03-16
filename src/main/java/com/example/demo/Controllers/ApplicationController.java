package com.example.demo.Controllers;

import com.example.demo.Exceptions.BeerNotFoundException;
import com.example.demo.Exceptions.DishNotFoundException;
import com.example.demo.Exceptions.MissingParametersException;
import com.example.demo.Services.BeerService;
import com.example.demo.Services.DishService;
import com.example.demo.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class ApplicationController {
    @Autowired
    private DishService dishService;
    @Autowired
    private BeerService beerService;

    private final String SET_VIEW_NAME = "dinnerSet";
    private final String SET_FORM_NAME = "form";
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/form")
    public ModelAndView showForm() {
        Params params = new Params();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(SET_FORM_NAME);
        modelAndView.addObject("params", params);
        return modelAndView;
    }

    @GetMapping("/dinner_set")
    public ModelAndView getDinnerSet(@RequestParam("i") String ingredient) throws IOException {
        Dish dish = getDishByIngredient(ingredient);
        Beer beer = getBeerByIngredient(ingredient);

        DinnerSet set = objectMapper.convertValue(dish, DinnerSet.class);
        objectMapper.updateValue(set, beer);

        return createSetView(SET_VIEW_NAME, set,"dinnerSet");
    }

    @PostMapping("/dinner_set")
    public ModelAndView showDinnerSet(@ModelAttribute("params") Params params) throws IOException {
        validateParams(params);

        Dish dish = getDishByIngredient(params.getIngredient());
        Beer beer = getBeerByIngredient(params.getIngredient());

        DinnerSet set = objectMapper.convertValue(dish, DinnerSet.class);
        objectMapper.updateValue(set, beer);

        return createSetView(SET_VIEW_NAME, set,"dinnerSet");
    }


    private Dish getDishByIngredient(String ingredient) throws IOException {
        Optional<DishResponse> responseBodyRandom = dishService.getDishByIngredient(ingredient);

        DishResponse dishResponseRandom = getResponse(responseBodyRandom, ingredient);

        Optional<DishResponse> responseBody = dishService.getDeatilsDishById(dishResponseRandom.getMeals().get(new Random().nextInt(dishResponseRandom.getMeals().size())).getIdMeal().longValue());

        DishResponse dishResponse = getResponse(responseBody, ingredient);

        return dishResponse.getMeals().get(0);
    }

    private Beer getBeerByIngredient(String ingredient) throws IOException {

        Optional<List<Beer>> beers = beerService.getBeerByFood(ingredient);
        if (beers.isPresent()) {
            List<Beer> beer = beers.get();
            return beers.get().get(new Random().nextInt(beer.size()));
        } else {
            throw new BeerNotFoundException(ingredient);
        }

    }

    private ModelAndView createSetView(String name, DinnerSet set, String params) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(name);
        modelAndView.addObject(params, set);
        return modelAndView;
    }

    private void validateParams(Params params) {
        if (params.getIngredient().isEmpty()) {
            throw new MissingParametersException();
        }
    }

    private DishResponse getResponse(Optional<DishResponse> response, String ingredient){
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new DishNotFoundException(ingredient);
        }
    }

//    @GetMapping(value = "/swagger.yaml", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Swagger> getSwagger() throws IOException {
//        InputStream inputStream = getClass().getResourceAsStream("/static/swagger.yaml");
//        Swagger swagger = new SwaggerParser().parse(inputStream);
//        return ResponseEntity.ok(swagger);
//    }

    @GetMapping(value = "/swagger.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSwaggerJson() throws IOException {
        // Wczytaj plik swagger.json z katalogu resources
        InputStream inputStream = getClass().getResourceAsStream("/static/swagger.json");
        String swaggerJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        return ResponseEntity.ok(swaggerJson);
    }

}

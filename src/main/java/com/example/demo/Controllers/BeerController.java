package com.example.demo.Controllers;

import com.example.demo.Services.BeerService;
import com.example.demo.model.Beer;
import com.example.demo.model.Params;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
public class BeerController {
    @Autowired
    private BeerService beerService;
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("beers/random")
    public ModelAndView getRandomBeers() throws IOException {
        String responseBody = beerService.getRandomBeer();
        List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() {
        });

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("beers");
        modelAndView.addObject("beers", beers);
        return modelAndView;
    }

    @PostMapping("beers/random")
    public ModelAndView showRandomBeers() throws IOException {
        String responseBody = beerService.getRandomBeer();
        List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() {
        });

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("beers");
        modelAndView.addObject("beers", beers);
        return modelAndView;
    }
    @PostMapping("beers/alcohol")
    public ModelAndView getBeerByAbv(@ModelAttribute("params") Params params) throws IOException {
        System.out.println(params.getAbv_gt());
        String responseBody = beerService.getBeerByAbv(params.getAbv_gt(), params.getAbv_it());
        List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() {
        });

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("beers");
        modelAndView.addObject("beers", beers);
        return modelAndView;
    }

    @PostMapping("beers/food")
    public ModelAndView getBeerByFood(@ModelAttribute("params") Params params) throws IOException {
        String responseBody = beerService.getBeerByFood(params.getIngredient());
        List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() {
        });

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("beers");
        modelAndView.addObject("beers", beers);
        return modelAndView;
    }


}
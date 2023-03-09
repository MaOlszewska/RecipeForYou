package com.example.demo;

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
    public ModelAndView getRandomBeer() throws IOException {
        String responseBody = beerService.getRandomBeer();
        List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() {
        });

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("beers");
        modelAndView.addObject("beers", beers);
        return modelAndView;
    }

    @GetMapping("beers{abv_gt}{abv_it}")
    public ModelAndView getBeerByAbv(@RequestParam("abv_gt") Double abvGt, @RequestParam("abv_it") Double abvIt) throws IOException {
        String responseBody = beerService.getBeerByAbv(abvGt, abvIt);
        List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() {
        });

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("beers");
        modelAndView.addObject("beers", beers);
        return modelAndView;
    }

    @PostMapping("beers{food}")
    public ModelAndView getBeerByFood(@ModelAttribute("params") Params params) throws IOException {
        String responseBody = beerService.getBeerByFood(params.getFood());
        List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() {
        });

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("beers");
        modelAndView.addObject("beers", beers);
        return modelAndView;
    }


}
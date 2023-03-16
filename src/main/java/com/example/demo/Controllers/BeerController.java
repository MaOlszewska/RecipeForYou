package com.example.demo.Controllers;

import com.example.demo.Exceptions.BeerNotFoundException;
import com.example.demo.Exceptions.InvalidAbvValueException;
import com.example.demo.Exceptions.MissingParametersException;
import com.example.demo.Services.BeerService;
import com.example.demo.model.Beer;
import com.example.demo.model.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class BeerController {
    @Autowired
    private BeerService beerService;

    private final String BEER_VIEW_NAME = "beers";

    @GetMapping("beers/random")
    public ResponseEntity<Object> getRandomBeers() throws IOException {
        Optional<List<Beer>> beers = beerService.getRandomBeer();
        if (beers.isPresent()) {
            return ResponseEntity.ok(beers.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BeerNotFoundException().getMessage());
        }
    }

    @PostMapping("beers/random")
    public ModelAndView showRandomBeers() throws IOException {
        Optional<List<Beer>> beers = beerService.getRandomBeer();
        if (beers.isPresent()) {
            return createBeerView(BEER_VIEW_NAME, beers.get());
        } else {
            throw new BeerNotFoundException();
        }
    }

    @PostMapping("beers/abv")
    public ModelAndView getBeerByAbv(@ModelAttribute("params") Params params) throws IOException {
        validateAbvValues(params);
        Optional<List<Beer>> beers = beerService.getBeerByAbv(params.getAbv_gt(), params.getAbv_it());
        if (beers.isPresent()) {
            return createBeerView(BEER_VIEW_NAME, beers.get());
        } else {
            throw new BeerNotFoundException(params.getAbv_gt(), params.getAbv_it());
        }
    }

    @PostMapping("beers/ingredient")
    public ModelAndView getBeerByFood(@ModelAttribute("params") Params params) throws IOException {
        validateParamsWithIngredient(params);
        Optional<List<Beer>> beers = beerService.getBeerByIngredient(params.getIngredient());
        if (beers.isPresent()) {
            return createBeerView(BEER_VIEW_NAME, beers.get());
        } else {
            throw new BeerNotFoundException(params.getIngredient());
        }
    }

    @GetMapping("beers/ingredient")
    public ResponseEntity<Object> getBeerByFood(@RequestParam("i") String i) {
        try {
            if (i.equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MissingParametersException().getMessage());
            }
            Optional<List<Beer>> beers = beerService.getBeerByIngredient(i);
            if (beers.isPresent()) {
                return ResponseEntity.ok(beers.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BeerNotFoundException(i).getMessage());
            }
        } catch (IOException e) {
            return null;
        }
    }

    private ModelAndView createBeerView(String name, List<Beer> beers) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(name);
        modelAndView.addObject("beers", beers);
        return modelAndView;
    }

    private void validateAbvValues(Params params) {
        if (params.getAbv_gt() == null || params.getAbv_it() == null) {
            throw new MissingParametersException();
        }
        if (params.getAbv_gt() < 0 || params.getAbv_it() < 0) {
            throw new InvalidAbvValueException(params.getAbv_gt(), params.getAbv_it());
        }
        if (params.getAbv_gt() > params.getAbv_it()) {
            throw new InvalidAbvValueException(params.getAbv_it());
        }
    }

    private void validateParamsWithIngredient(Params params) {
        if (params.getIngredient().isEmpty()) {
            throw new MissingParametersException();
        }
    }
}

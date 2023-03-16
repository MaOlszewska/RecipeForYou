package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Beer Not Found")  // 404
public class BeerNotFoundException extends RuntimeException {
    public BeerNotFoundException(String i) {
        super("Matching Beer Not Found: " + i);
    }

    public BeerNotFoundException(Double abvGt, Double abvIt) {
        super("Beer between " + abvGt.toString() + "-" + abvIt.toString() + " Abv Not Found");
    }

    public BeerNotFoundException() {
        super("Beer Not found");
    }
}

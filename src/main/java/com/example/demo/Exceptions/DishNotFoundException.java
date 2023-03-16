package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Beer Not Found")  // 404
public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(String i) {
        super("Dish with Ingredient:  " + i + " Not found");
    }
    public DishNotFoundException() {
        super("Dish Not found");
    }
}
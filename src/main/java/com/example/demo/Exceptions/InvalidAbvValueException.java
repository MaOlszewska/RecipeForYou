package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "InvalidAbvValue")
public class InvalidAbvValueException extends RuntimeException {

    public InvalidAbvValueException(Double abvGt, Double abvIt) {
        super("Values must be greater than zero. ABV greater:  " + abvGt + " ABV lower : " + abvIt);
    }

    public InvalidAbvValueException(Double abvGt) {
        super("Value " + abvGt + " must be greater than other");
    }
}

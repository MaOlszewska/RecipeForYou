package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "MissingParameters")
public class MissingParametersException extends RuntimeException {
    public MissingParametersException() {
        super("Parameters is Missing");
    }

}

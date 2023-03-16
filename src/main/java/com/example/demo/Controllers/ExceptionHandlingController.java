package com.example.demo.Controllers;

import com.example.demo.Exceptions.BeerNotFoundException;
import com.example.demo.Exceptions.DishNotFoundException;
import com.example.demo.Exceptions.InvalidAbvValueException;
import com.example.demo.Exceptions.MissingParametersException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({
            BeerNotFoundException.class,
            InvalidAbvValueException.class,
            MissingParametersException.class,
            DishNotFoundException.class})
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}

package com.example.demo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ApplicationController {


    @GetMapping("/register")
    public ModelAndView showForm() {
        Params params = new Params();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register_form");
        modelAndView.addObject("params", params);
        return modelAndView;
    }

}

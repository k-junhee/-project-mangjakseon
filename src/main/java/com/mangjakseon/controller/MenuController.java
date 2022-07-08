package com.mangjakseon.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class MenuController {

    @GetMapping("/movie")
    public void movie(){

    }

    @GetMapping("/actor")
    public void actor(){

    }
}

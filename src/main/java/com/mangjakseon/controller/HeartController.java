package com.mangjakseon.controller;

import com.mangjakseon.dto.HeartDTO;
import com.mangjakseon.dto.ReviewDTO;
import com.mangjakseon.entity.Heart;
import com.mangjakseon.repository.HeartRepository;
import com.mangjakseon.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@Log4j2
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping
    @RequestMapping(value="/heart",method =  {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<HeartDTO> heart(@RequestBody HeartDTO heartDto) {

        heartService.heart(heartDto);
        return new ResponseEntity<>(heartDto, HttpStatus.CREATED);
    }

//    @DeleteMapping(value = "/down")
//    public ResponseEntity<HeartDTO> unHeart(@RequestBody HeartDTO heartDto) {
//        heartService.unHeart(heartDto);
//        return new ResponseEntity<>(heartDto, HttpStatus.OK);
//    }



}

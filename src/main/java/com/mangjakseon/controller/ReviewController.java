package com.mangjakseon.controller;

import com.mangjakseon.dto.ReviewDTO;

import com.mangjakseon.repository.HeartRepository;
import com.mangjakseon.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/movies")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(value = "/movie/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewDTO>> findByMovieId(@PathVariable("movieId") String movieId ){
        log.info(movieId +"======================================");

        return new ResponseEntity<>( reviewService.findByMovieIdOrderByReviewNumDesc(movieId), HttpStatus.OK);

    }
    @RequestMapping(method=RequestMethod.POST)
    @PostMapping("/movies")
    public ResponseEntity<Long> register(@RequestBody ReviewDTO reviewDTO){

        log.info(reviewDTO);

        Long reviewNum = reviewService.register(reviewDTO);

        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }

    @DeleteMapping(value="/{reviewNum}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("reviewNum") Long reviewNum){
        log.info("reviewNum" + reviewNum);
        reviewService.remove(reviewNum);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PutMapping("/{reviewNum}")
    public ResponseEntity<String> modify(@RequestBody ReviewDTO reviewDTO) {

        log.info(reviewDTO);

        reviewService.modify(reviewDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}




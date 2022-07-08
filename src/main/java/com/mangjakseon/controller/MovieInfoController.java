package com.mangjakseon.controller;


import com.mangjakseon.dto.MovieInfoDTO;
import com.mangjakseon.dto.WriterDTO;
import com.mangjakseon.repository.MemberRepository;
import com.mangjakseon.service.HeartService;
import com.mangjakseon.service.WriterService;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.Principal;


@Controller
@Log4j2
@RequiredArgsConstructor
public class MovieInfoController {

    private final WriterService writerService;
    private final MemberRepository memberRepository;

    @Value("${API_KEY}")
    String key;

    @GetMapping ("/movie/{movieId}")
    public Object movieInfo(MovieInfoDTO dto, Principal principal, Model model) {

        Long movieId = dto.getMovieId();

        // 파싱한 데이터를 저장할 변수
        String movieJson = "";
        String watchJson = "";
        String castJson = "";

        JSONObject movieData = null;

        JSONObject watchData = null;
        JSONObject watchResults = null;
        JSONObject watchLink = null;
        JSONArray watchArray = null;
        JSONObject watchLogo = null;

        JSONObject castData = null;
        JSONArray castArray = null;
        JSONArray crewArray = null;
        JSONObject crewObject = null;
        JSONObject director = null;

        try {
            URL movieUrl = new URL("https://api.themoviedb.org/3/" +
                    "movie/" + movieId +
                    "?api_key=" + key +
                    "&language=ko");

            URL watchUrl = new URL("https://api.themoviedb.org/3/" +
                    "movie/" + movieId +
                    "/watch/providers?" +
                    "api_key=" + key);

            URL castUrl = new URL("https://api.themoviedb.org/3/" +
                    "movie/" + movieId +
                    "/credits?" +
                    "api_key=" + key);

            BufferedReader movieBf;
            BufferedReader watchBf;
            BufferedReader castBf;

            movieBf = new BufferedReader(new InputStreamReader(movieUrl.openStream()));
            watchBf = new BufferedReader(new InputStreamReader(watchUrl.openStream()));
            castBf = new BufferedReader(new InputStreamReader(castUrl.openStream()));

            movieJson = movieBf.readLine();
            watchJson = watchBf.readLine();
            castJson = castBf.readLine();

            JSONParser jsonParser = new JSONParser();

            movieData = (JSONObject) jsonParser.parse(movieJson);

            watchData = (JSONObject) jsonParser.parse(watchJson);
            watchResults = (JSONObject) watchData.get("results");
            watchLink = (JSONObject) watchResults.get("KR");
            if(watchLink == null){
                watchLink = (JSONObject) watchResults.get("US");
                watchArray = (JSONArray) watchLink.get("flatrate");
                if(watchArray == null) {
                    watchArray = (JSONArray) watchLink.get("buy");
                    if(watchArray == null) {
                        watchArray = (JSONArray) watchLink.get("rent");
                        if(watchArray == null){
                            watchArray = (JSONArray) watchLink.get("ads");
                        }
                    }
                }
            }

            watchArray = (JSONArray) watchLink.get("flatrate");
            if(watchArray == null) {
                watchArray = (JSONArray) watchLink.get("buy");
                if (watchArray == null) {
                    watchArray = (JSONArray) watchLink.get("rent");
                    if(watchArray == null){
                        watchArray = (JSONArray) watchLink.get("ads");
                    }
                }
            }

            for(int i=0; i<watchArray.size(); i++){
                watchLogo = (JSONObject) watchArray.get(i);
            }

            castData = (JSONObject) jsonParser.parse(castJson);
            castArray = (JSONArray) castData.get("cast");
            crewArray = (JSONArray) castData.get("crew");

            for(int i=0; i<crewArray.size(); i++){
                crewObject = (JSONObject) crewArray.get(i);
                //log.info(crewObject.get("job"));
                if(crewObject.get("job").equals("Director")){
                    //log.info(crewObject);
                    model.addAttribute("director", crewObject);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String email = "";
        if(principal != null) {
            email = principal.getName();
            WriterDTO writer = writerService.getWriter(email);
            model.addAttribute("writer", writer);
            model.addAttribute("userCode", memberRepository.findByMemberId(email));
        }else {
            email = "zzzzz@mjs.com";
            WriterDTO writer = writerService.getWriter(email);
            model.addAttribute("writer", writer);
        }

        model.addAttribute("movieData", movieData);
        model.addAttribute("castData", castArray);
        model.addAttribute("watchLink", watchLink);
        model.addAttribute("watchLogo", watchLogo);


        return "/movie-info";
    }
}


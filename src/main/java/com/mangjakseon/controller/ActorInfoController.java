package com.mangjakseon.controller;

import com.mangjakseon.dto.ActorInfoDTO;
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


@Controller
@Log4j2
@RequiredArgsConstructor
public class ActorInfoController {

    @Value("${API_KEY}")
    String key;

    @GetMapping ("/actor/{actorId}")
    public Object actorInfo(ActorInfoDTO dto, Model model) {



        Long actorId = dto.getActorId();

        String actorJson = "";
        String actorMovieJson = "";
        String snsJson = "";

        JSONObject actorData = null;
        JSONObject actormovieData = null;
        JSONArray actorMovieArray = null;
        JSONObject snsData = null;

        try {
            URL actorUrl = new URL("https://api.themoviedb.org/3/" +
                    "person/" + actorId +
                    "?api_key=" + key +
                    "&language=us");

            URL actorMovieUrl = new URL("https://api.themoviedb.org/3/" +
                    "person/" + actorId +
                    "/movie_credits" +
                    "?api_key=" + key +
                    "&language=ko");

            URL snsUrl = new URL("https://api.themoviedb.org/3/" +
                    "person/" + actorId +
                    "/external_ids" +
                    "?api_key=" + key +
                    "&language=ko");

            BufferedReader actorBf;
            BufferedReader actorMovieBf;
            BufferedReader snsBf;


            actorBf = new BufferedReader(new InputStreamReader(actorUrl.openStream()));
            actorMovieBf = new BufferedReader(new InputStreamReader(actorMovieUrl.openStream()));
            snsBf = new BufferedReader(new InputStreamReader(snsUrl.openStream()));


            actorJson = actorBf.readLine();
            actorMovieJson = actorMovieBf.readLine();
            snsJson = snsBf.readLine();

            JSONParser jsonParser = new JSONParser();

            actorData = (JSONObject) jsonParser.parse(actorJson);
            actormovieData = (JSONObject) jsonParser.parse(actorMovieJson);
            actorMovieArray = (JSONArray) actormovieData.get("cast");
            snsData = (JSONObject) jsonParser.parse(snsJson);

            //log.info(actorMovieArray + "어디");

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("actorData", actorData);
        model.addAttribute("actorMovieData", actorMovieArray);
        model.addAttribute("snsData", snsData);

        return "/actor-info";
    }

}


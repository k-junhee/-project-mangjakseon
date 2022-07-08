package com.mangjakseon.controller;

import com.mangjakseon.dto.queryDTO;
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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

@Controller
@Log4j2
@RequiredArgsConstructor
public class SearchController {

    @Value("${API_KEY}")
    String key;

    String queryJson = "";

    String keyword = "";

    JSONObject queryData = null;

    JSONArray dataArray = null;

    JSONObject dataObject = null;

    ArrayList<JSONObject> movieArray = null;
    ArrayList<JSONObject> personArray = null;
    @GetMapping("/search")
    public String search(queryDTO dto, Model model) {

        String query = dto.getQuery();

        try {
            keyword = URLEncoder.encode(query,"UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        try {
            URL queryUrl = new URL("https://api.themoviedb.org/3/search/multi" +
                    "?api_key=" + key +
                    "&language=ko" +
                    "&query=" + keyword +
                    "&page=1" +
                    "&include_adult=false");

            BufferedReader queryBf;

            queryBf = new BufferedReader(new InputStreamReader(queryUrl.openStream()));

            queryJson = queryBf.readLine();

            JSONParser jsonParser = new JSONParser();

            queryData = (JSONObject) jsonParser.parse(queryJson);

            dataArray = (JSONArray) queryData.get("results");

             movieArray = new ArrayList<>();
             personArray = new ArrayList<>();

            for (Object o : dataArray) {
                dataObject = (JSONObject) o;
                if (dataObject.get("media_type").equals("movie")) {
                    movieArray.add(dataObject);
                    //log.info(movieArray + "배열");
                    model.addAttribute("movieData", movieArray);
                }
                if (dataObject.get("media_type").equals("person")){
                    personArray.add(dataObject);
                    //log.info(dataObject + "배열");
                    model.addAttribute("personData", personArray);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/search";
    }
}


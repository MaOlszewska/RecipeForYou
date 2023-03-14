package com.example.demo.Services;

import com.example.demo.model.Beer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BeerService {
    private static final String BASE_URL = "https://api.punkapi.com/v2/";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<List<Beer>> getRandomBeer()  throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "beers/random")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                String responseBody = response.body().string();
                List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() { });
                if(!beers.isEmpty()){
                    return Optional.ofNullable(beers);
                }
            }
            return Optional.empty();
        }
    }

    public Optional<List<Beer>> getBeerByAbv(Double abvGt, Double abvIt) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "beers").newBuilder();
        urlBuilder.addQueryParameter("abv_gt", String.valueOf(abvGt));
        urlBuilder.addQueryParameter("abv_lt", String.valueOf(abvIt));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                String responseBody = response.body().string();
                List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() { });
                if(!beers.isEmpty()){
                    return Optional.ofNullable(beers);
                }
            }
            return Optional.empty();
        }
    }

    public Optional<List<Beer>> getBeerByFood(String food) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "beers").newBuilder();
        urlBuilder.addQueryParameter("food", String.valueOf(food));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                String responseBody = response.body().string();
                List<Beer> beers = objectMapper.readValue(responseBody, new TypeReference<List<Beer>>() { });
                if(!beers.isEmpty()){
                    return Optional.ofNullable(beers);
                    }
                }
            return Optional.empty();
        }
    }
}
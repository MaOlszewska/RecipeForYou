package com.example.demo.Services;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class BeerService {
    private static final String BASE_URL = "https://api.punkapi.com/v2/";
    private final OkHttpClient client = new OkHttpClient();

    public String getBeers() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "beers")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String getRandomBeer()  throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "beers/random")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String getBeerByAbv(Double abvGt, Double abvIt) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "beers").newBuilder();
        urlBuilder.addQueryParameter("abv_gt", String.valueOf(abvGt));
        urlBuilder.addQueryParameter("abv_lt", String.valueOf(abvIt));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String getBeerByFood(String food) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "beers").newBuilder();
        urlBuilder.addQueryParameter("food", String.valueOf(food));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
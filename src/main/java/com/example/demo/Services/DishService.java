package com.example.demo.Services;

import com.example.demo.model.DishResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    private static final String BASE_URL = "http://www.themealdb.com/api/json/v1/1/";
    private final OkHttpClient client = new OkHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<DishResponse> getRandomDish() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "random.php")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return checkResponse(response);
        }
    }


    public Optional<DishResponse> getDishByIngredient(String ingredient) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "filter.php").newBuilder();
        urlBuilder.addQueryParameter("i", String.valueOf(ingredient));
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return checkResponse(response);
        }
    }

    public Optional<DishResponse> getDeatilsDishById(Long idMeal) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "lookup.php").newBuilder();
        urlBuilder.addQueryParameter("i", String.valueOf(idMeal));
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return checkResponse(response);
        }
    }

    private Optional<DishResponse> checkResponse(Response response) throws IOException {
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            DishResponse dishResponse = objectMapper.readValue(responseBody, new TypeReference<DishResponse>() {
            });
            if (dishResponse.getMeals() != null) {
                return Optional.ofNullable(dishResponse);
            }
        }
        return Optional.empty();
    }
}

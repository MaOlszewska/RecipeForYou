package com.example.demo.Services;

import com.example.demo.model.Beer;
import com.example.demo.model.Params;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Service
public class DishService {
    private static final String BASE_URL = "http://www.themealdb.com/api/json/v1/1/";
    private final OkHttpClient client = new OkHttpClient();

    public String getRandomDish() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "random.php")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    public String getDishByIngredient(String ingredient) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "filter.php").newBuilder();
        urlBuilder.addQueryParameter("i", String.valueOf(ingredient));
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String getDeatilsDishById(Long idMeal) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "lookup.php").newBuilder();
        urlBuilder.addQueryParameter("i", String.valueOf(idMeal));
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

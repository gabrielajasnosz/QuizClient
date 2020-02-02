package com.pdfgenerator.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

public class GetQuestionByGET {
    public static void getByGET(int id) {
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet("http://192.168.0.106:8080/quiz/question/"+id);
        final Gson gson = new Gson();
        try {
            final HttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();
            final String json = EntityUtils.toString(entity);
            /*//czysty json
            System.out.println("Odczytujemy JSON'a z serwera:");
            System.out.println(json);
            */
            final Type type = new TypeToken<QuestionData>(){}.getType();
            final QuestionData file = gson.fromJson(json,type);

            System.out.println("Kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode() == 404) {
                System.out.println("Brak danych do wyswietlenia!");
            } else if(response.getStatusLine().getStatusCode() == 200) {
                //dane zwrocone klasa questiondata
                System.out.printf("Pytanie: %s, Odpowiedzi %s Punkty %s, czy ostatnie %b \n", file.getQuestion(), Arrays.toString(file.getAnswers()),file.getPoints(),file.isLastQuestion());
            }
        } catch (IOException e) {
            System.out.println("Houston, we have a problem with GET");
            e.printStackTrace();
        }
    }
}

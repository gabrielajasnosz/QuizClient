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

public class NetworkRequests {
    public static QuestionData getByGET(int id) throws Exception {
        //budujemy klineta jednorzaowego
        final HttpClient client = HttpClientBuilder.create().build();
        //podajemy link
        final HttpGet request = new HttpGet("http://127.0.0.1:8080/quiz/question/"+id);
        //obiekt do konwersacji json
        final Gson gson = new Gson();
        try {
            // Otrzymujemy odpowiedz od serwera.
            final HttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();
            // Na tym etapie odczytujemy JSON'a, ale jako String.
            final String json = EntityUtils.toString(entity);
            final Type type = new TypeToken<QuestionData>() {
            }.getType();
            final QuestionData files = gson.fromJson(json, type);
            System.out.println("Result - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
              // wyswietl pobrane dane  System.out.printf("Pytanie: %s, Odpowiedzi %s Punkty %s czy ostatnie %b \n", files.getQuestion(), Arrays.toString(files.getAnswers()),files.getPoints(),files.isLastQuestion());
            }
            return files;
        }
        catch (IOException e)
        {
            throw new Exception("Problem z zwróceniem JSONA");
        }
    }
}

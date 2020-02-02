package com.pdfgenerator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pdfgenerator.model.QuestionData;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Main {

    private static void exampleHttpClientForGetMethod(int id) {
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet("http://127.0.0.1:8080/quiz/question/"+id);
        final Gson gson = new Gson();
        try {
            final HttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();
            final String json = EntityUtils.toString(entity);
            System.out.println("Odczytujemy JSON'a z serwera:");
            System.out.println(json);

            final Type type = new TypeToken<QuestionData>(){}.getType();
            final QuestionData file = gson.fromJson(json,type);

            System.out.println("Kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode() == 404) {
                System.out.println("Brak danych do wyswietlenia!");
            } else if(response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Zwrocony z JSONA:");
                System.out.printf("Pytanie: %s, Odpowiedzi %s Punkty %s czy ostatnie %b \n", file.getQuestion(), Arrays.toString(file.getAnswers()),file.getPoints(),file.isLastQuestion());
            }
        } catch (IOException e) {
            System.out.println("Houston, we have a problem with GET");
            e.printStackTrace();
        }
    }

//    private static void exampleHttpClientForPostMethod() {
//
//        final CloseableHttpClient client = HttpClients.createDefault();
//        final HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/api/files/create-file");
//
//        Gson gson = new Gson();
//
//        // Tworzymy obiekt uzytkownika
//        final com.pdfgenerator.model.QuestionData questionData = new com.pdfgenerator.model.QuestionData("Tomasz", "Nowak", "Testowy opis");
//
//        // Serializacja obiektu do JSONa
//        final String json = gson.toJson(questionData);
//
//        try {
//
//            final StringEntity entity = new StringEntity(json);
//            httpPost.setEntity(entity);
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
//
//            final CloseableHttpResponse response = client.execute(httpPost);
//
//            System.out.println("Kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());
//
//            if(response.getStatusLine().getStatusCode() == 404) {
//
//                System.out.println("Prosze poprawic w kontrolerze sciezke do pliku - sciezka jest nieprawidlowa!");
//            } else if(response.getStatusLine().getStatusCode() == 201) {
//
//                final HttpEntity httpEntity = response.getEntity();
//
//                // Na tym etapie odczytujemy JSON'a, ale jako String.
//                final String jsonAsFileMetaData = EntityUtils.toString(httpEntity);
//
//                // Wyswietlamy zawartosc JSON'a na standardowe wyjscie.
//                System.out.println("Odczytujemy JSON'a z serwera:");
//                System.out.println(jsonAsFileMetaData);
//
//                final FileMetaData fileMetaData = gson.fromJson(jsonAsFileMetaData, FileMetaData.class);
//
//                // Dzialamy na obiekcie - mamy dostep do danych, ktore zostaly odczytane z JSON'a
//                System.out.println("Dane zapisanego pliku:");
//                System.out.printf("Data utworzenia pliku: %s \n", fileMetaData.getCreationDate());
//                System.out.printf("Nazwa pliku: %s \n", fileMetaData.getFileName());
//                System.out.printf("Rozmiar pliku: %d B \n", fileMetaData.getSize());
//            }
//
//            client.close();
//        } catch (UnsupportedEncodingException e) {
//
//            System.out.println("Houston, we have a problem with POST unsupported encoding");
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//
//            System.out.println("Houston, we have a problem with POST client protocol");
//            e.printStackTrace();
//        } catch (IOException e) {
//
//            System.out.println("Houston, we have a problem with POST input output");
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {

        // przyklad uzycia HTTP CLIENTA dla zadania typu POST
      //  System.out.println("Test metody POST: \n");
       // exampleHttpClientForPostMethod();

        System.out.println();

        // przyklad uzycia HTTP CLIENTA dla zadania typu GET
        System.out.println("Test metody GET: \n");
        exampleHttpClientForGetMethod(2);
    }
}

package com.pdfgenerator.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
            final Type type = new TypeToken<QuestionData>() {}.getType();
            final QuestionData files = gson.fromJson(json, type);
            System.out.println("");
            System.out.println("Result - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
             //wyswietl pobrane pytanie System.out.printf("Pytanie: %s, Odpowiedzi %s Punkty %s czy ostatnie %b \n", files.getQuestion(), Arrays.toString(files.getAnswers()),files.getPoints(),files.isLastQuestion());
            }
            return files;
        }
        catch (IOException e)
        {
            throw new Exception("Problem z zwróceniem JSONA");
        }
    }

    public static void answerData(final AnswerData answers) throws Exception{

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/quiz/calculate");
        Gson gson = new Gson();
        // Serializacja obiektu do JSONa
        final String json = gson.toJson(answers);
        try {
            final StringEntity entity = new StringEntity(json);
            httpPut.setEntity(entity);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            final CloseableHttpResponse response = client.execute(httpPut);
            System.out.println("answerData - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 400)
            {
                System.err.println("#BLAD 400");
            }
            else if (response.getStatusLine().getStatusCode() == 200)
            {
                System.err.println("Gitara jest #200");
                System.out.println("polecialy dane ");
                System.out.println(answers.getSelectedAnswers());
            }

            client.close();
        } catch (UnsupportedEncodingException e) {

            System.out.println("Houston, we have a problem with POST unsupported encoding");
            e.printStackTrace();
        } catch (ClientProtocolException e) {

            System.out.println("Houston, we have a problem with POST client protocol");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Houston, we have a problem with POST input output");
            e.printStackTrace();
        }
    }
}


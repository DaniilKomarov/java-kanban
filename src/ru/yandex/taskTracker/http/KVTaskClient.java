package ru.yandex.taskTracker.http;
import ru.yandex.taskTracker.file.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String URL;
    private final String API_TOKEN;

    public KVTaskClient(int port) {
        URL = "http://localhost:"+port+"/";
        API_TOKEN = register(URL);
    }
    private String register(String url){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()!=200){
                throw new ManagerSaveException("Во время запроса возникла ошибка " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("во время запроса возникла ошибка ");
        }
    }
    protected void put(String key, String json){
        try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "save/" + key +"?API_TOKEN=" + API_TOKEN))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
            if(response.statusCode()!=200){
                throw new ManagerSaveException("Во время запроса возникла ошибка " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("во время запроса возникла ошибка ");
        }
    }
    protected String load(String key){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "load/" + key +"?API_TOKEN=" + API_TOKEN))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()!=200){
                throw new ManagerSaveException("Во время запроса возникла ошибка " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("во время запроса возникла ошибка ");
        }
    }
}

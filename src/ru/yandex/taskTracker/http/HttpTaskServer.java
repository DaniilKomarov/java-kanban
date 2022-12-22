package ru.yandex.taskTracker.http;
import ru.yandex.taskTracker.*;
import ru.yandex.taskTracker.model.*;
import ru.yandex.taskTracker.adapter.*;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;
import ru.yandex.taskTracker.model.SubTask;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.List;
import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private static final int PORT= 8080;
    private final HttpServer httpServer;
    private final Gson gson;
    private final TaskManager taskManager;
    public HttpTaskServer() throws IOException{
        this(Managers.getDefault());
    }


    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        gson = Managers.getDefaultGson();
        httpServer = HttpServer.create(new InetSocketAddress("localhost",PORT),0);
        httpServer.createContext("/tasks",this::handler);
    }


    public void start() {
        httpServer.start();
    }
    public void stop(){
        System.out.println("Сервер остановлен");
        httpServer.stop(0);
    }


    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
    private void handler(HttpExchange h){
        try{
                System.out.println( "\n/tasks/");
                System.out.println(h.getRequestURI().getPath());
                final String path = h.getRequestURI().getPath().substring(7);
                System.out.println(path);
                switch (path){
                    case "" :{
                        if(!h.getRequestMethod().equals("GET")){
                            System.out.println("Не правильный метод,ожидалось GET,вместо" +h.getRequestMethod());
                            h.sendResponseHeaders(405,0);
                        }else{
                            final String response = gson.toJson(taskManager.getPrioritizedTasks());
                            sendText(h,response);
                        }
                        break;
                    }
                    case "history" :{
                        if(!h.getRequestMethod().equals("GET")) {
                            System.out.println("Не правильный метод,ожидалось GET,вместо " + h.getRequestMethod());
                            h.sendResponseHeaders(405, 0);
                        }else{
                            final String response = gson.toJson(taskManager.getHistory());
                            sendText(h,response);
                        }
                        break;
                    }
                    case "task":{
                        handlerTask(h);
                        break;
                    }
                    case "subtask":{
                        handlerSubTask(h);
                        break;
                    }
                    case "epic":{
                        handlerEpic(h);
                        break;
                    }
                    case "subtask/epic":{
                        if(!h.getRequestMethod().equals("GET")){
                            System.out.println("Не правильный метод,ожидалось GET,вместо" +h.getRequestMethod());
                            h.sendResponseHeaders(405,0);
                        }else{
                            final String query = h.getRequestURI().getQuery();
                            final int id = Integer.parseInt(query.substring(3));
                            Epic epic = taskManager.getEpicById(id);
                            final List<SubTask>  subTaskList = taskManager.getSubTaskEpic(epic);
                            final String response = gson.toJson(subTaskList);
                            sendText(h,response);
                        }
                        break;
                    }
                    default:{
                        System.out.println("Не известный запрос " + h.getRequestURI());
                        h.sendResponseHeaders(404, 0);

                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
            h.close();
        }
        h.close();
    }
    private void handlerTask(HttpExchange h) throws IOException{
        String query = h.getRequestURI().getQuery();
        String method = h.getRequestMethod();
        switch (method){
            case "GET": {
                if (query == null) {
                    final List<Task> taskList = taskManager.getAllListTask();
                    final String response = gson.toJson(taskList);
                    System.out.println("Получили все задачи");
                    sendText(h, response);

                }else{
                    final int id = Integer.parseInt(query.substring(3));
                    final Task task = taskManager.getTaskById(id);
                    final String response = gson.toJson(task);
                    System.out.println("Получили task по айди " + id);
                    sendText(h, response);
                }
                break;
            }
            case "DELETE":{
                if(query == null){
                    taskManager.clearAllTask();
                    System.out.println("Удалены все задачи");
                    h.sendResponseHeaders(200,0);
                }else{
                    final int id = Integer.parseInt(query.substring(3));
                    taskManager.removeTaskById(id);
                    System.out.println("Удален task с айди " + id);
                    h.sendResponseHeaders(200,0);
                }
                break;
            }
            case "POST":{
                InputStream inputStream = h.getRequestBody();
                String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                Task task = gson.fromJson(body,Task.class);
                System.out.println(task);
                if(query == null){
                    taskManager.createTask(task);
                    System.out.println("Создали задчу " +task);
                    h.sendResponseHeaders(200,0);
                }else{
                    taskManager.updateTask(task);
                    System.out.println("Обновили задачу " +task);
                    h.sendResponseHeaders(200,0);
                }
                break;
            }
            default:{
                System.out.println("Не известный запрос " + h.getRequestURI());
                h.sendResponseHeaders(404, 0);

            }
        }
    }
    private void handlerSubTask(HttpExchange h) throws IOException{
        String query = h.getRequestURI().getQuery();
        String method = h.getRequestMethod();
        switch (method){
            case "GET": {
                if (query == null) {
                    final List<SubTask> subTaskList = taskManager.getAllListSubTask();
                    final String response = gson.toJson(subTaskList);
                    System.out.println("Получили все сабтаски");
                    sendText(h, response);

                }else{
                    final int id = Integer.parseInt(query.substring(3));
                    final SubTask subTask = taskManager.getSubTaskById(id);
                    final String response = gson.toJson(subTask);
                    System.out.println("Получили subtask по айди " + id);
                    sendText(h, response);
                }
                break;
            }
            case "DELETE":{
                if(query == null){
                    taskManager.clearAllSubTask();
                    System.out.println("Удалены все сабтаски");
                    h.sendResponseHeaders(200,0);
                }else{
                    final int id = Integer.parseInt(query.substring(3));
                    taskManager.removeSubTaskById(id);
                    System.out.println("Удален subtask с айди " + id);
                    h.sendResponseHeaders(200,0);
                }
                break;
            }
            case "POST":{
                InputStream inputStream = h.getRequestBody();
                String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                SubTask subTask = gson.fromJson(body,SubTask.class);
                if(query == null){
                    taskManager.createSubTask(subTask);
                    System.out.println("Создали задчу " +subTask);
                    h.sendResponseHeaders(200,0);
                }else{
                    taskManager.updateSubTask(subTask);
                    System.out.println("Обновили задачу " +subTask);
                    h.sendResponseHeaders(200,0);
                }
                break;
            }
            default:{
                System.out.println("Не известный запрос " + h.getRequestURI());
                h.sendResponseHeaders(404, 0);

            }
        }
    }
    private void handlerEpic(HttpExchange h) throws IOException{
        String query = h.getRequestURI().getQuery();
        String method = h.getRequestMethod();
        switch (method){
            case "GET": {
                if (query == null) {
                    final List<Epic> epicList = taskManager.getAllListEpic();
                    final String response = gson.toJson(epicList);
                    System.out.println("Получили все эпики");
                    sendText(h, response);

                }else{
                    final int id = Integer.parseInt(query.substring(3));
                    final Epic epic = taskManager.getEpicById(id);
                    final String response = gson.toJson(epic);
                    System.out.println("Получили epic по айди " + id);
                    sendText(h, response);
                }
                break;
            }
            case "DELETE":{
                if(query == null){
                    taskManager.clearAllEpic();
                    System.out.println("Удалены все эпики");
                    h.sendResponseHeaders(200,0);
                }else{
                    final int id = Integer.parseInt(query.substring(3));
                    taskManager.removeEpicById(id);
                    System.out.println("Удален epic с айди " + id);
                    h.sendResponseHeaders(200,0);
                }
                break;
            }
            case "POST":{
                InputStream inputStream = h.getRequestBody();
                String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                Epic epic = gson.fromJson(body,Epic.class);
                if(query == null){
                    taskManager.createEpic(epic);
                    System.out.println("Создали задчу " +epic);
                    h.sendResponseHeaders(200,0);
                }else{
                    taskManager.updateEpic(epic);
                    System.out.println("Обновили задачу " +epic);
                    h.sendResponseHeaders(200,0);
                }
                break;
            }
            default:{
                System.out.println("Не известный запрос " + h.getRequestURI());
                h.sendResponseHeaders(404, 0);

            }
        }
    }
}

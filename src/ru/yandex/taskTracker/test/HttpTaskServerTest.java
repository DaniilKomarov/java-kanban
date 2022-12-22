package ru.yandex.taskTracker.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.InMemoryTaskManager;
import ru.yandex.taskTracker.Managers;
import ru.yandex.taskTracker.TaskManager;
import ru.yandex.taskTracker.http.HttpTaskServer;
import ru.yandex.taskTracker.http.KVServer;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.model.TaskStatus;
import java.time.LocalDateTime;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest  {
    Task task;
    SubTask subTask;
    Epic epic;
    KVServer kvServer;
    HttpTaskServer server;
    
    Gson gson = Managers.getDefaultGson();
    TaskManager taskManager;

    @BeforeEach
    void setUp() throws IOException {
        taskManager = new InMemoryTaskManager();
        kvServer = Managers.getDefaultKVServer();
        kvServer.start();
        task = new Task("task","task test", TaskStatus.NEW,50,null);
        epic = new Epic("epic","epic test",0,null);
        subTask = new SubTask("subTask","subtask test",TaskStatus.IN_PROGRESS,1,50,
                LocalDateTime.of(2122,1,1,0,0));
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        taskManager.createTask(task);

        server = new HttpTaskServer(taskManager);
        server.start();
        

    }
    @AfterEach
    void stop(){
        server.stop();
        kvServer.stop();
    }
    @Test
    void endPointCreateTasks() throws IOException, InterruptedException {
        assertEquals(1,taskManager.getAllListTask().size(),"Не правильный начальный список");
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        String json = gson.toJson(task);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(2,taskManager.getAllListTask().size(),"Пустой список");
    }
    @Test
    void endPointCreateEpic() throws IOException, InterruptedException {
        assertEquals(1,taskManager.getAllListEpic().size(),"Не правильный начальный список");
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        String json = gson.toJson(epic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(2,taskManager.getAllListEpic().size(),"Пустой список");
    }
    @Test
    void endPointCreateSubTask() throws IOException, InterruptedException {
        assertEquals(1,taskManager.getAllListSubTask().size(),"Не правильный начальный список");
        HttpClient client1 = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/subtask");
        String json1 = gson.toJson(subTask);
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).POST(body1).build();
        HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(2,taskManager.getAllListSubTask().size(),"Пустой список");
    }


    @Test
    void endPointGetTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String list = gson.toJson(taskManager.getAllListTask());
        assertEquals(list,response.body());
    }
    @Test
    void endPointGetSubTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String list = gson.toJson(taskManager.getAllListSubTask());
        assertEquals(list,response.body());
    }
    @Test
    void endPointGetEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String list = gson.toJson(taskManager.getAllListEpic());
        assertEquals(list,response.body());
    }
    @Test
    void endPointGetPrioritizedTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String list = gson.toJson(taskManager.getPrioritizedTasks());
        assertEquals(list,response.body());
    }
    @Test
    void endPointDeleteAllTask() throws IOException, InterruptedException {
        assertEquals(1,taskManager.getAllListTask().size(),"Не верный начальный список");
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(0,taskManager.getAllListTask().size(),"Задачи не удалены");
    }
    @Test
    void endPointDeleteAllSubTasks() throws IOException, InterruptedException {
        assertEquals(1,taskManager.getAllListSubTask().size(),"Не верный начальный список");
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(0,taskManager.getAllListSubTask().size(),"Задачи не удалены");
    }
    @Test
    void endPointDeleteAllEpics() throws IOException, InterruptedException {
        assertEquals(1,taskManager.getAllListEpic().size(),"Не верный начальный список");
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(0,taskManager.getAllListEpic().size(),"Задачи не удалены");
    }
    @Test
    void endPointDeleteTask() throws IOException, InterruptedException{
        assertEquals(task,taskManager.getTaskById(3),"Не верная задача");
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNull(taskManager.getTaskById(3),"Задача не удалены");
    }
    @Test
    void endPointDeleteSubTask() throws IOException, InterruptedException{
        assertEquals(subTask,taskManager.getSubTaskById(2),"Не верная задача");
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNull(taskManager.getSubTaskById(2),"Задача не удалены");
    }
    @Test
    void endPointDeleteEpic() throws IOException, InterruptedException{
        assertEquals(epic,taskManager.getEpicById(1),"Не верная задача");
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNull(taskManager.getEpicById(1),"Задача не удалены");
    }
    @Test
    void endPointGetTask() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task task1 = gson.fromJson(response.body(),Task.class);
        assertEquals(task,task1);
    }
    @Test
    void endPointGetSubTask() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        SubTask subTask1 = gson.fromJson(response.body(),SubTask.class);
        assertEquals(subTask,subTask1);
    }
    @Test
    void endPointGetEpic() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Epic epic1 = gson.fromJson(response.body(),Epic.class);
        assertEquals(epic,epic1);
    }
    @Test
    void endPointGetHistory() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String list = gson.toJson(taskManager.getHistory());
        assertEquals(list,response.body());
    }
    @Test
    void endPointGetSubTasksEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/epic?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String list = gson.toJson(taskManager.getSubTaskEpic(epic));
        assertEquals(list,response.body());
    }


}
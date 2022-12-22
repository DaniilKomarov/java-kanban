package ru.yandex.taskTracker.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.Managers;
import ru.yandex.taskTracker.file.FileBackedTasksManager;
import ru.yandex.taskTracker.http.HttpTaskManager;
import ru.yandex.taskTracker.http.KVServer;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    KVServer kvServer;
    @BeforeEach
    void setUp() throws IOException {
        kvServer = Managers.getDefaultKVServer();
        kvServer.start();
        taskManager = new HttpTaskManager(KVServer.PORT);
        initTask();
    }
    @AfterEach
    void stop(){
        kvServer.stop();
    }



    @Test
    void loadFromServerWithEmptyHistory(){

        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        taskManager.createTask(task);
        HttpTaskManager tasksManager2 = new HttpTaskManager(KVServer.PORT);
        tasksManager2.load();
        List<Epic> epicList = tasksManager2.getAllListEpic();
        List<Task> tasksHistory = tasksManager2.getHistory();
        assertEquals(1,epicList.size(),"Список пустой");
        assertEquals(0,tasksHistory.size(),"Не верная история");

    }
    @Test
    void loadFromServerWithEmptySubtask(){

        taskManager.createEpic(epic);
        HttpTaskManager tasksManager2 = new HttpTaskManager(KVServer.PORT);
        tasksManager2.load();
        List<Epic> epicList = tasksManager2.getAllListEpic();
        assertEquals(1,epicList.size(),"Пустой список с эпиками");
        List<SubTask> subTaskEpic= tasksManager2.getSubTaskEpic(epic);
        assertEquals(0,subTaskEpic.size(),"Не верное количество сабтасков");

    }

    @Test
    void loadFromServer(){

        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        taskManager.createTask(task);
        taskManager.getTaskById(3);
        taskManager.getSubTaskById(2);
        taskManager.getEpicById(1);
        HttpTaskManager tasksManager2 = new HttpTaskManager(KVServer.PORT);
        tasksManager2.load();
        List<Epic> epicList = tasksManager2.getAllListEpic();
        List<Task> tasksHistory = tasksManager2.getHistory();
        assertEquals(1,epicList.size(),"Список пустой");
        assertEquals(2,tasksHistory.size(),"Не верная история");

    }


}

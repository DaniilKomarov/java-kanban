package ru.yandex.taskTracker.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.FileBackedTasksManager;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.test.TaskManagerTest;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    File file;
    @BeforeEach
    void setUp(){
        file = new File("resources/pam");
        taskManager = new FileBackedTasksManager(file);
        initTask();
    }
    @AfterEach
    void deleteFile(){
        file.delete();
    }

    @Test
    void loadFromFileWithEmptyHistory(){
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        taskManager.createTask(task);
        FileBackedTasksManager tasksManager2 = FileBackedTasksManager.loadFromFile(file);
        List<Epic> epicList = tasksManager2.getAllListEpic();
        List<Task> tasksHistory = tasksManager2.getHistory();
        assertEquals(1,epicList.size(),"Список пустой");
        assertEquals(0,tasksHistory.size(),"Не верная история");
    }
    @Test
    void loadFromFileWithEmptySubtask(){
        taskManager.createEpic(epic);
        FileBackedTasksManager tasksManager2 = FileBackedTasksManager.loadFromFile(file);
        List<Epic> epicList = tasksManager2.getAllListEpic();
        assertEquals(1,epicList.size(),"Пустой список с эпиками");
        List<SubTask> subTaskEpic= tasksManager2.getSubTaskEpic(epic);
        assertEquals(0,subTaskEpic.size(),"Не верное количество сабтасков");
    }
    @Test
    void loadFromFileWithEmptyTask(){
        FileBackedTasksManager tasksManager2 = FileBackedTasksManager.loadFromFile(file);
        List<Epic> epicList = tasksManager2.getAllListEpic();
        List<Task> taskList = tasksManager2.getAllListTask();
        List<SubTask> subTaskList = tasksManager2.getAllListSubTask();
        assertEquals(0,taskList.size(),"Не верное количество тасков");
        assertEquals(0,subTaskList.size(),"Не верное количество сабтасков");
        assertEquals(0,epicList.size(),"Не верное количество эпиков");
    }
    @Test
    void loadFromFile(){
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        taskManager.createTask(task);
        taskManager.getTaskById(3);
        taskManager.getSubTaskById(2);
        taskManager.getEpicById(1);
        FileBackedTasksManager tasksManager2 = FileBackedTasksManager.loadFromFile(file);
        List<Epic> epicList = tasksManager2.getAllListEpic();
        List<Task> tasksHistory = tasksManager2.getHistory();
        assertEquals(1,epicList.size(),"Список пустой");
        assertEquals(2,tasksHistory.size(),"Не верная история");

    }

}
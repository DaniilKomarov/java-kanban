package ru.yandex.taskTracker.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.history.HistoryManager;
import ru.yandex.taskTracker.history.InMemoryHistoryManager;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    Task task;
    SubTask subTask;
    Epic epic;
    HistoryManager historyManager;
    @BeforeEach
    void setUp(){
        historyManager = new InMemoryHistoryManager();
        task = new Task("task","task test", TaskStatus.NEW,50,null);
        epic = new Epic("epic","epic test",0,null);
        subTask = new SubTask("subTask","subtask test",TaskStatus.IN_PROGRESS,2,50,
                LocalDateTime.of(2122,1,1,0,0));
        epic.setId(2);
        subTask.setId(3);

    }

    @Test
    void addTest() {
        assertEquals(0,historyManager.getHistory().size(),"История не пустая");
        historyManager.add(task);
        assertEquals(1,historyManager.getHistory().size(),"Не верное количесвто тасков в листе");
        historyManager.add(epic);
        assertEquals(2,historyManager.getHistory().size(),"Не верное количесвто тасков в листе");
    }
    @Test
    void addTwiceTest(){
        assertEquals(0,historyManager.getHistory().size(),"История не пустая");
        historyManager.add(task);
        historyManager.add(task);
        assertEquals(1,historyManager.getHistory().size(),"Не верное количесвто тасков в листе");
    }

    @Test
    void removeTest() {
        assertEquals(0,historyManager.getHistory().size(),"История не пустая");
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);
        assertEquals(3,historyManager.getHistory().size(),"История неправильно добавилась");
        assertEquals(subTask,historyManager.getHistory().get(2),"Таск не на той позиции");
        historyManager.remove(subTask.getId());
        assertEquals(2,historyManager.getHistory().size(),"Таска не удалилась");
        assertFalse(historyManager.getHistory().contains(subTask),"Удалилась неправильная таска");
        assertEquals(task,historyManager.getHistory().get(0),"Таск не на той позиции");
        historyManager.remove(task.getId());
        assertEquals(1,historyManager.getHistory().size(),"Эадача не удалилась");
        assertFalse(historyManager.getHistory().contains(task),"Удалилась неправильная задача");

    }
    @Test
    void removeMiddleTaskTest(){
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);
        assertEquals(epic,historyManager.getHistory().get(1),"Не верная таска по середине");
        historyManager.remove(epic.getId());
        assertEquals(2,historyManager.getHistory().size(),"Таска не удалилась");
        assertFalse(historyManager.getHistory().contains(epic),"Удалилась неправильная таска");
    }

    @Test
    void getHistoryTest() {
        assertEquals(0,historyManager.getHistory().size(),"История не пустая");
        historyManager.add(task);
        assertEquals(1,historyManager.getHistory().size(),"История не пустая");
        historyManager.add(epic);
        assertEquals(2,historyManager.getHistory().size(),"История не пустая");
    }
}
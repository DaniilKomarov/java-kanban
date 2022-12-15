package ru.yandex.taskTracker.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.InMemoryTaskManager;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.TaskStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @BeforeEach
    public void setUp(){
        taskManager = new InMemoryTaskManager();
        initTask();
    }
    @Test
    void createStatusEpicTest(){
        taskManager.createEpic(epic);
        assertEquals(TaskStatus.NEW,epic.getStatus(),"Не верный статус");
        taskManager.createSubTask(subTask);
        SubTask subTask1 = new SubTask("subTask1","subtask1 test1",TaskStatus.IN_PROGRESS,1,
                50, LocalDateTime.of(2142,1,1,0,0));
        taskManager.createSubTask(subTask1);
        assertEquals(TaskStatus.IN_PROGRESS,epic.getStatus(),"Не верный статус");
        subTask.setStatus(TaskStatus.NEW);
        subTask1.setStatus(TaskStatus.NEW);
        taskManager.clearAllSubTask();
        taskManager.createSubTask(subTask);
        taskManager.createSubTask(subTask1);
        assertEquals(TaskStatus.NEW,epic.getStatus(),"Не верный статус");
        subTask.setStatus(TaskStatus.DONE);
        subTask1.setStatus(TaskStatus.DONE);
        taskManager.clearAllSubTask();
        taskManager.createSubTask(subTask);
        taskManager.createSubTask(subTask1);
        assertEquals(TaskStatus.DONE,epic.getStatus(),"Не верный статус");
        subTask.setStatus(TaskStatus.NEW);
        taskManager.clearAllSubTask();
        taskManager.createSubTask(subTask);
        taskManager.createSubTask(subTask1);
        assertEquals(TaskStatus.IN_PROGRESS,epic.getStatus(),"Не верный статус");
    }

}
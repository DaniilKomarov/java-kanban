package ru.yandex.taskTracker.test;

import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.TaskManager;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected SubTask subTask;

    protected void initTask(){
        task = new Task("task","task test", TaskStatus.NEW,50,null);
        epic = new Epic("epic","epic test",0,null);
        subTask = new SubTask("subTask","subtask test",TaskStatus.IN_PROGRESS,1,50,
                LocalDateTime.of(2122,1,1,0,0));
    }

    @Test
    void createTaskTest(){
        taskManager.createTask(task);
        final int id = task.getId();
        final Task taskTest = taskManager.getTaskById(id);
        assertNotNull(taskTest,"Задачи не найдена");
        assertEquals(task,taskTest,"Задачи не совподают");

    }
    @Test
    void crateSubTaskTest(){
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        final  int id = subTask.getId();
        final SubTask subTaskTest = taskManager.getSubTaskById(id);
        assertNotNull(subTaskTest,"Задачи не найдена");
        assertEquals(subTask,subTaskTest,"Задачи не совподают");
    }
    @Test
    void crateEpicTest(){
        taskManager.createEpic(epic);
        final  int id = epic.getId();
        final Epic epicTest = taskManager.getEpicById(id);
        assertNotNull(epicTest,"Задачи не найдена");
        assertEquals(epic,epicTest,"Задачи не совподают");
    }
    @Test
    void getAllTaskListTest(){
        final List<Task> taskListEmptyTest = taskManager.getAllListTask();
        assertEquals(0,taskListEmptyTest.size(),"Список не пустой");
        taskManager.createTask(task);
        final List<Task> taskListTest = taskManager.getAllListTask();
        assertEquals(1,taskListTest.size(),"Пустой список");
        assertEquals(task,taskListTest.get(0),"Задачи не совпадают");
    }
    @Test
    void getAllSubTaskListTest(){
        final List<SubTask> subTaskListEmptyTest = taskManager.getAllListSubTask();
        assertEquals(0,subTaskListEmptyTest.size(),"Список не пустой");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        final List<SubTask> subTaskListTest = taskManager.getAllListSubTask();
        assertEquals(1,subTaskListTest.size(),"Пустой список");
        assertEquals(subTask,subTaskListTest.get(0),"Задачи не совпадают");
        assertNotNull(subTaskListTest,"Задачи не возвращаются");
    }
    @Test
    void getAllEpicListTest(){
        final List<Epic> epicListEmptyTest = taskManager.getAllListEpic();
        assertEquals(0,epicListEmptyTest.size(),"Список не пустой");
        taskManager.createEpic(epic);
        final List<Epic> epicListTest = taskManager.getAllListEpic();
        assertEquals(1,epicListTest.size(),"Пустой список");
        assertEquals(epic,epicListTest.get(0),"Задачи не совпадают");
        assertNotNull(epicListTest,"Задачи не возвращаются");
    }
    @Test
    void getTaskByIdTest(){
        taskManager.createTask(task);
        final Task taskTest = taskManager.getTaskById(1);
        assertNotNull(taskTest,"Задача не возвращается");
        assertEquals(task,taskTest,"Выводится неправильная задача");
        assertNull(taskManager.getTaskById(5),"Получена не существующая задача");
    }
    @Test
    void getSubTaskByIdTest(){
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        final SubTask subTaskTest = taskManager.getSubTaskById(2);
        assertNotNull(subTaskTest,"Задача не возвращается");
        assertEquals(subTask,subTaskTest,"Выводится неправильная задача");
        assertNull(taskManager.getSubTaskById(5),"Получена не существующая задача");
    }
    @Test
    void getEpicByIdTest(){
        taskManager.createEpic(epic);
        final Epic epicTest = taskManager.getEpicById(1);
        assertNotNull(epicTest,"Задача не возвращается");
        assertEquals(epic,epicTest,"Выводится неправильная задача");
        assertNull(taskManager.getEpicById(5),"Получена не существующая задача");
    }
    @Test
    void clearAllTaskTest(){
        final List<Task> tasks = taskManager.getAllListTask();
        assertEquals(0,tasks.size(),"Неверное количество тасков");
        taskManager.createTask(task);
        taskManager.clearAllTask();
        final List<Task> tasks1 = taskManager.getAllListTask();
        assertEquals(0,tasks1.size(),"таски не удалились");
    }
    @Test
    void clearAllSubTaskTest(){
        final List<SubTask> subTasks = taskManager.getAllListSubTask();
        assertEquals(0,subTasks.size(),"Неверное количество саб тасков");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        taskManager.clearAllSubTask();
        final List<SubTask> subTasks1 = taskManager.getAllListSubTask();
        assertEquals(0,subTasks1.size(),"Саб таски не удалились");
    }
    @Test
    void clearAllEpicTest(){
        final List<Epic> epics = taskManager.getAllListEpic();
        assertEquals(0,epics.size(),"Неверное количество эпиков");
        taskManager.createEpic(epic);
        taskManager.clearAllEpic();
        final List<Epic> epics1 = taskManager.getAllListEpic();
        assertEquals(0,epics1.size(),"Эпики не удалились");
    }

    @Test
    void updateTaskTest(){
        taskManager.createTask(task);
        final Task taskTest = new Task("task1","task1 test1", TaskStatus.NEW,50,null);
        taskTest.setId(1);
        taskManager.updateTask(taskTest);
        assertNotEquals(task,taskManager.getTaskById(1),"Задача не изменилась");

    }
    @Test
    void updateSubTaskTest(){
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        final SubTask subTaskTest = new SubTask("subTask1","subtask1 test2",TaskStatus.NEW,1,
                50, LocalDateTime.of(2132,1,1,0,0));
        subTaskTest.setId(2);
        taskManager.updateSubTask(subTaskTest);
        assertNotEquals(subTask,taskManager.getSubTaskById(2),"Задача не изменилась");

    }
    @Test
    void updateEpicTest(){
        taskManager.createEpic(epic);
        final Epic epicTest = new Epic("epic2","epic3 test3",0,null);
        epicTest.setId(1);
        taskManager.updateEpic(epicTest);
        assertNotEquals(epic,taskManager.getEpicById(1),"Задача не изменилась");

    }
    @Test
    void removeTaskById(){
        taskManager.createTask(task);
        taskManager.getTaskById(1);
        List<Task> taskList= taskManager.getAllListTask();
        final int id = taskList.get(0).getId();
        taskManager.removeTaskById(id);
        taskList = taskManager.getAllListTask();
        assertEquals(0,taskList.size(),"Эллемент не удален");
    }
    @Test
    void removeSubTaskById(){
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        taskManager.getSubTaskById(2);
        List<SubTask> subTaskList = taskManager.getAllListSubTask();
        int id = subTaskList.get(0).getId();
        taskManager.removeSubTaskById(id);
        subTaskList= taskManager.getAllListSubTask();
        assertEquals(0,subTaskList.size(),"Эллемент не удален");
    }
    @Test
    void removeEpicById(){
        taskManager.createEpic(epic);
        taskManager.getEpicById(1);
        List<Epic> epicList= taskManager.getAllListEpic();
        int id = epicList.get(0).getId();
        taskManager.removeEpicById(id);
        epicList= taskManager.getAllListEpic();
        assertEquals(0,epicList.size(),"Эллемент не удален");
    }
    @Test
    void getSubTasksEpicTest(){
        taskManager.createEpic(epic);
        final List<SubTask> subTaskListEmpty = taskManager.getSubTaskEpic(epic);
        assertEquals(0,subTaskListEmpty.size(),"Не верное количество сабтасков у эпика");
        taskManager.createSubTask(subTask);
        final List<SubTask> subTaskList = taskManager.getSubTaskEpic(epic);
        assertEquals(1,subTaskList.size(),"Не верное количество сабтасков у эпика");
    }
    @Test
    void getPrioritizedTasksTest(){
        final Set<Task> prioritizedTasksEmpty = taskManager.getPrioritizedTasks();
        assertEquals(0,prioritizedTasksEmpty.size(),"Не верное количество задач в множестве");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        taskManager.createTask(task);
        final Set<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
        assertEquals(3,prioritizedTasks.size(),"Не верное количество задач в множестве");

    }



}

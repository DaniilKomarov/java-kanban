package ru.yandex.taskTracker;

import ru.yandex.taskTracker.model.Task;

public class Node {
    public Task task;
    public Node next;
    public Node prev;

    public Node(Task task, Node next, Node prev) {
        this.task = task;
        this.next = next;
        this.prev = prev;
    }

}

package ru.yandex.taskTracker;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException() {
    }

    public ManagerSaveException(String e) {
        super(e);
    }
}

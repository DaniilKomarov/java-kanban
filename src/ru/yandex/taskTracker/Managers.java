package ru.yandex.taskTracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.taskTracker.adapter.LocalDateTimeDeserializer;
import ru.yandex.taskTracker.adapter.LocalDateTimeSerialization;
import ru.yandex.taskTracker.history.HistoryManager;
import ru.yandex.taskTracker.history.InMemoryHistoryManager;
import ru.yandex.taskTracker.http.HttpTaskManager;
import ru.yandex.taskTracker.http.KVServer;


import java.io.IOException;
import java.time.LocalDateTime;

public class Managers {
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
    public static TaskManager getDefault(){
        return new HttpTaskManager(KVServer.PORT);
    }
    public static KVServer getDefaultKVServer() throws IOException{
        final KVServer kvServer = new KVServer();
        return kvServer;
    }
    public static Gson getDefaultGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerialization());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class,new LocalDateTimeDeserializer());
        Gson gson = gsonBuilder.create();
        return gson;
    }


}

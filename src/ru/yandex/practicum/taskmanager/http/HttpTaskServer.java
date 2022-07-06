package ru.yandex.practicum.taskmanager.http;
import ru.yandex.practicum.taskmanager.manager.*;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

import ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager;

public class HttpTaskServer  extends FileBackedTasksManager {
    private static final int PORT = 8080;
    private HttpServer server;
    private static TaskManager manager;

    public HttpTaskServer( String fileName) throws IOException {
        super(fileName);
        manager = this;
        server = HttpServer.create();
        loadFromFile();
    }

    @Override
    public void save() {

    }

    public void startServer() throws IOException {
        server.bind(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler());
        server.start();
       // kvServer.start();
        System.out.println("Cервер открыт на порту " + PORT);
    }

    public void serverStop(){
        server.stop(0);
    }

    protected static TaskManager getManager() {
        return manager;
    }
}

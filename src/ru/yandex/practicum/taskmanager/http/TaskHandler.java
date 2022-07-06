package ru.yandex.practicum.taskmanager.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager;
import ru.yandex.practicum.taskmanager.manager.Manager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;

public class TaskHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String request ="";
        TaskManager manager = HttpTaskServer.getManager();
        String method = exchange.getRequestMethod();
        URI requestURI = exchange.getRequestURI();
        String path = requestURI.getPath();
        String query = requestURI.getQuery();
        InputStream body = exchange.getRequestBody();

        switch (method) {
            case "GET":
                request = ParseRequest.parseGetRequest(manager, query, path);
                break;
            case "POST" :
                //request = ParseRequest.parsePostRequest(manager, body, path);
                try {
                    request = ParseRequest.parsePostRequest(manager, body, query, path);
                } catch (ValidationTimeException e) {

                }
                exchange.sendResponseHeaders(200, 0);
                try ( OutputStream os = exchange.getResponseBody()) {
                    os.write(request.getBytes(StandardCharsets.UTF_8));
                }
                return;
            case "DELETE" :
                request = ParseRequest.parseDeleteRequest(manager, query, path);
                break;
            default:
                request = "Метод " + method + " не опознан";
        }
        exchange.sendResponseHeaders(200, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(request.getBytes());
        }
    }
}

package com.example.server;

import java.net.URI;
import java.util.Scanner;

import javax.websocket.*;

@ClientEndpoint
public class ChatClient {

    private static Session userSession;
    private Scanner scanner;

    public ChatClient() {
        try {
            // Создаем новое WebSocket подключение к серверу чата
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            userSession = container.connectToServer(this, new URI("ws://localhost:8080/chat"));
            System.out.println("WebSocket connection established.");
            // Запускаем ввод сообщений с консоли
            scanner = new Scanner(System.in);
            String message = "";
            while (!message.equals("exit")) {
                message = scanner.nextLine();
                userSession.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            System.out.println("Error connecting to chat: " + e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(String message) {
        // Обрабатываем полученное сообщение от сервера
        System.out.println(message);
    }

}
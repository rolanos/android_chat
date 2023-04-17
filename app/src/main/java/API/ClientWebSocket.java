package API;

import android.util.Log;

import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;

import Models.Message;
import tech.gusavila92.websocketclient.WebSocketClient;

//connection to server websocket
public class ClientWebSocket {
    public WebSocketClient webSocketClient;
    public void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://10.0.2.2:8080/chat");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {

            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
                Message message = new Message("Ivan", 1, 2, "Hey IT S FIRST MESSAGE");
                //webSocketClient.addHeader("user_id", String.valueOf(nameKey));
                Gson gson = new Gson();
                String obj = gson.toJson(message);
                webSocketClient.send(obj);
            }
            @Override
            public void onTextReceived(String s) {
                Gson gson = new Gson();
                Message message = gson.fromJson(s, Message.class);
                System.out.println("Сообщение от сервера пришло: " + message.getUserName() + message.getText());
                //Как-то изменить UI при получении сообщения
            }

            @Override
            public void onBinaryReceived(byte[] data) {
            }

            @Override
            public void onPingReceived(byte[] data) {
            }

            @Override
            public void onPongReceived(byte[] data) {
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
                System.out.println("onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }
    //send message to chat
    public void sendMessage(Message message){
        Gson gson = new Gson();
        String obj = gson.toJson(message);
        webSocketClient.send(obj);
    }
}

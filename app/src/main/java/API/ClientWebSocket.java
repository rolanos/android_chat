package API;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.RoomActivity;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import Models.Message;
import tech.gusavila92.websocketclient.WebSocketClient;

//connection to server websocket
public class ClientWebSocket {
    private RoomActivity activity;
    public WebSocketClient webSocketClient;
    public ListView messageList;
    public ArrayAdapter<String> adapter;

    public ArrayList<Message> MessagesList;
    public Message newMessage;

    public ClientWebSocket(RoomActivity activity){
        this.activity = activity;
    }
    public void setAdapterParam(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    public void send(Message message){
        Gson gson = new Gson();
        String obj = gson.toJson(message);
        webSocketClient.send(obj);
    }
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
            }
            @Override
            public void onTextReceived(String s) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Message message = null;
                        try {
                            message = gson.fromJson(s, Message.class);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        System.out.println("Сообщение от сервера пришло: " + message.getUserName() + message.getText());
                        newMessage=message;
                        MessagesList.add(newMessage);
                    }
                });
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

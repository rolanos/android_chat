package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

import API.ClientWebSocket;
import Models.Chat;
import Models.Message;


public class RoomActivity extends AppCompatActivity {

    private EditText messageBox;

    private TextView send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ClientWebSocket webSocket = new ClientWebSocket(this);
        webSocket.createWebSocketClient();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        webSocket.messageList = findViewById(R.id.messageList);
        messageBox = findViewById(R.id.messageBox);
        send = findViewById(R.id.send);

        Integer id = getIntent().getIntExtra("id", 0);
        Integer userId = getIntent().getIntExtra("userId", 0);
        String userName = getIntent().getStringExtra("userName");
        ArrayList<Message> messagesList = Chat.getChatContent(id);
        ArrayList<String> names = new ArrayList<>();
        if(messagesList != null){
            for (Message i:messagesList) {
                String s = i.getUserName() + '\n' + i.getText();
                names.add(s);
            }
        }



        webSocket.setAdapterParam(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));
        webSocket.messageList.setAdapter(webSocket.adapter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String message = messageBox.getText().toString();



                if (!message.isEmpty()) {


                    Message newMessage = new Message(userName, userId, id, message, new Date());
                    webSocket.send(newMessage);
                    messageBox.setText("");
                    return;
                }
            }
        });
    }
}
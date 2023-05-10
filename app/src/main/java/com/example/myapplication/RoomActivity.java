package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import API.ClientWebSocket;
import Models.Chat;
import Models.Message;


public class RoomActivity extends AppCompatActivity {

    private EditText messageBox;

    private ImageButton send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ClientWebSocket webSocket = new ClientWebSocket(this);
        webSocket.createWebSocketClient();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        messageBox = findViewById(R.id.messageBox);
        send = findViewById(R.id.send);

        Integer id = getIntent().getIntExtra("id", 0);
        Integer userId = getIntent().getIntExtra("userId", 0);
        String userName = getIntent().getStringExtra("userName");
        String chatName = getIntent().getStringExtra("chatName");
        webSocket.chatId = id;
                ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(chatName);

        webSocket.MessagesList = Chat.getChatContent(id);

        ListView listView = findViewById(R.id.messageList);
        webSocket.adapter = new CustomAdapter(this, webSocket.MessagesList, userId);
        listView.setAdapter(webSocket.adapter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String message = messageBox.getText().toString();



                if (!message.isEmpty()) {


                    Message NewMessage = new Message(userName, userId, id, message, new Date());
                    webSocket.send(NewMessage);
                    messageBox.setText("");
                    return;
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
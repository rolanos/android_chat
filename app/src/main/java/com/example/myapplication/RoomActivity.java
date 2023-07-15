package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.Date;

import API.ClientWebSocket;
import Models.Chat;
import Models.Message;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;


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
        Observable<Object> observable = RxView.clicks(send);

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


        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                String message = messageBox.getText().toString();
                Message NewMessage = new Message(userName, userId, id, message, new Date());
                webSocket.send(NewMessage);
                messageBox.setText("");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getBaseContext(), "Error",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribeWith(observer);
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
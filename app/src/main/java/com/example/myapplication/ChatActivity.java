package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Models.Chat;

public class ChatActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = findViewById(R.id.listView);

        Integer id = getIntent().getIntExtra("id", 0);
        String userName = getIntent().getStringExtra("userName");
        ArrayList<Chat> chats = Chat.getChats(id);
        ArrayList<String> names = new ArrayList<>();
        for (Chat i: chats) {
            names.add(i.getName());
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = mAdapter.getItem(position);
                Integer idChat = 0;
                for (Chat i:chats) {
                    if (name == i.getName()){
                        idChat = i.getID();
                    }
                }
                // запускаем новый экран с выбранным именем
                Intent intent = new Intent(ChatActivity.this, RoomActivity.class);
                intent.putExtra("id", idChat);
                intent.putExtra("userId", id);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }
}
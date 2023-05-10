package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Models.Message;

public class CustomAdapter extends BaseAdapter {
    private static final int TYPE_MY_MESSAGE = 0;
    private static final int TYPE_OTHER_MESSAGE = 1;

    private ArrayList<Message> messages;
    private LayoutInflater inflater;

    private int UserId;

    public CustomAdapter(Context context, ArrayList<Message> messages, int userId) {
        this.messages = messages;
        UserId=userId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getUserID()==UserId) {
            return TYPE_MY_MESSAGE;
        } else {
            return TYPE_OTHER_MESSAGE;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int type = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case TYPE_MY_MESSAGE:
                    convertView = inflater.inflate(R.layout.my_message, parent, false);
                    holder.textView = convertView.findViewById(R.id.message_body);
                    break;
                case TYPE_OTHER_MESSAGE:
                    convertView = inflater.inflate(R.layout.their_message, parent, false);
                    holder.textView = convertView.findViewById(R.id.message_body);
                    holder.name = convertView.findViewById(R.id.name);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Message message = messages.get(position);
        switch (type) {
            case TYPE_MY_MESSAGE:
                holder.textView.setText(message.getText());
                break;
            case TYPE_OTHER_MESSAGE:
                holder.textView.setText(message.getText());
                holder.name.setText(message.getUserName());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
        TextView name;
    }
}
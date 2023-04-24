package Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Chat {
    private int id;
    private String name;
    public Chat(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public static ArrayList<Chat> getChats(int userID){
        Gson gson = new Gson();
        try{
            String url ="http://10.0.2.2:8080/get-chats?participant_id="+Integer.toString(userID);
            ServletTask task = new ServletTask();
            String result = task.execute(url).get();
            return gson.fromJson(result, new TypeToken<ArrayList<Chat>>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<Message> getChatContent(int chatID){
        Gson gson = new Gson();
        try{
            String url ="http://10.0.2.2:8080/get-chats-content?chat_id="+Integer.toString(chatID);
            ServletTask task = new ServletTask();
            String result = task.execute(url).get();
            ArrayList<Message> list = new ArrayList<Message>();
            try {
                list = gson.fromJson(result, new TypeToken<ArrayList<Message>>(){}.getType());
            }catch (Exception e){
                e.printStackTrace();
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

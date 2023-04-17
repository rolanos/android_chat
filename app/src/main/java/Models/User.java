package Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public void print(){
        System.out.println(this);
    };
    public static ArrayList<User> getUserList(){
        Gson gson = new Gson();
        try{
            String url ="http://10.0.2.2:8080/get-users";
            ServletTask task = new ServletTask();
            String result = task.execute(url).get();
            return gson.fromJson(result, new TypeToken<ArrayList<User>>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

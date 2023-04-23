package Models;

import java.util.Date;

public class Message {
    private String userName;
    private int userId;
    private int chatId;
    private String text;
    private Date dispatch;
    public Message(String userName, int userId, int chatId, String text, Date dispatch){
        this.userName = userName;
        this.chatId = chatId;
        this.text = text;
        this.userId = userId;
        this.dispatch = dispatch;
    }

    public String getUserName(){
        return this.userName;
    }
    public int getUserID(){return this.userId;};
    public int getChatID(){return this.chatId;};
    public String getText(){
        return this.text;
    }
}

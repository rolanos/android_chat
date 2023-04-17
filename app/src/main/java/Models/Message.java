package Models;

public class Message {
    private String userName;
    private int userId;
    private int chatId;
    private String text;
    public Message(String userName, int userId, int chatId, String text){
        this.userName = userName;
        this.chatId = chatId;
        this.text = text;
        this.userId = userId;
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

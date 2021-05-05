package com.playdate.app.model;

public class Chat {
    String message;
    String image;
    int chat_type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getChat_type() {
        return chat_type;
    }

    public void setChat_type(int chat_type) {
        this.chat_type = chat_type;
    }

    public Chat(String message, String image, int chat_type) {
        this.message = message;
        this.image = image;
        this.chat_type = chat_type;

    }
}

package com.abc.myfirstchatapp.entity;

public class Message {

    public String text;
    public String senderUid;
    public String receiverUid;

    public Message() {
    }

    public Message(String text, String senderUid, String receiverUid) {
        this.text = text;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
    }
}

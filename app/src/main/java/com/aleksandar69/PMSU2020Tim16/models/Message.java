package com.aleksandar69.PMSU2020Tim16.models;

import androidx.annotation.NonNull;

public class Message {

    private int _id;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    // private Date dateTime;
    private String subject;
    private String content;

/*
    public static final Message[] messages =
            {
                    new Message("tester", "tester", "tester", "tester", "tester", "teter"),
                    new Message("tester2", "tester2", "tester2", "tester2", "tester2", "teter2")};
*/

    public Message(){}

    public Message(String from, String to, String cc, String bcc, String subject, String content) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        //     this.dateTime = dateTime;
        this.subject = subject;
        this.content = content;

    }

    @NonNull
    @Override
    public String toString() {
        return "\nTO: " + to + "\nFROM: " + from + "\nCC: " +  cc + "\nBCC: " + bcc + "\nSUBJECT: " + subject + "\nCONTENT: " + content;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

/*    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }*/

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

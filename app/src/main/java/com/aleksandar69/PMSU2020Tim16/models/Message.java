package com.aleksandar69.PMSU2020Tim16.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private int _id;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String dateTime;
    private String subject;
    private String content;
    private int logged_user_id;
    private List<Attachment> attachments;
/*    private int attachmentId;*/

    public Message() {
       // Calendar calendar = Calendar.getInstance();
        /*Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        String dateToString = format.format(curDate);
        format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        dateToString = format.format(curDate);
        dateTime = dateToString;*/
        attachments = new ArrayList<>();
    }



    public Message(String from, String to, String cc, String bcc, String subject, String content) {

/*
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        String dateToString = format.format(curDate);
        format = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        dateToString = format.format(curDate);
        dateTime = dateToString;
*/
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.content = content;
        attachments = new ArrayList<>();

    }

    @NonNull
    @Override
    public String toString() {
        return "\nTO: " + to + "\nFROM: " + from + "\nCC: " + cc + "\nBCC: " + bcc + "\nSUBJECT: " + subject + "\nCONTENT: " + content + "\n Date: " + dateTime;
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

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
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

/*    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }*/

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubject() {
        return subject;
    }

    public int getLogged_user_id() {
        return logged_user_id;
    }

    public void setLogged_user_id(int logged_user_id) {
        this.logged_user_id = logged_user_id;
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

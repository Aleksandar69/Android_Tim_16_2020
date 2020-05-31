package com.aleksandar69.PMSU2020Tim16.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class Folder {

    private int id;
    private String name;
    //private List<String> messages;

    public Folder(int id, String name) {
        this.id = id;
        this.name = name;

    }


    public Folder() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //public List<String> getMessages() {return messages;}

    //public void setMessages(List<String> messages) {this.messages = messages;}

}

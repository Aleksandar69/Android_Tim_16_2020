package com.aleksandar69.PMSU2020Tim16.models;

public class Account {

    private int _id;
    private String smtpAddress;
    private String port;
    private String username;
    private String password;
    private String displayName;
    private String eMail;

    public Account() {

    }

    public Account(String smtpAddress, String port, String username, String password, String displayName, String eMail){
        this.eMail = eMail;
        this.smtpAddress = smtpAddress;
        this.port = port;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}

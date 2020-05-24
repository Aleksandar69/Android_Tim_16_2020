package com.aleksandar69.PMSU2020Tim16.javamail;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class AuthenticateMail extends AsyncTask<Void, Void, Void> {

    String imapHOst;
    String eMail;
    String password;
    Store store;

    public AuthenticateMail(String imapHost, String email, String password) {
        this.imapHOst = imapHost;
        this.eMail = email;
        this.password = password;

    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            props.setProperty("mail.imap.port", "993");
            props.setProperty("mail.imap.host", imapHOst);
            props.setProperty("mail.imap.starttls.enable", "true");
            props.setProperty("mail.imap.ssl.enable", "true");

            Session emailSession = Session.getDefaultInstance(props);
            store = null;
                store = emailSession.getStore("imaps");


            store.connect(imapHOst, eMail, password);

            Boolean isconn = store.isConnected();


        } catch (MessagingException e) {
        e.printStackTrace();
    }
        return null;
    }
}

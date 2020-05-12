package com.aleksandar69.PMSU2020Tim16.javamail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aleksandar69.PMSU2020Tim16.activities.TestImageLoadActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;


public class SendMultipartEmail extends AsyncTask<Void, Void, Void> {

    //public static String myeMail = "mindsnackstore@gmail.com";
    public static String myeMail = "clockworkaleks@gmail.com";
    public static String myUsername = "MindSnack";
    public static String myPassword = "TooStronk69!";
  //  public static String recipient;
    public static String subject;
    public static String content;
    public static String fileName;
    Context mContext;
    private String myToList;
    private String myCCList;
    private String myBCCList;
    private static final String MAIL_SERVER = "smtp";
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;

    public SendMultipartEmail(Context context, String subject, String content, String fileName, String myCC, String myBCC, String myTo) {
        this.subject = subject;
        this.content = content;
        this.fileName = fileName;
        mContext = context;
        this.myCCList = myCC;
        this.myBCCList = myBCC;
        this.myToList = myTo;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session sess = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myeMail, myPassword);
            }
        });

        Message message = new MimeMessage(sess);

        List<String> ccListunConv = Arrays.asList(myCCList.split(",[ ]*"));
        String[] ccList = new String[ccListunConv.size()];
        ccList = ccListunConv.toArray(ccList);

        List<String> bccListunConv = Arrays.asList(myBCCList.split(",[ ]*"));
        String[] bccList = new String[bccListunConv.size()];
        bccList = bccListunConv.toArray(bccList);

        List<String> toListunConv = Arrays.asList(myToList.split(",[ ]*"));
        String[] toList = new String[toListunConv.size()];
        toList = toListunConv.toArray(toList);


        try {



            if(!(toListunConv.contains(""))){
                InternetAddress[] toAdress = new InternetAddress[toList.length];

                for(int i = 0; i < ccList.length; i++){
                    toAdress[i] = new InternetAddress(toList[i]);
                }
                for(int i = 0; i < toAdress.length; i++){
                    message.addRecipient(Message.RecipientType.TO, toAdress[i]);
                }

            }


            if(!(ccListunConv.contains(""))) {
                InternetAddress[] cCAddress = new InternetAddress[ccList.length];

                for (int i = 0; i < ccList.length; i++) {
                    cCAddress[i] = new InternetAddress(ccList[i]);
                }

                for (int i = 0; i < cCAddress.length; i++) {
                    message.addRecipient(Message.RecipientType.CC, cCAddress[i]);
                }
            }
            if(!(bccListunConv.contains(""))) {
                InternetAddress[] bCCAddress = new InternetAddress[bccList.length];

                for (int i = 0; i < bccList.length; i++) {
                    bCCAddress[i] = new InternetAddress(bccList[i]);
                }

                for (int i = 0; i < bCCAddress.length; i++) {
                    message.addRecipient(Message.RecipientType.BCC, bCCAddress[i]);
                }

            }

            message.setFrom(new InternetAddress(myeMail));
        //    message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();

            Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(messageBodyPart);
            messageBodyPart.setText(content);

            myCCList = "";
            myBCCList = "";

            //attachment

            //String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/5113lJZJQuL.JPG";

            //String fileName = "/storage/emulated/0/1091035_366679420102464_628854323_o.jpg";

            File file = new File(fileName);

            saveFile(encodeFileToBase64Binary(fileName), "/tempBase64.txt");

            String encoded = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tempBase64.txt";

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            messageBodyPart2.attachFile(encoded);


            //messageBodyPart = new MimeBodyPart();


      /*      String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/5113lJZJQuL.JPG";
            FileDataSource source = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Image");
            multiPart.addBodyPart(messageBodyPart);*/
/*
            String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/5113lJZJQuL.JPG";
            String base64attachment = encodeFileToBase64Binary(fileName);
            messageBodyPart.setHeader("Content-Transfer-Encoding", "base64");
            messageBodyPart.setFileName(fileName);
            messageBodyPart.setText(base64attachment);
            messageBodyPart.setDisposition(Part.ATTACHMENT);
            multiPart.addBodyPart(messageBodyPart);*/

            messageBodyPart2.setDisposition(Part.ATTACHMENT);
            messageBodyPart2.setFileName(file.getName());
            multiPart.addBodyPart(messageBodyPart2);

            message.setContent(multiPart);
            message.saveChanges();


           // Transport.send(message);

            Transport transport = sess.getTransport(MAIL_SERVER);
            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, myeMail, myPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();


        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveFile(String base64file, String fileName) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Build.VERSION.SDK_INT > 23) {
                if (checkPermission()) {
                    File storage = Environment.getExternalStorageDirectory();
                    File dir = new File(storage.getAbsolutePath());
                    dir.mkdir();
                    File file = new File(dir, fileName);
                    FileOutputStream os = null;
                    try {
                        os = new FileOutputStream(file);
                        os.write(base64file.getBytes());
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    private String encodeFileToBase64Binary(String fileName)
            throws IOException {

        File file = new File(fileName);
        byte[] bytes = loadFile(file);
        byte[] encoded = (Base64.encodeBase64(bytes));
        String encodedString = new String(encoded);

        return encodedString;
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // Toast.makeText(Context, "File too large",Toast.LENGTH_LONG);
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }


}
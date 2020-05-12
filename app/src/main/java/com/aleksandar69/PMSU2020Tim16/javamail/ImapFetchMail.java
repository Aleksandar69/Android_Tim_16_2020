package com.aleksandar69.PMSU2020Tim16.javamail;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Attachment;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class ImapFetchMail extends AsyncTask<Void, Void, Void> {


    //String user = "mindsnackstore@gmail.com";
    String user = "clockworkaleks@gmail.com";
    String password = "TooStronk69!";
    String storeType = "imaps";
    Context mContext;

    public ImapFetchMail(Context context){
        this.mContext = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {
  /*      if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();*/

        try {
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");

            Session emailSession = Session.getDefaultInstance(props);
            Store store = emailSession.getStore(storeType);

            store.connect("imap.googlemail.com", user, password);

            Folder inboxFolder = store.getFolder("INBOX");
            UIDFolder uf = (UIDFolder) inboxFolder;
            inboxFolder.open(Folder.READ_WRITE);

            Message[] messages = inboxFolder.getMessages();
            MessagesDBHandler dbHandler = new MessagesDBHandler(mContext);

            List<com.aleksandar69.PMSU2020Tim16.models.Message> messagesPulled = dbHandler.queryAllMessages();
            com.aleksandar69.PMSU2020Tim16.models.Message[] listMessages = new com.aleksandar69.PMSU2020Tim16.models.Message[messagesPulled.size()];
            listMessages = messagesPulled.toArray(listMessages);

            ArrayList<Integer> listInt = new ArrayList<Integer>();

            for(int j = 0; j < listMessages.length; j++){
                com.aleksandar69.PMSU2020Tim16.models.Message messagefromDB = listMessages[j];
                int idMess = messagefromDB.get_id();
                listInt.add(idMess);
            }

            for (int i = 0; i < messages.length; i++) {
                Message messageInb = messages[i];
                int messageid = (int) uf.getUID(messageInb);

                if(!(listInt.contains(messageid))) {

                    com.aleksandar69.PMSU2020Tim16.models.Message message = new com.aleksandar69.PMSU2020Tim16.models.Message();

                    message.set_id(messageid);

                    String contentType = messageInb.getContentType();
                    String content = getTextFromMessage(messageInb);

                    message.setSubject(messageInb.getSubject());
                    message.setFrom(messageInb.getFrom()[0].toString());

                    if (android.os.Debug.isDebuggerConnected())
                        android.os.Debug.waitForDebugger();

                    InternetAddress[] to = (InternetAddress[]) messageInb.getRecipients(Message.RecipientType.TO);
                    InternetAddress[] cc = (InternetAddress[]) messageInb.getRecipients(Message.RecipientType.CC);
                    InternetAddress[] bcc = (InternetAddress[]) messageInb.getRecipients(Message.RecipientType.BCC);


                    message.setContent(content);
                    if(to != null){
                        StringBuffer tobuffer = new StringBuffer();

                        for (i = 0; i < to.length; i++) {
                            tobuffer.append(to[i].getAddress() + ";");
                            message.setTo(tobuffer.toString());
                        }
                    }
                    if(cc != null) {
                        StringBuffer ccbuffer = new StringBuffer();
                        for (i = 0; i < cc.length; i++) {
                            ccbuffer.append(cc[i].getAddress() + ";");
                            message.setCc(ccbuffer.toString());
                        }
                    }
                    if(bcc != null) {
                        StringBuffer bccbuffer = new StringBuffer();

                        for (i = 0; i < bcc.length; i++) {
                            bccbuffer.append(bcc[i].getAddress() + ";");
                            message.setBcc(bccbuffer.toString());
                        }
                    }

                    String date = toUTC(messageInb.getSentDate());
                    Date dateConverted = fromUTC(date);

                    message.setDateTime(dateConverted.toString());


                    Log.d("DATE TO UTC: ", date);

                    if (contentType.contains("multipart")) {
                        // content may contain attachments
                        Multipart multiPart = (Multipart) messageInb.getContent();
                        int numberOfParts = multiPart.getCount();
                        for (int partCount = 0; partCount < numberOfParts; partCount++) {
                            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                // this part is attachment
                                Attachment attachment = new Attachment(part.getContent().toString(), part.getFileName());
                                //String fileContent = part.getContent().toString();
                                //part.saveFile(saveDirectory + File.separator + fileName);
                                dbHandler.addAttachment(attachment);
                                message.setAttachment(attachment);
                                Attachment attachmentCopy = dbHandler.queryAttachbyName(part.getFileName());
                                message.setAttachmentId(attachmentCopy.get_id());

                            }
                        }
                    } else {
                        message.setSubject(messageInb.getSubject());
                        message.setFrom(messageInb.getFrom()[0].toString());
                        message.setContent(messageInb.getContent().toString());
                    }
                    dbHandler.addMessage(message);
                }
            }
            inboxFolder.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Date fromUTC(String dateStr) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);

        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String toUTC(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }



    private String getTextFromMessage(Message message) throws IOException, MessagingException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws IOException, MessagingException {

        int count = mimeMultipart.getCount();
        if (count == 0)
            throw new MessagingException("Multipart with no body parts not supported.");
        boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
        if (multipartAlt)
            // alternatives appear in an order of increasing
            // faithfulness to the original content. Customize as req'd.
            return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
        String result = "";
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            result += (getTextFromBodyPart(bodyPart) + "\n");
        }
        return result;
    }


    private String getTextFromBodyPart(
            BodyPart bodyPart) throws IOException, MessagingException {

        String result = "";
        if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
            result = "Contains attachment ";
        }
        else if (bodyPart.isMimeType("text/plain")) {
            result = (String) bodyPart.getContent();
        } else if (bodyPart.isMimeType("text/html")) {
            String html = (String) bodyPart.getContent();
            result = org.jsoup.Jsoup.parse(html).text();
        } else if (bodyPart.getContent() instanceof MimeMultipart) {
            result = getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
        }
        return result;
    }


    public List<InputStream> getAttachments(Message message) throws Exception {
        Object content = message.getContent();
        if (content instanceof String)
            return null;

        if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            List<InputStream> result = new ArrayList<InputStream>();

            for (int i = 0; i < multipart.getCount(); i++) {
                result.addAll(getAttachments(multipart.getBodyPart(i)));
            }
            return result;

        }
        return null;
    }

    private List<InputStream> getAttachments(BodyPart part) throws Exception {
        List<InputStream> result = new ArrayList<InputStream>();
        Object content = part.getContent();
        if (content instanceof InputStream || content instanceof String) {
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()) /*|| StringUtils.isNotBlank(part.getFileName())*/) {
                result.add(part.getInputStream());
                return result;
            } else {
                return new ArrayList<InputStream>();
            }
        }

        if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                result.addAll(getAttachments(bodyPart));
            }
        }
        return result;
    }


}
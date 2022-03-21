import java.util.*;
import java.text.*;

/* $Id: Message.java,v 1.5 1999/07/22 12:10:57 kangasha Exp $ */

/**
 * Mail message.
 *
 * @author Jussi Kangasharju
 */
public class Message {
    /* The headers and the body of the message. */
    public String Headers;
    public String Body;

    /* Sender and recipient. With these, we don't need to extract them
       from the headers. */
    private String From;
    private String To;

    /* To make it look nicer */
    private static final String CRLF = "\r\n";
    private static final String NL = "\n";

    /* Create the message object by inserting the required headers from
       RFC 822 (From, To, Date). */
    public Message(String from, String to, String subject, String picture, String text) {
        /* Remove whitespace */
        String base64 = Converttobase64.Convert(picture);
        From = from.trim();
        To = to.trim();
        Headers = "From: " + From + CRLF;
        Headers += "To: " + To + CRLF;
        Headers += "Subject: " + subject.trim() + CRLF;

	/* A close approximation of the required format. Unfortunately
	   only GMT. */
        SimpleDateFormat format =
                new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
        String dateString = format.format(new Date());
        Headers += "Date: " + dateString + CRLF;
        Headers += "MIME-Version: 1.0 \n" +
                "Content-Type:multipart/alternative;boundary=\"KkK170891tpbkKk__FV_KKKkkkjjwq\"\n";
        Body= "--KkK170891tpbkKk__FV_KKKkkkjjwq\n"
                +"Content-Type: multipart/related; boundary=\"gc0p4Jq0M2Yt08jU534c0p\"\n"
                + "--gc0p4Jq0M2Yt08jU534c0p\n"
                + "Content-Type: text/html; charset=\"utf-8\"\n"
                + "Content-Transfer-Encoding: 8bit\n"
                + "<html>\n"
                + "<body>\n"
                + "<p>"+text+"</p>\n"
                + "<p class=\"image\"> <br><img src=\"cid:picture1\"></p>\n"
                + "</body>\n"
                + "</html>\n"
                + "--gc0p4Jq0M2Yt08jU534c0p\n"
                + "Content-Type:image/jpg;name=\""+Converttobase64.getName(picture)+"\"\n"
                + "Content-Transfer-Encoding:base64\n"
                + "Content-Disposition: inline; filename=\""+Converttobase64.getName(picture)+"\"\n"
                + "Content-Location: picture.jpg: "+Converttobase64.getName(picture)+"\n"
                + "Content-ID: <picture1>\n";



        if (!picture.equals("")){

            Body += "\n" + base64;
            Body += "\n" + "--gc0p4Jq0M2Yt08jU534c0p--";
            Body += "\n" + "--KkK170891tpbkKk__FV_KKKkkkjjwq--";

        }

    }

    /* Two functions to access the sender and recipient. */
    public String getFrom() {
        return From;
    }

    public String getTo() {
        return To;
    }

    /* Check whether the message is valid. In other words, check that
       both sender and recipient contain only one @-sign. */
    public boolean isValid() {
        int fromat = From.indexOf('@');
        int toat = To.indexOf('@');

        if(fromat < 1 || (From.length() - fromat) <= 1) {
            System.out.println("Sender address is invalid");
            return false;
        }
        if(toat < 1 || (To.length() - toat) <= 1) {
            System.out.println("Recipient address is invalid");
            return false;
        }
        if(fromat != From.lastIndexOf('@')) {
            System.out.println("Sender address is invalid");
            return false;
        }
        if(toat != To.lastIndexOf('@')) {
            System.out.println("Recipient address is invalid");
            return false;
        }
        return true;
    }

    /* For printing the message. */
    public String toString() {
        String res;

        res = Headers + CRLF;
        res += Body;
        return res;
    }
}

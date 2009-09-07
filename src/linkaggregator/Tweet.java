/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package linkaggregator;

import dygest.commons.db.simple.IStorable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author anand
 */
public class Tweet implements IStorable {

    private String uri;
    private String user;
    private String text;
    private String day;
    private String time;
    // links separated by ^A
    private String links;
    private Date date;

    private boolean hasLink = false;

    public Tweet(String uri, String user, String text, Date time, boolean resolve) {
        this.uri = uri;
        this.user = user;
        this.text = text;
        this.date = time;
        formatTime(time);

        if(text != null) {
            hasLink = text.matches(".*http://[a-zA-Z0-9]+.*");
            if(hasLink) {
                extractUrls(text, resolve);
            }
        }
    }

    private void extractUrls(String text, boolean resolve) {
        StringBuffer links = new StringBuffer();
        
        Pattern pattern = Pattern.compile("http://[a-zA-Z0-9\\/&?=#+-_%~]+");
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            if(resolve) {
                // @TODO resolve addresses
            }
            links.append(matcher.group());
            links.append('\001');
        }

        System.out.println(links.toString());
        this.links = links.toString();
    }

    private void formatTime(Date t) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hhmm");

        day = dayFormat.format(t);
        time = timeFormat.format(t);
    }

    /**
     * @return the uri
     */
    public String getURI() {
        return uri;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @return the day
     */
    public String getDay() {
        return day;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the hasLink
     */
    public boolean hasLink() {
        return hasLink;
    }

    public String getID() {
        return uri;
    }

    public HashMap<String, String> toMap() {
        HashMap<String, String> objAsMap = new HashMap<String, String>();

        objAsMap.put("id", uri);
        objAsMap.put("uri", uri);
        objAsMap.put("user", user);
        objAsMap.put("text", text);
        objAsMap.put("time", time);
        objAsMap.put("day", day);
        objAsMap.put("hasLink", String.valueOf(hasLink));

        return objAsMap;
    }

    public String toJSON() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

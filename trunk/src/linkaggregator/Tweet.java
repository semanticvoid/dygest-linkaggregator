/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package linkaggregator;

import java.util.Date;

/**
 *
 * @author anand
 */
public class Tweet {

    private String uri;
    private String user;
    private String text;
    private Date time;

    private boolean hasLink = false;

    public Tweet(String uri, String user, String text, Date time) {
        this.uri = uri;
        this.user = user;
        this.text = text;
        this.time = time;

        if(text != null) {
            hasLink = text.matches(".*http://[a-zA-Z0-9]+.*");
        }
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
    public Date getTime() {
        return time;
    }

    /**
     * @return the hasLink
     */
    public boolean hasLink() {
        return hasLink;
    }

}

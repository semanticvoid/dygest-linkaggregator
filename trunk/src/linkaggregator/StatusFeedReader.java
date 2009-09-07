/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package linkaggregator;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anand
 */
public class StatusFeedReader {

    private SyndFeed getFeed(String url) throws Exception {
        URL feedUrl = new URL(url);
        SyndFeedInput input = new SyndFeedInput(false);
        SyndFeed feed = input.build(new InputStreamReader(feedUrl.openStream()));

        return feed;
    }

    private String extractUser(String uri) {
        String user = null;
        String[] tokens = uri.split("[/]+");

        if(tokens.length > 2) {
            user = tokens[2];
        }

        return user;
    }

    public ArrayList<Tweet> read(String feedURL) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        try {
            SyndFeed feed = getFeed(feedURL);
            List<SyndEntryImpl> entries = feed.getEntries();
            
            for(SyndEntryImpl entry : entries) {
                String user = extractUser(entry.getUri());
                Tweet t = new Tweet(entry.getUri(), user, entry.getDescription().getValue().replace(user + ": ", ""), entry.getPublishedDate(), false);
                tweets.add(t);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return tweets;
    }
}

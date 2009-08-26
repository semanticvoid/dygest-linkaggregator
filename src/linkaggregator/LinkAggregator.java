/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package linkaggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author anand
 */
public class LinkAggregator {
 
    private List<String> extractUrls(String text, boolean resolve) {
        ArrayList<String> urls = new ArrayList<String>();

        Pattern pattern = Pattern.compile("http://[a-zA-Z\\/&?=#+-_%~]+");
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            if(resolve) {
                // @TODO resolve addresses
            }
            urls.add(matcher.group());
        }

        return urls;
    }

    public ArrayList<String> aggregate(List<Tweet> tweets) {
        ArrayList<String> urls = new ArrayList<String>();

        for(Tweet t : tweets) {
            if(t.hasLink()) {
                extractUrls(t.getText(), true);
            }
        }

        return urls;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package linkaggregator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 *
 * @author anand
 */
public class Main {

    static StatusFeedReader rdr = new StatusFeedReader();
    static LinkAggregator agg = new LinkAggregator();

    public static void collectLinksFromFeed(String feedURL) {
        List<Tweet> tweets = rdr.read(feedURL);
        List<String> links = agg.aggregate(tweets);

        for(String link : links) {
            System.out.println(link);
        }
    }

    public static void main(String[] args) {
        if(args.length <= 0) {
            System.out.println("insufficient args");
            System.exit(1);
        }

        try {
            String filePath = args[0];
            FileReader fRdr = new FileReader(filePath);
            BufferedReader in = new BufferedReader(fRdr);

            String line = "";
            while((line = in.readLine()) != null) {
                String[] tokens = line.split("[ \t]+");
                if(tokens.length == 2) {
                    collectLinksFromFeed(tokens[1]);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}

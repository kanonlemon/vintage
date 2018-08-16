package com.kanon.vintage.subscription.adapter;

import com.kanon.vintage.subscription.model.Subscription;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RSSAdapter {

    private String rssLink;

    private String rssName;

    public RSSAdapter(String rssLink, String rssName) {
        this.rssLink = rssLink;
        this.rssName = rssName;
    }

    public List<Subscription> parseRss2Subscription() throws IOException, FeedException {
        URL url = new URL(this.rssLink);
        XmlReader xmlReader = new XmlReader(url);
        SyndFeedInput input = new SyndFeedInput();

        List<Subscription> results = new ArrayList<Subscription>();

        SyndFeed feed = input.build(xmlReader);
        for(SyndEntry se: feed.getEntries()){
            results.add(new Subscription(se.getAuthor(), se.getTitle(), se.getDescription().getValue(),se.getLink() , se.getPublishedDate() ));
        }

        return results;
    }
}

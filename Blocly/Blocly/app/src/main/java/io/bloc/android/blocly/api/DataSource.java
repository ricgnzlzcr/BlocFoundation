package io.bloc.android.blocly.api;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.bloc.android.blocly.BloclyApplication;
import io.bloc.android.blocly.R;
import io.bloc.android.blocly.api.model.RssFeed;
import io.bloc.android.blocly.api.model.RssItem;
import io.bloc.android.blocly.api.network.GetFeedsNetworkRequest;

/**
 * Created by ricardo on 5/26/15.
 */
public class DataSource {

    public static final String ACTION_DOWNLOAD_COMPLETED = DataSource.class.getCanonicalName().concat(".ACTION_DOWNLOAD_COMPLETED");

    private List<RssFeed> feeds;
    private List<RssItem> items;
    private List<GetFeedsNetworkRequest.FeedResponse> dataList;

    public DataSource() {
        feeds = new ArrayList<RssFeed>();
        items = new ArrayList<RssItem>();
        //createFakeData();
        //createData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                dataList = new GetFeedsNetworkRequest("http://feeds.feedburner.com/androidcentral?format=xml").performRequest();
                Log.d("DATASOURCE", "Datalist created!");
                createData();
                BloclyApplication.getSharedInstance().sendBroadcast(new Intent(ACTION_DOWNLOAD_COMPLETED));
            }
        }).start();


    }

    public List<RssFeed> getFeeds() {
        return feeds;
    }

    public List<RssItem> getItems() {
        return items;
    }

    void createFakeData() {
        feeds.add(new RssFeed("My Favorite Feed",
                "This feed is just incredible, I can't even begin to tell you…",
                "http://favoritefeed.net", "http://feeds.feedburner.com/favorite_feed?format=xml"));
        for (int i = 0; i < 10; i++) {
            items.add(new RssItem(String.valueOf(i),
                    BloclyApplication.getSharedInstance().getString(R.string.placeholder_headline) + " " + i,
                    BloclyApplication.getSharedInstance().getString(R.string.placeholder_content),
                    "http://favoritefeed.net?story_id=an-incredible-news-story",
                    "http://rs1img.memecdn.com/silly-dog_o_511213.jpg",
                    0, System.currentTimeMillis(), false, false, false));
        }
    }

    private void createData() {
        if (dataList != null) {
            for (GetFeedsNetworkRequest.FeedResponse feed:dataList) {
                feeds.add(new RssFeed(feed.channelTitle, feed.channelDescription, feed.channelURL, feed.channelFeedURL));
                for (int i = 0; i < feed.channelItems.size(); i++) {
                    items.add(new RssItem((String.valueOf(i)),
                            feed.channelItems.get(i).itemTitle,
                            feed.channelItems.get(i).itemDescription,
                            "http://favoritefeed.net?story_id=an-incredible-news-story",
                            "http://rs1img.memecdn.com/silly-dog_o_511213.jpg",
                            0, System.currentTimeMillis(), false, false, false
                            ));
                    Log.d("ITEM TITLE", feed.channelItems.get(i).itemTitle);
                }
                Log.d("FEED NAME", feed.channelTitle);
            }
        }
    }

}

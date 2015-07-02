package io.bloc.android.blocly.api.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ricardo on 7/2/15.
 */
public class GetFeedsNetworkRequest extends NetworkRequest {

    // #6
    String[] feedUrls;

    public GetFeedsNetworkRequest(String... feedUrls) {
        this.feedUrls = feedUrls;
    }

    // #7
    @Override
    public Object performRequest() {
        for (String feedUrlString : feedUrls) {
            InputStream inputStream = openStream(feedUrlString);
            if (inputStream == null) {
                return null;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                // #8
                String line = bufferedReader.readLine();
                while (line != null) {
                    Log.v(getClass().getSimpleName(), "Line: " + line);
                    line = bufferedReader.readLine();

                    //Ric's code
                    int count = getRSSFeedCount(line);
                    Log.d("Feed Count", "" + count);

                }
                // #9
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                setErrorCode(ERROR_IO);
                return null;
            }
        }
        return null;
    }

    //Ric's methods
    private int getRSSFeedCount(String line) {
        String title = "<title>";
        int rssFeedCount = 0;
        rssFeedCount = instancesOfStringInString(title, line, rssFeedCount);
        return rssFeedCount;
    }

    private int instancesOfStringInString(String wordToFind, String stringToSearch, int count) {
        int wordToFindLength = wordToFind.length();
        if (stringToSearch != null) {
            if (stringToSearch.indexOf(wordToFind) == -1) {
                return count;
            } else {
                int posOfTitle = stringToSearch.indexOf(wordToFind);
                return instancesOfStringInString(wordToFind, stringToSearch.substring(posOfTitle + wordToFindLength), count + 1);
            }
        }
        return count;
    }


}

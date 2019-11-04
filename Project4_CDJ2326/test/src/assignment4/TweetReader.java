/* EE422C Assignment #4 submission by
 * Colby Janecka
 * CDJ2326
 */

package assignment4;


import java.net.URL;

import java.util.ArrayList;

import java.util.List;



/**
 * TweetReader contains method used to return tweets from method
 * Do not change the method header
 */
public class TweetReader {

    static int totalTweetCount;                                                     // TO BE REMOVED
    static int rejectedTweetCount;                                                 // TO BE REMOVED
    static int filteredTweetCount;                                                 // TO BE REMOVED
    static List<Tweets> rawData;

    /**
     * Find tweets written by a particular user.
     *
     * @param url
     *            url used to query a GET Request from the server
     * @return return list of tweets from the server
     *
     */
    public static List<Tweets> readTweetsFromWeb(String url) throws Exception
    {

        // Create URL object for GET requests:
        URL getURL = new URL("http://kevinstwitterclient2.azurewebsites.net/api/products");

        // First, get an array of tweets from the JSON.
        List<Tweets> unfilteredTweets = OkHttp.main(getURL);

        // PUTTING DATA IN INSTANCE VARIABLE FOR TESTING PURPOSES               // TO BE REMOVED
        rawData = unfilteredTweets;

        // Filter the tweets to meet the given criteria
        List<Tweets> filteredTweets = filter(unfilteredTweets);

        return filteredTweets;
    }


    /**
     * filters tweets
     *
     * @param unfilteredTweets is the raw tweets from site.
     *
     * @return list of tweet objects.
     */
    public static List<Tweets> filter(List<Tweets> unfilteredTweets){

        // create new list filteredTweets to store the valid content
        List<Tweets> filteredTweets = new ArrayList<>();

        // for each tweet in unfilteredTweets, check for validity and add to filteredTweets if needed
        for(Tweets tweet : unfilteredTweets){
            totalTweetCount++;
            if(isValidTweet(tweet)){
                filteredTweets.add(tweet);
                filteredTweetCount++;
            }
            else {
                rejectedTweetCount++;                                               // TO BE REMOVED
            }
        }
        // return list of filtered tweets
        return filteredTweets;
    }

    /**
     * checks to see if tweet is valid
     *
     * @param tweet is the tweet to check the validity of
     *
     * @return boolean value of the result
     */
    public static boolean isValidTweet(Tweets tweet){

        try{

            // verify ID, and return false if it is invalid.
            double id = tweet.getId();
            if(0 >= id || id > Math.pow(2,32)){
                return false;
            }

            // verify Name
            if(!tweet.getName().matches("^[a-zA-Z0-9_]+$")){
                return false;
            }

            // verify Date
            if (Filter.parseToInstant(tweet.getDate()).equals(null)) {
                return false;
            }

            // Verify text
            if(tweet.getText().length() > 140){
                return false;
            }

        } catch(NullPointerException n){
            return false;
        }

        return true;
    }


}

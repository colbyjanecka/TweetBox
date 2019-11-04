/* EE422C Assignment #4 submission by
 * Colby Janecka
 * CDJ2326
 */

package assignment4;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;


/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweets> writtenBy(List<Tweets> tweets, String username) {

        List<Tweets> filteredList = new ArrayList<Tweets>();
        for(Tweets tweet : tweets){
            if(tweet.getName().equals(username)){
                filteredList.add(tweet);
            }
        }

        return filteredList;

    }

    /**
     * Find tweets that were sent during a particular timespan.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweets> inTimespan(List<Tweets> tweets, Timespan timespan) {
        List<Tweets> filteredList = new ArrayList<Tweets>();

        if(timespan.getStart().isAfter(timespan.getEnd())){
            System.out.println("End Date before Start Date");
            return filteredList;
        }

        Instant start = timespan.getStart();
        Instant end = timespan.getEnd();

        for(Tweets tweet: tweets){
            //create instant for each tweet
            Instant instant = parseToInstant(tweet.getDate());

            if(instant.isAfter(start) && instant.isBefore(end)){
                filteredList.add(tweet);
            }
            else if(instant.equals(start) || instant.equals(end)){
                filteredList.add(tweet);
            }

        }

        return filteredList;
    }


    /**
     * creates an Instance object from time
     *
     * @param dateString is the date to create instance from
     *
     * @return Instant object representing the same time of the given string
     */
    public static Instant parseToInstant(String dateString) {

        //String validDatePattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]['Z']";  // USE THIS IF MILLISECONDS ARE ALLOWED
        String validDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        //String validDatePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter format = DateTimeFormatter.ofPattern(validDatePattern);
        Instant tweetInstant;

        try{
            TemporalAccessor temporalAccessor = format.parseBest(dateString, LocalDateTime::from, LocalDate::from);
            if(temporalAccessor instanceof LocalDateTime){
                //tweetInstant = Instant.parse(dateString);
                tweetInstant = ((LocalDateTime) temporalAccessor).atZone(ZoneId.systemDefault()).toInstant();
            }
            else {
                //tweetInstant = ((LocalDate) temporalAccessor).atStartOfDay(ZoneId.systemDefault()).toInstant();
                tweetInstant = ((LocalDate) temporalAccessor).atStartOfDay().toInstant(ZoneOffset.UTC);
            }
        }
        catch (DateTimeParseException d) {
            // System.out.println("     Error Parsing Date as Instant: (" + dateString + ")  - " + d.getMessage());
            tweetInstant = null;
        }
        return(tweetInstant);
    }

    /**
     * Find tweets that contain certain words.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets.
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when
     *         represented as a sequence of nonempty words bounded by space characters
     *         and the ends of the string) includes *at least one* of the words
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweets> containing(List<Tweets> tweets, List<String> words) {
        List<Tweets> filteredList = new ArrayList<Tweets>();

        for(Tweets tweet : tweets){

            boolean containsWord = false;

            // for each word in words,
            for(String word : words){

                // if tweet contents contain word, add to filteredlist
                if(tweet.getText().toLowerCase().indexOf(word.toLowerCase()) != -1){
                    containsWord = true;
                }

            }

            if(containsWord) filteredList.add(tweet);


        }

        return filteredList;
    }
}
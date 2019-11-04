/* EE422C Assignment #4 submission by
 * Colby Janecka
 * CDJ2326
 */

package assignment4;

import sun.nio.ch.Net;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Social Network consists of methods that filter users matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    private static NetworkGraph graph = new NetworkGraph();

    /**
     * Get K most followed Users.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @param k
     *            integer of most popular followers to return
     * @return the set of usernames who are most mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getName()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like ethomaz@utexas.edu does NOT
     *         contain a mention of the username.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static List<String> findKMostFollower(List<Tweets> tweets, int k) {

        updateNetworkGraph(tweets);

        List<String> orderedByFollowers = graph.orderByMostFollowed();

        List<String> mostFollowers = new ArrayList<>();
        mostFollowers = orderedByFollowers.stream().limit(k).collect(Collectors.toList());

        return mostFollowers;
    }

    /**
     * Find all cliques in the social network.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     *
     * @return list of set of all cliques in the graph
     */
    List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> result = new ArrayList<Set<String>>();
        updateNetworkGraph(tweets);

        Iterator iterator = tweets.iterator();
        int i = 0;
        while(iterator.hasNext()) {
            Tweets current = (Tweets) iterator.next();
            User currentAuthor = graph.getUser(current.getName());
            String currentName = current.getName();
            // get current user (author of tweet)
            if (currentAuthor != null) {


                List<String> currentFriends = getFriends(currentAuthor);
                // get list of his friends

                // for each friend:
                for(String friendName : currentFriends){


                    Set<String> cliquesTemp = new HashSet<String>();
                    cliquesTemp.add(currentName);
                    cliquesTemp.add(friendName);

                    for(String otherFriend : currentFriends){
                        if((otherFriend != friendName) && (otherFriend != currentName)){
                            if((getFriends(graph.getUser(otherFriend)).contains(friendName))){
                                cliquesTemp.add(otherFriend);
                            }
                        }
                    }
                    if(!result.contains(cliquesTemp)) {
                        result.add(cliquesTemp);
                    }
                }
            }
        }

        return result;
    }

    /**
     * find friends of a given user
     *
     * @param user is who you want to find the friends of
     *
     * @return String List of friends
     */
    List<String> getFriends(User user){
        List<String> friendList = new ArrayList<String>();

        if(user != null) {
            List<String> userFollowers = user.getFollowersArray();

            for (String follower : userFollowers) {
                User followerUser = graph.getUser(follower);
                if (followerUser.followers.contains(user) && (follower != user.getUsername())) {
                    friendList.add(follower);
                }
            }
        }

        return friendList;
    }

    /**
     * adds all users and connections to graph
     *
     * @param tweets is list of tweets to get data from
     */
    static void updateNetworkGraph(List<Tweets> tweets){

        // make sure all valid users are added to the graph:
        //addValidUsers(tweets);

        // loop through each tweet once.
        for(Tweets tweet : tweets){

            String author = tweet.getName().toLowerCase();

            User authorUser = addUserForName(author);

            // scan for any mentions in the tweet:
            List<String> mentionedUsernames = getMentions(tweet);

            for(String mentioned : mentionedUsernames){

                mentioned.toLowerCase();
                User mentionedUser = addUserForName(mentioned);

                mentionedUser.addFollower(authorUser);


            }



        }

    }

    /**
     * adds user to graph
     *
     * @param username is who you want to add
     *
     * @return User object for user added
     */
    static User addUserForName(String username){
        User tempUser = new User(username.toLowerCase());
        if(!graph.containsUser(username.toLowerCase())){
            graph.addUser(username.toLowerCase(), tempUser);
        }
        else
        {
            tempUser = graph.getUser(username.toLowerCase());
        }
        return(tempUser);
    }


    /**
     * get mentions in a tweet
     *
     * @param temp is tweet object of tweet
     *
     * @return String list of mentions
     */
    static List<String> getMentions(Tweets temp){
        String t = temp.getText();
        List<String> mentions = new ArrayList<String>();

        String tweetWords[] = t.split(" ");
        List<String> tweet = Arrays.asList(tweetWords);


        for(String word : tweet) {

            char[] letters = word.trim().toCharArray();

            if (letters.length > 1) {
                if (letters[0] == '@') {


                    List<Character> mentionedUsername = new ArrayList<>();
                    int i = 1;


                    while(i < letters.length){
                        mentionedUsername.add(letters[i]);
                        i += 1;
                    }

                    if(!mentions.contains(mentionedUsername)){
                        String m = mentionedUsername.stream().map(String::valueOf).collect(Collectors.joining());
                        mentions.add(m);
                    }


                }
            }


        }
        return mentions;
    }
}



/* EE422C Assignment #4 submission by
 * Colby Janecka
 * CDJ2326
 */

package assignment4;

import java.util.*;

class NetworkGraph<T> {

    /** users is a Map object with key of generic type T, and value of type User */
    Map<T, User> users;

    /**
     * Default NetworkGraph constructor
     *  Creates new empty HashMap for users.
     */
    NetworkGraph(){

        users = new HashMap<T, User>();
    }

    /**
     * creates a new entry in the users Map object.
     * @param username is an object of generic type T and is the key for the new Map entry
     * @param user is a User object to be stored as the value for the new Map entry
     */
    void addUser(T username, User user){
        users.put(username, user);
    }


    /**
     * creates a new entry in the users Map object.
     * @param username is the key value for the object to find in users HashMap
     * @return the User in the HashMap for the given username.
     */
    User getUser(T username){

        User user = users.get(username);
        return user;
    }

    /**
     * Replaces the value of a graph entry with a supplied User object.
     * @param username is the key value for the object to replace in the graph.
     */
    void setUser(T username, User u){
        users.replace(username, u);
    }

    /**
     * Finds whether or not there is a User for
     * @param username is the key value for the object to find in users HashMap
     * @return the User in the HashMap for the given username.
     */
    boolean containsUser(T username){
        return users.containsKey(username);
    }

    List<String> orderByMostFollowed(){
        List<String> orderedByFollowersStr = new ArrayList<>();
        List<User> orderedByFollowers = new ArrayList<>();
        Set< Map.Entry< T, User> > st = users.entrySet();

        for (Map.Entry< T, User> user : st){
            orderedByFollowers.add(user.getValue());
        }

        for(int i = orderedByFollowers.size()-1; i >= 0; i--){

            int max = orderedByFollowers.get(i).followerCount;
            int maxID = i;

            for(int j = i-1; j >= 0; j--){
                if(orderedByFollowers.get(j).followerCount < max){
                    max = orderedByFollowers.get(j).followerCount;
                    maxID = j;
                }
            }
            User temp = orderedByFollowers.get(i);
            orderedByFollowers.set(i, orderedByFollowers.get(maxID));
            orderedByFollowers.set(maxID, temp);

        }
        for(User user : orderedByFollowers){
            orderedByFollowersStr.add(user.getUsername());
        }

        return orderedByFollowersStr;
    }

    /**
     * Used for testing purposes to output the objects contained in the NetworkGraph.
     */
    void printNetworkGraph(){
        Set< Map.Entry< T, User> > st = users.entrySet();

        for (Map.Entry< T, User> foo : st){
            System.out.print(foo.getKey() + " - ");
            System.out.println(foo.getValue());
        }
        System.out.println("");
    }

}
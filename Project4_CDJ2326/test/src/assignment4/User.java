/* EE422C Assignment #4 submission by
 * Colby Janecka
 * CDJ2326
 */

package assignment4;
import java.util.*;

class User<T> {

    /**
     * name is a private variable of generic type T which represents the key of the object in the graph.
     */
    private T name;
    int followerCount;

    /**
     * followers is a list of users who follow a user.
     */
    List<User> followers;

    /**
     * default constructor: sets word value, and creates HashMap to store edges
     */
    User(T username) {
        name = username;
        followers = new ArrayList<User>();
    }

    void addFollower(User user){
        if(!followers.contains(user) && !this.name.equals(user.getUsername())){
            followers.add(user);
            followerCount += 1;
        }
    }

    List<String> getFollowersArray(){
        List<String> followersArray = new ArrayList<>();
        for(User follower : followers){
            followersArray.add(follower.getUsername());
        }
        return followersArray;
    }

    /**
     * returns the name of this Vertex as a String object
     *
     * @return a String object containing the name, which is generic type T.
     */
    String getUsername() {
        return name.toString();
    }

}

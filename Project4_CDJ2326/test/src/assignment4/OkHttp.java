/* EE422C Assignment #4 submission by
 * Colby Janecka
 * CDJ2326
 */

package assignment4;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.util.JSONPObject;
import okhttp3.*;
import com.fasterxml.jackson.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * OKHttp is a class that is used for scubbing the website
 *
 */
public class OkHttp {

    private final OkHttpClient httpClient = new OkHttpClient();

    /**
     * main
     *
     * @param getURL is the URL object representing where to pull data from
     */
    public static List<Tweets> main(URL getURL) throws Exception {

        OkHttp obj = new OkHttp();

        String jsonInString = obj.sendGet(getURL);

        String jsonInStringAlt = "{\"Id\":\"102\",\"Name\":\"foo\",\"Date\":\"null\",\"Text\":\"hello world.\"}";
        String jsonInStringAlt2 = "{\"id\":\"102\",\"name\":\"foo\",\"text\":\"hello world.\"}";
        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);

        ObjectMapper objMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);

        List<Tweets> tweets = Arrays.asList(objMapper.readValue(jsonInString,Tweets[].class));

        return tweets;

    }

    /**
     * sends a GET request to the URL
     *
     * @param getURL is the url to send request to
     *
     * @return String containing the data.
     */
    private String sendGet(URL getURL) throws Exception {

        Request request = new Request.Builder()
                .url(getURL)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return(response.body().string());
        }

    }




}

package ee.tlu;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterTest {

	public static void main(String[] args) throws TwitterException {
		List<String> tweets = new ArrayList<>();
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("JeRjATwmUq80uikAvzbeIxWIU")
		  .setOAuthConsumerSecret("HwG0WuWRfiKJAu5puJ7haWHIodYmr2gtKtEetSz0aqQ1902itV")
		  .setOAuthAccessToken("379873937-TDrRuqJoNnJ8yF5DukZYAEk612QQSFxXCAH2j6mY")
		  .setOAuthAccessTokenSecret("NeOfV7CQDjC4s6an5F6QSULMryfmf3q6j2Tby9lUzYEyW");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
	    Query query = new Query("#checkers");
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	        tweets.add(status.getText());
	    }
	    
	    for(String tweet: tweets){
	    	System.out.println(tweet);
	    }
	}

}

package ee.tlu.classes;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/*
 * Klass, mis küsib Twitter'ist tweet'e ja muudab need mängu käikudeks.
 * Käike saadetakse kujul:
 * #hashtag 1 1 1 2 2
 * tiim reast tulbast ritta tulpa
 */

public class TwitterMoveParser {
	
	private String hashtag;
	private Twitter twitter;
	
	public TwitterMoveParser(String hashtag){
		this.hashtag = hashtag;
		configureTwitter();
	}
	
	private void configureTwitter(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("JeRjATwmUq80uikAvzbeIxWIU")
		  .setOAuthConsumerSecret("HwG0WuWRfiKJAu5puJ7haWHIodYmr2gtKtEetSz0aqQ1902itV")
		  .setOAuthAccessToken("379873937-TDrRuqJoNnJ8yF5DukZYAEk612QQSFxXCAH2j6mY")
		  .setOAuthAccessTokenSecret("NeOfV7CQDjC4s6an5F6QSULMryfmf3q6j2Tby9lUzYEyW");
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();		
	}
	
	public TwitterMove getNewMove(TwitterMove lastMove){
		TwitterMove latestMoveFromTwitter = null;
		try {
			latestMoveFromTwitter = parseMoveString(getLatestMoveString());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		// Kui on sama käik, mis eelmine, tagastan eelmise
		if(lastMove.isEqualTo(latestMoveFromTwitter)){
			return lastMove;
		}
		return latestMoveFromTwitter;
	}
	
	private TwitterMove parseMoveString(String twitterMoveString){
		TwitterMove twitterMove = null;
		String[] pieces = twitterMoveString.split(" ");
		int team = Integer.parseInt(pieces[1]);
		int fromRow = Integer.parseInt(pieces[2]);
		int fromColumn = Integer.parseInt(pieces[3]);
		int toRow = Integer.parseInt(pieces[4]);
		int toColumn = Integer.parseInt(pieces[5]);
		twitterMove = new TwitterMove(fromRow, fromColumn, toRow, toColumn, team);
		return twitterMove;
	}
	
	private String getLatestMoveString() throws TwitterException{
		String latestMoveString = null;
	    Query query = new Query(hashtag);
	    query.count(1);
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	        latestMoveString = status.getText();
	    }
	    
	    return latestMoveString;
	}
	
}

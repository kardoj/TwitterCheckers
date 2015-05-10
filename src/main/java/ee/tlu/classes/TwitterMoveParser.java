package ee.tlu.classes;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/*
 * Klass, mis k�sib Twitter'ist tweet'e ja muudab need m�ngu k�ikudeks.
 * K�ike saadetakse kujul:
 * #hashtag 1 1 2 2 1
 * reast tulbast ritta tulpa tiim
 */

public class TwitterMoveParser {
	
	private String hashtag;
	private Twitter twitter;
	private long sinceId = 0;
	
	public TwitterMoveParser(String hashtag){
		this.hashtag = hashtag;
		configureTwitter();
		
		try {
			setInitialSinceId();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
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
		// Kui on sama k�ik, mis eelmine, v�i ei ole �ldse, tagastan eelmise
		if(latestMoveFromTwitter == null || lastMove.isEqualTo(latestMoveFromTwitter)){
			return lastMove;
		}
		return latestMoveFromTwitter;
	}
	
	// M��ran alguses �ra, millisest tweet'ist alates uusi v�tta, et eelmisi mitte lugeda
	private void setInitialSinceId() throws TwitterException{
		Query query = new Query(hashtag);
	    query.count(1);
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	        sinceId = status.getId();
	    }		
	}
	
	private TwitterMove parseMoveString(String twitterMoveString){
		TwitterMove twitterMove = null;
		try{
			String[] pieces = twitterMoveString.split(" ");
			int fromRow = Integer.parseInt(pieces[1]);
			int fromColumn = Integer.parseInt(pieces[2]);
			int toRow = Integer.parseInt(pieces[3]);
			int toColumn = Integer.parseInt(pieces[4]);
			int team = Integer.parseInt(pieces[5]);
			twitterMove = new TwitterMove(fromRow, fromColumn, toRow, toColumn, team);
		} catch(Exception e){
			System.out.println("Ei �nnestunud k�iku luua");
		}
		return twitterMove;
	}
	
	private String getLatestMoveString() throws TwitterException{
		String latestMoveString = null;
	    Query query = new Query(hashtag);
	    query.count(1);
	    query.setSinceId(sinceId);
	    query.getSinceId();
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	        latestMoveString = status.getText();
	        sinceId = status.getId();
	    }
	    return latestMoveString;
	}
	
}

package MiniStressTester;


import java.util.ArrayList;

public class Twitter {
	
	//ADD YOUR CODE BELOW HERE
	MyHashTable<String,ArrayList<Tweet>> date_tweet;
	MyHashTable<String,Tweet> Author_tweet;
	MyHashTable<String,Integer> tweets_wordfreq;
	MyHashTable<String,String> word_tweet;
	ArrayList<Tweet> tweets;
	ArrayList<String> stopWords;
	//ADD CODE ABOVE HERE 
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		this.tweets=tweets;
		this.stopWords=stopWords;
		this.tweets_wordfreq = new MyHashTable<String,Integer>(tweets.size()*100);
		MyHashTable<String,ArrayList<Tweet>> date_tweet=new MyHashTable<>(tweets.size());
		MyHashTable<String,Tweet> Author_tweet=new MyHashTable<>(tweets.size());
		
		this.date_tweet=date_tweet;
		this.Author_tweet=Author_tweet;
		this.stopWords=stopWords;
		
		for(Tweet t:tweets ) {

			MyHashTable<String,String> word_tweet=new MyHashTable<>(tweets.size());
			String d=t.getDateAndTime().substring(0,10);
			if (date_tweet.get(t.getDateAndTime().substring(0, 10))==null) {			
				ArrayList<Tweet> n=new ArrayList<>();
				n.add(t);
				date_tweet.put(d, n);
			}
		
			else {date_tweet.get(d).add(t);}
		
			if (Author_tweet.get(t.getAuthor())==null || (t.compareTo(Author_tweet.get(t.getAuthor()))>0)) {
					Author_tweet.put(t.getAuthor(), t);
			}
			
			ArrayList<String> words = Twitter.getWords(t.getMessage().toLowerCase());
			for(String word : words) {
				word_tweet.put(word,word);
				}
			words=word_tweet.keys();
			for(String word : words) {
			if(this.tweets_wordfreq.get(word.toLowerCase())!=null) {
				Integer freq = this.tweets_wordfreq.get(word.toLowerCase());
				freq += 1;
				this.tweets_wordfreq.put(word.toLowerCase(),freq);}
			
			else {
					this.tweets_wordfreq.put(word.toLowerCase(), 1);
				}
			}
		
		}
		
		//ADD CODE ABOVE HERE 
	}
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE
		String d=t.getDateAndTime().substring(0,10);
		if (date_tweet.get(d)==null) {
			ArrayList<Tweet> n=new ArrayList<>();
			n.add(t);
			date_tweet.put(d, n);
		}
		
		else {date_tweet.get(d).add(t);}
		
		ArrayList<String> words = Twitter.getWords(t.getMessage().toLowerCase());
		MyHashTable<String,String> word_tweet=new MyHashTable<>(tweets.size());
		for(String word : words) {
			word_tweet.put(word,word);
			}
		words=word_tweet.keys();
		for(String word : words) {
		if(this.tweets_wordfreq.get(word.toLowerCase())!=null) {
			Integer freq = this.tweets_wordfreq.get(word.toLowerCase());
			freq += 1;
			this.tweets_wordfreq.put(word.toLowerCase(),freq);}
		
		else {
				this.tweets_wordfreq.put(word.toLowerCase(), 1);
			}
		}
		if (Author_tweet.get(t.getAuthor())==null || t.compareTo(Author_tweet.get(t.getAuthor()))>0) {
			Author_tweet.put(t.getAuthor(), t);
		}

		//ADD CODE ABOVE HERE 
	}
	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
        //ADD CODE BELOW HERE
    	

    	return (Author_tweet.get(author));
    	
        //ADD CODE ABOVE HERE 
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE
    	
    	return (date_tweet.get(date));
    	
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE
    	
    	for(String word : stopWords) {
    		this.tweets_wordfreq.remove(word.toLowerCase());
    	}
    	return MyHashTable.fastSort(this.tweets_wordfreq);
    	
        //ADD CODE ABOVE HERE    	
    }
    
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

    

}

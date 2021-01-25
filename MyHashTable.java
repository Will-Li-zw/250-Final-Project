package MiniStressTester;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
    	this.numEntries=0;
    	this.numBuckets=initialCapacity;
    	buckets = new ArrayList<LinkedList<HashPair<K,V>>>(numBuckets);
    	for (int i=0; i<numBuckets; i++) {
    		buckets.add(new LinkedList<HashPair<K,V>>());
    	}
    	
        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
    	int hashvalue=hashFunction(key);
    	HashPair<K,V> p = new HashPair<>(key,value);
    	LinkedList<HashPair<K,V>> cur=this.buckets.get(hashvalue);
    	if (cur==null) {
    		cur.add(p);
    		this.numEntries++;
    		if ((((double)this.numEntries)/this.numBuckets)>this.MAX_LOAD_FACTOR) {
    			this.rehash();
    		}
        	return null;
    	}
    	else {   		
    		for (HashPair<K,V> np: cur) {
    			if (np.getKey().equals(key)) {
    				V old = np.getValue();
    				np.setValue(value);
    				return old;
    			}
    		}
    		cur.add(p);
    		this.numEntries++;
    		if ((((double)this.numEntries)/this.numBuckets)>this.MAX_LOAD_FACTOR) {
    			this.rehash();
    		}
    		return null;
    	}
    	
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
    	int hashvalue=hashFunction(key);
    	LinkedList<HashPair<K,V>> cur=buckets.get(hashvalue);
    	if (cur==null) {
    		return null;
    	}
    	else {
    		for (int i=0; i<cur.size(); i++) {
    			if (cur.get(i).getKey().equals(key)) {
    				V value = cur.get(i).getValue();
    				return value;
    			}
    		}
    	return null;
    		
    	}
        
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
    	int hashvalue=hashFunction(key);
    	
    	LinkedList<HashPair<K,V>> cur=buckets.get(hashvalue);
    	
    	if (cur==null) {
    		
    		return null;
    	}
    	else {
    		for (int i=0; i<cur.size(); i++) {
    			if (cur.get(i).getKey().equals(key)) {
    				V value = cur.get(i).getValue();
    				cur.remove(i);
    				numEntries--;
    				return value;
    			}
    		}
    	
    	return null;
    		
    	}
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
    	
    	MyHashTable<K,V> newtable = new MyHashTable<K,V> (this.numBuckets*2);
    	for (int i=0; i<this.numBuckets; i++) {
    		for (int j=0; j<this.buckets.get(i).size(); j++) {
    			HashPair<K,V> addthis = this.buckets.get(i).get(j);
    			newtable.put(addthis.getKey(),addthis.getValue());
    		}
    	}
    	
    	this.numBuckets=2*this.numBuckets;
    	this.numEntries=newtable.numEntries;
    	this.buckets=newtable.buckets;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
    	ArrayList<K> mykeys= new ArrayList<K>(this.numEntries);
    	
    	for (int i=0; i<numBuckets; i++) {
    		LinkedList<HashPair<K,V>> cur=buckets.get(i);
    		if (cur!=null) {
    			for (int j=0; j<cur.size(); j++) {
        			mykeys.add(cur.get(j).getKey());
        			
        		}
    			
    		}
    	}
        
    	return mykeys;
    	
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE
    	MyHashTable<V,V> Values = new MyHashTable<V,V>(this.numEntries);
    	
    	for(int i=0; i<this.numBuckets; i++) {
    		for(int j=0; j<this.buckets.get(i).size(); j++) {
				Values.put(this.buckets.get(i).get(j).getValue(),null);
    		}
    	}
    	return Values.keys();
    	
        //ADD CODE ABOVE HERE
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    

    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        //ADD CODE BELOW HERE
    	ArrayList<HashPair<K,V>> sortedResults = new ArrayList<>();
    	ArrayList<K> resultskey= new ArrayList<>();
    	for (HashPair<K, V> entry : results) {
			sortedResults.add(entry);
    	}
    	
    	doMergeSort(sortedResults);
      for(int i=0; i<sortedResults.size(); i++) {
      resultskey.add(sortedResults.get(i).getKey());
      
     }
    	
    	return resultskey;
		
        //ADD CODE ABOVE HERE
    }
   
    	private static <K, V extends Comparable<V>> void  doMergeSort (ArrayList<HashPair<K,V>> pairs){
    	  int middle;
    	  ArrayList<HashPair<K,V>> left = new ArrayList<>();
    	  ArrayList<HashPair<K,V>> right = new ArrayList<>();
    	  
    	  if (pairs.size() > 1) {
    	     middle = pairs.size() / 2;
    	        for (int i = 0; i < middle; i++) 
    	            left.add(pairs.get(i));
    	 
    	        for (int j = middle; j < pairs.size(); j++)
    	            right.add(pairs.get(j));
    	            
    	     doMergeSort(left);
    	     doMergeSort(right);
    	     
    	     merge(pairs, left, right);
    	  }
    	}

    	private static<K, V extends Comparable<V>> void merge (ArrayList<HashPair<K,V>> pairs, ArrayList<HashPair<K,V>> left, ArrayList<HashPair<K,V>> right){
    	  //set up a temporary arraylist to build the merge list
    	  ArrayList<HashPair<K,V>> temp = new ArrayList<>(); 
    	  
    	  //set up index values for merging the two lists 
    	  int numbersIndex = 0;    
    	  int leftIndex = 0;
    	  int rightIndex = 0;

    	  while (leftIndex < left.size() && rightIndex < right.size()) {
    	    if ((left.get(leftIndex).getValue()).compareTo( right.get(rightIndex).getValue())>0 ) {
    	            pairs.set(numbersIndex, left.get(leftIndex));
    	            leftIndex++;
				}
				 else {
    	            pairs.set(numbersIndex, right.get(rightIndex));
    	            rightIndex++;
    	        }
    	        numbersIndex++;
    	    }
    	    int tempIndex = 0;
    	    if (leftIndex >= left.size()) {
    	        temp = right;
    	        tempIndex = rightIndex;
    	    } 
    	    else {
    	        temp = left;
    	        tempIndex = leftIndex;
    	    }
    	    for (int i = tempIndex; i < temp.size(); i++) {
    	        pairs.set(numbersIndex, temp.get(i));
    	        numbersIndex++;
    	    }
    	 }
    


    
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE

   	 int index=0;
   	 ArrayList<HashPair<K,V>> mypairs= new ArrayList<>(numBuckets);
    	
        //ADD YOUR CODE ABOVE HERE
    	
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */ 
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	 for (int i=0; i<numBuckets; i++) {
         		LinkedList<HashPair<K,V>> cur=buckets.get(i);
         		if (cur!=null) {
         			for (int j=0; j<cur.size(); j++) {	
             			mypairs.add(cur.get(j));
             		}       			
         		}
         	}
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
           
        	return (index<mypairs.size());
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	HashPair<K,V> nextpair=mypairs.get(index);
        	index++;
        	return nextpair;
        	
        }
        
    }
}

///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Huffman.java
// File:             PrioQueue.java
// Semester:         CS367 Summer 2014
//
// Author:           Zak Scholl zscholl@wisc.edu
// CS Login:         scholl
// Lecturer's Name:  M. Hidayath Ansari
//
///////////////////////////////////////////////////////////////////////////////
/**
 * Constructs a minimum sorted priority queue using a heap. 
 * @author zakscholl
 *
 * @param <E>
 */
public class PrioQueue<E extends Comparable<E>> {
	private Heap<E> pQueue; // heap that holds the priority queue
	/**
	 * Constructs a new priority queue
	 */
	public PrioQueue(){
		pQueue = new Heap<E>();
	}
	/**
	 * inserts an item into the priority queue
	 * @param item
	 */
	public void insert(Comparable<E> item){
		pQueue.insert(item);
	}
	/**
	 * Removes the minimum value from this priority queue
	 * @return the minimum item of this priority queue
	 */
	public Comparable<E> removeMin(){
		return pQueue.removeMin();
	}
	/**
	 * Returns true if the queue is empty, false otherwise
	 * @return a boolean value representing the emptiness of the queue
	 */
	public boolean isEmpty(){
		return pQueue.isEmpty();
	}
	/**
	 * returns the minimum value of this queue without modifying the queu
	 * @return the minimum item in this queue
	 */
	public Comparable<E> getMin(){
		return pQueue.getMin();
	}
	/**
	 * returns the size of this priority queue
	 * @return int represengting the size of the queue.
	 */
	public int getSize(){ return pQueue.getSize(); }


}

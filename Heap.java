///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Huffman.java
// File:             Heap.java
// Semester:         CS367 Summer 2014
//
// Author:           Zak Scholl zscholl@wisc.edu
// CS Login:         scholl
// Lecturer's Name:  M. Hidayath Ansari
//
///////////////////////////////////////////////////////////////////////////////
/**
 * This class implements a generic max heap.
 * @author zakscholl
 *
 * @param <E>
 */
public class Heap<E extends Comparable<E>>{
	//fields for heap 
	private int currentIndex;
	private Comparable[] heap;
	private final int INIT_SIZE = 256;
	
	/**
	 * constructs a max heap.
	 */
	public Heap(){
		currentIndex = 0;
		heap =  new Comparable[INIT_SIZE];
	}
	/**
	 * This method returns true if the heap is empty and false otherwise
	 * @return boolean representing state of emptiness
	 */
	public boolean isEmpty(){
		return currentIndex == 0;
	}
	
	/**
	 * Expands the array if the array is full to twice its current size
	 */
	private void expandArray(){
		
		//new array thats twice the size
		Comparable[] tmp = new Comparable[currentIndex*2];
		
		//for every item in the array, copy it over to new array
		for(int i = 1; i <= currentIndex; i++){
			tmp[i] = heap[i];
		}
		heap = tmp;
	}
	/**
	 * Inserts an item into the heap and then re-orders the heap
	 * @param obj
	 */
	void insert(Comparable<E> obj){
		//if array is full, expand
		if(currentIndex + 1 >= heap.length) expandArray();
		currentIndex++;
		heap[currentIndex] = (E) obj;
		
		//if not inserting at root
		if(currentIndex > 1){
			heapify();
		}
		
	}
	/**
	 * Removes the maximum element in the heap, returns null if the heap is
	 * empty.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	Comparable<E> removeMin() {
		if (currentIndex == 0){
			return null;
		}
		Comparable<E> tmp = heap[1];
		heap[1] = heap[currentIndex];
		
		int parentIndex = 1;

		// reheapify from the top
	
			while ((parentIndex * 2 + 1) <= currentIndex
					&& (heap[parentIndex].compareTo((E) heap[parentIndex * 2]) > 0
					|| heap[parentIndex]
							.compareTo((E) heap[parentIndex * 2 + 1]) > 0)) {

				// if left child is larger than right then swap left
				if (heap[parentIndex*2 +1] == null || 
						heap[parentIndex * 2].compareTo((E) heap[parentIndex * 2 + 1]) < 0 ) {
					
					
					Comparable<E> tmp1 = heap[parentIndex * 2];
					heap[parentIndex * 2] = heap[parentIndex];
					heap[parentIndex] = (E) tmp1;
					// update parent for next iteration
					parentIndex = parentIndex * 2;
				}
				// else swap with the right child
				else {
					Comparable<E> tmp2 = heap[parentIndex * 2 + 1];
					heap[parentIndex * 2 + 1] = heap[parentIndex];
					heap[parentIndex] = (E) tmp2;
					parentIndex = parentIndex * 2 + 1;
				}
			}
			heap[currentIndex] = null;
			currentIndex--;
			
		return tmp;
	}
	/**
	 * Returns the minimum item of this heap, which is at the first index of
	 * the array
	 * @return
	 */
	@SuppressWarnings("unchecked")
	Comparable<E> getMin(){
		return heap[1];
	}
	/**
	 * Restores the heap property after adding a new element. 
	 */
	@SuppressWarnings("unchecked")
	private void heapify(){
		//holds index of child
		int childIndex = currentIndex;
		//while the child is greater than the parent, swap values...continues until heap is balanced
		while(childIndex != 1 && heap[childIndex].compareTo( heap[childIndex/2]) < 0){
			Comparable<E> obj = heap[childIndex];
			heap[childIndex] = heap[childIndex/2];
			heap[childIndex/2] = obj;
			childIndex = childIndex/2;
		}
	}
	/**
	 * returns the size of the heap, which is the current index
	 * @return currentIndex which is the size of the heap.
	 */
	public int getSize(){ return currentIndex;}

}

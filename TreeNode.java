///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Huffman.java
// File:             TreeNode.java
// Semester:         CS367 Summer 2014
//
// Author:           Zak Scholl zscholl@wisc.edu
// CS Login:         scholl
// Lecturer's Name:  M. Hidayath Ansari
//
///////////////////////////////////////////////////////////////////////////////
/**
 * This class represents a node of a binary tree with two pointers to a left
 * and right child. The node holds an integer for frequency, and a key which
 * contains a character for the Huffman tree.
 * @author zakscholl
 *
 */
public class TreeNode implements Comparable<TreeNode>{
	//fields for pointers and node data
	private TreeNode leftChild, rightChild;
	private char key;
	private int frequency;
	
	/**
	 * Constructs a node with no children
	 * @param s - character key
	 * @param freq - frequency of character
	 */
	public TreeNode(char s, int freq){
		leftChild = null;
		rightChild = null;
		key = s;
		frequency = freq;
	}
	/**
	 * Constructs a null root node
	 */
	public TreeNode(){ this(null, null, 0);}
	/**
	 * Constructs a node with left and right pointers, and frequency
	 * @param left - left child pointer
	 * @param right - right child pointer
	 * @param freq - frequency of character/or containing subtree of characters
	 */
	public TreeNode(TreeNode left, TreeNode right, int freq){
		leftChild = left;
		rightChild = right;
		frequency = freq;
		key = '\0';
	}
	/**
	 * Returns the frequency in this node
	 * @return frequency
	 */
	public int getFreq(){ return frequency;}
	
	/**
	 * Returns the key within this node
	 * @return key
	 */
	public char getKey(){ return key;}
	
	/**
	 * Returns the left child of this node
	 * @return left child TreeNode pointer
	 */
	public TreeNode getLeft(){ return leftChild;}
	
	/**
	 * Returns the right child of this node
	 * @return right child TreeNode pointer
	 */
	public TreeNode getRight(){ return rightChild;}
	
	/**
	 * Sets the left child pointer of this node
	 * @param left - pointer for left child
	 */
	public void setLeftChild(TreeNode left){
		leftChild = left;
	}
	/**
	 * Sets the right child pointer of this node
	 * @param right - pointer for the right child
	 */
	public void setRightChild(TreeNode right){
		rightChild = right;
	}
	/**
	 * Sets the key for this node
	 * @param key - new key
	 */
	public void setKey(char key){ this.key = key;}
	

	/**
	 * Compares two nodes via their frequency, returns the difference of the nodes
	 * so positive if this node is greater than the node passed as the parameter
	 * and vice versa.
	 */
	@Override
	public int compareTo(TreeNode o) {
		return this.frequency - o.frequency;
	}
	
}

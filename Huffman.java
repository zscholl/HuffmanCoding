///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Huffman.java
// Files:            Huffman.java, PrioQueue.java, Heap.java, TreeNode.java
// Semester:         CS367 Summer 2014
//
// Author:           Zak Scholl
// Email:            zscholl@wisc.edu
// CS Login:         scholl
// Lecturer's Name:  M. Hidayath Ansari
//
///////////////////////////////////////////////////////////////////////////////
import java.io.*;
import java.util.*;
import java.nio.charset.Charset;
/**
 * This class takes in a text file and uses Huffman compression to compress the 
 * file. Using the c modifier in the command line. It also decompresses a file
 * provided the huffman code table using the d modifier. During compression it 
 * also outputs an appropriate huffman code table.
 * @author zak scholl
 * 
 */
public class Huffman {
	public static void main(String[] args) throws IOException {
		if (args.length != 4) {
			System.out.println("Usage: java Huffman <option> <codeFileName> <inputFile> <outputFile>");
			System.exit(0);
		}
		String option, codeFileName, inputFile, outputFile;
		option = args[0];
		codeFileName = args[1];
		inputFile = args[2];
		outputFile = args[3];
		Huffman hf = new Huffman();
		if (option.equals("c")) {
		    hf.compress(codeFileName, inputFile, outputFile);// call the compress method
		} else if (option.equals("d")) {
		    hf.decompress(codeFileName, inputFile, outputFile);//call the decompress method
		}
		
	}

	/**
	 * Runs the compression routine
	 *
	 * @param cFile codeFile
	 * @param iFile inputFile
	 * @param oFile outputFile
	 */
	private void compress(String cFile, String iFile, String oFile) {
		
		//construct min priority queue with all characters
		PrioQueue<TreeNode> huffCode = new PrioQueue<TreeNode>();
		Map<Character, Integer> countMap = charCount(new File(iFile));
		//insert a node for each character and frequency into the queue
		for(Character c:countMap.keySet()){
			huffCode.insert(new TreeNode(c, countMap.get(c)));
		}
		
		//construct the tree from queue
		while(huffCode.getSize() != 1){
		TreeNode tmp =  (TreeNode) huffCode.removeMin();
		TreeNode tmp2 = (TreeNode) huffCode.removeMin();
		huffCode.insert(new TreeNode(tmp, tmp2, (tmp.getFreq() + tmp2.getFreq())));
		}
		
		//construct output code file
		TreeNode root = (TreeNode) huffCode.getMin();
	//assigns character bit strings to an array at the index of their numerical representation (0 - 256)
		String[] codeArray = new String[257];
		codeTable(root, "", codeArray);
		
		try {
			PrintWriter wFile = new PrintWriter(cFile);
			Double totLength = 0.0; //hold length
			int numChars = 0; //hold number of characters
			
			//for each character in the map, print out its bit representation
			for (Character c:countMap.keySet()){
				if (c != '\n') {
				wFile.println(c + " " + codeArray[(int)c]);
				numChars++;
				totLength += codeArray[(int)c].length();
				}
			}
			//print final line with average length of each character bit representation
			wFile.printf("%.2f%n", totLength/numChars);
			wFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//construct the compressed output file
		try{
			File inFile = new File(iFile);
			PrintWriter outFile = new PrintWriter(oFile);
			Scanner inScanner = new Scanner(inFile);
			
			while(inScanner.hasNext()){
			String next = inScanner.nextLine();
				
//for each character in the input file, output the binary string representation in the codeArray
				for(int i = 0; i < next.length(); i ++){
					outFile.print(  codeArray[ (int)   next.charAt(i) ]);
				}
			
			
			}
			outFile.close();
			inScanner.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Runs the decompression routine
	 *
	 * @param cFile codeFile
	 * @param iFile inputFile
	 * @param oFile outputFile
	 */
	private void decompress(String cFile, String iFile, String oFile) {
		// root of decompression tree
		TreeNode decompTree = new TreeNode();

		// construct decompression tree
		try {
			File codeFile = new File(cFile);
			Scanner fileSCNR = new Scanner(codeFile);

			while (fileSCNR.hasNext()) {
				// read in each character and string sequence and reconstruct
				// tree using 0 as left link and 1 as right link.
				String code = fileSCNR.nextLine();
				
				char input = code.charAt(0); //holds the character
				code = code.substring(1, code.length()).trim();//holds the bit string representation

				// construct path to node
				TreeNode tmp = decompTree;
				for (int i = 0; i < code.length() - 1; i++) {

					if (code.charAt(i) == '0') {
						//if there is no node there
						if (tmp.getLeft() == null) {
							//construct it
							tmp.setLeftChild(new TreeNode());
							tmp = tmp.getLeft();
						} else
							//move to the left
							tmp = tmp.getLeft();

					} else if (code.charAt(i) == '1') {
						//if there is no node there
						if (tmp.getRight() == null) {
							//construct it
							tmp.setRightChild(new TreeNode());
							tmp = tmp.getRight();
						} else
							//move to the right
							tmp = tmp.getRight();

					}
				}
				// put node in tree containing the character
				if (code.charAt(code.length() - 1) == '0') { //left
					tmp.setLeftChild(new TreeNode(input, 1));
				} else if (code.charAt(code.length() - 1) == '1') {//right
					tmp.setRightChild(new TreeNode(input, 1));

				}

			}
			fileSCNR.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//construct output file (decompressed)
		try {
			PrintWriter outWriter = new PrintWriter(oFile);
			
			TreeNode tmp2 = decompTree;//holds root of tree
			File inFile = new File(iFile);
			Scanner inSCNR = new Scanner(inFile);

			String input = inSCNR.nextLine();
			for (int i = 0; i < input.length(); i++) {
				//if 0 take a left
				if (input.charAt(i) == '0') {
					//check if character is not next
					if (tmp2.getLeft().getFreq() != 1) {
						//move left
						tmp2 = tmp2.getLeft();
					}
					// put the character in the node into the output file
					else if (tmp2.getLeft().getFreq() == 1) {
						//print character to output file
						outWriter.print(tmp2.getLeft().getKey());
						tmp2 = decompTree;//start over from root
					}
					//if 1 take a right
				} else if (input.charAt(i) == '1') {
					//check if character is not next
					if (tmp2.getRight().getFreq() != 1) {
						//move right
						tmp2 = tmp2.getRight();
					}
					// put the character in the node into the output file
					else if (tmp2.getRight().getFreq() == 1) {
						//print character to output file
						outWriter.print(tmp2.getRight().getKey());
						tmp2 = decompTree; //start over from root
					}
				}

			}
			inSCNR.close();
			outWriter.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		

	}

	/**
	 * Counts the frequency of each character in the document
	 * 
	 * @param file the file for which the character frequency needs to be calculated
	 * @return hashmap containing where key is the character and entry is the number of times it appeared in the document
	 */
	private Map<Character, Integer> charCount(File file) {
		try {
			BufferedReader reader = new BufferedReader( new InputStreamReader(new FileInputStream(file),Charset.forName("UTF-8")));
			Map<Character, Integer> countMap = new TreeMap<Character, Integer>();
			int character;
			while((character = reader.read()) != -1) {
			    char c = (char) character;
			    Integer count = countMap.get(new Character(c));
			    if (count != null) {
				countMap.put(c, ++count);
			    } else {
				countMap.put(c, 1);
			    }

			}
			reader.close();
			return countMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * This method goes through a huffman tree and assigns ever character to a corresponding
	 * ASCII index in a string array its binary string representation.
	 * @param root
	 * @param sequence
	 * @param arr
	 */
	private static void codeTable(TreeNode root, String sequence, String[] arr){
		//if node is a leaf
		if (root.getLeft() == null && root.getRight() == null) {
			//add the current bit sequence to the corresponding character index in the array
			arr[(int)root.getKey()] = (sequence);
			sequence = ""; //reset the sequence
		}
		
		
		else if (root.getLeft() != null || root.getRight() != null){ 
			TreeNode tmp = root;
			
			//go left
			sequence += "0";
			codeTable(tmp.getLeft(), sequence, arr);
			//remove last counted 0 because we have moved back from the left turn
			sequence = sequence.substring(0, sequence.length() - 1);
			
			
			
			//go right
			sequence += "1";
			codeTable(tmp.getRight(), sequence, arr);
			//remove last counted 1 because we have moved back from the right turn
			sequence = sequence.substring(0, sequence.length() - 1);
			
		}
	}
}

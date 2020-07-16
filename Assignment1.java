/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author eslemus
 */
public class Assignment1 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        System.out.println("Choose a question to implement from Assignment 1: ");
        System.out.println("1. Profile Matrix with scores and Consensus sequence");
        System.out.println("2. Hamming Distance using Median String finding");
        System.out.println("3. Brute Force Median ");
        System.out.print("= ");
        
        Scanner s = new Scanner(System.in);
	int x = s.nextInt();
        switch(x){
            case 1:
                System.out.println("Implementing Question 1.");
                Assignment1.cons();
                break;
            case 2:
                System.out.println("Implementing Question 2.");
                Assignment1.hamm();
                break;
            case 3:
                System.out.println("Implementing Question 3.");
                System.out.println("Input the Number of sequences and K-mer, respectively.");
                Scanner input = new Scanner(System.in);
                int Num = input.nextInt();
                int subLen = input.nextInt(); // this is the number of subsequences;
                String seqStr[] = new String[Num];
                for(int i=0; i<Num; i++){
                    seqStr[i] = input.next();
                }
                input.close();
	
                Hashtable<String, Integer> kmerTable = new Hashtable<String, Integer>();
                for(int i=0; i<Num; i++){
                    int Len = seqStr[i].length();
                    for(int j=0; j<Len-subLen; j++){
                        String dumpStr = seqStr[i].substring(j, j+subLen);
                        if(!kmerTable.containsKey(dumpStr)){
                            kmerTable.put(dumpStr, 1);
                        }
                    }
                }
        
                kmerTable = restore(kmerTable); 
                kmerTable = restore(kmerTable); 
                solveDist(kmerTable, seqStr, subLen, Num);
                break;
        }
        
    }
    
    
    //----------------Question 1 Function------------------------------------
    private static void cons() {
		int baseA[] = new int[1];
		int baseC[] = new int[1];
		int baseG[] = new int[1];
		int baseT[] = new int[1];
		try {
			Scanner inStream = new Scanner(new File("align1.txt"));
			String input = "";
			
			boolean first = true;
			try{
			while(true){
				input = inStream.nextLine();
				System.out.println("Implementing: " + input);
				
				
				if(first)
                                {
					baseA = new int[input.length()];
					baseC = new int[input.length()];
					baseG = new int[input.length()];
					baseT = new int[input.length()];
                                        
					for(int i = 0; i<input.length();i++){
						baseA[i] = 0;
						baseC[i] = 0;
						baseG[i] = 0;
						baseT[i] = 0;
					}
					first = false;
				}
			
			
				for(int i = 0; i < input.length();i++){
					switch(input.charAt(i)){
					case 'A':
						baseA[i]++; break;
					case 'C':
						baseC[i]++; break;
					case 'G':
						baseG[i]++; break;
					case 'T':
						baseT[i]++; break;
					} 
				}
			}
			}catch(Exception e){
				System.err.println(e); //blank lines
			}//end try-catch
			
			String sequence ="";
			int[] count = new int[input.length()];
                        int score = 0;
                        
			for(int i = 0; i < input.length();i++)
                        {
                            //Frequency count of alignment, and a counter for scoring purposes
				if(baseA[i] > baseC[i]){
					if(baseA[i] > baseG[i])
                                        {
						if(baseA[i] > baseT[i])
                                                {
							sequence += "A";
                                                        count[i] += baseA[i];
                                                        score += count[i];
                                                        //System.out.println("Frequency of A:" + count[i]);
                                                        //System.out.println("Score after A.1:" + score);
						}
                                                else
                                                {
							sequence += "T";
                                                        count[i] += baseT[i];
                                                        score += count[i];
                                                        //System.out.println("Frequency of T" + count[i]);
                                                        //System.out.println("Score after T.1:" + score);
						}
                                                
					}
                                        else if(baseG[i] > baseT[i])
                                        {
						sequence +=  "G";
                                                count[i] += baseG[i];
                                                score += count[i];
                                                //System.out.println("Frequency of G:" + count[i]);
                                                //System.out.println("Score after G.1:" + score);
					}
                                        else
                                        {
						sequence += "T";
                                                count[i] += baseT[i];
                                                score += count[i];
                                                //System.out.println("Frequency of T:" + count[i]);
                                                //System.out.println("Score after T.2:" + score);
					}
				}
                                else if(baseC[i] >  baseG[i])
                                {
					if(baseC[i] > baseT[i])
                                        {
						sequence += "C";
                                                count[i] += baseC[i];
                                                score += count[i];
                                                //System.out.println("Frequency of C: " + count[i]);
                                                //System.out.println("Score after C.1:" + score);
					}
                                        else
                                        {
						sequence += "T";
                                                count[i] += baseT[i];
                                                score += count[i];
                                                //System.out.println("Frequency of T:" + count[i]);
                                                //System.out.println("Score after T.3:" + score);
					}
				}
                                else if(baseG[i] > baseT[i])
                                {
					sequence += "G";
                                        count[i] += baseG[i];
                                        score += count[i];
                                        //System.out.println("Frequency of G:" + count[i]);
                                        //System.out.println("Score after G.2:" + score);
				}else{
					sequence += "T";
                                        count[i] += baseT[i];
                                        score += count[i];
                                        //System.out.println("Frequency of T:" + count[i]);
                                        //System.out.println("Score after T.4:" + score);
				}
			}
			
			PrintWriter Q1_output = new PrintWriter(new File("Q1_output.txt"));
			
                        //-------Display Profile---------------------------
			Q1_output.print("A:");
			for(int i = 0; i < input.length(); i++){
				Q1_output.printf(" %d",baseA[i]);
			}
			Q1_output.println();
			Q1_output.print("C:");
			for(int i = 0; i < input.length(); i++){
				Q1_output.printf(" %d",baseC[i]);
			}
			Q1_output.println();
			Q1_output.print("G:");
			for(int i = 0; i < input.length(); i++){
				Q1_output.printf(" %d",baseG[i]);
			}
			Q1_output.println();
			Q1_output.print("T:");
			for(int i = 0; i < input.length(); i++){
				Q1_output.printf(" %d",baseT[i]);
			}
                        
                        Q1_output.println();
                        Q1_output.println(score); //print score
                        Q1_output.println("Consensus Sequence:" + sequence); //print cons seq
                        
			Q1_output.close();
			inStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
		}
		
	}
    
    //---------------Question 2 Function-------------------------------------
    //NOTE: I was only successful in finding the hamming distance of DNA sequences of equal length
    private static void hamm() {
	try {
		Scanner in = new Scanner(new File("sequences.txt"));
		String s1 = in.nextLine();
		String s2 = in.nextLine();
		in.close();
			
		int hammingDist = 0;
			
		for(int i = 0; i < s1.length();i++){
			if(s1.charAt(i) != s2.charAt(i)){
				hammingDist++;
			}
		}
			
		PrintWriter out = new PrintWriter(new File("Q2_output.txt"));
		out.printf("%d",hammingDist);
		out.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
    }
    
    //-----------------------Question 3 Functions----------------------------------------
    private static void solveDist(Hashtable<String, Integer> kmerTable, String[] seqStr, int subLen, int Num) {
		
		int seq = Num*2; 
		
		for(String token: kmerTable.keySet()){
			int d_value=0;
			for(int i=0; i<Num; i++){
				d_value += getKey(token, seqStr[i]);
				
			} 
			
			if(d_value <= seq){
				seq = d_value;
				System.out.println("Finding Motif= " + seq +" " + token);
			}
                    
		}
                System.out.println("The last outputted motif is the correct motif median string");
	}
    
    private static int getKey(String tok, String Sequence) {
		// TO get the d_value of string key, comparing to String Sequence;
		
		int Len = Sequence.length();
		int seqLen = tok.length();
		int distance = seqLen; // suppost there's no match with key in the Sequence at first;
		
		for(int i=0; i<Len-seqLen+1; i++){
			String temStr = Sequence.substring(i, i+seqLen);
			int diff = compare(temStr, tok);
			if(diff <= distance){
				distance = diff;
			} 
		} 
		return distance;
	}
    
    private static int compare(String dumpStr, String token) {
		// TO count how many different chars between two strings;
		int Len = dumpStr.length();
		int diff=0;
		for(int i=0; i<Len; i++){
			if(dumpStr.charAt(i) != token.charAt(i))
				diff++;
		}
		return diff;
	}
    
    private static Hashtable<String, Integer> restore( Hashtable<String, Integer> kmerTable) {
		// TO return another hashtable with 1 base replaced with AGTC;
		
		Hashtable<String, Integer> retTable = new Hashtable<String, Integer>();
		for(String key : kmerTable.keySet()){
			int Len = key.length();
			char[] base = {'A', 'C', 'G', 'T'};
			
			for(int i=0; i<Len; i++){
				for(int j=0; j<4; j++){
					String dumpString = key.substring(0, i) + base[j] + key.substring(i+1);
					
					if( !retTable.containsKey(dumpString)){
						retTable.put(dumpString, 1);
					
					} // end if 
				}
			} //end for i<Len loop;
			
		} // end for key:kmerTable.keySet(); 
		
		return retTable;
	}
}

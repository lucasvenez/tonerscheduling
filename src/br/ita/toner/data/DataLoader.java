package br.ita.toner.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class DataLoader {

	public SparseMatrix loadFileAsSparseMatrix(String filePath) {
		
		SparseMatrix matrix = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
			
			String line = br.readLine();
			
			int n = Integer.parseInt(line.split(" ")[0]);
			
			int m = Integer.parseInt(line.split(" ")[1]);
			
			matrix = new SparseMatrix(n, m);
			
		    for(int i = 0; (line = br.readLine()) != null; i++) {
		    	
		    	String[] numbers = line.split(" ");
		    	
		    	for (int j = 0; j < numbers.length; j++)
		    		if (numbers[j].equals("1"))
		    			matrix.addPair(i, j);
		    }

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return matrix;
	}
	
	public List<BitSet> loadFileAsBitSetList(String filePath) {
		
		List<BitSet> result = new ArrayList<BitSet>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
			
			String line = br.readLine();				
			
		    for(int i = 0; (line = br.readLine()) != null; i++) {
		    	
		    	String[] numbers = line.split(" ");
		    	
		    	result.add(new BitSet());
		    	
		    	for (int j = 0; j < numbers.length; j++)
		    		if (numbers[j].equals("1"))
		    			result.get(i).set(j);
		    }

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return result;
	}
}

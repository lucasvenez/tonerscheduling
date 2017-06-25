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
	
	public List<BitSet> loadFileAsBitsetList(String filePath) {		
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
			
			String line = br.readLine();
						
			int m = Integer.parseInt(line.split(" ")[1]);	
			
			List<BitSet> answer = new ArrayList<>();
						
		    while ((line = br.readLine()) != null) {		    	
		    	
		    	String[] numbers = line.split(" ");
		    	
		    	BitSet curr_line = new BitSet(m);
		    	
		    	for (int j = 0; j < numbers.length; j++)
		    		if (numbers[j].equals("1"))
		    			curr_line.set(j);

		    	answer.add(curr_line);
		    }
		    
			return answer;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load input file");
		}
	}
}

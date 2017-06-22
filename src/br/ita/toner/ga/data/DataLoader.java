package br.ita.toner.ga.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import br.ita.toner.data.TestCase;
import br.ita.toner.ga.individual.SparseMatrix;

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
	
public TestCase loadFileAsBitsetList(String filePath) {		
    try(BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
			
        String line = br.readLine();
						
        int m = Integer.parseInt(line.split(" ")[1]);	
			
        List<BitSet> answer = new ArrayList<>();
						
        for(int i = 0; i < n; i++) {		    	
            line = br.readLine();
            String[] numbers = line.split(" ");
		    	
            BitSet curr_line = new BitSet();
		    	
            for (int j = 0; j < numbers.length; j++) {
                if (numbers[j].equals("1")) {
                    curr_line.set(j);
                }
            }
		    	
            answer.add(curr_line);
        }
		    
        TestCase test = new TestCase(n, m, answer);
		    
        return test;
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to load input file");
    }
	}
}

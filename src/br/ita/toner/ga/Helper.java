package br.ita.toner.ga;

import java.util.ArrayList;
import java.util.List;

public class Helper {
	
	public static List<Integer> generateIdentityArray(int size) {
		
		List<Integer> identity = new ArrayList<Integer>();
		
		for (int i = 0; i < size; i++)
			identity.add(i);
		
		return identity;		
	}
	
	public static List<Integer> generateEmptySet(int size) {
		
		List<Integer> result = new ArrayList<Integer>();
		
		for (int l = 0; l < size; l++) result.add(-1);
		
		return result;
	}
}

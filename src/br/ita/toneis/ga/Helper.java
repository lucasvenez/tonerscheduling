package br.ita.toneis.ga;

import java.util.ArrayList;
import java.util.List;

public class Helper {
	
	public static List<Integer> generateIdentityArray(int size) {
		
		List<Integer> identity = new ArrayList<Integer>();
		
		for (int i = 0; i < size; i++)
			identity.add(i);
		
		return identity;		
	}
}

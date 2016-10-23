package tema4.controller;

import tema4.model.*;

public class Test {

	public static void main(String[] args) {
		
		
		LabyrinthModelFile laby = new LabyrinthModelFile();
		
		try {
			
			int t= 0;
			
			(laby).readMatrix("src\\data.txt");
			
			int c = 0;
					
			c = (laby).getClumnCount();
			
			System.out.println(c);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
}

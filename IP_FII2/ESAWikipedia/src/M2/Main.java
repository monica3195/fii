package ip;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Concepts concepte = new Concepts();
	//	concepte.showConcepts();
		//System.out.println(concepte.findConcept("Rupturile"));
		//LoadFile load = new LoadFile();
	//	load.readFile("C:\\Users\\A\\Desktop\\DUML\\606\\1");
	/*	StringBuilder string = new StringBuilder();
		File f = new File("C:\\Users\\A\\Desktop\\DUML\\606\\1");
		  try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"))){
	            while (in.ready()) {
	                String line = in.readLine();
	                string.append(line);
	              
					
	            }
	        } catch (IOException e){
				System.out.println(e.getMessage());
			}
		  System.out.println(string);
	    }*/
	//	ProcessingFile p = new ProcessingFile();
	//	p.processingFile("DUML\\606\\1");
		
		ProcessingWikipedia procesare = new ProcessingWikipedia("DUMP");
		
	      
	}
}
	



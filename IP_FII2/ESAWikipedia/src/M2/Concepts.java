package ip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Concepts {
	private HashMap<String, String> concepts = new HashMap<>();
	private static String numeDump="rowiki.txt";
	public Concepts() {
		File file= new File(System.getProperty("user.dir")+System.getProperty("file.separator")+numeDump);

		String[] list;
		String[] list1;
		 int j=0;   
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))){
	        
			while (in.ready()) {
	            	String aux;
	            	
	            	list= in.readLine().split(":");
	            //	System.out.println(list[2]+"dada");
	            	/*if(list.length>=4)
	            	{
	            	int i;
	            	StringBuilder buildString = new StringBuilder(); 
	            	if(list[2].compareTo("Cod")==0||list[2].compareTo("Portal")==0||list[2].compareTo("Categorie")==0||list[2].compareTo("Format")==0||(list[2].startsWith("Fi")==true&&list[2].endsWith("ier")==true))
	            	 	i=3;
	            	else 
	            		i=2;
	            
	            		for(;i<list.length;i++)
	            		buildString.append(list[i]+(i+1<list.length?":":""));
	            		aux=buildString.toString();
	            	
	            	}
	            	else aux=list[list.length-1];*/
	            	//list1=aux.split("\\(");
	            	//if(aux.length()!=0)
					//{
	            		//if(list1[0].contains(")")==false&&list1[0].contains("(")==false)
	            		concepts.put(list[2].toLowerCase(),list[0] + ':' + list[1]);
					//}
	            	
	            }
	        } catch (IOException e){
				System.out.println(e.getMessage());
			}
		System.out.println(j);
	}

	public void showConcepts() {
		SortedSet<String> keys = new TreeSet<String>(concepts.keySet());
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(keys);
		for (String key : list) {
			System.out.println(concepts.get(key)+" "+key);
		}
	}
	public HashMap<String, String> getConcepts() {
		return concepts;
	}
	public String findConcept(String numeConcept)
	{
		return concepts.get(numeConcept);
	}

}

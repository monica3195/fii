package ip;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class ProcessingWikipedia {
	private HashMap<String, String> allConcepts ;
	private HashMap<String, Integer> conceptsFind ;
	private ArrayList<String> listWord;
	ProcessingFile procesare;
	BufferedWriter bw ;
	FileWriter fw;
	public ProcessingWikipedia(String nameDirector) {
	allConcepts=new Concepts().getConcepts();
		File f= new File(System.getProperty("user.dir")+System.getProperty("file.separator")+nameDirector);
				if(f.isDirectory()){
			File[] dirs=f.listFiles();
			for(File dir:dirs){//for each directory
				if(dir.isDirectory()){
					
					File[] fisiere = dir.listFiles();
					for(File fisier:fisiere){
						//System.out.println(fisier.getAbsolutePath());
						//System.out.println(fisier.length()+fisier.getName());
						creareFisiere(dir.getName(),fisier);
					}
				}
			}
		}
		else if(f.isFile()){
			//proceseaza fisier			
		}
		else{
			System.out.println("Calea introdusa este eronata.");
		}
		//File fisier= new File("C:\\Users\\A\\workspace\\ip\\1");
		//creareFisiere("517218",fisier);
	}
	public void creareFisiere(String director,File fisier)
	{
		procesare=new ProcessingFile();
		procesare.processingFile(fisier.getAbsolutePath(),allConcepts);
		createFileConcepts(director,fisier);
		createFileWord(director,fisier);
	}
	public void createFileConcepts(String director,File fisier)
	{
		File file = new File(fisier.getAbsolutePath()+".concept");
		 try {
		if (!file.exists()) {
				file.createNewFile();
		}
		Writer bw = new BufferedWriter(new OutputStreamWriter(
			    new FileOutputStream(file.getAbsoluteFile()), "UTF-8"));
			conceptsFind=procesare.getListConcepts();
			bw.write(director+":"+fisier.getName()+" "+procesare.getTotalConcepts()+"\n");
			SortedSet<String> keys = new TreeSet<String>(conceptsFind.keySet());
			ArrayList<String> list = new ArrayList<String>();
			list.addAll(keys);
			for (String key : list) {
				bw.write(allConcepts.get(key)+" "+conceptsFind.get(key)+" "+key+"\n");
			}
			bw.close();
		 } catch (IOException e) {
				e.printStackTrace();
			}
	}
	public void createFileWord(String director,File fisier)
	{
		File file = new File(fisier.getAbsolutePath()+".np");
		try{
			Writer bw = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(file.getAbsoluteFile()), "UTF-8"));
		listWord=procesare.getListWord();
		for(String word:listWord)
		bw.write(word+" ");
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}

}

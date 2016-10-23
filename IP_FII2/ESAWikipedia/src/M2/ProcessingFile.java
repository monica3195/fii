package ip;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;


public class ProcessingFile {
	private HashMap<String, Integer> listConcepts;
	private ArrayList<String> listWord;
	private HashMap<String, String> concepts ;
	private int totalConcepts;

	public ProcessingFile() {
	}
	public void processingFile(String file,HashMap<String, String> concepts)
	{	
		totalConcepts=0;
		this.concepts = concepts;
		listConcepts=new HashMap<>();
		listWord=new ArrayList<String>();
		int ok,ok1,j,i,limit;	
		String[] list;
		String aux=null,conceptFound=null;
		String textFile=getTextFile(file).toLowerCase();
		
		list=textFile.split(" ");
		for(int index=0;index<list.length;index++)
		{
			i=index;
			limit=(index+250<list.length?index+250:list.length);
		for(;i<limit;i++)
		{
			ok1=1;
			ok=1;
			j=i;
			StringBuilder x= new StringBuilder();
			while(ok1==1&&j!=limit)
			{
				x.append(list[j]);
				if(concepts.get(x.toString())!=null) {ok=0;
												aux=x.toString();
												i=j;
				}
				else if(ok==0) ok1=0;
				x.append(" ");
				j++;
			}
			if(ok==0) {if(listConcepts.get(aux)==null)listConcepts.put(aux, 1);
						else listConcepts.put(aux,listConcepts.get(aux)+1);
			totalConcepts++;
			}
			else     listWord.add(list[i]);
		}
		index=i-1;
		}
		/*SortedSet<String> keys = new TreeSet<String>(listConcepts.keySet());
		ArrayList<String> list1 = new ArrayList<String>();
		list1.addAll(keys);
		for (String key : list1) {
			System.out.println(listConcepts.get(key)+" "+key);
		}*/
		}
	public String getTextFile(String nameFile)
	{
		StringBuilder string=new StringBuilder();
		 try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile), "UTF8"))){
	            while (in.ready()) {
	            string.append(in.readLine());	
	            }
	        } catch (IOException e){
				System.out.println(e.getMessage());
			}

		 return string.toString();
	}
	public HashMap<String, Integer> getListConcepts()
	{
		return listConcepts;
	}
	public ArrayList<String> getListWord()
	{
		return listWord;
	}
	public int getTotalConcepts()
	{
		return totalConcepts;
	}
	
}

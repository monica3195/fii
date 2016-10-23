package modul3.simpleconceptstatistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ovidiu
 */
public class StatisticsCollection {

	Map<File, PageStatistics> colectie;
	File outputFile;
//	File outputFileRest;
	String sep = System.getProperty("file.separator");;
	
	public StatisticsCollection() {
		colectie = new HashMap<>();
		outputFile = new File(System.getProperty("user.dir")+ sep + "simpleConceptStatisticsOutput");
		if(!outputFile.exists()){
			outputFile.mkdir();
		}
//		outputFileRest = new File(System.getProperty("user.dir")+ sep + "outputRest");
//		if(!outputFileRest.exists()){
//			outputFileRest.mkdir();
//		}
	}

	public void add(File f, PageStatistics ps) {
		colectie.put(f, ps);
	}

	public void clear() {
		colectie.clear();
	}

	public PageStatistics get(File f) {
		return colectie.get(f);
	}

	public boolean isAnalysed(File pagina) {
		return colectie.containsKey(pagina);
	}

	public void saveToFile() {
		System.out.println("Scriu in fisiere.. Te rog sa astepti!");
		for(File key:colectie.keySet()){
			File creeazaDir = new File(outputFile.getAbsolutePath()+sep+key.getParent().substring(key.getParent().lastIndexOf(sep)+1));
			if(!creeazaDir.exists()&&!creeazaDir.getName().equals("lemmaProcessingOutput"))
				creeazaDir.mkdir();
			File temp;
			if(key.getParent().substring(key.getParent().lastIndexOf(sep)+1).equals("lemmaProcessingOutput"))
				temp = new File(outputFile.getAbsolutePath()+sep+key.getName()+".concept1");
			else
				temp = new File(outputFile.getAbsolutePath()+sep+creeazaDir.getName()+sep+key.getName()+".concept1");
			try(BufferedWriter outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp), "UTF8"))){
				if(key.getParent().substring(key.getParent().lastIndexOf(sep)+1).equals("lemmaProcessingOutput"))
					outFile.write(key.getName()+" "+colectie.get(key).getResult("NrCuvinte")+System.getProperty("line.separator"));
				else
					outFile.write(key.getParent().substring(key.getParent().lastIndexOf(sep)+1)+":"+key.getName()+" "+colectie.get(key).getResult("NrCuvinte")+System.getProperty("line.separator"));
				outFile.write(colectie.get(key).getResult("FrecvCuvinte"));
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
			//restFiles...
//			File creeazaDir2 = new File(outputFileRest.getAbsolutePath()+sep+key.getParent().substring(key.getParent().lastIndexOf(sep)+1));
//			if(!creeazaDir2.exists()&&!creeazaDir2.getName().equals("lemmaProcessingOutput"))
//				creeazaDir2.mkdir();
//			String sir=colectie.get(key).getResultRest("FrecvCuvinte");
//			if(sir.length()>0){
//				File rest;
//				if(key.getParent().substring(key.getParent().lastIndexOf(sep)+1).equals("lemmaProcessingOutput"))
//					rest = new File(outputFileRest.getAbsolutePath()+sep+key.getName()+".restul");
//				else
//					rest = new File(outputFileRest.getAbsolutePath()+sep+creeazaDir.getName()+sep+key.getName()+".restul");
//				try(BufferedWriter outFileRest = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rest), "UTF8"))){		
//					outFileRest.write(sir);
//				} catch (IOException ex) {
//					System.out.println(ex.getMessage());
//				}
//			}
		}
	}
}

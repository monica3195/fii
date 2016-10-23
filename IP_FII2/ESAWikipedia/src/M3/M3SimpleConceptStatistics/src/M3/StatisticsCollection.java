package M3;

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
	String sep = System.getProperty("file.separator");;
	
	public StatisticsCollection() {
		colectie = new HashMap<>();
		outputFile = new File(System.getProperty("user.dir")+ sep + "output");
		if(!outputFile.exists()){
			outputFile.mkdir();
		}
	}

	public void add(File f, PageStatistics ps) {
		colectie.put(f, ps);
	}

	public void remove(File f) {
		colectie.remove(f);
	}

	public PageStatistics get(File f) {
		return colectie.get(f);
	}

	public boolean isAnalysed(File pagina) {
		return colectie.containsKey(pagina);
	}

	public void saveToFile() {
		System.out.println("Salvez in fisiere.. va dura ceva timp!");
		for(File key:colectie.keySet()){
			File creeazaDir = new File(outputFile.getAbsolutePath()+sep+key.getParent().substring(key.getParent().lastIndexOf(sep)+1));
			if(!creeazaDir.exists()&&!creeazaDir.getName().equals("input"))
				creeazaDir.mkdir();
			File temp;
			if(key.getParent().substring(key.getParent().lastIndexOf(sep)+1).equals("input"))
				temp = new File(outputFile.getAbsolutePath()+sep+key.getName()+".concept1");
			else
				temp = new File(outputFile.getAbsolutePath()+sep+creeazaDir.getName()+sep+key.getName()+".concept1");
			try(BufferedWriter outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp), "UTF8"))){
				if(key.getParent().substring(key.getParent().lastIndexOf(sep)+1).equals("input"))
					outFile.write(key.getName()+" "+colectie.get(key).getResult("NrCuvinte")+System.getProperty("line.separator"));
				else
					outFile.write(key.getParent().substring(key.getParent().lastIndexOf(sep)+1)+":"+key.getName()+" "+colectie.get(key).getResult("NrCuvinte")+System.getProperty("line.separator"));
				outFile.write(colectie.get(key).getResult("FrecvCuvinte"));
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}

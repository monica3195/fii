package m3simpleconceptstatistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/** 
 *
 * @author Ovidiu
 */
public class SimpleConceptStatistics {
	JProgressBar bar;
	JLabel status;
	StatisticsCollection colectie = new StatisticsCollection();
	public static File pageIndex = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"index-maping.txt");
	public static Map<String, String> index = new HashMap<>();
	public static String sep = System.getProperty("file.separator");
	public SimpleConceptStatistics(JProgressBar b, JLabel s){
		bar=b;
		status = s;
	}
	public void run(){
		status.setText("Pas 3/4  Incarc Indexul...");
		StatisticsCollection colectie = new StatisticsCollection();
		loadIndex();
		status.setText("Pas 3/4  Identific conceptele...");
        File f = new File(System.getProperty("user.dir")+sep+"M3"+sep+"lemmaProcessingOutput");
		if(f.isDirectory()){
			System.out.println("Analysing...");
			File[] dirs=f.listFiles();
			bar.setMaximum(dirs.length);
			int c = 0;
			for(File dir:dirs){//for each directory
				if(dir.isDirectory()){
					File[] fisiere = dir.listFiles();
					bar.setValue(c++);
					for(File fisier:fisiere){//for each file in the directory
						Statistica fc = new FrecvCuvinte();
						Statistica nc = new NrCuvinte();
						PageStatistics ps = new PageStatistics();
						ps.atachStatistic(nc);
						ps.atachStatistic(fc);
						ps.runAnalyser(fisier);
						colectie.add(fisier, ps);
					}
				}
				else{
					bar.setValue(c++);
					Statistica fc = new FrecvCuvinte();
					Statistica nc = new NrCuvinte();
					PageStatistics ps = new PageStatistics();
					ps.atachStatistic(nc);
					ps.atachStatistic(fc);
					ps.runAnalyser(dir);
					colectie.add(dir, ps);
				}
				System.out.println("Folder: "+dir.getName());
				if(c%100==99){// ca sa nu streseze memoria, salveaza din 100 in 100 foldere!
					status.setText("Pas 3/4  Scriu in fisier...");
					colectie.saveToFile();
					colectie.clear();
					status.setText("Pas 3/4  Identific conceptele...");
				}
			}
		}
		else{
			System.out.println("Calea introdusa este eronata.");
		}
		status.setText("Pas 3/4  Scriu in fisier...");
		colectie.saveToFile();
		colectie.clear();
    }
	public static void loadIndex(){
		String indexLine;
		System.out.println("Loading index file...");
		try(BufferedReader indexIn = new BufferedReader(new InputStreamReader(new FileInputStream(pageIndex), "UTF8"))){
			while(indexIn.ready()){
				indexLine=indexIn.readLine();
				indexLine=indexLine.toLowerCase(new Locale("ro"));
				index.put(indexLine.substring(indexLine.indexOf(':',indexLine.indexOf(':')+1)+1), indexLine.substring(0, indexLine.indexOf(':',indexLine.indexOf(':')+1)));
			}
		} catch (IOException e){
			System.out.println("Eroare la construirea hashmap-ului din index.");
			System.out.println(e.getMessage());
		}
	}
}

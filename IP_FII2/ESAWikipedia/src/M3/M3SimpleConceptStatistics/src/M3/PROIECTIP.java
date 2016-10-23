package M3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Ovidiu
 */
public class PROIECTIP {
	StatisticsCollection colectie = new StatisticsCollection();
	public static File pageIndex = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"index-maping.txt");
	public static Map<String, String> index = new HashMap<>();
	public static String sep = System.getProperty("file.separator");
    public static void main(String[] args) {
		StatisticsCollection colectie = new StatisticsCollection();
		loadIndex();
        File f;
		if(args.length>0)
			f=new File(args[0]);
		else
			f= new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"input");
		if(f.isDirectory()){
			System.out.println("Transforming...");
			File[] dirs=f.listFiles();
			for(File dir:dirs){//for each directory
				if(dir.isDirectory()){
					File[] fisiere = dir.listFiles();
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
					Statistica fc = new FrecvCuvinte();
					Statistica nc = new NrCuvinte();
					PageStatistics ps = new PageStatistics();
					ps.atachStatistic(nc);
					ps.atachStatistic(fc);
					ps.runAnalyser(dir);
					colectie.add(dir, ps);
				}
				System.out.println("Folder: "+dir.getName());
			}
		}
		else if(f.isFile()){
			Statistica fc=new FrecvCuvinte(); 
			Statistica nc=new NrCuvinte();
			PageStatistics ps = new PageStatistics();
			ps.atachStatistic(fc);
			ps.atachStatistic(nc);
			ps.runAnalyser(f);
			colectie.add(f, ps);
		}
		else{
			System.out.println("Calea introdusa este eronata.");
		}
		colectie.saveToFile();
    }
	public static void loadIndex(){
		String indexLine;
		System.out.println("Loading index file...");
		try(BufferedReader indexIn = new BufferedReader(new InputStreamReader(new FileInputStream(pageIndex), "UTF8"))){
			while(indexIn.ready()){
				indexLine=indexIn.readLine();
				indexLine=indexLine.toLowerCase(new Locale("ro"));
				index.put(indexLine.substring(indexLine.indexOf(':',indexLine.indexOf(':')+1)+1), indexLine.substring(0, indexLine.indexOf(':',indexLine.indexOf(':')+1)));
//				System.out.println(indexLine.substring(0, indexLine.indexOf(':',indexLine.indexOf(':')+1))+" "+indexLine.substring(indexLine.indexOf(':',indexLine.indexOf(':')+1)+1));
			}
		} catch (IOException e){
			System.out.println("Eroare la construirea hashmap-ului din index.");
			System.out.println(e.getMessage());
		}
	}
}
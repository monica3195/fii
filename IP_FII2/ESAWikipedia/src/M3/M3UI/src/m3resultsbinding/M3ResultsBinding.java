package m3resultsbinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

/**
 *
 * @author Ovidiu
 */
public class M3ResultsBinding{
	JProgressBar bar;

	public M3ResultsBinding(JProgressBar b) {
		bar=b;
	}
	String sep = System.getProperty("file.separator");
	public final File outputDir=new File(System.getProperty("user.dir")+sep+"M3"+sep+"3.output");
	public void run(){
		int c = 0;
        final File inputDir1=new File(System.getProperty("user.dir")+sep+"M3"+sep+"1.inputM2");
		final File inputDir2=new File(System.getProperty("user.dir")+sep+"M3"+sep+"simpleConceptStatisticsOutput");
		File currentOutputDir;
		bar.setMaximum(inputDir1.listFiles().length);
		if(inputDir1.exists() && inputDir1.isDirectory() && inputDir2.exists() && inputDir2.isDirectory()){
			Path dirs1 = Paths.get(System.getProperty("user.dir")+sep+"M3"+sep+"1.inputM2");
			Path dirs2 = Paths.get(System.getProperty("user.dir")+sep+"M3"+sep+"simpleConceptStatisticsOutput");
			try(DirectoryStream<Path> dirStream1 = Files.newDirectoryStream(dirs1);
				DirectoryStream<Path> dirStream2 = Files.newDirectoryStream(dirs2)){
				Iterator<Path> it = dirStream2.iterator();
				for(Path dir1:dirStream1){//for each directory
					System.out.println("Director curent: "+dir1.getFileName());
					currentOutputDir = new File(outputDir.getAbsolutePath()+sep+dir1.getFileName());
					if(!currentOutputDir.exists())
						currentOutputDir.mkdirs();
					it.hasNext();
					bar.setValue(c++);
					Path dir2 = it.next();
					if(dir1.getFileName().equals(dir2.getFileName())){
						combineResults(dir1,dir2);
					}
					else{
						System.out.println("Am gasit 2 foldere ce nu au acelasi nume. Ma opresc..");
						System.exit(1);
					}
				}
			} 
			catch (IOException ex) {
				System.out.println("Eroare la parsare.");
			}
		}
		else{
			System.out.println("Directoarele \'1.inputM2\' sau \'simpleConceptStatisticsOutput\' nu exista!");
		}
    }

	private void combineResults(Path p1, Path p2) {
		File dir = p1.toFile();
		File fisiere1[] = dir.listFiles();
		dir = p2.toFile();
		File fisiere2[] = dir.listFiles();
		int i=0;
		for(File fisier2:fisiere2){
			for(;i<fisiere1.length;i++){
				if(fisiere1[i].getName().equals(fisier2.getName().substring(0, fisier2.getName().length()-1))){
					combine(fisiere1[i++],fisier2);
					break;
				}
				else
					copy(fisiere1[i]);
			}
		}
		for(;i<fisiere1.length;i++)
			copy(fisiere1[i]);
	}

	private void combine(File fisier1, File fisier2) {
		String folder = fisier1.getParent().substring(fisier1.getParent().lastIndexOf(sep)+1);
		File output = new File(outputDir.getAbsolutePath()+sep+folder+sep+fisier1.getName().substring(0,fisier1.getName().length()-8));
		Map<String, Integer> map = new HashMap<>();
		Map<String, String> mapName = new HashMap<>();
		int total = 0;
		String linie;
		try(BufferedReader inp1 = new BufferedReader(new InputStreamReader(new FileInputStream(fisier1), "UTF8"))){
			if(inp1.ready()){
				linie=inp1.readLine();
				String []tokens = linie.split(" ");
				total = Integer.valueOf(tokens[1]);
			}
			while(inp1.ready()){
				linie = inp1.readLine();
				String [] tokens = linie.split(" ");
				map.put(tokens[0], Integer.valueOf(tokens[1]));
				linie="";
				for(int i=2;i<tokens.length;i++){
					linie+=tokens[i];
					if(i+1<tokens.length)
						linie+=" ";
				}
				mapName.put(tokens[0], linie);
			}
		} catch (IOException ex) {
			Logger.getLogger(M3ResultsBinding.class.getName()).log(Level.SEVERE, null, ex);
		}
		try(BufferedReader inp2 = new BufferedReader(new InputStreamReader(new FileInputStream(fisier2), "UTF8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8"))){
			if(inp2.ready()){
				linie=inp2.readLine();
				String []tokens = linie.split(" ");
				total += Integer.valueOf(tokens[1]);
				out.append(tokens[0] + " "+total+System.getProperty("line.separator"));
				out.flush();
			}
			while(inp2.ready()){
				linie = inp2.readLine();
				String [] tokens = linie.split(" ");
				if(map.containsKey(tokens[0]))
					map.put(tokens[0], map.get(tokens[0]) + Integer.valueOf(tokens[1]));
				else{
					map.put(tokens[0], Integer.valueOf(tokens[1]));
					linie="";
					for(int i=2;i<tokens.length;i++){
						linie+=tokens[i];
						if(i+1<tokens.length)
							linie+=" ";
					}
					mapName.put(tokens[0], linie);
				}
			}
			map = sortByComparator(map, false);
			for (Map.Entry<String, Integer> entry : map.entrySet()){
				out.append(entry.getKey()+" "+entry.getValue()+" "+ mapName.get(entry.getKey())+System.getProperty("line.separator"));
			}
			out.flush();
		} catch (IOException ex) {
			Logger.getLogger(M3ResultsBinding.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void copy(File fisier) {
		String folder = fisier.getParent().substring(fisier.getParent().lastIndexOf(sep));
		File output = new File(outputDir.getAbsolutePath()+sep+folder+sep+fisier.getName().substring(0,fisier.getName().length()-8));
		try(BufferedReader inp = new BufferedReader(new InputStreamReader(new FileInputStream(fisier), "UTF8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8"))){
			while(inp.ready()){
				out.append(inp.readLine());
				out.append(System.getProperty("line.separator"));
			}
			out.flush();
		} catch (IOException ex) {
			Logger.getLogger(M3ResultsBinding.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order){
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
			@Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
                if (order)
                    return o1.getValue().compareTo(o2.getValue());
                else
                    return o2.getValue().compareTo(o1.getValue());
            }
        });
		Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
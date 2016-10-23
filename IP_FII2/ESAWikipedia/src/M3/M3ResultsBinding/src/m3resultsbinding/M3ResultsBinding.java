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

/**
 *
 * @author Ovidiu
 */
public class M3ResultsBinding {
	public static final File outputDir=new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"output");
	public static void main(String[] args) {
        final File inputDir1=new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"input1");
		final File inputDir2=new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"input2");
		File currentOutputDir;
		if(inputDir1.exists() && inputDir1.isDirectory() && inputDir2.exists() && inputDir2.isDirectory()){
			Path dirs1 = Paths.get(System.getProperty("user.dir")+System.getProperty("file.separator")+"input1");
			Path dirs2 = Paths.get(System.getProperty("user.dir")+System.getProperty("file.separator")+"input2");
			try(DirectoryStream<Path> dirStream1 = Files.newDirectoryStream(dirs1);
				DirectoryStream<Path> dirStream2 = Files.newDirectoryStream(dirs2)){
				Iterator<Path> it = dirStream2.iterator();
				for(Path dir1:dirStream1){//for each directory
//					System.out.println("Director curent: input1"+System.getProperty("file.separator")+dir1.getFileName());
					System.out.println("Director curent: "+dir1.getFileName());
					currentOutputDir = new File(outputDir.getAbsolutePath()+System.getProperty("file.separator")+dir1.getFileName());
					if(!currentOutputDir.exists())
						currentOutputDir.mkdirs();
					it.hasNext();
					Path dir2 = it.next();
//					System.out.println("Director curent: input2"+System.getProperty("file.separator")+dir2.getFileName());
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
			System.out.println("fisierele \'input1\' si \'input2\' nu exista sau nu sunt directoare.");
		}
    }

	private static void combineResults(Path p1, Path p2) {
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
	}

	private static void combine(File fisier1, File fisier2) {
		String sep = System.getProperty("file.separator");
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
			///
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
			///
			out.flush();
		} catch (IOException ex) {
			Logger.getLogger(M3ResultsBinding.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void copy(File fisier) {
		String sep = System.getProperty("file.separator");
		String folder = fisier.getParent().substring(fisier.getParent().lastIndexOf(sep));
		File output = new File(outputDir.getAbsolutePath()+sep+folder+sep+fisier.getName().substring(0,fisier.getName().length()-8));
		try(BufferedReader inp = new BufferedReader(new InputStreamReader(new FileInputStream(fisier), "UTF8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8"))){
			while(inp.ready()){
				out.append(inp.readLine());
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


// de rulat si de urmarit pas cu pas de la inceput!
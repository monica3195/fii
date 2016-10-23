package m3automaticlemmatization;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class M3AutomaticLemmatization {

    public static void main(String[] args) {
        final File inputDir=new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"inputuri");
		final File outputDir=new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"outputuri");
		File currentOutputDir;
		ArrayList<Process> proceseNeterminate = new ArrayList<>();
		ArrayList<String> proceseFolder = new ArrayList<>();
		Process p;
		String[] envp = {"classpath=data/POStagger.jar;data/maxent-3.0.0.jar;data/OpenNLP.jar;data/GGSEngine2.jar"};
		String command = "java -Xms1200m -Dfile.encoding=utf-8 uaic.postagger.tagger.HybridPOStagger data/posRoDiacr.model data/posDictRoDiacr.txt data/guesserTagset.txt data/posreduction.ggf ";
		if(inputDir.exists() && inputDir.isDirectory()){
			Path dirs = Paths.get(System.getProperty("user.dir")+System.getProperty("file.separator")+"inputuri");
			try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dirs)){
				for(Path dir:dirStream){//for each directory
					System.out.println("Director curent: inputuri"+System.getProperty("file.separator")+dir.getFileName());
					Runtime r = Runtime.getRuntime();
					try {
						currentOutputDir = new File(outputDir.getAbsolutePath()+System.getProperty("file.separator")+dir.getFileName());
						if(!currentOutputDir.exists())
							currentOutputDir.mkdir();
						p = r.exec(command+"inputuri"+System.getProperty("file.separator")+
							dir.getFileName()+" outputuri"+System.getProperty("file.separator")+
							dir.getFileName(),envp);
						BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
						BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
						p.waitFor(40,TimeUnit.SECONDS);
						while(reader.ready())
							reader.readLine();
						while(error.ready())
							error.readLine();
						if(p.isAlive()){
							proceseNeterminate.add(p);
							proceseFolder.add(dir.getFileName().toString());
						}
					} 
					catch (InterruptedException ex) {
						System.out.println("InterruptedException.");
					}
				}
			} catch (IOException ex) {
				Logger.getLogger(M3AutomaticLemmatization.class.getName()).log(Level.SEVERE, null, ex);
			}
			System.out.println("Foldere neprocesate complet:(din motive obscure) ");
			for(int i=0;i<proceseNeterminate.size();i++){
				if(proceseNeterminate.get(i).isAlive()){
					try {
						BufferedReader reader = new BufferedReader(new InputStreamReader(proceseNeterminate.get(i).getInputStream()));
						BufferedReader error = new BufferedReader(new InputStreamReader(proceseNeterminate.get(i).getErrorStream()));
						while(reader.ready())
							reader.readLine();
						while(error.ready())
							error.readLine();
						proceseNeterminate.get(i).destroyForcibly();
						System.out.println(proceseFolder.get(i));
					} catch (IOException ex) {
						proceseNeterminate.get(i).destroyForcibly();
						Logger.getLogger(M3AutomaticLemmatization.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		}
		else{
			System.out.println("fisierul \'inputuri\' nu exista sau nu este director.");
		}
    }
}

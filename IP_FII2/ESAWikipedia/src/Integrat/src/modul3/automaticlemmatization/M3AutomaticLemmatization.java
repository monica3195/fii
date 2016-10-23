package modul3.automaticlemmatization;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

final class Cronometru{
    private long begin, end;
 
    public void start(){
       end = begin = System.currentTimeMillis();
    }
    public double getSeconds() {
		end=System.currentTimeMillis();
        return (end - begin) / 1000.0;
    }
}
	
public class M3AutomaticLemmatization{
	public String sep = System.getProperty("file.separator");
	JProgressBar bar;
	public M3AutomaticLemmatization(JProgressBar bar){
		this.bar=bar;
	}
	
    public void run() {
        File inputDir=new File(System.getProperty("user.dir")+sep+"DumpFisier");
		File outputDir=new File(System.getProperty("user.dir")+sep+"automaticLemmatizationOutput");
		File currentOutputDir;
		ArrayList<String> proceseFolder = new ArrayList<>();
		String[] envp = {"classpath=data/POStagger.jar;data/maxent-3.0.0.jar;data/OpenNLP.jar;data/GGSEngine2.jar"};
		String command = "java -Xms1200m -Dfile.encoding=utf-8 uaic.postagger.tagger.HybridPOStagger data/posRoDiacr.model data/posDictRoDiacr.txt data/guesserTagset.txt data/posreduction.ggf ";
		int c=0;
		Cronometru ceas = new Cronometru();
		bar.setMinimum(0);
		bar.setMaximum(inputDir.listFiles().length);
		if(inputDir.exists() && inputDir.isDirectory()){
			Path dirs = Paths.get(System.getProperty("user.dir")+sep+"DumpFisier");
			try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dirs)){
				for(Path dir:dirStream){
					bar.setValue(c++);
					System.out.println("Director curent: "+dir.getFileName());
					Runtime r = Runtime.getRuntime();
					currentOutputDir = new File(outputDir.getAbsolutePath()+sep+dir.getFileName());
					if(!currentOutputDir.exists())
						currentOutputDir.mkdirs();
					Process p = r.exec(command+"DumpFisier"+sep+
							dir.getFileName()+" automaticLemmatizationOutput"+sep+
							dir.getFileName(),envp);
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					String err;
					ceas.start();
					while(p.isAlive()){
						while(p.getErrorStream().available()>0 ){
							err=error.readLine();
							if(err.startsWith("Exception in"))
								proceseFolder.add(dir.getFileName().toString());
						}
						while(p.getInputStream().available()>0)
							reader.readLine();
						if(ceas.getSeconds()>100)
							break;
					}
					if(p.isAlive()){
						p.destroyForcibly();
						proceseFolder.add(dir.getFileName().toString());
					}
				}
			} catch (IOException ex) {
				Logger.getLogger(M3AutomaticLemmatization.class.getName()).log(Level.SEVERE, null, ex);
			}
			System.out.println("Foldere neprocesate complet:(motive) \n - Contine cuvinte lungi(linkuri)\n - Exceptie in programul de lematizare\n---------------");
			for (String proceseFolder1 : proceseFolder) {
				System.out.println(proceseFolder1);
			}
			System.out.println("---------------");
		}
		else{
			System.out.println("Folderul \'DumpFisier\' nu exista!");
		}
    }
}

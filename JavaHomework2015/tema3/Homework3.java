package tema3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Homework3 {

	public static void main(String[] args) {

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		try {
			for (String line = br.readLine(); line != null; line = br.readLine())
				playMusic(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void playMusic(String line) {

		String[] help = line.split("\\s+");

		
		try {
			
		if(help.length < 2){
			if(help[0].equals("l")){
				(new ListFiles()).executeCommand(DirectorCurent.dirCurent());
			}
			else if (help[0].equals("q")) {
				(new Quit()).executeCommand(null);
			} else{
			System.err.println("Nu ati mentionat nici un argument.\nIntroduceti o comanda valida.");
			return;}
		}
		else
			if (help[0].equals("help"))
				(new Help()).executeCommand(help[1]);
			else if ((help[0].equals("dc"))) {
				(new DirectorCurent()).executeCommand(help[1]);
				// System.out.println(Pattern.compile("([A-Z]:)(\\\\[a-zA-Z0-9]+)+\\\\?").matcher("C:\\Users").matches());
				//System.out.println((new DirectorCurent()).dirCurent());
			} else if ((help[0].equals("m"))) {
				(new Modify()).executeCommand(help[1] + " " + help[2]);
			} else if ((help[0].equals("l"))) {
				(new ListFiles()).executeCommand(help[1]);
			} else if ((help[0].equals("s"))) {
				(new Search()).executeCommand(help[1]);
			} else if ((help[0].equals("p"))) {
				(new Play()).executeCommand(help[1]);
			}
		} catch (InvalidArgExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
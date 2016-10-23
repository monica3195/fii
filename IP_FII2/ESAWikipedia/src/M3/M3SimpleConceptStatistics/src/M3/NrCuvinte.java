/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 *
 * @author Ovidiu
 */
public class NrCuvinte implements Statistica {
    int nrCuvinte;
	public NrCuvinte(){
		nrCuvinte=0;
	}
    @Override
    public void analizeaza(File f) {
        try(BufferedReader fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"))){
            while(fileIn.ready()){
                String line=fileIn.readLine();
                String[] tokens=line.split(" ");
				for(String token:tokens){
					token=token.toLowerCase(new Locale("ro"));
					if(PROIECTIP.index.containsKey(token)){
						nrCuvinte++;
					}
				}
            }
        }catch(FileNotFoundException e){
			System.out.println("Fisierul nu a fost gasit");
        } catch (UnsupportedEncodingException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
    }
	@Override
    public String getResult(){
        return Integer.toString(nrCuvinte);
    }
}

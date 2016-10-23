/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3simpleconceptstatistics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ovidiu
 */
public class PageStatistics {

	File pagina;
    List<Statistica> statistici;

	public PageStatistics(){
		statistici = new ArrayList<>();
	}
    public void atachStatistic(Statistica s) {
        statistici.add(s);
    }
    public void removeStatistic(Statistica s) {
        statistici.remove(s);
    }
	/**
	*
	* Valorile acceptate sunt siruri care reprezinta numele clasei.
	* Spre exemplu, un apel valid poate fi : getResult("NrCuvinte");
	 * @param s Un parametru egal cu numele clasei derivate din Statistica
	 * @return Un string cu rezultatul procesarii 
	*/
	public String getResult(String s){
		for(Statistica statistica:statistici){
			if(statistica.toString().contains(s))
				return statistica.getResult();
		}
		return "Eroare";
	}
	public String getResultRest(String s){
		for(Statistica statistica:statistici){
		    if(statistica.toString().contains(s))
				return statistica.getResultRest();
		}
		return "Eroare";
	}
    public void runAnalyser(File f){
        for(Statistica s:statistici){
			s.analizeaza(f);
		}
    }
}

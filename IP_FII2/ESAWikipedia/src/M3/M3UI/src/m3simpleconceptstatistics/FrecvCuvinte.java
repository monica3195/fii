package m3simpleconceptstatistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Ovidiu
 */
public class FrecvCuvinte implements Statistica {

    Map<String, Integer> map;
    Map<String, Integer> maprest;
	
	public FrecvCuvinte(){
		map = new HashMap<>();
        maprest = new HashMap<>();
	}
	
    @Override
    public void analizeaza(File f) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"))){
            while (in.ready()) {
                String line = in.readLine();
                String[] tokens = line.split(" ");
				for (String token : tokens) {
					token=token.toLowerCase(new Locale("ro"));
					if(SimpleConceptStatistics.index.containsKey(token)){
						if(!map.containsKey(token))
							map.put(token, 1);
						else
							map.put(token,map.get(token)+1);
                    }
                    else{
                        if(!maprest.containsKey(token))
                            maprest.put(token, 1);
                        else
                            maprest.put(token,maprest.get(token)+1);
                    }
				}
            }
        } catch (IOException e){
			System.out.println(e.getMessage());
		}
    }
	
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order){
        List<Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Integer>>(){
			@Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2){
                if (order)
                    return o1.getValue().compareTo(o2.getValue());
                else
                    return o2.getValue().compareTo(o1.getValue());
            }
        });
		Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Entry<String, Integer> entry : list){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
	
	@Override
    public String getResult() {
		String rezultat="";
        Map<String,Integer> sortat = sortByComparator(map,false);
        for (Entry<String, Integer> entry : sortat.entrySet()){
			rezultat=rezultat+SimpleConceptStatistics.index.get(entry.getKey())+" "+entry.getValue()+" "+ entry.getKey()+System.getProperty("line.separator");
        }
		return rezultat;
    }
    @Override
    public String getResultRest() {
        String rezultat="";
        for (Entry<String, Integer> entry : maprest.entrySet())
            rezultat=rezultat+entry.getValue()+" "+ entry.getKey()+System.getProperty("line.separator");
    return rezultat;
    }
}

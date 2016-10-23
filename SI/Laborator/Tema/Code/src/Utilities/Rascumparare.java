package Utilities;

import java.io.Serializable;
import java.util.List;

public class Rascumparare implements Serializable{
	public CommitU commitU;
	public List<Payword> ci;
	public List<Integer> i;

	public String toString(){
		StringBuilder q = new StringBuilder();
		q.append(commitU);

		for(int x = 0; x< ci.size(); x++){
			q.append('\n');
			q.append(ci.get(x).toString() + " - " + i.get(x));
		}

		return q.toString();
	}
}

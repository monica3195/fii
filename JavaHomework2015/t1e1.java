import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class t1e1 {

//	public static void main(String[] args) {
//		
//		Integer[] intnums = parse();
//		
//		long ct = System.currentTimeMillis();
//		ArrayList<Integer> A = new ArrayList<Integer>();
//		ArrayList<Integer> B = new ArrayList<Integer>();
//		
//		A.add(intnums[0]);
//		
//		for (int i = 1; i < intnums.length; i++) {
//			if(sum(A) < sum(B))
//				A.add(intnums[i]);
//			else
//				B.add(intnums[i]);
//		}
//		
//		long ct2 = System.currentTimeMillis();
//		
//		if(sum(A) == sum(B))
//			System.out.println("Found match " + (ct2-ct) );
//		else
//			System.out.println("Didn't find any matching sum set " + (ct2-ct));
//	}

	@SuppressWarnings("unused")
	private static Integer[] parse() {
		String[] snums = read();
		
		Integer[] intnums = null;
		
		if (snums.length > 1){
			intnums = new Integer[snums.length];
		
		for (int i = 0; i < intnums.length; i++) {
			try {
				intnums[i] = Integer.parseInt(snums[i]);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		else if(snums.length ==1){
			intnums = new Integer[Integer.parseInt(snums[0])];
			for (int i = 0; i < intnums.length; i++) {
				intnums[i] = (int)( Math.random()*101); 
				System.out.print(intnums[i] + " ");
			}
			System.out.println();
		}
		
		
		Arrays.sort(intnums, Collections.reverseOrder());
		return intnums;
	}

	private static String[] read() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		String[] snums = null;
		try {
			String nrs = br.readLine();
			snums = nrs.split("\\D+");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return snums;
	}
	
	static Integer sum(ArrayList<Integer> list) {
		Integer sum = 0;
		for (Integer integer : list) {
			sum+=integer;
		}
		return sum;
	}
}

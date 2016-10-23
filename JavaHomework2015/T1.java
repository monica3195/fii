import java.util.stream.IntStream;

public class T1 {

//	public static void main(String[] args) {
//
//		String[] args_s = null;
//
//		try {
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			String arg_s = br.readLine();
//			args_s = arg_s.split("\\s+");
//		} catch (IOException e) {
//			System.err.print(e.getMessage());
//			System.exit(-1);
//		}
//
//		int[] nrArray = ParseArray(args_s);
//
//		if (nrArray.length == 1)
//			CreateNrArray(nrArray[0]);
//		else
//			FindSolution(nrArray);
//
//	}

	private static void FindSolution(int[] nrArray) {

		//nrArray= SortDesc(nrArray);
		
		int[] A = new int[nrArray.length / 2 + 1], B = new int[nrArray.length / 2 + 1];
		int bCount=0, aCount =0;
		for (int i : nrArray) {
			if(IntStream.of(A).sum() <= IntStream.of(B).sum())
			{
				System.out.println("_-"+i);
				A[aCount] = i;
				aCount++;
			}
			else {
				System.out.println("_+"+i);
				B[bCount] = i;
				bCount++;
			}
		}

		if(IntStream.of(A).sum() == IntStream.of(B).sum())
			System.out.println("Found solution");
		else 
			System.out.println("Didn't find solution");
	}

	@SuppressWarnings("unused")
	private static int[] SortDesc(int[] nrArray) {
		
		return null;
		
	}

	@SuppressWarnings("unused")
	private static void CreateNrArray(int arrayLen) {

		int[] tmpArray = new int[arrayLen];

		for (int i = 0; i < arrayLen; i++) {
			tmpArray[i] = (int) Math.random();
		}

		FindSolution(tmpArray);
	}

	@SuppressWarnings("unused")
	private static int[] ParseArray(String[] args_s) {

		int[] tmpNrArray = new int[args_s.length];
		int i = 0;
		for (String string : args_s) {
			
			try {
				tmpNrArray[i] = Integer.parseInt(string);
			} catch (NumberFormatException e) {
				System.err.print(e.getMessage());
				System.exit(-1);
			}
			i++;
		}
		return tmpNrArray;

	}

}


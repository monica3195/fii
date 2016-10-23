package esaWkipedia;

import java.util.HashMap;

public class OnlyRedirects {
	
	private static HashMap<Integer, Integer> redirects = new HashMap<Integer, Integer>();

	public static void add(int key, int value)
	{
		redirects.put(key, value); 
	}
	public static HashMap<Integer, Integer> getRedirects()
	{
		return redirects;
	}
}
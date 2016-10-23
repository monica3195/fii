package modul4;

import java.util.HashMap;

public class OnlyRedirects {

	private static HashMap<Integer, Integer> redirects = new HashMap<Integer, Integer>();
	private static OnlyRedirects instance = new OnlyRedirects();

	private OnlyRedirects() {

	}

	public static OnlyRedirects getInstance() {
		return instance;
	}

	public void add(int key, int value) {
		redirects.put(key, value);
		System.out.println(key + "->" + value);

	}

	public static HashMap<Integer, Integer> getRedirects() {
		return redirects;
	}
}

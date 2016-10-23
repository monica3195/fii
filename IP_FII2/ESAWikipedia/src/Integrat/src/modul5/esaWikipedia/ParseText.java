package modul5.esaWikipedia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Madalina Pastrav
 *
 */
public class ParseText {

	private String line;
	private LinkedHashMap<String, Double> hashFrecventa;
	private LinkedHashMap<String, String> hashConcepte;
	public static HashMap<Integer, Integer> hashRedirect;
	private String root;
	private String numarConcepte; // numarConcepte=0. afisez mesaj.
	private String[] elements;

	/**
	 * @param numeConcept
	 *            Conceptul dorit
	 */

	public ParseText() {

	}

	public ParseText(String numeConcept) {
		setHashConcepte(new LinkedHashMap<>());
		setHashFrecventa(new LinkedHashMap<>());
		String auxConcept = null;

		setHashConcepte(parseTextForName("rodumpindex.txt"));
		System.out.println("name" + numeConcept);
		String idConcept = returnIdConcept(numeConcept);
		String idRedirectFile = returnRedirectId(idConcept);
		System.out.println("aici1");
		System.out.println(idRedirectFile);
		System.out.println("aici1");
		if (idRedirectFile == null) {
			auxConcept = idConcept;
		} else
			auxConcept = idRedirectFile;

		File file = getFile(returnFolder(auxConcept), auxConcept);
		System.out.println("aici");
		System.out.println(auxConcept);
		System.out.println("aici");// asta e 5119?
		try {
			String strLine;

			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				// aici citesc prima linie din fisier
				strLine = br.readLine();
				String[] segments = strLine.split(" ");
				root = segments[0];
				numarConcepte = segments[1];
				// System.out.println(root +
				// "\n Numar Concepte:"+numarConcepte);

				while ((strLine = br.readLine()) != null) {

					// String[] lineVariables = strLine.split(" ");
					for (int i = 0; i < strLine.length(); i++) {
						String[] lineVariables = strLine.split(" ");
						hashFrecventa.put(lineVariables[0],
								Double.valueOf(lineVariables[1]));
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File is missing");
		}

	}

	public LinkedHashMap<String, String> getHashConcepte() {
		return hashConcepte;
	}

	public void setHashConcepte(LinkedHashMap<String, String> hashConcepte) {
		this.hashConcepte = hashConcepte;
	}

	public String getNumarConcepte() {
		return numarConcepte;
	}

	public void setNumarConcepte(String numarConcepte) {
		this.numarConcepte = numarConcepte;
	}

	public boolean checkIfFileExists(String numeConcept) {
		boolean found = true;
		setHashConcepte(new LinkedHashMap<>());
		setHashFrecventa(new LinkedHashMap<>());

		setHashConcepte(parseTextForName("rodumpindex.txt"));
		System.out.println("name" + numeConcept);
		File file = getFile(returnFolder(returnIdConcept(numeConcept)),
				returnIdConcept(numeConcept));
		try {
			String strLine;

			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				// aici citesc prima linie din fisier
				strLine = br.readLine();
				String[] segments = strLine.split(" ");
				root = segments[0];
				numarConcepte = segments[1];
				// System.out.println(root +
				// "\n Numar Concepte:"+numarConcepte);

				while ((strLine = br.readLine()) != null) {

					// String[] lineVariables = strLine.split(" ");
					for (int i = 0; i < strLine.length(); i++) {
						String[] lineVariables = strLine.split(" ");
						hashFrecventa.put(lineVariables[0],
								Double.valueOf(lineVariables[1]));
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			found = false;
		}

		return found;
	}

	/**
	 * @param numele
	 *            conceptului
	 * @return id-ul pentru conceptul dat ca parametru
	 */
	@SuppressWarnings("rawtypes")
	public String returnIdConcept(String nameConcept) {
		String id = "";
		String[] segments = null;
		String concept = "";
		String[] elements = null;
		String idConcept = "";
		Iterator<Entry<String, String>> it = hashConcepte.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (HashMap.Entry) it.next();
			segments = ((String) pair.getValue()).split(":");
			concept = segments[0];

			if (nameConcept.equalsIgnoreCase(concept)) {
				elements = ((String) pair.getKey()).split(":");
				idConcept = elements[1];
				id = idConcept;
				break;
			}
		}
		System.out.println("id---" + id);
		return id;

	}

	/**
	 * @param id
	 *            -ul unui concept
	 * @return folderul din care face parte conceptul cu id-ul dat ca parametru
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public String returnFolder(String idConcept) {
		String folder = "";
		String[] segments = null;
		String id = "";
		setElements(null);

		Iterator<Entry<String, String>> it = hashConcepte.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (HashMap.Entry) it.next();
			segments = ((String) pair.getKey()).split(":");
			id = segments[1];

			if (idConcept.equals(id)) {

				folder = segments[0];

				break;
			}
		}

		return folder;
	}

	/**
	 * @param filename
	 *            : index-maping.txt cu numele conceptelor
	 * @return hashMap pentru fisierul dat sub forma: key=folder:id ,
	 *         value=name1:name2...
	 */
	public LinkedHashMap<String, String> parseTextForName(String filename) {

		File file = new File(filename);
		String strLine;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));

			while ((strLine = reader.readLine()) != null) {

				String[] lineVariables = strLine.split(":");
				String valTemp = " ";
				if (lineVariables.length > 3) {
					// System.out.println("intra pe aici1");
					for (int j = 2; j < lineVariables.length; j++) {
						valTemp += lineVariables[j] + ":";
					}

				} else // aici pot sa fac test
				if (lineVariables.length == 3) {
					valTemp = lineVariables[2];
					// System.out.println("intra pe aici2 "+lineVariables[2]);
				} else

				{
					System.out.println("Pentru conceptul dat nu avem nume!");
					// valTemp = " ";
				}
				hashConcepte.put(lineVariables[0] + ":" + lineVariables[1],
						valTemp);

			}
			// }
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hashConcepte;

	}

	/**
	 * @param id
	 *            -ul radacinii(conceptului principal)
	 * @return numele corespunzator id-ului
	 */
	@SuppressWarnings("rawtypes")
	public String printRootName(String key) {
		String result = null;
		Iterator<Entry<String, String>> it2 = hashConcepte.entrySet()
				.iterator();
		while (it2.hasNext()) {
			Map.Entry pair2 = (HashMap.Entry) it2.next();
			String key2 = (String) pair2.getKey();
			if (key.equals(key2)) {
				result = (String) pair2.getValue();
				break;
			}
		}
		return result;

	}

	/**
	 * @param hashMap
	 *            : hashMap-ul cu frecventele si id-urile conceptelor
	 * @param numarIntrari
	 *            : cate concepte sa se afiseze
	 */
	@SuppressWarnings("rawtypes")
	public void print(Map<String, Double> hashMap, int numarIntrari) {
		Iterator<Entry<String, Double>> it = hashMap.entrySet().iterator();
		int count = 0;
		Integer nrConcepte = Integer.valueOf(getNumarConcepte());
		if (numarIntrari > nrConcepte) {
			while (it.hasNext()) {
				Map.Entry pair = (HashMap.Entry) it.next();
				System.out.println(pair.getKey() + " = " + pair.getValue());

			}

		} else if (numarIntrari <= nrConcepte) {
			while (it.hasNext() && count < numarIntrari) {
				Map.Entry pair = (HashMap.Entry) it.next();
				System.out.println(pair.getKey() + " = " + pair.getValue());
				count++;

			}
		}
	}

	/**
	 * @return hashMap-ul sortat descrescator dupa frecvente
	 */
	public LinkedHashMap<String, Double> sort() {
		Set<Entry<String, Double>> entries = hashFrecventa.entrySet();
		Comparator<Entry<String, Double>> valueComparator = new Comparator<Entry<String, Double>>() {

			@Override
			public int compare(Entry<String, Double> e1,
					Entry<String, Double> e2) {
				Double v1 = e1.getValue();
				Double v2 = e2.getValue();
				return v2.compareTo(v1);
			}
		};

		// Sort method needs a List, so let's first convert Set to List in Java
		List<Entry<String, Double>> listOfEntries = new ArrayList<Entry<String, Double>>(
				entries);

		// sorting HashMap by values using comparator
		Collections.sort(listOfEntries, valueComparator);

		LinkedHashMap<String, Double> sortedByValue = new LinkedHashMap<String, Double>(
				listOfEntries.size());

		// copying entries from List to Map
		for (Entry<String, Double> entry : listOfEntries) {
			sortedByValue.put(entry.getKey(), entry.getValue());
		}
		hashFrecventa = sortedByValue;
		return hashFrecventa;
	}

	/**
	 * @param hashMap
	 *            -ul cu numele conceptelor
	 * @param numarIntrari
	 */
	@SuppressWarnings("rawtypes")
	public void printConceptName(Map<String, String> hashMap, int numarIntrari) {

		Iterator<Entry<String, String>> it = hashMap.entrySet().iterator();
		int count = 0;
		Integer nrConcepte = Integer.valueOf(getNumarConcepte());
		if (numarIntrari > nrConcepte) {
			while (it.hasNext()) {
				Map.Entry pair = (HashMap.Entry) it.next();
				System.out.println(pair.getKey() + " = " + pair.getValue());

			}

		} else if (numarIntrari <= nrConcepte) {
			while (it.hasNext() && count < numarIntrari) {
				Map.Entry pair = (HashMap.Entry) it.next();
				System.out.println(pair.getKey() + " = " + pair.getValue());
				count++;

			}
		}

	}

	/**
	 * @param key
	 *            din hashmap-ul cu frecvente
	 * @return numele conceptului corespunzator cheii date ca parametru
	 */
	@SuppressWarnings("rawtypes")
	public String compare(String key) {
		String result = null;
		// Iterator<Entry<String, Double>> it =
		// hashFrecventa.entrySet().iterator();
		Iterator<Entry<String, String>> it2 = hashConcepte.entrySet()
				.iterator();
		while (it2.hasNext()) {
			Map.Entry pair2 = (HashMap.Entry) it2.next();
			String key2 = (String) pair2.getKey();
			if (key.equals(key2)) {
				result = (String) pair2.getValue();
				break;
			}
		}

		return result;
	}

	/**
	 * @param folder
	 *            : folderul in care se gaseste conceptul ex:606
	 * @param id
	 *            : id-ul conceptului care trebuie cautat in folderul dat
	 * @return fisierul corespunzator id-ului gasit in folder
	 */
	public File getFile(String folder, String id) {

		System.out.println("getFile" + folder + id);
		File f = new File("output/"
				+ folder + "/" + id);

		return f;
	}

	@SuppressWarnings("static-access")
	public HashMap<Integer, Integer> redirect(File file) {

		return modul4.OnlyRedirects.getInstance().getRedirects();
	}

	@SuppressWarnings("rawtypes")
	public String returnRedirectId(String id) {
		String ok = null;
		File nameRedirectFile = new File("hashmap.txt");
		hashRedirect = redirect(nameRedirectFile);

		Iterator<Entry<Integer, Integer>> it1 = redirect(nameRedirectFile)
				.entrySet().iterator();
		System.out.println();
		while (it1.hasNext()) {

			Map.Entry pair = (HashMap.Entry) it1.next();
			String aux = String.valueOf(pair.getKey());
			if (aux.equals(id)) {

				ok = String.valueOf(pair.getValue());
				break;
			}

		}

		return ok;
	}

	public static void main(String[] args) {

		forMain();

	}

	@SuppressWarnings("rawtypes")
	public static void forMain() {

		System.out.println("Introduce numele fisierului: ");
		Scanner in = new Scanner(System.in);
		String nameConcept = in.next();
		System.out.println();
		System.out.println("Please Wait!!");
		ParseText p = new ParseText(nameConcept);
		String nameFile = "index-maping.txt";

		/*
		 * //System.out.println("root  " + p.getRoot())
		 * System.out.println("root:");
		 * System.out.println(p.returnIdConcept(nameConcept)); // ala de unde il
		 * ia? sau unde il seteaza asa
		 */p.print(p.sort(), 10);
		p.sort();

		/*
		 * System.out.println("afiseaza");
		 * System.out.println(p.returnRedirectId(
		 * (p.returnIdConcept(nameConcept))));
		 * 
		 * System.out.println("afiseaza");
		 */
		// p.printConceptName(p.parseTextForName(nameFile),10);
		p.setHashConcepte(p.parseTextForName(nameFile));
		int i = 0;
		System.out.println("\tPentru [" + p.printRootName(p.getRoot())
				+ "] sunt urmatoarele concepte:");
		Iterator<Entry<String, Double>> it = p.getHashFrecventa().entrySet()
				.iterator();
		System.out.println();
		while (it.hasNext() && i < 10) {

			Map.Entry pair = (HashMap.Entry) it.next();
			String aux = p.compare((String) pair.getKey());
			if (aux != null) {
				System.out.println(aux);
				i++;
			}
		}

		in.close();

	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public LinkedHashMap<String, Double> getHashFrecventa() {
		return hashFrecventa;
	}

	public void setHashFrecventa(LinkedHashMap<String, Double> hashFrecventa) {
		this.hashFrecventa = hashFrecventa;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String[] getElements() {
		return elements;
	}

	public void setElements(String[] elements) {
		this.elements = elements;
	}
}

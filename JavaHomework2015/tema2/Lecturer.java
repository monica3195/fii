package tema2;

import java.util.ArrayList;

class Lecturer{
	
	private ArrayList<Student> preferedStud;
	private ArrayList<Project> oferdProj;
	private int capacity;
	private int current;
	
	public Lecturer(int capacity, ArrayList<Student> prefStuds, ArrayList<Project> offerProj) {
		this.capacity = capacity;
		this.current = 0;
		this.preferedStud = prefStuds;
		this.oferdProj = offerProj;
	}
	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("ofera proiectele: ");
		for (Project project : oferdProj) {
			sb.append(project.toString() + " ");
		}
		sb.append("\n");
		sb.append("si prefera studentii: ");
		for (Student student : preferedStud) {
			sb.append((String.valueOf(preferedStud.indexOf(student)+1)) + " ");
		}
		sb.append("\n\n");
		return sb.toString();
	}
	
	public boolean isOversub() {
		
		if(current > capacity)
			return true;
		else
			return false;
	}

	public ArrayList<Student> prefStudsByProj(Project firstProj) {

		ArrayList<Student> prefStud = new ArrayList<Student>();
		
		for (Student student : preferedStud) {
			if(student.isPref(firstProj))
				prefStud.add(student);
		}
		return prefStud;
	}

	public void removeStudent(Student worstStudent) {
			
		preferedStud.remove(worstStudent);
	}

	public boolean hasProj(Project firstProj) {

		if (oferdProj.contains(firstProj)) 
			return true;
		else
			return false;
	}

	public boolean isFull() {

		if(current == capacity)
			return true;
		else
			return false;
	}

	public Student getWorstStud() {

		return preferedStud.get(preferedStud.size()-1);
	}
	
	private ArrayList<Student> studentsPrefProj(Project prefProj) {
		ArrayList<Student> projStudPref = new ArrayList<Student>();
		
		for (Student student : preferedStud) {
			if(student.isPref(prefProj))
				projStudPref.add(student);
		}
		return projStudPref;
	}
	
	public Student getWorstStudForProj(Project prefProj) {
		
		ArrayList<Student> studPrefProf = studentsPrefProj(prefProj);
		
		return studPrefProf.get(studPrefProf.size()-1);
	}
	
	public ArrayList<Student> getPrefStud() {
		return preferedStud;
	}
	
	public void addAsoc() {
		current++;
	}
	
	public void remAsoc() {
		current--;
	}
}
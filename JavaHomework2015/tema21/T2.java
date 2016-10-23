package tema21;

import java.util.ArrayList;

public class T2 {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}

class Asigment{
	
	ArrayList<Project> projects;
	ArrayList<Student> students;
	ArrayList<Lecturer> lecturers;
	ArrayList<Tuple> M;
	
	public enum Types{
		STUDENT, LECTURER, PROJECT
	}
	
	public void add(Object obj, Types type) {
		
		switch (type) {
		case STUDENT:
			students.add((Student)obj);
			break;
		case LECTURER:
			lecturers.add((Lecturer)obj);
			break;
		case PROJECT:
			projects.add((Project)obj);
			break;
		default:
			break;
		}
	}

	public void Asign() {
		
		boolean asigned = true;
		
		for (Tuple tuple : M) {
			if(tuple.getSecond() == null && !tuple.getFirst().getProjPref().isEmpty())
			{
				asigned = false;
				
				Project firstP = tuple.getFirst().getProjPref().get(0);
				Lecturer lecP = getLectOfferingP(firstP);
				
				boolean oversubProj= !tuple.setSecond(firstP);
				boolean oversubLect= !tuple.setThird(lecP);
				
				if(oversubProj){
					Student worst = projWorstStud(tuple.getSecond()) ;
					breakAsig(worst);
				}
				else if(oversubLect){
					Student worst = lectWorstStud(tuple.getThird()) ;
					breakAsig(worst);
				}
				
			}
		}
		
		if(!asigned)
			Asign();
	}

	private Student lectWorstStud(Lecturer l) {
		return l.getStudentPref().get(l.getStudentPref().size());
	}

	private void breakAsig(Student worst) {
		for (Tuple tuple : M) {
			if(tuple.getFirst() == worst)
				tuple.setSecNull();
		}
	}
	
	private Student projWorstStud(Project p) {
		
		for (Lecturer l : lecturers) {
			if(l.getProjOffer().contains(p))
			{
				return l.projectPref(p).get(l.projectPref(p).size());
			}
		}
		return null;
	}
	
	private Lecturer getLectOfferingP(Project p){
		boolean found = false;
		
		while (!found) {
			for (Lecturer l : lecturers) {
				if(l.getProjOffer().contains(p)) return l;
			}
		}
		return null;
	}
}

class Tuple{
	
	private Student t;
	private Project u;
	private Lecturer l;
	public Tuple(Student t,Project u, Lecturer l) {
		this.t =t;
		this.u = u;
		this.l = l;
	}
	
	public Student getFirst() {
		return t;
	}
	
	public Project getSecond() {
		return u;
	}
	
	public boolean setSecond(Project u) {
		if(this.u == null){
			this.u = u;
			return (this.u).asign();
		}
		else
		{
			this.u.unasign();
			this.u = u;
			return (this.u).asign();
		}
	}
	
	public boolean setThird(Lecturer l) {
		
		if(this.l == null)
		{
			this.l = l;
			return (this.l).asign();
		}
		else
		{
			this.l.unasign();
			this.l = l;
			return (this.l).asign();
		}
	}
	
	public Lecturer getThird() {
		return l;
	}
	
	public void setSecNull() {
		this.u = null;
	}
}

class Student{
	
	ArrayList<Project> projectsPref;
	
	public Student(ArrayList<Project> projects) {
		this.projectsPref = projects;
	}
	
	public ArrayList<Project> getProjPref() {
		return projectsPref;
	}
		
	public boolean isPref(Project proj) {
		if(projectsPref.contains(proj)) 
			return true;
		else 
			return false;
	}
}

class Lecturer{
	
	private ArrayList<Student> studentsPref;
	private ArrayList<Project> projectsOffer;
	int capacity;
	int available;
	
	public Lecturer(ArrayList<Student> students, ArrayList<Project> projects, int capacity) {
		this.studentsPref= students;
		this.projectsOffer = projects;
		this.capacity = capacity;
	}
	
	public ArrayList<Student> projectPref(Project prefProj) {
		ArrayList<Student> projStudPref = new ArrayList<Student>();
		
		for (Student student : studentsPref) {
			if(student.isPref(prefProj))
				projStudPref.add(student);
		}
		return projStudPref;
	}

	public ArrayList<Student> getStudentPref() {
		return studentsPref;
	}
	
	public ArrayList<Project> getProjOffer() {
		return projectsOffer;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getAvailable() {
		return available;
	}
	
	public boolean asign() {
		
		if(available !=0){
			available--;	
			return true;
		}
		else 
			return false;
	}
	
	public void unasign() {
		available++;
	}
}

class Project{
	
	private int capacity;
	private int available;
	
	public Project(int capacity) {

		this.capacity = capacity;
		this.available = capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getAvailable() {
		return available;
	}
	
	public boolean asign() {
		
		if(available !=0){
			available--;	
			return true;
		}
		else 
			return false;
	}
	
	public void unasign() {
		available++;
	}
	
}
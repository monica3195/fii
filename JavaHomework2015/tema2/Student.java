package tema2;

import java.util.ArrayList;

class Student{

	private ArrayList<Project> preferedProj;
	private Project asigProj;
	
	public boolean hasProjs() {
		if(preferedProj.isEmpty())
			return false;
		else return true;
	}
	
	public Student(ArrayList<Project> prefProj) {
		this.preferedProj = prefProj;
	}
	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		if(asigProj != null)
			sb.append("are asignat proiestul: " + asigProj.toString()+ "\n");
		
		return sb.toString();
	}
	
	public void asigToProj(Project projToAsig, Lecturer lect) {
		
		if(projToAsig != null)
			asigProj = projToAsig;
		if(projToAsig != null)
			projToAsig.addAsoc();
		if(lect != null)
			lect.addAsoc();
	}
	
	public boolean isFree() {

		if(asigProj == null)
			return true;
		else 
			return false;
	}

	public void removeProj(Project firstProj) {
		preferedProj.remove(firstProj);
	}

	public void makeNull(Project proj, Lecturer lect) {

		asigProj = null;
		if(proj != null)
			proj.remAsoc();
		if(lect != null)
			lect.remAsoc();
	}

	public Project getAssignedProj() {

		return asigProj;
	}

	public Project getFirstProj() {
		
		return preferedProj.get(0);
	}

	public boolean listNotEmpty() {

		if(preferedProj.isEmpty())
			return false;
		else
			return true;
	}

	public boolean isPref(Project prefProj) {

		if(preferedProj.contains(prefProj))
			return true;
		else
			return false;
	}
	
	public ArrayList<Project> getPrefProj() {
		return preferedProj;
	}
}
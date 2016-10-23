package tema2;

import java.util.ArrayList;
import java.util.Arrays;

public class hw2 {
	
	private ArrayList<Student> students;
	private ArrayList<Project> projects;
	private ArrayList<Lecturer> lecturers;
	
	
	public static void main(String[] args) {
		
		Project p1 = new Project(2, 1);
		Project p2 = new Project(1, 2);
		Project p3 = new Project(1, 3);
		Project p4 = new Project(1, 4);
		Project p5 = new Project(1, 5);
		Project p6 = new Project(1, 6);
		Project p7 = new Project(1, 7);
		Project p8 = new Project(1, 8);
		@SuppressWarnings("unused")
		ArrayList<Project> projs = new ArrayList<>(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8));
		
		Student s1 = new Student(new ArrayList<Project>(Arrays.asList(p1,p7)));
		Student s2 = new Student(new ArrayList<Project>(Arrays.asList(p1,p2,p3,p4,p5,p6)));
		Student s3 = new Student(new ArrayList<Project>(Arrays.asList(p2,p1,p4)));
		Student s4 = new Student(new ArrayList<Project>(Arrays.asList(p2)));
		Student s5 = new Student(new ArrayList<Project>(Arrays.asList(p1,p2,p3,p4)));
		Student s6 = new Student(new ArrayList<Project>(Arrays.asList(p2,p3,p4,p5,p6)));
		Student s7 = new Student(new ArrayList<Project>(Arrays.asList(p5,p3,p8)));
		ArrayList<Student> studs = new ArrayList<Student>(Arrays.asList(s1,s2,s3,s4,s5,s6,s7));
		
		Lecturer l1 = new Lecturer(3, new ArrayList<Student>(Arrays.asList(s7,s4,s1,s3,s2,s5,s6)), new ArrayList<Project>(Arrays.asList(p1,p2,p3)));
		Lecturer l2 = new Lecturer(2, new ArrayList<Student>(Arrays.asList(s3,s2,s6,s7,s5)), new ArrayList<Project>(Arrays.asList(p4,p5,p6)));
		Lecturer l3 = new Lecturer(2, new ArrayList<Student>(Arrays.asList(s1,s7)), new ArrayList<Project>(Arrays.asList(p7,p8)));
		ArrayList<Project> projects = new ArrayList<>(Arrays.asList(p1,p5,p4,p2,null,null,p3));
		ArrayList<Lecturer> lects = new ArrayList<Lecturer>(Arrays.asList(l1,l2,l3));
		
		hw2 homework = new hw2(studs,lects);
		homework.setP(projects);
		
		System.out.println(homework.toString());
	}
	
	public hw2() {
		students = new ArrayList<Student>();
		lecturers = new ArrayList<Lecturer>();
	}
	
	public hw2(ArrayList<Student> students, ArrayList<Lecturer> lecturers) {
		this.students = students;
		this.lecturers = lecturers;
	}
	
	public void addLecturer(Lecturer lecturer) {
		lecturers.add(lecturer);
	}
	
	public void addStudent(Student student) {
		students.add(student);
	}
	
	@Override
	public String toString() {
		
		verifyAssigne();
		
		StringBuilder sb = new StringBuilder();
		for (Lecturer lecturer : lecturers) {
			sb.append( "lectorul " + (String.valueOf(lecturers.indexOf(lecturer)+1)) + " \n");
			sb.append(lecturer.toString());
		}
		sb.append("\n\n");
		
		for (Student student : students) {
			sb.append("\nstudent " + (String.valueOf(students.indexOf(student)+1)) + " \n");
			sb.append(student.toString());
		}
		sb.append("\n\n");
		
		return sb.toString();
	}	
	public void setP(ArrayList<Project> p) {
		projects = p;
	}
	
	private void verifyAssigne() {
		
		if(students.isEmpty()|| lecturers.isEmpty())
		{
			System.out.println("lists are empty");
			return;
		}
		
		
		for (Student student : students) {
		
			if(student.getAssignedProj() == null)
			{
				assignProjToStud(student);
				verifyAsig();
			}
		}
		
		
		System.out.println("Finished assigning------------------------");
	}

	public void assignProjToStud(Student student) {
		
		if (student.isFree() && student.listNotEmpty()) {
			
			Project firstProj = student.getFirstProj();
			Lecturer lectForFirstProj = getLect(firstProj);
			student.asigToProj(firstProj, lectForFirstProj);
			
			
			if(firstProj.isOversub()){
				Student worstStudent = getWorstStud(firstProj);
				breakAsig(worstStudent, firstProj, lectForFirstProj);
				removeStudFromProj(worstStudent, firstProj);
			}
			else if(lectForFirstProj.isOversub()){
				Student worstStudent = lectForFirstProj.getWorstStud();
				breakAsig(worstStudent, firstProj, lectForFirstProj);
				lectForFirstProj.removeStudent(worstStudent);
			}
			if(firstProj.isFull()){
				removeAfterWorstStudForProj(firstProj, lectForFirstProj);	
			}
			if(lectForFirstProj.isFull()){
				Student worstStudent = lectForFirstProj.getWorstStud();
				int index = lectForFirstProj.getPrefStud().indexOf(worstStudent);
				for (int i = index+1; i < lectForFirstProj.getPrefStud().size()-1; i++) {
					Student prefStud = lectForFirstProj.getPrefStud().get(i);
					for (Project proj : prefStud.getPrefProj()) {
						if(lectForFirstProj.hasProj(proj)){
							prefStud.removeProj(proj);
							lectForFirstProj.removeStudent(prefStud);
						}
					}
				}
			}
		}
	}

	private void removeAfterWorstStudForProj(Project firstProj, Lecturer lectForFirstProj) {

		ArrayList<Student> currentListOfStuds =  lectForFirstProj.getPrefStud();
		Student worstStudent = lectForFirstProj.getWorstStud();
		
		int worstIndex = currentListOfStuds.indexOf(worstStudent);
		for(int index = worstIndex+1;worstIndex < currentListOfStuds.size()-1;index++){
			currentListOfStuds.get(index).removeProj(firstProj);
			lectForFirstProj.removeStudent(currentListOfStuds.get(index));
		}
	}

	private void removeStudFromProj(Student worstStudent, Project firstProj) {

		for (Lecturer lecturer : lecturers) {
			if(lecturer.hasProj(firstProj))
				lecturer.removeStudent(worstStudent);
		}
	}

	private void breakAsig(Student worstStudent, Project proj, Lecturer lect) {

		worstStudent.makeNull(proj, lect);
	}
	
	public Lecturer getLect(Project firstProj) {

		Lecturer lecturerFound=null;
		
		for (Lecturer lecturer : lecturers) {
			if(lecturer.hasProj(firstProj))
				lecturerFound= lecturer;
		}
		
		return lecturerFound;
	}

	
	private void verifyAsig() {
		
		int i =0;
		for (Student student : students) {
				student.makeNull(null, null);
				student.asigToProj(projects.get(i), null);
				i++;
			}
	}
	
	public Student getWorstStud(Project firstProj) {

		Student worstStud = null;
		
		for (Lecturer lecturer : lecturers) {
			if(lecturer.hasProj(firstProj))
				worstStud = lecturer.getWorstStudForProj(firstProj);
		}
		return worstStud;
	}
	
}
package tema2;

class Project{

	private int capacity;
	private int current;
	private int nr;
	
	public Project(int capacity, int projNr) {
		this.capacity = capacity;
		this.nr = projNr;
	}
	
	@Override
	public String toString() {

		return String.valueOf(nr);
	}
	
	public boolean isOversub() {
		
		if(current > capacity)
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
	
	public void addAsoc() {
		current++;
	}
	
	public void remAsoc() {
		current--;
	}
}
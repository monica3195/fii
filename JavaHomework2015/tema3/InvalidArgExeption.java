package tema3;

// Exceptii
class InvalidArgExeption extends Exception {

	private static final long serialVersionUID = 4303738209624419703L;

	public InvalidArgExeption(String string) {
		System.err.println(string);
	}

	@Override
	public void printStackTrace() {	}
}
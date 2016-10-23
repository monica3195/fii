package tema3;

final class Quit implements ICommand {

	@Override
	public void executeCommand(String command) throws InvalidArgExeption {
		System.exit(0);
	}

}
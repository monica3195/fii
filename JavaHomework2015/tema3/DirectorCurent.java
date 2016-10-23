package tema3;

import java.io.File;
import java.util.regex.Pattern;

final class DirectorCurent implements ICommand {

	static private String dircurent = System.getProperty("user.home") + "\\Music";
	private Pattern directorPattern;

	public DirectorCurent() {

		if (System.getProperty("os.name").contains("Windows"))
			directorPattern = Pattern
					.compile("^([A-Z]:)(\\\\[a-zA-Z0-9]+)+\\\\?");
		else if (System.getProperty("os.name") == "Linux")
			directorPattern = Pattern.compile("^/home(/[a-zA-Z0-9]+)+/?");
	}

	static public String dirCurent() {
		return dircurent;
	}

	@Override
	public void executeCommand(String command) throws InvalidArgExeption {

		if (validateCommand(command)) {
			dircurent = command;
		}
	}

	private boolean validateCommand(String command) throws InvalidArgExeption {

		if (directorPattern.matcher(command).matches()) {
			if ((new File(command)).isDirectory())
				return true;
			else
				throw new InvalidArgExeption("Directorul nu exista");
		} else
			throw new InvalidArgExeption(
					"Argumentul intodus nu reprezinta calea catre un director");
	}

}
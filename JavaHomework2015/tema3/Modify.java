package tema3;

import java.io.File;

final class Modify implements ICommand {

	@Override
	public void executeCommand(String command) throws InvalidArgExeption {

		String[] args = command.split("\\s+");

		File old = new File(DirectorCurent.dirCurent() + "\\" + args[0]);

		if (!old.exists())
			throw new InvalidArgExeption("Fiserul specificat nu exista.");
		else {
			File newF = new File(DirectorCurent.dirCurent() + "\\" + args[1]);
			if (newF.exists())
				throw new InvalidArgExeption(
						"Numele este deja folosit de alt fiser.");
			else
				old.renameTo(newF);
		}

	}

}
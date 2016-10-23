package tema3;

import java.io.File;
import java.util.regex.Pattern;

final class Search implements ICommand {

	@Override
	public void executeCommand(String command) throws InvalidArgExeption {

		File folder = new File(DirectorCurent.dirCurent());
		Pattern pattern = Pattern.compile(command);
		File[] allFiles = folder.listFiles();

		for (File file : allFiles) {
			if (file.isDirectory())
				executeCommand(file.getPath());
			else if (CommandUtils.verifyFile(file.getName())) {
				if (pattern.matcher(file.getName()).matches())
					System.out.println("Am gasit fiserul cautat la: "
							+ file.getPath());
			} else
				throw new InvalidArgExeption(
						"Fiserul nu a putu fi gasit.\nIntroduceti alta expresie regulata si incercati din nou.");
		}
	}
}
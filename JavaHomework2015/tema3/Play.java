package tema3;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

final class Play implements ICommand {

	@Override
	public void executeCommand(String command) throws InvalidArgExeption {

		if (CommandUtils.verifyFile(command)) {
			File file = new File(DirectorCurent.dirCurent() + "\\" + command);
			if (file.exists())
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			else
				throw new InvalidArgExeption(
						"Fiserul audio specificat nu exista.");
		} else
			throw new InvalidArgExeption(
					"Argumentul specificat nu reprezinta un fiser audio.");

	}

}
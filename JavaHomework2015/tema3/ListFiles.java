package tema3;

import java.io.File;

final class ListFiles implements ICommand {

	@Override
	public void executeCommand(String command) throws InvalidArgExeption {

		File folder = new File(command);

		//System.out.println(folder.getPath());
		File[] allFiles = folder.listFiles();

		for (File file : allFiles) {
			if (file.isDirectory()){
				executeCommand(file.getPath());
				System.out.println("+ " + file.getName());
			}
			else if (CommandUtils.verifyFile(file.getName())) {
				System.out.println("- " + file.getName());
			}
		}
	}
}
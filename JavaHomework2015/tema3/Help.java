package tema3;

// Help
final class Help implements ICommand {

	private boolean isPossible(String command) {
		return CommandUtils.hasComm(command);
	}

	private void printWithArgs(String command) {
		System.out.println(CommandUtils.getCommandHelp(command));
	}

	@Override
	public String toString() {
		return CommandUtils.getHelp();
	}

	@Override
	public void executeCommand(String command) throws InvalidArgExeption {
		CommandUtils.initCommUtils();

		if (command == "")
			System.out.print(this.toString());
		else if (isPossible(command))
			printWithArgs(command);
		else
			throw new InvalidArgExeption(
					"The argument is invalid.\nPlease consult help for posiible arguments.\n");
	}

}
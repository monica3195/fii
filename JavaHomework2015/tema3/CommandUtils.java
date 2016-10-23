package tema3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class CommandUtils {

	private CommandUtils() {
	}

	public static void initCommUtils() {

		if (initialized)
			return;

		try {
			@SuppressWarnings("resource")
			String content = new Scanner(new File("commands.json"))
					.useDelimiter("\\Z").next();
			JSONObject jobj = new JSONObject(content);

			JSONArray comms = (JSONArray) jobj.get("commands");

			for (int i = 0; i < comms.length(); i++) {
				JSONObject o = comms.getJSONObject(i);

				commands[i][0] = o.getString("command");
				commands[i][1] = o.getString("desc");
			}

		} catch (JSONException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	static private boolean initialized = false;
	static private String[][] commands = new String[10][2];

	static public boolean hasComm(String command) {
		for (String[] strings : commands) {
			if (strings[0] != null)
				if (strings[0].equals(command))
					return true;
		}
		return false;
	}

	static public String getHelp() {

		StringBuilder sb = new StringBuilder();

		for (String[] strings : commands) {
			sb.append(strings[0] + " " + strings[1] + "\n");
		}

		return sb.toString();

	}

	static public String getCommandHelp(String command) {

		StringBuilder sb = new StringBuilder();

		for (String[] strings : commands) {

			if (strings[0].equalsIgnoreCase(command)) {
				sb.append(strings[0] + " " + strings[1] + "\n");
				break;
			}
		}
		return sb.toString();
	}

	static public boolean verifyFile(String name) {

		Pattern pattern = Pattern.compile("(.+(\\.(?i)(mp3|wav))$)");

		if (pattern.matcher(name).matches())
			return true;
		else
			return false;
	}
}
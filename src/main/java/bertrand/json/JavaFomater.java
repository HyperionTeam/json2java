package bertrand.json;

import java.io.File;
import java.io.FileNotFoundException;

import de.hunsicker.jalopy.Jalopy;

public class JavaFomater {

	public static void format(File file) throws FileNotFoundException {
		Jalopy jalopy = new Jalopy();
		jalopy.setInput(file);
		jalopy.setOutput(file);

		jalopy.format();

		if (jalopy.getState() == Jalopy.State.OK) {
			System.out.println(file + " successfully formatted");
		} else if (jalopy.getState() == Jalopy.State.WARN) {
			System.out.println(file + " formatted with warnings");
		} else if (jalopy.getState() == Jalopy.State.ERROR) {
			System.out.println(file + " could not be formatted");
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("Test.java");
		JavaFomater.format(file);
	}

}

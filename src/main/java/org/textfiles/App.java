package org.textfiles;

import org.textfiles.vowels.AverageCalculator;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

	public static void main(String[] args) {
		try {
			new AverageCalculator.Builder()
					.addRootLocation(Paths.get(System.getProperty("user.dir")))
					.addInputFileName("INPUT.txt")
					.addOutputFileName("OUTPUT.txt")
					.build()
					.calculate()
					.write();

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}

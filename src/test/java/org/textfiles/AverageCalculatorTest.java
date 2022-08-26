package org.textfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;
import org.junit.Test;
import org.textfiles.vowels.AverageCalculator;

public class AverageCalculatorTest {

	@Test
	public void testCalculateAndWrite() throws FileNotFoundException {
		new AverageCalculator.Builder()
				.addRootLocation(Paths.get(System.getProperty("user.dir")
						+ File.separator + "src"
						+ File.separator + "test"
						+ File.separator + "resources"))
				.addInputFileName("INPUT.txt")
				.addOutputFileName("OUTPUT.txt")
				.build()
				.calculate()
				.write();
		File output = new File(System.getProperty("user.dir")
				+ File.separator + "src"
				+ File.separator + "test"
				+ File.separator + "resources"
				+ File.separator + "OUTPUT.txt");

		assertTrue(output.exists());

		try (Scanner scanner = new Scanner(output)) {
			assertEquals("({a,e},4)->2.0", scanner.next());
			assertEquals("({a,o},5)->2.0", scanner.next());
			assertEquals("({a,o},6)->2.5", scanner.next());
		}
		output.delete();
	}
}

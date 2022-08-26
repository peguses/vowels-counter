package org.textfiles.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class InputTextFile extends TextFile {

	private List<String> words = new ArrayList<>();

	private InputTextFile(Path rootLocation, String fileName) {
		super.rootLocation = rootLocation;
		super.fileName = fileName;
	}

	public List<String> getWords() {
		return this.words;
	}

	public static class Builder {

		private Path rootLocation;
		private String fileName;

		public Builder setRootLocation(Path rootLocation) {
			this.rootLocation = rootLocation;
			return this;
		}

		public Builder setInputFileName(String name) {
			this.fileName = name;
			return this;
		}

		public InputTextFile build() throws FileNotFoundException {
			InputTextFile inputTextFile = new InputTextFile(this.rootLocation, this.fileName);
			inputTextFile.readWords();
			return inputTextFile;
		}
	}

	private void readWords() throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(rootLocation + File.separator + fileName))) {
			while (scanner.hasNext()) {
				words.add(scanner.next());
			}
		}
	}
}

package org.textfiles.vowels;

import org.textfiles.io.InputTextFile;
import org.textfiles.io.OutputTextFile;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class AverageCalculator {

	private InputTextFile inputTextFile;

	private OutputTextFile outputTextFile;

	private AverageCalculator(InputTextFile inputTextFile,
			OutputTextFile outputTextFile) {
		this.inputTextFile = inputTextFile;
		this.outputTextFile = outputTextFile;
	}

	public OutputTextFile calculate() {
		groupWordByLength(inputTextFile.getWords()).forEach((wordLength, word) -> {
			Map<Character, Integer> characterIntegerMap = countVowels(word);
			AtomicReference<Integer> totalCount = new AtomicReference<>(0);
			characterIntegerMap.forEach((vowel, count) -> {
				totalCount.updateAndGet(v -> v + count);
			});
			double average = 0;
			if (totalCount.get() == characterIntegerMap.size()) {
				average = totalCount.get();
			} else {
				average = 1.0 * totalCount.get() / characterIntegerMap.size();
			}
			outputTextFile.addOutPut(
					new OutputTextFile.OutPut(characterIntegerMap.keySet(), wordLength, average));
		});
		return outputTextFile;
	}

	private Map<Character, Integer> countVowels(List<String> words) {
		Map<Character, Integer> characterIntegerMap = new HashMap<>();
		for (String word : words) {
			char[] chars = word.toLowerCase(Locale.ROOT).toCharArray();
			for (char character : chars) {
				if (isVowel(character)) {
					if (!characterIntegerMap.containsKey(character)) {
						characterIntegerMap.put(character, 1);
					} else {
						characterIntegerMap.put(character, characterIntegerMap.get(character) + 1);
					}
				}
			}
		}
		return characterIntegerMap;
	}

	private boolean isVowel(char character) {
		switch (character) {
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
				return true;
			default:
				return false;
		}
	}

	private Map<Integer, List<String>> groupWordByLength(List<String> words) {
		return words.stream()
				.map(s -> s.replace(".", ""))
				.collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList()));
	}

	public static class Builder {

		private Path rootLocation;

		private String inputFileName;

		private String outputFileName;

		public Builder addRootLocation(Path rootLocation) {
			this.rootLocation = rootLocation;
			return this;
		}

		public Builder addInputFileName(String name) {
			this.inputFileName = name;
			return this;
		}

		public Builder addOutputFileName(String name) {
			this.outputFileName = name;
			return this;
		}


		public AverageCalculator build() throws FileNotFoundException {

			InputTextFile inputTextFile = new InputTextFile
					.Builder()
					.setInputFileName(this.inputFileName)
					.setRootLocation(this.rootLocation)
					.build();
			OutputTextFile outputTextFile = new OutputTextFile.Builder()
					.setRootLocation(this.rootLocation)
					.setOutputFileName(this.outputFileName)
					.build();
			return new AverageCalculator(inputTextFile, outputTextFile);
		}
	}
}

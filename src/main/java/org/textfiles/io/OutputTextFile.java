package org.textfiles.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class OutputTextFile extends TextFile {

	List<OutPut> outPuts = new ArrayList<>();

	private OutputTextFile(Path rootLocation, String fileName) {
		super.rootLocation = rootLocation;
		super.fileName = fileName;
	}

	public void addOutPut(OutPut outPut) {
		this.outPuts.add(outPut);
	}

	public void write() throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(
				new File(super.rootLocation + File.separator + super.fileName))) {
			this.outPuts.forEach(outPut -> {
				StringBuilder str = new StringBuilder();
				str.append("({");
				AtomicReference<Integer> totalCount = new AtomicReference<>(0);
				outPut.vowels.forEach(s -> {
					str.append(s);
					if (totalCount.get() != outPut.vowels.size() - 1) {
						str.append(",");
					}
					totalCount.updateAndGet(v->v+1);
				});
				str.append("},")
						.append(outPut.wordLength)
						.append(")->")
						.append(outPut.average);
				writer.println(str);
			});
		}
	}

	public static class Builder {

		private Path rootLocation;
		private String fileName;

		public Builder setRootLocation(Path rootLocation) {
			this.rootLocation = rootLocation;
			return this;
		}

		public Builder setOutputFileName(String name) {
			this.fileName = name;
			return this;
		}

		public OutputTextFile build() {
			return new OutputTextFile(rootLocation, fileName);
		}
	}

	public static class OutPut {

		private Set vowels = null;
		private final Integer wordLength;
		private final double average;

		public OutPut(Set<Character> characterSet, Integer wordLength, double average) {
			this.vowels = characterSet;
			this.wordLength = wordLength;
			this.average = average;
		}
	}
}

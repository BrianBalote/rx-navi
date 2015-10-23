package org.balote.drugsearch.utils;

import java.io.FileWriter;
import java.io.PrintWriter;

public final class StringFileWriterUtil {

	private StringFileWriterUtil() {
	}

	public static void writeStringToFile(String stringToWrite,
			String directoryAndFileName) {

		FileWriter outFile;
		try {

			outFile = new FileWriter(directoryAndFileName);
			PrintWriter out = new PrintWriter(outFile);
			out.println(stringToWrite);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

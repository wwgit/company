package com.qf.test.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class RWTxtFileWithBuffer {
	
	static Logger logger = Logger.getLogger(RWTxtFileWithBuffer.class.getName());

	public static void WriteStringToFile(String filePath, String content)
			throws Exception {
		try {
			File outFile = new File(filePath);
			FileWriter fileWrite = new FileWriter(outFile);
			BufferedWriter writer = new BufferedWriter(fileWrite);
			writer.write(content);
			writer.close();
			fileWrite.close();
		} catch (FileNotFoundException ex) {
			logger.error("operateTxtFile.WirteStringToFile throw FileNotFoundException: "
					+ ex.getMessage() + filePath);
			throw ex;

		} catch (IOException ex) {
			logger.error("operateTxtFile.WirteStringToFile throw IOException: "
					+ ex.getMessage() + filePath);
			throw ex;
		}
	}

	public static String GetStringFromFile(String filePath) throws Exception {
		String inString = "";
		StringBuilder str = new StringBuilder();

		try {
			File inFile = new File(filePath);
			FileReader fileReader = new FileReader(inFile);

			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			while ((inString = reader.readLine()) != null) {
				str.append(inString);
			}
			fileReader.close();
			reader.close();
		} catch (FileNotFoundException ex) {
			logger.error("operateTxtFile.GetStringFromFile throw FileNotFoundException: "
					+ ex.getMessage() + filePath);
			throw ex;

		} catch (IOException ex) {
			logger.error("operateTxtFile.GetStringFromFile throw IOException: "
					+ ex.getMessage() + filePath);
			throw ex;
		}
		return str.toString();
	}

	public static Map GetMapFromFile(String filePath) throws Exception {
		String inString = "";
		StringBuilder str = new StringBuilder();
		String[] temp = null;
		Map map = new HashMap<String, String>();

		try {
			File inFile = new File(filePath);
			FileReader fileReader = new FileReader(inFile);

			BufferedReader reader = new BufferedReader(new FileReader(inFile));

			while ((inString = reader.readLine()) != null) {
				temp = inString.split("=");
				map.put(temp[0], temp[1]);
			}
			fileReader.close();
			reader.close();
		} catch (FileNotFoundException ex) {
			logger.error("operateTxtFile.GetMapFromFile throw FileNotFoundException: "
					+ ex.getMessage() + filePath);
			throw ex;
		} catch (IOException ex) {
			logger.error("operateTxtFile.GetMapFromFile throw IOException: "
					+ ex.getMessage() + filePath);
			throw ex;
		}

		return map;
	}

	public static int getValueFromFile(String filePath, String key)
			throws Exception {
		int value = 0;
		Map map = GetMapFromFile(filePath);
		try {
			value = Integer.parseInt((String) map.get(key));
		} catch (Exception ex) {
			logger.error("operateTxtFile.getValueFromFile throw IOException: "
					+ ex.getMessage() + "\nParam 'filePath': " + filePath
					+ ", 'key': " + key);
			throw ex;
		}
		return value;

	}
}

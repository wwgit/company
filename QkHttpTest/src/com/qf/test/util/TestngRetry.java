package com.qf.test.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.qf.test.dal.RWTxtFileWithBuffer;

public class TestngRetry   implements IRetryAnalyzer{
	static {
		
		File logconfFile=new File("config/log4j.properties");
		if(!logconfFile.isFile()||!logconfFile.exists())	
		{}
		else
		{
			try{
			LogConfiguration.initLogFromConfigFile(logconfFile.getPath());
			}
			catch(Exception ex){
				
			}
		}
	}
	private static Logger logger = Logger.getLogger(TestngRetry.class);
	private static String retryKey = "retrycount";
	private int retryCount = 1;
	private static int maxRetryCount;
	private static String configFilepath = "config\\arrow.properties";

	static {

		try {
			maxRetryCount = RWTxtFileWithBuffer.getValueFromFile(configFilepath,
					retryKey);
			logger.info("RetryCount=" + maxRetryCount);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryCount) {
			String message = "Retry for： [" + result.getName() + "] on class ["
					+ result.getTestClass().getName() + "] retry " + retryCount
					+ " times";
			logger.info(message);
			Reporter.setCurrentTestResult(result);
			Reporter.log("RunCount=" + (retryCount + 1));
			retryCount++;
			return true;
		}
		return false;
	}

	public static int getMaxRetryCount() {
		return maxRetryCount;
	}

	public int getRetryCount() {
		return retryCount;
	}
}

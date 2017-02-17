package com.qf.test.unittest.base;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.apache.log4j.Logger;

import com.qf.test.dal.RWTxtFileWithBuffer;
import com.qf.test.util.*;

public class BaseParpare {
	static Logger logger = Logger.getLogger(BaseParpare.class.getName());
	protected ITestContext testContext = null;
	protected String testcasefolderPath = "";

	@BeforeClass
	public void startTest(ITestContext context) {
		logger.info("Start BeforeClass StartTest");
		try {
			String logConfgFilePath = context.getCurrentXmlTest().getParameter(
					"logConfgFilePath");
			LogConfiguration.initLogFromConfigFile(logConfgFilePath);
			
			testcasefolderPath = context.getCurrentXmlTest().getParameter(
					"testcaseFolderPath");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			Assert.fail(ex.getMessage());
		}
		logger.info("End BeforeClass StartTest ");
	}

	@AfterClass
	public void endTest() {
		if (true) {
		} else {
		}
	}
}

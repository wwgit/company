package com.qf.test.dal;

import java.io.File;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.testng.annotations.Test;

import com.qf.test.entity.*;

public class RWTxtFileWithJackson {

	static Logger logger = Logger.getLogger(RWTxtFileWithJackson.class
			.getName());

	public static TestCase Read(String fileName) throws Exception {
		TestCase tc = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			tc = mapper.readValue(new File(fileName), TestCase.class);
		} catch (Exception ex) {
			logger.error("RWTxtFileWithJackson.Read() throw exception: "
					+ ex.getMessage());
			throw ex;
		}
		return tc;
	}

	public static void Write(String fileName, TestCase tc) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		try {
			mapper.writeValue(new File(fileName), tc);
		} catch (Exception ex) {
			logger.error("RWTxtFileWithJackson.Write() throw exception: "
					+ ex.getMessage());
			throw ex;
		}
	}

	public static String WriteAsString(TestCase tc) throws Exception {
		String sb = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		try {
			sb = mapper.writeValueAsString(tc);
		} catch (Exception ex) {
			logger.error("RWTxtFileWithJackson.Write() throw exception: "
					+ ex.getMessage());
			throw ex;
		}
		return sb;
	}
}

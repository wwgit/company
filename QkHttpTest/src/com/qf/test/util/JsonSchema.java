package com.qf.test.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class JsonSchema {
	static Logger logger = Logger.getLogger(JsonSchema.class.getName()); 
	
	public static String  Validate(String tcFilePath) {
		
		final String schemaPath="Schema\\testcaseSchema.json";

		String errMessage=null;
        
        boolean isValid=false;
        JsonNode data = readJSONfile(tcFilePath);
        JsonNode schema = readJSONfile(schemaPath);

        ProcessingReport report =
                JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schema, data);
        
        isValid=report.isSuccess();
        
        if(!isValid)
        {
        	Iterator<ProcessingMessage> it = report.iterator(); 
        	while(it.hasNext())
            {
            	errMessage=it.next().getMessage();
            	logger.error("JsonSchema.Validate Error: "+errMessage+" - "+tcFilePath +"\n");            
            }            
        }        
        return errMessage;
    }
	
	private static JsonNode readJSONfile(String filePath) {
        JsonNode instance = null;
        try {
            instance = new JsonNodeReader().fromReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }
}

package com.qf.test.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mashape.unirest.http.JsonNode;


public class jsonParse {

	static Logger logger = Logger.getLogger(jsonParse.class.getName());

	private JSONObject jsonObject;

	public jsonParse(String content) {
		try {
			jsonObject = new JSONObject(content);

		} catch (JSONException ex) {
			logger.error("jsonParse constructor throw JSONException: "
					+ ex.getMessage() + content);
			throw ex;
		}
	}

	public jsonParse(JsonNode content) {
		this(content.getObject());
	}

	public jsonParse(JSONObject content) {
		try {
			jsonObject = new JSONObject(content);
		} catch (JSONException ex) {
			logger.error("jsonParse constructor throw JSONException: "
					+ ex.getMessage() + content);
			throw ex;
		}
	}
	
	public static String dataCovertToString(Map data)
	{
		StringBuilder sb=new StringBuilder();
		Boolean isBool=false;
		String key=null;
		String value=null;
		sb.append("{");
		
		Iterator iter = data.entrySet().iterator();
		while(iter.hasNext()) {
		    Map.Entry entry = (Map.Entry)iter.next();
		    // 获取key
		    key = (String)entry.getKey();
		        // 获取value
		    sb.append("\""+key+"\":");
		    try
			{
		    	
		    	 value = (String)entry.getValue();
		    	 sb.append("\""+value+"\"");
			}
			catch(Exception ex)
			{
				if(ex.getMessage().contains("java.lang.Boolean cannot be cast to java.lang.String"))
					sb.append((Boolean)entry.getValue());
				else if(ex.getMessage().contains("java.lang.Integer cannot be cast to java.lang.String"))
					sb.append((Integer)entry.getValue());
				else 
					throw ex;
			}		    
		    
		    if(iter.hasNext())
		    sb.append(",");
		}
		sb.append("}");
		
		return sb.toString();
	}

	public String getResultValue(String path) {		
		if(path.equals(""))
			return jsonObject.toString();
		String value = null;
		// headers.Content-Type
		String[] keys = path.split("\\.");
		JSONObject jObject = null;

		jObject = jsonObject;
		
		
		for (int i = 0; i < keys.length - 1; i++) {
			jObject = jObject.getJSONObject(keys[i]);
		}		

		value = jObject.optString(keys[keys.length - 1]).toString();

		return value;
	}
}

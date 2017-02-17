package com.qf.test.bll;

import java.io.File;
import java.util.*;

import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.qf.test.dal.jsonParse;
import com.qf.test.entity.Compare;

public class httpHelper {	


	public static void SetDefaultHeaders(Map headers) {
		String key = null;
		String value = null;
		Iterator iter = headers.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = (String) headers.get(key);
			Unirest.setDefaultHeader(key, value);
		}
	}

	public static HttpResponse<String> GetWithArgs(Map headers, String url,
			Map args) throws JSONException, UnirestException {

		HttpResponse<String> response = Unirest.get(url).headers(headers)
				.queryString(args).asString();

		return response;
	}

	public static HttpResponse<String> PostWithForm(Map headers, String url,
			Map form) throws JSONException, UnirestException {

		HttpResponse<String> strResponse = Unirest.post(url).headers(headers)
				.fields(form).asString();
		return strResponse;
	}

	public static HttpResponse<String> PostWithBody(Map headers, String url,
			Map body) throws JSONException, UnirestException {

		HttpResponse<String> strResponse = null;
		
		String strBody = jsonParse.dataCovertToString(body);
		
		strResponse = Unirest.post(url).headers(headers).body(strBody).asString();

		return strResponse;
	}

	public static HttpResponse<String> PostWithFile(String url, Map form,
			Map files) throws JSONException, UnirestException {

		String key = null;
		Iterator iter = files.keySet().iterator();
		HttpResponse<String> strResponse = null;
		if (iter.hasNext()) {
			key = (String) iter.next();

			strResponse = Unirest.post(url).fields(form)
					.field(key, new File((String) files.get(key))).asString();
		}
		return strResponse;
	}

	public static HttpResponse<String> PostWithImage(String url, Map form,
			Map imagines) throws JSONException, UnirestException {

		String key = null;
		Iterator iter = imagines.keySet().iterator();
		HttpResponse<String> strResponse = null;
		if (iter.hasNext()) {
			key = (String) iter.next();
			strResponse = Unirest
					.post(url)
					.fields(form)
					.field(key, new File((String) imagines.get(key)),
							"image/jpeg").asString();
		}
		return strResponse;
	}
}
/**   
* @Title: HttpJackHelper.java 
* @Package com.qf.test.helpers 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年2月20日 下午7:54:28 
* @version V1.0   
*/
package com.qf.test.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.options.Options;


/** 
* @ClassName: HttpUnirestHelper 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年2月20日 下午8:44:19 
*  
*/
public abstract class HttpUnirestHelper {
	
	public static void SetHeaders(HttpRequest req, Map<String,String> headers) {
		req.headers(headers);
	}
	public static void SetHeader(HttpRequest req, String name, String value) {
		req.header(name, value);
	}
	public static void SetCommonDefaultHeaders(HttpRequest req) {
		SetHeader(req, "Cache-Control", "no-cache");
		SetHeader(req, "Connection", "Keep-Alive");
		SetHeader(req, "Accept", "*/*");
		SetHeader(req, "Accept-Language", "en,zh-cn,zh;q=0.5");
		SetHeader(req, "Accept-Charset", "GB2312,utf-8");
		SetHeader(req, "Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	}
	public static void SetAjaxDefaultHeaders(HttpRequest req) {
		SetHeader(req, "X-Requested-With", "XMLHttpRequest");
		SetCommonDefaultHeaders(req);
	}
	public static void SetUploadDefaultHeaders(HttpRequest req) {
		SetHeader(req, "Content-Type", "multipart/form-data");
		SetCommonDefaultHeaders(req);
	}
	
	
	
	private static HttpRequest getInitHttpRequest(String url, String methodName) 
												 throws NoSuchMethodException, SecurityException, 
												 IllegalAccessException, IllegalArgumentException, 
												 InvocationTargetException {
		Method methodObj = Unirest.class.getMethod(methodName, String.class);
		return (HttpRequest) methodObj.invoke(null, url);
	}

}

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
import java.util.UUID;

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
	
	private static String uuid = UUID.randomUUID().toString();
	
	public static void setHeaders(HttpRequest req, Map<String,String> headers) {
		req.headers(headers);
	}
	public static void setHeader(HttpRequest req, String name, String value) {
		req.header(name, value);
	}
	public static void setCommonDefaultHeaders(HttpRequest req) {
		setHeader(req, "Cache-Control", "no-cache");
		setHeader(req, "Connection", "Keep-Alive");
		setHeader(req, "Accept", "*/*");
		setHeader(req, "Accept-Language", "en,zh-cn,zh;q=0.5");
		setHeader(req, "Accept-Charset", "GB2312,utf-8");
		setHeader(req, "Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	}
	public static void setAjaxDefaultHeaders(HttpRequest req) {
		setHeader(req, "X-Requested-With", "XMLHttpRequest");
		setCommonDefaultHeaders(req);
	}
	public static void SetUploadDefaultHeaders(HttpRequest req) {
		setHeader(req, "Content-Type", "multipart/form-data;boundary=" + uuid);
		setCommonDefaultHeaders(req);
	}
	public static void SetBasicAuth(HttpRequest req, String username, String password) {
		req.basicAuth(username, password);
	}
	public static void setTokenHeader(HttpRequest req, String token) {
		setHeader(req, "Autorization", token);
	}
	public static void setCookie(HttpRequest req, String cookieValue) {
		setHeader(req, "Cookie", cookieValue);
	}
	
	public static void setParams(HttpRequest req, Map<String,Object> args) {
		req.queryString(args);
	}
	public static HttpResponse<String> sendRequest(HttpRequest req) throws UnirestException {
		return req.asString();
	}
	
	private static HttpRequest getInitHttpRequest(String url, String methodName) 
												 throws NoSuchMethodException, SecurityException, 
												 IllegalAccessException, IllegalArgumentException, 
												 InvocationTargetException {
		Method methodObj = Unirest.class.getMethod(methodName, String.class);
		return (HttpRequest) methodObj.invoke(null, url);
	}
	
	

}

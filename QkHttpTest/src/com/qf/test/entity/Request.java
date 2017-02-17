package com.qf.test.entity;

import java.util.Map;

public class Request {

	private Map headers;
	private String method;
	private String url;
	private Map args;
	private Map form;
	private Map data;
	private Map files;
	private Map images;

	public Map getHeaders() {
		return headers;
	}

	public void setHeaders(Map headers) {
		this.headers = headers;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map getArgs() {
		return args;
	}

	public void setArgs(Map args) {
		this.args = args;
	}

	public Map getForm() {
		return form;
	}

	public void setFrom(Map form) {
		this.form = form;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public Map getFiles() {
		return files;
	}

	public void setFiles(Map files) {
		this.files = files;
	}

	public Map getImages() {
		return images;
	}

	public void setImages(Map images) {
		this.images = images;
	}

}

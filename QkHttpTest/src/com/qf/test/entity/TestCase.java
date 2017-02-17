package com.qf.test.entity;

import java.util.List;

public class TestCase {

	public TestCase() {
	}

	private Request request;
	private Dependence[] dependencies;
	private ResultCompare resultCompare;
	private PostSetting postSetting;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Dependence[] getDependencies() {
		return dependencies;
	}

	public void setDependencies(Dependence[] dependencies) {
		this.dependencies = dependencies;
	}

	public ResultCompare getResultCompare() {
		return resultCompare;
	}

	public void setResultCompare(ResultCompare resultCompare) {
		this.resultCompare = resultCompare;
	}

	public PostSetting getPostSetting() {
		return postSetting;
	}

	public void setPostSetting(PostSetting postSetting) {
		this.postSetting = postSetting;
	}

}

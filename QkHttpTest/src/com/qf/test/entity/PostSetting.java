package com.qf.test.entity;

public class PostSetting {
	private String module;
	private String testcase;
	private Setting[] settings;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTestcase() {
		return testcase;
	}

	public void setTestcase(String testcase) {
		this.testcase = testcase;
	}

	public Setting[] getSettings() {
		return settings;
	}

	public void setSettings(Setting[] settings) {
		this.settings = settings;
	}

}

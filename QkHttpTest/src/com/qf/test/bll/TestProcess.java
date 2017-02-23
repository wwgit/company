package com.qf.test.bll;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.qf.test.dal.RWTxtFileWithJackson;
import com.qf.test.dal.jsonParse;
import com.qf.test.dal.RWTxtFileWithBuffer;
import com.qf.test.entity.Compare;
import com.qf.test.entity.PostSetting;
import com.qf.test.entity.ResultCompare;
import com.qf.test.entity.Setting;
import com.qf.test.entity.TestCase;
import com.qf.test.unittest.base.BaseParpare;
import com.qf.test.util.JsonSchema;

public class TestProcess extends BaseParpare {

	static Logger logger = Logger.getLogger(TestProcess.class.getName());

	public static void Run(String testcaseFolderPath, String module,
			String testcaseName) {
		// Map headers, String url, Map form,Map files,String body

		String tcFullName = null;
		HttpResponse<String> strResponse = null;

		try {
			tcFullName = testcaseFolderPath + "\\" + module + "\\"
					+ testcaseName + ".csv";
			logger.info("Process start 'LoadTestCase()': " + tcFullName);
			TestCase tc = LoadTestCase(tcFullName);
			logger.info(RWTxtFileWithJackson.WriteAsString(tc));
			logger.info("Process end 'LoadTestCase()': " + tcFullName);

			logger.info("Process start 'SendHttpRequest()': " + tcFullName);
			strResponse = SendHttpRequest(tc);
			logger.warn(strResponse.getStatus());
			logger.warn(strResponse.getStatusText());
			logger.warn(strResponse.getHeaders());
			logger.warn(strResponse.getBody());
			logger.info("Process end 'SendHttpRequest()': " + tcFullName);

			logger.info("Process start 'ResultAnalyze()': " + tcFullName);
			ResultAnalyze(strResponse, tc);
			logger.info("Process end 'ResultAnalyze()': " + tcFullName);

			logger.info("Process start 'PostSetting()': " + tcFullName);
			PostSetting(testcaseFolderPath, strResponse, tc.getPostSetting());
			logger.info("Process end 'PostSetting()': " + tcFullName);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			Assert.fail(ex.getMessage());
		}

	}

	private static void PostSetting(String testcaseFolderPath,
			HttpResponse<String> strResponse, PostSetting postSetting) {
		if (postSetting == null)
			return;

		try {
			Setting[] settings = postSetting.getSettings();
			jsonParse jParse = new jsonParse(strResponse.getBody());

			for (int i = 0; i < settings.length; i++) {
				if (settings[i].getMethod().equals("replace")) {
					String sourceValue = jParse.getResultValue(settings[i]
							.getSourcepath());
					String sourceTemplet = settings[i].getSourcetemplet();
					// http://www.runoob.com/java/java-regular-expressions.html
					sourceTemplet = sourceTemplet.replace("$placeholder$",
							"(.*?)");
					Pattern r = Pattern.compile(sourceTemplet);
					Matcher m = r.matcher(sourceValue);
					if (m.find()) {
						sourceValue = m.group(1);
					} else {
						logger.error("Response not contain postSetting param with sourcepath: "
								+ settings[i].getSourcepath()
								+ ", sourcetemplet: "
								+ settings[i].getSourcetemplet());
						Assert.fail("Response not contain postSetting param with sourcepath: "
								+ settings[i].getSourcepath()
								+ ", sourcetemplet: "
								+ settings[i].getSourcetemplet());
					}

					String nextModule = postSetting.getModule();
					String nextTestcase = postSetting.getTestcase();
					String nextTCfilePath = testcaseFolderPath + "\\"
							+ nextModule + "\\" + nextTestcase + ".csv";
					String strNextRequest = RWTxtFileWithBuffer
							.GetStringFromFile(nextTCfilePath);
					jsonParse jNextParse = new jsonParse(strNextRequest);
					//jNextParse.initRequest();
					String oldtargetValue = jNextParse
							.getResultValue(settings[i].getTargetpath());
					String newtargetValue = null;
					String targetTemplet = (settings[i].getTargettemplet());
					String replaceValue = null;
					targetTemplet = targetTemplet.replace("$placeholder$",
							"(.*?)");

					r = Pattern.compile(targetTemplet);
					m = r.matcher(oldtargetValue);
					if (m.find()) {

						replaceValue = m.group(1);
						r = Pattern.compile(replaceValue);
						m = r.matcher(oldtargetValue);
						if (m.find()) {
							newtargetValue = m.replaceAll(sourceValue);
						} else {
							logger.error("PostSetting testcase not contain param with targetpath: "
									+ settings[i].getTargetpath()
									+ ", targettemplet: "
									+ settings[i].getTargettemplet()+", replaceValue: "+replaceValue);
							Assert.fail("PostSetting testcase not contain param with targetpath: "
									+ settings[i].getTargetpath()
									+ ", targettemplet: "
									+ settings[i].getTargettemplet()+", replaceValue: "+replaceValue);
						}

					} else {
						logger.error("PostSetting testcase not contain param with targetpath: "
								+ settings[i].getTargetpath()
								+ ", targettemplet: "
								+ settings[i].getTargettemplet()+", replaceValue: "+replaceValue);
						Assert.fail("PostSetting testcase not contain param with targetpath: "
								+ settings[i].getTargetpath()
								+ ", targettemplet: "
								+ settings[i].getTargettemplet()+", replaceValue: "+replaceValue);
					}

					strNextRequest = strNextRequest.replace(oldtargetValue,
							newtargetValue);
					RWTxtFileWithBuffer.WriteStringToFile(nextTCfilePath,
							strNextRequest);
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			Assert.fail(ex.getMessage());
		}
	}

	private static void ResultAnalyze(HttpResponse<String> strResponse,
			TestCase tc) {
		if (tc == null||tc.getResultCompare()==null)
			return;
		
		ResultCompare resultCompare=tc.getResultCompare();
		if (resultCompare.getAcceptpattern().equals("json")) {
			Compare[] compares = resultCompare.getCompares();
			jsonParse jParse = new jsonParse(strResponse.getBody());

			for (int i = 0; i < compares.length; i++) {

				String actualValue = jParse.getResultValue(compares[i]
						.getPath());
				String expectValue = compares[i].getExpectvalue();
				if (compares[i].getMethod().equals("equal")) {
					if (!expectValue.equals(actualValue))
						logger.error("Not equal: \n\texpectValue: "
								+ expectValue + " , actualValue: "
								+ actualValue);
					Assert.assertEquals(actualValue, expectValue);

				}
				if (compares[i].getMethod().equals("contain")) {
					if (!actualValue.contains(expectValue))
						logger.error("Not contain: \n\texpectValue: "
								+ expectValue + " , actualValue: "
								+ actualValue);
					Assert.assertTrue(actualValue.contains(expectValue),
							"AssertFail: Not Contain: Expect: " + expectValue
									+ "; Actual: " + actualValue + "\n");
				}
			}
		}
		else if(resultCompare.getAcceptpattern().equals("html"))
		{
			Compare[] compares = resultCompare.getCompares();
			for (int i = 0; i < compares.length; i++) {
				
			if (compares[i].getMethod().equals("contain")) {
				String expectValue = compares[i].getExpectvalue();
				String actualValue=strResponse.getBody();
				if (!actualValue.contains(expectValue))
					logger.error("Not contain: \n\texpectValue: "
							+ expectValue + " , actualValue: "
							+ actualValue);
				Assert.assertTrue(actualValue.contains(expectValue),
						"AssertFail: Not Contain: Expect: " + expectValue
								+ "; Actual: " + actualValue + "\n");
				
			}
			}		
		}
	}

	private static HttpResponse<String> SendHttpRequest(TestCase tc)
			throws UnirestException {
		HttpResponse<String> strResponse = null;
		try {			
			
			
			if (tc.getRequest().getMethod().equals("post")) {

				if (tc.getRequest().getData() != null&&tc.getRequest().getData().size()!=0)
					strResponse = httpHelper.PostWithBody(tc.getRequest()
							.getHeaders(), tc.getRequest().getUrl(), tc
							.getRequest().getData());
				else if (tc.getRequest().getFiles() != null&&tc.getRequest().getFiles().size()!=0)
					strResponse = httpHelper.PostWithFile(tc.getRequest()
							.getUrl(), tc.getRequest().getForm(), tc
							.getRequest().getFiles());
				else if (tc.getRequest().getImages() != null&&tc.getRequest().getImages().size()!=0)
					strResponse = httpHelper.PostWithImage(tc.getRequest()
							.getUrl(), tc.getRequest().getForm(), tc
							.getRequest().getImages());
				else
					strResponse = httpHelper.PostWithForm(tc.getRequest()
							.getHeaders(), tc.getRequest().getUrl(), tc
							.getRequest().getForm());
			} else if (tc.getRequest().getMethod().equals("get")) {
				strResponse = httpHelper.GetWithArgs(tc.getRequest()
						.getHeaders(), tc.getRequest().getUrl(), tc
						.getRequest().getArgs());
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			Assert.fail(ex.getMessage());
		}
		return strResponse;
	}

	private static TestCase LoadTestCase(String fileName) throws Exception {
		
		String validError=JsonSchema.Validate(fileName);
		if(validError!=null)
			Assert.fail(validError);
			
		return RWTxtFileWithJackson.Read(fileName);
	}

}

package com.qf.test.unittest; 
import org.testng.annotations.Test; 

import com.qf.test.unittest.base.BaseParpare; 
import com.qf.test.bll.TestProcess;
public class APPLY_Test extends BaseParpare{ 
//@Test(dependsOnMethods = {"login_Test","postFileList_Test"})
public void fileUpload_Test() {
	TestProcess.Run(testcasefolderPath,"APPLY","FileUpload");
 }
//@Test(dependsOnMethods = {"login_Test"})
public void getCities_Test() {
	TestProcess.Run(testcasefolderPath,"APPLY","GetCities");
 }
@Test
public void login_Test() {
	TestProcess.Run(testcasefolderPath,"APPLY","Login");
 }
//@Test(dependsOnMethods = {"login_Test"})
public void postFileList_Test() {
	TestProcess.Run(testcasefolderPath,"APPLY","PostFileList");
 }
}
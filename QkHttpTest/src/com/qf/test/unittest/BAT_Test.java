package com.qf.test.unittest; 
import org.testng.annotations.Test; 
import com.qf.test.unittest.base.BaseParpare; 
 import com.qf.test.bll.TestProcess; 
public class BAT_Test extends BaseParpare{ 
@Test
public void getWithArgs_Test() {
	TestProcess.Run(testcasefolderPath,"BAT","GetWithArgs");
 }
@Test
public void postwithBody_Test() {
	TestProcess.Run(testcasefolderPath,"BAT","PostwithBody");
 }
@Test
public void postwithFiles_Test() {
	TestProcess.Run(testcasefolderPath,"BAT","PostwithFiles");
 }
@Test(dependsOnMethods = {"postwithImages_Test","getWithArgs_Test"})
public void postwithForm_Test() {
	TestProcess.Run(testcasefolderPath,"BAT","PostwithForm");
 }
@Test
public void postwithImages_Test() {
	TestProcess.Run(testcasefolderPath,"BAT","PostwithImages");
 }
}
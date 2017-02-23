package com.qf.test.unittest; 
import org.testng.annotations.Test; 

import com.qf.test.unittest.base.BaseParpare; 
import com.qf.test.bll.TestProcess;
public class DR_Test extends BaseParpare{ 
//@Test
public void getLoanStatus_Test() {
	TestProcess.Run(testcasefolderPath,"DR","GetLoanStatus");
 }
}
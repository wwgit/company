package com.loan.autotest.testcase.tester01.testcase01; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class Testcase01Page_003_Testcase3_Test extends BaseParpare{ 
@Test 
 public void testcase3() { 
SuperAction.parseExcel("tester01","Testcase01","003_Testcase3",seleniumUtil);
 }
}
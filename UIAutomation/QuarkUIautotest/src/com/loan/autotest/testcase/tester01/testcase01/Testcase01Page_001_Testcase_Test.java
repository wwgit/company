package com.loan.autotest.testcase.tester01.testcase01; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class Testcase01Page_001_Testcase_Test extends BaseParpare{ 
@Test 
 public void testcase() { 
SuperAction.parseExcel("tester01","Testcase01","001_Testcase",seleniumUtil);
 }
}
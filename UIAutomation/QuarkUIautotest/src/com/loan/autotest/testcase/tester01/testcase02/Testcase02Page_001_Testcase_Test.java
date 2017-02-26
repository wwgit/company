package com.loan.autotest.testcase.tester01.testcase02; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class Testcase02Page_001_Testcase_Test extends BaseParpare{ 
@Test 
 public void testcase() { 
SuperAction.parseExcel("tester01","Testcase02","001_Testcase",seleniumUtil);
 }
}
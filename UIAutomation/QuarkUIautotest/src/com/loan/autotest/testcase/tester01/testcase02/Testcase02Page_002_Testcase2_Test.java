package com.loan.autotest.testcase.tester01.testcase02; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class Testcase02Page_002_Testcase2_Test extends BaseParpare{ 
@Test 
 public void testcase2() { 
SuperAction.parseExcel("tester01","Testcase02","002_Testcase2",seleniumUtil);
 }
}
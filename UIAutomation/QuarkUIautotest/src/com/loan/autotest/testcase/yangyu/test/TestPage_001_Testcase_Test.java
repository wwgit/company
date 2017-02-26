package com.loan.autotest.testcase.yangyu.test; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class TestPage_001_Testcase_Test extends BaseParpare{ 
@Test 
 public void testcase() { 
SuperAction.parseExcel("yangyu","Test","001_Testcase",seleniumUtil);
 }
}
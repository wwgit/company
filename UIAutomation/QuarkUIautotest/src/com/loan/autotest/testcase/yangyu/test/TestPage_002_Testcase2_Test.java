package com.loan.autotest.testcase.yangyu.test; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class TestPage_002_Testcase2_Test extends BaseParpare{ 
@Test 
 public void testcase2() { 
SuperAction.parseExcel("yangyu","Test","002_Testcase2",seleniumUtil);
 }
}
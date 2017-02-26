package com.loan.autotest.testcase.yangyu.test; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class TestPage_003_Testcase3_Test extends BaseParpare{ 
@Test 
 public void testcase3() { 
SuperAction.parseExcel("yangyu","Test","003_Testcase3",seleniumUtil);
 }
}
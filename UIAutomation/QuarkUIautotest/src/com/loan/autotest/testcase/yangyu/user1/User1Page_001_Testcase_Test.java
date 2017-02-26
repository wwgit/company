package com.loan.autotest.testcase.yangyu.user1; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class User1Page_001_Testcase_Test extends BaseParpare{ 
@Test 
 public void testcase() { 
SuperAction.parseExcel("yangyu","User1","001_Testcase",seleniumUtil);
 }
}
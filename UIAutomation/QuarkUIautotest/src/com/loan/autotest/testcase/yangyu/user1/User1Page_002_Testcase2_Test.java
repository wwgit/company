package com.loan.autotest.testcase.yangyu.user1; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class User1Page_002_Testcase2_Test extends BaseParpare{ 
@Test 
 public void testcase2() { 
SuperAction.parseExcel("yangyu","User1","002_Testcase2",seleniumUtil);
 }
}
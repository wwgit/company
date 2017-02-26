package com.loan.autotest.testcase.yangyu.user1; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class User1Page_003_Testcase3_Test extends BaseParpare{ 
@Test 
 public void testcase3() { 
SuperAction.parseExcel("yangyu","User1","003_Testcase3",seleniumUtil);
 }
}
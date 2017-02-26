package com.loan.autotest.testcase.lichen.testlogin; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class TestloginPage_001_LoginSuc_Test extends BaseParpare{ 
@Test 
 public void loginSuc() { 
SuperAction.parseExcel("lichen","Testlogin","001_LoginSuc",seleniumUtil);
 }
}
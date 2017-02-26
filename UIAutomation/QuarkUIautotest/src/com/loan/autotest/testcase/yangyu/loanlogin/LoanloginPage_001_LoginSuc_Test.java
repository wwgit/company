package com.loan.autotest.testcase.yangyu.loanlogin; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class LoanloginPage_001_LoginSuc_Test extends BaseParpare{ 
@Test 
 public void loginSuc() { 
SuperAction.parseExcel("yangyu","Loanlogin","001_LoginSuc",seleniumUtil);
 }
}
package com.loan.autotest.testcase.yangyu.loanback; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class LoanbackPage_002_LoanProductTypeSet_Test extends BaseParpare{ 
@Test 
 public void loanProductTypeSet() { 
SuperAction.parseExcel("yangyu","Loanback","002_LoanProductTypeSet",seleniumUtil);
 }
}
package com.loan.autotest.testcase.yangyu.loanback; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class LoanbackPage_001_SysDayEndSet_Test extends BaseParpare{ 
@Test 
 public void sysDayEndSet() { 
SuperAction.parseExcel("yangyu","Loanback","001_SysDayEndSet",seleniumUtil);
 }
}
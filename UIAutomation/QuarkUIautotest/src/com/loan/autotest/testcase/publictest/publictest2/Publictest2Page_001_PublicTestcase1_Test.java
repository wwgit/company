package com.loan.autotest.testcase.publictest.publictest2; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class Publictest2Page_001_PublicTestcase1_Test extends BaseParpare{ 
@Test 
 public void publicTestcase1() { 
SuperAction.parseExcel("publictest","Publictest2","001_PublicTestcase1",seleniumUtil);
 }
}
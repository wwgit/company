package com.loan.autotest.testcase.publictest.publictest2; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class Publictest2Page_002_PublicTestcase2_Test extends BaseParpare{ 
@Test 
 public void publicTestcase2() { 
SuperAction.parseExcel("publictest","Publictest2","002_PublicTestcase2",seleniumUtil);
 }
}
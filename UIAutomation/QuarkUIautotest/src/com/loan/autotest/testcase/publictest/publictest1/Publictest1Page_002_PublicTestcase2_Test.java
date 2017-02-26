package com.loan.autotest.testcase.publictest.publictest1; 
import org.testng.annotations.Test; 
import com.loan.autotest.base.BaseParpare; 
 import com.loan.autotest.utils.SuperAction; 
public class Publictest1Page_002_PublicTestcase2_Test extends BaseParpare{ 
@Test 
 public void publicTestcase2() { 
SuperAction.parseExcel("publictest","Publictest1","002_PublicTestcase2",seleniumUtil);
 }
}
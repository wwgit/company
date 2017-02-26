package com.loan.autotest.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;


/**
 * 
 * @author yuyang
 * @Description 把Selenium操作的变成关键字操作
 *
 */
public class SuperAction {
	public static Logger logger = Logger.getLogger(SuperAction.class.getName());
	//static  String pageFilePath = "res/page/PageElements.xlsx";
	static String pageFileDir = "res/page/";
	public static Alert a = null;

	/**
	 * 
	 * @param locateWay 定位方式
	 * @param locateValue 定位的方式的具体值
	 * @return 定位的方式（By）
	 */
	public static By getLocateWay(String locateWay,String locateValue){
		 By elementLocator=null;
		 	if(locateWay.equalsIgnoreCase("xpath")){
		 		elementLocator=By.xpath(locateValue);
		 	}
		 	else if(locateWay.equalsIgnoreCase("className")){
		 		elementLocator=By.className(locateValue);
		 	}
		 	else if(locateWay.equalsIgnoreCase("id")){
		 		elementLocator=By.id(locateValue);
		 	}
		 	else	if(locateWay.equalsIgnoreCase("linktext")){
		 		elementLocator=By.linkText(locateValue);
		 	}
		 	else	if(locateWay.equalsIgnoreCase("name")){
		 		elementLocator=By.name(locateValue);
		 	}
		 	else	if(locateWay.equalsIgnoreCase("css")){
		 		elementLocator=By.cssSelector(locateValue);
		 	}
		 	else	if(locateWay.equalsIgnoreCase("tagname")){
		 		elementLocator=By.tagName(locateValue);
		 	}
		 	else{
		 		Assert.fail("你选择的定位方式：["+locateWay+"] 不被支持!");
		 	}
		 	return elementLocator;
		 }
	

	/**
	 * 
	 * @param sheet - 测试用例表中的sheet
	 * @param rowIndex - 测试用例表中的行index
	 * @param locateColumnIndex - 测试用例表中的定位列的index
	 * @return 从page表中 返回定位方式和定位值
	 * @Description 根据testcase中的元素定位列，去取得page页中的 定位方式和定位值
	 */
	public static String[] getPageElementLocator(Sheet sheet,int rowIndex,int locateColumnIndex,String pageName){

			XSSFWorkbook pageBook = null;
			//定位方式
			String elementLocatorWay = null;
			//定位值
			String elementLocatorValue = null;
			//sheet表
			Sheet pageSheet = null;
			//page excel路径
			String pageFilePath = pageFileDir+pageName+".xlsx";
			//获取定位列的值
			String locator = sheet.getRow(rowIndex).getCell(locateColumnIndex).getStringCellValue();
			//用.分割开元素定位值
			String locatorSplit[] = locator.split("\\.");
		try {
			pageBook = new XSSFWorkbook(new FileInputStream(new File(pageFilePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		  pageSheet =  pageBook.getSheetAt(0); //取得第一个sheet
		 int pageRowNum =  pageSheet.getPhysicalNumberOfRows();//获得这个sheet的实际有效行数
		 for (int j = 0; j < pageRowNum; j++) {
			 //如果获取到的别名和指定的别名相同，就存储当前行的定位值和定位方式
			if(pageSheet.getRow(j).getCell(0).getStringCellValue().equalsIgnoreCase(locatorSplit[1])){
				 elementLocatorWay = pageSheet.getRow(j).getCell(1).getStringCellValue();
				 elementLocatorValue = pageSheet.getRow(j).getCell(2).getStringCellValue();
				break;
			}
		}
		return new String[]{elementLocatorWay,elementLocatorValue};
	
	}
	/**
	 * @param testerName
	 *            excel文件的名字
	 * @param founction
	 *            excel文件的名字
	 * @param caseName
	 *            excel中sheet的名字
	 * @param seleniumUtil
	 *            引用seleniumUtil
	 * @Description 读取excel中每个sheet的操作步骤，进而生成测试用例
	 * */
	public static void parseExcel(String testerName,String founction, String caseName, SeleniumUtil seleniumUtil) {
		FileInputStream filePath = null;
		XSSFWorkbook workbook = null;
		String locateSplit[]  = null;//页面sheet中的定位方式和定位值拆解
		String locator = null;//用例页面的定位列
		
		//项目路径
		String file = "res/testcase/" + testerName + "/testcase/" + founction + ".xlsx";
		try {
			filePath = new FileInputStream(file);// 读取功能模块
		} catch (FileNotFoundException e) {
			logger.error("文件：" + file + "不存在");
			Assert.fail("文件：" + file + "不存在");
		}
		try {
			workbook = new XSSFWorkbook(filePath);
		} catch (IOException e) {
			logger.error("IO异常");
			Assert.fail("IO异常");
		}
		/**取得指定的case名字*/
		Sheet sheet = workbook.getSheet(caseName);
		/**获得的实际行数*/
		int rows = sheet.getPhysicalNumberOfRows(); 
		/** excel中的测试数据*/
		String testData = null;
		//获取首行的单元格数
		int cellsNumInOneRow = sheet.getRow(0).getPhysicalNumberOfCells();
		//声明一个数组存储列值的角标
		String column[] = new String[cellsNumInOneRow];
		//声明一个迭代器
		Iterator<Cell> cell = sheet.getRow(0).iterator();
		int ii =0;
		while(cell.hasNext()){
			column[ii]= String.valueOf(cell.next()); 
			ii++;
		}
		//定义动作列的角标
		int actionColumnIndex =0;
		//定义元素定位列的角标
		int locateColumnIndex = 0;
		//定义测试数据列的角标
		int testDataColumnIndex = 0;
		//动态获取这几个关键列所在位置
		 for (int i = 0; i < column.length; i++) {
			 if(column[i].equals("动作")){
				 actionColumnIndex = i;
			 }
			 if(column[i].equals("元素定位")){
				 locateColumnIndex = i;
			 }
			 if(column[i].equals("测试数据")){
				 testDataColumnIndex = i;
			 }
			
		}

		// 循环每行的操作，根据switch来判断每行的操作是什么，然后转换成具体的代码，从第二行开始循环，因为第一行是列的说明数据。
		logger.info("actual rows:" + rows);
		for (int i = 1; i < rows; i++) {
			logger.info("正在解析excel:["+founction+".xlsx]中的sheet(用例)：["+caseName+"]的第"+i+"行步骤...");
			String action = sheet.getRow(i).getCell(actionColumnIndex).getStringCellValue();
			Row row = sheet.getRow(i);
			if (row != null) {
				switch (action) {
				case "打开链接":
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					seleniumUtil.get(testData);
					break;
			
				case "输入":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue(); //测试数据
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的元素定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]); //找到定位方式、定位值
					seleniumUtil.type(getLocateWay(locateSplit[0], locateSplit[1]), testData);
					break;
					
				case "点击":
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.click(getLocateWay(locateSplit[0], locateSplit[1]));
					break;
					
				case "暂停":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					seleniumUtil.pause(Integer.parseInt(testData));
					break;
					
				case "等待元素":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.waitForElementToLoad(Integer.parseInt(testData), getLocateWay(locateSplit[0], locateSplit[1]));
					break;
					
				case "查找元素(尝试3次)":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.FindElementUtil3TimesTry(Integer.parseInt(testData), getLocateWay(locateSplit[0], locateSplit[1]));
					break;
					
				case "清除":
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.clear(getLocateWay(locateSplit[0], locateSplit[1]));
					break;
					
				case "进入iFrame":
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.switchFrame(getLocateWay(locateSplit[0], locateSplit[1]));
					break;
					
				case "跳出iFrame":
					seleniumUtil.outFrame();
					break;
					
				case "选择下拉列表 - Text":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.selectByText(getLocateWay(locateSplit[0], locateSplit[1]), testData);
					break;
					
				case "选择下拉列表 - Index":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.selectByIndex(getLocateWay(locateSplit[0], locateSplit[1]), Integer.parseInt(testData));
					break;
					
				case "选择下拉列表 - Value":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.selectByValue(getLocateWay(locateSplit[0], locateSplit[1]),testData );
					break;						
					
				case "检查文本 - 属性":
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					String[] Datas = testData.split(",");
					seleniumUtil.isTextCorrect(seleniumUtil.getAttributeText(getLocateWay(locateSplit[0], locateSplit[1]), Datas[0]),Datas[1]);
					break;
					
				case "获得网页标题":
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					seleniumUtil.isTextCorrect(seleniumUtil.getTitle(),testData);
					break;
					
			
				case "页面的URL是否正确":
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					seleniumUtil.isTextCorrect(seleniumUtil.getPageURL(), testData);
					break;
					
				case "检查文本":
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.isTextCorrect(seleniumUtil.getText(getLocateWay(locateSplit[0], locateSplit[1])), testData);
					break;
					
				case "进入Tab":
					//需要改进，第二个窗口的driver问题
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.switchNewWindow(getLocateWay(locateSplit[0], locateSplit[1]));
					break;
					
				case "跳出Tab":
					seleniumUtil.backToOriginalWindow();
					break;
					
				case "接受alert弹窗":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					a = seleniumUtil.switchToPromptedAlertAfterWait(Long.parseLong(testData));
					a.accept();
					break;
					
				case "取消alert弹窗":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					a = seleniumUtil.switchToPromptedAlertAfterWait(Long.parseLong(testData));
					a.dismiss();
					break;	
					
				case "执行JS点击":
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					seleniumUtil.executeJS("arguments[0].click();", seleniumUtil.findElementBy(getLocateWay(locateSplit[0], locateSplit[1])));
					break;	
					
				case "刷新页面":
					seleniumUtil.refresh();
					break;	
					
				case "前进页面":
					seleniumUtil.back();
					break;
					
				case "后退页面":
					seleniumUtil.forward();
					break;
					
				case "上传文件":
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					String uploadValues[] = testData.split(",");
					seleniumUtil.handleUpload(uploadValues[0], new File(uploadValues[1]));
					break;
					
				case "元素被启用":
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					if(seleniumUtil.isEnabled(getLocateWay(locateSplit[0], locateSplit[1]))){
						logger.info(getLocateWay(locateSplit[0], locateSplit[1])+"元素被启用");
					}else{
						Assert.fail(getLocateWay(locateSplit[0], locateSplit[1])+"：没有被启用");
					}
					break;
					
				case "元素未启用":
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					if(seleniumUtil.isEnabled(getLocateWay(locateSplit[0], locateSplit[1]))){
						Assert.fail(getLocateWay(locateSplit[0], locateSplit[1])+"元素被启用");
					}else{
						logger.info(getLocateWay(locateSplit[0], locateSplit[1])+"：没有被启用");
					}
					break;
					
				case "验证首页菜单栏文本":
					locator = sheet.getRow(i).getCell(locateColumnIndex).getStringCellValue();//获取步骤中的定位
					//locator.split("\\.")[0]分离出页面名称，比如HomePage.我的单据，提取出HomePage
					locateSplit = getPageElementLocator(sheet, i, locateColumnIndex,locator.split("\\.")[0]);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					String menus[] = testData.split("，");
					for (int i1 = 0; i1 < menus.length; i1++) {
						seleniumUtil.isTextCorrect(seleniumUtil.findElementsBy(getLocateWay(locateSplit[0], locateSplit[1])).get(i1).getText().trim().toLowerCase(), menus[i1].toLowerCase());
					}
					
					break;
					
				case "验证是否重复登录":
					if (seleniumUtil.getTitle().equals("登进确认")) {
						logger.info("需要登录确认，开始进行登录操作");
						seleniumUtil.click(getLocateWay("className","yesBtnClass"));//登进确认按钮元素直接写死						
					}					
					break;
					
				//有时间再优化代码	
				case "要素用途配置":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					for (int i1 = 1 ; i1 <= Integer.parseInt(testData) ; i1 ++){
						//定义当页执行数
						int number;
						//定义当前页码	
						int page_number;			
							//对页码和执行数赋值
							if (i1%10 != 0){
								number = i1%10;
								page_number = (i1/10+1);
							}else{
								number = 10;
								page_number =i1/10;
							}
						logger.info("正在运行： "+ i1 + " 次操作，当前是第：  "+ page_number +" 页的第： " + number +" 条数据。" );	
						//选择页码
						seleniumUtil.selectByText(getLocateWay("ID","pageNo"), Integer.toString(page_number));
						//点击要素配置
						seleniumUtil.click(getLocateWay("xpath","/html/body/div[2]/div[1]/div[2]/table/tbody/tr[" + (number+1)  +"]/td[7]/a[4]"));
						seleniumUtil.waitForElementToLoad(3, getLocateWay("xpath","/html/body/div[2]/div[3]/form/div[4]/table/tbody/tr/td/div[1]/div[2]"));
						seleniumUtil.click(getLocateWay("xpath","/html/body/div[2]/div[3]/form/div[4]/table/tbody/tr/td/div[1]/div[2]"));
						//选择页码
						seleniumUtil.selectByText(getLocateWay("ID","pageNo"), Integer.toString(page_number));
						//点击用途配置
						seleniumUtil.click(getLocateWay("xpath","/html/body/div[2]/div[1]/div[2]/table/tbody/tr[" + (number+1) +"]/td[7]/a[5]"));
						seleniumUtil.waitForElementToLoad(3, getLocateWay("xpath","/html/body/div[2]/div[5]/form/div/table/tbody/tr/td/div[1]/div[2]"));
						seleniumUtil.click(getLocateWay("xpath","/html/body/div[2]/div[5]/form/div/table/tbody/tr/td/div[1]/div[2]"));
						seleniumUtil.pause(1);						
					}
					break;
											
				case "服务器切日":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					String strUpDate[] = testData.split(",");
					SshUtil.updateDate(testerName,strUpDate[0],strUpDate[1]);
					break;
				
				case "服务器启动校验":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
					String HopeTitle = "系统登录（小贷通 V3.5.22）";
					seleniumUtil.get(testData);
					for (int i1 = 0 ; i1<5 ;i1 ++)
					if(seleniumUtil.getTitle().equals(HopeTitle)){
						logger.info("ok");
						break;
					}else{
						logger.info("again");
						seleniumUtil.pause(15);
					}
					//String strUpDate[] = testData.split(",");
					//SshUtil.updateDate(strUpDate[0],strUpDate[1]);
					break;
				
				//根据需求 修改查询SQL次数和等待时间，未参数这2个值
				case "数据库欠款批量校验":
					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();				
					
					String sql ="Select TYPENAME from parm_oa_type where id ='FA' and CHG_DATE='1' and `NAME`='欠款情况自动处理'";
					String queryContent="TYPENAME";//SQL字段名	
					String sqlQuaryTemp = null; //sql的查询结果;//SQL结果值
					int tempCount = 10;
					//查询SQL的次数 ： i1
					for (int i1=0; i1 < tempCount ; i1 ++){
						sqlQuaryTemp =JdbcUtil.querySql(testerName,sql,queryContent);
						String strSqlResult ="当前数据库查询欠款批量执行时间是：["+ sqlQuaryTemp +"],期望时间是:["+ testData +"]" ;
						//非空判断和对比结果是否相同
						if (sqlQuaryTemp == null || sqlQuaryTemp ==""){
							logger.error("SQL语句结果为NULL，无法进行批量校验，请检查SQL语句是否正确");
							Assert.fail("SQL语句结果为NULL，无法进行批量校验，请检查SQL语句是否正确");
						}else if (sqlQuaryTemp.equals(testData)){
							logger.info(strSqlResult + "二者结果相同。");
							break;
						}else if (i1 == tempCount-1){
							logger.error("多次SQL查询校验后,二者结果依然不同。故欠款批量操作【失败】，请查找原因。");
							Assert.fail("多次SQL查询校验后,二者结果依然不同。故欠款批量操作【失败】，请查找原因。");	
						}else{
							logger.info(strSqlResult + "二者结果不同，将等待15秒后继续查询比较。");
							seleniumUtil.pause(20);	
						}					
					}	
					break;

					//秀华临时专用 给URL传日期2017-02-16 待重构
//				case "输出日期":
//					//先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
//					sheet.getRow(i).getCell(testDataColumnIndex).setCellType(Cell.CELL_TYPE_STRING);
//					testData = sheet.getRow(i).getCell(testDataColumnIndex).getStringCellValue();
//					//获取起始日和截止日	
//					String targetDates[] = testData.split(",");										
//					int daysDifference = 0;
//					//计算差额
//					try{						
//						daysDifference = DateUtils.daysDifference(targetDates[0], targetDates[1]);
//						logger.info("初始日期: " + targetDates[0] + "; 截止日期: " + targetDates[1]+ "; 差额日是: " + daysDifference);							
//					}catch (Exception e) {
//						logger.error("日期格式获取失败，请查看日期格式是否正确");
//					}	
//					String baseurl = "http://";
//					String tmpurl = baseurl + targetDates[0] + "cde\"" ;								
//					seleniumUtil.get(targetDates[0]);
//					logger.info("已完成地址： " + tmpurl );
//					for (int i2 = 0; i2 < daysDifference; i2++) {
//						targetDates[0] = DateUtils.daysAdd(targetDates[0]);
//						tmpurl = baseurl + targetDates[0] + "cde\"" ;
//						seleniumUtil.get(tmpurl);	
//						logger.info("已完成地址：  " + tmpurl );
//					}
//					break;
					
					
					default:
						logger.error("你输入的操作：["+action+"]不被支持，请自行添加");
						Assert.fail("你输入的操作：["+action+"]不被支持，请自行添加");
					
				}
			}
		}
	}

}

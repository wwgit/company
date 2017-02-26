package com.loan.autotest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import jxl.read.biff.BiffException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.ITestContext;

/**
 * 
 * @author yuyang
 * @Description 自动生成测试代码的工具类,生成所有模块的测试类
 *
 */
public class TestCaseFactoryForAll {

	public static void main(String[] args) {
		
		File sourceFile = null;
		String sheetName = null;
		
		//功能文件夹名称
		String fileName = null;		
		int sheetNum = 0;
		@SuppressWarnings("resource")
		
		//从控制台可以输入
		Scanner s = new Scanner(System.in); 
		System.out.println("请输入EXCEL文件所在的文件夹名称（不要按回车键，输入完成之后请再按回车键）："); 
		fileName = s.nextLine();// 输入文件夹名字
		final String caseFolder = "src/com/loan/autotest/testcase/"+ fileName +"/";
		System.out.println("caseFolder:" + caseFolder);
		for (int i = 0; i < getFunctionNum(fileName); i++) { // 第一层循环 取得模块的个数
			try {

				String functionName = getFunctionName(fileName,i);// 获得每轮循环的 模块名
				System.out.println("functionName:" + functionName);
				functionName = functionName.replaceFirst(
						functionName.substring(0, 1),
						functionName.substring(0, 1).toLowerCase());
				// 如果包名不存在，就新建
				File functionPackage = new File(caseFolder + "/" + functionName);
				if (functionPackage.exists()) {
					System.out.println(functionName + "包已经存在，自动跳过！");
					System.out.println("正在生成用例到" + functionName + "包下，请稍等...");
				} else {
					boolean ret = functionPackage.mkdirs();
					System.out.println(functionName + "包已创建！" + ret);
					System.out.println("正在生成用例到" + functionName + "包下，请稍等...");
				}

				for (int j = 0; j < getSheetNum(getExcelRelativePath(fileName,i)); j++) { // 第二层循环，根据传入的模块文件路径获得
																					// 模块中sheet数量
																					// 也就是用例个数
					if (j == getSheetNum(getExcelRelativePath(fileName,i)) - 1) {

						break;
					}
					try {
						sheetName = getSheetName(j + 1, getExcelRelativePath(fileName,i)); // 取得sheetName
																					// 只进行一次全局异常捕获
						sheetNum = getSheetNum(getExcelRelativePath(fileName,i));
					} catch (BiffException e1) {
						e1.printStackTrace();
					}
					
					String javaSrcName = functionName.replaceFirst(functionName.substring(
							0, 1), functionName.substring(0, 1)
							.toUpperCase()) + "Page_" + sheetName
					+ "_Test.java";
					
					String caseDir = caseFolder
							+ functionName.replaceFirst(functionName.substring(
									0, 1), functionName.substring(0, 1)
									.toLowerCase());
					if(new File(caseDir).exists() == false) {
						
					}
					
					String casePath = caseDir
							+ File.separator
							+ javaSrcName;
					
					System.out.println(casePath);
					sourceFile = new File(casePath);// 创建测试用例源码，指定存放路径
					System.out.println(sourceFile);
					FileWriter writer = new FileWriter(sourceFile);

					// 生成测试用例代码的头文件
					writer.write("package com.loan.autotest.testcase."
							+ fileName + "."
							+ functionName
							+ "; \n"
							+ "import org.testng.annotations.Test; \n"
							+ "import com.loan.autotest.base.BaseParpare; \n "
							+ "import com.loan.autotest.utils.SuperAction; \n"
							+ "public class "
							+ functionName.replaceFirst(functionName.substring(
									0, 1), functionName.substring(0, 1)
									.toUpperCase()) + "Page_" + sheetName
							+ "_Test extends BaseParpare{ \n");

					// @Test的主体部分，也就是测试用例的方法
					String firstLetter = sheetName.substring(
							sheetName.indexOf("_") + 1).substring(0, 1);
					String others = sheetName.substring(
							sheetName.indexOf("_") + 1).substring(1);
					String function = firstLetter.toLowerCase() + others;
					writer.write("@Test \n"
							+ " public void"
							+ " "
							+ function
							+ "() { \n"
							+ "SuperAction.parseExcel(\""
							+ fileName + "\",\""
							+ functionName.replaceFirst(functionName.substring(
									0, 1), functionName.substring(0, 1)
									.toUpperCase()) + "\",\"" + sheetName
							+ "\",seleniumUtil);\n" + " }\n");

					// 代码结尾大括号
					writer.write("}");
					writer.close();
				}
			} catch (IOException e) {
				Assert.fail("IO异常", e);
			}
			System.out.println("模块[" + getFunctionName(fileName,i) + "] 的用例已经生成完毕，共计："
					+ (sheetNum - 1) + "条，请到" + caseFolder
					+ getFunctionName(fileName,i).toLowerCase() + "路径下查阅！");
		}

	}

/**
	 * 获得当前路径下模块个数
	 * 
	 * @return 得到模块的个数
	 */
	public static int getFunctionNum(String fileName) {
		String path = "res/testcase/" + fileName + "/testcase";
		File file = new File(path);
		File[] array = file.listFiles();
		return array.length;
	}

	/**
	 * 获得模块名字 也就是excel 表名
	 * 
	 * @param 循环模块名称的角标
	 * @return 得到对应index的模块名字
	 */
	public static String getFunctionName(String fileName,int index) {
		String excelCasePath = "res/testcase/" + fileName + "/testcase";
		String functionName = "";
		// get file list where the path has
		File file = new File(excelCasePath);

		// get the folder list
		File[] array = file.listFiles();
		if (array[index].isFile()) {
			functionName = array[index].getName().substring(0,
					array[index].getName().lastIndexOf("."));
		}

		return functionName;
	}

	/**
	 * 获得excel的相对路径
	 * 
	 * @param 循环模块名称的角标
	 * @return 得到对应index的模块名字
	 */
	public static String getExcelRelativePath(String fileName,int index) {
		String path = "res/testcase/" + fileName+ "/testcase";
		System.out.println("relative path of excel:" + path);
		String functionName = "";
		// get file list where the path has
		File file = new File(path);
		// get the folder list
		File[] array = file.listFiles();
		functionName = array[index].getPath();
		return functionName;
	}

	/**
	 * 获得当前excel的sheet数量 - 每个模块的用例数
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 获得excel的sheet数量
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int getSheetNum(String filePath)
			throws FileNotFoundException, IOException {
		int casesNum = 0;
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(
				filePath)));
		casesNum = workbook.getNumberOfSheets();

		return casesNum;
	}

	/**
	 * 
	 * @param sheetIndex
	 *            sheet的位置
	 * @param filePath
	 *            excel文件路径相对的
	 * @return 返回sheet的名字
	 * @throws BiffException
	 * @throws IOException
	 */
	public static String getSheetName(int sheetIndex, String filePath)
			throws BiffException, IOException {
		String casesName = "";
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
		casesName = workbook.getSheetName(sheetIndex);

		return casesName;

	}

}

package com.qf.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.DependencyMap;

import com.qf.test.dal.RWTxtFileWithJackson;
import com.qf.test.dal.RWTxtFileWithBuffer;
import com.qf.test.entity.Dependence;
import com.qf.test.entity.TestCase;


/**
 * 
 * @author xy-incito-wy
 * @Description 自动生成测试代码的工具类,生成所有模块的测试类
 *
 */
public class TestCaseFactoryForModule {
	static String caseFolder = "test\\TestCases";
	

	public static void main(String[] args) throws Exception {

		final String casePageFolder = "src/com/qf/test/unittest";
		String moduleName = null;
		File sourceFile = null;

		@SuppressWarnings("resource")
		// 从控制台可以输入
		Scanner s = new Scanner(System.in);
		System.out.println("请输入模块名称（不要按回车键，输入完成之后请再按回车键）：");
		moduleName = s.nextLine();// 输入模块名字

		moduleName = moduleName.replaceFirst(moduleName.substring(0, 1),
				moduleName.substring(0, 1).toLowerCase());
		// 如果包名不存在，就新建
		// File functionPackage = new File(caseFolder + "/" + moduleName);
		File functionPackage = new File(casePageFolder);
		if (functionPackage.exists()) {
			System.out.println(functionPackage + "包已经存在，自动跳过！");
			System.out.println("正在生成用例到" + moduleName + "包下，请稍等...");
		} else {
			functionPackage.mkdir();
			System.out.println(functionPackage + "包已创建！");
			System.out.println("正在生成用例到" + moduleName + "包下，请稍等...");
		}

		String functionName=null;
		sourceFile = new File(casePageFolder + File.separator
				+ moduleName.toUpperCase() + "_Test.java");// 创建测试用例源码，指定存放路径
		try {
			FileWriter writer = new FileWriter(sourceFile);
			// 生成测试用例代码的头文件
			writer.write("package com.qf.test.unittest; \n"
					+ "import org.testng.annotations.Test; \n"
					+ "import com.qf.test.unittest.base.BaseParpare; \n "
					+ "import com.qf.test.bll.TestProcess; \n"

					+ "public class " + moduleName.toUpperCase()
					+ "_Test extends BaseParpare{ \n");

			for (int i = 0; i < getFunctionNum(moduleName); i++) { // 第一层循环
																	// 取得模块的个数

				functionName = getFunctionName(moduleName, i);// 获得每轮循环的
																		// 模块名

				TestCase tc = RWTxtFileWithJackson.Read(caseFolder + "\\"
						+ moduleName + "\\" + functionName + ".csv");

				Dependence[] dep = tc.getDependencies();

				functionName = functionName.replaceFirst(
						functionName.substring(0, 1),
						functionName.substring(0, 1).toLowerCase());

				// @Test(dependsOnMethods = {"PostwithImages"})

				String testcase = null;
				String testcases = "";
				if (dep != null) {
					for (int k = 0; k < dep.length; k++) {
						testcase = dep[k].getTestcase();
						testcase = testcase.replaceFirst(
								testcase.substring(0, 1),
								testcase.substring(0, 1).toLowerCase())
								+ "_Test";
						if (testcases != "")
							testcases = testcases + "\",\"" + testcase;
						else
							testcases = testcase;
					}
					testcases = "(dependsOnMethods = {\"" + testcases + "\"})";

				}

				// @Test的主体部分，也就是测试用例的方法

				writer.write("@Test"
						+ testcases
						+ "\npublic void"
						+ " "
						+ functionName
						+ "_Test() {\n"
						+ "\tTestProcess.Run(testcasefolderPath,"
						+ "\""
						+ moduleName.toUpperCase()
						+ "\",\""
						+ functionName.replaceFirst(functionName
								.substring(0, 1), functionName.substring(0, 1)
								.toUpperCase()) + "\");\n" + " }\n");
			}

			// 代码结尾大括号
			writer.write("}");
			writer.close();
		} catch (IOException ex) {
			System.out.println("Error: " +functionName +"\n" + ex.getMessage());
		}

		System.out.println("模块[" + moduleName + "] 的用例已经生成完毕，共计："
				+ getFunctionNum(moduleName) + "条，请到" + casePageFolder + "/"
				+ moduleName.toLowerCase() + "路径下查阅！");

	}

	/**
	 * 获得当前路径下模块个数
	 * 
	 * @return 得到模块的个数
	 */
	public static int getFunctionNum(String moduleName) {
		String path = caseFolder+"\\" + moduleName;
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
	public static String getFunctionName(String moduleName, int index) {
		String path = caseFolder+"\\" + moduleName;
		String functionName = "";
		// get file list where the path has
		File file = new File(path);

		// get the folder list
		File[] array = file.listFiles();
		if (array[index].isFile()) {
			functionName = array[index].getName().substring(0,
					array[index].getName().lastIndexOf("."));
		}
		return functionName;
	}

}

package com.loan.autotest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshUtil {
	public static Logger logger = Logger.getLogger(SuperAction.class.getName());

	/**
	 * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
	 * 
	 * @param host
	 *            主机名
	 * @param user
	 *            用户名
	 * @param psw
	 *            密码
	 * @param port
	 *            端口
	 * @param command
	 *            命令
	 * @return
	 */
	public static String exec(String host, String user, String psw, int port, String command) {
		String result = "";
		Session session = null;
		ChannelExec openChannel = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
			session.setConfig(config);
			session.setPassword(psw);
			session.connect();
			openChannel = (ChannelExec) session.openChannel("exec");
			openChannel.setCommand(command);
			// int exitStatus = openChannel.getExitStatus();
			// System.out.println(exitStatus);
			openChannel.connect();
			InputStream in = openChannel.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String buf = null;
			while ((buf = reader.readLine()) != null) {
				result += new String(buf.getBytes("gbk"), "UTF-8") + "    <br>\r\n";
			}
		} catch (JSchException e) {
			result += e.getMessage();
		} catch (IOException e) {
			result += e.getMessage();
		} finally {
			if (openChannel != null && !openChannel.isClosed()) {
				openChannel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
		return result;
	}

	
	
	/**
	 * 服务器 关TOMCAT 切日后在开启TOMCAT(有空再优化下 )
	 * @param date
	 * 				日期格式:2017/01/01
	 * @param time
	 * 				时间格式:18:00
	 * */
	public static void updateDate(String testerName,String date,String time){	
		String sshConfgFilePath = "res/testcase/"+ testerName + "/SSH.properties";
		String ip = PropertiesDataProvider.getTestData(sshConfgFilePath, "ssh_ip");
		int port = Integer.parseInt(PropertiesDataProvider.getTestData(sshConfgFilePath, "ssh_port"));
		String user = PropertiesDataProvider.getTestData(sshConfgFilePath, "ssh_user");
		String pwd = PropertiesDataProvider.getTestData(sshConfgFilePath, "ssh_pwd");
		String tomcatpath = PropertiesDataProvider.getTestData(sshConfgFilePath, "ssh_tomcatpath");				
		String closetomacatcommand = "cmd /c \"start " + tomcatpath + "/bin/shutdown.bat\"";
		String updatedatecommand = "cmd /c \"date " + date+"\"";
		String updatetimecommand = "cmd /c \"time " + time+"\"";
		String satartomacatcommand = "cmd /c \"start " + tomcatpath + "/bin/startup.bat\"";
		String killCMD = "cmd /c \"taskkill /f /fi \"IMAGENAME eq cmd.exe\"\"";
		
		try {
//			//获取当前系统时间
//			time = time.substring(time.lastIndexOf("?") + 1, time.lastIndexOf("?") + 10).trim();				
			logger.info("系统执行的CMD命令是：\"["+closetomacatcommand +"]\"");
			SshUtil.exec(ip, user, pwd, port, closetomacatcommand);	
			Thread.sleep(9000);
			logger.info("系统执行的CMD命令是：\"["+updatedatecommand +"]\"");
			SshUtil.exec(ip, user, pwd, port, updatedatecommand);			
			Thread.sleep(1000);
			logger.info("系统执行的CMD命令是：\"["+updatetimecommand +"]\"");
			SshUtil.exec(ip, user, pwd, port, updatetimecommand);			
			Thread.sleep(1000);
			logger.info("系统执行的CMD命令是：\"["+satartomacatcommand +"]\"");
			SshUtil.exec(ip, user, pwd, port, satartomacatcommand);	
			SshUtil.exec(ip, user, pwd, port, killCMD);	
		} catch (Exception e) {
			logger.error("切日操作失败，请查找原因",e);
		}
	}


}
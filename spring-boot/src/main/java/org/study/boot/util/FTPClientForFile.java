package org.study.boot.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:ftp.properties")
public class FTPClientForFile {

	@Value("${ftp.url}")
	private String url; 
	@Value("${ftp.port}")
	private int port; 
	@Value("${ftp.username}")
	private String username; 
	@Value("${ftp.password}")
	private String password; 
	
	private FTPClient ftp = new FTPClient();
	public FTPClientForFile(){}

	public void login(){
		login(url,port, username, password);
	}
	public void login(String url, int port, String username, String password) {
		try {
			// 开启登录
			ftp.connect(url, port);
			ftp.login(username, password);
			// 返回的最后一个FTP答复代码整数
			int reply = ftp.getReplyCode();
			// 判断是否连接成功
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new RuntimeException("无法创建连接对象");
			}
		} catch (IOException e) {
			throw new RuntimeException("无法创建连接对象", e);
		}
	}

	public boolean uploadFile(String fileNamePath, String upFileSavePath) throws IOException {
		// 设置编码
		ftp.setControlEncoding("GBK");
		try {
			// 设置文件类型
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 改变工作目录
			ftp.changeWorkingDirectory(upFileSavePath);
			// 上传文件
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileNamePath));
			if (!ftp.storeFile(fileNamePath.substring(fileNamePath.lastIndexOf(File.separator) + 1), inputStream))
				return false;
			inputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				ftp.disconnect();
			}
		}
		return false;
	}

	public boolean logout() {
		try {
			return ftp.logout();
		} catch (IOException e) {
			throw new RuntimeException("无法关闭连接", e);
		}
	}
}

package org.study.boot.util;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Zip4jToFile {

	public static String createEncryptZip(String password, File file, String zipFileNamePath) throws ZipException {
		ZipParameters parameters = EncryptNeedPwd(password);
		ZipFile zipFile = new ZipFile(zipFileNamePath);
		if (file.isDirectory())
			zipFile.addFolder(file, parameters);
		else
			zipFile.addFile(file, parameters);
		return zipFileNamePath;
	}

	public static String createEncryptZip(String password, ArrayList<File> files, String zipFileNamePath)
			throws ZipException {

		ZipParameters parameters = EncryptNeedPwd(password);

		ZipFile zipFile = new ZipFile(zipFileNamePath);// 創建zip包，指定了zip路徑和zip名稱
		// 第一种 // 创建压缩包完成
		// zipFile.createZipFile(files, parameters);
		// 第二种
		for (File file : files) {
			if (file.isDirectory())
				zipFile.addFolder(file, parameters);
			else
				zipFile.addFile(file, parameters);
		}
		return zipFileNamePath;
	}

	private static ZipParameters EncryptNeedPwd(String password) {
		ZipParameters parameters = new ZipParameters();// 设置zip包的一些参数集合
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);// 压缩方式(默认值)
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);// 普通级别（参数很多）
		parameters.setEncryptFiles(true); // 是否设置密码（此处设置为：是）
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);// 加密级别
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);// 秘钥级别
		parameters.setPassword(password);// 设置密码
		return parameters;
	}

	// 不要解压密码的压缩方式
	public static String createEncryptZipNoPwd(ArrayList<File> files, String zipFileNamePath) throws ZipException {

		ZipFile zipFile = new ZipFile(zipFileNamePath);

		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

		for (File file : files) {
			zipFile.addFile(file, parameters);
		}
		return zipFileNamePath;
	}
	public static String createEncryptZipNoPwd(File file, String zipFileNamePath) throws ZipException {
		
		ZipFile zipFile = new ZipFile(zipFileNamePath);
		
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		
		zipFile.addFile(file, parameters);

		return zipFileNamePath;
	}

	public static void deEncryptZip(String srcfileName, String password, String targerfileName) throws ZipException {
		ZipFile zipFile = new ZipFile(srcfileName); // 根据路径取得需要解压的Zip文件
		if (zipFile.isEncrypted()) { // 判断文件是否加码
			zipFile.setPassword(password); // 密码为
		}
		zipFile.extractAll(targerfileName); // 压缩包文件解压路径
	}

}

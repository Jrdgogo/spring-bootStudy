package org.study.boot.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.study.boot.dao.UserDAO;
import org.study.boot.pojo.User;
import org.study.boot.util.HttpClientUtil;
import org.study.boot.util.Zip4jToFile;

import net.lingala.zip4j.exception.ZipException;


@RestController
public class StudyController {
    @Autowired
	private UserDAO userDao;
    
    
    @GetMapping("/getUserAll")
    public List<User> getUsers(HttpServletRequest request,@RequestParam("url")String url) throws ZipException{
    	String parentpath="C:/Users/Administrator/Desktop";
    	String[] filenames={"字段的多重解释.xls","矿机使用流程.doc","MyBatis-3-User-Guide-Simplified-Chinese.pdf","新建文件夹"};
    	ArrayList<File> files=new ArrayList<File>();
    	for(String name:filenames){
    		files.add(new File(parentpath+"/"+name));
    	}
    	String srcfileName="d:/zip/zip4j.zip";
    	Zip4jToFile.createEncryptZip("123", files,srcfileName );
    	
    	HttpClientUtil.loadUrlContent(url+"/downFile?srcfileName="+srcfileName);
    	return userDao.findAll();
    }
    @GetMapping("/downFile")
    public List<User> downFile(@RequestParam("srcfileName")String srcfileName) throws ZipException{
    	
    	Zip4jToFile.deEncryptZip(srcfileName, "123", "d:/zip/down");
    	return userDao.findAll();
    }
    @GetMapping("/")
    public String rootReq(HttpServletRequest request){
    	StringBuffer url=request.getRequestURL();
    	return HttpClientUtil.getInstance().sendHttpGet(url.toString()+"getUserAll?url="+url);
    }

}
